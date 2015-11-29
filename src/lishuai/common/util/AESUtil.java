package lishuai.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



/**
 * AES 对称加密算法
 * @author li
 *
 */
public class AESUtil {
	
	private static final String AES="AES";
	
	private static final String CHARSET="UTF-8";
	
	
	public static String encryptBASE64(String data,String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		byte[] bt=encrypt(data.getBytes(CHARSET),key.getBytes(CHARSET));
		String str=new BASE64Encoder().encode(bt);
		return str;
	}
	
	public static String decryptBASE64(String data,String key) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		BASE64Decoder decoder=new BASE64Decoder();
		byte[] buf=decoder.decodeBuffer(data);
		byte[] bt=decrypt(buf,key.getBytes(CHARSET));
		return new String(bt,CHARSET);
	}
	
	public static String encryptHEX(String data,String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		byte[] bt=encrypt(data.getBytes(CHARSET),key.getBytes(CHARSET));
		String str=byteTohex(bt);
		return str;
	}
	
	/**
	 * 根据密钥加密
	 * @param data
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static byte[] encrypt(byte[] data,byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException{
		SecureRandom sr=new SecureRandom();
		SecretKeySpec skeySpec = new SecretKeySpec(key,AES);
		Cipher cipher=Cipher.getInstance(AES); 
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec,sr);
		return cipher.doFinal(data);
	}
	
	private static byte[] decrypt(byte[] data,byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		SecureRandom sr=new SecureRandom();
		SecretKeySpec skeySpec = new SecretKeySpec(key,AES);
		Cipher cipher=Cipher.getInstance(AES);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec,sr);
		return cipher.doFinal(data);
	}
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, IOException {
		String data="20151212";
		InetAddress addr=InetAddress.getLocalHost();
		String key=LocalMAC.getSystemMAC(addr)+"DHCC";
		String str=encryptBASE64(data,key);
		System.out.println(str);
		System.out.println(decryptBASE64(str,key));
		
		String strHEX=encryptHEX(data,key);
		
		System.out.println(strHEX);
	}
	
	public static String byteTohex(byte[] b) {  
	    String hs = "";  
	    String stmp = "";  
	    for (int n = 0; n < b.length; n++) {  
	        stmp = Integer.toHexString(b[n] & 0xFF);  
	        if (stmp.length() == 1)  
	            hs += ("0" + stmp);  
	        else  
	            hs += stmp;  
	    }  
	    return hs.toUpperCase();  
	}
	
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}
	
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	} 

}
