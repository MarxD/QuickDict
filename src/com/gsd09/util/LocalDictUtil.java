package com.gsd09.util;
import com.gsd09.entity.Word;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LocalDictUtil {
	Context mContext;
	SQLiteDatabase LocalDB;
	public LocalDictUtil(Context c) {
		this.mContext=c;
		LocalDB = SQLiteDatabase.openOrCreateDatabase(mContext.getExternalFilesDir(null)+"/dict/MyDict.db", null);
	}
	
	public void query(String source,Word word)
	{
		Cursor cursor =  LocalDB.query("Words", new String[]{"word","explain"}, "word like ?", new String[]{source+"%"}, null, null, "word");
		cursor.moveToFirst();
		String sourceword ;
		String trans ;
		if(cursor.getCount()==0)
		{
			 sourceword = source;
			 trans = "本地无数据";
		}
		else
		{
			cursor.move(0);
			 sourceword = cursor.getString(0);
			 trans = cursor.getString(1);
		}
		word.setsourceword(sourceword);
		word.settranslation(trans);
		cursor.close();
	}

}