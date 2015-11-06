package com.iterson.GooglePlay.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.adpter.DefaultAdapter;
import com.iterson.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class MoreHolder extends BaseHolder<Integer> {
	public static final int  HAS_MORE=0; 
	public static final int  HAS_NO_MORE=1; 
	public static final int  LOAD_ERROR=2; 
	
	private DefaultAdapter adapter;
	public MoreHolder(DefaultAdapter defaultAdapter) {
		super();
		this.adapter=defaultAdapter;
	}
	@ViewInject(R.id.rl_more_loading)
	private RelativeLayout rl_more_loading;
	@ViewInject(R.id.rl_more_error)
	private RelativeLayout rl_more_error;
	//  初始化界面
	@Override
	public View initView() {
		View view=UIUtils.inflate(R.layout.item_more);
		ViewUtils.inject(this, view);
		return view;
	}
	//  填充数据
	@Override
	public void refreshView(Integer info) {
		//  修改界面
		rl_more_error.setVisibility(info==LOAD_ERROR?View.VISIBLE:View.GONE);
		rl_more_loading.setVisibility(info==HAS_MORE?View.VISIBLE:View.GONE);
	}
	
	@Override
	public View getContentView() {
		//  加载更多逻辑 
		loadMore();
		return super.getContentView();
	}

	private void loadMore() {
		adapter.loadMore();
	}


	
}
