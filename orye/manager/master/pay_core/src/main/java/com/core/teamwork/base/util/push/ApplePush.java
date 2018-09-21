package com.core.teamwork.base.util.push;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class ApplePush {

//	public static void main(String[] args) {
//		System.out.println(path);
//		List<String> list = new ArrayList<String>();
//		list.add("7f668b05b7af1a5cd7c8ec17088d6cb30df322c7fcd1ebd2db0704bf678d24f0");
//		ApplePush ap = new ApplePush();
//		ap.push(list, "iyizhan测试，收到了吗？？？", path, password, null);
//
//	}

	// private static Log log = LogFactory.getLog(MainSend.class.getName());

	// public static String path =
	// ApplePush.class.getClassLoader().getResource("izcProDevTest.p12").getPath();
	// public static String path =
	// ClassLoader.getSystemResource("izcProDevTest.p12").getPath();
	// Thread.currentThread().getContextClassLoader().getResource("izcProDevTest.p12").getPath();
//	public static String path = "/zywa/keystore/izcProDevTest.p12";
//	public static String password = "1";s

	// String password = "46511616";// 证书密码

	/*
	 * //************************************************************************
	 * *** 测试推送服务器地址：gateway.sandbox.push.apple.com /2195
	 * 产品推送服务器地址：gateway.push.apple.com / 2195 需要javaPNS_2.2.jar包
	 * ************************************************************************
	 *//*
	*//**
	 * 这是一个比较简单的推送方法， apple的推送方法
	 * 
	 * @param tokens
	 *            iphone手机获取的token
	 * @param path
	 *            这里是一个.p12格式的文件路径，需要去apple官网申请一个
	 * @param password
	 *            p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
	 * @param message
	 *            推送消息的内容
	 * @param count
	 *            应用图标上小红圈上的数值
	 * @param sendCount
	 *            单发还是群发 true：单发 false：群发
	 */

	public static int push(List<String> tokens, String message, String path,
			String password, Map<String, String> extra,boolean isTest) {
		try {
			boolean sendCount = true;
			if (tokens.size() > 1)
				sendCount = false;
			// System.out.println("判断完成");
			// message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}
			PushNotificationPayload payLoad = PushNotificationPayload
					.fromJSON("{'aps':{'alert':'" + message + "'}}");
			System.out.println("推送内容==>" + "{'aps':{'alert':'" + message
					+ "'}}");
			payLoad.addAlert(message); // 消息内容
			payLoad.addBadge(1); // iphone应用图标上小红圈上的数值
			payLoad.addSound("default"); // 铃音 默认
			payLoad.addCustomDictionary("content-available", 1);
			// 设置附加字典
			if (extra != null) {
				for (Map.Entry<String, String> entry : extra.entrySet()) {
					String key = entry.getKey().toString();
					String value = entry.getValue().toString();
					payLoad.addCustomDictionary(key, value);
				}
			}
			// payLoad.addCustomDictionary("type", "1");
			PushNotificationManager pushManager = new PushNotificationManager();
			// System.out.println("实例pushManager对象");
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			// System.out.println(path);
			pushManager
					.initializeConnection(new AppleNotificationServerBasicImpl(
							path, password, isTest));// true：表示的是产品发布推送服务
			// false：表示的是产品测试推送服务
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (sendCount) {
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(
						device, payLoad, isTest);
				notifications.add(notification);
			} else {
				// System.out
				// .println("--------------------------apple 推送 群-------");
				List<Device> device = new ArrayList<Device>();
				for (String token : tokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payLoad, device);
			}
			// 去回执服务器找哪些成功了
			List<PushedNotification> success = PushedNotification
					.findSuccessfulNotifications(notifications);
			System.out.println("iPhone推送成功 (" + success.size() + ")");
			return success.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// public static int newSend(String cerPath, String password,
	// boolean production, List<String> tokens, String message, String flag) {
	// String keystore = getCerPath(cerPath);// 证书路径和证书名
	// int threadThreads = tokens.size(); // 线程数
	// try {
	// // 建立与Apple服务器连接
	// AppleNotificationServer server = new AppleNotificationServerBasicImpl(
	// keystore, password, production);
	// List<PayloadPerDevice> list = new ArrayList<PayloadPerDevice>();
	// PushNotificationPayload payload = new PushNotificationPayload();
	// payload.addAlert(message);
	// payload.addSound("default");// 声音
	// payload.addBadge(1);// 图标小红圈的数值
	// payload.addCustomDictionary("flag", flag);
	// for (int i = 0; i < tokens.size(); i++) {
	// PayloadPerDevice pay = new PayloadPerDevice(payload, tokens
	// .get(i));// 将要推送的消息和手机唯一标识绑定
	// list.add(pay);
	// }
	// System.out.println("newPush--->" + list.size());
	// NotificationThreads work = new NotificationThreads(server, list,
	// threadThreads);//
	// work.setListener(DEBUGGING_PROGRESS_LISTENER);// 对线程的监听，一定要加上这个监听
	// work.start(); // 启动线程
	// // work.waitForAllThreads();// 等待所有线程启动完成
	// try {
	// work.waitForAllThreads(true);
	// } catch (InterruptedException e) {
	// }
	// return work.getPushedNotifications().getSuccessfulNotifications()
	// .size();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return 0;
	// }
	//
	// public int sendpush(String cerPath, String cerPwd, boolean isDevelop,
	// List<String> tokens, String message, String flag,String cus,String
	// sound,int badge) {
	// try {
	// String path = getCerPath(cerPath);
	// System.out.println("正式进入推送:\r证书地址:" + path + "\r证书密码:" + cerPwd
	// + "\r部署环境:" + isDevelop + "\r发送内容" + message+"\rtoken[0]"+tokens.get(0));
	// String password = cerPwd;
	// boolean sendCount = true;
	// // System.out.println("准备判断true和false");
	// if (tokens.size() > 1)
	// sendCount = false;
	// // System.out.println("判断完成");
	// // message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}
	// PushNotificationPayload payLoad = PushNotificationPayload
	// .fromJSON("{'aps':{'alert':'" + message + "'}}");
	// System.out.println("推送内容-->" + "{'aps':{'alert':'" + message
	// + "'}}");
	// // System.out.println("实例payLoad对象");
	// payLoad.addAlert(message); // 消息内容
	// // System.out.println("加入消息体");
	// payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
	// // System.out.println("设置Badge");
	// payLoad.addSound(sound); // 铃音 默认
	// // System.out.println("设置铃音");
	// if(!StaticValue.formatNull(cus).equals(""))
	// payLoad.addCustomDictionary("custom", cus);
	// // System.out.println("设置参数字典type");
	// PushNotificationManager pushManager = new PushNotificationManager();
	// // System.out.println("实例pushManager对象");
	// // true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
	// // System.out.println(path);
	// pushManager
	// .initializeConnection(new AppleNotificationServerBasicImpl(
	// path, password, isDevelop));// true：表示的是产品发布推送服务
	// // false：表示的是产品测试推送服务
	// List<PushedNotification> notifications = new
	// ArrayList<PushedNotification>();
	// // 发送push消息
	// // if (sendCount) {
	// // System.out
	// // .println("--------------------------apple 推送 单-------");
	// // try {
	// // Device device = new BasicDevice();
	// // device.setToken(tokens.get(0));
	// // PushedNotification notification = pushManager.sendNotification(
	// // device, payLoad, true);
	// // notifications.add(notification);
	// // } catch (Exception e) {
	// //
	// System.out.println("------------APNS出错--------------"+e.fillInStackTrace());
	// // }
	// // } else {
	// // System.out
	// // .println("--------------------------apple 推送 群-------");
	// try {
	// List<Device> device = new ArrayList<Device>();
	// for (String token : tokens) {
	// device.add(new BasicDevice(token));
	// }
	// System.out.println("这是List<Device>的size:" + device.size());
	// notifications = pushManager.sendNotifications(payLoad, device);
	//
	// } catch (Exception e) {
	// System.out.println("------------APNS出错--------------"+e.fillInStackTrace());
	// }
	// // }
	// // 去回执服务器找哪些推送失败了
	// // List<PushedNotification> fail = PushedNotification
	// // .findFailedNotifications(notifications);
	// // 去回执服务器找哪些成功了
	// List<PushedNotification> success = PushedNotification
	// .findSuccessfulNotifications(notifications);
	// // System.out.println("成功 (" + success.size() + "):");
	// return success.size();
	// } catch (Exception e) {
	// System.out.println("------------APNS出错111--------------"+e.fillInStackTrace());
	// e.printStackTrace();
	// }
	// return 0;
	// }
	//
	//
	// public int sendpush(String cerPath, String cerPwd, boolean isDevelop,
	// List<String> tokens, String message, String flag) {
	// try {
	// String path = getCerPath(cerPath);
	// System.out.println("正式进入推送:\r证书地址:" + path + "\r证书密码:" + cerPwd
	// + "\r部署环境:" + isDevelop + "\r发送内容" + message+"\rtoken[0]"+tokens.get(0));
	// String password = cerPwd;
	// boolean sendCount = true;
	// // System.out.println("准备判断true和false");
	// if (tokens.size() > 1)
	// sendCount = false;
	// // System.out.println("判断完成");
	// // message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}
	// PushNotificationPayload payLoad = PushNotificationPayload
	// .fromJSON("{'aps':{'alert':'" + message + "'}}");
	// System.out.println("推送内容-->" + "{'aps':{'alert':'" + message
	// + "'}}");
	// // System.out.println("实例payLoad对象");
	// payLoad.addAlert(message); // 消息内容
	// // System.out.println("加入消息体");
	// payLoad.addBadge(1); // iphone应用图标上小红圈上的数值
	// // System.out.println("设置Badge");
	// payLoad.addSound("default"); // 铃音 默认
	// // System.out.println("设置铃音");
	// payLoad.addCustomDictionary("flag", flag);
	// // System.out.println("设置参数字典type");
	// PushNotificationManager pushManager = new PushNotificationManager();
	// // System.out.println("实例pushManager对象");
	// // true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
	// // System.out.println(path);
	// pushManager
	// .initializeConnection(new AppleNotificationServerBasicImpl(
	// path, password, isDevelop));// true：表示的是产品发布推送服务
	// // false：表示的是产品测试推送服务
	// List<PushedNotification> notifications = new
	// ArrayList<PushedNotification>();
	// // 发送push消息
	// if (sendCount) {
	// // System.out
	// // .println("--------------------------apple 推送 单-------");
	// Device device = new BasicDevice();
	// device.setToken(tokens.get(0));
	// PushedNotification notification = pushManager.sendNotification(
	// device, payLoad, true);
	// notifications.add(notification);
	// } else {
	// // System.out
	// // .println("--------------------------apple 推送 群-------");
	// List<Device> device = new ArrayList<Device>();
	// for (String token : tokens) {
	// device.add(new BasicDevice(token));
	// }
	// System.out.println("这是List<Device>的size:" + device.size());
	// notifications = pushManager.sendNotifications(payLoad, device);
	// }
	// // 去回执服务器找哪些推送失败了
	// // List<PushedNotification> fail = PushedNotification
	// // .findFailedNotifications(notifications);
	// // 去回执服务器找哪些成功了
	// List<PushedNotification> success = PushedNotification
	// .findSuccessfulNotifications(notifications);
	// // System.out.println("成功 (" + success.size() + "):");
	// return success.size();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return 0;
	// }

	public static String getCerPath(String url) {
		try {
			String fileName = url.substring(url.lastIndexOf("/") + 1,
					url.length());
			String currPath = "C:\\cer\\" + fileName;
			File file = new File("C:\\cer\\");
			if (!file.exists())
				file.mkdir();
			file = new File(currPath);
			// 如果该文件不存在,就去下载下来保存到本地,如果存在,就直接返回文件路径
			if (!file.exists()) {
				InputStream in = new URL(url).openConnection().getInputStream(); // 创建连接、输入流
				FileOutputStream f = new FileOutputStream(currPath);// 创建文件输出流
				byte[] bb = new byte[1024]; // 接收缓存
				int len;
				while ((len = in.read(bb)) > 0) { // 接收
					f.write(bb, 0, len); // 写入文件
				}
				f.close();
				in.close();
			}
			return currPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
