package lishuai.common.util;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("StoreService")
public class StoreService {
	
	@Cacheable(value="codeCache",key="#key")
	public String getOrStoreCode(String key,String code){
		return code;
	}
	@CachePut(value="codeCache",key="#key")
	public String setStoreCode(String key,String code){
		return code;
	}

}
