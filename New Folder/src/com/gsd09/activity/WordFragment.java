package com.gsd09.activity;
import java.util.ArrayList;
import java.util.List;

import com.gsd09.QuickDict.R;
import com.gsd09.biz.GetTransBiz;
import com.gsd09.entity.Word;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;


public class WordFragment extends BaseFragment
{
	
	
	private View view;//布局
	private EditText et_sourceword;//输入框
	private String myname="翻译";
	List<Word> words =new  ArrayList<Word>();//储存获得的单词数据
	List<TextView> tvs = new ArrayList<TextView>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_word, container,false);
		et_sourceword = (EditText)view.findViewById(R.id.et_sourceword);
		setviews();
		setlistener();
		return view;
		
	}
	
	private void setviews() {
		TextView youdao_source = (TextView)view.findViewById(R.id.tv_ch_youdao);
		TextView youdao_trans = (TextView)view.findViewById(R.id.tv_en_youdao);
		TextView baidu_source = (TextView)view.findViewById(R.id.tv_ch_baidu);
		TextView baidu_trans = (TextView)view.findViewById(R.id.tv_en_baidu);
		TextView local_source = (TextView)view.findViewById(R.id.tv_ch_local);
		TextView local_trans = (TextView)view.findViewById(R.id.tv_en_local);
		tvs.add(youdao_source);
		tvs.add(youdao_trans);
		tvs.add(baidu_source);
		tvs.add(baidu_trans);
		tvs.add(local_source);
		tvs.add(local_trans);
		
	}

	/**
	 * 设置输入框的搜索监听
	 */
	private void setlistener() {
		et_sourceword.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId==EditorInfo.IME_ACTION_SEARCH)
				{
					search();
					et_sourceword.getText().clear();
					return true;
				}
				return false;
			}


		});
		
	}
	
	/**
	 * 发送请求，获得单词数据
	 */
	private void search() 
	{
		
		GetTransBiz gtb = new GetTransBiz(tvs);
		gtb.execute(et_sourceword.getText().toString());
			
	}
	

	@Override
	public String getmyname() {
		
		return myname;
	}
	
	
	
}
