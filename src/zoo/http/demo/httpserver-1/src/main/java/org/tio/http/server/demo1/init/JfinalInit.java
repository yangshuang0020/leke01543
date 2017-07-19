package org.tio.http.server.demo1.init;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.filter.config.ConfigTools;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * @author tanyaowu 
 * 2017年7月19日 下午5:23:21
 */
public class JfinalInit {
	private static Logger log = LoggerFactory.getLogger(JfinalInit.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public JfinalInit() {
	}

	public static void init() {
		PropKit.use("app.conf");
		
		
//		db.jdbc.pool.initialSize=1
//		db.jdbc.pool.maxActive=100
//		db.jdbc.pool.minIdle=1
//		db.jdbc.driverClassName=com.mysql.jdbc.Driver
//		db.jdbc.filters=slf4j,config
//		db.jdbc.imageDatabase.lobHandler=defaultLobHandler
//		db.jdbc.validationQuery=SELECT 'x'
//
//		#业务库
//		db01.jdbc.url=jdbc:mysql://127.0.0.1:3306/tio_site?generateSimpleParameterMetadata=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull
//		db01.jdbc.driverClassName=${db.jdbc.driverClassName}
//		db01.jdbc.username=root
//		db01.jdbc.password=DrhiDCYwESm+9KuR9pkoqjEMVvgQ/ZtZgHwko49Iu9Z5TSH3yWTt+AuSjEvbWThgtiYscSg/w7TPRQNQSraFWA==
//

		
		
		
		final String URL = PropKit.get("db01.jdbc.url");
		final String USERNAME = PropKit.get("db01.jdbc.username");
		String PASSWORD;
		try {
			PASSWORD = ConfigTools.decrypt(PropKit.get("db01.jdbc.password"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		final Integer INITIALSIZE = PropKit.getInt("db.jdbc.pool.initialSize");
		final Integer MIDIDLE = PropKit.getInt("db.jdbc.pool.minIdle");
		final Integer MAXACTIVEE = PropKit.getInt("db.jdbc.pool.maxActive");
		DruidPlugin druidPlugin = new DruidPlugin(URL, USERNAME, PASSWORD);
		//		druidPlugin.setConnectionProperties(PropKit.get("connectionProperties"));
		druidPlugin.set(INITIALSIZE, MIDIDLE, MAXACTIVEE);
		druidPlugin.setFilters("stat,wall");
		ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
		activeRecordPlugin.setBaseSqlTemplatePath(PathKit.getRootClassPath() + File.separator + PropKit.get("sqlfile"));
		//		activeRecordPlugin.addMapping("extension","id", Extension.class);
		//		activeRecordPlugin.addMapping("policyconfig","id", Policyconfig.class);
		activeRecordPlugin.addSqlTemplate("extension.sql");
		activeRecordPlugin.setShowSql(true);
		EhCachePlugin ehCache = new EhCachePlugin();
		ehCache.start();
		druidPlugin.start();
		activeRecordPlugin.start();

	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}
}
