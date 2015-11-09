package com.iterson.GooglePlay.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.utils.UIUtils;

public class AppDetailsBottomHolder extends BaseHolder<AppInfo> implements
		OnClickListener {
	private Button bottom_favorites;
	private Button bottom_share;
	private Button progress_btn;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_bottom);
		bottom_favorites = (Button) view.findViewById(R.id.bottom_favorites);
		bottom_share = (Button) view.findViewById(R.id.bottom_share);
		progress_btn = (Button) view.findViewById(R.id.progress_btn);

		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		bottom_favorites.setOnClickListener(this);
		bottom_share.setOnClickListener(this);
		progress_btn.setOnClickListener(this);
	}

	/**
	 * 几个点击
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bottom_favorites:
			Toast.makeText(UIUtils.getContext(), "收藏", 0).show();
			break;
		case R.id.bottom_share:
			Toast.makeText(UIUtils.getContext(), "分享", 0).show();
			break;

		case R.id.progress_btn:
			Toast.makeText(UIUtils.getContext(), "进度", 0).show();
			break;

		default:
			break;
		}
	}

}
