package com.td.um.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.um.dao.ResourceDao;
import com.td.um.entity.Resource;

/**
 * 远程接口实现类
 * 
 * @author changpengfei
 * 
 */
@Service("securityService")
public class SecurityService {
	
	private final static Logger logger = Logger.getLogger(SecurityService.class);
	@Autowired
	private ResourceDao dao;

	// @Autowired
	// private AppDao<?> appDao;
	//
	// @Autowired
	// private UmUserDao<UmUser> umUserDao;
	//
	// @Autowired
	// private MailManageService mailManageService;
	//
	// @Autowired
	// private RoleDao<?> roleDao;
	//
	// @Autowired
	// private RoleUserDao<?> roleUserDao;
	//
	// @Autowired
	// private RoleUserService<?> roleUserService;
	//
	// @Autowired
	// private UmUserService<?> umUserService;

	@Autowired
	private Properties sysConfig;

	// @Autowired
	// private RoleService<?> roleService;
	//
	// @Autowired
	// private OuterPartnerService<?> outerPartnerService;
	//
	// @Autowired
	// private AppService<?> appService;
	//
	// @Autowired
	// private SessionIdCacheService sessionIdCacheService;

	@Autowired
	private ResourceService resourceService;

//	/**
//	 * 得到某个角色下的资源
//	 * 
//	 * @param appCode
//	 *            应用系统code
//	 * @param roleCode
//	 *            角色code
//	 * @param resourceTypeCode
//	 *            资源类型code
//	 * @return
//	 */
//	public List<ExtResource> getResourceByRole(String appCode, String roleCode, String resourceTypeCode) throws Exception {
//		List<Resource> list = resourceService.getResourceByRole(appCode, roleCode, resourceTypeCode);
//		List<ExtResource> resources = new ArrayList<ExtResource>();
//		for (Resource resource : list) {
//			// 兼容老系统
//			resource.setResourceDesc(resource.getResourceUri());
//			ExtResource extResource = new ExtResource();
//			try {
//				BeanUtils.copyProperties(extResource, resource);
//			} catch (Exception e) {
//				throw e;
//			}
//			resources.add(extResource);
//		}
//		return resources;
//	}

	// /**
	// * 修改密码
	// * @param umid
	// * @param oldPassword
	// * @param newPassword
	// * @return
	// * @throws UnsupportedEncodingException
	// * @throws IllegalArgumentException
	// * @throws IllegalAccessException
	// * @throws NoSuchMethodException
	// * @throws SecurityException
	// * @throws InvocationTargetException
	// * @throws BusinessException
	// */
	// public void changeUserPassword(String umid, String oldPassword, String
	// newPassword) throws BusinessException{
	// UmUser umUser = umUserDao.findByUmidWithPassword(umid);
	// try {
	// if(!umUser.getUserPassword().equalsIgnoreCase(LdapUtil.hashAndEncodePassword(oldPassword)))
	// {
	// throw new BusinessException("老密码不正确");
	// }
	// umUser.setUserPassword(LdapUtil.hashAndEncodePassword(newPassword));
	//
	// UmUser userUpdate = new UmUser();
	//
	// // 构建外部商户dn 必须特殊处理
	// userUpdate.setDn(LdapUtil.buildDnExtends(umUser));
	// userUpdate.setUserPassword(umUser.getUserPassword());
	// umUserDao.updateSelective(userUpdate);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new BusinessException("密码修改失败，请联系管理员");
	// }
	// }

	// /**
	// * 兼容老系统
	// * 建议使用 getRoles
	// * @param rolename
	// * @return
	// */
	// @Deprecated
	// public com.pingan.jrkj.datacenter.dcds.vo.security.Role
	// getRoleByRolename(String rolename){
	// Map<String,Object> map=new HashMap<String, Object>();
	// map.put("roleCode", rolename);
	// Role list= roleDao.getRoleByCode(rolename);
	// com.pingan.jrkj.datacenter.dcds.vo.security.Role role=null;
	// if(list!=null){
	// role=new com.pingan.jrkj.datacenter.dcds.vo.security.Role();
	// role.setRoledesc(list.getRoleCode());
	// role.setRolename(list.getRoleName());
	// }
	// return role;
	// }
	//
	// /**
	// * 通过描述信息模糊查询角色
	// * @param roledesc
	// * @return
	// */
	// public List<com.pingan.jrkj.datacenter.dcds.vo.security.Role>
	// getRolesByRoledesc(String roledesc){
	// Map<String,Object> map=new HashMap<String, Object>();
	// map.put("roleName", roledesc);
	// List<Role> list= this.getRoles(map);
	// List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> tlist=new
	// ArrayList<com.pingan.jrkj.datacenter.dcds.vo.security.Role>();
	// if(list!=null&&list.size()>0){
	// for(Role tRole:list){
	// com.pingan.jrkj.datacenter.dcds.vo.security.Role role=new
	// com.pingan.jrkj.datacenter.dcds.vo.security.Role();
	// role.setRoledesc(tRole.getRoleName());
	// role.setRolename(tRole.getRoleCode());
	// tlist.add(role);
	// }
	// }
	// return tlist;
	// }

