package com.iterson.GooglePlay.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.renderscript.Sampler.Value;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.utils.UIUtils;

public class AppDetailDesHolder extends BaseHolder<AppInfo> implements
		OnClickListener {

	private TextView des_content;
	private TextView des_author_tv;
	private ImageView des_arrow_iv;
	private LinearLayout detail_des_ll;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_des);

		detail_des_ll = (LinearLayout) view.findViewById(R.id.detail_des_ll);
		des_arrow_iv = (ImageView) view.findViewById(R.id.des_arrow_iv);
		des_content = (TextView) view.findViewById(R.id.des_content);
		des_author_tv = (TextView) view.findViewById(R.id.des_author_tv);

		des_content.getLayoutParams().height = 100;
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		des_content.setText(info.getDes());
		des_author_tv.setText("作者:" + info.getauthor());
		detail_des_ll.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Expand();
	}

	boolean isExpand = false;

	private void Expand() {
		final int startHeight;
		final int endHeight;
		// 得到ScrollView 从而让ScrollView滑到底部
		final ScrollView scrollView = getScrollView(des_content);

		if (isExpand) {
			startHeight = getMeasureLongHeight();
			endHeight = 150;
		} else {
			startHeight = 150;
			endHeight = getMeasureLongHeight();
		}
		final ValueAnimator animator = ValueAnimator.ofInt(startHeight,
				endHeight);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int animatedValue = (int) animator.getAnimatedValue();
				des_content.getLayoutParams().height = animatedValue;
				
				int roationStartValue = endHeight-startHeight;
				int roationEndValue = animatedValue-startHeight;
				des_arrow_iv.setRotation(roationEndValue*180/roationStartValue);
				
				des_content.setLayoutParams(des_content.getLayoutParams());
				if (scrollView != null) {
					scrollView.scrollTo(des_content.getLayoutParams().width, animatedValue);
				}
			}
		});
		//监听动画，当点击时不能被点击，结束才能再次被点击
				animator.addListener(new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animation) {
						detail_des_ll.setEnabled(false);
					}
					
					@Override
					public void onAnimationRepeat(Animator animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animator animation) {
						detail_des_ll.setEnabled(true);
					}
					
					@Override
					public void onAnimationCancel(Animator animation) {
						
					}
				});
		animator.setDuration(1000);
		animator.start();
		isExpand = !isExpand;
	}
	//测量控件高度
	private int getMeasureLongHeight() {
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(des_content.getWidth(), MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);
		des_content.measure(widthMeasureSpec, heightMeasureSpec);
		return des_content.getMeasuredHeight();
	}

	/**
	 * 得到父容器 直到得到ScrollView为止
	 * 
	 * @param view
	 * @return
	 */
	private ScrollView getScrollView(View view) {
		ViewParent parent = view.getParent();
		if (parent instanceof ViewGroup) {
			if (parent instanceof ScrollView) {
				return (ScrollView) parent;
			} else {
				// 如果不是ScrollView,递归调用该方法
				return getScrollView((View) parent);
			}
		} else {
			return null;
		}

	}

}
