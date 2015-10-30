package com.iterson.GooglePlay.activity;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.R.array;
import com.iterson.GooglePlay.R.id;
import com.iterson.GooglePlay.R.layout;
import com.iterson.GooglePlay.R.menu;
import com.iterson.GooglePlay.fragment.AppFragment;
import com.iterson.GooglePlay.fragment.HomeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		OnQueryTextListener {
	private ViewPager mViewPager;
	private String[] items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 加载tab的名字，value，string-array
		items = getResources().getStringArray(R.array.tab_names);
		mViewPager = (ViewPager) findViewById(R.id.vp);
		mViewPager.setAdapter(new MainAdpater(getSupportFragmentManager()));
		// 添加tabs
		ActionBar actionBar = getSupportActionBar();
		// 增加actionbar的滑行模式
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (final String string : items) {
			Tab tab = actionBar.newTab().setText(string)
					.setTabListener(new TabListener() {
						// 当没有被选中
						@Override
						public void onTabUnselected(Tab arg0,
								FragmentTransaction arg1) {
							
						}

						// 当被选中
						@Override
						public void onTabSelected(Tab tab,
								FragmentTransaction arg1) {
							mViewPager.setCurrentItem(tab.getPosition());
							
						}

						// 当重新被选中
						@Override
						public void onTabReselected(Tab arg0,
								FragmentTransaction arg1) {
						}
					});
			actionBar.addTab(tab);
		}
		// 设置一个viewpage的改变监听
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						getActionBar().setSelectedNavigationItem(position);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

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
