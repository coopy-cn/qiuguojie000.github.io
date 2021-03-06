package com.core.teamwork.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.teamwork.fileupload.client.AbstractClient;
import com.core.teamwork.fileupload.client.StorageClient;
import com.core.teamwork.fileupload.client.StorageClientFactory;
import com.core.teamwork.fileupload.client.TrackerClient;
import com.core.teamwork.fileupload.client.TrackerClientFactory;
import com.core.teamwork.fileupload.data.GroupInfo;
import com.core.teamwork.fileupload.data.Result;
import com.core.teamwork.fileupload.data.StorageInfo;
import com.core.teamwork.fileupload.data.UploadStorage;

public class FastdfsClientImpl extends AbstractClient implements FastdfsClient {

	private static Logger logger = LoggerFactory
			.getLogger(FastdfsClientImpl.class);
	private GenericKeyedObjectPool<String, TrackerClient> trackerClientPool;
	private GenericKeyedObjectPool<String, StorageClient> storageClientPool;
	private List<String> trackerAddrs = new ArrayList<String>();
	private String visitPath;
	private Map<String, String> storageIpMap = new ConcurrentHashMap<String, String>();

	public FastdfsClientImpl(List<String> trackerAddrs,String visit_path) throws Exception {
		super();
		this.trackerAddrs = trackerAddrs;
		trackerClientPool = new GenericKeyedObjectPool<String, TrackerClient>(
				new TrackerClientFactory());
		storageClientPool = new GenericKeyedObjectPool<String, StorageClient>(
				new StorageClientFactory());
		visitPath = visit_path;
		updateStorageIpMap();
	}

	public FastdfsClientImpl(List<String> trackerAddrs,String visit_path,
			GenericKeyedObjectPool<String, TrackerClient> trackerClientPool,
			GenericKeyedObjectPool<String, StorageClient> storageClientPool) {
		super();
		this.visitPath = visit_path;
		this.trackerAddrs = trackerAddrs;
		this.trackerClientPool = trackerClientPool;
		this.storageClientPool = storageClientPool;
	}

	public void close() {
		this.trackerClientPool.close();
		this.storageClientPool.close();
	}

	public String upload(File file, String ext, Map<String, String> meta)
			throws Exception {
		String fileId = uploadToFileId(file, ext, meta);
		return visitPath+fileId;
	}
	
	public Map<String, String> uploadFile(File file, String ext, Map<String, String> meta)
			throws Exception {
		Map<String, String> returnMap = new HashMap<String, String>();
		String fileId = uploadToFileId(file, ext, meta);
		returnMap.put("domain", visitPath);
		returnMap.put("fileId", fileId);
		return returnMap;
	}
	
