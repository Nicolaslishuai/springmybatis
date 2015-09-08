package lishuai.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lishuai.message.AbstractMessage;
import lishuai.message.MessageFactory;
import lishuai.message.PublishServiceManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("TaskJob")
public class TaskJob {
	private static Logger log=LoggerFactory.getLogger(TaskJob.class);
	
	@Scheduled(cron = "0/10 * * * * ?") 
	public void sendMessage(){
		log.info("任务开始。。。。。。。。。。"+PublishServiceManager.getPublishservice().size());
		/*AbstractMessage<String> message=MessageFactory.createDefaultMessage();
		message.setData(new Date().toGMTString());*/
		AbstractMessage<Map<String, String>> message=MessageFactory.createUpdateMessage();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("name", "nihao");
		map.put("age", "12");
		message.setData(map);
		System.out.println(message);
		PublishServiceManager.sendMessage(message);
	}
	


}
