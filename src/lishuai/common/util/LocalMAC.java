package lishuai.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 获取系统的MAC地址
 * @author li
 *
 */
public class LocalMAC {
	
	
	public static String getSystemMAC(InetAddress addr) throws SocketException{
		byte[] mac=NetworkInterface.getByInetAddress(addr).getHardwareAddress();
		return byte2hex(mac);
	}
	
	public static void main(String[] args) throws UnknownHostException, SocketException {
		InetAddress addr=InetAddress.getLocalHost();
		System.out.println(addr);
		System.out.println(getSystemMAC(addr));
	}
	
	public static String byte2hex(byte[] b) {  
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

}
