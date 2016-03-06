package com.gsd09.util;

import com.gsd09.entity.NewWordsDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/** 数据表操作工具类 */
public class NewWordsDBUtils {
	NewWordsDBHelper helper;

	public NewWordsDBUtils(Context context) {
		super();
		this.helper = new NewWordsDBHelper(context, "newwords.db", null, 1);
	}

	public long insert(String table, ContentValues values) {
		SQLiteDatabase db = helper.getWritableDatabase();
		long id = db.insert(table, null, values);
		db.close();
		return id;
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int deleteId = db.delete(table, whereClause, whereArgs);
		db.close();
		return deleteId;
	}

	public void update() {

	}

	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor query = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		return query;
	}

}
