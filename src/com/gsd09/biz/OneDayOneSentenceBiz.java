package com.gsd09.biz;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gsd09.QuickDict.R;
import com.gsd09.entity.ODOSEntity;
import com.gsd09.parse.ODOSParse;
import com.gsd09.util.ExceptionUtil;
import com.gsd09.util.LogUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * ÿ��һ��ҵ����
 * 
 * @author Keith
 *
 */
public class OneDayOneSentenceBiz {
	private Context context;
	private ODOSEntity odosEntity = null;
	private View view;
	private LinearLayout llAnim;
	private ScrollView svOdos;
	private ImageView ivOdos;
	private TextView tvEn;
	private TextView tvCn;
	private TextView tvTr;
	private String url;

	public OneDayOneSentenceBiz(View view,Context context) {
		super();
		this.view = view;
		this.context=context;
	}

	/**
	 * ��ȡҪ��ʾ��ͼƬ ����ʾ��ͼƬ�ؼ�
	 */
	public void getOdosImage(String url) {
		 try {
		 LogUtil.i("ODOS��ȡͼƬ", "url: " + url);
		 BitmapUtils bitmapUtils = new BitmapUtils(context);
		 bitmapUtils.display(ivOdos, url);
		 } catch (Exception e) {
		 ExceptionUtil.handleException(e);
		 }
	}

	/**
	 * ��ȡÿ��һ����Ϣ
	 */
	public void getOdosInfo(String date) {
		try {
			HttpUtils httpUtils = new HttpUtils();
			if (date == null) {
				date = "";
			} else {
				date = "?/date=" + date;
			}
			String url = "http://open.iciba.com/dsapi/" + date;
			LogUtil.i("ODOS��ȡ��Ϣ��", "URL = " + url);
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					LogUtil.i("ODOS��ȡ��Ϣ��", "ҵ��ǰ�߳����ƣ�"
							+ Thread.currentThread().getName());
					LogUtil.i("ODOS��ȡ��Ϣ��", "��ȡ��...");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					try {
						String json = responseInfo.result;
						LogUtil.i("ODOS��ȡ�ɹ���Ϣ", json);
						odosEntity = ODOSParse.parse(json);
						LogUtil.i("ODOS��Ϣ",
								"odosEntity.getSid()" + odosEntity.getSid());
						// ���ô洢ʵ�����ݵķ���
						showOdosInfo();
					} catch (Exception e) {
						LogUtil.i("ODOS��ȡ��Ϣ", "�쳣e : " + e.toString());
						ExceptionUtil.handleException(e);
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					LogUtil.i("ODOSʧ����Ϣ", error);
					// ���ô洢ʵ�����ݵķ���
					showOdosInfo();
				}
			});
		} catch (Exception e) {
			LogUtil.i("ODOSERROR", "odosEntityΪ��");
			ExceptionUtil.handleException(e);
		}
	}

	/**
	 * ����ѯ��������Ϣ����Ϣҳ��
	 */
	public void showOdosInfo() {
		if (odosEntity==null) {
			Toast.makeText(context, "��Ǹ�� ÿ��һ�����δ�����...�����Ƿ�����.", Toast.LENGTH_SHORT).show();
			return;
		}
		getViews();
		llAnim.setVisibility(View.GONE);
		svOdos.setVisibility(View.VISIBLE);
		tvTr.setText(odosEntity.getTranslation());
		tvCn.setText(odosEntity.getNote());
		tvEn.setText(odosEntity.getContent());
		url = odosEntity.getPicture();
		LogUtil.i("ODOSIMG��ȡ��Ϣ", "url: " + url);
		getOdosImage(url);
	}

	/**
	 * ��ȡҳ����ʾ�ؼ�
	 */
	private void getViews() {
		
		llAnim = (LinearLayout) view.findViewById(R.id.ll_anim);
		svOdos = (ScrollView) view.findViewById(R.id.sv_odos);
		ivOdos = (ImageView) view.findViewById(R.id.iv_odos);
		tvEn = (TextView) view.findViewById(R.id.tv_odos_english);
		tvCn = (TextView) view.findViewById(R.id.tv_odos_chinese);
		tvTr = (TextView) view.findViewById(R.id.tv_odos_translation);
	}

}
