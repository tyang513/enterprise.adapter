package com.td.proxy.controller.task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talkingdata.base.web.BaseController;
import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysParam;
import com.td.common.constant.CommonConstant;
import com.td.proxy.service.task.TaskInfoService;

@Controller
@RequestMapping("/common")
public class PorxyCommonControl extends BaseController {

	public Logger logger = LoggerFactory.getLogger(PorxyCommonControl.class);

	@Autowired
	private TaskInfoService taskInfoService;

	@RequestMapping(value = "/callBackUrl.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> callBackUrl(String taskCode) throws Exception {

		taskInfoService.createResponseFileProcess(taskCode);

		Map<String, Object> returnMap = getSuccessMessage("请求成功");
		returnMap.put("statu", 200);

		return getSuccessMessage("回调成功");
	}

	/**
	 * 上传附件 新增 - 提交 – 保存文件到数据库
	 */
	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request, ProxyAttachment entity) throws Exception {
		Collection<Part> parts = request.getParts();
		for (Part part : parts){
			BufferedInputStream inputStream = new BufferedInputStream(part.getInputStream());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File("d://1234.txt")));
			IOUtils.copy(inputStream, output);
			inputStream.close();
			output.close();
		}
		logger.info(entity.toString());
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_ATTACHMENT_PATH);
		MultipartFile dataFile = entity.getFile();
		String originalFileName = dataFile.getOriginalFilename();
		String taskid = System.currentTimeMillis() + "";
		BufferedInputStream inputStream = new BufferedInputStream(dataFile.getInputStream());
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(sysParam.getParamvalue() + File.separatorChar + taskid)));
		IOUtils.copy(inputStream, output);
		inputStream.close();
		output.close();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("taskid", taskid);
		returnMap.put("msg", "上传成功");
		returnMap.put("status", 200);
		if (originalFileName.indexOf(".") == -1) {
			return getFailureMessage("不能上传成功");
		}
		return returnMap;
	}
	
	@RequestMapping(value = "/upload2.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request) throws Exception {
		Object object = request.getParameter("file");
		Collection<Part> parts = request.getParts();
		for (Part part : parts){
			BufferedInputStream inputStream = new BufferedInputStream(part.getInputStream());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File("d://1234.txt")));
			IOUtils.copy(inputStream, output);
			inputStream.close();
			output.close();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("taskid", "asdf");
		returnMap.put("msg", "上传成功");
		returnMap.put("status", 200);
		return returnMap;
	}

	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	@ResponseBody
	public void download(HttpServletResponse response, HttpServletRequest request, proxyDownload entity) throws Exception {
		OutputStream os = null;
		try {
			SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_ATTACHMENT_PATH);
			String path = sysParam.getParamvalue() + File.separatorChar + entity.getTaskid() + ".gz";
			File attachmentFile = new File(path);
			if (!attachmentFile.exists()) {
				throw new Exception(path);
			}
			String fileName = new String(attachmentFile.getName().getBytes("GBK"), "ISO8859-1");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.setContentType("application/octet-stream; charset=utf-8");
			os = response.getOutputStream();
			os.write(FileUtils.readFileToByteArray(attachmentFile));
			os.flush();
		} catch (Exception e) {
			logger.error("下载附件异常!", e);
			throw new Exception("下载附件异常!", e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error("下载附件异常!", e);
					throw new Exception("下载附件异常!", e);
				}
			}
		}
	}
}

class proxyDownload {
	private String token;

	private String appkey;

	private String taskid;
	
	private String segmentId;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the appkey
	 */
	public String getAppkey() {
		return appkey;
	}

	/**
	 * @param appkey
	 *            the appkey to set
	 */
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	/**
	 * @return the taskid
	 */
	public String getTaskid() {
		return taskid;
	}

	/**
	 * @param taskid
	 *            the taskid to set
	 */
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	/**
	 * @return the segmentId
	 */
	public String getSegmentId() {
		return segmentId;
	}

	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

}

class ProxyAttachment {

	private String token;

	private String appkey;

	private String callBackUrl;

	private MultipartFile file;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the appkey
	 */
	public String getAppkey() {
		return appkey;
	}

	/**
	 * @param appkey
	 *            the appkey to set
	 */
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	/**
	 * @return the callBackUrl
	 */
	public String getCallBackUrl() {
		return callBackUrl;
	}

	/**
	 * @param callBackUrl
	 *            the callBackUrl to set
	 */
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProxyAttachment [token=" + token + ", appkey=" + appkey + ", callBackUrl=" + callBackUrl + "]";
	}

	/**
	 * @return the file
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}