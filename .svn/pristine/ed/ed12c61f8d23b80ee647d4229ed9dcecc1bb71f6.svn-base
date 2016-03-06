package com.gsd09.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gsd09.QuickDict.R;
import com.gsd09.util.DataBaseSelector;
import com.gsd09.util.ExceptionUtil;
import com.gsd09.util.LogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	public Button btn_addNewWord;
	private List<BaseFragment> mFragments = new ArrayList<BaseFragment>();
	TextView tv_title;
	Fragment[] fragMentArray = new Fragment[4];
	private MessageReceiver messageReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		new DataBaseSelector(this).selectNewWordsDataBase();// 吕仲熙 2016-01-19
															// 启动程序立即打开数据库
		registBroadCaster();// 吕仲熙 2016-01-20 启动程序立即注册广播
		initview();
		initLeftMenu();
		switchfragment(-1);// 设置首页

	}

	public void display() {
		btn_addNewWord.setVisibility(View.VISIBLE);
	}

	public void undisplay() {
		btn_addNewWord.setVisibility(View.GONE);
	}

	/**
	 * 初始化空间
	 * */
	private void initview() {
		WordFragment wordfragment = new WordFragment();
		NewWordsFragment newwordsfragment = new NewWordsFragment();
		ODOSFragment odosfragment = new ODOSFragment();
		tv_title = (TextView) findViewById(R.id.tv_title);
		mFragments.add(wordfragment);
		mFragments.add(newwordsfragment);
		mFragments.add(odosfragment);
		btn_addNewWord = (Button) findViewById(R.id.id_iv_right);
	}

	/**
	 * 初始化侧滑菜单
	 * */
	private void initLeftMenu()// 不用做修改
	{
		Fragment leftMenuFragment = new MenuLeftFragment();
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// menu.setBehindWidth()
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		// menu.setBehindScrollScale(1.0f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);

	}

	/**
	 * 传入切换的帧布局下标，默认为-1
	 * 
	 * @param position
	 */
	public void switchfragment(int position) {
		int currentposition = position != -1 ? position : 0;

		FragmentManager fgm = getSupportFragmentManager();
		fgm.beginTransaction()
				.replace(R.id.main_content_frame,
						mFragments.get(currentposition)).commit();
		getSlidingMenu().showContent();
		tv_title.setText(mFragments.get(currentposition).getmyname());

	}

	/**
	 * 显示侧滑菜单
	 * 
	 * @param view
	 */
	public void showLeftMenu(View view) {
		getSlidingMenu().showMenu();
	}

	// 吕仲熙2016.01.20
	/** 点击添加按钮,自定义中英文翻译输入后,存入到数据库中 */
	public void addNewWords(View view) {
		try {
			NewWordsFragment.instance.showCustomNewWordsInputView();
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {

		}
	}

	// 吕仲熙 2016.01.20
	/** 注册广播 */
	private void registBroadCaster() {
		try {
			messageReceiver = new MessageReceiver();
			IntentFilter displayFilter = new IntentFilter();
			displayFilter.addAction("DISPLAY_TOP_RIGHT_BUTTON");
			this.registerReceiver(messageReceiver, displayFilter);
			IntentFilter undisplayFilter = new IntentFilter();
			undisplayFilter.addAction("UNDISPLAY_TOP_RIGHT_BUTTON");
			this.registerReceiver(messageReceiver, undisplayFilter);
			LogUtil.i("广播", "注册广播");
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {
		}
	}

	// 吕仲熙 2016.01.20
	/** 广播接收器,通过接收广播判断是否显示btn_addNewWord按钮 */
	class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				String action = intent.getAction();
				if (action.equals("DISPLAY_TOP_RIGHT_BUTTON")) {// 显示按钮
					LogUtil.i("广播", "展示按钮广播");
					btn_addNewWord.setVisibility(View.VISIBLE);
				}
				if (action.equals("UNDISPLAY_TOP_RIGHT_BUTTON")) {// 隐藏按钮
					LogUtil.i("广播", "取消按钮广播");
					btn_addNewWord.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			} finally {
			}
		}
	}

	// 吕仲熙2016.01.20
	/** 程序销毁时注销广播 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			unregisterReceiver(messageReceiver);
			LogUtil.i("广播", "注销广播");
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {
		}
	}
}
