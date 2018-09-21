package com.orye.business.util;

import com.core.teamwork.base.util.returnback.BaseParameterEunm;

public class ParameterEunm extends BaseParameterEunm{
	
	public static final String PAY_FAILED_CODE = "2011=支付失败";
	public static final String NOTPAY_FAILED_CODE = "2012=订单未支付";
	public static final String PARA_FAILED_CODE = "2013=参数不能为空或者有误";
	public static final String PAY_FAILED_SUCCESS = "2014=支付已完成";
	public static final String PAY_FAILED_KEY_FAIL = "2015=渠道key与appKey不匹配";
	public static final String PAY_FAILED_PACKAGE_FAIL = "2016=包名错误或不匹配";
	public static final String PAY_REPEAT_FAIL = "2017=订单号重复提交，请重新发起支付";
	public static final String PAY_TYPE_ONE_QUOTA = "2018=超过单笔限额，请降低支付金额";
	
	public static final String SIGNATURE_ERROR = "100=商户签名错误";
	
	public static final String PARAM_ERROR_MONEY = "101=缺少参数";
	
	public static final String MOENY_ERROR = "1020=支付金额错误";
	
	public static final String APP_KEY_INVALID = "501=商户号无效";
	
	public static final String TRANSACTION_NOT_EXIST = "502=交易不存在";
	
	public static final String ORDER_ERROR = "1011=订单错误,请联系技术支持";
	
	
	
	public static final String REFUND_NOT_EXIST = "1012=退款单号不存在";
	
	public static final String RATE_ORDER_ERROR = "1013=已超过限额,请检查支付通道单笔额度和每日额度";
	
	public static final String RATE_TYPE_ERROR = "1018=未配置支付通道或支付类型不支持";
	
	public static final String RATE_FAIL = "1019=支付通道下单出错";
	
	public static final String PAY_ORDER_INVAlLID = "1021=订单已失效";
	
	public static final String CLEAR_ORDER_ERROR = "1014=账单不存在";
	
	public static final String SHOP_KEY_INVALID = "503=shopKey或者手机类型无效";
	
	public static final String SHOP_NOT_EXIST = "504=店铺信息不存在";
	
	public static final String PAY_WAY_NOT_EXIST = "505=钱包信息不存在";
	
	public static final String PAY_WAY_SDK_VERSION_NOT_EXIST = "506=钱包版本信息不存在";
	
	public static final String PAY_WAY_DISCOUNT_NOT_EXIST = "507=钱包优惠信息不存在";
	
	public static final String SHOP_KEY_MONEY_INVALID = "508=shopKey或者订单金额参数无效";
	
	public static final String SHOP_ERROR = "1013=店铺信息查询错误,请联系技术支持";
	
	public static final String SHOP_DISCOUNT_ERROR = "1014=店铺优惠查询错误,请联系技术支持";
	
	public static final String SHOP_KEY_MONEY_DISCOUNT_PAYTYPE_INVALID = "509=shopKey或者订单金额或者折扣或者支付方式参数无效";
	
	public static final String SHOP_BUILD_ORDER_ERROR = "1015=根据shopKey、订单金额、优惠金额、支付方式创建订单错误,请联系技术支持";
	
	public static final String COMPANY_NOT_EXIST = "5010=商户信息不存在";
	
	public static final String USER_EXIST = "1004=用户已经存在";
	
	public static final String USER_NO_EXIST = "1005=用户不存在";
	
	public static final String USER_AND_PASSWORD_MISMATCH = "1006=帐号和密码不匹配";
	
	public static final String PAY_ALI_CODE= "10001=调用支付宝发生错误,可能因为key错误";
	
	
	public static final String COMPANY_KEY_INVALID = "601=商户号无效";
	
	public static final String PARAM_STR_ERROR = "1022=paramStr参数错误";
	
	public static final String PARAM_ORDER_LACK = "1023=未传平台订单号或商户订单号";
	
	public static final String QUERY_FAIL = "1024=查询出错";
	
	public static final String MOENY_INSUFFICIENT = "1025=余额错误";
	
	public static final String PAYORDER_ERROR = "1026=下单通道错误,请联系技术支持";
	
	public static final String PAYORDER_REPEAT = "1027=订单重复，请重新下单";
	
	public static final String BALANCE_ERROR = "602=余额不足";
	
	public static final String COMPANY_CODE_ERROR = "603=收款码无效";
	
	public static final String COMPANY_REMARK_ERROR = "604=商户设置备注信息必填";
	
	public static final String REFUND_ERROR = "700=不支持接口退款，请登录商户后台退款";
}
