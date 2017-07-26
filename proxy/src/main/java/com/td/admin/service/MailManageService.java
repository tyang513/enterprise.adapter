package com.td.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.MailManageDao;
import com.td.admin.entity.SysEmailReceiveJobInput;
import com.td.admin.entity.SysEmailSendJobInput;
import com.td.admin.entity.SysEmailServerConfig;
import com.td.admin.entity.SysEmailTemplate;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;

@Service("mailManageService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MailManageService {

	private static final Logger logger = Logger.getLogger(MailManageService.class);
	
	@Autowired
	private MailManageDao mailManageDao;
	private JdbcTemplate JdbcTemplate;
	
	private static String sql_get_allMails = "SELECT id,code,title,content,systemCode FROM FIN_EMAIL_TEMPLATE ";
		
	private static String sql_get_all_by_code_or_name = "SELECT id,code,title,content,systemCode FROM FIN_EMAIL_TEMPLATE where code like ? or name like ? ";

	private static List<Map<String, Object>> allMails;
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryMailServers(Long page,Long rows){
		PageBean pageBean = null;
		pageBean = mailManageDao.queryMailServers(page, rows);
		return pageBean;
	}
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Body updateMailServer(SysEmailServerConfig record,String newPassword){
		Body body = null;
		body = mailManageDao.updateMailServerConfig(record,newPassword);
		return body;
	}
	

	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryMailTemplate(SysEmailTemplate emailTemp,Long page,Long rows){
		PageBean pageBean = null;
		pageBean = mailManageDao.queryMailTemplate(emailTemp,page, rows);
		return pageBean;
	}
	
	
	public int updateMailTemplate(SysEmailTemplate record){
//		Body body = null;
		
		return mailManageDao.updateMailTemplate(record);
	}
	
	
	public int saveMailTemplate(SysEmailTemplate record){
		//Body body = null;
		int result = mailManageDao.saveMailTemplate(record);
		return result;
	}

	public int deleteMailTemp(Long id) {
		return mailManageDao.deleteMailTemp(id);
	}

	public SysEmailServerConfig findServerConfigByCode(String code) {
		return mailManageDao.findServerConfigByCode(code);
	}
	public List<SysEmailTemplate> getAllMails(){
		//List<Map<String, Object>> list = this.JdbcTemplate.queryForList(sql_get_allMails);
		//allMails = list;
		List<SysEmailTemplate> list = this.mailManageDao.getAllMails();

		return list;
	}
	public List<SysEmailTemplate> getMailTemplatesByCodeOrTitle(String codeOrTitle){
		if(StringUtils.isBlank(codeOrTitle)){
			return getAllMails();
		}
//		List<Map<String, Object>> list = this.JdbcTemplate.queryForList(sql_get_all_by_code_or_name,"%"+codeOrTitle.trim()+"%","%"+codeOrTitle.trim()+"%");
		List<SysEmailTemplate> list = this.mailManageDao.getMailTemplatesByCodeOrTitle(codeOrTitle.trim());
		return list;
	}
	public SysEmailTemplate getMailTemplateByCode(String code) {
		return mailManageDao.getMailTemplateByCode(code);
	}

	public void addReceiveJobInput(SysEmailReceiveJobInput receiveJobInput) {
		mailManageDao.addReceiveJobInput(receiveJobInput);
	}

	public void updateSendJobInput(SysEmailSendJobInput sendJobInput) {
		mailManageDao.updateSendJobInput(sendJobInput);
	}
	
}
