package com.iterson.GooglePlay.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.utils.FileUtils;
import com.lidroid.xutils.util.IOUtils;

public abstract class BaseProtocol<T>{
	// http://127.0.0.1:8090/home?index=0
	public T load(int index) {
		// 加载本地的数据
		String json = null;
		json = loadLocal(index);
		if (json == null) {
			json = loadServer(index);
			if (json != null) {
				// System.out.println(json.toString());
				// 保存到本地
				save2Local(index, json);
			}
		}
		// 解析json
		if (json != null) {
			T parserJson = parserJson(json);
			return parserJson;
		}
		return null;
	}


	protected abstract T parserJson(String json);

	// 保存到本地
	private void save2Local(int index, String json) {
		// 1创建数据库 存放到数据库表中
		// 2 缓存整个json文件   为了方便管理缓存  最好在sd卡根目录下创建文件夹,把缓存都保存到指定文件夹中  
		String dir = FileUtils.getCacheDir();
		File file = new File(dir, getKey()+"_" + index);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(System.currentTimeMillis()+1000*60+"\r\n");
			writer.write(json);
			writer.flush();// 刷到文件中
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private String loadServer(int index) {
		String json = HttpHelper.sendGet(HttpHelper.SERVER_URL + getKey()+"?index="+index);
		return json;
	}
	/**
	 * 请求服务器的关键字  比如 HomeFragment  关键字home
	 * @return
	 */
	public abstract String getKey();

	private String loadLocal(int index) {
		String dir = FileUtils.getCacheDir();
		File file = new File(dir, getKey()+"_" + index);
		if(file.exists()){
			 BufferedReader bufferedReader=null;
			try {
				FileReader reader = new FileReader(file);
				bufferedReader = new BufferedReader(reader);
				String readLine = bufferedReader.readLine(); // 先读取一行
				long parseLong = Long.parseLong(readLine);
				//如果过期了 
				if(System.currentTimeMillis()>=parseLong){
					return null;
				}
				StringWriter sw=new StringWriter();
				
				String str=null;
				while((str=bufferedReader.readLine())!=null){
					sw.write(str);
				}
				return sw.toString();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				IOUtils.closeQuietly(bufferedReader);
			}
			
		}
		return null;
	}
}
