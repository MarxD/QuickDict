package com.gsd09.util;

import com.gsd09.entity.NewWordsDBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** ������Ϊѡ�����ݿ��� */
public class DataBaseSelector {
	NewWordsDBHelper dbHelper;
	private Context context;

	public DataBaseSelector(Context context) {
		this.context = context;
	}

	/** ѡ��NewWords���ݿ� */
	public void selectNewWordsDataBase() {
		dbHelper = new NewWordsDBHelper(context, "newwords.db", null, 1);
		dbHelper.getReadableDatabase();
	}
	
	

}
