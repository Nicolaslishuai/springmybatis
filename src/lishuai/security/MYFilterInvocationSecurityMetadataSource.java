package lishuai.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lishuai.base.dao.RoleDao;
import lishuai.base.dao.RuleDao;
import lishuai.base.entity.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

/**
 * 资源对应所需权限关系
 * @author li
 *
 */
public class MYFilterInvocationSecurityMetadataSource implements
                                   FilterInvocationSecurityMetadataSource {
	private Logger log=LoggerFactory.getLogger(MYFilterInvocationSecurityMetadataSource.class);
	
	 private static Map<String, Collection<ConfigAttribute>> resourceMap = null; 
	
	@Autowired
	private RoleDao roledao;
	@Autowired
	private RuleDao ruledao;

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		log.info("***加载所有角色权限***");
		List<Role> list=roledao.getALLRole();
		Set<ConfigAttribute> set=new HashSet<ConfigAttribute>();
		for(Role role:list){
			set.add(new SecurityConfig(role.getRolename()));
		}
		return set;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		if(resourceMap==null){
			resourceMap=loadResource();
		}
		HttpServletRequest request = ((FilterInvocation)object).getRequest();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
		    String requestURL = ite.next();
		    RequestMatcher requestMatcher = new AntPathRequestMatcher(requestURL);
		    if(requestMatcher.matches(request)) {
		        return resourceMap.get(requestURL);
		    }
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		log.info(arg0.getName());
		return true;
	}
	
	private Map<String, Collection<ConfigAttribute>> loadResource(){
		List<Map<String,String>>list=ruledao.findUrlRole();
		Map<String, Collection<ConfigAttribute>> resourcemap=new HashMap<String, Collection<ConfigAttribute>>();
		Set<ConfigAttribute> set=null;
		for(Map<String,String> map:list){
			String role=map.get("rolelist");
			String[] rolelist=role.split(",");
		    set=new HashSet<ConfigAttribute>();
			for(String r:rolelist)
			set.add(new SecurityConfig(r));
			resourcemap.put(map.get("url"), set);
		}
		return resourcemap;
	}
	

}
