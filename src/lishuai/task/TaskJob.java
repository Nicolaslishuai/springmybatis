package lishuai.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lishuai.message.PublishServiceManager;
import lishuai.message.entity.DefaultMessage;
import lishuai.message.entity.UpdateMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("TaskJob")
public class TaskJob {
	private static Logger log=LoggerFactory.getLogger(TaskJob.class);
	
	@Scheduled(cron = "0/10 * * * * ?") 
	public void sendMessage() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		log.info("任务开始。。。。。。。。。。"+PublishServiceManager.getPublishservice().size());
		/*DefaultMessage<String> defaultmessage=new DefaultMessage<String>();
		defaultmessage.setData(new Date().toGMTString());*/
		UpdateMessage<Map<String,String>> message=new UpdateMessage<Map<String,String>>();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("name", "nihao");
		map.put("age", "12");
		message.setData(map);
		System.out.println(message);
		/*System.out.println(defaultmessage);
		PublishServiceManager.sendMessage(defaultmessage);*/
		PublishServiceManager.sendMessage(message);
	}
	


}
