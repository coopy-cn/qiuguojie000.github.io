package com.core.teamwork.base.util.sms;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetSMSCodeUtil {
	// 创建一个可重用固定线程数的线程池
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    private static GetSMSCodeUtil instance = null;
    private GetSMSCodeUtil(){}
    public static synchronized GetSMSCodeUtil getInstence(){
    	if(instance==null){
    		instance =  new GetSMSCodeUtil();
    	}
    	return instance;
    }
    public  String sendSSms(String phone) {
        String code = getCode();
        SSMSThread ssmsThread = new SSMSThread(phone, code);
        pool.execute(ssmsThread);
        return code;
    }
    
    public  String sendSSmsDydx(String phone) {
        String code = getCode();
        SSMSThreadDiDuanXin ssmsThread = new SSMSThreadDiDuanXin(phone, code);
        pool.execute(ssmsThread);
        return code;
    }
	
    private String getCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return code + "";
    }
}
