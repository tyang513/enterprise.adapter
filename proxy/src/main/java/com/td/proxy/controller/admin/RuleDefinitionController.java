package com.td.proxy.controller.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.talkingdata.base.web.BaseController;
import com.talkingdata.casclient.User;
import com.td.common.util.Constants;
import com.td.common.util.JSONUtil;
import com.td.proxy.entity.admin.RuleDefinition;
import com.td.proxy.entity.admin.TemplateRule;
import com.td.proxy.page.admin.RuleDefinitionPage;
import com.td.proxy.page.admin.TemplateRulePage;
import com.td.proxy.service.admin.RuleDefinitionService;
import com.td.proxy.service.admin.TemplateRuleService;
import com.td.proxy.service.admin.TemplateService;

/**
 * 
 * <br>
 * <b>功能：</b>RuleDefinitionController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/ruleDefinition") 
public class RuleDefinitionController extends BaseController{
	
	public final static Logger logger = Logger.getLogger(RuleDefinitionController.class);
	
	@Autowired
	private RuleDefinitionService ruleDefinitionService;
	
	@Autowired
	private TemplateRuleService<TemplateRule> templateRuleService;
	
	@Autowired
	private TemplateService settleTemplateService;
	
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(RuleDefinitionPage page) throws Exception {
		List<RuleDefinition> rows = ruleDefinitionService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}
	
	@RequestMapping("/queryRuleDefinitionList.do") 
	@ResponseBody
	public List<RuleDefinition> queryRuleDefinitionList(RuleDefinitionPage page, String q) throws Exception {
		//根据结算类别,查询不同的类型的规则定义
		if(StringUtils.isNotBlank(page.getCategoryCode())){
			//积分托管--5:规则定义
			if(page.getCategoryCode().equals(Constants.JFTG)){
				page.setBusinessTypeArray(new String[]{Constants.RULE_BUSINESS_TYPE_SETTLE,Constants.RULE_BUSINESS_TYPE_DATA_PREPROCESS});
			}
			//B2C--3:商户文件导入规则(对账文件解析规则)、4:对账规则、5:规则定义
			else if(page.getCategoryCode().equals(Constants.B2C)){
				page.setBusinessTypeArray(new String[]{Constants.RULE_BUSINESS_TYPE_RECON_FILE_PARSE,Constants.RULE_BUSINESS_TYPE_RECON,Constants.RULE_BUSINESS_TYPE_SETTLE});
			}
		}
		if(!StringUtils.isEmpty(q)) {
			page.setName(q);
		}
		
		List<RuleDefinition> ruleDefinitionList = ruleDefinitionService.queryByList(page);
		return ruleDefinitionList;
	}
	
	@RequestMapping("/queryRuleDefinitionListNoPage.do") 
	@ResponseBody
	public List<RuleDefinition> queryRuleDefinitionListNoPage(RuleDefinitionPage page, String q) throws Exception {
		
//		if(StringUtils.isNotBlank(page.getCategoryCode())){
//			//积分托管--5:规则定义
//			if(page.getCategoryCode().equals(Constants.JFTG)){
//				page.setBusinessTypeArray(new String[]{Constants.RULE_BUSINESS_TYPE_SETTLE,Constants.RULE_BUSINESS_TYPE_DATA_PREPROCESS});
//			}
//		}
		
		List<String> list=new ArrayList<String>();
		list.add("通用");
		list.add("获客");
		list.add("营销");
		list.add("请求");
		list.add(page.getName());
//		List<Category> categoryList = this.categoryService.findSuperCategoryByParentCategory(page.getCategoryCode());
//		if(!categoryList.isEmpty()) {
//			for(Category category : categoryList) {
//				list.add(category.getName());
//			}
//		}
		if(!StringUtils.isEmpty(q)) {
			page.setName(q.trim());
		} else {
			page.setName(null);
		}
		page.setNameArray(list.toArray());
		
		page.getPager().setPageEnabled(false);
		List<RuleDefinition> ruleDefinitionList = ruleDefinitionService.queryByList(page);
		
		return ruleDefinitionList;
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
	public Map<String, Object> save(RuleDefinition entity, @RequestParam("attachmentIds") String attachmentIds, MultipartFile dataFile, HttpServletRequest request) throws Exception {
		
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			//验证规则名，规则类名唯一
			Map checkResult = hasSameClassName(entity);
			if(checkResult != null) {
				return checkResult;
			} else {
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("user");
				int info = ruleDefinitionService.saveRuleDefinition(entity, attachmentIds, dataFile, user);
				if(info == 1){
					return getSuccessMessage("规则定义添加成功!");
				}else if(info == 0){
					return getFailureMessage("规则定义添加失败!提交失败!");
				}else{
					return getFailureMessage("规则定义添加失败!附件上传失败  不再进行入库操纵!");
				}
			}
		} else {
			entity.setMTime(new Date());
			ruleDefinitionService.updateByPrimaryKeySelective(entity);
			return getSuccessMessage("规则定义修改成功!");
		}
	}
	
	/**
	 * 修改一个规则定义
	 * 
	 * @param bean
	 *            初始化属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update.do")
	@ResponseBody
	public Map<String, Object> update(RuleDefinition entity, MultipartFile dataFile, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			int info = ruleDefinitionService.updateRuleDefinition(entity, dataFile, user);
			if(info == 3){
				return getSuccessMessage("规则定义修改成功!");
			}
		} catch (Exception e) {
			logger.error("规则定义修改失败!", e);
		}
		return getFailureMessage("规则定义修改失败!");
	}
	
	/**
	 * 判断是否有相同的类名
	 * @return
	 */
	private Map<String, Object> hasSameClassName(RuleDefinition entity) throws Exception{
		RuleDefinitionPage p1 = new RuleDefinitionPage();
		p1.setName(entity.getName());
		int count1 = ruleDefinitionService.queryByCount(p1);
		if(count1 > 0) {
			return getFailureMessage("规则名称已存在");
		}
		
		RuleDefinitionPage p2 = new RuleDefinitionPage();
		p2.setRuleClassName(entity.getRuleClassName());
		int count2 = ruleDefinitionService.queryByCount(p2);
		if(count2 > 0) {
			return getFailureMessage("规则实现类的名称已存在");
		}
		
		return null;
		
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
		RuleDefinition entity = (RuleDefinition) JSONObject.toBean(jsonForm.getJSONObject("entity"), RuleDefinition.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			ruleDefinitionService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			ruleDefinitionService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		RuleDefinition entity = ruleDefinitionService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		
		int info = ruleDefinitionService.deleteRuleDefinition(id);
		if(info == 0) {
			return getSuccessMessage("规则定义删除成功");
		} else if(info == 1){
			return getFailureMessage("没有找到待删除的记录!");
		} else {
			return getFailureMessage("规则定义删除失败");
		}
	}
	
	@RequestMapping("/updateRuleDefinition.do")
	@ResponseBody
	public Map<String, Object> updateRuleDefinition(RuleDefinition ruleDefinition) throws Exception {
		String status = ruleDefinition.getStatus();
		if (StringUtils.isNotBlank(status)){
			if (Constants.BASIS_STATUS_ENABLE.equals(status)){
				TemplateRulePage templateRulePage = new TemplateRulePage();
				templateRulePage.getPager().setPageEnabled(false);
				templateRulePage.setRuleDefinitionId(ruleDefinition.getId());
				templateRulePage.setStatus(status);
				int i = templateRuleService.queryByCount(templateRulePage);
				if(i > 0) {
					return getFailureMessage("模板已使用此规则定义，不可禁用!");
				}
				ruleDefinition.setStatus(Constants.BASIS_STATUS_DISABLE);
			} else {
				ruleDefinition.setStatus(Constants.BASIS_STATUS_ENABLE);
			}
		}
		ruleDefinitionService.updateRuleStatusByPrimaryKey(ruleDefinition);
		return getSuccessMessage("规则定义更新成功!");
	}
	
	@RequestMapping(value = "/leadOutRuleDefinition.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> leadOutRuleDefinition(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Map<String, Object> map = this.ruleDefinitionService.createRuleDefinitionData("".equals(id) ? null : id.split(","));
		if(map.containsKey(Constants.LOGIC_FAILURE)) {
			return getFailureMessage(map.get(Constants.LOGIC_FAILURE).toString());
		} else {
			OutputStream os = null;
			List<RuleDefinition> jsonList = (List<RuleDefinition>)map.get(Constants.LOGIC_SUCCESS);
			String fileName = "规则定义.json";
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
	
	@RequestMapping(value = "/leadInRuleDefinition.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> leadInRuleDefinition(HttpServletRequest request) throws IOException {
		
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
        Map<String, String> map = this.ruleDefinitionService.ruleDefinitionData(json, flag);
        if(map.containsKey(Constants.LOGIC_FAILURE)) {
        	return getFailureMessage(map.get(Constants.LOGIC_FAILURE));
        }
		return getSuccessMessage("数据导入成功！");
	}
	
}
