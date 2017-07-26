package com.td.admin.service;

import org.springframework.stereotype.Service;

/**
 * DCDS用户和角色服务，主要用于查询
 * @author zhouguoping
 */
@Service
public class DcdsUserAndRoleService {
//	
//	/**
//	 * 根据UMID获取用户完整信息，包括：ID、UMID、USERNAME、EMAIL
//	 * @param umid
//	 * @return
//	 */
//	public Map<String, Object> getUserInfo(String umid) {
//		if (StringUtils.isBlank(umid)) {
//			return null;
//		}
//		Map resultInfo = new HashMap();
//
//		User user = UmRmiServiceFactory.getSecurityService().getUserByUmid(umid);
//
//		resultInfo.put("username", user.getUserName());
//		resultInfo.put("umid", user.getUmid());
//		resultInfo.put("email", user.getEmail());
//		resultInfo.put("id", user.getRecordid());
//
//		return resultInfo;
//	}
//	
//	/**
//	 * 根据UMID获取对应用户，用户信息包括：ID、UMID、USERNAME、EMAIL
//	 * @param umid
//	 * @return
//	 */
//	public User getUsersByUmid(String umid){
//		User user = UmRmiServiceFactory.getSecurityService().getUserByUmid(umid);
//		return user;
//	}
//
//	/**
//	 * 根据UMID或者用户名称获取对应用户，用户信息包括：ID、UMID、USERNAME、EMAIL
//	 * @param umidOrUsername	umid或者username
//	 * @return
//	 */
//	public List<Map<String, Object>> getUsersByUmidOrUsername(String umidOrUsername){
//		List<User> listUser = UmRmiServiceFactory.getSecurityService().getSystemUsers(umidOrUsername);
//		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
//		Map resultInfo = null;
//		for (User user : listUser) {
//			resultInfo = new HashMap();
//			resultInfo.put("username", user.getUserName());
//			resultInfo.put("umid", user.getUmid());
//			resultInfo.put("email", user.getEmail());
//			resultInfo.put("id", user.getRecordid());
//			listMap.add(resultInfo);
//		}
//		return listMap;
//	}

}
