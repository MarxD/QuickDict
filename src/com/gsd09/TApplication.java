package com.gsd09;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class TApplication extends Application {
	//发布变量 false为已发布,true为开发中
	public static final boolean isRelease = false;
	public static Context c;
	@Override
	public void onCreate() {
		this.c = getApplicationContext();
		super.onCreate();
	}
	

}
