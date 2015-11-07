package com.iterson.GooglePlay.fragment;

import java.util.List;

import android.view.View;

import com.iterson.GooglePlay.adpter.ListBaseAdapter;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.protocol.GameProtocol;
import com.iterson.GooglePlay.ui.BaseListView;
import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;
import com.iterson.GooglePlay.utils.UIUtils;

public class GameFragment extends BaseFragment {

	private List<AppInfo> datas;

	@Override
	public View createSuccessView() {
		BaseListView lv=new BaseListView(UIUtils.getContext());
		lv.setAdapter(new ListBaseAdapter(datas,lv){

			@Override
			protected List<AppInfo> onLoad() {
				GameProtocol protocol=new GameProtocol();
				List<AppInfo> newDatas = protocol.load(datas.size());
				return newDatas;
			}
			
		});
		return lv;
	}

	@Override
	public LoadResult onLoad() {
		GameProtocol protocol=new GameProtocol();
		datas = protocol.load(0);
		return checkDatas(datas);
	}

}
