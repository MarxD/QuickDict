package com.gsd09.activity;

import com.gsd09.QuickDict.R;
import com.gsd09.biz.OneDayOneSentenceBiz;
import com.gsd09.util.LogUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ODOSFragment extends BaseFragment 
{
	String myname = "ÿ��һ��";
	/**
	 * ������ʾ����
	 */
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_odos, container,false);
		LogUtil.i("ODOS��ȡ��Ϣ��", "View�е�ǰ�߳����ƣ�" + Thread.currentThread().getName());
		OneDayOneSentenceBiz biz = new OneDayOneSentenceBiz(view,this.getActivity());
		biz.getOdosInfo(null);
		return view;
	}
	@Override
	public String getmyname() {
		return myname;
	}
}
