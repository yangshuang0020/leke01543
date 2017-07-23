package org.tio.http.server.demo1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.demo1.init.JfinalInit;
import org.tio.http.server.demo1.model.Donate;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;

/**
 * @author tanyaowu 
 * 2017年7月22日 上午10:44:55
 */
public class DonateService {
	private static Logger log = LoggerFactory.getLogger(DonateService.class);
	public static final DonateService me = new DonateService();
	private final Donate dao = new Donate().dao();
	
	public Page<Donate> page(int pageNumber, int pageSize) {
		SqlPara sqlPara = dao.getSqlPara("donate.page");
		return dao.paginate(pageNumber, pageSize, sqlPara);
	}

	/**
	 * 
	 * @author: tanyaowu
	 */
	public DonateService() {
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {
		JfinalInit.init();
		
		Page<Donate> page = DonateService.me.page(1, 10);
		System.out.println(page);
	}
}
