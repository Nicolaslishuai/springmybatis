package lishuai.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 非对称加密解密
 * @author li
 *
 */
public class RSAUtil {
	
	private static String RSA="RSA";
	
	private KeyPairGenerator kpg = null;
	private KeyPair kp = null;
    private PublicKey public_key = null;
	private PrivateKey private_key = null;
	
	public RSAUtil(int in, String address) throws NoSuchAlgorithmException,
       FileNotFoundException, IOException{
          kpg = KeyPairGenerator.getInstance(RSA); //创建‘密匙对’生成器
          kpg.initialize(in); //指定密匙长度（取值范围：512～2048）
          kp = kpg.genKeyPair(); //生成‘密匙对’，其中包含着一个公匙和一个私匙的信息
          public_key = kp.getPublic(); //获得公匙
          private_key = kp.getPrivate(); //获得私匙
          
          sun.misc.BASE64Encoder  b64 =  new sun.misc.BASE64Encoder();
          String pkStr = b64.encode(public_key.getEncoded());
          String prStr = b64.encode(private_key.getEncoded());
          System.out.print("pkStr length:" +pkStr.length() +pkStr);

          FileWriter fw=new FileWriter(address + "/private_key.dat"); 
          fw.write(prStr);
          fw.close();
          
          FileWriter  fw2 = new FileWriter(address + "/public_key.dat"); 
          fw2.write(pkStr);
          fw2.close();        
     }
	/**
	 * 加载公钥
	 * @param publicKeyPath
	 * @return
	 * @throws Exception
	 */
	 public static PublicKey loadPublicKeyByPath(String publicKeyPath)  
	            throws Exception {  
		    /** 将文件中的公钥对象读出 */
		   FileReader  fr = new FileReader(publicKeyPath);
		   BufferedReader br=new BufferedReader(fr);//建立BufferedReader对象，并实例化为br
		    String getPbKey = "";
		      while(true){
		       String aLine = br.readLine();
		       if(aLine==null)break;
		       getPbKey += aLine;
		      }
		      System.out.println( "myBuilderStr :  length: " +  getPbKey.length() +""+getPbKey); 
		       BASE64Decoder   b64d = new  BASE64Decoder();
		      byte [] keyByte =  b64d.decodeBuffer(getPbKey); 
		      X509EncodedKeySpec  x509ek  = new X509EncodedKeySpec(keyByte);
		      KeyFactory keyFactory = KeyFactory.getInstance(RSA); 
		      PublicKey  publicKey = keyFactory.generatePublic(x509ek);
		      return publicKey;
	    }
	 /**
	  * 加载密钥
	  * @param privatekeyPath
	  * @return
	  * @throws Exception
	  */
	 public static PrivateKey loadPrivateKeyByPath(String privatekeyPath) throws Exception {  
		    FileReader  fr = new FileReader(privatekeyPath);
		    BufferedReader br=new BufferedReader(fr);//建立BufferedReader对象，并实例化为
		     String getPvKey = "";
		       while(true){
		        String aLine = br.readLine();
		        if(aLine==null)break;
		        getPvKey += aLine;
		       }
		         BASE64Decoder   b64d = new  BASE64Decoder();
		        byte [] keyByte =  b64d.decodeBuffer(getPvKey); 
		        PKCS8EncodedKeySpec  s8ek  = new PKCS8EncodedKeySpec(keyByte);
		        KeyFactory keyFactory = KeyFactory.getInstance(RSA); 
		        PrivateKey  privateKey = keyFactory.generatePrivate(s8ek);
		        return privateKey;
	    }  
	/**
	 * 公钥加密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  static String encrypt(String source,PublicKey publickey) throws Exception{
	      Cipher cipher = Cipher.getInstance("RSA"); 
	      cipher.init(Cipher.ENCRYPT_MODE,publickey); 
	      byte[] sbt = source.getBytes();
	      byte [] epByte = cipher.doFinal(sbt); 
	      BASE64Encoder encoder = new BASE64Encoder();
	      String epStr =  encoder.encode(epByte);
	      return epStr;
	 }
	/**
	 * 私钥签名
	 * @param source
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public  static String encrypt(String source,PrivateKey privateKey) throws Exception{
	      Cipher cipher = Cipher.getInstance("RSA"); 
	      cipher.init(Cipher.ENCRYPT_MODE,privateKey); 
	      byte[] sbt = source.getBytes();
	      byte [] epByte = cipher.doFinal(sbt); 
	      BASE64Encoder encoder = new BASE64Encoder();
	      String epStr =  encoder.encode(epByte);
	      return epStr;
	 }
	
	
	/**
	 * 私钥解密
	 * @param cryptograph
	 * @param privatekey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph,PrivateKey privateKey) throws Exception{ 
	        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
	        Cipher cipher = Cipher.getInstance(RSA);
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        BASE64Decoder decoder = new BASE64Decoder();
	        byte[] b1 = decoder.decodeBuffer(cryptograph);
	        /** 执行解密操作 */
	        byte[] b = cipher.doFinal(b1);
	        return new String(b);
	 }
	/**
	 * 公钥解密
	 * @param cryptograph
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph,PublicKey publicKey) throws Exception{ 
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b1 = decoder.decodeBuffer(cryptograph);
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
 }
	
	
	
	public static void main(String[] args) {

	   try { 
	       //new RSAUtil(1024, "D:/");//私匙和公匙保存到D盘下的文件中.
			PublicKey publickey=loadPublicKeyByPath("D://public_key.dat");
			PrivateKey privatekey=loadPrivateKeyByPath("D://private_key.dat");
	        String content =  encrypt("20131212",privatekey);
	        System.out.println("code:"+content);
	        String  drpStr = decrypt(content,publickey);
	        System.out.println("date:"+drpStr);
	      }catch (IOException ex) {
	       ex.printStackTrace();
	     }catch (NoSuchAlgorithmException ex) {
	      ex.printStackTrace();
	     }catch(Exception ex){
	      ex.printStackTrace();
	    }
	  }
	

}
