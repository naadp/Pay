package com.yao.util;

/**
 * 设备工具类
 * @author Administrator
 *
 */
public class DeviceUtil {

	/**
	 * 判断是否是移动设备
	 * @param requestHeader
	 * @return
	 */
	public static boolean isMobileDevice(String requestHeader){
		String[] deviceArray = new String[]{"android","iphone","windows phone"};  
		if(requestHeader == null)  
            return false;
		requestHeader = requestHeader.toLowerCase(); 
		for(int i=0;i<deviceArray.length;i++){  
            if(requestHeader.indexOf(deviceArray[i])>0){  
                return true;  
            }  
        }  
		return false;
	}
}
