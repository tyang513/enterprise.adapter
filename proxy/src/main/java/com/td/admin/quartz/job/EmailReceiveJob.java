package com.td.admin.quartz.job;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.td.admin.entity.SysEmailReceiveJobInput;
import com.td.admin.mapper.SysEmailReceiveJobInputMapper;
import com.td.common.constant.CommonConstant;
import com.td.common.util.ApplicationContextManager;
import com.td.common.util.WorkFlowManageUtil;

/**
 * @description: 邮件接收定时任务
 * @author: lianjie 2013-10-18
 * @modify:
 * @Copyright: 公司版权拥有
 */

public class EmailReceiveJob implements Job, StatefulJob {

    private static Logger logger = Logger.getLogger(EmailReceiveJob.class);
    private SysEmailReceiveJobInputMapper emailReceiveJobInputMapper;
    
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	logger.info("触发定时任务:邮件接收[EmailReceiveJob]");
    	emailReceiveJobInputMapper = (SysEmailReceiveJobInputMapper) ApplicationContextManager.getBean("finEmailReceiveJobInputMapper");
    	try {
    		//查询已接收邮件
    		List<SysEmailReceiveJobInput>  EmailReceiveList =  emailReceiveJobInputMapper.findByReceive();
    		logger.debug("已接收邮件:"+EmailReceiveList.size()+"封!");
    		for (int i = 0; i < EmailReceiveList.size(); i++) {
    			SysEmailReceiveJobInput emailReceive = EmailReceiveList.get(i);
    			String subject = emailReceive.getSubject();
    			//任务号
    			String finTaskId = subject.substring(subject.indexOf("任务号:")+4);
    			if(StringUtils.isNumeric(finTaskId)){
        			emailReceive.setTaskId(Long.parseLong(finTaskId));
    			}
    			if(subject.indexOf("任务号:")==-1){
    				logger.error("回复的审批邮件标题不合法:"+subject+"!");
    				emailReceive.setStatus(CommonConstant.EMAIL_RECEIVE_STATUS_ERROR);
    				emailReceiveJobInputMapper.updateByPrimaryKeySelective(emailReceive);
    				continue;
    			}
    			if(StringUtils.isBlank(finTaskId)){
    				logger.error("回复的审批邮件:"+subject+"任务号为空!");
    				emailReceive.setStatus(CommonConstant.EMAIL_RECEIVE_STATUS_ERROR);
    				emailReceiveJobInputMapper.updateByPrimaryKeySelective(emailReceive);
    				continue;
    			}
    			//审批内容
    			String content = emailReceive.getContent();
    			//是否审批通过 0不通过 1通过
    			String approve = "0";
    			String remark = "";
    			content = content.replaceAll("同意/不同意", "");
    			if(content.indexOf("不同意")!=-1){
    				approve="0";
    				remark="移动审批:审批不通过";
    			} 
    			else if(content.indexOf("同意")!=-1){
    				approve="1";
    				remark="移动审批:审批通过"; 
    			}
    			else{
    				logger.error("回复的审批邮件:"+subject+"内容不合法!");
    				emailReceive.setStatus(CommonConstant.EMAIL_RECEIVE_STATUS_ERROR);
    				emailReceiveJobInputMapper.updateByPrimaryKeySelective(emailReceive);
    				continue;
    			}
    			try{
    				//处理审批任务
    				String jsonResult = WorkFlowManageUtil.completeApprove(finTaskId,approve,remark);
    				logger.debug("任务执行结果:"+jsonResult);
        			emailReceive.setStatus(CommonConstant.EMAIL_RECEIVE_STATUS_PROCESSED);
    				emailReceiveJobInputMapper.updateByPrimaryKeySelective(emailReceive);
    			}catch (Exception e) {
    				logger.error("审批任务:"+finTaskId+",移动审批失败:",e);
    				emailReceive.setStatus(CommonConstant.EMAIL_RECEIVE_STATUS_ERROR);
    				emailReceiveJobInputMapper.updateByPrimaryKeySelective(emailReceive);
				}
    			
    		}
    		logger.debug("定时任务:邮件接收[EmailReceiveJob],已执行完毕.");
		} catch (Exception e) {
			logger.error("定时任务:邮件接收[EmailReceiveJob],执行异常.",e);
		}
    }
    
    public static void main(String[] args) {
		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		ApplicationContextManager.setAppContext(cxt);
		EmailReceiveJob job = new EmailReceiveJob();
		try {
			job.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}
}
