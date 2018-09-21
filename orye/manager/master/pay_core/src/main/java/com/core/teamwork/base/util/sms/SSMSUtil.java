package com.core.teamwork.base.util.sms;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

/**
 * 
 * 项目名称：yzqyy-client 类名称：SSMSUtil 类描述：短信接口类 创建人：buyuer 创建时间：2015年4月29日 下午3:51:45
 */
public class SSMSUtil {

//    private static SSMSUtil ssmsUtil = new SSMSUtil();

    public SSMSUtil() {
    }
    public String sendMessage(String mobel, String code){
        String ssms_content = getContent() + code;
        try {
            sendSSMS(ssms_content, mobel);
        } catch (IOException e) {
        }
        
        return code;
    }
    /**
     * 
     * sendSSMS(短信发送工具类)
     * 
     * @param content
     *            :发送内容 ,moble：电话号码
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public void sendSSMS(String content, String moble) throws IOException {
        // 创建StringBuffer对象用来操作字符串
        StringBuffer sb = new StringBuffer(init());
        // 向StringBuffer追加手机号码
        sb.append("&mobile=" + moble);
        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&content=" + URLEncoder.encode(content, "UTF-8"));
        // 追加发送时间，可为空，为空为及时发送
        sb.append("&stime=");
        // extno为扩展码，必须为数字 可为空
        sb.append("&extno=");
        // 创建url对象
        // String temp = new String(sb.toString().getBytes("GBK"),"UTF-8");
        URL url = new URL(sb.toString());
        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置编码格式
        connection.setRequestProperty("Charset", "UTF-8");
        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");
        // 发送
        InputStream is = url.openStream();
        // 转换返回值
        String returnStr = convertStreamToString(is);
        // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功 具体见说明文档
        System.out.println(returnStr);

    }

    /**
     * 转换返回值类型为UTF-8格式.
     * 
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }

    /**
     * init(初始化短信类)
     * 
     * @throws UnsupportedEncodingException
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    private String init() throws UnsupportedEncodingException {
        ResourceBundle bundle = ResourceBundle.getBundle("ssms_config");
        String url = bundle.getString("url").trim();
        String name = bundle.getString("name").trim();
        String password = bundle.getString("password").trim();
        String sign = bundle.getString("sign").trim();
        String type = bundle.getString("type").trim();
        StringBuffer sb = new StringBuffer(url);
        sb.append("name=" + name);
        sb.append("&pwd=" + password);
        sb.append("&sign=" + URLEncoder.encode(sign, "UTF-8"));
        sb.append("&type=" + type);
        return sb.toString();
    }

    /**
     * 
     * getContent(获取短信模板)
     * 
     * @return String 短信模板
     * @Exception 异常对象
     */
    private String getContent() {
        ResourceBundle bundle = ResourceBundle.getBundle("ssms_config");
        String content = bundle.getString("content").trim();
        return content;
    }

    public static void main(String[] args) {
        /*
         * try { sendSSMS("测试", "13042197925"); } catch (IOException e) { e.printStackTrace(); }
         */
    	SSMSUtil s = new SSMSUtil();
         System.out.println(s.getContent());
    }
}
