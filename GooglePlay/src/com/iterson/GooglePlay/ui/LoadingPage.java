package com.iterson.GooglePlay.ui;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.manager.ThreadManager;
import com.iterson.GooglePlay.utils.UIUtils;

public abstract class LoadingPage extends FrameLayout {
	// 当帧布局创建的时候调用
	private void init() {
		// 1 把几种不同的界面 添加到帧布局上,
		initView();
		// 2 根据不同的状态 显示不同的界面(控制界面的显示和隐藏)
		showPage();
	}

	// 五种状态 默认 加载中 加载失败 加载为空 加载成功 0 1 2 3 4 magic number
	public static final int STATE_NONE = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_EMPTY = 3;
	public static final int STATE_SUCCESS = 4;
	public int state = STATE_NONE;// 当前的状态

	private View loadingView;// 加载中的View对象
	private View errorView;// 错误的View对象
	private View emptyView;// 空界面的View对象
	private View successView;// 成功的界面

	// 1 把三种不同的界面 添加到帧布局上,
	private void initView() {
		// 添加了加载中view对象
		loadingView = createLoadingView();
		if (loadingView != null) {
			this.addView(loadingView);
		}
		errorView = createErrorView();
		if (errorView != null) {
			this.addView(errorView);
		}
		emptyView = createEmptyView();
		if (emptyView != null) {
			this.addView(emptyView);
		}

	}

	// 2 根据不同的状态 显示不同的界面(控制界面的显示和隐藏)
	private void showPage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_NONE
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (state == STATE_SUCCESS) {
			if (successView == null) {
				successView = createSuccessView();// 创建成功界面
				this.addView(successView);
				successView.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * 创建成功界面
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	// 创建空的界面
	private View createEmptyView() {

		return UIUtils.inflate(R.layout.loadpage_empty);
	}

	// 创建错误界面
	private View createErrorView() {
		View view = UIUtils.inflate(R.layout.loadpage_error);
		Button reload = (Button) view.findViewById(R.id.btn_reloading);
		reload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	// 3 根据服务器数据 切换状态
	public void show() {
		// 在android 4.0 以后 不允许在主线程请求服务器
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_NONE; // 在这个位置 也修改过状态

		}
		if (state == STATE_NONE) {
			state = STATE_LOADING;
			// new Thread(new TaskRunnable()).start();
			ThreadManager.getInstance().executeLongTask(new TaskRunnable());
		}
		showPage();
	}

	private class TaskRunnable implements Runnable {

		@Override
		public void run() {
			SystemClock.sleep(2000);
			// ------
			// 在子线程中
			// 处理请求服务器
			final LoadResult result = onLoad();
			// 获取服务器返回的状态码
			// getActivity(). 只有当Fragment挂载到activity上的时候 才不为null
			UIUtils.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					state = result.getValue();
					showPage();
				}
			});

		}

	}

	// 枚举类型 限制值的范围
	public enum LoadResult {
		error(2), empty(3), success(4);

		int value;// 用来提供返回的状态 2 3 4

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	/** 连接服务器 */
	public abstract LoadResult onLoad();

	// 创建加载中的view对象
	private View createLoadingView() {
		return UIUtils.inflate(R.layout.loadpage_loading);
	}

	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingPage(Context context) {
		super(context);
		init();
	}
}
