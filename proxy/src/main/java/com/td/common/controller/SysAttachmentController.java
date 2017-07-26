package com.td.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talkingdata.base.web.BaseController;
import com.td.common.entity.SysAttachment;
import com.td.common.page.SysAttachmentPage;
import com.td.common.service.SysAttachmentService;
 
/**
 * 
 * <br>
 * <b>功能：</b>SysAttachmentController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */ 
@Controller
@RequestMapping("/sysAttachment") 
public class SysAttachmentController extends BaseController{
	
	private final static Logger logger = Logger.getLogger(SysAttachmentController.class);
	
	@Autowired
	private SysAttachmentService<SysAttachment> sysAttachmentService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(SysAttachmentPage page) throws Exception {
		List<SysAttachment> rows = sysAttachmentService.queryByList(page);
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
	public Map<String, Object> save(SysAttachment entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			sysAttachmentService.insert(entity);
			return getSuccessMessage("附件添加成功!");
		} else {
			sysAttachmentService.updateByPrimaryKey(entity);
			return getSuccessMessage("附件修改成功!");
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
		SysAttachment entity = (SysAttachment) JSONObject.toBean(jsonForm.getJSONObject("entity"), SysAttachment.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			sysAttachmentService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			sysAttachmentService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		SysAttachment entity = sysAttachmentService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		sysAttachmentService.deleteByPrimaryKey(id);
		return getSuccessMessage("附件删除成功!");
	}
	

	/**
	 * 上传附件 新增 - 提交 – 保存文件到数据库
	 */
	@RequestMapping(value = "/proxyUpload.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request, SysAttachment entity) throws Exception {
		HttpSession session = request.getSession();
		
		MultipartFile dataFile = entity.getDataFile(); 
		String originalFileName = dataFile.getOriginalFilename();
		if(originalFileName.indexOf(".") == -1) {
			return getFailureMessage("不能上传成功");
		}
		return getSuccessMessage("附件上传成功!");
	}
	
}
