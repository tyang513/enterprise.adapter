package com.td.admin.quartz.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.td.admin.entity.SysJobTempJobInput;
import com.td.admin.mapper.SysJobTempJobInputMapper;
import com.td.admin.quartz.tempjob.ITempJobExecutor;
import com.td.common.exception.CommonException;
import com.td.common.exception.ExceptionUtil;
import com.td.common.util.ApplicationContextManager;

/**
 * @description: Quartz任务 用来检查TempJobInput表的临时任务,并执行
 * @author: liucaixing 2013-8-1
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权拥有
 */

public class TempJob implements Job, StatefulJob {

    private static Logger logger = Logger.getLogger(TempJob.class);

   
  
    private SysJobTempJobInputMapper finJobTempJobInputMapper;

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	finJobTempJobInputMapper=(SysJobTempJobInputMapper) ApplicationContextManager.getBean("finJobTempJobInputMapper");
    	//  if(!=null){
        	
    	logger.info("触发定时任务:TempJob.");
        // 查询所有未完成的临时任务
        List<SysJobTempJobInput> tempJobList = finJobTempJobInputMapper.findValid();
        logger.info("已查找到" + tempJobList.size() + "个可执行的临时任务。");
        // 执行临时任务
        for (int i = 0; i < tempJobList.size(); i++) {
        	SysJobTempJobInput tempJob = tempJobList.get(i);
            ITempJobExecutor tje = null;
            String errorMessage = null;
            try {
                // 实例化临时任务对应执行器
            	
                tje = (ITempJobExecutor) Class.forName(tempJob.getExecutorclassname()).newInstance();
            }
            catch (ClassNotFoundException e) {
                errorMessage = "实例化临时任务:" + tempJob.getExecutorclassname() + "失败，类路径错误，找不到该类。" + ExceptionUtil.getStackTrace(e);
            }
            catch (InstantiationException e) {
                errorMessage = "实例化临时任务:" + tempJob.getExecutorclassname() + "失败，实例化异常，不能创建对象。" + ExceptionUtil.getStackTrace(e);
            }
            catch (IllegalAccessException e) {
                errorMessage = "实例化临时任务:" + tempJob.getExecutorclassname() + "失败，实例化异常，未公开的类或未公开构造方法。" + ExceptionUtil.getStackTrace(e);
            }
            catch (ClassCastException e) {
                errorMessage = "实例化临时任务:" + tempJob.getExecutorclassname() + "失败，类型转换异常，未实现TempJobExecutor接口。" + ExceptionUtil.getStackTrace(e);
            }
            // 临时任务执行结果
            boolean result = false;
            try {
                // 执行临时任务
                result = tje.execute(tempJob.getParam());
            }
            catch (CommonException e) {
            	errorMessage = "执行临时任务:" + tempJob.getExecutorclassname() + "异常。" + ExceptionUtil.getStackTrace(e);
			}
            catch (Exception e) {
                errorMessage = "执行临时任务:" + tempJob.getExecutorclassname() + "异常。" + ExceptionUtil.getStackTrace(e);
            }
            if (errorMessage != null) {
                logger.error(errorMessage);
                // 如异常信息不为空，则产生了异常，创建异常信息对象保存至数据库
                ExceptionUtil.createJobErrorInfo(this.getClass().getName(), errorMessage, tempJob.getId().intValue());
            }
            //执行完成,修改状态
            if (result)
                tempJob.setStatus(1);
            else
                tempJob.setStatus(-1);
            //执行次数+1;
            tempJob.setExecutetime(tempJob.getExecutetime() == null ? 1 : tempJob.getExecutetime() + 1);
            //保存执行结果到数据库
            finJobTempJobInputMapper.updateByPrimaryKeySelective(tempJob);
        }
//        }else{
//        	System.out.println("finJobTempJobInputMapper = null");
//        }
    }

//    public FinJobTempJobInputMapper getTempJobInputMapper() {
//        if (tempJobInputMapper == null)
//            tempJobInputMapper = (FinJobTempJobInputMapper) ApplicationContextManager.getBean("tempJobInputMapper");
//        return tempJobInputMapper;
//    }

}
