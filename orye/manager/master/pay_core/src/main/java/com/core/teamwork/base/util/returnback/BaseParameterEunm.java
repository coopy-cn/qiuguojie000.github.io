package com.core.teamwork.base.util.returnback;

import org.apache.commons.lang3.StringUtils;

public class BaseParameterEunm {
    /**
     * 返回请求标识ErrorCode
     */
//    RESULT_DATA_STATUS_KEY("返回标识", "resultCode"),
	public static final String RESULT_DATA_STATUS_KEY = "resultCode";
    /**
     * 返回的内容标识Data
     */
//    RESULT_DATA_CONTENT_KEY("内容标识", "Data"),
	public static final String RESULT_DATA_CONTENT_KEY = "Data";
    /**
     * 返回的错误描述ErrorMessage
     */
//    RESULT_DATA_MESSAGE_KEY("返回的错误描述", "message"),
	public static final String RESULT_DATA_MESSAGE_KEY = "message";
    /**
     * 返回的用户sessionId:userCertificate
     */
//    SESSION_ID_PARA_NAME("用户sessionId标识", "userCertificate"),
	public static final String SESSION_ID_PARA_NAME = "userCertificate";
    /**
     * 成功编号
     */
//    SUCCESSFUL_CODE("成功", 200),
	public static final String SUCCESSFUL_CODE = "200=成功";
    /**
     * 失败
     */
//    FAILED_CODE("失败", 201),
	public static final String FAILED_CODE = "201=失败";
    
//    /**
//     * 帐号被锁定
//     */
//    ERROR_LOCK("帐号被锁定", 262),
	public static final String ERROR_LOCK = "262=帐号被锁定";
//    
//    /**
//     * 用户未登录
//     */
//    NOT_LOGIN("用户未登录", 301),
	public static final String NOT_LOGIN = "301=用户未登录";
//
//    /**
//     * 用户无权限
//     */
//    ERROR_NO_POWER("无权限操作", 400),
	public static final String ERROR_NO_POWER = "400=无权限操作";
//
//    /**
//     * 格式不正确,请重试
//     */
//    ERROR_PARAMS_CODE("格式不正确,请重试", 401),
	public static final String ERROR_PARAMS_CODE = "401=格式不正确,请重试";
//    /**
//     * 403错误,请求资源不可用
//     */
//    ERROR_403_CODE("请求异常", 403),
	public static final String ERROR_403_CODE = "403=请求异常";
//    
//    /**
//     * 404错误,链接无效
//     */
//    ERROR_404_CODE("请求异常", 404),
	public static final String ERROR_404_CODE = "404=请求异常";
//    /**
//     * 内部错误500
//     */
//    ERROR_500_CODE("服务器异常,请稍后再试", 500),
	public static final String ERROR_500_CODE = "500=服务器异常,请稍后再试";
	
//    /**
//     * 短信验证码不正确
//     */
//    ERROR_SMS_CODE("验证码不正确",1001),
	public static final String ERROR_SMS_CODE = "1001=验证码不正确";
//    
//    /**
//     * 帐号被冻结
//     */
//    ERROR_USER_LOCK("帐号被冻结",1002),
	public static final String ERROR_USER_LOCK = "1002=帐号被冻结";
//    
//    /**
//     * "帐号被删除
//     */
//    ERROR_USER_DELETE("帐号被删除",1003),
	public static final String ERROR_USER_DELETE = "1003=帐号被删除";
//    
//    /**
//     * SDK验证不通过
//     */
//    ERROR_SDK_CODE("SDK验证不通过",2001),
	public static final String ERROR_SDK_CODE = "2001=SDK验证不通过";
	
	
	
	public static String getKey(String constant){
		if(StringUtils.isNotBlank(constant)){
			String[] splitArray = constant.split("=");
			if(splitArray.length >= 2){
				return splitArray[0];
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static String getValue(String constant){
		if(StringUtils.isNotBlank(constant)){
			String[] splitArray = constant.split("=");
			if(splitArray.length >= 2){
				return splitArray[1];
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
//    
//    /**
//     * 短信验证码不正确
//     */
//    ERROR_SMS_CODE("验证码不正确",1001),
//    
//    /**
//     * 帐号被冻结
//     */
//    ERROR_USER_LOCK("帐号被冻结",1002),
//    
//    /**
//     * 帐号被删除
//     */
//    ERROR_USER_DELETE("帐号被删除",1003),
//    
//    /**
//     * 帐号已注册
//     */
//    ERROR_USER_IS_REG("手机号已注册",1004),
//    
//    
//    /**
//     * 密码不正确
//     */
//    ERROR_USER_PWD_WRONG("密码错误",1005),
//    
//    /**
//     * 新旧密码相同
//     */
//    ERROR_USER_PWD_NOT_CHANGE("新旧密码相同",1006),
//    
//    /**
//     * 手机号未注册
//     */
//    ERROR_USER_NOT_REG("手机号未注册",1007),
//    
//    /**
//     * 图片验证码不正确
//     */
//    ERROR_IMG_CODE("验证码不正确",1008),
//    
//    /**
//     * 未绑定手机号
//     */
//    ERROR_USER_NOT_BIND("未绑定手机号",1009),
//    
//    /**
//     * 注册次数过多
//     */
//    ERROR_REG_OVER_3("注册次数超过三次",1010),
//    
//    /**
//     * 只能修改一次登录名
//     */
//    ERROR_EDIT_USER_CODE_OVER_1("只能修改一次登录名",1011),
//    
//    /**
//     * 帐号已经存在
//     */
//    ERROR_USER_CODE_REPEAT("帐号已经存在",1012),
//    
//    /**
//     * 帐号没有发生变化
//     */
//    ERROR_USER_CODE_NO_CHANGE("帐号没有发生变化",1013),
//    
//    /**
//     * 帐号格式不正确
//     */
//    ERROR_USER_CODE_FORMAT("帐号格式不正确",1014),
//    
//    /**
//     * 已绑定手机号
//     */
//    ERROR_ALREADY_BIND_PHONE("已绑定手机号",1015),
//    
//    /**
//     * SDK验证不通过
//     */
//    ERROR_SDK_CODE("SDK验证不通过",2001),
//    
//    /**
//     * APP验证不通过
//     */
//    ERROR_APP_CODE("appCode错误",3001),
//    
//    /**
//     * 1分钟内不能重复发送短信
//     */
//    ERROR_SMS_REPEAT_1M("1分钟内不能重复发送短信",4001),
//    
//    /**
//     * 10分钟内不能重复发送短信
//     */
//    ERROR_SMS_REPEAT_10M("10分钟内不能重复发送短信",4002),
//    
//    /**
//     * 签名文件已上传过
//     */
//    ERROR_UPLOAD_SAME_SIGN("签名文件已上传过", 4003),
//    
//    ;

//    private Object parameterName;
//
//    private Object backParameter;
//
//    private ParameterEunm(Object parameterName, Object backParameter) {
//        this.parameterName = parameterName;
//        this.backParameter = backParameter;
//    }
//
//    public Object getParameterName() {
//        return parameterName;
//    }
//
//    public void setParameterName(Object parameterName) {
//        this.parameterName = parameterName;
//    }
//
//    public Object getBackParameter() {
//        return backParameter;
//    }
//
//    public void setBackParameter(Object backParameter) {
//        this.backParameter = backParameter;
//    }

}
