package com.td.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.td.admin.entity.SysAuditLog;
import com.td.admin.service.AuditLogService;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;

/**
 * 审计日志Controller
 * @author lianjie 2013-09-02
 */
@Controller
@RequestMapping("auditLog")
public class AuditLogController {
	
	public static final Logger logger = LoggerFactory.getLogger(AuditLogController.class);
	
	@Autowired
	private AuditLogService auditLogService;
	
	/**
	 * 查询审计日志
	 * @return Map
	 */
	@RequestMapping(value="/find.do")
	@ResponseBody
	public Map<String, Object> find(PageBean page,SysAuditLog auditLog){
		Map<String, Object> resultMap = new HashMap<String, Object>();
        int count = auditLogService.findCount(auditLog);
        auditLog.setBeginIndex((page.getPage() - 1) * page.getRows());
        auditLog.setNumPerPage(page.getRows());
        List<SysAuditLog> oaInstanceList = auditLogService.find(auditLog);
        resultMap.put(CommonConstant.PAGE_LIST_DATA_ROWS, oaInstanceList);
        resultMap.put(CommonConstant.PAGE_LIST_DATA_TOTAL, count);
		return resultMap;
	}
}
