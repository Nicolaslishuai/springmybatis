package lishuai.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import lishuai.base.entity.UserEntity;
import lishuai.common.util.JSONUtil;
import lishuai.service.Userservice;

import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private Userservice userservice;
	
	@ResponseBody
	@RequestMapping("/select.htm")
	public Page<UserEntity> selectUser(UserEntity user,int page,int pageSize) throws UnsupportedEncodingException{
		Page<UserEntity> users=userservice.selectPage(user, page, pageSize);
		//System.out.println(URLDecoder.decode(user.getUsername(), "GB2312"));
		return users;
	}
	@ResponseBody
	@RequestMapping(value="/put.htm")
	public String putUser(@RequestBody UserEntity user){
		System.out.println(user.toString());
		return "success";
	}

}
