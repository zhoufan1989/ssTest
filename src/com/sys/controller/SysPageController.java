package com.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * @author Zhoufan
 * @date 2018年07月28日 下午19:05:27
 */
@Controller
public class SysPageController {
	@RequestMapping("sys/{url}.html")
	public String page(@PathVariable("url") String url) {
		String key = url;
		return "sys/" + key + ".html";
	}

	
	
	@RequestMapping("smart/{url}.html")
	public String getProduct(@PathVariable("url") String url){
		return "smart/" + url + ".html";
	}

}
