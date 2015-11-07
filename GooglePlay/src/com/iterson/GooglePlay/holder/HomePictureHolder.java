package com.iterson.GooglePlay.holder;

import java.util.LinkedList;
import java.util.List;

import com.iterson.GooglePlay.activity.BaseApplication;
import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.utils.UIUtils;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;

public class HomePictureHolder extends BaseHolder<List<String>>{
	private ViewPager vp;
	private List<String> datas;
	//创建view对象
	@Override
	public View initView() {
		vp = new ViewPager(UIUtils.getContext());
		//参数1 宽度，参数2宽度   参2因为创建的时候还没有给他设置数据，所有如果用匹配内容的话高度还是为0.所以最好固定高度
		vp.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,UIUtils.dip2px(134)));//vp 最终挂载到listview上，所有参数数据取决于父容器
		return vp;
	}
	//给view对象赋值
	@Override
	public void refreshView(List<String> datas) {
		this.datas = datas;
		vp.setAdapter(new HomePictureAdapter());
		//设置开始显示条目的位置，这样就能开始就能左右滑动
		vp.setCurrentItem(1000*datas.size());
		final AutoRunningTask autoRunningTask = new AutoRunningTask();
		autoRunningTask.start();
		vp.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					autoRunningTask.stop();
					break;
				case MotionEvent.ACTION_CANCEL://事件取消，比如开始在viewPager上滑动，完了手指移动到viewPager之外。
					autoRunningTask.stop();
					break;
				case MotionEvent.ACTION_UP:
					autoRunningTask.start();
					break;	
				default:
					break;
				}
				return false;//因为viewpage自己有监听触摸事件，如果返回ture的话，就会viewpage的触摸事件，手指一动
			}
		});
	}
	/**
	 * pagerAdpter
	 * @author Yang
	 *
	 */
	private class HomePictureAdapter extends PagerAdapter{
		LinkedList<ImageView> list = new LinkedList<ImageView>();
		
		//多添加2个方法， 3  4 销毁条目，和创建条目
		@Override
		public int getCount() {
			//可以无限滑动
			return Integer.MAX_VALUE;
		}
		//判断添加的view对象和返回的对象的关系
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		//回收view对象
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// object 代表的是   instantiateItem 方法返回的对象
			list.add((ImageView) object);
			container.removeView((View) object);
		}
		//返回条目对应的对象
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			String url = datas.get(position%datas.size());
			ImageView imageView;
			//优化原则，不要来来回回创建对象，能复用就复用
			if (list.size()>0) {
				 imageView = list.removeFirst();
			}else {
				 imageView = new ImageView(UIUtils.getContext());
			}
			
			//给imageview 设置图片。用xUtils封装的
			bitmapUtils.display(imageView, HttpHelper.SERVER_URL + "image?name="+url);
			container.addView(imageView);//添加当前条目到viewPager上
			return imageView;
		}
		
	}
	
	boolean flag;
	private class AutoRunningTask implements Runnable{

		@Override
		public void run() {
			if (flag) {
				BaseApplication.getHandler().removeCallbacks(this);//移除之前的任务
				//得到当前条目
				int currentItem = vp.getCurrentItem();
				currentItem++;
				vp.setCurrentItem(currentItem);
				//把当前任务交给handler去执行，他会自动调用run方法
				BaseApplication.getHandler().postDelayed(this, 2000); //延迟1秒钟调用
			}
			
		}
		public void start(){
			if (!flag) {
				
				flag = true;
				BaseApplication.getHandler().postDelayed(this, 2000);
			}
		}
		public void stop() {
			if (flag) {
				flag = false;
			}
		}
		
	}
}
