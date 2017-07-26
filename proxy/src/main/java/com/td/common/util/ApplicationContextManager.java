package com.td.common.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * 管理应用上下文(spring)
 * @author liucaixing
 * 
 */
public class ApplicationContextManager {

	private static Logger logger = Logger.getLogger(ApplicationContextManager.class);

	/**
	 * 应用上下文
	 */
	private static ApplicationContext appContext = null;

	/**
	 * 获取应用上下文
	 * @param servletContext 
	 * @return
	 */
	public static synchronized ApplicationContext initApplicationContext() {
		if (appContext == null) {
			logger.info("使用web context 初始化");
			appContext = ContextLoader.getCurrentWebApplicationContext();
		}
		if (appContext == null){
			// 单独启动一个application context,主要给hadoop应用使用
			logger.info("未获取web context的spring 上下文.初始化spring,使用ClassPathXmlApplicationContext进行初始化");
			appContext = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		}
		return appContext;
	}

	public static void setAppContext(ApplicationContext appContext) {
		logger.info("setAppContext: " + appContext);
		ApplicationContextManager.appContext = appContext;
	}
	
	/**
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		if (appContext == null) {
			appContext = initApplicationContext();
		}
		Object bean = appContext.getBean(beanName);
		return bean;
	}
	
	/**
	 * 根据类型获取bean
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType) {
		if (appContext == null) {
			appContext = initApplicationContext();
		}
		return appContext.getBean(requiredType);
	}
	
	public static ApplicationContext getAppConext(){
		if (appContext == null){
			logger.info("未获取spring context");
			initApplicationContext();
		}
		return ApplicationContextManager.appContext;
	}

	public static void main(String[] args) {
		ApplicationContextManager.getAppConext();
	}
	
}
