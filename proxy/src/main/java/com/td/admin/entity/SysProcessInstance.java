package com.td.admin.entity;

import java.util.Date;

public class SysProcessInstance {
	
	private Date findStartTime;
	private Date findEndTime;
	
	private String assigneeumid;
	private String assigneename;
	private String systemcode;
	private String systemName;
	private String statusName;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.id
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processCode
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String processcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.activiti_ProcessKey
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String activitiprocessid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetId
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String sheetid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetType
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String sheettype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.startTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private Date starttime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.endTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private Date endtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterUmId
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String starterumid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String startername;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskCode
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String currenttaskcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String currenttaskname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.status
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private String processname;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.MTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private Date mtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.CTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    private Date ctime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.id
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.id
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    
    
    private Long pageNum;
    
    private Long beginIndex;
    
    private Long numPerPage;
    

	public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.id
     *
     * @param id the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.id
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processCode
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processCode
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getProcesscode() {
        return processcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processCode
     *
     * @param processcode the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processCode
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setProcesscode(String processcode) {
        this.processcode = processcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.activiti_ProcessKey
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.activiti_ProcessKey
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getActivitiprocessid() {
        return activitiprocessid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.activiti_ProcessKey
     *
     * @param activitiprocessid the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.activiti_ProcessKey
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setActivitiprocessid(String activitiprocessid) {
        this.activitiprocessid = activitiprocessid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetId
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetId
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getSheetid() {
        return sheetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetId
     *
     * @param sheetid the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetId
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setSheetid(String sheetid) {
        this.sheetid = sheetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetType
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetType
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getSheettype() {
        return sheettype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetType
     *
     * @param sheettype the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.sheetType
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setSheettype(String sheettype) {
        this.sheettype = sheettype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.startTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.startTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.startTime
     *
     * @param starttime the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.startTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.endTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.endTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.endTime
     *
     * @param endtime the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.endTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterUmId
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterUmId
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getStarterumid() {
        return starterumid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterUmId
     *
     * @param starterumid the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterUmId
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setStarterumid(String starterumid) {
        this.starterumid = starterumid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterName
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getStartername() {
        return startername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterName
     *
     * @param startername the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.starterName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setStartername(String startername) {
        this.startername = startername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskCode
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskCode
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getCurrenttaskcode() {
        return currenttaskcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskCode
     *
     * @param currenttaskcode the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskCode
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setCurrenttaskcode(String currenttaskcode) {
        this.currenttaskcode = currenttaskcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskName
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getCurrenttaskname() {
        return currenttaskname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskName
     *
     * @param currenttaskname the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.currentTaskName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setCurrenttaskname(String currenttaskname) {
        this.currenttaskname = currenttaskname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.status
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.status
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.status
     *
     * @param status the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.status
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processName
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public String getProcessname() {
        return processname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processName
     *
     * @param processname the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.processName
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setProcessname(String processname) {
        this.processname = processname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.MTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.MTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.MTime
     *
     * @param mtime the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.MTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.CTime
     *
     * @return the value of FIN_INTEGRAL_MARKET_PROCESSINSTANCE.CTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_INTEGRAL_MARKET_PROCESSINSTANCE.CTime
     *
     * @param ctime the value for FIN_INTEGRAL_MARKET_PROCESSINSTANCE.CTime
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
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

	public String getAssigneeumid() {
		return assigneeumid;
	}

	public void setAssigneeumid(String assigneeumid) {
		this.assigneeumid = assigneeumid;
	}

	public String getAssigneename() {
		return assigneename;
	}

	public void setAssigneename(String assigneename) {
		this.assigneename = assigneename;
	}
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}