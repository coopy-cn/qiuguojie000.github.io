package com.core.teamwork.base.util.returnback;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

public class ReMessage {
	/**
	 * 用于客户端解析的json对象
	 *
	 * @param paraEunm
	 *            枚举对象 eg:ParameterEunm.SUCCESSFUL_CODE
	 * @param paraData
	 *            返回的具体数据 可对象为空
	 * @return 封装好的返回对象
	 */
	public static Map<String, Object> resultBack(String paraEunm,
			Object paraData) {
		return resultBack(paraEunm, paraData, null, null, null);
	}

	/**
	 * 用于客户端解析的json对象
	 *
	 * @param paraEunm
	 *            枚举对象 eg:ParameterEunm.SUCCESSFUL_CODE
	 * @param paraData
	 *            返回的具体数据 可对象为空
	 * @param errMsg
	 *            填充message中错误的信息 （为空则显示枚举中的描述）
	 * @return 封装好的返回对象
	 */
	public static Map<String, Object> resultBack(String paraEunm,
			Object paraData, String errMsg) {
		return resultBack(paraEunm, paraData, null, null, errMsg);
	}

	// /**
	// * 用于客户端解析的json对象
	// *
	// * @param paraEunm 枚举对象 eg:ParameterEunm.SUCCESSFUL_CODE
	// * @param paraData 返回的具体数据 可对象为空
	// * @return 封装好的返回对象
	// */
	// public static Map<String, Object> resultErrorBack(ParameterEunm paraEunm,
	// String errMsg) {
	// Map<String, Object> re = new HashMap<>();
	// re.put(ParameterEunm.RESULT_DATA_STATUS_KEY.getBackParameter().toString(),
	// paraEunm.getBackParameter());
	// re.put(ParameterEunm.RESULT_DATA_MESSAGE_KEY.getBackParameter().toString(),
	// errMsg);
	// return re;
	// }

	/**
	 * 用于客户端解析的json对象
	 * 
	 * @param paraEunm
	 *            枚举对象 eg:ParameterEunm.SUCCESSFUL_CODE
	 * @param paraData
	 *            返回的具体数据 可对象为空
	 * @param keepFields
	 *            对象中要保留的字段(两个条件只有一个生效)
	 * @param ignoreFields
	 *            对象中忽略的字段(两个条件只有一个生效)
	 * @param errMsg
	 *            填充message中错误的信息 （为空则显示枚举中的描述）
	 * @return
	 */
	public static Map<String, Object> resultBack(String paraEunm,
			Object paraData, String[] keepFields, String[] ignoreFields,
			String errMsg) {
		String[] paraSplist = paraEunm.split("=");
		Map<String, Object> re = new HashMap<String, Object>();
		re.put(BaseParameterEunm.RESULT_DATA_STATUS_KEY, paraSplist[0]);
		if (null != paraData) {
			if (keepFields != null || ignoreFields != null) {
				Map paraMap = null;
				Map newParaData = new HashMap();
				if (paraData instanceof Map) {
					paraMap = (Map) paraData;
				} else {
					paraMap = new HashMap();
					for (Field field : paraData.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						try {
							paraMap.put(field.getName(), field.get(paraData));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (keepFields != null) {
					for (String filed : keepFields) {
						if (filed != null && paraMap.get(filed) != null) {
							newParaData.put(filed, paraMap.get(filed));
						}
					}
				} else if (ignoreFields != null) {
					newParaData.putAll(paraMap);
					for (String filed : ignoreFields) {
						if (filed != null) {
							newParaData.remove(filed);
						}
					}
				}
				re.put(BaseParameterEunm.RESULT_DATA_CONTENT_KEY, paraDataFilter(newParaData));
			} else {
				re.put(BaseParameterEunm.RESULT_DATA_CONTENT_KEY, paraDataFilter(paraData));
			}
		}
		re.put(BaseParameterEunm.RESULT_DATA_MESSAGE_KEY,
				StringUtils.isNotEmpty(errMsg) ? errMsg : paraSplist[1]);
		return re;
	}

	private static Object paraDataFilter(Object paraData) {
		ValueFilter filter = new ValueFilter() {
			public Object process(Object obj, String s, Object v) {
//				if (v == null || v.equals("null")) {
//					return "";
//				} else {
					if (v instanceof Date) {
						return ((Date) v).getTime();
					} else if (v instanceof Double) {
						double tempD = (double) v;
						int tempI = (int) tempD;
						if (tempD == tempI) {
							return tempI;
						} else {
							return tempD;
						}
					} else {
						return v;
					}
//				}
			}
		};
		String str = JSON.toJSONString(paraData, filter);
		Object json = new Object();
		if (!StringUtils.isEmpty(str)) {
			json = JSON.parse(str);
		}
		return json;
	}
}
