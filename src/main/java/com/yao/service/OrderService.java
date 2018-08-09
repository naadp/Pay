package com.yao.service;

import com.yao.entity.Order;

import java.util.List;

/**
 * 订单Service接口
 * @author Administrator
 *
 */
public interface OrderService {
	/**
	 * 添加或者修改订单信息
	 * @param order
	 */
	public void save(Order order);

	/**
	 * 根据订单号查询订单
	 * @param orderNo
	 * @return
	 */
	public Order getByOrderNo(String orderNo);

	/**
	 * 根据id获取订单实体
	 * @param id
	 * @return
	 */
	public Order getById(Integer id);

	/**
	 * 分页查询订单信息
	 * @param order
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Order> list(Order order, Integer page, Integer pageSize);

	/**
	 * 获取总记录数
	 * @param order
	 * @return
	 */
	public Long getCount(Order order);
}
