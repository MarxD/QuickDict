package com.gsd09.activity;

import com.gsd09.QuickDict.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ODOSFragment extends BaseFragment 
{
	String myname = "每日一句";
	View view;

	/**
	 *声明图片显示控件
	 */
	private ImageView ivODOS;
	/**
	 * 声明中文翻译显示控件
	 */
	private TextView tvODOSChinese;
	/**
	 * 声明英文翻译显示控件
	 */
	private TextView tvODOSEnglish;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_odos, container,false);
		return view;		
	}
	@Override
	public String getmyname() {
		// TODO Auto-generated method stub
		return myname;
	}

}
