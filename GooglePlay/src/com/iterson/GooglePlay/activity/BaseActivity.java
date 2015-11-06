package com.iterson.GooglePlay.activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
	//  管理所有打开的activity
	//  static  和对象没有关系  和类有关系
	static List<BaseActivity> mActivities=new LinkedList<BaseActivity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("BaseActivity");
		synchronized (mActivities) {
			mActivities.add(this);
		}
		init();
		initView();
		initActionBar();
	}
	/**
	 * 退出程序
	 */
	public void killAll(){
		List<BaseActivity> clone;// 复制了集合
		synchronized (mActivities) {
			clone = new ArrayList<BaseActivity>(mActivities);
		}
	
		for(BaseActivity activity:clone){
			activity.finish();
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivities) {
			// 当activity退出的时候 让它在集合中 移出
			mActivities.remove(this);
		}
	}
	/**
	 * 初始化
	 */
	protected void init() {
		
	}
	/**
	 * 初始化view界面
	 */
	protected void initView() {
		
	}
	/**
	 * 初始化ActionBar
	 */
	protected void initActionBar() {
		
	}



}

