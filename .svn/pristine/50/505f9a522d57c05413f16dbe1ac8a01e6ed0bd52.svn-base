package com.gsd09.util;

import com.gsd09.entity.NewWordsDBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** 此类中为选择数据库类 */
public class DataBaseSelector {
	NewWordsDBHelper dbHelper;
	private Context context;

	public DataBaseSelector(Context context) {
		this.context = context;
	}

	/** 选择NewWords数据库 */
	public void selectNewWordsDataBase() {
		dbHelper = new NewWordsDBHelper(context, "newwords.db", null, 1);
		dbHelper.getReadableDatabase();
	}
	
	

}
