package com.iterson.GooglePlay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AppDetailInfoHolder extends BaseHolder<AppInfo> {
	// 初始化view控件
	@ViewInject(R.id.item_icon)private ImageView item_icon;
	@ViewInject(R.id.item_title)private TextView item_title;
	@ViewInject(R.id.item_download)private TextView item_download;
	@ViewInject(R.id.item_version)private TextView item_version;
	@ViewInject(R.id.item_date)private TextView item_date;
	@ViewInject(R.id.item_size)private TextView item_size;
	@ViewInject(R.id.item_rating)private RatingBar item_rating;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_info);
		ViewUtils.inject(this,view);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		item_title.setText(info.getName());
		item_download.setText("下载:"+info.getDownloadNum());
		item_version.setText("版本::"+info.getVersion());
		item_size.setText("大小:"+info.getSize());
		item_date.setText("时间:"+info.getDate());
		bitmapUtils.display(item_icon, HttpHelper.SERVER_URL+"image?name"+info.getIconUrl());

	}

}
