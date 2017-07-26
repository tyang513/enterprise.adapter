package com.td.admin.service;

import org.springframework.stereotype.Service;

/**
 * 销售管理（层级管理、临时接管设置等）服务
 * 
 * @author zhouguoping
 */
@Service("salesConfigService")
public class SalesConfigService {
//
//	@Autowired
//	private SysTakeoverMapper finSalesTakeoversettingMapper;
//
//	@Autowired
//	private DcdsUserAndRoleService dcdsUserAndRoleService;
//
//	/**
//	 * 根据条件分页查询对应的记录
//	 * 
//	 * @param userUmId
//	 *            模糊匹配
//	 * @param targetUserUmId
//	 *            模糊匹配
//	 * @param startTime
//	 *            开始时间（大于等于）
//	 * @param endTime
//	 *            开始时间（小于等于）
//	 * @param pageRows
//	 *            每页显示记录数
//	 * @param page
//	 *            显示的页面
//	 * @return
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public PageResult getSalesTakeoverSettings(String userName, String targetUserName, String startTime,
//			String endTime, int pageRows, int page, String sort, String order) {
//		PageResult pageResult = new PageResult();
//		Map<String, Object> params = new HashMap<String, Object>();
//		if (userName != null && !userName.equals("")) {
//			SecurityService securityService = UmRmiServiceFactory.getSecurityService();
//			List<User> userList = securityService.getSystemUsers(userName);
//			String inUserUmid = "";
//			if (userList != null && userList.size() > 0) {
//				for (int i = 0; i < userList.size(); i++) {
//					if (i != 0)
//						inUserUmid += ",";
//					inUserUmid += "'" + userList.get(i).getUmid() + "'";
//				}
//			} else {
//				inUserUmid += "null";
//			}
//			params.put("inUserUmid", inUserUmid);
//		}
//		if (targetUserName != null && !targetUserName.equals("")) {
//			SecurityService securityService = UmRmiServiceFactory.getSecurityService();
//			List<User> userList = securityService.getSystemUsers(targetUserName);
//			String inTargetUserUmid = "";
//			if (userList != null && userList.size() > 0) {
//				for (int i = 0; i < userList.size(); i++) {
//					if (i != 0)
//						inTargetUserUmid += ",";
//					inTargetUserUmid += "'" + userList.get(i).getUmid() + "'";
//				}
//			} else {
//				inTargetUserUmid += "null";
//			}
//			params.put("inTargetUserUmid", inTargetUserUmid);
//		}
//		if (StringUtils.isNotBlank(startTime)) {
//			try {
//				params.put("starttime", new SimpleDateFormat("yyyy-MM-dd").parse(startTime));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		if (StringUtils.isNotBlank(endTime)) {
//			try {
//				params.put("endtime", new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		params.put("limitStart", pageRows * (page - 1));
//		params.put("pageRows", pageRows);
//		params.put("sort", sort);
//		params.put("order", order);
//		List<SysTakeoverExtends> list = finSalesTakeoversettingMapper.selectByConditionsPagely(params);
//		fillSalesTakeoversettingExtends(list);
//		int total = finSalesTakeoversettingMapper.selectTotalCountByConditions(params);
//		pageResult.setTotal(total);
//		pageResult.setRows(list);
//		return pageResult;
//	}
//
//	/**
//	 * 根据UMID精确查找对应的记录
//	 * 
//	 * @param userUmId
//	 * @return
//	 */
//	public List<SysTakeoverExtends> getSalesTakeoverSettings(String userUmId) {
//		List<SysTakeoverExtends> list = finSalesTakeoversettingMapper.selectByUmid(userUmId);
//		fillSalesTakeoversettingExtends(list);
//		return list;
//	}
//
//	/**
//	 * 填充临时接管扩展信息
//	 * 
//	 * @param list
//	 */
//	private void fillSalesTakeoversettingExtends(List<SysTakeoverExtends> list) {
//		Map<String, Object> userInfo = null;
//		for (SysTakeoverExtends s : list) {
//			userInfo = dcdsUserAndRoleService.getUserInfo(s.getUserumid());
//			if (userInfo != null) {
//				s.setUsername(userInfo.get("username") == null ? (userInfo.get("USERNAME") == null ? null : userInfo
//						.get("USERNAME").toString()) : userInfo.get("username").toString());
//				s.setEmail(userInfo.get("email") == null ? (userInfo.get("EMAIL") == null ? null : userInfo
//						.get("EMAIL").toString()) : userInfo.get("email").toString());
//			}
//			userInfo = dcdsUserAndRoleService.getUserInfo(s.getTargetuserumid());
//			if (userInfo != null) {
//				s.setTargetUsername(userInfo.get("username") == null ? (userInfo.get("USERNAME") == null ? null : userInfo
//						.get("USERNAME").toString()) : userInfo.get("username").toString());
//				s.setTargetEmail(userInfo.get("email") == null ? (userInfo.get("EMAIL") == null ? null : userInfo.get(
//						"EMAIL").toString()) : userInfo.get("email").toString());
//			}
//		}
//	}
//
//	/**
//	 * 新增临时接管配置
//	 * 
//	 * @param record
//	 */
//	public void saveSalesTakeoverSetting(SysTakeover record) {
//		record.setCtime(new Date());
//		record.setMtime(new Date());
//		finSalesTakeoversettingMapper.insertSelective(record);
//	}
//
//	/**
//	 * 新增后根据业务key获取记录ID
//	 * 
//	 * @param vo
//	 * @return
//	 */
//	public SysTakeoverExtends getSalesTakeoversettingByBusinessKey(SysTakeover vo) {
//		SysTakeoverExtends nvo = finSalesTakeoversettingMapper.selectByBusinessKey(vo);
//		List<SysTakeoverExtends> list = new ArrayList<SysTakeoverExtends>();
//		list.add(nvo);
//		fillSalesTakeoversettingExtends(list);
//		return nvo;
//	}
//
//	/**
//	 * 更新临时接管配置
//	 * 
//	 * @param record
//	 */
//	public void updateSalesTakeoverSetting(SysTakeover record) {
//		record.setMtime(new Date());
//		finSalesTakeoversettingMapper.updateByPrimaryKeySelective(record);
//	}
//
//	/**
//	 * 删除临时接管配置
//	 * 
//	 * @param id
//	 */
//	public void removeSalesTakeoverSetting(Long id) {
//		finSalesTakeoversettingMapper.deleteByPrimaryKey(id);
//	}
//
//	public int selectTakeOverUserByTime(SysTakeover record) {
//		return finSalesTakeoversettingMapper.selectTakeOverUserByTime(record);
//	}
//
//	public int selectUserByTime(SysTakeover record) {
//		return finSalesTakeoversettingMapper.selectUserByTime(record);
//	}
//
//	public List<SysTakeover> findAccreditUserByUmid(String umid) {
//		return finSalesTakeoversettingMapper.findAccreditUserByUmid(umid);
//	}
//
//	/**
//	 * 查询用户的接管者
//	 * 
//	 * @param umid
//	 * @return
//	 */
//	public List<SysTakeoverExtends> getTakeoverUsers(String umid) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userumid", umid);
//		params.put("currentTime", new Date());
//		params.put("limitStart", 0);
//		params.put("pageRows", 10000);// 保证足够多条数出来
//		List<SysTakeoverExtends> list = finSalesTakeoversettingMapper.getTakeoverUsers(params);
//		return list;
//	}
}
