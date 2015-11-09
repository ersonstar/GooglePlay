package com.iterson.GooglePlay.holder;

import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Color;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.domain.AppInfo;
import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.utils.UIUtils;

public class AppDetailsDetailSafeHolder extends BaseHolder<AppInfo> implements OnClickListener {
	// 初始化界面
	private RelativeLayout safe_layout;
	private RelativeLayout safe_title_layout;
	private LinearLayout safe_title;
	private LinearLayout safe_content;
	private ImageView[] ivs;
	private ImageView safe_arrow;
	private LinearLayout[] des_layouts;
	private ImageView[] des_ivs;
	private TextView[] des_tvs;
	//是否展开
	private boolean isExpand = false;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_safe);
		 safe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);
		 safe_content = (LinearLayout) view.findViewById(R.id.safe_content);
		 safe_title_layout = (RelativeLayout) view.findViewById(R.id.safe_title_layout);
		 safe_title= (LinearLayout) view.findViewById(R.id.safe_title);
		 ivs = new  ImageView[4];
		 ivs[0]= (ImageView) view.findViewById(R.id.iv_1);
		 ivs[1]= (ImageView) view.findViewById(R.id.iv_2);
		 ivs[2]= (ImageView) view.findViewById(R.id.iv_3);
		 ivs[3]= (ImageView) view.findViewById(R.id.iv_4);
		 safe_arrow= (ImageView) view.findViewById(R.id.safe_arrow);
		 
		 des_layouts  = new LinearLayout[4];
		 des_ivs = new ImageView[4];
		 des_tvs = new TextView[4];
		 des_layouts[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
		 des_ivs[0] = (ImageView) view.findViewById(R.id.des_iv_1);
		 des_tvs[0] = (TextView) view.findViewById(R.id.des_tv_1);
		
		 des_layouts[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
		 des_ivs[1] = (ImageView) view.findViewById(R.id.des_iv_2);
		 des_tvs[1] = (TextView) view.findViewById(R.id.des_tv_2);
		
		 des_layouts[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
		 des_ivs[2] = (ImageView) view.findViewById(R.id.des_iv_3);
		 des_tvs[2] = (TextView) view.findViewById(R.id.des_tv_3);
		 
		 des_layouts[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);
		 des_ivs[3] = (ImageView) view.findViewById(R.id.des_iv_4);
		 des_tvs[3] = (TextView) view.findViewById(R.id.des_tv_4);
		 //把内容的的高度初始化设置为0 
		 safe_content.getLayoutParams().height = 0;
		 return view;
	}

	// 给界面填充数据
	@Override
	public void refreshView(AppInfo info) {
		List<String> safeDes = info.getSafeDes();
		List<Integer> safeDesColor = info.getSafeDesColor();
		List<String> safeDesUrl = info.getSafeDesUrl();
		List<String> safeUrl = info.getSafeUrl();
		
		
		for (int i = 0; i < 4; i++) {
			if (i < safeDes.size()) {
				bitmapUtils.display(ivs[i], HttpHelper.SERVER_URL
						+ "image?name=" + safeUrl.get(i));
				ivs[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(des_ivs[i], HttpHelper.SERVER_URL
						+ "image?name=" + safeDesUrl.get(i));
				des_tvs[i].setText(safeDes.get(i));
				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0);
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				} 
				des_tvs[i].setTextColor(color);
				
				des_layouts[i].setVisibility(View.VISIBLE);
			} else {
				des_layouts[i].setVisibility(View.GONE);
				ivs[i].setVisibility(View.GONE);
			}
			
		}
		safe_layout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Expand();
	}
	
	private void Expand() {
		final LayoutParams layoutParams = safe_content.getLayoutParams();
		int startHeight;
		int endHeight;
		if (isExpand) {//如果是展开的
			startHeight = getMeasureHeight();
			endHeight = 0;
		}else{//如果是未展开
			startHeight = 0;
			endHeight = getMeasureHeight();
		}
		
		final ValueAnimator animator = ValueAnimator.ofInt(startHeight,endHeight);
		animator.setDuration(1000);
		//监听值得变化
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//得到变化的值
				int animatedValue = (int) animator.getAnimatedValue();
				
				layoutParams.height = animatedValue;
				//设置旋转角度,随着变化
				safe_arrow.setRotation(animatedValue*180/getMeasureHeight());
				
				//因为界面已经显示了，不设置参数，界面不会重新绘制
				safe_content.setLayoutParams(layoutParams);
			}
		});
		//监听动画，当点击时不能被点击，结束才能再次被点击
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				safe_layout.setEnabled(false);
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				safe_layout.setEnabled(true);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
		animator.start();
		
		
		
		isExpand = !isExpand;
	}
	/**
	 * 设置高度
	 * @return
	 */
	private int getMeasureHeight() {
		int width = safe_content.getWidth();
		//MeasureSpec.makeMeasureSpec 两参数，第一个带是数组，第二个是模式
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);//代表精确数值得模式
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);//代表最大值的模式
		safe_content.measure(widthMeasureSpec, heightMeasureSpec);
		//不完全的设置方法
//		safe_content.measure(0, 0); 此处该方法可以用，但是不是所有地方通用
		return safe_content.getMeasuredHeight();
	}

	

}
