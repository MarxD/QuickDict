package com.gsd09.util;
import java.security.MessageDigest;

/**
 * MD5�����࣬Bit32(),������Ҫ���ܵ��ַ���������MD5���ܺ��32λСд�ַ���
 * ��̬�����࣬�޹��캯��
 * @author Marx
 *
 */
public class MD5Util 
{
	/**
	 * ����ԭ�ַ���MD5���ܺ󷵻�32λСд�ַ���
	 * @param SourceString ԭ�ַ���
	 * @return 32λСд�ַ���
	 * @throws Exception ���ش���˭Ҳ��֪��
	 */
    public static String  Bit32(String SourceString) throws Exception 
    {  
	    MessageDigest digest = MessageDigest.getInstance("MD5");  
	    digest.update(SourceString.getBytes());  
	    byte messageDigest[] = digest.digest(); 
	    //����JAVA�Դ��ļ��ܹ���
	    String result =toHexString(messageDigest).toLowerCase();  
	    //����Сд���
	    return result;
    }  
    
    
   /**
    * ����̫�����������󶮣������ǰѼ��ܺ��byte�ֽ�ת��Ϊ16�����ַ�������
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
