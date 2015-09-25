package lishuai.message;

public abstract class AbstractMessage {
	
	protected String id;
	
	protected String event;
	
	protected String data="";
	
	protected long retry=1000;
	
	protected AbstractMessage(Event event){
		this.event=event.name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public String getData() {
		return data;
	}
	
	public long getRetry() {
		return retry;
	}

	public void setRetry(long retry) {
		this.retry = retry;
	}
	
	@Override
	public abstract String toString();
	
	/**
	 * 定义的事件类型
	 * 
	 * @author li
	 * 
	 */
	public enum Event{
		MESSAGE("message"),ADD("add"),DELETE("delete"),UPDATE("update");
		private final String name;
		
		public String getName() {
			return name;
		}

		private Event(String name){
			this.name=name;
		}
	}

}
