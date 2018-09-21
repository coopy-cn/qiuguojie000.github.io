package com.orye.manger.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.teamwork.base.util.encrypt.PasswordEncoder;
import com.core.teamwork.base.util.returnback.ReMessage;
import com.orye.business.admin.entity.SysUcenterAdmin;
import com.orye.business.admin.service.SysUcenterAdminService;
import com.orye.manger.controller.InterfaceBaseController;
import com.orye.business.util.ParameterEunm;

@Controller
@RequestMapping("/admin/*")
public class AppLoginController extends InterfaceBaseController{
	@Autowired
    private SysUcenterAdminService adminService;
	/**
     * 
     * @author buyuer
     * @Title: login
     * @Description: 登录方法
     */
    @RequestMapping("/appLogin")
    @ResponseBody
    public Map<String, Object> appLogin(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 此处默认有值
        try {
            String username = map.get("userName").toString();
            String password = map.get("password").toString();
            password = PasswordEncoder.getPassword(password);
            
            SysUcenterAdmin admin = adminService.loginAdmin(username, password);
            if (admin != null) {
            	setSessionAttr("user", admin, 86400);
                resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, getSession().getId());
            }else{
            	resultMap = ReMessage.resultBack(ParameterEunm.FAILED_CODE,null, "请输入正确账号和密码!");
            }
            
        } catch (Exception e) {
        	resultMap = ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
        }
        return resultMap;
    }
}
