package com.iterson.GooglePlay.utils;

import com.iterson.GooglePlay.ui.LoadingPage;

import android.view.ViewGroup;
import android.view.ViewParent;


public class ViewUtils {

	public static void removeParent(LoadingPage layout) {
		//  获取到爹
		
		ViewParent parent = layout.getParent();
		if(parent instanceof ViewGroup){
			ViewGroup v=(ViewGroup) parent;
			// 爹移除孩子
			v.removeView(layout);
		}
		  
	}

}
