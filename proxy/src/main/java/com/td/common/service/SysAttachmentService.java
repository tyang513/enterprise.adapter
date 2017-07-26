package com.td.common.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talkingdata.base.service.BaseService;
import com.talkingdata.casclient.User;
import com.td.common.dao.SysAttachmentDao;
import com.td.common.entity.SysAttachment;
import com.td.common.util.CommonUtil;

/**
 * 
 * <br>
 * <b>功能：</b>SysAttachmentService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013, <br>
 */
@Service("sysAttachmentService")
public class SysAttachmentService<T> extends BaseService<T> {
	private final static Logger logger = Logger.getLogger(SysAttachmentService.class);
	
	@Autowired
	private SysAttachmentDao<T> dao;

	public SysAttachmentDao<T> getDao() {
		return dao;
	}
	
	public SysAttachment uploadAttachment(User user, SysAttachment attachment) {
		InputStream is = null;
		try {
			is = attachment.getDataFile().getInputStream();
			// FinSystemParam finSystemParam = finSystemParamMapper
			// .selectByParamKey(CommonConstant.FIN_SALES_ATTACHMENT);

			// FinSystemParam finSystemParam = (FinSystemParam)
			// FinCacheWrapper.getValue(CommonConstant.SYSTEM_PARAM_CACHE_KEY,
			// CommonConstant.FIN_SALES_ATTACHMENT);

			// String attachmentpath = finSystemParam.getParamvalue();
			// //"D:\\webapps\\common\\";

			String attachmentPath = CommonUtil.getAttachmentFilePath();

			MultipartFile dataFile = attachment.getDataFile();

			String originalFileName = dataFile.getOriginalFilename();
			String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

			attachmentPath = attachmentPath + CommonUtil.getUUID() + suffix;

			FileUtils.copyInputStreamToFile(is, new File(attachmentPath));

			String fileName = attachment.getDataFile().getOriginalFilename();
			//FINII-702【财务一期】附件名中包含了逗号，下载时Chrome会报错，IE正常 
			fileName = fileName.replaceAll(",","-").replaceAll("，", "-");
		/*	if(attachment.getType() == CommonConstant.ATTACHMENT_TYPE_TC_DELIVERY && attachment.getTaskid()!=null && attachment.getTaskid() > 0)
				attachment.setName("券卡交付快递信息-"+fileName);
			else
				attachment.setName(fileName);*/
			attachment.setName(fileName);
			attachment.setPath(attachmentPath);
			attachment.setSubmitUserUmId(user.getLoginName());
			attachment.setSubmitUserName(user.getName());
			attachment.setCtime(new Date());
			dao.insert((T) attachment);
		} catch (Exception e) {
			logger.error("文件上传失败", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// 自动生成 catch 块
				e.printStackTrace();
			}

		}

		return attachment;
		
	}

	
}
