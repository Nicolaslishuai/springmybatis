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
 * �ǶԳƼ��ܽ���
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
          kpg = KeyPairGenerator.getInstance(RSA); //�������ܳ׶ԡ�������
          kpg.initialize(in); //ָ���ܳ׳��ȣ�ȡֵ��Χ��512��2048��
          kp = kpg.genKeyPair(); //���ɡ��ܳ׶ԡ������а�����һ�����׺�һ��˽�׵���Ϣ
          public_key = kp.getPublic(); //��ù���
          private_key = kp.getPrivate(); //���˽��
          
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
	 * ���ع�Կ
	 * @param publicKeyPath
	 * @return
	 * @throws Exception
	 */
	 public static PublicKey loadPublicKeyByPath(String publicKeyPath)  
	            throws Exception {  
		    /** ���ļ��еĹ�Կ������� */
		   FileReader  fr = new FileReader(publicKeyPath);
		   BufferedReader br=new BufferedReader(fr);//����BufferedReader���󣬲�ʵ����Ϊbr
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
	  * ������Կ
	  * @param privatekeyPath
	  * @return
	  * @throws Exception
	  */
	 public static PrivateKey loadPrivateKeyByPath(String privatekeyPath) throws Exception {  
		    FileReader  fr = new FileReader(privatekeyPath);
		    BufferedReader br=new BufferedReader(fr);//����BufferedReader���󣬲�ʵ����Ϊ
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
	 * ��Կ����
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
	 * ˽Կǩ��
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
	 * ˽Կ����
	 * @param cryptograph
	 * @param privatekey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph,PrivateKey privateKey) throws Exception{ 
	        /** �õ�Cipher��������ù�Կ���ܵ����ݽ���RSA���� */
	        Cipher cipher = Cipher.getInstance(RSA);
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        BASE64Decoder decoder = new BASE64Decoder();
	        byte[] b1 = decoder.decodeBuffer(cryptograph);
	        /** ִ�н��ܲ��� */
	        byte[] b = cipher.doFinal(b1);
	        return new String(b);
	 }
	/**
	 * ��Կ����
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
        /** ִ�н��ܲ��� */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
 }
	
	
	
	public static void main(String[] args) {

	   try { 
	       //new RSAUtil(1024, "D:/");//˽�׺͹��ױ��浽D���µ��ļ���.
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
