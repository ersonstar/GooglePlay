package com.iterson.GooglePlay.fragment;

import java.util.HashMap;
import java.util.Map;

import android.util.SparseArray;

/**
 * fragmen工厂
 * 
 * @author Yang
 * 
 */
public class FragmentFactory {  
	//  如果HashMap 泛型 key 是Interger  可以用SparseArray  替代HashMap
	static SparseArray<BaseFragment> fragments=new SparseArray<BaseFragment>();
	
	public static BaseFragment createFrament(int postion) {
		//  每次进来首先在map集合中取出来一个Frament
		BaseFragment fragment = fragments.get(postion);
		if (fragment == null) {
			switch (postion) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new CategoryFragment();
				break;
			case 5:
				fragment = new HotFragment();
				break;
			}
			//  当创建的Fragment 存放到map集合中
			fragments.put(postion, fragment);
			
		}
		return fragment;
	}
}