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
 * ���빤����,�̳����첽������,������Ҫ����TextView�б�ִ��Executeʱ�����ѯ�ַ���������Wordʵ���б�
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
		String sourceword = Source[0];//��ò�ѯ����
		Word fromyoudao = new Word();
		try 
		{
			fromyoudao = JsonParserUtil.getyoudaotranslation(HttpUtil.doget(sourceword));
			//�е����룬get��ʽ
			Log.i("tagg", "���ض���"+fromyoudao.getsourceword()+"="+fromyoudao.gettranslation());
		} catch (JSONException e) 
		{
			Log.i("tagg","��������"+e.toString());
			e.printStackTrace();
			
		} catch (SocketTimeoutException e) 
		{
			LogUtil.i("geterror","���ӳ�ʱ");
			inserterrot(fromyoudao,"���ӳ�ʱ");
			e.printStackTrace();
			
		} catch (ClientProtocolException e) 
		{
			LogUtil.i("geterror","��ȡ��ʱ");
			inserterrot(fromyoudao,"��ȡ��ʱ");
			e.printStackTrace();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
			LogUtil.i("geterror", "δ֪������ϸ��Ϣ"+e.toString());
			inserterrot(fromyoudao,"δ֪����");
		}
		//====================================================�е��������
		
		
		
		
		
		Word frombaidu = new Word();//��Űٶȷ�����
		//�ٶȷ���Ҫ��POST
		List<NameValuePair> params = new ArrayList<NameValuePair>();//��ֵ������	
		params.add(new BasicNameValuePair("q", sourceword));//��ѯԭ��
		params.add(new BasicNameValuePair("from", "auto"));//Դ����
		params.add(new BasicNameValuePair("to", "auto"));//Ŀ������
		params.add(new BasicNameValuePair("appid", Const.APPID_BAIDU));//�������õİٶ�APPID
		String salt = new Random().nextInt(999999)+"";//APIҪ�������������
		params.add(new BasicNameValuePair("salt", salt));
		//���API����ļ�ֵ��,����ǩ�����赥������
		
		String fucking_key = Const.APPID_BAIDU+sourceword+salt+Const.BAIDU_KEY;
		//APPID+��ѯ��+�����+�������õ��ܳף�KEY����ƴ�������ַ���
		
		try 
		{
			params.add(new BasicNameValuePair("sign", MD5Util.Bit32(fucking_key)));
			//����MD5���ܺ���ַ�����Ϊǩ������ʽΪ32λСд
		} catch (Exception e1) 
		{
			inserterrot(frombaidu, "δ֪����");
			LogUtil.i("baiduerror", e1.toString());
		}
		//=====���м�ֵ��������
		
		try 
		{

			frombaidu = JsonParserUtil.getbaidutranslation(HttpUtil.doPost(params, Const.BASE_URL_BAIDU));
			//����õ�JSON�ַ�������JsonParser���н���
			
		} catch (IOException e) 
		{
			inserterrot(frombaidu, "δ֪����");
			LogUtil.i("baiduerror", e.toString());
		} catch (JSONException e) {
			inserterrot(frombaidu, "��������");
			LogUtil.i("baiduerror", e.toString());
		}
		//====================================================�ٶȷ������
		
		words.add(fromyoudao);
		words.add(frombaidu);
		words.add(new Word("����","test"));
		
		return words;
	}
	
	/**
	 *  ��װ��С���������ִ������ô����������Ϣ
	 * @param w �����Word��ʵ��
	 * @param errorinfo ������Ϣ
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
			t.setText("��ѯ�У����Ժ�");
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
