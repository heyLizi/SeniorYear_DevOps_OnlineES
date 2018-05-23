package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * @author zs
 * 类说明 加密算法工具类
 */
public class EncryptUtil {

	 /**
	  * 
	  * @Title: SHA256
	  * @Description: 传入文本内容，返回 SHA-256 串  
	  * @param strText
	  */
	 public static String SHA256(final String strText){  
	   return SHA(strText, "SHA-256");  
	 }  
	  public static void main(String[] args) {
		System.out.println(SHA256("123"));
	}
	 /**
	  * 
	  * @Title: SHA512
	  * @Description: 传入文本内容，返回 SHA-512 串
	  * @param strText
	  */
	 public static String SHA512(final String strText){  
	   return SHA(strText, "SHA-512");  
	 }  
	  
	  /**
	   *  
	   * @Title: SHA
	   * @Description: 字符串 SHA 加密 
	   * @param strText
	   * @param strType
	   */
	  private static String SHA(final String strText, final String strType){  
	    // 返回值  
	    String strResult = null;  
	    // 是否是有效字符串  
	    if (strText != null && strText.length() > 0){  
	      try{  
	        // SHA 加密开始  
	        // 创建加密对象 并傳入加密类型
	        MessageDigest messageDigest = MessageDigest.getInstance(strType);  
	        // 传入要加密的字符串  
	        messageDigest.update(strText.getBytes());  
	        // 得到 byte 类型结果
	        byte byteBuffer[] = messageDigest.digest();  
	  
	        // 将 byte 转换为 string
	        StringBuffer strHexString = new StringBuffer();  
	        // 遍历 byte buffer
	        for (int i = 0; i < byteBuffer.length; i++){  
	          String hex = Integer.toHexString(0xff & byteBuffer[i]);  
	          if (hex.length() == 1){
	            strHexString.append('0');  
	          }  
	          strHexString.append(hex);  
	        }  
	        // 得到返回结果
	        strResult = strHexString.toString();  
	      }catch (NoSuchAlgorithmException e){  
	        e.printStackTrace();  
	      }  
	    }  
	    return strResult;  
	  }
	  
	  	/**
	  	 * 
	  	 * @Title: getMD5
	  	 * @Description: MD5加密
	  	 * @param reqStr
	  	 */
		public static String getMD5(String reqStr) {  
			  String s = null;  
			  char hexDigits[] = {       // 用来将字节转换成 16 进制表示的字符  
			     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};   
			   try  
			   {  
				byte[] source = reqStr.getBytes();
			    MessageDigest md = MessageDigest.getInstance( "MD5" );  
			    md.update( source );  
			    byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，  
			                                                // 用字节表示就是 16 个字节  
			    char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，  
			                                                 // 所以表示成 16 进制需要 32 个字符  
			    int k = 0;                                // 表示转换结果中对应的字符位置  
			    for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节  
			                                                 // 转换成 16 进制字符的转换  
			     byte byte0 = tmp[i];                 // 取第 i 个字节  
			     str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,   
			                                                             // >>> 为逻辑右移，将符号位一起右移  
			     str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换  
			    }   
			    s = new String(str);                                 // 换后的结果转换为字符串  (32位加密)
			  //s = s.substring(8, 24);     				//(16位加密)
			  
			   }catch( Exception e )  
			   {  
			    e.printStackTrace();  
			   }  
			   return s;  
		} 
}
