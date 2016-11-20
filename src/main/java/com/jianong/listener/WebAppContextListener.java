package com.jianong.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jianong.util.Const;
import com.jianong.util.Tools;

/**
 * 
 * 类名称：WebAppContextListener.java
 * 
 * @version 1.0
 */
public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
	}

	/**
	 * 获取Spring WebApplicationContext
	 */
	public void contextInitialized(ServletContextEvent event) {
		Const.APP_ID = Tools.readTxtFile(Const.APP_ID_file);
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}

}
