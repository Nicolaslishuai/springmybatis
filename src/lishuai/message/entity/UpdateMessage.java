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
public class UpdateMessage extends AbstractMessage<Map<String,String>> {
	
	private Map<String,String> updateData;
	
	public UpdateMessage() {
		super(Event.UPDATE);
	}
	
	public Map<String, String> getUpdateData() {
		return updateData;
	}

	@Override
	public String toString() {
		return "event: "+event+"\ndata: "+data+"\nid:"+id+"\n\n";
	}

	@Override
	public void setData(Map<String, String> data) {
		this.updateData=data;
		this.data=JSONUtil.toJSON(data);
	}

}
