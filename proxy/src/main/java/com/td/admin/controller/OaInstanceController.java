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

import com.td.admin.entity.SysOaInstance;
import com.td.admin.service.OaInstanceService;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;

/**
 * OA流程监控Controller
 * @author lianjie 2013-09-02
 */
@Controller
@RequestMapping("oaInstance")
public class OaInstanceController {
	
	public static final Logger logger = LoggerFactory.getLogger(OaInstanceController.class);
	@Autowired
	private OaInstanceService oaInstanceService;
	/**
	 * 查询OA流程
	 * @return Map
	 */
	@RequestMapping(value="/find.do")
	@ResponseBody
	public Map<String, Object> find(PageBean page,SysOaInstance oaInstance){
		Map<String, Object> resultMap = new HashMap<String, Object>();
        int count = oaInstanceService.findCount(oaInstance);
		oaInstance.setBeginIndex((page.getPage() - 1) * page.getRows());
		oaInstance.setNumPerPage(page.getRows());
        List<SysOaInstance> oaInstanceList = oaInstanceService.find(oaInstance);
        resultMap.put(CommonConstant.PAGE_LIST_DATA_ROWS, oaInstanceList);
        resultMap.put(CommonConstant.PAGE_LIST_DATA_TOTAL, count);
		return resultMap;
	}
}
