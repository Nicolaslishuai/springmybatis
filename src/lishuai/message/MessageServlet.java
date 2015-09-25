package lishuai.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lishuai.message.entity.DefaultMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端对消息推送进行定制 
 * @author li
 *
 */
@WebServlet(urlPatterns = { "/message.ms" }, asyncSupported = true)
public class MessageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final long TIMEOUT=10*60*1000;
	private static Logger log=LoggerFactory.getLogger(MessageServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/event-stream");
		response.setHeader("Cache-Control", "no-cache");
		String ID=request.getParameter("ID");
		if(ID==null||"".equals(ID)){
			return;
		}
		if(PublishServiceManager.isHaveService(ID)){
			PublishServiceManager.getPublishService(ID).complete();
		}
		AsyncContext asyncContext = request.startAsync();
		asyncContext.setTimeout(TIMEOUT);
		asyncContext.addListener(new MyAsyncListener(ID));
		PublishServiceManager.addService(ID, asyncContext);
	}
	/**
	 * 异步处理监听器
	 * @author li
	 *
	 */
 static	class MyAsyncListener implements AsyncListener{
	 
	  private String ID;
	  
	  public MyAsyncListener(String ID){
		  this.ID=ID;
	  }
	   
		@Override
		public void onComplete(AsyncEvent event) throws IOException {
			PublishServiceManager.removeService(ID,event.getAsyncContext());
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			PublishServiceManager.removeService(ID,event.getAsyncContext());
		}

		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {
			ServletResponse response=event.getAsyncContext().getResponse();
			AbstractMessage message=new DefaultMessage<String>();
			postMessage(response,message);
		}

		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			PublishServiceManager.removeService(ID,event.getAsyncContext());
		}
		
		private void  postMessage(ServletResponse response,AbstractMessage message) throws IOException{
			PrintWriter writer = response.getWriter();
				writer.print(message);
				writer.flush();
		}
	}

}
