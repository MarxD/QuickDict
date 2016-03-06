package com.gsd09.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.gsd09.QuickDict.R;
import com.gsd09.activity.NewWordsFragment;
import com.gsd09.biz.NewWordsBiz;
import com.gsd09.entity.NewWords;
import com.gsd09.util.ExceptionUtil;
import com.gsd09.util.LogUtil;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class NewWordsAdapter extends BaseAdapter {
	static public Context context;
	static public List<NewWords> newWordses;
	static public NewWordsFragment newWordsFragment;
	private LayoutInflater inflater;
	MyButton button = null;

	public NewWordsAdapter(Context context, List<NewWords> newWordses,
			NewWordsFragment newWordsFragment) {
		try {
			this.context = context;
			this.newWordses = newWordses;
			this.newWordsFragment = newWordsFragment;
			this.inflater = LayoutInflater.from(context);
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {

		}
	}

	@Override
	public int getCount() {
		return newWordses.size();
	}

	@Override
	public Object getItem(int position) {
		return newWordses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.newwords_for_adapter, null);
			viewHolder = new ViewHolder();
			viewHolder.tvCreated = (TextView) convertView
					.findViewById(R.id.tvCreated);
			viewHolder.tvEnglishNewWord = (TextView) convertView
					.findViewById(R.id.tvEnglishNewWords);
			viewHolder.tvChineseNewTranslation = (TextView) convertView
					.findViewById(R.id.tvChineseTranslation);
			viewHolder.ivLibraryPicture = (ImageView) convertView
					.findViewById(R.id.ivLibraryPicture);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		// ���ÿؼ�������
		NewWords n = newWordses.get(position);
		// ��ȡ���뵥�ʵ�����
		long date = n.getCreated();
		// תΪ�����ո�ʽ
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		String created = f.format(date);
		// ���ô��뵥�ʵ�����
		viewHolder.tvCreated.setText(created);
		// ����ÿ�ι���ʱ,���˱߽��convertview�ᱻ�����ÿ�
		// ������������ÿ�ι���������item������ʾ
		// Ȼ���������ж�������������ɸѡ
		viewHolder.tvCreated.setVisibility(View.VISIBLE);
		// ����һ������һ����������ʱ�����,��������ͬ,��ѱ��ε���������
		if (position != 0) {
			NewWords lastPosition = (NewWords) getItem(position - 1);
			long last = lastPosition.getCreated();
			String lastDate = f.format(last);
			if (lastDate.equals(created)) {
				viewHolder.tvCreated.setVisibility(View.GONE);
			}
		}
		// ���ö�Ӧ��Ӣ���뷭�������
		viewHolder.tvEnglishNewWord.setText(n.getEnglishNewWords());
		viewHolder.tvChineseNewTranslation
				.setText(n.getChineseNewTranslation());
		// ��ȡ��item�Ĵʿ���Դ
		String librarySource = n.getLibrarySource();
		// �ж���Դ,ѡ���Ӧ��ͬ��ͼƬ
		if (librarySource.equals("custom")) {
			viewHolder.ivLibraryPicture
					.setImageResource(R.drawable.new_words_custom);
		} else if (librarySource.equals("youdao")) {
			viewHolder.ivLibraryPicture
					.setImageResource(R.drawable.new_words_youdao);
		} else if (librarySource.equals("baidu")) {
			viewHolder.ivLibraryPicture
					.setImageResource(R.drawable.new_words_baidu);
		} else if (librarySource.equals("local")) {
			viewHolder.ivLibraryPicture
					.setImageResource(R.drawable.new_words_local);
		}

		button = (MyButton) convertView.findViewById(R.id.ibDeleteThisNewWords);
		button.setIndex(position);
		button.setOnClickListener(MyOnClickListener.getInstance());
		return convertView;
	}

	class ViewHolder {
		TextView tvCreated;
		TextView tvEnglishNewWord;
		TextView tvChineseNewTranslation;
		ImageView ivLibraryPicture;
	}

}

// ����OnClickListner����д�䷽��
class MyOnClickListener implements OnClickListener {

	private static MyOnClickListener instance = null;

	private MyOnClickListener() {
	}

	public static MyOnClickListener getInstance() {
		if (instance == null) {
			instance = new MyOnClickListener();
		}
		return instance;
	}

	// ǽשΪMyButton���ͺ�,���ͼƬ���ɻ�õ�ǰitemλ��,�Ӷ���ȡ��item�еĵ���ԭ�� �����Լ���Դ�ʿ�
	@Override
	public void onClick(View v) {
		int index = ((MyButton) v).getIndex();
		String englishNewWordsToBeDeleted = NewWordsAdapter.newWordses.get(
				index).getEnglishNewWords();
		String chineseNewTranslationToBeDeleted = NewWordsAdapter.newWordses
				.get(index).getChineseNewTranslation();
		String librarySourceToBeDeleted = NewWordsAdapter.newWordses.get(index)
				.getLibrarySource();
		new NewWordsBiz(NewWordsAdapter.newWordsFragment,
				NewWordsAdapter.context).deleteThisNewWords(
				englishNewWordsToBeDeleted, chineseNewTranslationToBeDeleted,
				librarySourceToBeDeleted);
	}

}

// �½�MyButton��дButton����,��д�� ��ȡpositionλ��
class MyButton extends Button {

	private int index = -1;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public MyButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
}