	// public void removeUser(String systemCode,String systemToken,String umid)
	// throws BusinessException{
	// if(this.validateSystemTaken(systemCode,systemToken)){
	// UmUser umUser=new UmUser();
	// umUser.setUmid(umid);
	// umUserDao.delete(LdapUtil.buildDn(umUser).toString());
	// }else{
	// throw new BusinessException("没有权限删除用户，请联系um系统管理员");
	// }
	// }
	//
	//
	// /**
	// * 默认密码 umid + 123
	// */
	// public void addOrUpdateUser(List<String> toBeAddedRoles,String
	// systemCode,String systemToken,User user) throws BusinessException{
	// if(toBeAddedRoles!=null&&toBeAddedRoles.size()>0){
	// if(this.validateSystemTaken(systemCode,systemToken)){
	// umUserService.addOrUpdateUser(user,systemCode,toBeAddedRoles);
	// }else{
	// throw new BusinessException("应用系统密码错误");
	// }
	// }
	//
	// }
	//
	// /**
	// * 增加 用户
	// * @param user 新增 用户信息
	// */
	// public void addOrUpdateUserWithPassword(User user, String operatorUmid,
	// String operatorPwd) throws BusinessException {
	// umUserService.addOrUpdateUserWithPassword(user, operatorUmid,
	// operatorPwd);
	// }
	//
	//
	// public void removeUserRoles(List<String> toBeRemovedRoles,String
	// systemCode,String systemToken,String umid) throws BusinessException{
	// if(toBeRemovedRoles!=null&&toBeRemovedRoles.size()>0){
	// if(this.validateSystemTaken(systemCode,systemToken)){
	// roleUserService.removeUserRoles(umid,toBeRemovedRoles,systemCode);
	// }else{
	// throw new BusinessException("应用系统编码不存在或令牌错误");
	// }
	// }
	// }

//	private boolean validateSystemTaken(String appCode, String appTaken) {
		// AppPage appPage=new AppPage();
		// appPage.setAppCode(appCode);
		// List<App> apps=(List<App>) appDao.queryByList(appPage);
		// String json="";
		// if(apps!=null&&apps.size()>0){
		// App app=apps.get(0);
		// if(appTaken.equals(app.getAppToken())){
		// return true;
		// }
		// }
//		return true;
//	}

	// /**
	// * 通过角色编码获得角色
	// * map中支持的参数有
	// * umId 精确匹配 如：map.put("umId",umId);
	// * umId 模糊匹配 如：map.put("appCode",appCode);
	// * umId 模糊匹配 如：map.put("roleCode",roleCode);
	// * umId 模糊匹配 如：map.put("roleName",roleName);
	// * umId 模糊匹配 如：map.put("role_desc",role_desc);
	// * @param rolecode
	// * @return
	// */
	// public List<Role> getRoles(Map<String,Object> map){
	// List<Role> roles=roleDao.getRoles(map);
	// return roles;
	// }

//	/**
//	 * 通过角色编码获得该角色下的用户
//	 * @param rolecode
//	 * @return
//	 * @throws BusinessException
//	 */
//	public List<User> getUsersByRolecode(String rolecode) throws BusinessException {
//		List<User> users = new ArrayList<User>();
//		List<RoleUser> list = roleUserDao.getUsersByRoleCode(rolecode);
//		if (list != null && list.size() > 0) {
//			for (RoleUser roleUser : list) {
//				User user = this.getUserByUmId(roleUser.getUserRid());
//				if (user != null)
//					users.add(user);
//			}
//		}
//		return users;
//	}

//	/**
//	 * 根据UMID获取某应用下的对应的角色
//	 * 
//	 * @param umid
//	 * @return
//	 * @throws BusinessException
//	 */
//	@Override
//	public List<Role> getRolesByUmid(String umId, String appCode) throws BusinessException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("umId", umId);
//		map.put("appCode", appCode);
//		List<Role> list = roleDao.getRoleByUmIdAndAppcode(map);
//		return list;
//	}

