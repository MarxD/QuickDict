package com.gsd09.util;
import android.util.Log;

import com.gsd09.entity.*;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
/**
 * 
 * JSON解析类
 * 无构造函数
 * 方法 1.getyoudaotranslation(String jsonstr)解析有道返回的JSON字符串
 * 方法 2.getbaidutranslation(String jsonstr)解析百度返回的JSON字符串
 * 
 * ps:错误捕获部分很乱
 * @author Marx
 *
 */
public class JsonParserUtil 
{


	/**
	 * 解析有道翻译获得的JSON字符串
	 * @param jsonstr JSON字符串
	 * @return Word对象
	 * @throws JSONException 解析错误
	 */
	public static Word getyoudaotranslation(String jsonstr) throws JSONException 
	{
		JSONObject jsonobj=null;
		try 
		{
			Log.i("tagg",jsonstr);
			jsonobj = new JSONObject(jsonstr);

		} catch (JSONException e) 
		{
			Log.i("tagg","错误"+e.toString());
			return new Word("error","解析错误"+e.toString());
			
		}
		
		if(jsonobj==null)
		{
			return new Word("youdao","数据为空");
		}
		
		String sourceword="错误";
		String explain="";
		JSONArray explains=null;
		sourceword = jsonobj.getString("query");
		try {
			
		explains = jsonobj.getJSONObject("basic").getJSONArray("explains");
		} catch (Exception e) {
			return new Word("有道太垃圾","请查询其他数据源");
		}
		for(int i=0;i<explains.length();i++)
		{
			explain+=explains.getString(i);
		}
		Log.i("tagg","获得源单词和解释,单词"+sourceword+",解释"+explains.toString());
		return new Word(sourceword, explain);
	
		
	}
	
	/**
	 * 解析百度JSON字符串
	 * @param jsonstr JSON字符串
	 * @return Word对象
	 * @throws JSONException JSON解析错误
	 */
	public static Word getbaidutranslation(String jsonstr) throws JSONException
	{

		JSONObject jsonaobj = null;
		try 
		{
			jsonaobj = new JSONObject(jsonstr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jsonaobj==null)
		{
			return new Word("error","查询失败，数据为空");
			
		}
		else if(!jsonaobj.isNull("error_code"))
		{
			switch (jsonaobj.getInt("error_code")) {
			case 52000:
				return new Word("error", "查询失败");
				
			default:
				return new Word("error", "参数错误， 请暂时使用本地查询");
				
			}
		}
		else
		{
			JSONArray obj_query = jsonaobj.getJSONArray("trans_result");//获取翻译结果
			String query = obj_query.getJSONObject(0).getString("src");//原词
			String trans = unicode2string(obj_query.getJSONObject(0).getString("dst"));//Unicode转UTF-8字符串
			return new Word(query, trans);
			
		}
	}
	
	
	
	/**
	 * 解析json字符串内的unicode字符
	 * @param Unicode
	 * @return 解析后的字符串
	 */
	public static String unicode2string(String unicode) 
	{
        List<String> list =new ArrayList<String>();
        String zz="\\\\u[0-9,a-z,A-Z]{4}";
         
        //正则表达式,用法参考API
        Pattern pattern = Pattern.compile(zz);
        Matcher m = pattern.matcher(unicode);
        while(m.find()){
            list.add(m.group());
        }
        for(int i=0,j=2;i<list.size();i++){
            String st = list.get(i).substring(j, j+4);
             
            //将得到的数值按照16进制解析为十进制整数，再強转为字符
            char ch = (char) Integer.parseInt(st, 16);
            
            //用得到的字符替换编码表达式
            unicode = unicode.replace(list.get(i), String.valueOf(ch));
        }
        return unicode;
    }

}
