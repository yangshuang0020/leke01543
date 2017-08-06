package org.tio.http.common.session;

/**
 * 
 * @author tanyaowu 
 * 2017年8月5日 上午10:16:26
 */
public class HttpSession {
	private IHttpSession proxy = null; 
	
	/**
	 * @author: tanyaowu
	 */
	public HttpSession(IHttpSession proxy) {
		this.proxy = proxy;
		if (this.proxy == null) {
			this.proxy = new HashMapHttpSession();
		}
	}
	
	public HttpSession() {
		this(null);
	}

	/**
	 * 设置会话属性
	 * @param key
	 * @param value
	 * @author: tanyaowu
	 */
	public void setAtrribute(String key, Object value) {
		proxy.setAtrribute(key, value);
	}

	/**
	 * 获取会话属性
	 * @param key
	 * @return
	 * @author: tanyaowu
	 */
	public Object getAtrribute(String key) {
		return proxy.getAtrribute(key);
	}
	
	/**
	 * 清空所有属性
	 * 
	 * @author: tanyaowu
	 */
	public void clear(){
		proxy.clear();
	}
}
