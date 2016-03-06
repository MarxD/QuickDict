package com.gsd09.util;
import java.security.MessageDigest;

/**
 * MD5工具类，Bit32(),传入需要加密的字符串，返回MD5加密后的32位小写字符串
 * 静态方法类，无构造函数
 * @author Marx
 *
 */
public class MD5Util 
{
	/**
	 * 传入原字符串MD5加密后返回32位小写字符串
	 * @param SourceString 原字符串
	 * @return 32位小写字符串
	 * @throws Exception 神秘错误，谁也不知道
	 */
    public static String  Bit32(String SourceString) throws Exception 
    {  
	    MessageDigest digest = MessageDigest.getInstance("MD5");  
	    digest.update(SourceString.getBytes());  
	    byte messageDigest[] = digest.digest(); 
	    //调用JAVA自带的加密工具
	    String result =toHexString(messageDigest).toLowerCase();  
	    //返回小写结果
	    return result;
    }  
    
    
   /**
    * 基础太渣。。看不大懂，大致是把加密后的byte字节转化为16进制字符串返回
    */
	private static  String toHexString(byte[] b) 
	{  
	    StringBuilder sb = new StringBuilder(b.length * 2);  
	    for (int i = 0; i < b.length; i++) 
	    {  
	        sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);  
	        sb.append(HEX_DIGITS[b[i] & 0x0f]);  
	    }  
	    return sb.toString();  
	}  
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    

}
