package com.iterson.GooglePlay.activity;

import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.fragment.BaseFragment;
import com.iterson.GooglePlay.fragment.FragmentFactory;
import com.iterson.GooglePlay.utils.UIUtils;

public class MainActivity extends BaseActivity implements OnQueryTextListener {
	private ViewPager mViewPager;
	private String[] items;
	private PagerTabStrip mPagerTabStrip;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle toggle;

	@Override
	protected void init() {
		// 加载tab的名字，value，string-array
		items = UIUtils.getStringArray(R.array.tab_names);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
		mPagerTabStrip.setTextColor(Color.BLACK);
		mPagerTabStrip.setBackgroundColor(Color.WHITE);
		// 设置指示器颜色
		mPagerTabStrip.setTabIndicatorColor(getResources().getColor(
				R.color.indicatorcolor));
		mViewPager = (ViewPager) findViewById(R.id.vp);
		mViewPager.setAdapter(new MainAdpater(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position) {//当切换条目的时候，调用条目对呀fragment 的show方法
				super.onPageSelected(position);
				BaseFragment createFrament = FragmentFactory.createFrament(position);
				createFrament.show();
			}
			
		});
	}

	@Override
	protected void initActionBar() {

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);// 显示actionbar
		// 参1是当前activity 参2目标drawerlayout
		// 参3图片
		// 参4 5 是字符串，但是参数只能是int类型，所以放在string引用
		toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer_am, R.string.open_drawer,
				R.string.close_drawer) {
			// 复写抽屉打开和关闭方法，同时也是监听
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

		};
		toggle.syncState();// 同步状态
		mDrawerLayout.setDrawerListener(toggle);// toggle实现了drawerlistener接口，所以能直接放进去

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
			//创建了一个工厂 方便使用
			Fragment createFrament = FragmentFactory.createFrament(position);
			return createFrament;
		}

		// 返回条目数量
		@Override
		public int getCount() {
			return items.length;
		}

		// 指定每个标题的内容
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
		if (item.getItemId() == R.id.action_search) {// 如果点击的是搜索

			return true;
		}
		// 由开关首先去处理事件
		return toggle.onOptionsItemSelected(item)
				|| super.onOptionsItemSelected(item);
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
