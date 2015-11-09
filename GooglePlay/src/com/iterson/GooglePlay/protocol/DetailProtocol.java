package com.iterson.GooglePlay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iterson.GooglePlay.domain.AppInfo;

public class DetailProtocol extends BaseProtocol<AppInfo> {
	String packageName;

	public DetailProtocol(String packageName) {
		this.packageName = packageName;
	}

	@Override
	protected AppInfo parserJson(String json) {
		try {
			JSONObject object = new JSONObject(json);
			long id = object.getLong("id");
			String name = object.getString("name");
			String packageName = object.getString("packageName");
			String iconUrl = object.getString("iconUrl");
			float stars = Float.parseFloat(object.getString("stars"));
			String downloadNum = object.getString("downloadNum");
			String version = object.getString("version");
			String date = object.getString("date");
			long size = object.getLong("size");
			String downloadUrl = object.getString("downloadUrl");
			String des = object.getString("des");
			String author = object.getString("author");

			List<String> screen = new ArrayList<String>();
			JSONArray jsonArray = object.getJSONArray("screen");
			for (int i = 0; i < jsonArray.length(); i++) {
				screen.add(jsonArray.getString(i));
			}

			List<String> safeUrl = new ArrayList<String>();
			List<String> safeDesUrl = new ArrayList<String>();
			;
			List<String> safeDes = new ArrayList<String>();
			;
			List<Integer> safeDesColor = new ArrayList<Integer>();

			JSONArray jsonArray2 = object.getJSONArray("safe");
			for (int i = 0; i < jsonArray2.length(); i++) {
				JSONObject object2 = jsonArray2.getJSONObject(i);
				safeUrl.add(object2.getString("safeUrl"));
				safeDesUrl.add(object2.getString("safeDesUrl"));
				safeDes.add(object2.getString("safeDes"));
				safeDesColor.add(object2.getInt("safeDesColor"));
			}
			AppInfo info = new AppInfo(id, name, packageName, iconUrl, stars,
					size, downloadUrl, des, downloadNum, version, date, author,
					screen, safeUrl, safeDesUrl, safeDes, safeDesColor);
			
			return info;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		
	}

	// 请求服务器跳转的关键字
	@Override
	public String getKey() {
		return "detail";
	}

	@Override
	protected String getParams() {
		return "&packageName=" + packageName;
	}

}
