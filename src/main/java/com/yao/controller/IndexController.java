package com.yao.controller;

import com.yao.properties.AlipayProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 首页请求
 * @author Administrator
 *
 */
@Controller
public class IndexController {

	@Resource
	private AlipayProperties alipayProperties;


	/**
	 * 网页根目录请求
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView root(){
		System.out.println("文件的配置是： " + alipayProperties);
		ModelAndView mav=new ModelAndView();
		mav.addObject("title", "在线支付系统");
		mav.setViewName("index");
		return mav;
	}
}
