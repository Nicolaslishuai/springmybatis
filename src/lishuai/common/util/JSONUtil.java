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
 * <p> Title:������  </p>
 * <p> Description:��objectת��Ϊjson���ݸ�ʽ </p>
 * @���� li
 * @����ʱ�� 2015-7-4
 * @�汾 1.00
 * @�޸ļ�¼
 * <pre>
 * �汾   �޸���    �޸�ʱ��    �޸���������
 * ----------------------------------------
 * 
 * </pre>
 */
public class JSONUtil {
	
	private static Logger log=LoggerFactory.getLogger(JSONUtil.class);
	
	 /**
	  * 
	  * WriteJSON
	  * ������������ĳ������ת��ΪJSON��ʽ���ַ�����������ֱ��д��HttpResponse����
	  * �߼�����: 
	  * @author li
	  * @return void
	  * @throws �쳣����  �쳣����
	  */
	public static void 	WriteJSON(Object obj){
		try {
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().getWriter().println(toJSON(obj));
		} catch (Exception e) {
			log.error("�ڽ�����"+obj+"ת��ΪJSON��ʽ�ַ�����ʱ�����쳣��"+e.getMessage());
			throw new RuntimeException("�ڽ�����"+obj+"ת��ΪJSON��ʽ�ַ�����ʱ�����쳣��");
		}
	}
	/**
	 * 
	 * toJSON
	 * ������������ĳ������ת��ΪJSON��ʽ���ַ���
	 * �߼�����: 
	 * @author li
	 * @return String
	 * @throws �쳣����  �쳣����
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
			log.error("�ڽ�����"+obj+"ת��ΪJSON��ʽ�ַ�����ʱ�����쳣��"+e.getMessage());
			throw new RuntimeException("�ڽ�����"+obj+"ת��ΪJSON��ʽ�ַ�����ʱ�����쳣��");
		}
		return data;
	}

}