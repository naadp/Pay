package com.yao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yao.entity.Order;
import com.yao.service.OrderService;

/**
 * 订单Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	private OrderService orderService;
	
	/**
	 * 跳转订单列表页面
	 * @return
	 */
	@RequestMapping("/toOrderListPage")
	public ModelAndView toOrderListPage(){
		ModelAndView mav=new ModelAndView();
		mav.addObject("title", "大佬儿们打赏列表");
		mav.setViewName("orderList");
		return mav;
	}
	
	/**
	 * 分页查询订单信息
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderList")
	public Map<String,Object> orderList(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="limit",required=false)Integer limit){
		Map<String,Object> resultMap=new HashMap<>();
		Order s_order=new Order();
		List<Order> orderList = orderService.list(s_order, page, limit);
		Long count = orderService.getCount(s_order);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", orderList);
		return resultMap;
	}
}
