package com.iterson.GooglePlay.fragment;

import java.util.List;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iterson.GooglePlay.R;
import com.iterson.GooglePlay.adpter.DefaultAdapter;
import com.iterson.GooglePlay.domain.SubjectInfo;
import com.iterson.GooglePlay.holder.BaseHolder;
import com.iterson.GooglePlay.manager.HttpHelper;
import com.iterson.GooglePlay.protocol.SubjectProtocol;
import com.iterson.GooglePlay.ui.BaseListView;
import com.iterson.GooglePlay.ui.LoadingPage.LoadResult;
import com.iterson.GooglePlay.utils.UIUtils;



/**
 * 专题
 * @author Yang
 *
 */
public class SubjectFragment extends BaseFragment {

	private List<SubjectInfo> datas;
	@Override
	public View createSuccessView() {
//		TextView tv=new TextView(UIUtils.getContext());
//		tv.setText(datas.size()+"");//  如果给setText(int)  默认把int 当做资源id 
//		tv.setTextSize(30);
		BaseListView lv=new BaseListView(UIUtils.getContext());
		lv.setAdapter(new SubjectAdapter(datas));
		
		return lv;
	}
	private class SubjectAdapter extends DefaultAdapter<SubjectInfo>{

		public SubjectAdapter(List<SubjectInfo> datas) {
			super(datas);
		}
		@Override
		public BaseHolder<SubjectInfo> getHolder() {
			return new SubjectHolder();
		}
		@Override
		protected List<SubjectInfo> onLoad() {
			SubjectProtocol protocol = new SubjectProtocol();
			List<SubjectInfo> newDatas = protocol.load(datas.size());
			return newDatas;
		}
	}
	//  把getView方法中 和holder相关的代码 拆分到Holder中 
	class SubjectHolder  extends BaseHolder<SubjectInfo>{
		// holder里面 持有了view对象 
		TextView item_txt;
		ImageView item_icon;
		
		public View initView() {
			View contentView=UIUtils.inflate(R.layout.list_subject); 
			this.item_icon=(ImageView) contentView.findViewById(R.id.item_icon);
			this.item_txt=(TextView) contentView.findViewById(R.id.item_txt);
			return contentView;
		}
		public void refreshView(SubjectInfo info){
			this.item_txt.setText(info.getDes());
			
			// 参数 显示图片的控件  参数2 图片服务器的地址
			bitmapUtils.display(this.item_icon, HttpHelper.SERVER_URL+"image?name="+info.getUrl());
		}
		
	}
	
	// 请求服务器
	@Override
	public LoadResult onLoad() {
		SubjectProtocol protocol=new SubjectProtocol();
		datas = protocol.load(0);
		return checkDatas(datas);
	}
	

}
