package com.yao.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

/**
 * HttpClient工具类
 * @author Administrator
 *
 */
public class HttpClientUtil {

	/**
	 * 发送xml数据
	 * @param url
	 * @param xmlData
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse sendXMLDataByPost(String url, String xmlData)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(xmlData);
        httppost.setEntity(entity);
        httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");
        HttpResponse response = httpClient.execute(httppost);
        return response;
    }
}
