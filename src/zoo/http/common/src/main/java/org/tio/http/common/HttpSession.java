package org.tio.http.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author tanyaowu 
 *
 */
public class HttpSession {
	
	
	private Map<String, Object> props = new HashMap<>();
	
	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2017年2月21日 上午10:27:54
	 * 
	 */
	public HttpSession() {

	}

	public void setProp(String key, Object value) {
		props.put(key, value);
	}

	public Object getProp(String key) {
		return props.get(key);
	}

	/**
	 * @param args
	 *
	 * @author: tanyaowu
	 * 2017年2月21日 上午10:27:54
	 * 
	 */
	public static void main(String[] args) {

	}

}
