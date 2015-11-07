package com.iterson.GooglePlay.fragment;

import java.util.List;

import android.view.View;

import com.iterson.GooglePlay.adpter.ListBaseAdapter;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.protocol.AppProtocol;
import com.iterson.GooglePlay.ui.BaseListView;
import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;
import com.iterson.GooglePlay.utils.UIUtils;


public class AppFragment extends BaseFragment {

	private List<AppInfo> datas;
	// 创建成功界面
	@Override
	public View createSuccessView() {
		BaseListView lv = new BaseListView(UIUtils.getContext());
		lv.setAdapter(new ListBaseAdapter(datas,lv){

			@Override
			protected List<AppInfo> onLoad() {
				AppProtocol protocol=new AppProtocol();
				System.out.println(datas.size());
				List<AppInfo> newDatas = protocol.load(datas.size());
				
				return newDatas;
			}
			
		});
		return lv;
	}

	@Override
	public LoadResult onLoad() {
		AppProtocol protocol=new AppProtocol();
		datas = protocol.load(0);
		return checkDatas(datas);
	}
	
}
