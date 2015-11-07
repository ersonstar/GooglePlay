package com.iterson.GooglePlay.activity;

import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.fragment.BaseFragment;
import com.iterson.GooglePlay.fragment.FragmentFactory;
import com.iterson.GooglePlay.holder.MenuHolder;
import com.iterson.GooglePlay.utils.UIUtils;

public class MainActivity extends BaseActivity implements OnQueryTextListener {
	private DrawerLayout mDrawerLayout;
	private String[] items;
	private ViewPager mViewPager;
	private PagerTabStrip mPagerTabStrip;
	private ActionBarDrawerToggle toggle;
	private FrameLayout fl_drawer;
	private RelativeLayout photo_layout;

	@Override
	protected void init() {
		items = UIUtils.getStringArray(R.array.tab_names);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		//添加抽屉
		fl_drawer = (FrameLayout) findViewById(R.id.fl_drawer);
		MenuHolder holder = new MenuHolder();
		View v = holder.getContentView();
		fl_drawer.addView(v);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
		mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
		mPagerTabStrip.setTextColor(Color.BLACK);
		mPagerTabStrip.setBackgroundColor(Color.WHITE);
		// 设置指示器的颜色
		mPagerTabStrip.setTabIndicatorColor(UIUtils
				.getColor(R.color.indicatorcolor));
		mViewPager = (ViewPager) findViewById(R.id.vp);
		mViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) { // 当切换条目的时候调用条目对应Fragment的show方法
						super.onPageSelected(position);
						BaseFragment createFrament = FragmentFactory
								.createFrament(position);
						createFrament.show();// 请求服务器 加载数据
					}

				});
	}

	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		// actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		// 参数1 ActionBar所在的acitivity 参数2 控制的抽屉
		// 参数3 显示图片
		// 参数4 参数5 描述信息
		toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer_am, R.string.open_drawer,
				R.string.close_drawer) {
			// 监听抽屉关闭
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			// 监听抽屉打开
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

		};

		toggle.syncState();// 和actionBar 抽屉 同步状态
		// 因为toggle 实现了DrawerListener 所在可以把它当做DrawerListener 用
		mDrawerLayout.setDrawerListener(toggle);
	}

	private class MainAdapter extends FragmentStatePagerAdapter {

		public MainAdapter(FragmentManager fm) {
			super(fm);
		}

		// 每个条目 显示的Fragment
		@Override
		public Fragment getItem(int postion) {
			return FragmentFactory.createFrament(postion);
		}

		// 返回条目的数量
		@Override
		public int getCount() {
			return items.length;
		}

		// 指定每个条目标题的内容
		@Override
		public CharSequence getPageTitle(int position) {
			return items[position];
		}

	}

	// 菜单的点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_search) {
			// 搜索
			Toast.makeText(getApplicationContext(), "搜索", 0).show();
			return true;
		}
		// 由开关首先去处理事件
		return toggle.onOptionsItemSelected(item)
				|| super.onOptionsItemSelected(item);
	}

	// 创建菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		// android.os.Build.VERSION.SDK_INT 当前运行环境的版本号
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			SearchView searchView = (SearchView) menu.findItem(
					R.id.action_search).getActionView();
			searchView.setOnQueryTextListener(this);// null.方法()
		} else {
			// 通过别的方式去实现搜索
		}
		return true;
	}

	// 当搜索的文本提交的时候
	@Override
	public boolean onQueryTextSubmit(String query) {
		Toast.makeText(getApplicationContext(), query, 0).show();
		return true;
	}

	// 当搜索的文本改变的时候调用
	@Override
	public boolean onQueryTextChange(String newText) {
		System.out.println(newText);
		return true;
	}

}
