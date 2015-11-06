package com.iterson.GooglePlay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iterson.GooglePlay.domain.SubjectInfo;

public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>>{

	protected List<SubjectInfo> parserJson(String json) {
		List<SubjectInfo> infos=new ArrayList<SubjectInfo>();
		try {
			JSONArray array=new JSONArray(json);
			for(int i=0;i<array.length();i++){
				JSONObject object=array.getJSONObject(i);
				String des=object.getString("des");
				String url=object.getString("url");
				SubjectInfo info=new SubjectInfo(des, url);
				infos.add(info);
			}
			return infos;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public String getKey() {
		return "subject";
	}

}
