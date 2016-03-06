package com.gsd09.activity;

import java.util.Arrays;
import java.util.List;
import com.gsd09.QuickDict.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
//此类不用修改
public class MenuLeftFragment extends Fragment 
{
	private View mView;
	private ListView mCategories;
	private List<String> mDatas = Arrays
			.asList("翻译", "生词本", "每日一句","设置");
	private ListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if (mView == null)
		{
			initView(inflater, container);
			setonlistener();
		}
		return mView;
	}

	private void setonlistener() 
	{
		mCategories.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				MainActivity ma = (MainActivity)getActivity();
				ma.switchfragment(position);
				
			}
			
		}
		);
		
	}
	

	private void initView(LayoutInflater inflater, ViewGroup container)
	{
		mView = inflater.inflate(R.layout.left_menu, container, false);
		mCategories = (ListView) mView
				.findViewById(R.id.id_listview_categories);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mDatas);
		mCategories.setAdapter(mAdapter);
	}
}