	@SuppressWarnings("unchecked")
	public List<LinkedHashMap<String, Object>> getAuthMenu4Map( String type, String userId) {
		// 验证appTaken
//		AppPage appPage = new AppPage();
//		appPage.setAppCode(sysCode);
		List<LinkedHashMap<String, Object>> results = new LinkedList<LinkedHashMap<String, Object>>();
//		if (this.validateSystemTaken(sysCode, appTaken)) {
			try {
				List<Resource> list = this.getAuthMenu(type, userId);
				ObjectMapper mapper = new ObjectMapper();
				results = mapper.convertValue(list, results.getClass());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("菜单获取失败");
			}
//		} 
		return results;
	}

//	public void resetPassword(String umId) throws BusinessException {
//		try {
//			mailManageService.resetPassword(umId);
//		} catch (BusinessException e) {
//			e.printStackTrace();
//			throw e;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BusinessException("重置密码失败");
//		}
//	}

//	public List<User> getUsersByRoleCode(String appCode, String appTaken, String roleCode) throws BusinessException {
//		// 验证appTaken
//		AppPage appPage = new AppPage();
//		appPage.setAppCode(appCode);
//		List<User> users = new ArrayList<User>();
//		if (this.validateSystemTaken(appCode, appTaken)) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("roleCode", roleCode);
//			map.put("appCode", appCode);
//			List<RoleUser> list = roleUserDao.getUsersByRoleCode(map);
//			if (list != null && list.size() > 0) {
//				for (RoleUser roleUser : list) {
//					users.add(this.getUserByUmId(roleUser.getUserRid()));
//				}
//			}
//
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//		return users;
//	}
//
//	public List<User> getUsersByAppCode(String appCode, String appTaken) throws BusinessException {
//		// 验证appTaken
//		List<User> users = new ArrayList<User>();
//		if (this.validateSystemTaken(appCode, appTaken)) {
//
//			List<RoleUser> list = roleUserDao.getUsersByAppCode(appCode);
//			if (list != null && list.size() > 0) {
//				for (RoleUser roleUser : list) {
//					users.add(this.getUserByUmId(roleUser.getUserRid()));
//				}
//			}
//
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//		return users;
//	}

