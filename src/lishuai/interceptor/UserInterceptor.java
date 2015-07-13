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
	        	//如果是UserAction  dosomething
	        	UserAction useraction = (UserAction)action;
	        	
	        }
	        log.info("调用之前doSomething..........");
	        result=invocation.invoke();//调用被拦截的方法
	        log.info("调用之后doSomething..........");
	        return result;
	}

}
