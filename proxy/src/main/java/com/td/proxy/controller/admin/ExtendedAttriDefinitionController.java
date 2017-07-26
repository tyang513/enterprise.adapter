package com.td.proxy.controller.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.talkingdata.base.web.BaseController;
import com.td.admin.entity.SysDicItem;
import com.td.admin.service.DictService;
import com.td.common.util.Constants;
import com.td.common.util.JSONUtil;
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.entity.admin.ExtendedAttriDefinition;
import com.td.proxy.page.admin.ExtendedAttriDefinitionPage;
import com.td.proxy.page.admin.ExtendedAttriPage;
import com.td.proxy.service.admin.ExtendedAttriDefinitionService;
import com.td.proxy.service.admin.ExtendedAttriService;
 
/**
 * 
 * <br>
 * <b>功能：</b>ExtendedAttriDefinitionController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/extendedAttriDefinition") 
public class ExtendedAttriDefinitionController extends BaseController{
	
	private final static Logger logger = Logger.getLogger(ExtendedAttriDefinitionController.class);
	
	@Autowired
	private ExtendedAttriDefinitionService<ExtendedAttriDefinition> extendedAttriDefinitionService; 
	@Autowired
	private DictService dictService;
	@Autowired
	private ExtendedAttriService extendedAttriService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(ExtendedAttriDefinitionPage page) throws Exception {
		page.setOrder("desc");
		page.setSort("id");
		List<ExtendedAttriDefinition> rows = extendedAttriDefinitionService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}
	
	@RequestMapping("/queryExtendAttriDefinitionList.do") 
	@ResponseBody
	public List<ExtendedAttriDefinition> queryRuleDefinitionList(ExtendedAttriDefinitionPage page) throws Exception {
		page.getPager().setPageEnabled(false);
		List<ExtendedAttriDefinition> ExtendedAttriDefinitionList = extendedAttriDefinitionService.queryByList(page);
		return ExtendedAttriDefinitionList;
	}
	
	@RequestMapping("/queryExtendValue.do") 
	@ResponseBody
	public List<SysDicItem> getDictItemsByName(HttpServletRequest request){
		String dicName = request.getParameter("name");
		List<SysDicItem> list = dictService.getDictItemsByDicName(dicName);
		return list;
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
	public Map<String, Object> save(ExtendedAttriDefinition entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			/** 扩展属性类型为字典时，需后端校验默认值不能为空 */
			if(entity.getType() != null && "2".equals(entity.getType()) 
					&& ((entity.getDefaultValue() == null) || "".equals(entity.getDefaultValue()))) {
				return this.getFailureMessage("扩展属性类型为字典时，默认值不可为空");
			}
			
			//判断扩展属性定义代码是否重复
			String code = entity.getCode();
			boolean isExist = extendedAttriDefinitionService.findExtendedAttriDefinitionByCode(code);
			if (isExist){
				return this.getFailureMessage("扩展属性定义代码重复");
			} else {
				ExtendedAttriDefinitionPage page = new ExtendedAttriDefinitionPage();
				page.setName(entity.getName());
				List<ExtendedAttriDefinition> list = extendedAttriDefinitionService.queryByList(page);
				if(list != null && !list.isEmpty()) {
					return this.getFailureMessage("扩展属性定义名称重复");
				}
				entity.setCTime(new Date());	
				extendedAttriDefinitionService.insert(entity);
				return getSuccessMessage("扩展属性定义添加成功!");
			}
		} else {
			entity.setMTime(new Date());
			extendedAttriDefinitionService.updateByPrimaryKeySelective(entity);
			return getSuccessMessage("扩展属性定义修改成功!");
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
		ExtendedAttriDefinition entity = (ExtendedAttriDefinition) JSONObject.toBean(jsonForm.getJSONObject("entity"), ExtendedAttriDefinition.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			extendedAttriDefinitionService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			extendedAttriDefinitionService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		ExtendedAttriDefinition entity = extendedAttriDefinitionService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		ExtendedAttriPage page = new ExtendedAttriPage();
		page.getPager().setPageEnabled(false);
		page.setExtendAttrId(Integer.parseInt(id[0]));
		List<ExtendedAttri> extendedAttriList = extendedAttriService.queryByList(page);
		if(extendedAttriList == null || extendedAttriList.isEmpty()) {
			extendedAttriDefinitionService.deleteByPrimaryKey(id);
			return getSuccessMessage("扩展属性定义删除成功!");
		} else {
			return getFailureMessage("扩展属性定义已被引用，不可删除！");
		}
	}
	
	@RequestMapping(value = "/leadOutExtendedAttriDef.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> leadOutExtendedAttriDef(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Map<String, Object> map = this.extendedAttriDefinitionService.createExtendedAttriDefData("".equals(id) ? null :id.split(","));
		if(map.containsKey(Constants.LOGIC_FAILURE)) {
			return getFailureMessage(map.get(Constants.LOGIC_FAILURE).toString());
		} else {
			OutputStream os = null;
			List<ExtendedAttriDefinition> jsonList = (List<ExtendedAttriDefinition>)map.get(Constants.LOGIC_SUCCESS);
			String fileName = "扩展属性定义.json";
			File f = JSONUtil.jsonToFile(request, JSONUtil.objToJsonFormat(jsonList), fileName);
			if(f != null) {
				response.reset();  
	            response.setContentType("application/x-download;charset=UTF-8"); 
	            fileName = new String(f.getName().getBytes("GBK"), "ISO8859-1");
	            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
	            os = response.getOutputStream();
				os.write(FileUtils.readFileToByteArray(f));
				os.flush();
				os.close();
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/leadInExtendedAttriDef.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> leadInExtendedAttriDef(HttpServletRequest request) throws IOException {
		
        MultipartHttpServletRequest multirequest = (MultipartHttpServletRequest)request;
		
        MultipartFile multipartFile = multirequest.getFile("dataFile");
        InputStream inputStream = multipartFile.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");   
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String json = "";
        String s = null;
        while((s = bufferedReader.readLine()) != null) {
        	json += s;
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        
        boolean flag = false;
        if("1".equals(multirequest.getParameter("flag"))) { // 获取普通参数  强制覆盖
        	flag = true;
        }
        Map<String, String> map = this.extendedAttriDefinitionService.extendedAttriDefData(json, flag);
        if(map.containsKey(Constants.LOGIC_FAILURE)) {
        	return getFailureMessage(map.get(Constants.LOGIC_FAILURE));
        }
		return getSuccessMessage("数据导入成功！");
	}
	
}
