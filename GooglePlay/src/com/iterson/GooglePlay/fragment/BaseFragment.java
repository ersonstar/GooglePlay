package com.iterson.GooglePlay.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iterson.GooglePlay.ui.LoadingPage;
import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;
import com.iterson.GooglePlay.utils.UIUtils;
import com.iterson.GooglePlay.utils.ViewUtils;

public abstract class BaseFragment extends Fragment {
	// 把和帧布局相关的操作全部拆分到 帧不居中
	private LoadingPage layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			layout = new LoadingPage(UIUtils.getContext()) {

				@Override
				public View createSuccessView() {
					return BaseFragment.this.createSuccessView();
				}

				@Override
				public LoadResult onLoad() {
					return BaseFragment.this.onLoad();
				}

			};
		}else{
			ViewUtils.removeParent(layout);// 移除之前的父容器
		}
		// show(); 不要在界面创建的时候调用 在界面真正显示的时候调用
		return layout;
	}

	/**
	 * 检查当前服务器返回的数据
	 * @param datas
	 * @return
	 */
	public LoadResult checkDatas(List datas) {
		if (datas == null) {
			return LoadResult.error;
		} else if (datas.size() == 0) {
			return LoadResult.empty;
		} else {
			return LoadResult.success;
		}
	}

	/**
	 * 创建成功界面
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	// 3 根据服务器数据 切换状态
	public void show() {
		layout.show();
	}

	/** 连接服务器 */
	public abstract LoadResult onLoad();

}
