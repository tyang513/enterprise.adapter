package com.td.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.FinSalesHierarchyGroup;
import com.td.admin.entity.FinSalesHierarchyNode;
import com.td.admin.entity.FinSalesHierarchyRelation;
import com.td.admin.entity.FinSalesHierarchyUser;
import com.td.admin.mapper.FinSalesHierarchyGroupMapper;
import com.td.admin.mapper.FinSalesHierarchyRelationMapper;
import com.td.admin.mapper.FinSalesHierarchyUserMapper;

/**
 * @description:
 * @author: LiAnJie 2013-12-11
 * @version: 1.0
 * @modify:
 * @Copyright: 
 */
@Service
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class SalesHierarchyService {

    private static final Logger logger = Logger.getLogger(SalesHierarchyService.class);
    @Autowired
    private FinSalesHierarchyUserMapper salesHierarchyUserMapper;
    
    @Autowired
    private FinSalesHierarchyGroupMapper salesHierarchyGroupMapper;
    
    @Autowired
    private FinSalesHierarchyRelationMapper salesHierarchyRelationMapper;
    
    //树形索引
    private long index = 0;
    
	public List<FinSalesHierarchyNode> findSalesHierarchyTree() {
		logger.debug("findSalesHierarchyTree!");
		List<FinSalesHierarchyNode> tree = new ArrayList<FinSalesHierarchyNode>(); 
		index = 0;
		tree = this.findSalesHierarchyNode(tree,(long)0,index);
		return tree;
	}
	
	/**
	 * 根据父组ID查询 对应的用户和下属组,拼装成 List<FinSalesHierarchyNode>
	 * @return 对应的用户和下属组
	 */
	private List<FinSalesHierarchyNode> findSalesHierarchyNode(List<FinSalesHierarchyNode> tree,Long superGroupId,long superIndex) {
		//用户集合
		List<FinSalesHierarchyUser> userList = salesHierarchyUserMapper.findByGroupId(superGroupId);
		for (int i = 0; i < userList.size(); i++) {
			FinSalesHierarchyUser user = userList.get(i);
			FinSalesHierarchyNode node = new FinSalesHierarchyNode();
			node.setId(++index);
			node.setpId(superIndex);
			node.setCode(user.getCode());
			node.setUserumid(user.getUserumid());
			node.setUsername(user.getUsername());
			node.setName(user.getUsername());
			node.setDrop(false);
			node.setRelationid(user.getRelationid());
			tree.add(node);
		}
		//组集合
		List<FinSalesHierarchyGroup> groupList = salesHierarchyGroupMapper.findBySuperGroupId(superGroupId);
		for (int i = 0; i < groupList.size(); i++) {
			FinSalesHierarchyGroup group = groupList.get(i);
			FinSalesHierarchyNode node = new FinSalesHierarchyNode();
			node.setId(++index);
			node.setpId(superIndex);
			node.setGroupid(group.getId());
			node.setGroupname(group.getGroupname());
			node.setName(group.getGroupname());
			node.setDescription(group.getDescription());
			tree.add(node);
			//递归子节点
			this.findSalesHierarchyNode(tree,group.getId(),index);
		}
		return tree;
	}
	
	/**
	 * 获取用户销售层级
	 * @param userUmId
	 * @return
	 */
	public FinSalesHierarchyUser findByUserUmId(String userUmId) {
		return salesHierarchyUserMapper.findByUserUmId(userUmId);
	}

	public void saveUser(FinSalesHierarchyNode userNode) {
		if(salesHierarchyUserMapper.findByUserUmId(userNode.getUserumid())==null){
			FinSalesHierarchyUser user = new FinSalesHierarchyUser();
			user.setCode(userNode.getCode());
			user.setUserumid(userNode.getUserumid());
			user.setUsername(userNode.getUsername());
			user.setCtime(new Date());
			salesHierarchyUserMapper.insertSelective(user);
		}
		
		FinSalesHierarchyRelation relation = new FinSalesHierarchyRelation();
		relation.setCode(userNode.getCode());
		relation.setGroupid(userNode.getGroupid());
		relation.setCtime(new Date());
		salesHierarchyRelationMapper.insertSelective(relation);
	}

	public FinSalesHierarchyUser queryUser(String userUmid) {
		return salesHierarchyUserMapper.findByUserUmId(userUmid);
	}
	
	public void delGroup(Long id) {
		salesHierarchyGroupMapper.deleteByPrimaryKey(id);
		salesHierarchyRelationMapper.deleteByGroupid(id);
	}

	public void delRelation(Long groupid, String code) {
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("groupid", groupid);
		condMap.put("code", code);
		salesHierarchyRelationMapper.deleteByMap(condMap);
	}

	/**
	 * 根据销售的code删除销售层级
	 * @param userUmid
	 */
	public void delSalesHierarchyByUmid(String userUmid) {
		salesHierarchyRelationMapper.deleteByUmid(userUmid);
		salesHierarchyUserMapper.deleteByUmid(userUmid);
	}
	
	public void saveGroup(FinSalesHierarchyGroup group) {
		if(group.getId()!=null && group.getId()>0 )
			salesHierarchyGroupMapper.updateByPrimaryKeySelective(group);
		else{
			group.setCtime(new Date());
			salesHierarchyGroupMapper.insertSelective(group);
		}
	}

	public int queryRelation(Long groupid, String code) {
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("groupid", groupid);
		condMap.put("code", code);
		return salesHierarchyRelationMapper.queryRelationByMap(condMap);
	}

	public void saveSalesHierarchy(JSONArray jsonInvoiceList) {
		for (int i = 0; i < jsonInvoiceList.size(); i++) {
			JSONObject obj = jsonInvoiceList.getJSONObject(i);
			if(obj.containsKey("groupid")){
				FinSalesHierarchyGroup group = new FinSalesHierarchyGroup();
				group.setId(obj.getLong("groupid"));
				group.setSuperiorid(obj.getLong("superiorid"));
				salesHierarchyGroupMapper.updateByPrimaryKeySelective(group);
			}else if(obj.containsKey("code")&&obj.containsKey("relationid")){
				FinSalesHierarchyRelation relation = new FinSalesHierarchyRelation();
				relation.setId(obj.getLong("relationid"));
				relation.setGroupid(obj.getLong("superiorid"));
				relation.setCode(obj.getString("code"));
				salesHierarchyRelationMapper.updateByPrimaryKeySelective(relation);
			}
		}
	}
}
