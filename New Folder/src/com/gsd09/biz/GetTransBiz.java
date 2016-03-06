package com.gsd09.biz;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.gsd09.entity.Word;
import com.gsd09.util.Const;
import com.gsd09.util.HttpUtil;
import com.gsd09.util.JsonParserUtil;
import com.gsd09.util.LogUtil;
import com.gsd09.util.MD5Util;

/**
 * 翻译工具类,继承自异步任务类,构建需要传入TextView列表，执行Execute时传入查询字符串，返回Word实体列表
 * GetTransUtil gettransutil = new GetTransUtil(List<Textview> list)
 * @author Marx
 * 
 */
public class GetTransBiz extends AsyncTask<String, Integer, List<Word>>{

	List<TextView> views;
	public GetTransBiz(List<TextView> list)
	{
		this.views=list;
	}
	
	@Override
	protected List<Word> doInBackground(String... Source) {
		ArrayList<Word> words = new ArrayList<Word>();
		String sourceword = Source[0];//获得查询单词
		Word fromyoudao = new Word();
		try 
		{
			fromyoudao = JsonParserUtil.getyoudaotranslation(HttpUtil.doget(sourceword));
			//有道翻译，get方式
			Log.i("tagg", "返回对象"+fromyoudao.getsourceword()+"="+fromyoudao.gettranslation());
		} catch (JSONException e) 
		{
			Log.i("tagg","解析出错"+e.toString());
			e.printStackTrace();
			
		} catch (SocketTimeoutException e) 
		{
			LogUtil.i("geterror","连接超时");
			inserterrot(fromyoudao,"连接超时");
			e.printStackTrace();
			
		} catch (ClientProtocolException e) 
		{
			LogUtil.i("geterror","读取超时");
			inserterrot(fromyoudao,"读取超时");
			e.printStackTrace();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
			LogUtil.i("geterror", "未知错误，详细信息"+e.toString());
			inserterrot(fromyoudao,"未知错误");
		}
		//====================================================有道翻译结束
		
		
		
		
		
		Word frombaidu = new Word();//存放百度翻译结果
		//百度翻译要求，POST
		List<NameValuePair> params = new ArrayList<NameValuePair>();//键值对链表	
		params.add(new BasicNameValuePair("q", sourceword));//查询原词
		params.add(new BasicNameValuePair("from", "auto"));//源语言
		params.add(new BasicNameValuePair("to", "auto"));//目标语言
		params.add(new BasicNameValuePair("appid", Const.APPID_BAIDU));//申请所得的百度APPID
		String salt = new Random().nextInt(999999)+"";//API要求随机数，闹腾
		params.add(new BasicNameValuePair("salt", salt));
		//添加API所需的键值对,除了签名，需单独生成
		
		String fucking_key = Const.APPID_BAIDU+sourceword+salt+Const.BAIDU_KEY;
		//APPID+查询词+随机数+申请所得的密匙（KEY），拼接生成字符串
		
		try 
		{
			params.add(new BasicNameValuePair("sign", MD5Util.Bit32(fucking_key)));
			//加入MD5加密后的字符串作为签名，格式为32位小写
		} catch (Exception e1) 
		{
			inserterrot(frombaidu, "未知错误");
			LogUtil.i("baiduerror", e1.toString());
		}
		//=====所有键值对添加完成
		
		try 
		{

			frombaidu = JsonParserUtil.getbaidutranslation(HttpUtil.doPost(params, Const.BASE_URL_BAIDU));
			//将获得的JSON字符串传入JsonParser进行解析
			
		} catch (IOException e) 
		{
			inserterrot(frombaidu, "未知错误");
			LogUtil.i("baiduerror", e.toString());
		} catch (JSONException e) {
			inserterrot(frombaidu, "解析错误");
			LogUtil.i("baiduerror", e.toString());
		}
		//====================================================百度翻译完成
		
		words.add(fromyoudao);
		words.add(frombaidu);
		words.add(new Word("测试","test"));
		
		return words;
	}
	
	/**
	 *  封装的小方法，出现错误后调用传入错误星信息
	 * @param w 传入的Word型实体
	 * @param errorinfo 错误信息
	 */
	private void inserterrot(Word w,String errorinfo) 
	{
		
		w.setsourceword("error");
		w.settranslation(errorinfo);
	}

	@Override
	protected void onPreExecute() 
	{
		for(TextView t:views)
		{
			t.setText("查询中，请稍后");
		}
		super.onPreExecute();
	}
	
	
	@Override
	protected void onPostExecute(List<Word> result) {
		
		
		for(int i=0;i<result.size();i++)
		{
			Log.i("tagg", result.get(i).getsourceword()+"="+result.get(i).gettranslation());

		}
		
		for(int i=0;i<views.size();i++)
		{
			if(i%2==0)
			views.get(i).setText(result.get(i/2).getsourceword());
			else
			{
				views.get(i).setText(result.get(i/2).gettranslation());
			}
		}
		
	}
}
