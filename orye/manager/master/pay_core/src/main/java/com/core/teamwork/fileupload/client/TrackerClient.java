package com.core.teamwork.fileupload.client;

import java.io.IOException;
import java.util.List;

import com.core.teamwork.fileupload.data.GroupInfo;
import com.core.teamwork.fileupload.data.Result;
import com.core.teamwork.fileupload.data.StorageInfo;
import com.core.teamwork.fileupload.data.UploadStorage;

public interface TrackerClient {

	public Result<UploadStorage> getUploadStorage() throws IOException;
	public Result<String> getUpdateStorageAddr(String group,String fileName) throws IOException;
	public Result<String> getDownloadStorageAddr(String group,String fileName) throws IOException;
	public Result<List<GroupInfo>> getGroupInfos() throws IOException;
	public Result<List<StorageInfo>> getStorageInfos(String group) throws IOException;
	public void close() throws IOException;
	
}
