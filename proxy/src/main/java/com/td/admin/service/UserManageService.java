package com.td.admin.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("finUserManageService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class UserManageService {
	
	private static final Logger logger = Logger.getLogger(UserManageService.class);

//	public List<User> getUser(String umidOrUserName){
//		SecurityService securityService = UmRmiServiceFactory.getSecurityService();
//		List<User> list = securityService.getSystemUsers(umidOrUserName);
//		return list;
//	}	
//	
//	public List<User> getUserByRolename(String roleName){
//		SecurityService securityService = UmRmiServiceFactory.getSecurityService();
//		List<User> list = securityService.getUsersByRolename(roleName);
//		return list;
//	}
//	
//	public User getUserByUmid(String umid){
//		SecurityService securityService = UmRmiServiceFactory.getSecurityService();
//		User user = securityService.getUserByUmid(umid);
//		return user;
//	}
	
}
