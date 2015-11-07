package com.iterson.GooglePlay.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.iterson.GooglePlay.domain.UserInfo;

public class UserProtocol extends BaseProtocol<UserInfo> {

	@Override
	protected UserInfo parserJson(String json) {
		try {
			JSONObject object = new JSONObject(json);
			String name = object.getString("name");
			String email = object.getString("email");
			String url = object.getString("url");
			UserInfo userInfo = new UserInfo(name, email, url);
			return userInfo;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getKey() {
		return "user";
	}

}
