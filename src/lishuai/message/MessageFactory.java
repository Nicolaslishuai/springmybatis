package lishuai.message;

import java.util.Map;

import lishuai.message.entity.DefaultMessage;
import lishuai.message.entity.UpdateMessage;

/**
 * ��Ϣ����
 * @author li
 *
 */
public class MessageFactory {
	
	public static AbstractMessage<Map<String,String>> createUpdateMessage(){
		return new UpdateMessage();
	}
	
	public static AbstractMessage<String> createDefaultMessage(){
		return new DefaultMessage();
	}

}
