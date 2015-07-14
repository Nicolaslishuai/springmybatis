package lishuai.base.dao;

import java.util.List;
import java.util.Map;

import lishuai.base.entity.Rule;

public interface RuleDao {
	
	public List<Map<String,String>> findUrlRole();

}
