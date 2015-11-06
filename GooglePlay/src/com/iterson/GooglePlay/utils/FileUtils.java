package com.iterson.GooglePlay.utils;

import java.io.File;

import android.os.Environment;
/**
 * 为了更好的管理缓存
 * @author Yang
 *
 */

public class FileUtils {
	private static final String ROOT_DIR="GooglePlay";
	/**
	 * 获取json文件缓存路径/mnt/sdcard/GooglePlay/cache
	 * @return
	 */
	public static String getCacheDir() {
		return getDir("cache");
	}
	/**
	 * 获取图片缓存的路径 /mnt/sdcard/GooglePlay/icon
	 * @return
	 */
	public static String geIconDir() {
		return getDir("icon");
	}

	private static String getDir(String cache) {
		String path;
		StringBuilder  sb=new StringBuilder();
		//  如果用户内存已经缓存过了 ,  可以不需要缓存了
		if(isSDAvailable()){
			String sd=Environment.getExternalStorageDirectory().getAbsolutePath();// 
			sb.append(sd);
			sb.append(File.separator);// /mnt/sdcard/
			sb.append(ROOT_DIR);// /mnt/sdcard/GooglePlay
			sb.append(File.separator).append(cache);//// /mnt/sdcard/GooglePlay/cache
			path=sb.toString();

		}else{
			//  sd不可用  /data/data/包名/cache 
			String dataPath=UIUtils.getContext().getCacheDir().getAbsolutePath();
			sb.append(dataPath);//  /data/data/包名/cache 
			sb.append(File.separator).append(cache);//  /data/data/包名/cache/cache
			path=sb.toString();
			
		}
		File file=new File(path);
		if(!file.exists()||!file.isDirectory()){
			file.mkdirs();  // 创建文件夹
		}
		
		return path;
	}

	private static boolean isSDAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

}
