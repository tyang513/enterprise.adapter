package com.td.proxy.page.task;

import java.util.Date;

import com.talkingdata.base.page.BasePage;

/**
 * 
 * <br>
 * <b>功能：</b>TaskLogPage<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class TaskLogPage extends BasePage {

	private Integer id;
	private String taskCode;
	private String taskJobInputName;
	private Integer taskJobInputId;
	private String description;
	private Integer type;
	private Object errorInfo;
	private Date startTime;
	private Date finishTime;
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

	public String getTaskJobInputName() {
		return this.taskJobInputName;
	}

	public void setTaskJobInputName(String taskJobInputName) {
		this.taskJobInputName = taskJobInputName;
	}

	public Integer getTaskJobInputId() {
		return this.taskJobInputId;
	}

	public void setTaskJobInputId(Integer taskJobInputId) {
		this.taskJobInputId = taskJobInputId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getErrorInfo() {
		return this.errorInfo;
	}

	public void setErrorInfo(Object errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
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

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
}
