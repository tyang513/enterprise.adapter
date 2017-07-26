package com.td.proxy.page.admin;

import java.util.Date;

import com.talkingdata.base.page.BasePage;

/**
 * 
 * <br>
 * <b>功能：</b>ExtendedAttriPage<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class ExtendedAttriPage extends BasePage {

	private Integer id;
	private Integer templateId;
	private Integer extendAttrId;
	private String extendName;
	private String extendCode;
	private String extendValue;
	private String isExtended;
	private Date ctime;
	private Date mtime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getExtendAttrId() {
		return extendAttrId;
	}

	public void setExtendAttrId(Integer extendAttrId) {
		this.extendAttrId = extendAttrId;
	}

	public String getExtendName() {
		return this.extendName;
	}

	public void setExtendName(String extendName) {
		this.extendName = extendName;
	}

	public String getExtendCode() {
		return this.extendCode;
	}

	public void setExtendCode(String extendCode) {
		this.extendCode = extendCode;
	}

	public String getExtendValue() {
		return this.extendValue;
	}

	public void setExtendValue(String extendValue) {
		this.extendValue = extendValue;
	}

	public String getIsExtended() {
		return this.isExtended;
	}

	public void setIsExtended(String isExtended) {
		this.isExtended = isExtended;
	}

	public Date getCTime() {
		return this.ctime;
	}

	public void setCTime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMTime() {
		return this.mtime;
	}

	public void setMTime(Date mtime) {
		this.mtime = mtime;
	}
}