	public List<Resource> getAuthMenu(String type, String userId) {

		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("sysCode", sysCode);
		map.put("type", type);
		map.put("userId", userId);
		List<Resource> resources = null;
		// List<App> apps = appService.getAppAdminByUmid(userId);
		// 系统用户 system 特殊处理
//		String code = "proxy";//(String) UserInfoUtil.getSession().getServletContext().getFilterRegistration("casClientExtentionFilter").getInitParameter("appcode");
//		String menu = "MENU";//(String) UserInfoUtil.getSession().getServletContext().getFilterRegistration("casClientExtentionFilter").getInitParameter("menu");
//		code = (sysConfig.get("appcode") == null) ? code : (String) sysConfig.get("appcode");
//		menu = (sysConfig.get("menu") == null) ? menu : (String) sysConfig.get("menu");
		// if (UmConstant.SYSTEM.equals(userId) && code.equals(sysCode) &&
		// type.equals(menu)) {
		// resources = dao.getSystemMenu();
		// } else if (apps != null && apps.size() != 0 && code.equals(sysCode)
		// && type.equals(menu)) {
		// resources = dao.getAdminMenu(map);
		// } else {
		// resources = dao.getAuthMenu(map);
		// }
		resources = dao.findAllResource(map);

		return resources;
	}

//	/**
//	 * 更新用户状态
//	 * 
//	 * @param status
//	 *            0：启用 1：禁用
//	 * 
//	 * @param rid
//	 * @throws BusinessException
//	 */
//	public void updateOuterUserStatusEnable(String umid, Integer status) throws BusinessException {
//		try {
//			umUserService.updateOuterStatus(umid, status + "");
//		} catch (BusinessException e) {
//			throw new BusinessException(e.getMessage());
//		} catch (Exception e) {
//			throw new BusinessException("更新外部用户状态为启用异常:umid=" + umid);
//		}
//	}
//
//	/**
//	 * 根据外部商户名like 查询，得到这些商户列表，并查询这些商户下的所有人 分页显示
//	 * 
//	 * @param departmentName
//	 *            商户名称
//	 * @param pageIndex
//	 *            第几页 索引 从0 开始 ，0 代表第一页
//	 * @param pageSize
//	 *            每页显示条数
//	 * @return rows 内返回 UmUser 集合 ，total 返回总记录数
//	 */
//	@Override
//	public Map<String, Object> getOuterParterList(String departmentName, int pageIndex, int pageSize) throws BusinessException {
//		Map<String, Object> page = new HashMap<String, Object>();
//		page = outerPartnerService.getOuterParterList(departmentName, pageIndex, pageSize);
//		List<UmUser> list = (List<UmUser>) page.get("rows");
//		if (list != null) {
//			List<User> userList = new ArrayList<User>();
//			for (UmUser umUser : list) {
//				User user = new User();
//				try {
//					BeanUtils.copyProperties(user, umUser);
//					userList.add(user);
//				} catch (Exception e) {
//					throw new BusinessException("SecurityServiceImpl：getOuterParterList-  用户实体类转化异常");
//				}
//				;
//			}
//			page.put("rows", userList);
//		} else {
//			page.put("rows", null);
//		}
//
//		return page;
//	}
//
//	/**
//	 * 根据商户代码，查询该商户下所有人。并且根据这些人的中文名称 like查询 分页显示
//	 * 
//	 * @param departmentName
//	 * @param pageIndex
//	 *            第几页 索引 从1 开始
//	 * @param pageSize
//	 *            每页显示条数
//	 * @return
//	 * @throws BusinessException
//	 * @throws IllegalAccessException
//	 * @throws IllegalArgumentException
//	 */
//	public Map<String, Object> getOuterParterListByCodeUserName(String departmentCode, String userName, int pageIndex, int pageSize)
//			throws BusinessException {
//		UmUserPage page = new UmUserPage();
//		page.setDepartmentId("ou=" + departmentCode + ",ou=outer,ou=people");
//		page.setUserName(userName);
//		page.setPage(pageIndex);
//		page.setRows(pageSize);
//		List<UmUser> rows = new ArrayList<UmUser>();
//		try {
//			rows = umUserService.queryOuterUserList(page);
//		} catch (Exception e) {
//			throw new BusinessException(e);
//		}
//		Map<String, Object> resultPage = new HashMap<String, Object>();
//		resultPage.put("rows", rows);
//		resultPage.put("total", page.getPager().getRowCount());
//		return resultPage;
//	}
//
//	/**
//	 * 查询 fromdcds下所有人。并且根据这些人的中文名称 like查询 分页显示
//	 * 
//	 * @param departmentName
//	 * @param pageIndex
//	 *            第几页 索引 从1 开始
//	 * @param pageSize
//	 *            每页显示条数
//	 * @return
//	 * @throws BusinessException
//	 */
//	public Map<String, Object> getOuterParterListByfromdcdsUserName(String userName, int pageIndex, int pageSize) throws BusinessException {
//		String FROM_DCDS = "fromDcds";
//		return getOuterParterListByCodeUserName(FROM_DCDS, userName, pageIndex, pageSize);
//	}
//
//	@Override
//	public List<User> getUsersByUmidOrName(String umidOrUsername) throws BusinessException {
//		List<User> users = new ArrayList<User>();
//		List<UmUser> umUsers = umUserDao.findByUmidOrUserNameLike(umidOrUsername);
//		if (umUsers != null && umUsers.size() > 0) {
//			for (UmUser umUser : umUsers) {
//				User user = new User();
//				try {
//					BeanUtils.copyProperties(user, umUser);
//				} catch (Exception e) {
//					e.printStackTrace();
//					throw new BusinessException("SecurityServiceImpl：getSystemUsers-  用户实体类转化异常");
//				}
//				users.add(user);
//			}
//		}
//		return users;
//	}
//
//	@Override
//	public User getUserByUmId(String umid) throws BusinessException {
//		UmUser umUser = umUserDao.findByUmid(umid);
//		User user = null;
//		if (umUser != null) {
//			user = new User();
//			try {
//				BeanUtils.copyProperties(user, umUser);
//			} catch (Exception e) {
//				logger.error("SecurityServiceImpl：getUserByUmId-  用户实体类转化异常", e);
//				throw new BusinessException("SecurityServiceImpl：getUserByUmId-  用户实体类转化异常");
//			}
//		}
//		return user;
//	}
//
//	private List<Role> getRolesByUmId(String umid) {
//		List<Role> list = roleDao.getRoleByUmId(umid);
//		return list;
//	}

//	public List<ExtResource> getExtResourcesByTypeAndUmid(String type, String umid) {
//		List<ExtResource> resources = new ArrayList<ExtResource>();
//		List<Resource> list = new ArrayList<Resource>();
////		if (this.validateSystemTaken(appCode, appTaken)) {
//			list = this.getAuthMenu(type, umid);
//			if (list != null && list.size() > 0) {
//				for (Resource resource : list) {
//					// 兼容老系统
//					resource.setResourceDesc(resource.getResourceUri());
//					ExtResource extResource = new ExtResource();
//					try {
//						BeanUtils.copyProperties(extResource, resource);
//					} catch (Exception e) {
//						e.printStackTrace();
//						logger.error("类型转化异常");
//					}
//					resources.add(extResource);
//				}
//			}
////		}
//		return resources;
//	}

//	@Override
//	public void deleteUserRoles(String systemCode, String systemToken, String umid, List<String> toBeRemovedRoles) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			roleUserService.removeUserRoles(umid, toBeRemovedRoles, systemCode);
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//
//	}

