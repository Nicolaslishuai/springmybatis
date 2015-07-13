package lishuai.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * <p> Title:工具类  </p>
 * <p> Description:将object转化为json数据格式 </p>
 * @作者 li
 * @创建时间 2015-7-4
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本   修改人    修改时间    修改内容描述
 * ----------------------------------------
 * 
 * </pre>
 */
public class JSONUtil {
	
	private static Logger log=LoggerFactory.getLogger(JSONUtil.class);
	
	 /**
	  * 
	  * WriteJSON
	  * 功能描述：将某个对象转换为JSON格式的字符串，并将其直接写入HttpResponse对象
	  * 逻辑描述: 
	  * @author li
	  * @return void
	  * @throws 异常类型  异常描述
	  */
	public static void 	WriteJSON(Object obj){
		try {
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().getWriter().println(toJSON(obj));
		} catch (Exception e) {
			log.error("在将对象"+obj+"转换为JSON格式字符串的时候发生异常！"+e.getMessage());
			throw new RuntimeException("在将对象"+obj+"转换为JSON格式字符串的时候发生异常！");
		}
	}
	/**
	 * 
	 * toJSON
	 * 功能描述：将某个对象转换为JSON格式的字符串
	 * 逻辑描述: 
	 * @author li
	 * @return String
	 * @throws 异常类型  异常描述
	 */
	public static String toJSON(Object obj){
		String data;
		try {
            if(JSONUtils.isObject(obj)){
				data=JSONObject.fromObject(obj).toString();
			}else{
				data=JSONArray.fromObject(obj).toString();
			}
		} catch (Exception e) {
			log.error("在将对象"+obj+"转换为JSON格式字符串的时候发生异常！"+e.getMessage());
			throw new RuntimeException("在将对象"+obj+"转换为JSON格式字符串的时候发生异常！");
		}
		return data;
	}

}