package com.td.common.page;

import java.util.Date;

import com.talkingdata.base.page.BasePage;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserApproveChainStepPage<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */
public class SysUserApproveChainStepPage extends BasePage {

	private Integer id;
	private Integer chainID;
	private Integer index;
	private String approverUmId;
	private String approverName;
	private Date mtime;
	private Date ctime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChainID() {
		return this.chainID;
	}

	public void setChainID(Integer chainID) {
		this.chainID = chainID;
	}

	public Integer getIndex() {
		return this.index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getApproverUmId() {
		return this.approverUmId;
	}

	public void setApproverUmId(String approverUmId) {
		this.approverUmId = approverUmId;
	}

	public String getApproverName() {
		return this.approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
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
}
