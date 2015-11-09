package com.iterson.GooglePlay.holder;

import java.util.List;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.utils.UIUtils;

import android.view.View;
import android.widget.ImageView;


public class AppDetailSrceenHolder extends BaseHolder<AppInfo> {
	
	ImageView[] screens;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_screen);
		screens = new ImageView[5];
		screens[0] = (ImageView) view.findViewById(R.id.screen_1);
		screens[1] = (ImageView) view.findViewById(R.id.screen_2);
		screens[2] = (ImageView) view.findViewById(R.id.screen_3);
		screens[3] = (ImageView) view.findViewById(R.id.screen_4);
		screens[4] = (ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		List<String> screen = info.getScreen();
		for (int i = 0; i < 5; i++) {
			if (i<screen.size()) {
				screens[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(screens[i], HttpHelper.SERVER_URL+"image?name="+screen.get(i));
			}else {
				screens[i].setVisibility(View.GONE);
			}
		}
		
	}
	

}
