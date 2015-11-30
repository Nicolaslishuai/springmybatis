package lishuai.action;

import lishuai.base.entity.UserEntity;
import lishuai.common.db.MultipleDataSource;
import lishuai.common.util.JSONUtil;
import lishuai.service.Userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
public class UserAction implements ModelDriven<UserEntity> {
	
	private static Logger log=LoggerFactory.getLogger(UserAction.class);
	
	private UserEntity userEntity;
	
	@Autowired
	private Userservice userservice;

	@Override
	public UserEntity getModel() {
		if(userEntity==null){
			userEntity=new UserEntity();
		}
		return userEntity;
	}
	
	public void selectUserlist(){
		log.info("excute selectUserlist.......");
		MultipleDataSource.setDataSourceKey("dataSourcemysqlB");
		UserEntity user=userservice.findby(userEntity.getId());
		JSONUtil.WriteJSON(user);
	}

}
