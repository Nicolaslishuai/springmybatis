package lishuai.base.dao;

import java.util.List;

import lishuai.base.entity.Role;

public interface RoleDao {
	
	public List<Role> getUserRole(String username);
	
	public List<Role> getALLRole();

}
