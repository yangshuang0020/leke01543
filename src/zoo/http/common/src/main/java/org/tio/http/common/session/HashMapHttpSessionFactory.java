package org.tio.http.common.session;

import org.tio.core.GroupContext;

/**
 * 
 * @author tanyaowu 
 * 2017年8月5日 上午11:58:43
 */
public class HashMapHttpSessionFactory implements IHttpSessionFactory<HashMapHttpSession> {

	public static HashMapHttpSessionFactory self = new HashMapHttpSessionFactory();

	private HashMapHttpSessionFactory() {
	}

	/**
	 * 
	 * @param serverGroupContext
	 * @return
	 * @author: tanyaowu
	 */
	public HashMapHttpSession create(GroupContext<?, ?, ?> groupContext) {
		return new HashMapHttpSession();
	}
}
