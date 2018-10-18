package com.cn.jay.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/index")
    public String listInput(ModelMap model) {
        return "index";
    }
}
