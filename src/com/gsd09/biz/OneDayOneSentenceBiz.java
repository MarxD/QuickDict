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
 * 每日一句业务类
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
	 * 获取要显示的图片 并显示在图片控件
	 */
	public void getOdosImage(String url) {
		 try {
		 LogUtil.i("ODOS获取图片", "url: " + url);
		 BitmapUtils bitmapUtils = new BitmapUtils(context);
		 bitmapUtils.display(ivOdos, url);
		 } catch (Exception e) {
		 ExceptionUtil.handleException(e);
		 }
	}

	/**
	 * 获取每日一句信息
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
			LogUtil.i("ODOS获取信息中", "URL = " + url);
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					LogUtil.i("ODOS获取信息中", "业务当前线程名称："
							+ Thread.currentThread().getName());
					LogUtil.i("ODOS获取信息中", "获取中...");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					try {
						String json = responseInfo.result;
						LogUtil.i("ODOS获取成功信息", json);
						odosEntity = ODOSParse.parse(json);
						LogUtil.i("ODOS信息",
								"odosEntity.getSid()" + odosEntity.getSid());
						// 调用存储实体数据的方法
						showOdosInfo();
					} catch (Exception e) {
						LogUtil.i("ODOS获取信息", "异常e : " + e.toString());
						ExceptionUtil.handleException(e);
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					LogUtil.i("ODOS失败信息", error);
					// 调用存储实体数据的方法
					showOdosInfo();
				}
			});
		} catch (Exception e) {
			LogUtil.i("ODOSERROR", "odosEntity为空");
			ExceptionUtil.handleException(e);
		}
	}

	/**
	 * 将查询的资料信息在信息页面
	 */
	public void showOdosInfo() {
		if (odosEntity==null) {
			Toast.makeText(context, "抱歉！ 每日一句加载未能完成...请检查是否联网.", Toast.LENGTH_SHORT).show();
			return;
		}
		getViews();
		llAnim.setVisibility(View.GONE);
		svOdos.setVisibility(View.VISIBLE);
		tvTr.setText(odosEntity.getTranslation());
		tvCn.setText(odosEntity.getNote());
		tvEn.setText(odosEntity.getContent());
		url = odosEntity.getPicture();
		LogUtil.i("ODOSIMG获取信息", "url: " + url);
		getOdosImage(url);
	}

	/**
	 * 获取页面显示控件
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
