package com.gsd09.util;

import android.os.Environment;

/**
 * ��ŵ���API��Ҫ�Ĳ�������ַ��API,KEY��
 * @author Marx
 *
 */
public class Const
{
	public static final String BASE_URL_YOUDAO = "http://fanyi.youdao.com/openapi.do";
	public static final String APP_FROM_YOUDAO = "Android-MyAPP";
	public static final String key = "1344685129";
	public static final String BASE_URL_BAIDU ="http://api.fanyi.baidu.com/api/trans/vip/translate";
	public static final String APPID_BAIDU="20160222000013010";
	public static final String BAIDU_KEY="2bjbdWAI9emCE8o5Ym_q";
	//�����ֻ�ϵͳ��Ŀ¼����·��
	public static final String  SDCARD_ROOT=Environment.getExternalStorageDirectory().getAbsolutePath();
	//����odosͼƬ����Ŀ¼
	public static final String  AUDIO_PATH=SDCARD_ROOT+"/QuickDict/images";
	
	public static final String ACTION_CHANGED_SOURCE = "ACTION_CHANGED_SOURCE";
	public static final String ACTION_SERVICE_STOP = "ACTION_SERVICE_STOP";
	public static final String ACTION_START_TRANS = "ACTION_START_TRANS";
}
