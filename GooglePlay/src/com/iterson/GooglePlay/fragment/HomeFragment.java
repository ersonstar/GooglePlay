package com.iterson.GooglePlay.fragment;

import java.util.List;

import android.content.Intent;
import android.view.View;

import com.iterson.GooglePlay.activity.DetailActivity;
import com.iterson.GooglePlay.adpter.ListBaseAdapter;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.holder.HomePictureHolder;
import com.iterson.GooglePlay.protocol.HomeProtocol;
import com.iterson.GooglePlay.ui.BaseListView;
import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;
import com.iterson.GooglePlay.utils.UIUtils;

/**
 * 首页
 * 
 * @author Yang
 * 
 */

public class HomeFragment extends BaseFragment {
	private List<AppInfo> datas;
	private List<String> pictures;

	// 保证第一个界面一进来加载数据
	@Override
	public void onStart() {
		super.onStart();
		show();
	}
	/**
	 * 创建成功界面
	 * 
	 * @return
	 */
	public View createSuccessView() {
		BaseListView lv = new BaseListView(UIUtils.getContext());
		//创建viewPage 设置adapter
		//给listview添加个头布局
		HomePictureHolder holder = new HomePictureHolder();
		holder.refreshView(pictures);//界面数据设置
		lv.addHeaderView(holder.getContentView());//吧viewPager添加到listview上
		
		lv.setAdapter(new ListBaseAdapter(datas,lv){

			@Override
			protected List<AppInfo> onLoad() {
				HomeProtocol protocol = new HomeProtocol();
				List<AppInfo> newDatas = protocol.load(datas.size());
				return newDatas;
			}
			//该方法中处理条目点击事件
			@Override
			protected void onInnerItemClick(int position) {
				super.onInnerItemClick(position);
				//根据对象获取数据
				AppInfo appInfo = datas.get(position);
				//跳转到DetailActivity
				Intent intent = new Intent(UIUtils.getContext(),DetailActivity.class);
				startActivity(intent);
			}
			
		});
// java.lang.IllegalStateException: Cannot add header view to list -- setAdapter has already been called.
//不能设置了adapter再添加一个headview;
		
//		//创建viewPage 设置adapter
//		//给listview添加个头布局
//		HomePictureHolder holder = new HomePictureHolder();
//		holder.refreshView(pictures);//界面数据设置
//		lv.addHeaderView(holder.getContentView());//吧viewPager添加到listview上

		return lv;
	}


	

	/** 连接服务器 */
	@Override
	public LoadResult onLoad() {
		// 真正连接服务器
		HomeProtocol protocol = new HomeProtocol();
		//list数据
		datas = protocol.load(0);
		//顶部轮询图
		pictures = protocol.getPictures();
		return checkDatas(datas);
		// 需要获取服务器的数据 null 错误的状态 空集合 empty状态 有数据 success
		// 再根据数据 判断服务器返回的状态
	}



}
