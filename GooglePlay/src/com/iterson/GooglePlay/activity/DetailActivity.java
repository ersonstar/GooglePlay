package com.iterson.GooglePlay.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.holder.AppDetailDesHolder;
import com.iterson.GooglePlay.holder.AppDetailInfoHolder;
import com.iterson.GooglePlay.holder.AppDetailSrceenHolder;
import com.iterson.GooglePlay.holder.AppDetailsBottomHolder;
import com.iterson.GooglePlay.holder.AppDetailsDetailSafeHolder;
import com.iterson.GooglePlay.protocol.DetailProtocol;
import com.iterson.GooglePlay.ui.LoadingPage;
import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;
import com.iterson.GooglePlay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class DetailActivity extends BaseActivity {
	private LoadingPage layout;
	private String packageName;
	@Override
	protected void init() {
		Intent intent = getIntent();
		packageName = intent.getStringExtra("packageName");
		System.out.println(packageName);
		super.init();
	}
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
	
	private FrameLayout bottom_layout;
	private FrameLayout detail_info;
	private FrameLayout detail_safe;
	private FrameLayout detail_des;
	private HorizontalScrollView detail_screen;
	
	private AppDetailInfoHolder mAppDetailInfoHolder;
	private AppDetailSrceenHolder mAppDetailSrceenHolder;
	private AppDetailsBottomHolder mAppDetailsBottomHolder;
	private AppDetailsDetailSafeHolder mAppDetailsDetailSafeHolder;
	private AppDetailDesHolder mAppDetailDesHolder;
	/**
	 * 成功创建界面
	 * @return
	 */
	private AppInfo datas;
	protected View createSuccessView() {
		View view = UIUtils.inflate(R.layout.activity_detail);
		ViewUtils.inject(this,view);//利用xUtils工具实例化控件
		//给每个frameLayout添加数据
		//顶部framelayout
		detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		mAppDetailInfoHolder = new AppDetailInfoHolder();//创建Holder  里面管理一个view对象
		mAppDetailInfoHolder.refreshView(datas);//给Holder里面的view对象 填充数据
		detail_info.addView(mAppDetailInfoHolder.getContentView());//把view对象添加到帧布局上
		//滑动图片framelayout
		detail_screen = (HorizontalScrollView) view.findViewById(R.id.detail_screen);
		mAppDetailSrceenHolder = new AppDetailSrceenHolder();
		mAppDetailSrceenHolder.refreshView(datas);
		detail_screen.addView(mAppDetailSrceenHolder.getContentView());
		//底部framelayout
		bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);
		mAppDetailsBottomHolder = new AppDetailsBottomHolder();
		mAppDetailsBottomHolder.refreshView(datas);
		bottom_layout.addView(mAppDetailsBottomHolder.getContentView());
		//detailSafe
		detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
		mAppDetailsDetailSafeHolder = new AppDetailsDetailSafeHolder();
		mAppDetailsDetailSafeHolder.refreshView(datas);
		detail_safe.addView(mAppDetailsDetailSafeHolder.getContentView());
		//简介
		detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
		mAppDetailDesHolder = new AppDetailDesHolder();
		mAppDetailDesHolder.refreshView(datas);
		detail_des.addView(mAppDetailDesHolder.getContentView());
		
		return view;
	}
	/**
	 * 加载数据
	 * @return
	 */
	protected LoadResult onLoad() {
		DetailProtocol protocol = new DetailProtocol(packageName);
		datas = protocol.load(0);
		if (datas==null) {
			return LoadResult.error;
		}else{
		return LoadResult.success;
	}
	}
	
	
}	
