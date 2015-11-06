package com.iterson.GooglePlay.fragment;

import android.view.View;

import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;

public class HotFragment extends BaseFragment {

	@Override
	public View createSuccessView() {
		return null;
	}

	@Override
	public LoadResult onLoad() {
		return LoadResult.error;
	}
}
