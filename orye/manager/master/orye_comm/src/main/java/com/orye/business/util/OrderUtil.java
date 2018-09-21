package com.orye.business.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtil {
	public static Random rad=new Random();
	
	// 精确到毫秒
	public static String getOrderNum() {
			// 订单生成DD+yyyyMMddHHmmssSSS+三组二位随机数（22-25位字符）
		String orderNum = "oy_" + DateStr1(new Date()) + rad.nextInt(100)+""
		+rad.nextInt(100)+""+rad.nextInt(100)+"";
		return orderNum;
	}
	
	public static String getOrder(){
		return MySeq.getPay()+rad.nextInt(100);
	}
	
	// 精确到毫秒
	private static String DateStr1(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sdf.format(date);
		return str;
	}
}
