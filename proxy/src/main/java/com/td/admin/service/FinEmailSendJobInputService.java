package com.td.admin.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysEmailSendJobInput;
import com.td.admin.entity.SysParam;
import com.td.admin.mapper.SysEmailSendJobInputMapper;
import com.td.common.constant.CommonConstant;

/** 
 * @description: 邮件服务服务类
 * @author: cmh  2013-11-04
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
@Service
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class FinEmailSendJobInputService {
	private static final Logger logger = Logger.getLogger(FinEmailSendJobInputService.class);
	
	@Autowired
	private SysEmailSendJobInputMapper finEmailSendJobInputMapper;
	
	/**
	 * 创建邮件发送任务记录
	 * @param title 标题
	 * @param content 内容
	 * @param mail 邮件地址
	 */
	public void sendMail(String title, String content, String mail) {
		SysParam jobMaxRetryFinSystemParam = (SysParam) SysCacheWrapper.getValue(CommonConstant.SYSTEM_PARAM_CACHE_KEY,
				CommonConstant.JOB_MAX_RETRY);
		SysEmailSendJobInput emailSend = new SysEmailSendJobInput();
		emailSend.setTitle(title);
		emailSend.setRetry(0);
		emailSend.setMaxretry(Integer.valueOf(jobMaxRetryFinSystemParam.getParamvalue()));
		emailSend.setEmailservercode(CommonConstant.FINANCE_EMAIL_SERVER_CODE);
		emailSend.setStatus(CommonConstant.EMAIL_STATUS_NO);
		emailSend.setCreatetime(new Date());
		emailSend.setTo(mail);
		
		emailSend.setContent(content);
		int success = finEmailSendJobInputMapper.insertSelective(emailSend);
		logger.debug("创建邮件发送任务记录" + success + "条");
	}
}
