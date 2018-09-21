package com.core.teamwork.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcUtil {
	
	/**
	 * 运算符：+<br>
	 * 精确加法运算,保留两位小数，超出向前进一
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Double add(Double num1,Double num2){
		if(num1!=null&&num2!=null){
			BigDecimal d1 = new BigDecimal(num1.toString());
			BigDecimal d2 = new BigDecimal(num2.toString());
			return d1.add(d2).setScale(2,RoundingMode.UP).doubleValue();
		}
		return null;
	}
	
	/**
	 * 运算符：-<br>
	 * 精确减法运算,保留两位小数，超出向前进一
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Double subtract(Double num1,Double num2){
		if(num1!=null&&num2!=null){
			BigDecimal d1 = new BigDecimal(num1.toString());
			BigDecimal d2 = new BigDecimal(num2.toString());
			return d1.subtract(d2).setScale(2,RoundingMode.UP).doubleValue();
		}
		return null;
	}
	
	/**
	 * 运算符：*<br>
	 * 精确乘法运算,保留两位小数，超出向前进一
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Double multiply(Double num1,Double num2){
		if(num1!=null&&num2!=null){
			BigDecimal d1 = new BigDecimal(num1.toString());
			BigDecimal d2 = new BigDecimal(num2.toString());
			return d1.multiply(d2).setScale(2,RoundingMode.UP).doubleValue();
		}
		return null;
	}
	
	/**
	 * 运算符：/<br>
	 * 精确除法运算,保留两位小数，超出向前进一
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Double divide(Double num1,Double num2){
		if(num1!=null&&num2!=null){
			BigDecimal d1 = new BigDecimal(num1.toString());
			BigDecimal d2 = new BigDecimal(num2.toString());
			return d1.divide(d2).setScale(2,RoundingMode.UP).doubleValue();
		}
		return null;
	}
}
