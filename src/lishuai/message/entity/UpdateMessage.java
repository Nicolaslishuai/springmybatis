package lishuai.message.entity;

import java.util.HashMap;
import java.util.Map;

import lishuai.common.util.JSONUtil;
import lishuai.message.AbstractMessage;
/**
 * 更新消息
 * @author li
 *
 */
public class UpdateMessage<T> extends AbstractMessage {
	
	private T updateData;
	
	public UpdateMessage() {
		super(Event.UPDATE);
	}
	
	public T getUpdateData() {
		return updateData;
	}

	@Override
	public String toString() {
		return "event:"+event+"\ndata: "+data+"\nid:"+id+"\n\n";
	}

	public void setData(T data) {
		this.updateData=data;
		this.data=JSONUtil.toJSON(data).replaceAll("\\n", "");
	}

}
