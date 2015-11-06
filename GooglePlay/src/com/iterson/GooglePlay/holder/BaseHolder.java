package com.iterson.GooglePlay.holder;
import android.view.View;

import com.iterson.GooglePlay.manager.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

public abstract class BaseHolder<T> {
	// holder里面 持有了view对象 
	View contentView;
	protected BitmapUtils bitmapUtils;
	public  BaseHolder(){
		bitmapUtils = BitmapHelper.getBitmapUtils();
		contentView=initView();
		contentView.setTag(this); 
	}

	public View getContentView() {
		return contentView;
	}
	/**
	 * 创建view对象 初始化控件
	 * @return
	 */
	public  abstract View initView();
	/**
	 * 给控件填充数据
	 * @param info
	 */
	public abstract void refreshView(T info);
}

