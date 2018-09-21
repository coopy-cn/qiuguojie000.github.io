package com.orye.manger.controller.pay;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.core.teamwork.base.util.ObjectUtil;
import com.core.teamwork.base.util.ReadProChange;
import com.core.teamwork.base.util.date.DateStyle;
import com.core.teamwork.base.util.date.DateUtil;
import com.core.teamwork.base.util.page.PageObject;
import com.core.teamwork.base.util.pay.alipay.sign.Base64;
import com.core.teamwork.base.util.returnback.ReMessage;
import com.orye.business.course.entity.CourseInfo;
import com.orye.business.course.entity.CourseOrder;
import com.orye.business.course.entity.CourseTableConfig;
import com.orye.business.course.entity.CourseType;
import com.orye.business.course.entity.CourseUser;
import com.orye.business.course.service.CourseInfoService;
import com.orye.business.course.service.CourseOrderService;
import com.orye.business.course.service.CourseTableConfigService;
import com.orye.business.course.service.CourseTypeService;
import com.orye.business.course.service.CourseUserService;
import com.orye.business.task.service.TaskService;
import com.orye.business.task.util.TaskUtil;
import com.orye.business.teach.entity.CityRegion;
import com.orye.business.teach.entity.TeachPlace;
import com.orye.business.teach.entity.TeacherInfo;
import com.orye.business.teach.service.CityRegionService;
import com.orye.business.teach.service.HomePersonService;
import com.orye.business.teach.service.TeachPlaceService;
import com.orye.business.teach.service.TeacherInfoService;
import com.orye.business.util.ParameterEunm;
import com.orye.business.util.httpsUtil.HttpsUtil;

@Controller
@RequestMapping("/apiAll/*")
public class CourseController {
	//Logger logger = Logger.getLogger(this.getClass());
	private static final Log logger = LogFactory.getLog(CourseController.class);
	
	@Autowired
	private CityRegionService cityRegionService;
	@Autowired
	private TeachPlaceService teachPlaceService;
	@Autowired
	private CourseTypeService courseTypeService;
	@Autowired
	private CourseInfoService courseInfoService;
	@Autowired
	private CourseTableConfigService courseTableConfigService;
	@Autowired
	private TeacherInfoService teacherInfoService;
	@Autowired
	private CourseUserService userService;
	@Autowired
	private HomePersonService homePersonService;
	@Autowired
	private CourseOrderService courseOrderService;
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
    private TaskService taskService;
	
