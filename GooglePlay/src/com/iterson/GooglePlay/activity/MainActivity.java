package com.iterson.GooglePlay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.fragment.AppFragment;
import com.iterson.GooglePlay.fragment.HomeFragment;

public class MainActivity extends ActionBarActivity implements
		OnQueryTextListener {
	private ViewPager mViewPager;
	private String[] items;
	private PagerTabStrip mPagerTabStrip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
		mPagerTabStrip.setTextColor(Color.BLACK);
		mPagerTabStrip.setBackgroundColor(Color.WHITE);
		mPagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.indicatorcolor));
		
		// 加载tab的名字，value，string-array
		items = getResources().getStringArray(R.array.tab_names);
		mViewPager = (ViewPager) findViewById(R.id.vp);
		mViewPager.setAdapter(new MainAdpater(getSupportFragmentManager()));
		// 设置一个viewpage的改变监听
//		mViewPager
//				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//					@Override
//					public void onPageSelected(int position) {
//						getActionBar().setSelectedNavigationItem(position);
//					}
//
//					@Override
//					public void onPageScrollStateChanged(int arg0) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void onPageScrolled(int arg0, float arg1, int arg2) {
//						// TODO Auto-generated method stub
//						
//					}
//				});

	}

	/**
	 * ViewPage的adapter
	 */
	private class MainAdpater extends FragmentStatePagerAdapter {

		public MainAdpater(FragmentManager fm) {
			super(fm);
		}

		// 每个条目显示的fragment
		@Override
		public Fragment getItem(int position) {
			if (position % 2 == 0) {
				return new HomeFragment();
			} else {
				return new AppFragment();
			}
		}

		// 返回条目数量
		@Override
		public int getCount() {
			return items.length;
		}
		//指定每个标题的内容
		@Override
		public CharSequence getPageTitle(int position) {
			return items[position];
		}
		

	}

	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setOnQueryTextListener(this);// this 让这个类继承，然后添加为实现的方法
		return true;

	}

	/**
	 * 菜单点击事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_search) {
			// 搜索

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 当文本改变的时候
	 */
	@Override
	public boolean onQueryTextChange(String newText) {
		System.out.println(newText);
		return true;
	}

	/**
	 * 当文本提交的时候
	 */
	@Override
	public boolean onQueryTextSubmit(String query) {
		Toast.makeText(getApplicationContext(), query, 1).show();
		return true;
	}
}
