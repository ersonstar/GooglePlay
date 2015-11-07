package com.iterson.GooglePlay.activity;

import android.view.View;
import android.widget.TextView;

import com.iterson.GooglePlay.ui.LoadingPage;
import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;
import com.iterson.GooglePlay.utils.UIUtils;


public class DetailActivity extends BaseActivity {
	private LoadingPage layout;
	@Override
	protected void initView() {
		
			layout =new LoadingPage(UIUtils.getContext()) {
				
				@Override
				public LoadResult onLoad() {
					return DetailActivity.this.onLoad();
							
				}
				
				@Override
				public View createSuccessView() {
					return DetailActivity.this.createSuccessView();
				}
			};
			layout.show();
			setContentView(layout);
	}
	
	/**
	 * 成功创建界面
	 * @return
	 */
	protected View createSuccessView() {
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText("loadingsuccess");
		return tv;
	}
	/**
	 * 加载数据
	 * @return
	 */
	protected LoadResult onLoad() {
		return LoadResult.success;
	}
	
	
	
}	
