package com.iterson.GooglePlay.utils;

import com.iterson.GooglePlay.activity.BaseApplication;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;

/**
 * 工具类，得到String-array数组 而且还不用传context application中做了全局上下文
 * 
 * @author Yang
 * 
 */

public class UIUtils {
	/**
	 * 获取字符串数组
	 * 
	 * @param id
	 * @return
	 */
	public static String[] getStringArray(int id) {
		return getResource().getStringArray(id);
	}

	public static Resources getResource() {
		return getContext().getResources();
	}

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** px转换dip */

	public static int px2dip(int px) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 获取上下文
	 * 
	 * @return
	 */
	public static Context getContext() {
		return BaseApplication.getApplication();
	}

	/**
	 * 获取资源目录的颜色
	 * 
	 * @param id
	 * @return
	 */
	public static int getColor(int id) {
		return getResource().getColor(id);
	}

	public static View inflate(int resource) {
		return View.inflate(getContext(), resource, null);
	}

	public static void runOnUiThread(Runnable runnable) {
		// 当前线程的id
		int myTid = android.os.Process.myTid();
		System.out.println("UIUtils:" + myTid);
		// 判断是否在主线程中
		if (myTid == BaseApplication.getMainId()) {
			runnable.run();
		} else {
			// 获取Handler
			Handler handler = BaseApplication.getHandler();
			handler.post(runnable);
		}
	}

}
