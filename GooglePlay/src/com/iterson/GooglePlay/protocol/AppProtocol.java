package com.iterson.GooglePlay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iterson.GooglePlay.domain.AppInfo;

//泛型代表 最终返回的类型
public class AppProtocol extends BaseProtocol<List<AppInfo>> {

@Override
protected List<AppInfo> parserJson(String json) {
	List<AppInfo> infos=new ArrayList<AppInfo>();
	try {
		JSONArray array=new JSONArray(json);
		for(int i=0;i<array.length();i++){
			JSONObject obj = array.getJSONObject(i);
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
	return "app";
}

}
