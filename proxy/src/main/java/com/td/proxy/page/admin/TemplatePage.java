package com.td.proxy.page.admin;

import java.util.Date;

import com.talkingdata.base.page.BasePage;

/**
 * 
 * <br>
 * <b>功能：</b>SettleTemplatePage<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class TemplatePage extends BasePage {

	private Integer id;
	private String templateCode;
	private Integer ftpServerConfigId;
	private Integer partnerId;
	private Integer version;
	private String status;
	private Date effectDate;
	private Date expiryDate;
	private Date ctime;
	private Date mtime;
	private String partnerFullName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getFtpServerConfigId() {
		return ftpServerConfigId;
	}

	public void setFtpServerConfigId(Integer ftpServerConfigId) {
		this.ftpServerConfigId = ftpServerConfigId;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the partnerFullName
	 */
	public String getPartnerFullName() {
		return partnerFullName;
	}

	/**
	 * @param partnerFullName the partnerFullName to set
	 */
	public void setPartnerFullName(String partnerFullName) {
		this.partnerFullName = partnerFullName;
	}
}
