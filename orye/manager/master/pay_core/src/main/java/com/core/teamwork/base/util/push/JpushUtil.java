package com.core.teamwork.base.util.push;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.teamwork.base.util.ReadPro;

public class JpushUtil {
	
	private static JpushUtil jpushUtil;
	private JPushClient client;
	
	private JpushUtil(){
		
	}
	
	public static JpushUtil getInstance(){
		if(jpushUtil==null){
			jpushUtil = new JpushUtil();
//			jpushUtil.client = new JPushClient("9459917ab5a5967e303f10d7","059f201f6b2e94efa9683149");
			jpushUtil.client = new JPushClient(ReadPro.getValue("jpush_masterSecret","9459917ab5a5967e303f10d7"),ReadPro.getValue("jpush_appKey","059f201f6b2e94efa9683149"));
		}
		return jpushUtil;
	}
	
	/**
	 * 推送安卓通知
	 * @param tag
	 * @param alias
	 * @param alert
	 * @param title
	 * @param extras
	 */
	public String pushAlert(String[] tag,String[] alias,String alert,String title,Map<String,String> extras){
		String msgId = null;
		PushPayload pushPayload = createCommonBuilder(tag, alias)
				.setNotification(Notification.android(alert, title, extras)).build();
		try {
			msgId = getPushMsgID(client.sendPush(pushPayload));
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return msgId;
	}
	
	/**
	 * 推送安卓消息
	 * @param tag
	 * @param alias
	 * @param msgContent
	 * @param title
	 * @param extras
	 * @return
	 */
	public String pushMessage(String[] tag,String[] alias,String msgContent,String title,Map<String,String> extras){
		String msgId = null;
		PushPayload pushPayload = createCommonBuilder(tag, alias)
				.setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).addExtras(extras).build()).build();
		try {
			msgId = getPushMsgID(client.sendPush(pushPayload));
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return msgId;
	}
	
	private Builder createCommonBuilder(String[] tag,String[] alias) {
		Builder builder = PushPayload.newBuilder().setPlatform(Platform.android());
		Audience audience = null;
		//tag和alias都为空则全部推送
		if(tag==null&&alias==null){
			audience = Audience.all();
		}else{
			if(tag==null){
				audience = Audience.alias(alias);
			}else if(alias==null){
				audience = Audience.tag(tag);
			}else{
				audience = Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tag)).addAudienceTarget(AudienceTarget.alias(alias)).build();
			}
		}
		return builder.setAudience(audience);
    }
	
	private String getPushMsgID(PushResult pushResult){
		if(pushResult == null) 
			return "";
		if(pushResult.getOriginalContent() == null || pushResult.getOriginalContent().equals(""))
			return "";
		JSONObject json = JSON.parseObject(pushResult.getOriginalContent());
		return json.getString("msg_id");
	}
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("type","1");
		map.put("value","20");
		JpushUtil.getInstance().pushAlert(null,null,"test","title",map);
	}
}