	@ResponseBody
	@RequestMapping(value = "/test")
	public Map<String,Object> test(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean con = ObjectUtil.checkObject(new String[] { "mch_id"}, map);
		
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/wxPro")
	public Map<String,Object> wxPro(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", "200");
		resultMap.put("msg", "成功");
		return resultMap;
	}
	
	public void test(){
		String key ="ffd78897e73a0319be4bb32bbf2d1074";
	}
	
	/**
	 * 市、区、站点下拉框数据接口
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/placeList")
	public Map<String,Object> placeList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String,Object> param = new HashMap<>();
			if(ObjectUtil.checkObject(new String[] { "cityId"}, map)){
				param.put("parentId", map.get("cityId"));
			}
			
			List<CityRegion> regList = cityRegionService.queryByParent(param);
			param.put("parentId", "0");
			List<CityRegion> cityList = cityRegionService.query(param);
			
			param.clear();
			if(ObjectUtil.checkObject(new String[] { "cityId"}, map)){
				param.put("parentId", map.get("cityId"));
			}
			if(ObjectUtil.checkObject(new String[] { "regId"}, map)){
				param.put("regId", map.get("regId"));
			}
			List<TeachPlace> placeList = teachPlaceService.getList(param);
			for (TeachPlace teachPlace : placeList) {
				teachPlace.setName(teachPlace.getCourseAddr());
			}
			
			List list = new ArrayList<>();
			ListInit(cityList);
			ListInit(regList);
			ListInit1(placeList);
			list.add(cityList);
			list.add(regList);
			list.add(placeList);
			
			resultMap.put("list", list);
			
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		
		return resultMap;
	}
	
	/**
	 * 课程下拉框数据接口
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/courseList")
	public Map<String,Object> courseList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String,Object> param = new HashMap<>();
			
			List<CourseType> typeList = courseTypeService.query(param);
			param.put("typeId", map.get("typeId"));
			List<CourseInfo> courseList = courseInfoService.query(param);
			for (CourseInfo courseInfo : courseList) {
				courseInfo.setName(courseInfo.getCourseName());
			}
			
			List list = new ArrayList<>();
			ListInit2(typeList);
			ListInit3(courseList);
			list.add(typeList);
			list.add(courseList);
			
			resultMap.put("list", list);
			
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 课程列表数据
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/courseTable")
	public Map<String,Object> courseTable(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			map.put("isDelete", 2);
			map.put("status", 1);
			PageObject<CourseTableConfig> list = courseTableConfigService.pageList(map);
			
			resultMap.put("list", list);
			
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 课程预约详情
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/courseDetail")
	public Map<String,Object> courseDetail(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			CourseTableConfig courseTableConfig = courseTableConfigService.detail(map);
			courseTableConfig.setTeachTime(DateUtil.DateToString(courseTableConfig.getTeachDate(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			
			Map<String,Object> param = new HashMap<>();
			param.put("id", courseTableConfig.getTeachId());
			TeacherInfo teacherInfo = teacherInfoService.detail(param);
			
			param.put("id", courseTableConfig.getCourseId());
			CourseInfo courseInfo = courseInfoService.detail(param);
			
			param.put("id", courseTableConfig.getPlaceId());
			TeachPlace teachPlace = teachPlaceService.queryDetail(param);
			
			resultMap.put("courseTableConfig", courseTableConfig);
			resultMap.put("teacherInfo", teacherInfo);
			resultMap.put("courseInfo", courseInfo);
			resultMap.put("teachPlace", teachPlace);
			
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 登陆接口
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	public Map<String,Object> login(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*String secret = "05095ff8c9b00758d879cd6d8ec72099";
		String appId = "wxf9535006f414ba26";*/
		
		//Or野自然（密钥、appId）
		String secret = "6860de089372308040d55a1b0bda7abf";
		String appId = "wx0b5d11d8855824e6";
		boolean con = ObjectUtil.checkObject(new String[] { "code","encryptedData","iv" }, map);
		if(con){
			try {
				String code = map.get("code").toString();
				String url ="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret
						+"&js_code="+code+"&grant_type=authorization_code";
				//获取登陆凭证
				String result = HttpsUtil.doGetString(url);
				if(StringUtils.isBlank(result)){
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
					return resultMap;
				}
				JSONObject obj = JSONObject.parseObject(result);
				if(obj==null){
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
					return resultMap;
				}
				//解密敏感数据
				String encryptedData = map.get("encryptedData").toString();
				String iv = map.get("iv").toString();
				if(!obj.containsKey("session_key")){
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
					return resultMap;
				}
				JSONObject userInfo = getUserInfo(encryptedData, obj.getString("session_key"), iv);
				if(userInfo==null){
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
					return resultMap;
				}
				Map<String,Object> param = new HashMap<>();
				param.put("openId", obj.getString("openid"));
				if(obj.containsKey("openid")){
					CourseUser user = userService.detail(param);
					if(user==null){
						user = new CourseUser();
						user.setOpenId(obj.getString("openid"));
						user.setCountry(userInfo.getString("country"));
						user.setAvatarUrl(userInfo.getString("avatarUrl"));
						user.setCity(userInfo.getString("city"));
						user.setProvince(userInfo.getString("province"));
						user.setNickName(userInfo.getString("nickName"));
						user.setSex(userInfo.getInteger("gender"));
						user.setCreateTime(new Date());
						userService.add(user);
					}else{
						user.setCountry(userInfo.getString("country"));
						user.setAvatarUrl(userInfo.getString("avatarUrl"));
						user.setCity(userInfo.getString("city"));
						user.setProvince(userInfo.getString("province"));
						user.setNickName(userInfo.getString("nickName"));
						user.setSex(userInfo.getInteger("gender"));
						userService.update(user);
					}
					
					resultMap.put("sk", obj.getString("openid"));
					
					userInfo.remove("openId");
					
					resultMap.put("userInfo", userInfo);
					
					resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
				}else{
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
			}
		}else{
			resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
		}
		
		return resultMap;
	}
	
	/**
	 * 课程预约提交数据和支付
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/submitOrder")
	public Map<String,Object> submitOrder(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			boolean con = ObjectUtil.checkObject(new String[] { "conId","sk" }, map);
			if(con){
				Map<String,Object> param = new HashMap<>();
				CourseInfo courseInfo = courseInfoService.queryDetail(map);
				if(courseInfo!=null){
					int month = getMonthDiff(new Date(), DateUtil.StringToDate(map.get("birthDate").toString()));
					int minMonth = courseInfo.getMinYear()*12+courseInfo.getMinMonth();
					int maxMonth = courseInfo.getMaxYear()*12+courseInfo.getMaxMonth();
					if(month<minMonth||month>maxMonth){
						resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null
								,"年龄不在"+courseInfo.getMinYear()+"岁"+courseInfo.getMinMonth()
								+"个月至"+courseInfo.getMaxYear()+"岁"+courseInfo.getMaxMonth()+"个月之间");
						return resultMap;
					}
					param.put("status", 2);
					param.put("conId", map.get("conId"));
					List<CourseOrder> list = courseOrderService.query(param);
					if(list!=null&&list.size()>=courseInfo.getMaxCount()){
						resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"预约人数已满");
						return resultMap;
					}
					
				}else{
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"数据错误");
					return resultMap;
				}
				
				//进入线程池-保存填写的信息
				taskExecutor.execute(new TaskUtil(taskService, map));
				
				Map<String,String> result = courseOrderService.submitOrder(map,courseInfo);
				if("10000".equals(result.get("code"))){
					resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, result);
				}else{
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"此卡次数不够或已过期");
				}
			}else{
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 课程预约-立即预约-填充数据接口
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/homeInfo")
	public Map<String,Object> homeInfo(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			boolean con = ObjectUtil.checkObject(new String[] { "sk" }, map);
			if(con){
				map.put("openId", map.get("sk"));
				CourseOrder courseOrder = courseOrderService.queryLast(map);
				if(courseOrder!=null){
					courseOrder.setStrBirthDate(DateUtil.DateToString(courseOrder.getBirthDate(), DateStyle.YYYY_MM_DD));
					resultMap.put("courseOrder", courseOrder);
					resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
				}else{
					resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null);
				}
			}else{
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 我的预约列表
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/myPageList")
	public Map<String,Object> myPageList(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			boolean con = ObjectUtil.checkObject(new String[] { "sk" }, map);
			if(con){
				map.put("openId", map.get("sk"));
				PageObject<CourseOrder> list = courseOrderService.pageList(map);
				resultMap.put("list", list);
				resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
			}else{
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 我的预约数量
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/noTogoCount")
	public Map<String,Object> noTogoCount(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			boolean con = ObjectUtil.checkObject(new String[] { "sk" }, map);
			if(con){
				map.put("openId", map.get("sk"));
				Integer count = courseOrderService.noTogoCount(map);
				resultMap.put("count", count);
				resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
			}else{
				resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE, null,"缺少参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 首页
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public Map<String,Object> index(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			map.put("curPage", 1);
			map.put("pageData", 5);
			map.put("sortName", "sort_num");
			PageObject<CourseInfo> courseList = courseInfoService.Pagequery(map);
			PageObject<TeachPlace> placeList = teachPlaceService.getPageList(map);
			PageObject<TeacherInfo> teacherList = teacherInfoService.Pagequery(map);
			
			resultMap.put("courseList", courseList);
			resultMap.put("placeList", placeList);
			resultMap.put("teacherList", teacherList);
			resultMap.put("url", ReadProChange.getValue("http_ip"));
			
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	/**
	 * 教师列表
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/teacher")
	public Map<String,Object> teacher(HttpServletRequest request,HttpServletResponse response
			,@RequestParam Map<String, Object> map) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			map.put("curPage", 1);
			map.put("sortName", "sort_num");
			PageObject<TeacherInfo> teacherList = teacherInfoService.Pagequery(map);
			resultMap.put("teacherList", teacherList);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
		}
		return resultMap;
	}
	
	public void ListInit(List<CityRegion> list){
		CityRegion cr = new CityRegion();
		cr.setName("全部");
		list.add(0, cr);
	}
	
	public void ListInit1(List<TeachPlace> list){
		TeachPlace tp = new TeachPlace();
		tp.setName("全深圳");
		list.add(0, tp);
	}
	
	public void ListInit2(List<CourseType> list){
		CourseType ct = new CourseType();
		ct.setName("全部");
		list.add(0, ct);
	}
	
	public void ListInit3(List<CourseInfo> list){
		CourseInfo ci = new CourseInfo();
		ci.setName("全部课程");
		list.add(0, ci);
	}
	
	public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
 
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	* 获取两个日期相差的月数
	* @param d1  较大的日期
	* @param d2  较小的日期
	* @return 如果d1>d2返回 月数差 否则返回0
	*/
	public static int getMonthDiff(Date d1, Date d2) {
	    Calendar c1 = Calendar.getInstance();
	    Calendar c2 = Calendar.getInstance();
	    c1.setTime(d1);
	    c2.setTime(d2);
	    if(c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
	    int year1 = c1.get(Calendar.YEAR);
	    int year2 = c2.get(Calendar.YEAR);
	    int month1 = c1.get(Calendar.MONTH);
	    int month2 = c2.get(Calendar.MONTH);
	    int day1 = c1.get(Calendar.DAY_OF_MONTH);
	    int day2 = c2.get(Calendar.DAY_OF_MONTH);
	    // 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
	    int yearInterval = year1 - year2;
	    // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
	    if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
	    // 获取月数差值
	    int monthInterval = (month1 + 12) - month2 ;
	    if(day1 < day2) monthInterval --;
	    monthInterval %= 12;
	    return yearInterval * 12 + monthInterval;
	}
	
	public static void main(String[] args) {
//		System.out.println(MD5.GetMD5Code("orye1511008541sophia-wanwei"));
//		
//		String app_id="wx0b5d11d8855824e6";
//		String mch_id="1511008541";
//		String key="7d1a8fdeacc4ef7272a16840819323bb";
		
		LinkedList list = new LinkedList<>();
		
		
	}
}
