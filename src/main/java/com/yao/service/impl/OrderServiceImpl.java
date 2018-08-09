package com.yao.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.yao.entity.Order;
import com.yao.repository.OrderRepository;
import com.yao.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 订单Service实现类
 * @author Administrator
 *
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrderRepository orderRepository;

	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

	@Override
	public Order getByOrderNo(String orderNo) {
		return orderRepository.getByOrderNo(orderNo);
	}

	@Override
	public Order getById(Integer id) {
		return orderRepository.getOne(id);
	}

	@Override
	public List<Order> list(Order order, Integer page, Integer pageSize) {
		Pageable pageable=new PageRequest(page-1,pageSize,Sort.Direction.DESC,"buyTime");
		Page<Order> pageOrder = orderRepository.findAll(new Specification<Order>() {

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.get("isPay"), 1));
				return predicate;
			}
		}, pageable);
		return pageOrder.getContent();
	}

	@Override
	public Long getCount(Order order) {
		return orderRepository.count(new Specification<Order>() {

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.get("isPay"), 1));
				return predicate;
			}
		});
	}

}