	/**************************************** 老系统接口实现 ********************************************************/
//
//	@Override
//	public com.pingan.jrkj.datacenter.dcds.vo.security.User getUserByUmid(String umid) {
//		UmUser umUser = umUserDao.findByUmid(umid);
//		com.pingan.jrkj.datacenter.dcds.vo.security.User user = null;
//		if (umUser != null) {
//			user = new com.pingan.jrkj.datacenter.dcds.vo.security.User();
//			user.setEmail(umUser.getEmail());
//			user.setUmid(umUser.getUmid());
//			user.setUserName(umUser.getUserName());
//		}
//		return user;
//	}

//	@Override
//	public List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> getRolesByUmid(String umid) throws BusinessException {
//		throw new BusinessException("当前接口已被废弃，请使用新接口");
		// List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> roles=new
		// ArrayList<com.pingan.jrkj.datacenter.dcds.vo.security.Role>();
		//
		// List<Role>list=roleDao.getRoleByUmId(umid);
		// if(list!=null&&list.size()>0){
		// for(Role role:list){
		// com.pingan.jrkj.datacenter.dcds.vo.security.Role tRole=new
		// com.pingan.jrkj.datacenter.dcds.vo.security.Role();
		// tRole.setRoledesc(role.getRoleName());
		// tRole.setRolename(role.getRoleCode());
		// roles.add(tRole);
		// }
		// }
		// return roles;
//
//	}

//	@Override
//	public void addOrUpdateUser(String systemCode, String systemToken, DcdsUser user,
//			List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> toBeAddedRoles) throws BusinessException {
//		User s = new User();
//		s.setEmail(user.getEmail());
//		s.setUmid(user.getUmid());
//		s.setUserDesc(user.getUserDesc());
//		s.setUserName(user.getUserName());
//		s.setUserPassword(user.getPasswd());
//
//		List<String> list = new ArrayList<String>();
//		if (toBeAddedRoles != null && toBeAddedRoles.size() > 0)
//			for (com.pingan.jrkj.datacenter.dcds.vo.security.Role role : toBeAddedRoles) {
//				list.add(role.getRolename());
//			}
//		this.addOrUpdateUser(list, systemCode, systemToken, s);
//	}
//
//	@Override
//	public void removeUserRoles(String systemCode, String systemToken, String umid,
//			List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> toBeRemovedRoles) throws BusinessException {
//		List<String> list = new ArrayList<String>();
//		if (toBeRemovedRoles != null && toBeRemovedRoles.size() > 0)
//			for (com.pingan.jrkj.datacenter.dcds.vo.security.Role role : toBeRemovedRoles) {
//				list.add(role.getRolename());
//			}
//		this.removeUserRoles(list, systemCode, systemToken, umid);
//	}
//
//	@Override
//	public void addOrUpdateRole(String systemCode, String systemToken, com.pingan.jrkj.datacenter.dcds.vo.security.Role role)
//			throws BusinessException {
//		Role role2 = new Role();
//		role2.setRoleCode(role.getRolename());
//		role2.setRoleName(role.getRoledesc());
//		this.addOrUpdateRole(role2, systemCode, systemToken);
//	}
//
//	public List<com.pingan.jrkj.datacenter.dcds.vo.security.User> getSystemUsers(String umidOrUsername) {
//		List<com.pingan.jrkj.datacenter.dcds.vo.security.User> sList = new ArrayList<com.pingan.jrkj.datacenter.dcds.vo.security.User>();
//		List<UmUser> umUsers = umUserDao.findByUmidOrUserNameLike(umidOrUsername);
//		if (umUsers != null && umUsers.size() > 0) {
//			for (UmUser umUser : umUsers) {
//				com.pingan.jrkj.datacenter.dcds.vo.security.User user = new com.pingan.jrkj.datacenter.dcds.vo.security.User();
//				user.setEmail(umUser.getEmail());
//				user.setUmid(umUser.getUmid());
//				user.setUserName(umUser.getUserName());
//				sList.add(user);
//			}
//		}
//		return sList;
//	}
//
//	/**
//	 * 兼容老系统 通过角色编码获得该角色下的用户 推荐使用 getUsersByRolecode
//	 * 
//	 * @param rolename
//	 * @return
//	 */
//	@Deprecated
//	public List<com.pingan.jrkj.datacenter.dcds.vo.security.User> getUsersByRolename(String rolename) {
//		List<com.pingan.jrkj.datacenter.dcds.vo.security.User> users = new ArrayList<com.pingan.jrkj.datacenter.dcds.vo.security.User>();
//		try {
//			List<User> list = this.getUsersByRolecode(rolename);
//			if (list != null && list.size() > 0) {
//				for (User umUser : list) {
//					com.pingan.jrkj.datacenter.dcds.vo.security.User user = new com.pingan.jrkj.datacenter.dcds.vo.security.User();
//					user.setEmail(umUser.getEmail());
//					user.setUmid(umUser.getUmid());
//					user.setUserName(umUser.getUserName());
//					users.add(user);
//				}
//			}
//		} catch (BusinessException e) {
//			e.printStackTrace();
//			logger.error("获取用户异常");
//		}
//		return users;
//	}
//
//	/**
//	 * 兼容老系统，建议使用getRoles方法
//	 */
//	@Deprecated
//	public List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> getRolesByRolename(String rolename) {
//		List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> roles = new ArrayList<com.pingan.jrkj.datacenter.dcds.vo.security.Role>();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("roleCode", rolename);
//		List<Role> list = this.getRoles(map);
//		if (list != null && list.size() > 0) {
//			for (Role role : list) {
//				com.pingan.jrkj.datacenter.dcds.vo.security.Role tRole = new com.pingan.jrkj.datacenter.dcds.vo.security.Role();
//				tRole.setRoledesc(role.getRoleName());
//				tRole.setRolename(role.getRoleCode());
//				roles.add(tRole);
//			}
//		}
//
//		return roles;
//	}
//
//	/**
//	 * 根据UMID和角色名前缀获取对应的角色 如：getPrefixRolesByUmid("test","FINANCE_ROLE_")
//	 * 
//	 * @param umid
//	 * @param roleNamePrefix
//	 *            角色名前缀（按此模糊查询）
//	 * @return
//	 */
//	public List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> getRolesByUmidAndRolenamePrefix(String umid, String roleNamePrefix) {
//		List<com.pingan.jrkj.datacenter.dcds.vo.security.Role> roles = new ArrayList<com.pingan.jrkj.datacenter.dcds.vo.security.Role>();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("umId", umid);
//		map.put("roleCode", roleNamePrefix);
//		List<Role> list = roleDao.getRoles(map);
//		if (list != null && list.size() > 0) {
//			for (Role role : list) {
//				com.pingan.jrkj.datacenter.dcds.vo.security.Role tRole = new com.pingan.jrkj.datacenter.dcds.vo.security.Role();
//				tRole.setRoledesc(role.getRoleName());
//				tRole.setRolename(role.getRoleCode());
//				roles.add(tRole);
//			}
//		}
//		return roles;
//	}
//
//	/**
//	 * 兼容老系统 建议使用 getAuthMenu
//	 * 
//	 * @param resourceTypeId
//	 * @param umid
//	 * @return
//	 */
//	@Deprecated
//	public List<com.pingan.jrkj.datacenter.dcds.vo.security.ExtResource> getExtResourcesByTypeAndUmid(Long resourceTypeId, String umid) {
//		List<com.pingan.jrkj.datacenter.dcds.vo.security.ExtResource> tt = new ArrayList<com.pingan.jrkj.datacenter.dcds.vo.security.ExtResource>();
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("resourceTypeId", resourceTypeId.toString());
//		map.put("umid", umid);
//		List<Resource> list = dao.getExtResourceByType(map);
//		if (list != null && list.size() > 0) {
//			for (Resource extResource : list) {
//				com.pingan.jrkj.datacenter.dcds.vo.security.ExtResource resource = new com.pingan.jrkj.datacenter.dcds.vo.security.ExtResource();
//				resource.setResourceDesc(extResource.getResourceUri());
//				tt.add(resource);
//			}
//		}
//
//		return tt;
//	}
//
//	@Override
//	public List<User> getUserByTenentCode(String systemCode, String systemToken, String tenentCode) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			List<UmUser> list = umUserDao.getUserByTenentCode(tenentCode);
//			List<User> rows = new ArrayList<User>();
//			try {
//				for (UmUser umUser : list) {
//					User u = new User();
//					umUser.setDn(null);
//					BeanUtils.copyProperties(u, umUser);
//					rows.add(u);
//				}
//			} catch (Exception e) {
//				throw new BusinessException("查询异常");
//			}
//			return rows;
//		} else {
//			throw new BusinessException("得到商户下的所有人错误");
//		}
//
//	}
//
//	@Override
//	public void addTenentUser(String systemCode, String systemToken, String tenentCode, User user) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			Name dn = LdapUtil.buildDn("uid=" + user.getUmid() + ",ou=" + tenentCode + ",ou=outer,ou=people");
//
//			// UM帐号唯一判断
//			UmUser hasUser = umUserService.findByUmid(user.getUmid());
//			if (hasUser != null) {
//				throw new BusinessException("UM帐号已存在");
//			}
//
//			UmUser entity = new UmUser();
//			try {
//				BeanUtils.copyProperties(entity, user);
//			} catch (Exception e) {
//				new BusinessException("参数复制异常");
//			}
//
//			entity.setCreateTime(DateTimeUtil.formatDate(new Date()));
//			entity.setUpdateTime(DateTimeUtil.formatDate(new Date()));
//			entity.setCn(entity.getUmid());
//			entity.setDn(dn);
//			entity.setDepartmentId("ou=" + tenentCode + ",ou=outer,ou=people");
//
//			// 默认新增用户状态启用
//			entity.setStatus(UmConstant.STATUS_0 + "");
//
//			String passwordSecret;
//			try {
//				passwordSecret = LdapUtil.hashAndEncodePassword(user.getUserPassword());
//				entity.setUserPassword(passwordSecret);
//			} catch (UnsupportedEncodingException e) {
//			}
//			UmUserService.replaceSpace(entity);
//			umUserDao.insert(entity);
//		} else {
//			throw new BusinessException("在某个商户下增加人错误");
//		}
//
//	}
//
//	@Override
//	public User getUserByTenentCodeAndUmid(String systemCode, String systemToken, String tenentCode, String umid) throws BusinessException {
//
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			List<User> list = getUserByTenentCode(systemCode, systemToken, tenentCode);
//			for (User u : list) {
//				if (u.getUmid().equals(umid))
//					return u;
//			}
//			return null;
//		} else {
//			throw new BusinessException("在某个商户下增加人错误");
//		}
//	}
//
//	@Override
//	public User getUserByUmidIsOuter(String systemCode, String systemToken, String umid, Boolean isOuter) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			UmUser umuser = umUserDao.findByUmid(umid);
//			if (umuser == null) {
//				return null;
//			}
//			User u = new User();
//			try {
//				umuser.setDn(null);
//				BeanUtils.copyProperties(u, umuser);
//			} catch (Exception e) {
//				throw new BusinessException("获取异常");
//			}
//
//			if (umuser.getDepartmentId() != null && umuser.getDepartmentId().indexOf("ou=outer,ou=people") != -1) {
//				if (isOuter) {
//					return u;
//				} else {
//					return null;
//				}
//			} else {
//				if (isOuter) {
//					return null;
//				} else {
//					return u;
//				}
//			}
//		} else {
//			throw new BusinessException("得到内部或外部商户下的某个人错误");
//		}
//	}
//
//	@Override
//	public void updateTenentUser(String systemCode, String systemToken, String tenentCode, User user) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//
//			Name dn = LdapUtil.buildDn("uid=" + user.getUmid() + ",ou=" + tenentCode + ",ou=outer,ou=people");
//			UmUser entity = new UmUser();
//			try {
//				BeanUtils.copyProperties(entity, user);
//			} catch (Exception e) {
//				new BusinessException("参数复制异常");
//			}
//
//			entity.setUpdateTime(DateTimeUtil.formatDate(new Date()));
//			entity.setDepartmentId(null);
//			entity.setDepartmentName(null);
//			if (StringUtils.isEmpty(user.getTitle())) {
//				entity.setTitle(" ");
//			}
//			entity.setUserPassword(null); // 密码不让他更新，必须走重置密码渠道
//			entity.setDn(dn);
//			try {
//				umUserService.updateOuterUser(entity);
//			} catch (Exception e) {
//				throw new BusinessException("更新异常");
//			}
//		} else {
//			throw new BusinessException("更新某个商户下的人错误");
//		}
//	}
//
//	@Override
//	public void deleteTenentUser(String systemCode, String systemToken, String tenentCode, String umid) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			umUserDao.delete("uid=" + umid + ",ou=" + tenentCode + ",ou=outer,ou=people");
//		} else {
//			throw new BusinessException("删除某个商户下的人错误");
//		}
//	}
//
//	/************************************ 角色扩展接口 *****************************************/
//
//	/**
//	 * 分配指定应用系统下的角色，如果存在会抛出BusinessException异常,不存在就追加
//	 * 
//	 * @param systemCode
//	 * @param systemToken
//	 * @param umid
//	 * @param roleCodeList
//	 * @throws BusinessException
//	 */
//	@Override
//	public void allocateRoles(String systemCode, String systemToken, String umid, List<String> roleCodeList) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			roleUserService.allocateRoles(systemCode, umid, roleCodeList);
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//	}
//
//	/**
//	 * 收回某个人某个应用系统下的所有角色
//	 * 
//	 * @param systemCode
//	 * @param systemToken
//	 * @param umid
//	 * @throws BusinessException
//	 */
//	@Override
//	public void recallRoles(String systemCode, String systemToken, String umid) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			roleUserService.recallRoles(systemCode, umid);
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//	}
//
//	/**
//	 * 收回某个人某个系统下的指定角色
//	 * 
//	 * @param systemCode
//	 * @param systemToken
//	 * @param umid
//	 * @param roleCodeList
//	 * @throws BusinessException
//	 */
//	@Override
//	public void recallRoles(String systemCode, String systemToken, String umid, List<String> roleCodeList) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			roleUserService.recallRoles(systemCode, umid, roleCodeList);
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//	}
//
//	/**
//	 * 新增或修改角色
//	 * 
//	 * @param role
//	 * @param systemCode
//	 * @param systemToken
//	 * @throws BusinessException
//	 */
//	@Override
//	public void addOrUpdateRole(Role role, String systemCode, String systemToken) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			AppPage appPage = new AppPage();
//			appPage.setAppCode(systemCode);
//			List<App> apps = (List<App>) appDao.queryByList(appPage);
//			App app = apps.get(0);
//			role.setAppRid(app.getRid());
//			roleService.addOrUpdateRole(role);
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//	}
//
//	/**
//	 * 根绝角色编码删除角色
//	 * 
//	 * @param systemCode
//	 * @param systemToken
//	 * @param rolename
//	 * @throws BusinessException
//	 */
//	@Override
//	public void removeRole(String systemCode, String systemToken, String rolecode) throws BusinessException {
//		if (this.validateSystemTaken(systemCode, systemToken)) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("systemCode", systemCode);
//			map.put("rolecode", rolecode);
//			roleDao.deleteByCode(map);
//		} else {
//			throw new BusinessException("应用系统编码不存在或令牌错误");
//		}
//	}
//
//	/**
//	 * 根据sessionId 获取用户 （用于报表项目wlt_report验证用户是否登录）
//	 * 
//	 * @param sessionId
//	 * @return
//	 */
//	@Override
//	public com.pingan.jrkj.datacenter.dcds.vo.User getUserBySessionId(String sessionId) {
//		return sessionIdCacheService.getUserBySessionId(sessionId);
//	}

//	/**
//	 * 根据sessionId 获取um用户菜单
//	 * 
//	 * @param sessionId
//	 * @param appcode
//	 * @param menu
//	 * @param umid
//	 * @param apptaken
//	 * @return
//	 */
//	@Override
//	public List<com.pingan.wlt.dc.umic.entity.ExtResource> getCupeReportMenu(String umid, String appcode, String menu, String apptaken)
//			throws Exception {
//
//		List<LinkedHashMap<String, Object>> list = getAuthMenu(appcode, menu, umid, apptaken);
//		// 菜单权限处理
//		List<ExtResource> menuList = MenuUtil.formateToMenu(list);
//
//		// 组装成树
//		for (ExtResource menuTmp : menuList) {
//			getChildren(menuTmp, menuList);
//		}
//
//		ExtResource root = getRootMenu(menuList);
//
//		List<ExtResource> list2 = new ArrayList<ExtResource>();
//		for (ExtResource menuTmp : menuList) {
//			if (menuTmp.getPrid().equals(root.getRid())) {
//				list2.add(menuTmp);
//			}
//		}
//
//		// 不带root的数据
//		List<ExtResource> menuListWithOutRoot = new ArrayList<ExtResource>();
//
//		for (ExtResource extRes : list2) {
//			if (!"root".equals(extRes.getResourceCode())) {
//				menuListWithOutRoot.add(extRes);
//			}
//		}
//		return menuListWithOutRoot;
//	}
//
//	private static void getChildren(ExtResource menu, List<ExtResource> list) {
//		List<ExtResource> mList = new ArrayList<ExtResource>();
//		for (ExtResource temp : list) {
//			if (menu.getRid().equals(temp.getPrid())) {
//				mList.add(temp);
//			}
//		}
//		menu.setChildrens(mList);
//	}
//
//	private static ExtResource getRootMenu(List<ExtResource> menuList) {
//		ExtResource menu = null;
//		for (ExtResource temp : menuList) {
//			if (temp.getPrid().equals(0)) {
//				menu = temp;
//			}
//		}
//		return menu;
//	}
}
