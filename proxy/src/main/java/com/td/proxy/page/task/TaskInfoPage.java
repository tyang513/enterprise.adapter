package com.td.proxy.page.task;

import java.util.Date;

import com.talkingdata.base.page.BasePage;

/**
 * 
 * <br>
 * <b>功能：</b>TaskInfoPage<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class TaskInfoPage extends BasePage {

	private Integer id;
	private String taskCode;
	private String taskName;
	private Integer partnerId;
	private String partnerFullName;
	private String status;
	private String fileName;
	private String filePath;
	private String refDmpId;
	private String refCloudId;
	private Date cTime;
	private Date mTime;
	private String type;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	
	private String[] statusArray;

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

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerFullName() {
		return this.partnerFullName;
	}

	public void setPartnerFullName(String partnerFullName) {
		this.partnerFullName = partnerFullName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	 * @return the refDmpId
	 */
	public String getRefDmpId() {
		return refDmpId;
	}

	/**
	 * @param refDmpId the refDmpId to set
	 */
	public void setRefDmpId(String refDmpId) {
		this.refDmpId = refDmpId;
	}

	/**
	 * @return the refCloudId
	 */
	public String getRefCloudId() {
		return refCloudId;
	}

	/**
	 * @param refCloudId the refCloudId to set
	 */
	public void setRefCloudId(String refCloudId) {
		this.refCloudId = refCloudId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the attr1
	 */
	public String getAttr1() {
		return attr1;
	}

	/**
	 * @param attr1 the attr1 to set
	 */
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	/**
	 * @return the attr2
	 */
	public String getAttr2() {
		return attr2;
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	/**
	 * @return the attr3
	 */
	public String getAttr3() {
		return attr3;
	}

	/**
	 * @param attr3 the attr3 to set
	 */
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	/**
	 * @return the attr4
	 */
	public String getAttr4() {
		return attr4;
	}

	/**
	 * @param attr4 the attr4 to set
	 */
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(String[] statusArray) {
		this.statusArray = statusArray;
	}

}
