package com.iterson.GooglePlay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iterson.GooglePlay.domain.AppInfo;

public class HomeProtocol  extends BaseProtocol<List<AppInfo>>{
	private List<String> pictures;
	//见到大括号就是jsonObject  见到中括号就是jsonArray
	public  List<AppInfo> parserJson(String json) {
		List<AppInfo> infos=new ArrayList<AppInfo>();
		pictures = new ArrayList<String>();
		try {
			JSONObject jsonObject=new JSONObject(json);
			//图片地址
			JSONArray jsonArray2 = jsonObject.getJSONArray("picture");
			for (int i = 0; i < jsonArray2.length(); i++) {
				String url = jsonArray2.getString(i);
				pictures.add(url);
			}
			//app详情
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				long id = obj.getLong("id");
				String name = obj.getString("name");
				String packageName = obj.getString("packageName");
				String iconUrl = obj.getString("iconUrl");
				float stars=Float.parseFloat(obj.getString("stars"));
				long size = obj.getLong("size");
				String downloadUrl=obj.getString("downloadUrl");
				String des=obj.getString("des");
				
				AppInfo appInfo=new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
				infos.add(appInfo);
			}
			return infos;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getKey() {
		return "home";
	}

	/**
	 * 顶部轮询图
	 * 暴露出pictures
	 * @return
	 */
	public List<String> getPictures() {
		return pictures;
	}



}
