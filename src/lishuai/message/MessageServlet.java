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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �ͻ��˶���Ϣ���ͽ��ж��� 
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
			/*PublishServiceManager.getPublishService(ID).complete();*/
		}
		AsyncContext asyncContext = request.startAsync();
		asyncContext.setTimeout(TIMEOUT);
		asyncContext.addListener(new MyAsyncListener(ID));
		PublishServiceManager.addService(ID, asyncContext);
	}
	/**
	 * �첽���������
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
			log.info("complete:"+event.toString());
			PublishServiceManager.removeService(ID);
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			log.info("error:"+event.toString());
			PublishServiceManager.removeService(ID);
		}

		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {
			log.info("start:"+event.toString());
			ServletResponse response=event.getAsyncContext().getResponse();
			AbstractMessage message=MessageFactory.createDefaultMessage();
			postMessage(response,message);
		}

		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			log.info("timeout:"+event.toString());
			PublishServiceManager.removeService(ID);
		}
		
		private void  postMessage(ServletResponse response,AbstractMessage message){
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.print(message);
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		
	    }

}
