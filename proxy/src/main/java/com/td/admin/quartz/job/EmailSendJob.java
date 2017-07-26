package com.td.admin.quartz.job;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.td.admin.entity.SysEmailSendJobInput;
import com.td.admin.mail.MailUtils;
import com.td.admin.mapper.SysEmailSendJobInputMapper;
import com.td.admin.mapper.SysParamMapper;
import com.td.common.constant.CommonConstant;
import com.td.common.util.ApplicationContextManager;
import com.td.common.util.DateTimeUtil;

/**
 * @description: 邮件发送定时任务 
 * @author: lianjie 2013-8-29
 * @version: 1.1
 * @modify: 1.1 Finance邮件发送定时任务只辅助想Email项目同步数据,不进行实际发送操作
 * @Copyright: 公司版权拥有
 */

public class EmailSendJob implements Job, StatefulJob {

    private static Logger logger = Logger.getLogger(EmailSendJob.class);
    
    private SysEmailSendJobInputMapper finEmailSendJobInputMapper;
    private SysParamMapper systemParamMapper;

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	logger.info("触发定时任务:邮件发送同步[EmailSendJob]");
    	finEmailSendJobInputMapper = (SysEmailSendJobInputMapper) ApplicationContextManager.getBean("sysEmailSendJobInputMapper");
    	systemParamMapper = (SysParamMapper) ApplicationContextManager.getBean("sysParamMapper");
    	try {
    		//查询所有状态为未发送的邮件进行同步
    		List<SysEmailSendJobInput> emailSendList = finEmailSendJobInputMapper.findNoSend();
    		if(emailSendList!=null){
    			logger.debug("待同步的发送邮件有:"+emailSendList.size()+"封.");
    		}
    		for (int i = 0; emailSendList != null && i < emailSendList.size(); i++) {
    			SysEmailSendJobInput emailSend = emailSendList.get(i);
    			
    			//同步数据到Email项目进行邮件发送
    			 String result = null;
				 SysEmailSendJobInput newSendJobInput = new SysEmailSendJobInput();
				 newSendJobInput.setId(emailSend.getId());
    			 try{
    				 //同步数据
    				NameValuePair[] data = {
    							new NameValuePair("emailservercode", emailSend.getEmailservercode()),
    							new NameValuePair("to", emailSend.getTo()),
    							new NameValuePair("title", emailSend.getTitle()),
    							new NameValuePair("content", emailSend.getContent()),
    							new NameValuePair("status", emailSend.getStatus().toString()),
    							new NameValuePair("bcc", emailSend.getBcc()),
    							new NameValuePair("retry", emailSend.getRetry().toString()),
    							new NameValuePair("maxretry", emailSend.getMaxretry().toString()),
    				 			new NameValuePair("createtime", DateTimeUtil.formatLongDate(emailSend.getCreatetime()))};
    				//查询系统参数配置
    				String emailProjectUrl = systemParamMapper.selectByParamKey(CommonConstant.SYSTEM_PARAM_EMAIL_PROJECT_URL).getParamvalue();
    				//同步
    				result = MailUtils.request(emailProjectUrl +"/mail/addSendJobInput.action",data);
    				JSONObject json = JSONObject.fromObject(result);
	        		//修改状态为已同步
 	    			if(json.containsKey("success")){
	    				logger.debug("邮件发送同步成功");
 	    				newSendJobInput.setStatus(CommonConstant.EMAIL_STATUS_SYNC);
 	    			}
 	    			else{
 	    				logger.error("邮件发送同步失败");
 	    				newSendJobInput.setStatus(CommonConstant.EMAIL_STATUS_NO_SYNC);
 	    			}
    			 }catch (Exception e) {
        			 //修改状态为同步失败
    				 newSendJobInput.setStatus(CommonConstant.EMAIL_STATUS_NO_SYNC);
    				 logger.error(result);
					 logger.error("邮件发送同步失败",e);
				 }
    			 finEmailSendJobInputMapper.updateByPrimaryKeySelective(newSendJobInput);
			}
    		logger.debug("定时任务:邮件发送同步[EmailSendJob],已执行完毕.");
		} catch (Exception e) {
			logger.error("数据库连接异常!",e);
		}
    }
    
    public static void main(String[] args) {
		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		ApplicationContextManager.setAppContext(cxt);
		EmailSendJob job = new EmailSendJob();
		try {
			job.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}
}
