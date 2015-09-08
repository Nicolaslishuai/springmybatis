package test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lishuai.base.dao.RuleDao;
import lishuai.base.entity.UserEntity;
import lishuai.common.util.StoreService;
import lishuai.message.AbstractMessage;
import lishuai.message.MessageFactory;
import lishuai.message.AbstractMessage.Event;
import lishuai.message.entity.UpdateMessage;
import lishuai.service.Userservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context.xml")
public class ServiceTest {
	
	 @Autowired
	 private Userservice userservice;
	 
	 @Autowired
	 private RuleDao ruledao;
	 
     @Resource(name = "StoreService")
     private StoreService storeService;
	
	 @Before //在每个测试用例方法之前都会执行    
	 public void init(){
		 System.out.println("init..."); 
	 }    
	        
	 @After //在每个测试用例执行完之后执行    
	 public void destory(){ 
		 System.out.println("destory...");
	 }  
	 @Test
	 public void insert(){
		 List<UserEntity> list=new ArrayList<UserEntity>();
		 UserEntity userentity=null;
		 for(int i=0;i<5;i++){
			 userentity=new UserEntity(i+"1","小明",i+10,"那里");
			 list.add(userentity);
		 }
		 try {
			 userservice.addUserBatch(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	@Test
	public void selectById() {
		 UserEntity u=userservice.findby("01");
         System.out.println(u.toString());
	 }
	@Test
	public void selectPage(){
		UserEntity userentity=new UserEntity("11",null,0,"那里");
		Page<UserEntity> page;
		try {
			page = userservice.selectPage(userentity,1, 2);
			System.out.println(page.getPageNum());
			System.out.println(page.getPageSize());
			System.out.println(page.getTotal());
			
			for(UserEntity e :page){
				System.out.println(e.toString());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	@Test
	public void select(){
		List<Map<String, String>>list=ruledao.findUrlRole();
		System.out.println(list.toArray().toString());
	}
	@Test
	public void md5(){
		Md5PasswordEncoder e=new Md5PasswordEncoder();
		System.out.println(e.encodePassword("123", "小明"));
	}
	@Test
	public void testCache(){
		storeService.setStoreCode("a", "1000");
		System.out.println(storeService.getOrStoreCode("a", null));
	}
	@Test
	public void testMessage(){
		AbstractMessage message=MessageFactory.createDefaultMessage();
		message.setData("Hello world");
		System.out.println(message.toString()); 
	}

}
