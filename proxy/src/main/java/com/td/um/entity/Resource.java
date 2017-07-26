package com.td.um.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.talkingdata.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>ResourceEntity<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class Resource extends BaseEntity {
	
	private Integer rid;
	private Integer appRid;
	private Integer resourceTypeRid;
	private Integer parentResourceRid;
	private String resourceCode;
	private String resourceName;
	private String resourceDesc;
	private Integer resourceOrder;
	private String resourceUri;
	private Integer isAction;
	private String action;
	private String extAttr1;
	private String extAttr2;
	private String extAttr3;
	private String extAttr4;
	private String extAttr5;
	private String extAttr6;
	private Date createTime;
	private Date updateTime;
	private String opUmid;
	private String parentResourceName;
	private String appName;
	private String resourceTypeName;
	private String parentId;
	private String appCode;
	
	private String parentResourceCode;
	private String resourceTypeCode;
	
	
	
	public String getParentResourceCode() {
		return parentResourceCode;
	}

	public void setParentResourceCode(String parentResourceCode) {
		this.parentResourceCode = parentResourceCode;
	}

	public String getResourceTypeCode() {
		return resourceTypeCode;
	}

	public void setResourceTypeCode(String resourceTypeCode) {
		this.resourceTypeCode = resourceTypeCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	private String editMark;
	
	
	
	public String getEditMark() {
		return editMark;
	}

	public void setEditMark(String editMark) {
		this.editMark = editMark;
	}

	public String getParentResourceName() {
		return parentResourceName;
	}

	public void setParentResourceName(String parentResourceName) {
		this.parentResourceName = parentResourceName;
	}

	private List<Resource> children = new ArrayList<Resource>();

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public Integer getRid() {
		return this.rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getAppRid() {
		return this.appRid;
	}

	public void setAppRid(Integer appRid) {
		this.appRid = appRid;
	}

	public Integer getResourceTypeRid() {
		return this.resourceTypeRid;
	}

	public void setResourceTypeRid(Integer resourceTypeRid) {
		this.resourceTypeRid = resourceTypeRid;
	}

	public Integer getParentResourceRid() {
		return this.parentResourceRid;
	}

	public void setParentResourceRid(Integer parentResourceRid) {
		this.parentResourceRid = parentResourceRid;
	}

	public String getResourceCode() {
		return this.resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public Integer getResourceOrder() {
		return this.resourceOrder;
	}

	public void setResourceOrder(Integer resourceOrder) {
		this.resourceOrder = resourceOrder;
	}

	public String getResourceUri() {
		return this.resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public Integer getIsAction() {
		return this.isAction;
	}

	public void setIsAction(Integer isAction) {
		this.isAction = isAction;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getExtAttr1() {
		return this.extAttr1;
	}

	public void setExtAttr1(String extAttr1) {
		this.extAttr1 = extAttr1;
	}

	public String getExtAttr2() {
		return this.extAttr2;
	}

	public void setExtAttr2(String extAttr2) {
		this.extAttr2 = extAttr2;
	}

	public String getExtAttr3() {
		return this.extAttr3;
	}

	public void setExtAttr3(String extAttr3) {
		this.extAttr3 = extAttr3;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOpUmid() {
		return this.opUmid;
	}

	public void setOpUmid(String opUmid) {
		this.opUmid = opUmid;
	}

	public String getExtAttr4() {
		return extAttr4;
	}

	public void setExtAttr4(String extAttr4) {
		this.extAttr4 = extAttr4;
	}

	public String getExtAttr5() {
		return extAttr5;
	}

	public void setExtAttr5(String extAttr5) {
		this.extAttr5 = extAttr5;
	}

	public String getExtAttr6() {
		return extAttr6;
	}

	public void setExtAttr6(String extAttr6) {
		this.extAttr6 = extAttr6;
	}
}

