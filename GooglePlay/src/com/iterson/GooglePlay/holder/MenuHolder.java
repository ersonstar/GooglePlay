package com.iterson.GooglePlay.holder;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.UserInfo;
import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.manager.ThreadManager;
import com.iterson.GooglePlay.protocol.UserProtocol;
import com.iterson.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuHolder extends BaseHolder<UserInfo> implements OnClickListener {
	@ViewInject(R.id.photo_layout)
	private RelativeLayout photo_layout;
	@ViewInject(R.id.home_layout)
	private RelativeLayout home_layout;

	// 登录成功用到的界面
	@ViewInject(R.id.image_photo)
	private ImageView image_photo;
	@ViewInject(R.id.user_name)
	private TextView user_name;
	@ViewInject(R.id.user_email)
	private TextView user_email;

	// 初始化界面
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.item_menu);
		ViewUtils.inject(this, view);// 利用xUtils里面的封装，初始化控件
		photo_layout.setOnClickListener(this);
		home_layout.setOnClickListener(this);

		return view;
	}

	@Override
	public void refreshView(UserInfo info) {
		user_name.setText(info.getName());
		user_email.setText(info.getEmail());
		bitmapUtils.display(image_photo, HttpHelper.SERVER_URL
				+ "image?name=" + info.getUrl());

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo_layout:
			Toast.makeText(UIUtils.getContext(), "loading", 0).show();
			login();
			break;

		case R.id.home_layout:
			Toast.makeText(UIUtils.getContext(), "首页", 0).show();
			break;
		default:
			break;
		}

	}

	// login在主线程
	private void login() {
		// 请求服务器校验 服务器返回用户信息
		//请求服务器需要在子线程做
		ThreadManager.getInstance().executeLongTask(new Runnable() {

			@Override
			public void run() {
				UserProtocol protocol = new UserProtocol();
				final UserInfo userInfo= protocol.load(0);
				//修改ui需要回到主线程
				UIUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						refreshView(userInfo);
					}
				});

			}
		});

	}

}
