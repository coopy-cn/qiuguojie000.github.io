package com.core.teamwork.base.util.sms;


public class SSMSThreadDiDuanXin  implements Runnable{

    private String phone_number;
    
    private String code;
    
    public SSMSThreadDiDuanXin(String phone_number ,String code) {
        this.phone_number = phone_number;
        this.code = code;
    }
    public void run() {
        SSMSUtil ssmsUtil = new SSMSUtil();
        ssmsUtil.sendMessage(phone_number, code);
    }
}
