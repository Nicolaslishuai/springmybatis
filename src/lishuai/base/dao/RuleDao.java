package lishuai.base.dao;

import java.util.List;

import lishuai.base.entity.Rule;

public interface RuleDao {
	
	public List<Rule> findByUsername(String username);

}
