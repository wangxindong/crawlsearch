package com.wxd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jianong.controller.base.RestBaseController;
import com.jianong.service.system.user.UserManager;
import com.jianong.util.PageData;

/**
 * 登录接口
 * 
 * @author wxd
 *
 */
@RestController
@RequestMapping(value = "/api/v1")
@SuppressWarnings("all")
public class LoginCroller extends RestBaseController {

	@Resource(name = "userService")
	private UserManager userService;

	@RequestMapping(value = "/login")
	public Object login(@PathVariable String id) throws Exception {
		PageData pd = getPageData();
		pd.put("USER_ID", id);
		Map<String, Object> dataMap = new HashMap<>();
		pd = userService.findById(pd);
		System.out.println(pd);
		dataMap.put("pd", pd);
		return dataMap;
	}

}
