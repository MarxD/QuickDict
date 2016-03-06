package com.gsd09.biz;

import java.util.ArrayList;
import java.util.List;

import com.gsd09.activity.NewWordsFragment;
import com.gsd09.activity.WordFragment;
import com.gsd09.entity.NewWords;
import com.gsd09.util.ExceptionUtil;
import com.gsd09.util.LogUtil;
import com.gsd09.util.NewWordsDBUtils;

import android.R.anim;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class NewWordsBiz {
	private NewWordsFragment newWordsFragment;
	private NewWordsDBUtils newWordsDBUtils;
	private List<NewWords> newWordses;

	public NewWordsBiz(NewWordsFragment newWordsFragment, Context context) {
		this.newWordsFragment = newWordsFragment;
		newWordsDBUtils = new NewWordsDBUtils(context);
	}

	public void queryAllNewWords() {
		new QueryAllNewWords().execute();
	}

	public void deleteAllNewWords() {
		new DeleteAllNewWords().execute();
	}

	public void deleteThisNewWords(String englishNewWordsToBeDeleted, String chineseNewTranslationToBeDeleted,
			String librarySourceToBeDeleted) {
		new DeleteThisNewWords(englishNewWordsToBeDeleted, chineseNewTranslationToBeDeleted, librarySourceToBeDeleted)
				.execute();
	}

	public void insert(String table, ContentValues cv) {
		new InsertNewWords(table, cv).execute();
	}

	/** 查找所有单词异步任务 */
	class QueryAllNewWords extends AsyncTask<Void, Void, List<NewWords>> {

		@Override
		protected List<NewWords> doInBackground(Void... params) {
			try {
				// 对整个newwordstable表搜索
				String[] columns = { "englishnewwords", "chinesenewtranslation", "librarysource", "created" };
				// 进入NewWordsDBUtils类执行query方法,获得指针,
				Cursor query = newWordsDBUtils.query("newwordstable", columns, null, null, null, null, "created desc");
				newWordses = new ArrayList<NewWords>();
				while (query.moveToNext()) {
					NewWords newWords = new NewWords();
					newWords.setEnglishNewWords(query.getString(0));
					newWords.setChineseNewTranslation(query.getString(1));
					newWords.setLibrarySource(query.getString(2));
					newWords.setCreated(query.getLong(3));
					newWordses.add(newWords);
				}
				query.close();
				Log.i("newWordses", "" + newWordses);
				return newWordses;
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			} finally {

			}
			return null;

		}

		@Override
		protected void onPostExecute(List<NewWords> result) {
			super.onPostExecute(result);
			newWordsFragment.setAdapter(result);
		}
	}

	/** 删除所有单词异步任务 */
	class DeleteAllNewWords extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				// 进入NewWordsDBUtils类执行delete方法,删除所有单词
				int deleteMount = newWordsDBUtils.delete("newwordstable", null, null);
				Log.i("数据表", "删除了" + deleteMount + "行");
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			} finally {
				// 执行完毕后执行查询方法
				queryAllNewWords();
			}
			return null;
		}
	}

	/** 删除选中单词异步任务 */
	class DeleteThisNewWords extends AsyncTask<Void, Void, Void> {
		private String englishNewWordsToBeDeleted;
		private String chineseNewTranslationToBeDeleted;
		private String librarySourceToBeDeleted;

		public DeleteThisNewWords(String englishNewWordsToBeDeleted, String chineseNewTranslationToBeDeleted,
				String librarySourceToBeDeleted) {
			this.englishNewWordsToBeDeleted = englishNewWordsToBeDeleted;
			this.chineseNewTranslationToBeDeleted = chineseNewTranslationToBeDeleted;
			this.librarySourceToBeDeleted = librarySourceToBeDeleted;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				// 查找条件
				int deleteId = newWordsDBUtils.delete("newwordstable",
						"englishnewwords='" + englishNewWordsToBeDeleted.toString() + "' and chinesenewtranslation='"
								+ chineseNewTranslationToBeDeleted + "' and librarysource='" + librarySourceToBeDeleted
								+ "'",
						null);
				LogUtil.i("数据表", deleteId);
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			} finally {
				// 执行完毕后执行查询方法
				queryAllNewWords();
			}
			return null;
		}

	}

	/** 增加添加单词异步任务 */
	class InsertNewWords extends AsyncTask<Void, Void, Void> {
		private String table;
		private ContentValues cv;

		public InsertNewWords(String table, ContentValues cv) {
			this.table = table;
			this.cv = cv;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				long insertId = newWordsDBUtils.insert(table, cv);
				LogUtil.i("数据表", "插入数据,_id为" + insertId);
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			} finally {
				// 执行完毕后执行查询方法
				queryAllNewWords();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			newWordsFragment.dismissDialog();
		}
	}

	// //////////////////////////////////下面为WordFragment操作////////////////////////////////////
	private WordFragment wordFragment;
	private Context context;

	public NewWordsBiz(WordFragment wordFragment, Context context) {
		this.wordFragment = wordFragment;
		this.context = context;
		newWordsDBUtils = new NewWordsDBUtils(context);
	}

	public void insertOnWordFragment(String table, ContentValues cv) {
		new InsertNewWordsOfWordFragment(table, cv).execute();
	}

	/** 从翻译页面添加单词异步任务 */
	class InsertNewWordsOfWordFragment extends AsyncTask<Void, Void, Void> {
		private String table;
		private ContentValues cv;

		public InsertNewWordsOfWordFragment(String table, ContentValues cv) {
			this.table = table;
			this.cv = cv;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				long insertId = newWordsDBUtils.insert(table, cv);
				LogUtil.i("数据表", "插入数据,_id为" + insertId);
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			} finally {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Toast.makeText(context, "添加生词成功", 0).show();
		}
	}

}
