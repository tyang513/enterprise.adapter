package com.td.admin.quartz.tempjob;

/** 
 * @description:任务接口
 * @author: lcx  2013-8-2
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
public interface ITempJobExecutor {
	/**
	 * 临时任务执行方法
	 * @param param 执行参数
	 * @throws Exception 
	 */
	public boolean execute(String param) throws Exception;
}
