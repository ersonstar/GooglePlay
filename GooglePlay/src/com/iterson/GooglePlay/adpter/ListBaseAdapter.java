package com.iterson.GooglePlay.adpter;


import java.util.List;

import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.holder.BaseHolder;
import com.iterson.GooglePlay.holder.ListBaseHolder;

public abstract class ListBaseAdapter extends DefaultAdapter<AppInfo> {
	public ListBaseAdapter(List<AppInfo> datas) {
		super(datas);
	}
	

	@Override
	public BaseHolder<AppInfo> getHolder() {
		return new ListBaseHolder();
	}
}
