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
 * HTTP请求类，doPost()post请求，doget()get请求,dog et 一只叫ET的狗
 * 方法 1.doPost(List<NameValuePair> params, String url) 执行POST请求
 * 方法 2.doget(String SourceWord) 执行GET请求,由一只叫et的狗完成
 * @author Marx
 *
 */
public class HttpUtil 
{
	/**
     * 
     * 获取百度翻译结果,POST请求,贼麻烦
     * 
     * @param params 向服务器端传的键值对
     * @param url API提供的地址
     * @return 返回JSON字符串
	 * @throws IOException 读写错误
	 * @throws ParseException 解析错误
	 * @throws Exception 神秘错误
     */
    public static String doPost(List<NameValuePair> params, String url) throws 
ParseException, IOException,ClientProtocolException,SocketTimeoutException,IOException
             {
        String result = null;//存放JSON字符串
        
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        
        // 新建HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        
        if (params != null) 
        {
            // 设置字符集
            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            
            // 设置参数实体
            httpPost.setEntity(entity);
        }

        // 连接超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        
        // 请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,3000);
        
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpPost);
        
        // 判断是够请求成功
        if (httpResp.getStatusLine().getStatusCode() == 200) 
        {
            // 获取返回的数据
        	result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
        	
        } else 
        {
            Log.i("HttpPost", "请求失败");
        }

        return result;
    }
	
    
    /**
     * 获取有道翻译，get请求，简直方便
     * 
     * @param SourceWord 查询的词
     * @return JSON字符串
     * @throws ClientProtocolException 读取错误
     * @throws SocketTimeoutException 连接超时
     * @throws IOException 读写错误
     */
    public static String  doget(String SourceWord) throws ClientProtocolException,SocketTimeoutException,IOException//有道使用get方法
    {
    	
    		
			HttpClient client =new  DefaultHttpClient();
			
			String source = SourceWord.replace(" ", "%20");
			//获取client对象
			String geturl = Const.BASE_URL_YOUDAO+"?keyfrom="+Const.APP_FROM_YOUDAO+"&key="+Const.key+"&type=data&doctype=json&version=1.1&q="+source;
			//fanyi.youdao.com/openapi.do?keyfrom=Android-MyAPP&key=1344685129&type=data&doctype=json&version=1.1&q=goods
			Log.i("tagg","geturl==="+geturl);
			//拼接GET请求所需的url
			HttpGet request = new HttpGet(geturl); 
			
			HttpParams params = new BasicHttpParams(); 
			HttpConnectionParams.setConnectionTimeout(params, 5000); //设置连接超时
			HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
			request.setParams(params);
			String page = client.execute(request,  new BasicResponseHandler()); 
			return page;

    	
    }
}
