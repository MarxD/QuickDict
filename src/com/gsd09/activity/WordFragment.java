package com.gsd09.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.gsd09.QuickDict.R;
import com.gsd09.biz.GetTransBiz;
import com.gsd09.biz.NewWordsBiz;
import com.gsd09.entity.Word;
import com.gsd09.util.ExceptionUtil;
import com.gsd09.util.LogUtil;

public class WordFragment extends BaseFragment {

	private View view;// 布局
	private EditText et_sourceword;// 输入框
	private String myname = "翻译";
	private Button btn_addYouDao, btn_addBaiDu, btn_addLocal;// 吕仲熙
																// 2016.01.23
																// 声明控件
	List<Word> words = new ArrayList<Word>();// 储存获得的单词数据
	List<TextView> tvs = new ArrayList<TextView>();
	private TextView youdao_source;
	private TextView youdao_trans;
	private TextView baidu_source;
	private TextView baidu_trans;
	private TextView local_source;
	private TextView local_trans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_word, container, false);
		et_sourceword = (EditText) view.findViewById(R.id.et_sourceword);
		setviews();
		setlistener();
		return view;
	}

	private void setviews() {
		youdao_source = (TextView) view.findViewById(R.id.tv_ch_youdao);
		youdao_trans = (TextView) view.findViewById(R.id.tv_en_youdao);
		baidu_source = (TextView) view.findViewById(R.id.tv_ch_baidu);
		baidu_trans = (TextView) view.findViewById(R.id.tv_en_baidu);
		local_source = (TextView) view.findViewById(R.id.tv_ch_local);
		local_trans = (TextView) view.findViewById(R.id.tv_en_local);
		btn_addYouDao = (Button) view.findViewById(R.id.btn_addYouDao);// 吕仲熙
																		// 2016.01.23
																		// 初始化控件
		btn_addBaiDu = (Button) view.findViewById(R.id.btn_addBaiDu);// 吕仲熙
																		// 2016.01.23
																		// 初始化控件
		btn_addLocal = (Button) view.findViewById(R.id.btn_addLocal);// 吕仲熙
																		// 2016.01.23
																		// 初始化控件

		tvs.add(youdao_source);
		tvs.add(youdao_trans);
		tvs.add(baidu_source);
		tvs.add(baidu_trans);
		tvs.add(local_source);
		tvs.add(local_trans);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		tvs.clear();
	}

	/**
	 * 设置输入框的搜索监听
	 */
	private void setlistener() {
		et_sourceword.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					search();
					et_sourceword.getText().clear();
					return true;
				}
				return false;
			}
		});
		// 吕仲熙 2016.01.23 设置btn_addYouDao按钮监听器
		btn_addYouDao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String englishnewwords = youdao_source.getText().toString();
					Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
					Matcher m = p.matcher(englishnewwords);
					while (m.matches()) {
						Toast.makeText(getActivity(), "仅支持 英-汉 生词格式", 1).show();
						return;
					}
					String chinesenewtranslation = youdao_trans.getText()
							.toString();
					long created = System.currentTimeMillis();
					ContentValues cv = new ContentValues();
					cv.put("englishnewwords", englishnewwords);
					cv.put("chinesenewtranslation", chinesenewtranslation);
					cv.put("librarysource", "youdao");
					cv.put("created", created);
					new NewWordsBiz(WordFragment.this, getActivity())
							.insertOnWordFragment("newwordstable", cv);
				} catch (Exception e) {
					ExceptionUtil.handleException(e);
				} finally {
				}
			}
		});

		// 吕仲熙 2016.01.23 设置btn_addYouDao按钮监听器
		btn_addBaiDu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String englishnewwords = baidu_source.getText().toString();
					Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
					Matcher m = p.matcher(englishnewwords);
					while (m.matches()) {
						Toast.makeText(getActivity(), "仅支持 英-汉 生词格式", 1).show();
						return;
					}
					String chinesenewtranslation = baidu_trans.getText()
							.toString();
					long created = System.currentTimeMillis();
					ContentValues cv = new ContentValues();
					cv.put("englishnewwords", englishnewwords);
					cv.put("chinesenewtranslation", chinesenewtranslation);
					cv.put("librarysource", "baidu");
					cv.put("created", created);
					new NewWordsBiz(WordFragment.this, getActivity())
							.insertOnWordFragment("newwordstable", cv);
				} catch (Exception e) {
					ExceptionUtil.handleException(e);
				} finally {
				}
			}
		});

		// 吕仲熙 2016.01.23 设置btn_addYouDao按钮监听器
		btn_addLocal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String englishnewwords = local_source.getText().toString();
					Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
					Matcher m = p.matcher(englishnewwords);
					while (m.matches()) {
						Toast.makeText(getActivity(), "仅支持 英-汉 生词格式", 1).show();
						return;
					}
					String chinesenewtranslation = local_trans.getText()
							.toString();
					long created = System.currentTimeMillis();
					ContentValues cv = new ContentValues();
					cv.put("englishnewwords", englishnewwords);
					cv.put("chinesenewtranslation", chinesenewtranslation);
					cv.put("librarysource", "local");
					cv.put("created", created);
					new NewWordsBiz(WordFragment.this, getActivity())
							.insertOnWordFragment("newwordstable", cv);
				} catch (Exception e) {
					ExceptionUtil.handleException(e);
				} finally {
				}
			}
		});

	}

	/**
	 * 发送请求，获得单词数据
	 */
	private void search() {

		GetTransBiz gtb = new GetTransBiz(tvs,getActivity());
		gtb.execute(et_sourceword.getText().toString());

	}

	@Override
	public String getmyname() {

		return myname;
	}

}
