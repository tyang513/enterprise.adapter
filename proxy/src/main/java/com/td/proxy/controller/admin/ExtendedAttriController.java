package com.td.proxy.controller.admin;

import java.util.Date;
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
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.page.admin.ExtendedAttriPage;
import com.td.proxy.service.admin.ExtendedAttriService;

/**
 * 
 * <br>
 * <b>功能：</b>ExtendedAttriController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/extendedAttri") 
public class ExtendedAttriController extends BaseController{

	public final static Logger logger = Logger.getLogger(ExtendedAttriController.class);

	@Autowired
	private ExtendedAttriService extendedAttriService; 

	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(ExtendedAttriPage page) throws Exception {
		List<ExtendedAttri> rows = extendedAttriService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}

	/**
	 * 新建或修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 * 
	 * 积分托管对账类型1.按发放 2	按兑换
	 *
	 */
	@RequestMapping(value = "/save.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ExtendedAttri entity) throws Exception {
		ExtendedAttri existExtendAttr  = extendedAttriService.findByTemplateId(entity.getTemplateId(), entity.getExtendCode());
		if (existExtendAttr != null) {
			return this.getFailureMessage("扩展属性已存在");
		} else {
			if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
				//entity.setExtendValue(entity.getExtendValue().replace("," , "")); 
				entity.setCTime(new Date());
				extendedAttriService.insert(entity);
				return getSuccessMessage("扩展属性添加成功!");
			}else {
				extendedAttriService.updateByPrimaryKeySelective(entity);
				return getSuccessMessage("扩展属性修改成功!");
			}
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
		ExtendedAttri entity = (ExtendedAttri) JSONObject.toBean(jsonForm.getJSONObject("entity"), ExtendedAttri.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			extendedAttriService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			extendedAttriService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}

	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		ExtendedAttri entity = extendedAttriService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}

	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		extendedAttriService.deleteByPrimaryKey(id);
		return getSuccessMessage("扩展属性删除成功!");
	}
}
