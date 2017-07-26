package com.td.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;

import com.talkingdata.casclient.User;
import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysEmailSendJobInput;
import com.td.admin.entity.SysEmailTemplate;
import com.td.admin.mapper.SysEmailSendJobInputMapper;
import com.td.admin.mapper.SysEmailTemplateMapper;

public class EmailSendUtil {
	
	
	public static void sendEmailByJob(String emailTemplateCode,Map<String,String> dataMap,String role){
		sendEmailByJob(emailTemplateCode,dataMap,role, Constants.SYSTEM_SEND_EMAIL);
	}
	
	public static void sendEmailByJob(String emailTemplateCode,Map<String,String> dataMap,String role, String emailServerCode){
		List<User> userList = (List<User>)SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_ROLE_OF_USER, role);
		sendEmailByJob(emailTemplateCode,dataMap,userList, emailServerCode);
	}
	
	public static void sendEmailByJob(String emailTemplateCode,Map<String,String> dataMap,List<User> userList){
		sendEmailByJob(emailTemplateCode,dataMap,userList, Constants.SYSTEM_SEND_EMAIL);
	}
	
	public static void sendEmailByJob(String emailTemplateCode,Map<String,String> dataMap,List<User> userList, String emailServerCode){
		ApplicationContext context = ApplicationContextManager.getAppConext();
		SysEmailTemplateMapper sysEmailTemplateMapper = context.getBean("sysEmailTemplateMapper", SysEmailTemplateMapper.class);
		SysEmailTemplate emailTemplate = sysEmailTemplateMapper.getMailTemplateByCode(emailTemplateCode);
		
		String title=emailTemplate.getTitle();
		String content=emailTemplate.getContent();
		/********* 增加发送附件逻辑处理 20150127 start ***********/
		String attachmentPath = null;
		String attachmentDisplayName = null;
		if(dataMap.containsKey("attachmentPath")) {
			attachmentPath = dataMap.get("attachmentPath");
			dataMap.remove("attachmentPath");
			if(dataMap.containsKey("attachmentDisplayName")) {
				attachmentDisplayName = dataMap.get("attachmentDisplayName");
				dataMap.remove("attachmentDisplayName");
			}
		}
		
		/********* 增加发送附件逻辑处理 20150127 end ***********/
		for(Entry<String, String> entry  : dataMap.entrySet()){
			String key="\\$\\{"+entry.getKey()+"\\}";
			title=title.replaceAll(key, entry.getValue());
			content=content.replaceAll(key, entry.getValue());
		}
		sendEmailByJob(title,content,attachmentPath,attachmentDisplayName,userList,emailServerCode);
	}
	
	

	public static void sendEmailByJob(String title,String content,String role,String emailServerCode){
		List<User> userList = (List<User>)SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_ROLE_OF_USER, role);
		sendEmailByJob(title,content,null,null,userList, emailServerCode);
	}
	

	public static void sendEmailByJob(String title,String content, String attachmentPath, String attachmentDisplayName, List<User> userList, String emailServerCode){
		ApplicationContext context = ApplicationContextManager.getAppConext();
		SysEmailSendJobInputMapper sysEmailSendJobInputMapper = context.getBean("sysEmailSendJobInputMapper", SysEmailSendJobInputMapper.class);
		for (User user : userList) {
			if(user != null && user.getEmail() != null && !user.getEmail().trim().equals("")){

	    		SysEmailSendJobInput emailSend = new SysEmailSendJobInput();

				emailSend.setTitle(title);
			
				emailSend.setRetry(0);
				emailSend.setMaxretry(3);
//				emailSend.setEmailservercode(Constants.SYSTEM_SEND_EMAIL);
				emailSend.setEmailservercode(emailServerCode);
				emailSend.setStatus(Constants.EMAIL_STATUS_NO);
				emailSend.setCreatetime(new Date());
				emailSend.setTo(user.getEmail());
				
				emailSend.setContent(content);
				emailSend.setAttachmentPath(attachmentPath);
				emailSend.setAttachmentDisplayName(attachmentDisplayName);
				sysEmailSendJobInputMapper.insertSelective(emailSend);
			}
		}
	}
	
	public  static  void main(String args[]){
		Map<String,String> map=new HashMap<String, String>();
		map.put("templateCode", "12324");
		sendEmailByJob(Constants.EMAIL_TEMPLATE_KEY_TIMEOUT_REMIND,map,"AAAAA");
	}
}
