package com.gsd09.activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gsd09.QuickDict.R;
import com.gsd09.adapter.NewWordsAdapter;
import com.gsd09.biz.NewWordsBiz;
import com.gsd09.entity.NewWords;
import com.gsd09.util.ExceptionUtil;
import com.gsd09.util.LogUtil;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/** 生词本页面 */
public class NewWordsFragment extends BaseFragment {
	View view;
	private String myname = "生词本";
	private ListView lv_newWords;
	private EditText etPutInEnglishWords;
	private EditText etPutInChineseTranslation;
	private Button btAdd;
	private Button btReset;
	private Button btCancle;
	private Button btDeleteAll;

	private MainActivity activity;
	private AlertDialog dialog;
	private NewWordsAdapter newWordsAdapter;
	public static NewWordsFragment instance;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_newwords, container, false);
		// 随该fragment创建,就立即执行查询所有单词
		new NewWordsBiz(this, getActivity()).queryAllNewWords();
		activity = (MainActivity) getActivity();
		instance = this;
		setViews();
		setListeners();
		return view;
	}

	/** 重写继承父类的方法,获取当前fragment标题名 */
	@Override
	public String getmyname() {
		return myname;
	}

	/** 初始化控件 */
	private void setViews() {
		lv_newWords = (ListView) view.findViewById(R.id.lv_newWords);
		btDeleteAll = (Button) view.findViewById(R.id.btDeleteAll);

		// 一显示这个fragment立即发送广播,请求显示btn_addNewWord按钮
		Intent intent = new Intent("DISPLAY_TOP_RIGHT_BUTTON");
		getActivity().sendBroadcast(intent);
	}

	// 通过前面new NewWordsBiz(this,getActivity()).execute();异步加载后,返回NewWords集合
	/** 设置适配器,展现listView */
	public void setAdapter(List<NewWords> newWordses) {
		newWordsAdapter = new NewWordsAdapter(getActivity(), newWordses, this);
		lv_newWords.setAdapter(newWordsAdapter);
	}

	/** 设置监听器 */
	public void setListeners() {
		/** 点击"全部删除"按钮 */
		btDeleteAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(getActivity()).setTitle("这么多单词,记住了么")
						.setIcon(R.drawable.do_you_remember_all_of_these)
						.setPositiveButton("记住了", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						new NewWordsBiz(NewWordsFragment.this, getActivity()).deleteAllNewWords();
					}
				}).setNegativeButton("还没呢", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();

			}
		});

	}

	/** 弹出窗口供输入生词 */
	public void showCustomNewWordsInputView() {
		try {
			LogUtil.i("自定义生词弹窗", "弹出");
			// 弹出AlertDialog
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			dialog = builder.create();
			// 在show()方法调用之前，添加一个空的EditText，由于是自定义的AlertDialog，有我们指定的布局，
			// 所以设置这个不会影响我们的功能，这样就可以弹出入法了
			dialog.setView(new EditText(getActivity()));
			dialog.show();
			// 考虑到调用键盘时dialog界面可能未加载完成,导致editText可能仍为空,故加上200毫秒延迟任务
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					setKeyBoard();
				}
			}, 200);
			setKeyBoard();
			// 获取dialog的窗口
			Window window = dialog.getWindow();
			View v = View.inflate(getActivity(), R.layout.add_custom_new_words, null);
			// 获取v对象中的控件
			etPutInEnglishWords = (EditText) v.findViewById(R.id.etPutInEnglishWords);
			etPutInChineseTranslation = (EditText) v.findViewById(R.id.etPutInChineseTranslation);
			btAdd = (Button) v.findViewById(R.id.btAdd);
			btReset = (Button) v.findViewById(R.id.btReset);
			btCancle = (Button) v.findViewById(R.id.btCancle);

			// 点击添加,把输入框内的文本添加到数据库
			btAdd.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String englishnewwords = etPutInEnglishWords.getText().toString();
					String chinesenewtranslation = etPutInChineseTranslation.getText().toString();
					if (englishnewwords.equals("")) {
						etPutInEnglishWords.setFocusableInTouchMode(true);
						etPutInEnglishWords.setFocusable(true);
						etPutInEnglishWords.requestFocus();
						etPutInEnglishWords.setError(Html.fromHtml("<font color=#E10979>内容不得为空</font>"));
						return;
					}
					if (chinesenewtranslation.equals("")) {
						etPutInChineseTranslation.setFocusableInTouchMode(true);
						etPutInChineseTranslation.setFocusable(true);
						etPutInChineseTranslation.requestFocus();
						etPutInChineseTranslation.setError(Html.fromHtml("<font color=#E10979>内容不得为空</font>"));
						return;
					}
					Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
					Matcher m = p.matcher(englishnewwords);
					if (m.matches()) {
						etPutInEnglishWords.setFocusableInTouchMode(true);
						etPutInEnglishWords.setFocusable(true);
						etPutInEnglishWords.requestFocus();
						etPutInEnglishWords
								.setError(Html.fromHtml("<font color=#E10979>格式不正确<br/>应输入英文 如:apple</font>"));
						return;
					}

					long created = System.currentTimeMillis();
					ContentValues cv = new ContentValues();
					cv.put("englishnewwords", englishnewwords);
					cv.put("chinesenewtranslation", chinesenewtranslation);
					cv.put("librarysource", "custom");
					cv.put("created", created);
					new NewWordsBiz(NewWordsFragment.this, getActivity()).insert("newwordstable", cv);
				}
			});

			// 点击重置,把输入框内的文本清空
			btReset.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					etPutInChineseTranslation.getText().clear();
					etPutInEnglishWords.getText().clear();
				}
			});

			// 点击取消,关闭弹窗
			btCancle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismissDialog();
				}
			});
			window.setContentView(v);
			// 点击控件外部位置不可关闭dialog
			dialog.setCanceledOnTouchOutside(false);
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {

		}
	}

	/** 关闭dialog */
	public void dismissDialog() {
		dialog.dismiss();
	}

	/** 设置键盘,在弹窗出现时自动弹出键盘 */
	private void setKeyBoard() {
		try {
			if (etPutInEnglishWords != null) {
				// 设置可获得焦点
				etPutInEnglishWords.setFocusable(true);
				etPutInEnglishWords.setFocusableInTouchMode(true);
				// 请求获得焦点
				etPutInEnglishWords.requestFocus();
				// 调用系统输入法
				InputMethodManager inputMethodManager = (InputMethodManager) etPutInEnglishWords.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(etPutInEnglishWords, 0);
			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {

		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			// 该fragment一旦销毁立即发送广播,请求隐藏btn_addNewWord按钮
			Intent intent = new Intent("UNDISPLAY_TOP_RIGHT_BUTTON");
			getActivity().sendBroadcast(intent);
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {

		}
	}

}
