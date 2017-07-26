package com.td.um.service;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author changpengfei
 *	菜单实体类
 */
public class ExtResource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer rid;
	
	private Integer prid;
	
	private String resourceId;
	
	private String resourceLabel;
	
	private List<ExtResource> childrens;
	
	private String resourceDesc;
	
	private Integer appRid;
	
	private Integer resourceTypeRid;
	
	private Integer parentResourceRid;
	
	private String resourceCode;
	
	private String resourceName;
	
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
	
	private String parentId;
	
	
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getAppRid() {
		return appRid;
	}

	public void setAppRid(Integer appRid) {
		this.appRid = appRid;
	}

	public Integer getResourceTypeRid() {
		return resourceTypeRid;
	}

	public void setResourceTypeRid(Integer resourceTypeRid) {
		this.resourceTypeRid = resourceTypeRid;
	}

	public Integer getParentResourceRid() {
		return parentResourceRid;
	}

	public void setParentResourceRid(Integer parentResourceRid) {
		this.parentResourceRid = parentResourceRid;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer getResourceOrder() {
		return resourceOrder;
	}

	public void setResourceOrder(Integer resourceOrder) {
		this.resourceOrder = resourceOrder;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public Integer getIsAction() {
		return isAction;
	}

	public void setIsAction(Integer isAction) {
		this.isAction = isAction;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getExtAttr1() {
		return extAttr1;
	}

	public void setExtAttr1(String extAttr1) {
		this.extAttr1 = extAttr1;
	}

	public String getExtAttr2() {
		return extAttr2;
	}

	public void setExtAttr2(String extAttr2) {
		this.extAttr2 = extAttr2;
	}

	public String getExtAttr3() {
		return extAttr3;
	}

	public void setExtAttr3(String extAttr3) {
		this.extAttr3 = extAttr3;
	}

	public Integer getPrid() {
		return prid;
	}

	public void setPrid(Integer prid) {
		this.prid = prid;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceLabel() {
		return resourceLabel;
	}

	public void setResourceLabel(String resourceLabel) {
		this.resourceLabel = resourceLabel;
	}

	public List<ExtResource> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<ExtResource> childrens) {
		this.childrens = childrens;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
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
