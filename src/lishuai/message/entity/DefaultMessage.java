package lishuai.message.entity;

import lishuai.message.AbstractMessage;
/**
 * 默认的消息类型
 * @author li
 *
 */
public class DefaultMessage extends AbstractMessage<String> {
	
	private String defaultData;

	public DefaultMessage() {
		super(Event.MESSAGE);
	}
	@Override
	public String toString() {
		return "event: "+event+" \ndata:"+data+"\n\n";
	}
	@Override
	public void setData(String data) {
		this.defaultData=data;
		this.data=data;
	}


}
