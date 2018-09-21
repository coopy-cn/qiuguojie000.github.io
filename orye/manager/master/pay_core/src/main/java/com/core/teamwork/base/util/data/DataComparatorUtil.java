package com.core.teamwork.base.util.data;

import java.util.Comparator;
import java.util.Map;

public class DataComparatorUtil  implements Comparator<Map<String,Object>>{

	private String filed;
	private int orderType;

	public DataComparatorUtil(String filed, int orderType) {
		this.filed = filed;
		this.orderType = orderType;
	}

	/**
	 * @return the filed
	 */
	public String getFiled() {
		return filed;
	}

	/**
	 * @param filed the filed to set
	 */
	public void setFiled(String filed) {
		this.filed = filed;
	}
	
	/**
	 * @return the orderType
	 */
	public int getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		double num1= Double.valueOf(o1.get(filed).toString());
		double num2= Double.valueOf(o2.get(filed).toString());
		if(orderType==1?num1>num2:num1<num2){
			return -1;
		}else if(num1==num2){
			return 0;
		}else{
			return 1;
		}
	}
	
}
