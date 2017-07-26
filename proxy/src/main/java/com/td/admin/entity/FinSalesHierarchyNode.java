package com.td.admin.entity;



public class FinSalesHierarchyNode {
    private Long id;//ID
    private String name;//显示文字 
    private Long pId;//上级ID
    private Long groupid;//组ID
    private String groupname;//组名
    private String description; //说明
    private String code; //用户code
    private String userumid; //用户UMID
    private String username; //用户名
    private Boolean open = true; //是否节点 默认打开
    private Boolean drop; //是否允许拖入
    private Boolean drag = true; //是否允许拖动
    private Long relationid;//关系ID
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserumid() {
		return userumid;
	}
	public void setUserumid(String userumid) {
		this.userumid = userumid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public Boolean getDrop() {
		return drop;
	}
	public void setDrop(Boolean drop) {
		this.drop = drop;
	}
	public Boolean getDrag() {
		return drag;
	}
	public void setDrag(Boolean drag) {
		this.drag = drag;
	}
	public Long getRelationid() {
		return relationid;
	}
	public void setRelationid(Long relationid) {
		this.relationid = relationid;
	}
}