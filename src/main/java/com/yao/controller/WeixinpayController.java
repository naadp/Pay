package com.yao.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.yao.entity.Order;
import com.yao.properties.WeixinpayProperties;
import com.yao.service.OrderService;
import com.yao.util.DateUtil;
import com.yao.util.DeviceUtil;
import com.yao.util.HttpClientUtil;
import com.yao.util.MD5Util;
import com.yao.util.StringUtil;
import com.yao.util.XmlUtil;

/**
 * 微信支付Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/weixinpay")
public class WeixinpayController {

	@Resource
	private WeixinpayProperties weixinpayProperties;
	
	@Resource
	private OrderService orderService;
	
	private static Logger logger=Logger.getLogger(WeixinpayController.class);
	
	/**
	 * 微信请求
	 * @param order
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/pay")
	public ModelAndView pay(Order order,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		
		String totalAmount=""; // 支付总金额
		String subject=""; // 订单名称
		String body=""; // 商品描述
		switch(order.getProductId()){
			case 1:
				totalAmount="5160";
				subject="给naadp买蝴蝶刀";
				body="5160元-给naadp买蝴蝶刀";
				break;
			case 2:
				totalAmount="22800";
				subject="给naadp买M4-A4咆哮";
				body="22800元-给naadp该咆哮";
				break;
			case 3:
				totalAmount="8550";
				subject="给naadp买巨龙传说";
				body="8550元-给naadp买巨龙传说";
				break; 
			default:
				totalAmount="8550";
				subject="给naadp买巨龙传说";
				body="8550元-给naadp买巨龙传说";
				break;
		}
	
		order.setSubject(subject);
		order.setTotalAmount(totalAmount);
		order.setBody(body);
		
		orderService.save(order);
		
		String userAgent=request.getHeader("user-agent");
		
		if(DeviceUtil.isMobileDevice(userAgent)){ // 移动设备
			String orderNo=DateUtil.getCurrentDateStr(); // 生成订单号
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("appid", weixinpayProperties.getAppid());
			map.put("mch_id", weixinpayProperties.getMch_id());
			map.put("device_info", weixinpayProperties.getDevice_info());
			map.put("notify_url", weixinpayProperties.getNotify_url());
			map.put("trade_type", "MWEB"); // 交易类型
			map.put("out_trade_no", orderNo);
			map.put("body", order.getBody());
			map.put("total_fee", Integer.parseInt(order.getTotalAmount())*100);
			map.put("nonce_str", StringUtil.getRandomString(30)); // 随机串
			map.put("spbill_create_ip", getRemortIP(request)); // 终端IP
			map.put("sign", getSign(map)); // 签名
			String xml=XmlUtil.genXml(map);
			System.out.println(xml);
			InputStream in = HttpClientUtil.sendXMLDataByPost(weixinpayProperties.getUrl().toString(),xml).getEntity().getContent(); // 发送xml消息
			String mweb_url=getElementValue(in,"mweb_url");
			logger.info("mweb_url:"+mweb_url);
			// 拼接跳转地址 
			mweb_url+="&redirect_url="+URLEncoder.encode(weixinpayProperties.getReturn_url(),"GBK");
			logger.info("编码：mweb_url:"+mweb_url);
			order.setOrderNo(orderNo); // 设置订单号
     		orderService.save(order); // 保存订单信息
     		response.sendRedirect(mweb_url);
     		return null;
		}else{
			ModelAndView mav=new ModelAndView();
			mav.addObject("order",order);
			mav.addObject("title", "可怜可怜我吧");
			mav.setViewName("weixinpay");
			return mav;
		}
		
		
	}
	
	/**
	 * 微信支付服务器异步通知
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/notifyUrl")
	public void notifyUrl(HttpServletRequest request)throws Exception{
		logger.info("notifyUrl");
		//读取参数    
        InputStream inputStream ;    
        StringBuffer sb = new StringBuffer();    
        inputStream = request.getInputStream();    
        String s ;    
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));    
        while ((s = in.readLine()) != null){    
            sb.append(s);    
        }    
        in.close();    
        inputStream.close();    
    
        //解析xml成map    
        Map<String, String> m = new HashMap<String, String>();    
        m = XmlUtil.doXMLParse(sb.toString());    
            
        //过滤空 设置 TreeMap    
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();          
        Iterator<String> it = m.keySet().iterator();    
        while (it.hasNext()) {    
            String parameter = it.next();    
            String parameterValue = m.get(parameter);    
                
            String v = "";    
            if(null != parameterValue) {    
                v = parameterValue.trim();    
            } 
            logger.info("p:"+parameter+",v:"+v);
            packageParams.put(parameter, v);    
        }  
        
        // 微信支付的API密钥    
        String key = weixinpayProperties.getKey();  
        
        if(isTenpaySign("UTF-8", packageParams, key)){ // 验证通过
        	if("SUCCESS".equals((String)packageParams.get("result_code"))){  
        		String out_trade_no=(String) packageParams.get("out_trade_no");
        		Order order=orderService.getByOrderNo(out_trade_no);
        		if(order!=null){
					order.setBuyTime(new Date()); // 支付时间
					order.setIsPay(1); // 支付支付状态 已经支付
					orderService.save(order);
				}
        	}
        }else{
        	logger.error("验证未通过");
        }
	}
	
	/**
	 * 加载支付二维码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/loadPayImage")
	public void loadPayImage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String id=request.getParameter("id");
		Order order=orderService.getById(Integer.parseInt(id));
		String orderNo=DateUtil.getCurrentDateStr(); // 生成订单号
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("appid", weixinpayProperties.getAppid());
		map.put("mch_id", weixinpayProperties.getMch_id());
		map.put("device_info", weixinpayProperties.getDevice_info());
		map.put("notify_url", weixinpayProperties.getNotify_url());
		map.put("trade_type", "NATIVE"); // 交易类型
		map.put("out_trade_no", orderNo);
		map.put("body", order.getBody());
		map.put("total_fee", Integer.parseInt(order.getTotalAmount())*100);
		map.put("nonce_str", StringUtil.getRandomString(30)); // 随机串
		 map.put("spbill_create_ip", getRemortIP(request)); // 终端IP
//		map.put("spbill_create_ip", "127.0.0.1"); // 终端IP
		map.put("sign", getSign(map)); // 签名
		String xml=XmlUtil.genXml(map);
		System.out.println(xml);
		
		InputStream in = HttpClientUtil.sendXMLDataByPost(weixinpayProperties.getUrl().toString(),xml).getEntity().getContent(); // 发送xml消息
		String code_url=getElementValue(in,"code_url"); 
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
	     Map hints = new HashMap();  
	     BitMatrix bitMatrix = null;  
	     try {  
	         bitMatrix = multiFormatWriter.encode(code_url, BarcodeFormat.QR_CODE, 250, 250,hints);  
	         BufferedImage image = toBufferedImage(bitMatrix);  
	         //输出二维码图片流  
	         try {  
	             ImageIO.write(image, "png", response.getOutputStream());  
	         } catch (IOException e) {  
	             e.printStackTrace();  
	         }finally{
	     		order.setOrderNo(orderNo); // 设置订单号
	     		orderService.save(order); // 保存订单信息
	         }
	     } catch (WriterException e1) {  
	         e1.printStackTrace();  
	     }           
	}
	
	/**
	 * 查询订单支付状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/loadPayState")
	public Integer loadPayState(Integer id)throws Exception{
		Order order=orderService.getById(id);
		return order.getIsPay();
	}
	
	/**
	 * 微信支付同步通知页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/returnUrl")
	public ModelAndView returnUrl()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("title", "感谢您的打赏！");
		mav.addObject("message", "相当感谢你！祝你打DOTA把把暴走，打CSGO局局MVP，打荒野行动天天吃鸡！");
		mav.setViewName("returnUrl");
		return mav;
	}
	
	/** 
	   * 类型转换 
	   * @author chenp 
	   * @param matrix 
	   * @return 
	   */  
	 public static BufferedImage toBufferedImage(BitMatrix matrix) {  
      int width = matrix.getWidth();  
      int height = matrix.getHeight();  
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);  
      for (int x = 0; x < width; x++) {  
          for (int y = 0; y < height; y++) {  
              image.setRGB(x, y, matrix.get(x, y) == true ? 0xff000000 : 0xFFFFFFFF);  
          }  
      }  
      return image;  
	 }  
	
	/**
	 * 通过返回IO流获取支付地址
	 * @param in
	 * @return
	 */
	private String getElementValue(InputStream in,String key)throws Exception{
		SAXReader reader = new SAXReader();
    Document document = reader.read(in);
    Element root = document.getRootElement();
    List<Element> childElements = root.elements();
    for (Element child : childElements) {
    	if(key.equals(child.getName())){
    		return child.getStringValue();
    	}
    }
    return null;
	}
	
	/**
 * 获取本机IP地址
 * @return IP
 */
	public static String getRemortIP(HttpServletRequest request) {  
    if (request.getHeader("x-forwarded-for") == null) {  
        return request.getRemoteAddr();  
    }  
    return request.getHeader("x-forwarded-for");  
} 

