package com.td.common.util;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.td.admin.quartz.entrance.JobEntrance;

/**
 * @description: 应用程序初始、销毁监听
 * @author: LiAnJie 2013-6-24
 * @version: 1.0
 * @modify:
 * @Copyright:
 */

public class CommonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CommonServlet.class);

	/**
	 * 启动web应用时程序时初始化
	 */
	public void init(ServletConfig servletConfig) {
		JobEntrance jobEntrance = (JobEntrance) ApplicationContextManager.getBean("jobEntrance");
		String startJob = servletConfig.getInitParameter("startJob");
		logger.debug("启动参数startJob==" + startJob);
		if (StringUtils.isNotBlank(startJob) && startJob.equals("1")) {
			logger.info("应用程序加载作业管理器!");
			jobEntrance.execute();
		}
	}

	/**
	 * 销毁web应用时终止程序
	 */
	public void destroy() {
		// 关闭缓存
		CacheManager.getInstance().shutdown();
	}
}
