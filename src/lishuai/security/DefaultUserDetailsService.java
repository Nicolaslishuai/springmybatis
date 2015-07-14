package lishuai.security;

import java.util.Collection;

import lishuai.base.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;

public class DefaultUserDetailsService implements UserDetailsService  {
	
	@Autowired
    private SysUsersImpl sysuserImple;
	

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		SysUser user=sysuserImple.findUserBy(username);
		if(user==null){
			throw new UsernameNotFoundException("”√ªßŒ¥’“µΩ£∫"+username);
		}
		Collection<GrantedAuthority> auths =sysuserImple.loadUserAuthorities(username);
		user.setAuthorities(auths);
		return user;
	}

	

}
