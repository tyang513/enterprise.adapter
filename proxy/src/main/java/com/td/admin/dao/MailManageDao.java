package com.td.admin.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysEmailReceiveJobInput;
import com.td.admin.entity.SysEmailSendJobInput;
import com.td.admin.entity.SysEmailServerConfig;
import com.td.admin.entity.SysEmailTemplate;
import com.td.admin.mapper.SysEmailReceiveJobInputMapper;
import com.td.admin.mapper.SysEmailSendJobInputMapper;
import com.td.admin.mapper.SysEmailServerConfigMapper;
import com.td.admin.mapper.SysEmailTemplateMapper;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;

@Service("FinEmailTemplateDao")
public class MailManageDao {
	
	private static final Logger logger = LoggerFactory.getLogger(MailManageDao.class);
	

	@Autowired
	private SysEmailTemplateMapper finEmailTemplateMapper;
	
	@Autowired
	private SysEmailServerConfigMapper finEmailServerConfigMapper;
	 
	@Autowired
	private SysEmailReceiveJobInputMapper receiveJobInputMapper; 
	@Autowired
	private SysEmailSendJobInputMapper sendJobInputMapper; 
	
	/**
	 * @description 查询邮件配置
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageBean queryMailServers(Long page,Long rows){
		PageBean pageBean = null;
		logger.info("查询MailServerConfig");
		Long totalCount = finEmailServerConfigMapper.queryTotalCount();
		logger.info("MailServerConfig totalCount : " + totalCount);
		pageBean = new PageBean(page,rows,totalCount);
		List<SysEmailServerConfig> list = finEmailServerConfigMapper.queryMailServers(pageBean);
		pageBean.getExtend().put("list",list);
		return pageBean;
	}
	
	/**
	 * @修改邮件配置
	 * @param record
	 * @param newPassword
	 * @return
	 */
	public Body updateMailServerConfig(SysEmailServerConfig record,String newPassword){
		Body body = new Body();
		logger.info("修改邮件配置 CODE : " +record.getCode());
		int updatePassword = 1;
		SysEmailServerConfig fesc = finEmailServerConfigMapper.find(record.getCode());
		if(newPassword!=null&&!newPassword.equals("")){
			logger.info("修改邮件密码 ");
			if(fesc.getPassword().equals(record.getPassword())){
				logger.info("密码校验匹配");
				updatePassword = 0;
			}else{
				updatePassword = 2;
				logger.info("密码校验不匹配");
			}
		}
		if(updatePassword==1){
			finEmailServerConfigMapper.updateByPrimaryKeySelective(record);
			body.success();
			body.setMessage("修改邮件配置成功");
		}else if(updatePassword == 0){
			record.setPassword(newPassword);//以前没有设置成功
			finEmailServerConfigMapper.updateByPrimaryKeySelective(record);
			body.success();
			body.setMessage("修改邮件配置成功");
		}else{
			body.error();
			body.setMessage("修改邮件配置失败,密码校验不匹配");
		}
		return body;
	}
	
	/**
	 * @description 查询邮件模版
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageBean queryMailTemplate(SysEmailTemplate emailTemp,Long page,Long rows){
		PageBean pageBean = null;
		logger.info("查询MailServerConfig");
		Long totalCount = finEmailTemplateMapper.queryTotalCount(emailTemp);
		logger.info("MailServerConfig totalCount : " + totalCount);
		pageBean = new PageBean(page,rows,totalCount);
		emailTemp.setRows(rows);
		emailTemp.setBeginIndex(pageBean.getFirstResult());
		List<SysEmailTemplate> list = finEmailTemplateMapper.queryMailTemplate(emailTemp);
		pageBean.getExtend().put("list",list);
		return pageBean;
	}
	
	/**
	 * @description 修改邮件模版
	 * @param record
	 * @return
	 */
	public int updateMailTemplate(SysEmailTemplate record){
		//Body body = new Body();
		logger.info("修改邮件模版 ID : " +record.getId());
//		body.success();
//		body.setMessage("模版修改成功");
		return finEmailTemplateMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * @description 新增邮件模版
	 * @param record
	 * @return
	 */
	public int saveMailTemplate(SysEmailTemplate record){
		//Body body = new Body();
		logger.info("新增邮件模版");
		int result= finEmailTemplateMapper.insertSelective(record);
		//body.success();
		//body.setMessage("新增模版成功 id ： "+ record.getId());
		return result;
	}

	public int deleteMailTemp(Long id) {
		return finEmailTemplateMapper.deleteByPrimaryKey(id);
	}

	public SysEmailServerConfig findServerConfigByCode(String code) {
		return finEmailServerConfigMapper.find(code);
	}
	public List<SysEmailTemplate> getAllMails(){
	
		List<SysEmailTemplate> list = finEmailTemplateMapper.getAllMails();
		
		return list;
	}
	public List<SysEmailTemplate> getMailTemplatesByCodeOrTitle(String codeOrTitle){
	
		List<SysEmailTemplate> list = finEmailTemplateMapper.getMailTemplatesByCodeOrTitle(codeOrTitle);
		
		return list;
	}
	public SysEmailTemplate getMailTemplateByCode(String code) {
		return finEmailTemplateMapper.getMailTemplateByCode(code);
	}

	public void addReceiveJobInput(SysEmailReceiveJobInput receiveJobInput) {
		receiveJobInputMapper.insertSelective(receiveJobInput);
	}

	public void updateSendJobInput(SysEmailSendJobInput sendJobInput) {
		sendJobInputMapper.updateByPrimaryKeySelective(sendJobInput);
	}
}
