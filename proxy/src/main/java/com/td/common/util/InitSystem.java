package com.td.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.talkingdata.casclient.CasClientExtentionFilter;
import com.td.admin.entity.SysEmailServerConfig;
import com.td.admin.mapper.SysEmailServerConfigMapper;
import com.td.common.constant.CommonConstant;
import com.td.common.controller.WebSecurityController;



@Component("initSystem")
public class InitSystem implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(InitSystem.class);
	
	public static Map varMap = new HashMap();
	
	@Autowired
	private Properties sysConfig;
	
	@Autowired
	private SysEmailServerConfigMapper sysEmailServerConfigMapper; 

	public void afterPropertiesSet() throws Exception {

		String dcdsdata= null;
		String dcdsuserurl = null;
		String activitiUrl = null;
		try {
			//获取访问资源地址
			dcdsdata = sysConfig.getProperty("dcds.sdata.finance.menuurl");
			//获取邮件服务配置地址
			String code = sysConfig.getProperty("mail.serverconfig.code");
			dcdsuserurl = sysConfig.getProperty("sso.showuser.finance.url");
			activitiUrl = sysConfig.getProperty("activitiUrl");
			if(code!=null&&!code.equals("")){
				//获取邮件服务器配置
				SysEmailServerConfig fesc = sysEmailServerConfigMapper.find(code);
				if(fesc!=null){
					varMap.put(CommonConstant.EMAIL_SERVER_CONFIG,fesc);
				}else{
					logger.error("未查到邮件服务配置 code = " +code);
				}
			}else{
				logger.error("未配置邮件服务 code,请在配置文件中加入mail.serverconfig.code且需要数据库FIN_EMAIL_SERVER_CONFIG中需要对应数据");
			}
			logger.info("Initialize system data SUCCESS");
		} catch (Exception e) {
			logger.error("Initialize system data ERROR[{}]", e);
		}
		CasClientExtentionFilter.dcdsdata=dcdsdata;
		WebSecurityController.dcdsuserurl=dcdsuserurl;
		WorkFlowManageUtil.activitiUrl = activitiUrl;
		try{
			
		} catch (Exception e){
			logger.error("Initialize system data ERROR[{}]", e);
		}
	}
	
	
	

}
