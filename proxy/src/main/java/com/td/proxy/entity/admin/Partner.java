package com.td.proxy.entity.admin;

import java.util.Date;

import com.talkingdata.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>PartnerEntity<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class Partner extends BaseEntity {
	
	private Integer id;
	private String partnerFullName;
	private String partnerShortName;
	private String partnerCode;
	private Integer status;
	private Integer encryptionAlgorithmType;
	private String secretKey;
	private String secretIv;
	private String email;
	private Date cTime;
	private Date mTime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartnerFullName() {
		return this.partnerFullName;
	}

	public void setPartnerFullName(String partnerFullName) {
		this.partnerFullName = partnerFullName;
	}

	public String getPartnerShortName() {
		return this.partnerShortName;
	}

	public void setPartnerShortName(String partnerShortName) {
		this.partnerShortName = partnerShortName;
	}

	public String getPartnerCode() {
		return this.partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEncryptionAlgorithmType() {
		return this.encryptionAlgorithmType;
	}

	public void setEncryptionAlgorithmType(Integer encryptionAlgorithmType) {
		this.encryptionAlgorithmType = encryptionAlgorithmType;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getSecretIv() {
		return this.secretIv;
	}

	public void setSecretIv(String secretIv) {
		this.secretIv = secretIv;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

