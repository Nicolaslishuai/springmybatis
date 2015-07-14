package lishuai.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lishuai.base.dao.RoleDao;
import lishuai.base.dao.RuleDao;
import lishuai.base.dao.UserDao;
import lishuai.base.entity.Role;
import lishuai.base.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
@Component("sysuserImple")
public class SysUsersImpl {
	
	@Autowired
	private UserDao userdao;
	
	@Autowired 
	private RoleDao roledao;
	
	public SysUser findUserBy(String username){
		UserEntity user=userdao.findByName(username);
		if(user!=null){
			SysUser sysuser=new SysUser();
			sysuser.setUsername(username);
			sysuser.setPassword(user.getPassword());
			return sysuser;
		}
		return null;
	}
	
	 /** 
     * 根据用户名获取到用户的权限并封装成GrantedAuthority集合 
     * @param username 
     */  
    public Collection<GrantedAuthority> loadUserAuthorities(String username){  
    	List<Role> list=roledao.getUserRole(username);
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();  
        for (Role authority : list){  
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getRolename());  
            auths.add(grantedAuthority);  
        }  
        return auths;  
    }  

}
