package com.orye.manger.controller.pay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.pay.entity.PayOrder;
import com.orye.business.pay.service.PayOrderService;

/**
 * @ClassName: CourseInfoController
 * @Description: 支付订单管理
 * @author qiuguojie
 * @date 2018年09月18日 上午10:46:10
 * 
 */
@RequestMapping("/payOrder/*")
@Controller
public class PayOrderController {
	
	@Autowired
	private PayOrderService service;
	
	/**
     * 
     * @author qiuguojie
     * @Title: list
     * @Description: 用户列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam Map<String, Object> map) {
        ModelAndView andView = new ModelAndView("pay/pay-order-list");
        //根据时间降序
        map.put("sortName", "create_time");
        map.put("sortOrder", "desc");
        PageObject<PayOrder> list = service.getPageList(map);
        andView.addObject("list", list);
        andView.addObject("map", map);
        return andView;
    }
}
