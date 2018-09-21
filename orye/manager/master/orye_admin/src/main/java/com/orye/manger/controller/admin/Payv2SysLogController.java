package com.orye.manger.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.admin.entity.SysUcenterAdmin;
import com.orye.business.admin.service.SysUcenterAdminService;
import com.orye.business.sysconfig.entity.SysLog;
import com.orye.business.sysconfig.service.SysLogService;
import com.orye.business.util.LogTypeEunm;

/**
 * @ClassName: Payv2PayOrderController
 * @Description:订单管理
 * @author zhoulibo
 * @date 2016年12月7日 下午3:10:42
 */
@Controller
@RequestMapping("/payv2SysLog/*")
public class Payv2SysLogController {

	private static final Logger logger = Logger.getLogger(Payv2SysLogController.class);
	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private SysUcenterAdminService sysUcenterAdminService;
	
	/**
	 * @Title: getPayv2PayOrderList
	 * @Description:获取订单管理列表
	 * @param map
	 * @return 设定文件
	 * @return ModelAndView 返回类型
	 * @date 2016年12月7日 下午3:12:21
	 * @throws
	 */
	@RequestMapping("getSysLogList")
	public ModelAndView getPayv2PayOrderList(@RequestParam Map<String, Object> map) {
		ModelAndView av = new ModelAndView("payv2/syslog/payv2SysLog-list");
		if(!map.containsKey("sysType")){
			map.put("sysType", 1);
		}		
		PageObject<SysLog> pageObject = sysLogService.getSysLogPageList(map);
		Map<String, Object> aMap = new HashMap<String, Object>();
		List<SysUcenterAdmin> admins = sysUcenterAdminService.query(aMap);
		
		av.addObject("map", map);
		av.addObject("adminList", admins);
		av.addObject("executeTypeList", LogTypeEunm.getAllType());
		av.addObject("list", pageObject);
		return av;
	}
}
