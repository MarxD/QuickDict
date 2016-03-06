package com.gsd09.entity;

import com.gsd09.util.LogUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class NewWordsDBHelper extends SQLiteOpenHelper {
	public NewWordsDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table newwordstable("
				+ "_id integer primary key autoincrement,"
				+ "englishnewwords text not null,"
				+ "chinesenewtranslation text not null,"
				+ "librarysource text not null," 
				+ "created text not null)";
		db.execSQL(sql);
		LogUtil.i("数据库", "table create ok");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table newwordstable";
		// 删除表
		db.execSQL(sql);
		LogUtil.i("数据库", "table drop");
		onCreate(db);
	}

}