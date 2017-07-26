package com.td.admin.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.SysOaTemplate;
import com.td.admin.entity.SysOaTemplateTask;
import com.td.admin.mapper.SysOaTemplateMapper;
import com.td.admin.mapper.SysOaTemplateTaskMapper;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;



@Service("approvalTemplateManageDao")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ApprovalTemplateManageDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalTemplateManageDao.class);
	
	@Autowired
	private SysOaTemplateMapper oaOatemplateMapper;
	
	@Autowired
	private SysOaTemplateTaskMapper oaTemplatetaskMapper;
	
	public PageBean queryApprovalTemplate(SysOaTemplate record){
		PageBean pageBean = null;
		logger.info("查询审批模版");
		Long totalCount = oaOatemplateMapper.queryTotalCount(record);
		logger.info("System totalCount : " + totalCount);
		pageBean = new PageBean(record.getPageNum(),record.getNumPerPage() ,totalCount);
		List<SysOaTemplate> list = oaOatemplateMapper.queryApprovalTemplate(record);
		pageBean.getExtend().put("list",list);
		return pageBean;
	}
	
	public List<SysOaTemplateTask> queryApprovalChain(SysOaTemplateTask record){
		logger.info("查询审批链");
		List<SysOaTemplateTask> list = oaTemplatetaskMapper.queryApprovalChain(record);
		return list;
	}
	
	
	public Body saveApprovalTemplate(SysOaTemplate record){
		Body body = new Body();
		List<SysOaTemplateTask> approvalChain = record.getOaTemplatetasks();
		record.setApprovalChainJsonData(null);
		record.setCtime(new java.util.Date());
		//保存模版
		oaOatemplateMapper.insertSelective(record);
		//保存审批链
		int index = 1;
		for (SysOaTemplateTask oaTemplatetask : approvalChain) {
			oaTemplatetask.setTemplateid(record.getId());
			oaTemplatetask.setCtime(new java.util.Date());
			oaTemplatetask.setTaskindex(index);
			index++;
		}
		oaTemplatetaskMapper.insertBatch(approvalChain);
		body.success();
		body.setMessage("模版保存成功");
		return body;
	}
	
	public Body updateApprovalTemplate(SysOaTemplate record){
		Body body = new Body();
		List<SysOaTemplateTask> approvalChain = record.getOaTemplatetasks();
		record.setApprovalChainJsonData(null);
		record.setCtime(new java.util.Date());
		//更新模版
		oaOatemplateMapper.updateByPrimaryKeySelective(record);
		//保存审批链
		int index = 1;
		//判断审批链是否未空
		if(approvalChain!=null){
			for (SysOaTemplateTask oaTemplatetask : approvalChain) {
				oaTemplatetask.setTemplateid(record.getId());
				oaTemplatetask.setCtime(new java.util.Date());
				oaTemplatetask.setTaskindex(index);
				index++;
			}
			//删除之前保存的审批链
			oaTemplatetaskMapper.deleteTemplate(record.getId());
			//保存审批链
			oaTemplatetaskMapper.insertBatch(approvalChain);
//			//给定templateCode 删除表里 FIN_SALES_SALESAPPROVECHAIN 的值，同时，根据Id，级联删除
//			//FIN_SALES_SALESAPPROVECHAINSTEP (chainID)
//			finSalesSalesapprovechainstepMapper.deleteByTemplateCode(record.getCode().trim());
//			finSalesSalesapprovechainMapper.deleteByTemplateCode(record.getCode().trim());
			
		}
		if(record.getStatus() == 3){//停用的时候，也要删除，由于停用的时候，也能传进的approvalChain为null
			//给定templateCode 删除表里 FIN_SALES_SALESAPPROVECHAIN 的值，同时，根据Id，级联删除
//			//FIN_SALES_SALESAPPROVECHAINSTEP (chainID)
//			finSalesSalesapprovechainstepMapper.deleteByTemplateCode(record.getCode().trim());
//			finSalesSalesapprovechainMapper.deleteByTemplateCode(record.getCode().trim());
			
		}
		body.success();
		body.setMessage("更新模版成功");
		return body;
	}
	
	public Body deleteApprovalTemplate(SysOaTemplate record) throws Exception{
		Body body = new Body();
		//删除销售人员对应的审批链
		record = oaOatemplateMapper.selectByPrimaryKey(record.getId());
//		finSalesSalesapprovechainstepMapper.deleteByTemplateCode(record.getCode());
//		finSalesSalesapprovechainMapper.deleteByTemplateCode(record.getCode());
		//删除审批模版对应审批任务模版
		oaTemplatetaskMapper.deleteTemplate(record.getId());
		//删除模版
		oaOatemplateMapper.deleteByPrimaryKey(record.getId());
		body.success();
		body.setMessage("删除模版成功");
		return body;
	}
	
	/**
	 * 根据code 查找模板
	 * @param code
	 * @return
	 */
	public SysOaTemplate selectBycode(String code) {
		return oaOatemplateMapper.selectBycode(code);
	}
	
	/**
	 * 根据code 查找模板
	 * @param code
	 * @return
	 */
	public List<SysOaTemplate> queryByApprovalTemplate(SysOaTemplate record) {
		return oaOatemplateMapper.queryApprovalTemplate(record);
	}
}
