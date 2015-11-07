package com.iterson.GooglePlay.adpter;

import java.util.List;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.iterson.GooglePlay.holder.BaseHolder;
import com.iterson.GooglePlay.holder.MoreHolder;
import com.iterson.GooglePlay.manager.ThreadManager;
import com.iterson.GooglePlay.utils.UIUtils;

public abstract class DefaultAdapter<T> extends BaseAdapter implements OnItemClickListener {
	private List<T> datas;
	public static final int ITEM_MORE = 0;
	public static final int ITEM_DEFAULT = 1;
	private ListView lv;
	public DefaultAdapter(List<T> datas,ListView lv) {
		this.datas = datas;
		this.lv = lv;
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//得到顶部view条目的数量
		int headerViewsCount = lv.getHeaderViewsCount();
		position = position - headerViewsCount;
		Toast.makeText(UIUtils.getContext(), "点击了"+position, 0).show();
		onInnerItemClick(position);
	}

	protected void onInnerItemClick(int position){
		Toast.makeText(UIUtils.getContext(), "点击了"+position, 0).show();
	}

	private Class<? extends BaseHolder> instance;

	public DefaultAdapter(List<T> datas, Class<? extends BaseHolder> instance) {
		this.datas = datas;
		this.instance = instance;
		// HomeAdapter 查询map集合或者配置文件 找到HomeHolder
		// String name=this.getClass().getSimpleName();
	}

	@Override
	public int getCount() {
		return datas.size() + 1; // 多了一个条目 加载更多
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 返回对应每个位置 是什么类型
	@Override
	public int getItemViewType(int position) {
		if (position == datas.size()) {
			return ITEM_MORE; // 当前加载更多类型
		}
		return ITEM_DEFAULT; // 默认的类型
	}

	// 控制ListView 条目类型的数量 默认是1
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1; // 有两种类型
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder = null;
		switch (getItemViewType(position)) {
		case ITEM_MORE:
			if (convertView == null) {
				holder = getMoreHolder();
			} else {
				holder = (BaseHolder) convertView.getTag();
			}
			break;
		case ITEM_DEFAULT:
			if (convertView == null) {
				holder = getHolder(); // 相当于调用了Holder的构造方法
			} else {
				// 由于view对象Holder里面创建的 最终通过getVIew方法返回回去了,
				// 代表当前界面显示的view对象就是Holder里面创建
				// converView 就是复用的view对象 也是Holder创建的 可以通过converView.getTag
				// 获取到holder.
				holder = (BaseHolder<T>) convertView.getTag();
			}
			T info = datas.get(position);
			holder.refreshView(info);

			break;
		}
		// 当返回view对象的时候 该view才可见
		// 当调用MoreHolder.getContentView的时候 这时候 才能看到 加载更多界面
		// 当这个时候 才能处理加载更多的业务逻辑
		return holder.getContentView();
	}
	private MoreHolder holder;
	private MoreHolder getMoreHolder() {
		holder=new MoreHolder(this);
		return holder;
	}

	public abstract BaseHolder<T> getHolder();

	/**
	 * 加载更多
	 */
	public void loadMore() {
		// 联网请求服务器 额外的数据
		ThreadManager.getInstance().executeLongTask(new Runnable() {

			@Override
			public void run() {
				SystemClock.sleep(1800);
				final List<T> newDatas = onLoad();
				UIUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (newDatas == null) {
							// 加载失败了 修改MoreHolder的界面
							holder.refreshView(MoreHolder.LOAD_ERROR);
						} else {
							if (newDatas.size() == 0) {
								// 没有额外数据了
								// 修改MoreHolder的界面
								holder.refreshView(MoreHolder.HAS_NO_MORE);// 没有额外数据了
							} else {
								holder.refreshView(MoreHolder.HAS_MORE);
								// 修改界面
								datas.addAll(newDatas);
								notifyDataSetChanged();// 更新界面
							}
						}

					}
				});
			}
		});
	}

	/**
	 * 加载更多的数据 交给子类实现 因为每个界面 数据不一样
	 */
	protected abstract List<T> onLoad();

}
