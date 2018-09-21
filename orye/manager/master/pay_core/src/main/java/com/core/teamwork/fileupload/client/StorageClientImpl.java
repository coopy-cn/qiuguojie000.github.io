package com.core.teamwork.fileupload.client;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

import com.core.teamwork.fileupload.FastdfsClientConfig;
import com.core.teamwork.fileupload.command.ActiveTestCmd;
import com.core.teamwork.fileupload.command.CloseCmd;
import com.core.teamwork.fileupload.command.Command;
import com.core.teamwork.fileupload.command.DeleteCmd;
import com.core.teamwork.fileupload.command.GetMetaDataCmd;
import com.core.teamwork.fileupload.command.SetMetaDataCmd;
import com.core.teamwork.fileupload.command.UploadCmd;
import com.core.teamwork.fileupload.command.UploadSlaveCmd;
import com.core.teamwork.fileupload.data.Result;

public class StorageClientImpl extends AbstractClient implements StorageClient {

    private Socket socket;
    private String host;
    private Integer port;
    private Integer connectTimeout = FastdfsClientConfig.DEFAULT_CONNECT_TIMEOUT * 1000;
    private Integer networkTimeout = FastdfsClientConfig.DEFAULT_NETWORK_TIMEOUT * 1000;

    private Socket getSocket() throws IOException {
        if (socket == null) {
            socket = new Socket();
            socket.setSoTimeout(networkTimeout);
            socket.connect(new InetSocketAddress(host, port), connectTimeout);
        }
        return socket;
    }

    public StorageClientImpl(String address) {
        super();
        String[] hostport = address.split(":");
        this.host = hostport[0];
        this.port = Integer.valueOf(hostport[1]);
    }

    public StorageClientImpl(String address, Integer connectTimeout, Integer networkTimeout) {
        this(address);
        this.connectTimeout = connectTimeout;
        this.networkTimeout = networkTimeout;
    }

    public void close() throws IOException {
        Socket socket = getSocket();
        Command<Boolean> command = new CloseCmd();
        command.exec(socket);
        socket.close();
        socket = null;
    }

    
    public Result<String> uploadSlave(File file, String fileid, String slavePrefix, String ext, Map<String, String> meta) throws IOException {
        Socket socket = getSocket();
        UploadSlaveCmd uploadSlaveCmd = new UploadSlaveCmd(file,fileid,slavePrefix,ext);
        Result<String> result = uploadSlaveCmd.exec(socket);

        if (meta != null) {
            String[] tupple = super.splitFileId(fileid);
            if (tupple != null) {
                String group = tupple[0];
                String fileName = tupple[1];
                this.setMeta(group, fileName, meta);
            }
        }
        return result;
    }

    public Result<String> upload(File file, String fileName, byte storePathIndex) throws IOException {
        Socket socket = getSocket();
        UploadCmd uploadCmd = new UploadCmd(file, fileName, storePathIndex);
        return uploadCmd.exec(socket);
    }

    public Result<Boolean> delete(String group, String fileName) throws IOException {
        Socket socket = getSocket();
        DeleteCmd deleteCmd = new DeleteCmd(group, fileName);
        return deleteCmd.exec(socket);
    }

    
    public Result<Boolean> setMeta(String group, String fileName,
                                   Map<String, String> meta) throws IOException {
        Socket socket = getSocket();
        SetMetaDataCmd setMetaDataCmd = new SetMetaDataCmd(group, fileName, meta);
        return setMetaDataCmd.exec(socket);
    }

    
    public Result<Map<String, String>> getMeta(String group, String fileName)
            throws IOException {
        Socket socket = getSocket();
        GetMetaDataCmd getMetaDataCmd = new GetMetaDataCmd(group, fileName);
        return getMetaDataCmd.exec(socket);
    }


    /**
     * check storage client socket is closed
     *
     * @return boolean
     */
    
    public boolean isClosed() {

        if (this.socket == null) {
            return true;
        }

        if (this.socket.isClosed()){
            return true;
        }else {
            //根据fastdfs的Active_Test_Cmd测试连�?��??
            ActiveTestCmd atcmd = new ActiveTestCmd();
            try {
                Result<Boolean> result = atcmd.exec(getSocket());
                //True,表示连接正常
                if(result.getData()){
                    return false;
                }else {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //有异常，直接丢掉这个连接，让连接池回�?
            return true;
        }
    }

}
