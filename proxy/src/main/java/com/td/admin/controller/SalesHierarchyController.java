package com.td.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.talkingdata.common.UserInfoUtil;
import com.talkingdata.vo.AccreditUser;
import com.td.admin.entity.FinSalesHierarchyGroup;
import com.td.admin.entity.FinSalesHierarchyNode;
import com.td.admin.entity.FinSalesHierarchyUser;
import com.td.admin.service.SalesHierarchyService;
import com.td.common.constant.CommonConstant;

/**
 * @description: 销售层级管理
 * @author: LiAnJie 2013-12-11
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权拥有
 */
@Controller
@RequestMapping("salesHierarchy")
public class SalesHierarchyController {

	private static final Logger logger = LoggerFactory.getLogger(SalesHierarchyController.class);

	@Autowired
	private SalesHierarchyService salesHierarchyService;

	@RequestMapping(value = "/findSalesHierarchyTree.do")
	@ResponseBody
	public List<FinSalesHierarchyNode> findSalesHierarchyTree(){
		logger.debug("findSalesHierarchyTree!");
		List<FinSalesHierarchyNode> tree = salesHierarchyService.findSalesHierarchyTree();
		return tree;
	}
	
	/**
     * 得到当前用户的销售层级
     * @return
     */
    @RequestMapping(value = "/getSalesHierarchy.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSalesHierarchy() {
    	Map<String, Object> returnMap = new HashMap<String, Object>();;
        User user = UserInfoUtil.getUser();
        //代理用户
    	AccreditUser accreditUser = UserInfoUtil.getAccreditUser();
    	String userUmid = user.getLoginName();
    	
    	if(accreditUser!=null&&accreditUser.isCurrentUser()){
    		userUmid = accreditUser.getAccreditUserUmid();
    	}
    	FinSalesHierarchyUser finSalesHierarchy = salesHierarchyService.findByUserUmId(userUmid);
        if(finSalesHierarchy != null) {
        	returnMap.put("data", finSalesHierarchy);
        }
        return returnMap;
    }
    
    /**
     * 保存销售层级组
     * @return
     */
    @RequestMapping(value = "/saveGroup.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveGroup(FinSalesHierarchyGroup group) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	salesHierarchyService.saveGroup(group);
    	
		FinSalesHierarchyNode node = new FinSalesHierarchyNode();
		node.setGroupid(group.getId());
		node.setGroupname(group.getGroupname());
		node.setDescription(group.getDescription());
		
    	resultMap.put("group", node);
        return resultMap;
    }
    
    /**
     * 删除销售层级组
     * @return
     */
    @RequestMapping(value = "/delGroup.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delGroup(@RequestParam("groupid")Long groupid) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	salesHierarchyService.delGroup(groupid);
    	resultMap.put(CommonConstant.RESPONSE_SEND_PAGE_SUCCESS_MSG, "删除组成功!");
        return resultMap;
    }
    
    /**
     * 查询销售层级用户
     * @return
     */
    @RequestMapping(value = "/queryUser.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryUser(@RequestParam("userumid") String userumid) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	FinSalesHierarchyUser user = salesHierarchyService.queryUser(userumid);
    	resultMap.put("user", user);
        return resultMap;
    }
    
    /**
     * 保存销售层级用户
     * @return
     */
    @RequestMapping(value = "/saveUser.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveUser(FinSalesHierarchyNode userNode) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	if(salesHierarchyService.queryRelation(userNode.getGroupid(),userNode.getCode())>0){
    		resultMap.put(CommonConstant.RESPONSE_SEND_PAGE_MSG, "重复添加用户");
            return resultMap;
    	}
    	salesHierarchyService.saveUser(userNode);
    	resultMap.put("user", userNode);
        return resultMap;
    }
    
    /**
     * 删除销售层级用户
     * @return
     */
    @RequestMapping(value = "/delUser.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delUser(@RequestParam("groupid")Long groupid,@RequestParam("code")String code) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	salesHierarchyService.delRelation(groupid,code);
    	resultMap.put(CommonConstant.RESPONSE_SEND_PAGE_SUCCESS_MSG, "删除用户成功!");
        return resultMap;
    }
    
    /**
     * 保存销售层级树
     * @return
     */
    @RequestMapping(value = "/saveSalesHierarchy.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveSalesHierarchy(@RequestBody Map<String, Object> form) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
	    	JSONObject json = JSONObject.fromObject(form);
			JSONArray jsonInvoiceList = json.getJSONArray("nodeList");
			salesHierarchyService.saveSalesHierarchy(jsonInvoiceList);
			resultMap.put(CommonConstant.RESPONSE_SEND_PAGE_SUCCESS_MSG, "保存销售层级树成功!");
    	}catch(Exception e){
    		resultMap.put(CommonConstant.RESPONSE_SEND_PAGE_ERROR_MSG, "保存销售层级树失败,数据异常!");
    		logger.error("保存销售层级树失败,数据异常!",e);
    	}
        return resultMap;
    }
}
