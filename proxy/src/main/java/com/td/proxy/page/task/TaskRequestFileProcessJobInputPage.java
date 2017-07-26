package com.td.proxy.page.task;

import java.util.Date;

import com.talkingdata.base.page.BasePage;

/**
 * 
 * <br>
 * <b>功能：</b>TaskRequestFileProcessJobInputPage<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class TaskRequestFileProcessJobInputPage extends BasePage {

	private Integer id;
	private String taskCode;
	private String jobInputName;
	private String fileName;
	private String filePath;
	private Integer status;
	private Object errorInfo;
	private Integer retry;
	private Integer maxRetry;
	private Integer timeout;
	private Date cTime;
	private Date mTime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskCode() {
		return this.taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getJobInputName() {
		return this.jobInputName;
	}

	public void setJobInputName(String jobInputName) {
		this.jobInputName = jobInputName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getErrorInfo() {
		return this.errorInfo;
	}

	public void setErrorInfo(Object errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Integer getRetry() {
		return this.retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public Integer getMaxRetry() {
		return this.maxRetry;
	}

	public void setMaxRetry(Integer maxRetry) {
		this.maxRetry = maxRetry;
	}

	public Integer getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Date getCTime() {
		return this.cTime;
	}

	public void setCTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getMTime() {
		return this.mTime;
	}

	public void setMTime(Date mTime) {
		this.mTime = mTime;
	}
}
