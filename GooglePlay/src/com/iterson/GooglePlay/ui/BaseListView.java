package com.iterson.GooglePlay.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.utils.UIUtils;
/**
 * 对listView 扩充
 * 
 *
 */
public class BaseListView extends ListView {

	public BaseListView(Context context) {
		super(context);
		init();
	}

	private void init() {
//		setSelector  点击显示的颜色
//		setCacheColorHint  拖拽的颜色
//		setDivider  每个条目的间隔
		this.setSelector(R.drawable.nothing);
//		lv.setCacheColorHint(Color.TRANSPARENT);
		this.setDivider(UIUtils.getResource().getDrawable(R.drawable.nothing));
	}

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
}

