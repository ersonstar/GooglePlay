package com.iterson.GooglePlay.fragment;

import java.util.List;

import android.view.View;

import com.iterson.GooglePlay.adpter.ListBaseAdapter;
import com.iterson.GooglePlay.domain.AppInfo;
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
		lv.setAdapter(new ListBaseAdapter(datas){

			@Override
			protected List<AppInfo> onLoad() {
				HomeProtocol protocol = new HomeProtocol();
				List<AppInfo> newDatas = protocol.load(datas.size());
				return newDatas;
			}
			
		});

		return lv;
	}


	

	/** 连接服务器 */
	@Override
	public LoadResult onLoad() {
		// 真正连接服务器
		HomeProtocol protocol = new HomeProtocol();
		datas = protocol.load(0);
		return checkDatas(datas);
		// 需要获取服务器的数据 null 错误的状态 空集合 empty状态 有数据 success
		// 再根据数据 判断服务器返回的状态
	}



}
