package com.iterson.GooglePlay.holder;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.utils.UIUtils;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class ListBaseHolder extends BaseHolder<AppInfo>{
	
	ImageView item_icon;
	TextView item_title, item_size, item_bottom;
	RatingBar item_rating;
	// 创建view对象 初始化控件
	public View initView() {
		View contentView=UIUtils.inflate(R.layout.list_item);
		this.item_icon=(ImageView) contentView.findViewById(R.id.item_icon);
		this.item_title=(TextView) contentView.findViewById(R.id.item_title);
		this.item_size=(TextView) contentView.findViewById(R.id.item_size);
		this.item_bottom=(TextView) contentView.findViewById(R.id.item_bottom);
		this.item_rating=(RatingBar) contentView.findViewById(R.id.item_rating);
		return contentView;
	}
	//给控件填充数据
	public void refreshView(AppInfo appInfo){
		this.item_title.setText(appInfo.getName());
		this.item_size.setText(Formatter.formatFileSize(UIUtils.getContext(), appInfo.getSize()));
		this.item_bottom.setText(appInfo.getDes());
		// 所有图片都交给bitMapUitls 去管理    整个应用只能有一个bitmapUtils
		bitmapUtils.display(this.item_icon, HttpHelper.SERVER_URL+"image?name="+appInfo.getIconUrl());
	}
	

}