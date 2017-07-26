package com.td.common.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.talkingdata.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>SysAttachmentEntity<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */
public class SysAttachment extends BaseEntity {
	
	private Integer id;
	private String name;
	private Integer type;
	private String description;
	private String path;
	private String submitUserUmId;
	private String submitUserName;
	private String swfPathName;
	private String swfName;
	private String attr1;
	private String attr2;
	private String attr3;
	private Date mtime;
	private Date ctime;
	private MultipartFile dataFile;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSubmitUserUmId() {
		return this.submitUserUmId;
	}

	public void setSubmitUserUmId(String submitUserUmId) {
		this.submitUserUmId = submitUserUmId;
	}

	public String getSubmitUserName() {
		return this.submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	
	public String getSwfPathName() {
		return swfPathName;
	}

	public void setSwfPathName(String swfPathName) {
		this.swfPathName = swfPathName;
	}

	public String getSwfName() {
		return swfName;
	}

	public void setSwfName(String swfName) {
		this.swfName = swfName;
	}

	public String getAttr1() {
		return this.attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return this.attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return this.attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public Date getMtime() {
		return this.mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public MultipartFile getDataFile() {
		return dataFile;
	}

	public void setDataFile(MultipartFile dataFile) {
		this.dataFile = dataFile;
	}
	
}

