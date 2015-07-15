package lishuai.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
/**
 * ��Դ���ʾ�����
 * @author li
 *
 */
public class DefaultAccessDecisionManager extends AbstractAccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object obj,
			Collection<ConfigAttribute> collection)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if(authentication==null){//û���κ�Ȩ��
			throw new AccessDeniedException("û���κ�Ȩ�ޣ�");
		}
		 Iterator<?> voters = getDecisionVoters().iterator();
		 do{
			 if(voters.hasNext()){
				AccessDecisionVoter voter=(AccessDecisionVoter)voters.next();
				int result=voter.vote(authentication, obj, collection);
		         switch(result){
		          case 1:return;
		          case -1:throw new AccessDeniedException("û�з��ʸ���Դ��Ȩ�ޣ�");
		          default:
		         }   
			 }
		 }while(true);
		  
	}

}
