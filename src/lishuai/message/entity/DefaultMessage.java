package lishuai.message.entity;

import lishuai.common.util.JSONUtil;
import lishuai.message.AbstractMessage;
/**
 * Ĭ�ϵ���Ϣ����
 * @author li
 * @param <T>
 *
 */
public class DefaultMessage<T> extends AbstractMessage {
	
	private T defaultData;

	public DefaultMessage() {
		super(Event.MESSAGE);
	}

	public void setData(T data) {
		this.defaultData=data;
		this.data=JSONUtil.toJSON(data).replaceAll("\\n", "");
	}
	@Override
	public String toString() {
		return "event:"+event+"\ndata:"+data+"\n\n";
	}


}
