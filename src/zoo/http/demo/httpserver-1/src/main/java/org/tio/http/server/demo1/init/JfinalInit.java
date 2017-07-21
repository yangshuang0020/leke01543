package org.tio.http.server.demo1.init;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
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

	public static  DruidPlugin druidPlugin;
	
	public static void init() {
		
		
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
		druidPlugin = new DruidPlugin(URL, USERNAME, PASSWORD);
		//		druidPlugin.setConnectionProperties(PropKit.get("connectionProperties"));
		druidPlugin.set(INITIALSIZE, MIDIDLE, MAXACTIVEE);
		druidPlugin.setFilters("stat,wall");
		
		
		
//		WallFilter wallFilter = new WallFilter();              // 加强数据库安全
//	    wallFilter.setDbType("mysql");
//	    druidPlugin.addFilter(wallFilter);
//	    druidPlugin.addFilter(new StatFilter());    // 添加 StatFilter 才会有统计数据
	    
		druidPlugin.start();
		
		
		
		
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setBaseSqlTemplatePath(PathKit.getRootClassPath() + File.separator + PropKit.get("sqlfile"));
		arp.addSqlTemplate("all_sqls.sql");
		arp.setShowSql(true);
		arp.start();
		
		EhCachePlugin ehCache = new EhCachePlugin();
		ehCache.start();
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}
}