	private String uploadToFileId(File file, String ext, Map<String, String> meta)throws Exception{
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		String storageAddr = null;
		String fileId = null;
		try {
			trackerClient = trackerClientPool.borrowObject(trackerAddr);
			Result<UploadStorage> result = trackerClient.getUploadStorage();
			if (result.getCode() == 0) {
				storageAddr = result.getData().getAddress();
				storageClient = storageClientPool.borrowObject(storageAddr);

				String extname = ext;
				if (ext == null) {
					extname = getFileExtName(file);
				}
				Result<String> result2 = storageClient.upload(file, extname,
						result.getData().getPathIndex());
				if (result2.getCode() == 0) {
					fileId = result2.getData();
					// if meta key value
					if (meta != null) {
						this.setMeta(fileId, meta);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (storageClient != null) {
				storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return fileId;
	}

	public String uploadSlave(File file, String fileid, String prefix,
			String ext) throws Exception {

		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		String storageAddr = null;
		String fileId = null;
		try {
			trackerClient = trackerClientPool.borrowObject(trackerAddr);

			if (fileid != null) {
				String[] tupple = splitFileId(fileid);
				String groupname = tupple[0];
				String filename = tupple[1];

				Result<String> result = trackerClient.getUpdateStorageAddr(
						groupname, filename);
				if (result.getCode() == 0) {
					storageAddr = result.getData();
					storageClient = storageClientPool.borrowObject(storageAddr);
					Result<String> result2 = storageClient.uploadSlave(file,
							filename, prefix, ext, null);
					if (result2.getCode() == 0) {
						fileId = result2.getData();
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (storageClient != null) {
				storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return fileId;
	}

	private void updateStorageIpMap() throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		try {
			trackerClient = trackerClientPool.borrowObject(trackerAddr);
			Result<List<GroupInfo>> result = trackerClient.getGroupInfos();
			if (result.getCode() == 0) {
				List<GroupInfo> groupInfos = result.getData();
				for (GroupInfo groupInfo : groupInfos) {
					Result<List<StorageInfo>> result2 = trackerClient
							.getStorageInfos(groupInfo.getGroupName());
					if (result2.getCode() == 0) {
						List<StorageInfo> storageInfos = result2.getData();
						for (StorageInfo storageInfo : storageInfos) {
							String hostPort = storageInfo.getDomainName();
							if (storageInfo.getStorageHttpPort() != 80) {
								hostPort = hostPort + ":"
										+ storageInfo.getStorageHttpPort();
							}
							storageIpMap.put(storageInfo.getIpAddr() + ":"
									+ storageInfo.getStoragePort(), hostPort);
						}
					}
				}
			} else {
				throw new Exception("Get getGroupInfos Error");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (trackerClient != null) {
				trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
	}

	private String getDownloadHostPort(String storageAddr) throws Exception {
		String downloadHostPort = storageIpMap.get(storageAddr);
		if (downloadHostPort == null) {
			updateStorageIpMap();
			downloadHostPort = storageIpMap.get(storageAddr);
		}
		return downloadHostPort;
	}

	public Boolean setMeta(String fileId, Map<String, String> meta)
			throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		Boolean result = null;
		String storageAddr = null;
		try {
			FastDfsFile fastDfsFile = new FastDfsFile(fileId);
			trackerClient = trackerClientPool.borrowObject(trackerAddr);
			Result<String> result2 = trackerClient.getUpdateStorageAddr(
					fastDfsFile.group, fastDfsFile.fileName);
			if (result2.getCode() == 0) {
				storageAddr = result2.getData();
				storageClient = storageClientPool.borrowObject(storageAddr);
				Result<Boolean> result3 = storageClient.setMeta(
						fastDfsFile.group, fastDfsFile.fileName, meta);
				if (result3.getCode() == 0 || result3.getCode() == 0) {
					result = result3.getData();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (storageClient != null) {
				storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return result;
	}

	public Map<String, String> getMeta(String fileId) throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		Map<String, String> meta = null;
		String storageAddr = null;
		try {
			FastDfsFile fastDfsFile = new FastDfsFile(fileId);
			trackerClient = trackerClientPool.borrowObject(trackerAddr);
			Result<String> result2 = trackerClient.getUpdateStorageAddr(
					fastDfsFile.group, fastDfsFile.fileName);
			if (result2.getCode() == 0) {
				storageAddr = result2.getData();
				storageClient = storageClientPool.borrowObject(storageAddr);
				Result<Map<String, String>> result3 = storageClient.getMeta(
						fastDfsFile.group, fastDfsFile.fileName);
				if (result3.getCode() == 0 || result3.getCode() == 0) {
					meta = result3.getData();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (storageClient != null) {
				storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return meta;
	}

	public String getUrl(String fileId) throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		String url = null;
		try {
			FastDfsFile fastDfsFile = new FastDfsFile(fileId);
			trackerClient = trackerClientPool.borrowObject(trackerAddr);
			Result<String> result = trackerClient.getDownloadStorageAddr(
					fastDfsFile.group, fastDfsFile.fileName);
			if (result.getCode() == 0) {
				String hostPort = getDownloadHostPort(result.getData());
				url = "http://" + hostPort + "/" + fastDfsFile.fileName;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (trackerClient != null) {
				trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return url;
	}

	public String upload(File file) throws Exception {
		String fileName = file.getName();
		return upload(file, fileName);
	}
	
	public Map<String, String> uploadFile(File file) throws Exception {
		String fileName = file.getName();
		return uploadFile(file, fileName);
	}

	public String upload(File file, String fileName) throws Exception {
		return this.upload(file, fileName, null);
	}
	
	public Map<String, String> uploadFile(File file, String fileName) throws Exception {
		return this.uploadFile(file, fileName, null);
	}

	public Boolean delete(String fileId) throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		Boolean result = false;
		String storageAddr = null;
		try {
			FastDfsFile fastDfsFile = new FastDfsFile(fileId);
			trackerClient = trackerClientPool.borrowObject(trackerAddr);
			Result<String> result2 = trackerClient.getUpdateStorageAddr(
					fastDfsFile.group, fastDfsFile.fileName);
			if (result2.getCode() == 0) {
				storageAddr = result2.getData();
				storageClient = storageClientPool.borrowObject(storageAddr);
				Result<Boolean> result3 = storageClient.delete(
						fastDfsFile.group, fastDfsFile.fileName);
				if (result3.getCode() == 0 || result3.getCode() == 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (storageClient != null) {
				storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return result;
	}

	public String getTrackerAddr() {
		Random r = new Random();
		int i = r.nextInt(trackerAddrs.size());
		return trackerAddrs.get(i);
	}

	private String getFileExtName(File file) {
		String name = file.getName();
		if (name != null) {
			int i = name.lastIndexOf('.');
			if (i > -1) {
				return name.substring(i + 1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private class FastDfsFile {
		private String group;
		private String fileName;

		public FastDfsFile(String fileId) {
			super();
			int pos = fileId.indexOf("/");
			group = fileId.substring(0, pos);
			fileName = fileId.substring(pos + 1);
		}

	}

	public String upload(InputStream ins,String path) throws Exception {
		File file = new File(path);
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
		return upload(file);
	}

}
