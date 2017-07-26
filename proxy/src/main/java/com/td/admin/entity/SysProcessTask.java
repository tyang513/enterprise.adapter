package com.td.admin.entity;

import java.util.Date;

public class SysProcessTask {
	
	private String systemcode;
	private String systemName;
	private String statusName;
	private String processname;
	private String candidateuser;
	private Long taskConfigId;
	
	private Date findStartTime;
	private Date findEndTime;
	
	//流程发起人
	private String startername;

	private String sheetId;
	
	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.id
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.taskCode
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private String taskcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.startTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Date starttime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.endTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Date endtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.taskName
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private String taskname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.assignerUmId
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private String assignerumid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.assignerName
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private String assignername;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.status
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.processInstanceId
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Long processinstanceid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.type
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.approve
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Integer approve;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.memo
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private String memo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.MTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Date mtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_TASK.CTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    private Date ctime;
    
    private Long pageNum;
    
    private Long beginIndex;

	private Long numPerPage;
    

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.id
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.id
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.id
     *
     * @param id the value for FIN_INTEGRAL_MARKET_TASK.id
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.taskCode
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.taskCode
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public String getTaskcode() {
        return taskcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.taskCode
     *
     * @param taskcode the value for FIN_INTEGRAL_MARKET_TASK.taskCode
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setTaskcode(String taskcode) {
        this.taskcode = taskcode;
    }
   
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.startTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.startTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.startTime
     *
     * @param starttime the value for FIN_INTEGRAL_MARKET_TASK.startTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.endTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.endTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.endTime
     *
     * @param endtime the value for FIN_INTEGRAL_MARKET_TASK.endTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.taskName
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.taskName
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.taskName
     *
     * @param taskname the value for FIN_INTEGRAL_MARKET_TASK.taskName
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.assignerUmId
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.assignerUmId
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public String getAssignerumid() {
        return assignerumid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.assignerUmId
     *
     * @param assignerumid the value for FIN_INTEGRAL_MARKET_TASK.assignerUmId
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setAssignerumid(String assignerumid) {
        this.assignerumid = assignerumid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.assignerName
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.assignerName
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public String getAssignername() {
        return assignername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.assignerName
     *
     * @param assignername the value for FIN_INTEGRAL_MARKET_TASK.assignerName
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setAssignername(String assignername) {
        this.assignername = assignername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.status
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.status
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.status
     *
     * @param status the value for FIN_INTEGRAL_MARKET_TASK.status
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.processInstanceId
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.processInstanceId
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.processInstanceId
     *
     * @param processinstanceid the value for FIN_INTEGRAL_MARKET_TASK.processInstanceId
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.type
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.type
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.type
     *
     * @param type the value for FIN_INTEGRAL_MARKET_TASK.type
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.approve
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.approve
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Integer getApprove() {
        return approve;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.approve
     *
     * @param approve the value for FIN_INTEGRAL_MARKET_TASK.approve
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.memo
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.memo
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.memo
     *
     * @param memo the value for FIN_INTEGRAL_MARKET_TASK.memo
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.MTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.MTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.MTime
     *
     * @param mtime the value for FIN_INTEGRAL_MARKET_TASK.MTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_TASK.CTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_TASK.CTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_TASK.CTime
     *
     * @param ctime the value for FIN_INTEGRAL_MARKET_TASK.CTime
     *
     * @mbggenerated Fri Aug 09 11:00:21 CST 2013
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
    
    
    public Long getPageNum() {
		return pageNum;
	}

	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}

	public Long getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(Long beginIndex) {
		this.beginIndex = beginIndex;
	}

	public Long getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Long numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getSystemcode() {
		return systemcode;
	}

	public void setSystemcode(String systemcode) {
		this.systemcode = systemcode;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getCandidateuser() {
		return candidateuser;
	}

	public void setCandidateuser(String candidateuser) {
		this.candidateuser = candidateuser;
	}

	public Long getTaskConfigId() {
		return taskConfigId;
	}

	public void setTaskConfigId(Long taskConfigId) {
		this.taskConfigId = taskConfigId;
	}

	public Date getFindStartTime() {
		return findStartTime;
	}

	public void setFindStartTime(Date findStartTime) {
		this.findStartTime = findStartTime;
	}

	public Date getFindEndTime() {
		return findEndTime;
	}

	public void setFindEndTime(Date findEndTime) {
		this.findEndTime = findEndTime;
	}
	
    public String getStartername() {
		return startername;
	}

	public void setStartername(String startername) {
		this.startername = startername;
	}
	private String processRemark;

	public String getProcessRemark() {
		return processRemark;
	}

	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}
	
}