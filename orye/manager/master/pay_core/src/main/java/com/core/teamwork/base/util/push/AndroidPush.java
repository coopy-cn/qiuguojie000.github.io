package com.core.teamwork.base.util.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AndroidPush {

//	private static final String masterSecret = "78119539f6466fed550fff61";
//	private static final String appKey = "7663919d6c2dbd2bfff57814";

//	private static void sendPush() {
//		// 初始化推送，第三个参数是离线时长
//		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 259200);
//		PushPayload payload = buildPushObject_all_all_alert();
//		try {
//			PushResult result = jpushClient.sendPush(payload);
//			System.out.println(result);
//			String msgid = getPushMsgID(result);
//			ReceivedsResult rr = report(msgid);
//			System.out.println(rr);
//		} catch (APIConnectionException e) {
//		} catch (APIRequestException e) {
//		}
//
//	}
	
	private static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll("df");
    }

//	public static void main(String[] args) {
//		sendPush();
////		System.out.println(report("745208948"));
//	}
	/**
	 * 给单个Android设备推送弹窗通知
	 * @param title 弹窗标题
	 * @param content 弹窗内容
	 * @param receiver 接收者
	 * @param extra 附加字段,没有就传null
	 * @return 消息ID
	 */
	public static String pushAlertByOne(String title, String content,
			String receiver, String masterSecret, String appKey, Map<String, String> extra, int type){
		String r = "";
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 259200);
		List<String> receivers = new ArrayList<String>();
		receivers.add(receiver);
		PushPayload payload = buildeAlert(title, content, receivers, extra, type);
		try {
			PushResult result = jpushClient.sendPush(payload);
			r = getPushMsgID(result);
			System.out.println(result.getOriginalContent());
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * 给多个Android设备推送弹窗通知
	 * @param title 弹窗标题
	 * @param content 弹窗内容
	 * @param receiver 接收者们
	 * @param extra 附加字段,没有就传null
	 * @return 消息ID
	 */
	public static String pushAlertByMore(String title, String content,
			List<String> receivers, String masterSecret, String appKey, Map<String, String> extra, int type){
		String r = "";
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 259200);
		PushPayload payload = buildeAlert(title, content, receivers, extra, type);
		try {
			PushResult result = jpushClient.sendPush(payload);
			r = getPushMsgID(result);
			System.out.println(result.getOriginalContent());
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * 给单个Android设备推送自定义消息
	 * @param content 消息体(一般为json,与C端协商好之后由C端自行解析)
	 * @param receiver 接收者
	 * @param extra 附加字段,没有就传null
	 * @return 消息ID
	 */
	public static String pushMessageByOne(String content,
			String receiver, String masterSecret, String appKey, Map<String, String> extra, int type){
		String r = "";
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 259200);
		List<String> receivers = new ArrayList<String>();
		receivers.add(receiver);
		PushPayload payload = buildeMessage(content, receivers, extra, type);
		try {
			PushResult result = jpushClient.sendPush(payload);
			r = getPushMsgID(result);
			System.out.println(result.getOriginalContent());
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * 给多个Android设备推送自定义消息
	 * @param content 消息体(一般为json,与C端协商好之后由C端自行解析)
	 * @param receiver 接收者们
	 * @param extra 附加字段,没有就传null
	 * @return 消息ID
	 */
	public static String pushMessageByMore(String title, String content,
			List<String> receivers, String masterSecret, String appKey, Map<String, String> extra, int type){
		String r = "";
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 259200);
		PushPayload payload = buildeMessage(content, receivers, extra, type);
		try {
			PushResult result = jpushClient.sendPush(payload);
			r = getPushMsgID(result);
			System.out.println(result.getOriginalContent());
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	

	/**
	 * 构建<弹窗>推送
	 * @param title 标题
	 * @param content 内容
	 * @param receivers 接收者们
	 * @param extra 
	 * @return
	 */
	private static PushPayload buildeAlert(String title, String content,
			List<String> receivers, Map<String, String> extra, int type) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(
						Audience.newBuilder()
								.addAudienceTarget(
										AudienceTarget.alias(receivers))
								.build())
				.setNotification(Notification.android(content, title, extra))
				.build();
	}


	/**
	 * 构建<消息>推送
	 * 
	 * @param content
	 *            推送内容
	 * @param receivers
	 *            接收者们
	 * @param extra
	 *            附加字段
	 * @return
	 */
	private static PushPayload buildeMessage(String content,
			List<String> receivers, Map<String, String> extra, int type) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(
						Audience.newBuilder()
								.addAudienceTarget(
										AudienceTarget.alias(receivers))
								.build())
				.setMessage(
						Message.newBuilder().setMsgContent(content)
								.addExtras(extra).build()).build();
	}
	
	private static String getPushMsgID(PushResult pushResult){
		if(pushResult == null) 
			return "";
		if(pushResult.getOriginalContent() == null || pushResult.getOriginalContent().equals(""))
			return "";
		JSONObject json = JSON.parseObject(pushResult.getOriginalContent());
		return json.getString("msg_id");
	}
	
	/**
	 * 根据消息ID获取推送结果
	 * @param msgId 消息ID
	 * @return
	 */
	public static ReceivedsResult report(String msgId, String masterSecret, String appKey){
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        try {
            ReceivedsResult result = jpushClient.getReportReceiveds(msgId);
//            System.out.println("result.isResultOK():"+result.isResultOK());
//            System.out.println("result.received_list.size():"+result.received_list.size());
            return result;
        } catch (APIConnectionException e) {
            // Connection error, should retry later
        	e.printStackTrace();
        } catch (APIRequestException e) {
        	e.printStackTrace();
            // Should review the error, and fix the request
//            LOG.error("Should review the error, and fix the request", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Code: " + e.getErrorCode());
//            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return null;
	}

}