/**
 * 微信支付签名算法sign
 */
private String getSign(Map<String,Object> map) {
    StringBuffer sb = new StringBuffer();
    String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);//获取map中的key转为array
    Arrays.sort(keyArr);//对array排序
    for (int i = 0, size = keyArr.length; i < size; ++i) {
        if ("sign".equals(keyArr[i])) {
            continue;
        }
        sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
    }
    sb.append("key=" + weixinpayProperties.getKey());
    String sign = string2MD5(sb.toString());
    return sign;
}

/***
 * MD5加码 生成32位md5码
 */
private String string2MD5(String str){
    if (str == null || str.length() == 0) {
        return null;
    }
    char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };

    try {
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(str.getBytes("UTF-8"));

        byte[] md = mdTemp.digest();
        int j = md.length;
        char buf[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
            buf[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(buf).toUpperCase();
    } catch (Exception e) {
        return null;
    }
}


/**  
 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。  
 * @return boolean  
 */    
public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {    
    StringBuffer sb = new StringBuffer();    
    Set es = packageParams.entrySet();    
    Iterator it = es.iterator();    
    while(it.hasNext()) {    
        Map.Entry entry = (Map.Entry)it.next();    
        String k = (String)entry.getKey();    
        String v = (String)entry.getValue();    
        if(!"sign".equals(k) && null != v && !"".equals(v)) {    
            sb.append(k + "=" + v + "&");    
        }    
    }    
    sb.append("key=" + API_KEY);    
        
    //算出摘要    
    String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();    
    String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();    
        
    return tenpaySign.equals(mysign);    
}  
}
