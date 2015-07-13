package lishuai.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lishuai.action.UserAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private Logger log=LoggerFactory.getLogger(UserInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result;
		  String methodName = invocation.getInvocationContext().getName();
		  log.info(methodName);
		  Object action = invocation.getAction();
	        if(action instanceof UserAction)
	        {
	        	//�����UserAction  dosomething
	        	UserAction useraction = (UserAction)action;
	        	
	        }
	        log.info("����֮ǰdoSomething..........");
	        result=invocation.invoke();//���ñ����صķ���
	        log.info("����֮��doSomething..........");
	        return result;
	}

}
