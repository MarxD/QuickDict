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
	String myname = "ÿ��һ��";
	View view;

	/**
	 *����ͼƬ��ʾ�ؼ�
	 */
	private ImageView ivODOS;
	/**
	 * �������ķ�����ʾ�ؼ�
	 */
	private TextView tvODOSChinese;
	/**
	 * ����Ӣ�ķ�����ʾ�ؼ�
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
