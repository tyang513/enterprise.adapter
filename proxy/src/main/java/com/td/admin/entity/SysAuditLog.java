package com.td.admin.entity;

import java.util.Date;

public class SysAuditLog {
	private Integer beginIndex;
	private Integer numPerPage;
	
	private String systemName;
	private String operationTypeName;
	private String targetTypeName;
	private String resultName;
	
	private Date findCreatetime;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.id
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.systemCode
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String systemcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.createTime
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.actorUmId
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String actorumid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.actorName
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String actorname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.operationType
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String operationtype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.targetType
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String targettype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.targetId
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String targetid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.result
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String result;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_AUDIT_LOG.description
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.id
     *
     * @return the value of FIN_AUDIT_LOG.id
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.id
     *
     * @param id the value for FIN_AUDIT_LOG.id
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.systemCode
     *
     * @return the value of FIN_AUDIT_LOG.systemCode
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getSystemcode() {
        return systemcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.systemCode
     *
     * @param systemcode the value for FIN_AUDIT_LOG.systemCode
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setSystemcode(String systemcode) {
        this.systemcode = systemcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.createTime
     *
     * @return the value of FIN_AUDIT_LOG.createTime
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.createTime
     *
     * @param createtime the value for FIN_AUDIT_LOG.createTime
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.actorUmId
     *
     * @return the value of FIN_AUDIT_LOG.actorUmId
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getActorumid() {
        return actorumid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.actorUmId
     *
     * @param actorumid the value for FIN_AUDIT_LOG.actorUmId
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setActorumid(String actorumid) {
        this.actorumid = actorumid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.actorName
     *
     * @return the value of FIN_AUDIT_LOG.actorName
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getActorname() {
        return actorname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.actorName
     *
     * @param actorname the value for FIN_AUDIT_LOG.actorName
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setActorname(String actorname) {
        this.actorname = actorname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.operationType
     *
     * @return the value of FIN_AUDIT_LOG.operationType
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getOperationtype() {
        return operationtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.operationType
     *
     * @param operationtype the value for FIN_AUDIT_LOG.operationType
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.targetType
     *
     * @return the value of FIN_AUDIT_LOG.targetType
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getTargettype() {
        return targettype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.targetType
     *
     * @param targettype the value for FIN_AUDIT_LOG.targetType
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setTargettype(String targettype) {
        this.targettype = targettype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.targetId
     *
     * @return the value of FIN_AUDIT_LOG.targetId
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getTargetid() {
        return targetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.targetId
     *
     * @param targetid the value for FIN_AUDIT_LOG.targetId
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.result
     *
     * @return the value of FIN_AUDIT_LOG.result
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.result
     *
     * @param result the value for FIN_AUDIT_LOG.result
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_AUDIT_LOG.description
     *
     * @return the value of FIN_AUDIT_LOG.description
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_AUDIT_LOG.description
     *
     * @param description the value for FIN_AUDIT_LOG.description
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

	public Integer getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	public String getTargetTypeName() {
		return targetTypeName;
	}

	public void setTargetTypeName(String targetTypeName) {
		this.targetTypeName = targetTypeName;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public Date getFindCreatetime() {
		return findCreatetime;
	}

	public void setFindCreatetime(Date findCreatetime) {
		this.findCreatetime = findCreatetime;
	}
}