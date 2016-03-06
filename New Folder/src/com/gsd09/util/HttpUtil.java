package com.gsd09.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * HTTP�����࣬doPost()post����doget()get����,dog et һֻ��ET�Ĺ�
 * ���� 1.doPost(List<NameValuePair> params, String url) ִ��POST����
 * ���� 2.doget(String SourceWord) ִ��GET����,��һֻ��et�Ĺ����
 * @author Marx
 *
 */
public class HttpUtil 
{
	/**
     * 
     * ��ȡ�ٶȷ�����,POST����,���鷳
     * 
     * @param params ��������˴��ļ�ֵ��
     * @param url API�ṩ�ĵ�ַ
     * @return ����JSON�ַ���
	 * @throws IOException ��д����
	 * @throws ParseException ��������
	 * @throws Exception ���ش���
     */
    public static String doPost(List<NameValuePair> params, String url) throws 
ParseException, IOException,ClientProtocolException,SocketTimeoutException,IOException
             {
        String result = null;//���JSON�ַ���
        
        // ��ȡHttpClient����
        HttpClient httpClient = new DefaultHttpClient();
        
        // �½�HttpPost����
        HttpPost httpPost = new HttpPost(url);
        
        if (params != null) 
        {
            // �����ַ���
            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            
            // ���ò���ʵ��
            httpPost.setEntity(entity);
        }

        // ���ӳ�ʱ
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        
        // ����ʱ
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,3000);
        
        // ��ȡHttpResponseʵ��
        HttpResponse httpResp = httpClient.execute(httpPost);
        
        // �ж��ǹ�����ɹ�
        if (httpResp.getStatusLine().getStatusCode() == 200) 
        {
            // ��ȡ���ص�����
        	result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
        	
        } else 
        {
            Log.i("HttpPost", "����ʧ��");
        }

        return result;
    }
	
    
    /**
     * ��ȡ�е����룬get���󣬼�ֱ����
     * 
     * @param SourceWord ��ѯ�Ĵ�
     * @return JSON�ַ���
     * @throws ClientProtocolException ��ȡ����
     * @throws SocketTimeoutException ���ӳ�ʱ
     * @throws IOException ��д����
     */
    public static String  doget(String SourceWord) throws ClientProtocolException,SocketTimeoutException,IOException//�е�ʹ��get����
    {
    	
    		
			HttpClient client =new  DefaultHttpClient();
			
			String source = SourceWord.replace(" ", "%20");
			//��ȡclient����
			String geturl = Const.BASE_URL_YOUDAO+"?keyfrom="+Const.APP_FROM_YOUDAO+"&key="+Const.key+"&type=data&doctype=json&version=1.1&q="+source;
			//fanyi.youdao.com/openapi.do?keyfrom=Android-MyAPP&key=1344685129&type=data&doctype=json&version=1.1&q=goods
			Log.i("tagg","geturl==="+geturl);
			//ƴ��GET���������url
			HttpGet request = new HttpGet(geturl); 
			
			HttpParams params = new BasicHttpParams(); 
			HttpConnectionParams.setConnectionTimeout(params, 5000); //�������ӳ�ʱ
			HttpConnectionParams.setSoTimeout(params, 10000); //��������ʱ
			request.setParams(params);
			String page = client.execute(request,  new BasicResponseHandler()); 
			return page;

    	
    }
}
