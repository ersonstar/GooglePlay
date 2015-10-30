package com.iterson.GooglePlay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnQueryTextListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void click(View v){
		Intent intent = new Intent(this,DetailActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(this);//this 让这个类继承，然后添加为实现的方法
		return true;
		
	}
	/**
	 * 菜单点击事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==R.id.action_search) {
			//搜索
			
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
