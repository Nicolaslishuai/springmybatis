package lishuai.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
/**
 * 对定制消息的客户端进行管理
 * @author li
 *
 */
public class PublishServiceManager {
	
	private static final Map<String, AsyncContext> publishService = new ConcurrentHashMap<String,AsyncContext>();

	public static Map<String, AsyncContext> getPublishservice() {
		return publishService;
	}
	
	public static AsyncContext getPublishService(String ID) {
		return publishService.get(ID);
	}
	
	public static boolean isHaveService(String ID){
		return publishService.containsKey(ID);
	}
	
	public synchronized static void addService(String id,AsyncContext asyncContext){
		publishService.put(id, asyncContext);
	}
	public synchronized static void removeService(String ID){
		publishService.remove(ID);
	}
	/**
	 * 向所有定制的推送
	 * @param message
	 */
	public static void sendMessage(AbstractMessage message){
		Iterator<Entry<String, AsyncContext>> it=publishService.entrySet().iterator();
		while(it.hasNext()){
			AsyncContext ca=it.next().getValue();
			ServletResponse response=ca.getResponse();
			broadcast(response,message);
			ca.complete();
		}
	}
	/**
	 * 向某个推送
	 * @param id
	 * @param message
	 */
	public static void sendMessage(String ID,AbstractMessage message){
		if(publishService.containsKey(ID)){
			AsyncContext ca=publishService.get(ID);
			ServletResponse response=ca.getResponse();
			broadcast(response,message);
			ca.complete();
		}
	}
	
	private static void broadcast(ServletResponse response,AbstractMessage message){
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(message);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
