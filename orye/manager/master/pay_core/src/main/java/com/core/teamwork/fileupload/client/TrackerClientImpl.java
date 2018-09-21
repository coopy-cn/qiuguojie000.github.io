package com.core.teamwork.fileupload.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import com.core.teamwork.fileupload.FastdfsClientConfig;
import com.core.teamwork.fileupload.command.CloseCmd;
import com.core.teamwork.fileupload.command.Command;
import com.core.teamwork.fileupload.command.GroupInfoCmd;
import com.core.teamwork.fileupload.command.QueryDownloadCmd;
import com.core.teamwork.fileupload.command.QueryUpdateCmd;
import com.core.teamwork.fileupload.command.QueryUploadCmd;
import com.core.teamwork.fileupload.command.StorageInfoCmd;
import com.core.teamwork.fileupload.data.GroupInfo;
import com.core.teamwork.fileupload.data.Result;
import com.core.teamwork.fileupload.data.StorageInfo;
import com.core.teamwork.fileupload.data.UploadStorage;

public class TrackerClientImpl implements TrackerClient{
	
	private Socket socket;
	private String host;
	private Integer port;
	private Integer connectTimeout = FastdfsClientConfig.DEFAULT_CONNECT_TIMEOUT * 1000;
	private Integer networkTimeout = FastdfsClientConfig.DEFAULT_NETWORK_TIMEOUT * 1000;
	
	private boolean close = false; // 关闭连接标志位，true表示关闭，false表示连接
	
	public TrackerClientImpl(String address){
		super();
		String[] hostport = address.split(":");
		this.host = hostport[0];
		this.port = Integer.valueOf(hostport[1]);
	}
	
	public TrackerClientImpl(String address,Integer connectTimeout, Integer networkTimeout){
		this(address);
		this.connectTimeout = connectTimeout;
		this.networkTimeout = networkTimeout;
	}
	
	private Socket getSocket() throws IOException{
		close = isServerClose(socket);
		if(socket==null || close){
			socket = new Socket();
			socket.setKeepAlive(true);//开启保持活动状态的套接字  
			socket.setSoTimeout(networkTimeout);
			socket.connect(new InetSocketAddress(host, port),connectTimeout);
		}
		return socket;
	}
	

	public void close() throws IOException{
		Socket socket = getSocket();
		Command<Boolean> command = new CloseCmd();
		command.exec(socket);
		socket.close();
		socket = null;
	}
	
	/** 
	* 判断是否断开连接，断开返回true,没有返回false 
	* @param socket 
	* @return 
	*/  
	private Boolean isServerClose(Socket socket){  
	   try{  
	    socket.sendUrgentData(0);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信  
	    return false;  
	   }catch(Exception se){  
	    return true;  
	   }  
	} 

	
	public Result<UploadStorage> getUploadStorage() throws IOException{
		Socket socket = getSocket();
		Command<UploadStorage> command = new QueryUploadCmd();
		return command.exec(socket);
	}
	
	public Result<String> getUpdateStorageAddr(String group,String fileName) throws IOException{
		Socket socket = getSocket();
		Command<String> cmd = new QueryUpdateCmd(group,fileName);
		return cmd.exec(socket);
	}
	
	public Result<String> getDownloadStorageAddr(String group,String fileName) throws IOException{
		Socket socket = getSocket();
		Command<String> cmd = new QueryDownloadCmd(group,fileName);
		return cmd.exec(socket);
	}
	
	public Result<List<GroupInfo>> getGroupInfos() throws IOException{
		Socket socket = getSocket();
		Command<List<GroupInfo>> cmd = new GroupInfoCmd();
		return cmd.exec(socket);
	}
	
	public Result<List<StorageInfo>> getStorageInfos(String group) throws IOException{
		Socket socket = getSocket();
		Command<List<StorageInfo>> cmd = new StorageInfoCmd(group);
		return cmd.exec(socket);
	}
	
}
