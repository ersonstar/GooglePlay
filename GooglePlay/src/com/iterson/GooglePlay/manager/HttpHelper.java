package com.iterson.GooglePlay.manager;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;


public class HttpHelper {
	private static HttpUtils httpUtils;
	public static final String SERVER_URL="http://127.0.0.1:8090/";
	public static String sendGet(String url){
		if(httpUtils==null){
			httpUtils=new HttpUtils();
		}
		try {
			ResponseStream sendSync = httpUtils.sendSync(HttpMethod.GET, url);
			return sendSync.readString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
