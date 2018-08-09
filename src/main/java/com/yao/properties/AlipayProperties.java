package com.yao.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付配置文件
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix="alipayconfig")
public class AlipayProperties {

	private String appid; // 商户appid
	
	private String rsa_private_key; // 私钥 pkcs8格式的
	
	private String notify_url; // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	
	private String return_url; // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	
	private String url; // 请求网关地址
	
	private String charset; // 编码
	
	private String format; // 返回格式
	
	private String alipay_public_key; // 支付宝公钥
	
	private String signtype;  // RSA2

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getRsa_private_key() {
		return rsa_private_key;
	}

	public void setRsa_private_key(String rsa_private_key) {
		this.rsa_private_key = rsa_private_key;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAlipay_public_key() {
		return alipay_public_key;
	}

	public void setAlipay_public_key(String alipay_public_key) {
		this.alipay_public_key = alipay_public_key;
	}

	public String getSigntype() {
		return signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	@Override
	public String toString() {
		return "AlipayProperties [appid=" + appid + ", rsa_private_key=" + rsa_private_key + ", notify_url="
				+ notify_url + ", return_url=" + return_url + ", url=" + url + ", charset=" + charset + ", format="
				+ format + ", alipay_public_key=" + alipay_public_key + ", signtype=" + signtype + "]";
	}
	
	
	
	
}
