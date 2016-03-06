package com.gsd09.util;

import android.util.Log;

import com.gsd09.TApplication;

/** 存放Log类 */
public class LogUtil {
	public static void i(String tag, Object msg) {
		// 判断是否已经发布
		if (TApplication.isRelease == false) {
			// 若尚在开发则可打印
			Log.i(tag, String.valueOf(msg));
		}// 已发布则不打印
	}
}
