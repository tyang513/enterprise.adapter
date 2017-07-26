package com.td.um.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.um.dao.ResourceDao;
import com.td.um.entity.Resource;

/**
 * 
 * <br>
 * <b>功能：</b>ResourceService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("resourceService")
public class ResourceService extends BaseService<Resource> {
	private final static Logger logger = LoggerFactory.getLogger(ResourceService.class);
	@Autowired
	private ResourceDao dao;

	// @Autowired
	// private RoleUserDao<T> roleUserDao;
	//
	// @Autowired
	// private RoleResourceDao<T> roleResourceDao;
	//
	// @Autowired
	// private AppDao<T> appDao;
	//
	//
	// @Autowired
	// private AppService<App> appService;
	//
	// @Autowired
	// private ResourceTypeDao<T> resourceTypeDao;

	public ResourceDao getDao() {
		return dao;
	}

	//
	// public int insert(T t) throws Exception {
	// return getDao().insert(t);
	// }

	/**
	 * 组装树节点并返回
	 * 
	 * @param page
	 * @return
	 */
	public List<Resource> queryTreeList(Resource page) {
		List<Resource> list = dao.findList(page);
		List<Resource> resources = new ArrayList<Resource>();
		if (list != null && list.size() > 0) {
			page.setParentResourceRid(0);
			Resource root = dao.findList(page).get(0);
			for (Resource temp : list) {
				findParentChildren(temp, list);
				// 前端显示第几级
				if (temp.getRid().equals(root.getRid())) {
					resources.add(temp);
				}
			}
		} else {
			// 如果不存在创建根节点
			Resource resource = new Resource();
			resource.setAppRid(page.getAppRid());
			resource.setResourceTypeRid(page.getResourceTypeRid());
			resource.setResourceCode("root");
			resource.setResourceDesc(resource.getResourceCode());
			resource.setResourceUri(resource.getResourceCode());
			resource.setResourceName(resource.getResourceCode());
			resource.setParentResourceRid(0);
			resource.setResourceOrder(0);
			dao.insert(resource);
			resources.add(resource);
		}
		return resources;
	}

	/**
	 * 递归组装树节点
	 * 
	 * @param root
	 * @param list
	 */
	private void findParentChildren(Resource root, List<Resource> list) {
		List<Resource> results = new ArrayList<Resource>();
		for (Resource temp : list) {
			if (temp.getParentResourceRid().equals(root.getRid())) {
				results.add(temp);
			}
		}
		root.setChildren(results);
	}

	public boolean isHasCode(Resource entity) {
		Resource e = new Resource();
		if (entity.getRid() != null) {
			Resource old = (Resource) dao.selectByPrimaryKey(entity.getRid());
			e.setEditMark(old.getResourceCode());
		}
		e.setResourceCode(entity.getResourceCode());
		e.setAppRid(entity.getAppRid());
		List<Resource> list = dao.findList(e);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public Resource getRootNode(Resource page) {
		List<Resource> list = dao.findList(page);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			Resource resource = new Resource();
			resource.setAppRid(page.getAppRid());
			resource.setResourceTypeRid(page.getResourceTypeRid());
			resource.setResourceCode("root_" + page.getAppRid() + "_" + page.getResourceTypeRid());
			resource.setResourceName(resource.getResourceCode());
			resource.setResourceUri(resource.getResourceCode());
			resource.setParentResourceRid(0);
			resource.setResourceOrder(0);
			dao.insert(resource);
			return resource;
		}
	}

	// public String getAuthMenu(String sysCode, String type, String
	// userId,String appTaken) throws JsonGenerationException,
	// JsonMappingException, IOException {
	//
	// //验证appTaken
	// AppPage appPage=new AppPage();
	// appPage.setAppCode(sysCode);
	// List<App> apps=(List<App>) appDao.queryByList(appPage);
	// String json="";
	// if(apps!=null&&apps.size()>0){
	// App app=apps.get(0);
	// if(SecurityUtil.md5(appTaken).equals(app.getAppToken())){
	// json=this.getAuthMenu(sysCode, type, userId);
	// }
	// }
	// return json;
	// }

	public String getAuthMenu(String sysCode, String type, String userId) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysCode", sysCode);
		map.put("type", type);
		map.put("userId", userId);
		List<Resource> resources = dao.getAuthMenu(map);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(resources);
		return json;
	}

	public Map<String, Object> export(List<Integer> ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		List<Resource> resources = dao.getResourceByArray(map);
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(resources);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("jackson转换错误");
		}
		result.put("json", json);
		result.put("node", resources.get(0));
		return result;
	}

	// /**
	// * 资源导入
	// * @param user
	// * @param entity
	// * @return
	// */
	// @Transactional(rollbackFor=Throwable.class,
	// propagation=Propagation.REQUIRED, readOnly=false)
	// public Map<String, Object> importResource(User user, MultipartFile
	// dataFile) throws Exception{
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	//
	// List<Resource> list = readFormInputStream(dataFile);
	//
	// resultMap.put("success", Boolean.valueOf(true));
	// resultMap.put("msg", "导入成功");
	//
	// return resultMap;
	// }
	//
	// /**
	// * 从json 流中读取数据
	// * @param dataFile
	// * @return
	// * @throws IOException
	// * @throws InvocationTargetException
	// * @throws IllegalAccessException
	// * @throws BusinessException
	// */
	// private List<Resource> readFormInputStream(MultipartFile dataFile) throws
	// Exception {
	// List list = new ArrayList();
	// InputStream is = null;
	//
	// is = dataFile.getInputStream();
	// ObjectMapper mapper = new ObjectMapper();
	// list = mapper.readValue(is, List.class);
	//
	// for(Object obj : list) {
	// Resource res = new Resource();
	// BeanUtils.copyProperties(res, obj);
	//
	// AppPage param= new AppPage();
	// param.setAppCode(res.getAppCode());
	// List<App> appList = appDao.selectByAppCodeOrName(param);
	// App app = null;
	// if(appList != null && appList.size() > 0) {
	// app = appList.get(0);
	// }
	//
	// //导入资源的验证，app应用系统是否存在，app应用系统是对应的资源十分有导入权限
	// importDataCheck(app);
	//
	// res.setParentResourceRid(null); // 无用的关联数据清空
	// res.setAppRid(app.getRid()); // 本系统相对应的appRid
	// res.setUpdateTime(new Date());
	// insertResource(res, list);
	//
	// }
	//
	// return list;
	// }

	// /**
	// * 导入资源的验证，app应用系统是否存在，app应用系统是对应的资源十分有导入权限
	// * @param app
	// * @throws BusinessException
	// */
	// private void importDataCheck(App appParam) throws BusinessException {
	// if(appParam == null) {
	// throw new BusinessException("导入的资源所对应应用系统不存在");
	// }
	//
	// Map<String,Object> map=new HashMap<String, Object>();
	// map.put("umId", UserInfoUtil.getUser().getUmid());
	// map.put("status", 0);
	// List<App> appList=appDao.getAppAdminByUmid(map);
	//
	// boolean existApp = false;
	// for(App app : appList) {
	// if(appParam.getAppName().equals(app.getAppName())) {
	// existApp = true;
	// }
	// }
	// if(existApp == false) {
	// throw new BusinessException("没有权限导入"+appParam.getAppName()+"的资源数据");
	// }
	// }
	//
	// /**
	// * 资源数据进行处理后插入
	// * @param res
	// * @throws BusinessException
	// */
	// private void insertResource(Resource res, List list) throws Exception {
	// Integer appRid = res.getAppRid();
	//
	// //获取资源类型
	// ResourceType rt = new ResourceType();
	// rt.setResourceTypeCode(res.getResourceTypeCode());
	// List<ResourceType> resourceTypeList =
	// resourceTypeDao.findByResourceTypeCode(rt);
	// Integer resourceTypeRid = null;
	// if(resourceTypeList == null || resourceTypeList.size() == 0) {
	// ResourceType rtParam = new ResourceType();
	// rtParam.setResourceTypeCode(res.getResourceCode());
	// rtParam.setResourceTypeDesc(res.getResourceTypeName());
	// rtParam.setResourceTypeName(res.getResourceTypeName());
	// rtParam.setStatus(0);
	// rtParam.setCreateTime(new Date());
	// rtParam.setUpdateTime(new Date());
	// rtParam.setOpUmid(UserInfoUtil.getUser().getUmid());
	// resourceTypeDao.insert((T)rtParam);
	// resourceTypeRid = rtParam.getRid();
	// } else {
	// resourceTypeRid = resourceTypeList.get(0).getRid();
	// }
	// res.setResourceTypeRid(resourceTypeRid);
	// if("root".equals(res.getResourceCode())){
	// res.setParentResourceRid(0);
	// saveOrUpdateResource(res);
	// } else {
	// Resource parentResource = findParent(res);
	// if(parentResource == null) {
	// throw new BusinessException("没有根节点，请从根节点开始导入");
	// }
	// res.setParentResourceRid(parentResource.getRid()); // 设置父节点
	//
	// saveOrUpdateResource(res);
	// }
	// }

	// /**
	// * 根据res 的rid 查询，如果有数据则更新，如果没数据则插入
	// * @param res
	// */
	// private void saveOrUpdateResource(Resource res) {
	// Resource resource = findByAppRidResourceTypeIdResourceCode(res);
	// if(resource == null){
	// res.setRid(null);
	// dao.insert((T)res);
	// } else {
	// res.setRid(resource.getRid());
	// dao.updateByPrimaryKeySelective((T)res);
	// }
	// }

	// /**
	// * 查找父节点
	// * @param res
	// * @return
	// */
	// private Resource findParent(Resource res) throws Exception{
	// Integer appRid = res.getAppRid();
	// Integer resourceTypeRid = res.getResourceTypeRid();
	// Map param = new HashMap();
	// param.put("appRid", appRid);
	// param.put("resourceTypeRid", resourceTypeRid);
	// param.put("resourceCode", res.getParentResourceCode());
	// List<Resource> list = dao.findByAppRidResourceTypeIdResourceCode(param);
	// if(list.size() == 0 ){
	// return null;
	// } else {
	// return list.get(0);
	// }
	// }

	// /**
	// * 根据父资源id，获取父资源code
	// * @param parentResourceRid
	// * @param list
	// * @return
	// * @throws InvocationTargetException
	// * @throws IllegalAccessException
	// */
	// private String getParentCode(int parentResourceRid, List listImport)
	// throws IllegalAccessException, InvocationTargetException {
	// String parentResourceCode = null;
	// for(Object obj : listImport) {
	// Resource res = new Resource();
	// BeanUtils.copyProperties(res, obj);
	// if(res.getRid().intValue() == parentResourceRid) {
	// parentResourceCode = res.getResourceCode();
	// }
	// }
	// if(StringUtils.isEmpty(parentResourceCode)) {
	// parentResourceCode = "root";
	// }
	//
	// return parentResourceCode;
	// }

	// /**
	// * 根据 appRid， resourceTypeRid， resourceCode 查询资源表
	// * @param res
	// * @return
	// */
	// private Resource findByAppRidResourceTypeIdResourceCode(Resource res) {
	// Integer appRid = res.getAppRid();
	// Integer resourceTypeRid = res.getResourceTypeRid();
	// String resourceCode = res.getResourceCode();
	// Map param = new HashMap();
	// param.put("appRid", appRid);
	// param.put("resourceTypeRid", resourceTypeRid);
	// param.put("resourceCode", resourceCode);
	// List<Resource> list = dao.findByAppRidResourceTypeIdResourceCode(param);
	// if(list.size() == 0 ){
	// return null;
	// } else {
	// return list.get(0);
	// }
	// }

	// public void deleteResources(String[] id) throws Exception {
	// this.deleteByPrimaryKey(id);
	// //删除资源角色中的关联关系
	// Map<String,Object> map=new HashMap<String, Object>();
	// map.put("resourceIds", id);
	// roleResourceDao.deleteByResource(map);
	// }

	/**
	 * 得到某个角色下的资源
	 * 
	 * @param appCode
	 *            应用系统code
	 * @param roleCode
	 *            角色code
	 * @param resourceTypeCode
	 *            资源类型code
	 * @return
	 */
	public List<Resource> getResourceByRole(String appCode, String roleCode, String resourceTypeCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appCode", appCode);
		map.put("roleCode", roleCode);
		map.put("resourceTypeCode", resourceTypeCode);
		List<Resource> list = dao.getResourceByRole(map);
		return list;
	}

}
