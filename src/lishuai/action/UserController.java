package lishuai.action;

import lishuai.base.entity.UserEntity;
import lishuai.common.util.JSONUtil;
import lishuai.service.Userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	
	@Autowired
	private Userservice userservice;
	
	@RequestMapping("/selectUser.htm")
	public void selectUser(String age){
		System.out.println(age+".............");
		UserEntity user=userservice.findby(age);
		JSONUtil.WriteJSON(user);
	}

}
