package com.td.common.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.base.web.BaseController;
import com.td.common.entity.SysApproveChain;
import com.td.common.page.SysApproveChainPage;
import com.td.common.service.SysApproveChainService;
 
/**
 * 
 * <br>
 * <b>功能：</b>SysApproveChainController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */ 
@Controller
@RequestMapping("/sysApproveChain") 
public class SysApproveChainController extends BaseController{
	
	private final static Logger logger = Logger.getLogger(SysApproveChainController.class);
	
	@Autowired
	private SysApproveChainService<SysApproveChain> sysApproveChainService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(SysApproveChainPage page) throws Exception {
		List<SysApproveChain> rows = sysApproveChainService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}
	
	/**
	 * 新建或修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(SysApproveChain entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			sysApproveChainService.insert(entity);
			return getSuccessMessage("审批链添加成功!");
		} else {
			sysApproveChainService.updateByPrimaryKey(entity);
			return getSuccessMessage("审批链修改成功!");
		}
	}
	
	/**
	 * 新建或修改，用于复杂表单
	 * 
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveData.do", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> saveData(@RequestBody Map<String, Object> formData) throws Exception {
		JSONObject jsonForm = JSONObject.fromObject(formData);
		SysApproveChain entity = (SysApproveChain) JSONObject.toBean(jsonForm.getJSONObject("entity"), SysApproveChain.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			sysApproveChainService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			sysApproveChainService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		SysApproveChain entity = sysApproveChainService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		sysApproveChainService.deleteByPrimaryKey(id);
		return getSuccessMessage("审批链删除成功!");
	}
}
