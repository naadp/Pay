package com.yao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;



/**
 * 订单表
 * @author Administrator
 *
 */
@Entity
@Table(name="t_order")
public class Order {

	@Id
	@GeneratedValue
	private Integer id; // 编号
	
	@Column(length=200)
	private String orderNo; // 订单号
	
	private Integer productId; // 商品ID
	
	@Column(length=200)
	private String subject; // 订单名称
	
	@Column(length=800)
	private String body; // 商品描述
	
	@Column(length=50)
	private String totalAmount; // 总金额
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date buyTime; // 购买日期时间
	
	@Column(length=50)
	private String nickName; // 支付人
	
	@Column(length=20)
	private String qq; // 购买人QQ
	
	@Column(length=1000)
	private String message; // 购买人留言
	
	@Column(length=10)
	private String way; // 支付方式
	
	private int isPay; // 是否支付 0 未支付 1 支付

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", orderNo='" + orderNo + '\'' +
				", productId=" + productId +
				", subject='" + subject + '\'' +
				", body='" + body + '\'' +
				", totalAmount='" + totalAmount + '\'' +
				", buyTime=" + buyTime +
				", nickName='" + nickName + '\'' +
				", qq='" + qq + '\'' +
				", message='" + message + '\'' +
				", way='" + way + '\'' +
				", isPay=" + isPay +
				'}';
	}
}
