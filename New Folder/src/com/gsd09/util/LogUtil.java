package com.gsd09.util;

import android.util.Log;

import com.gsd09.TApplication;

/** ���Log�� */
public class LogUtil {
	public static void i(String tag, Object msg) {
		// �ж��Ƿ��Ѿ�����
		if (TApplication.isRelease == false) {
			// �����ڿ�����ɴ�ӡ
			Log.i(tag, String.valueOf(msg));
		}// �ѷ����򲻴�ӡ
	}
}
