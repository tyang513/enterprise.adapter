package com.td.admin.entity;

import java.util.Date;
import java.util.List;

public class SysJobErrorInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_JOB_ERRORINFO.id
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_JOB_ERRORINFO.jobKey
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    private String jobkey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_JOB_ERRORINFO.createTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_JOB_ERRORINFO.MTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    private Date mtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_JOB_ERRORINFO.CTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    private Date ctime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_JOB_ERRORINFO.jobInputId
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    private Integer jobinputid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_JOB_ERRORINFO.errorInfo
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    private String errorinfo;
    
    
    //查询条件
    private Date startcreatetime;
    
    private Date endcreatetime;
    
  //非数据库字段
    /**
     * 开始记录
     */
    private Long beginIndex;
    
    /**
     * 每页多少
     */
    private Long numPerPage;
    /**
     * 查询页
     */
    private Long pageNum;

    public Date getStartcreatetime() {
		return startcreatetime;
	}

	public void setStartcreatetime(Date startcreatetime) {
		this.startcreatetime = startcreatetime;
	}

	public Date getEndcreatetime() {
		return endcreatetime;
	}

	public void setEndcreatetime(Date endcreatetime) {
		this.endcreatetime = endcreatetime;
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

	public Long getPageNum() {
		return pageNum;
	}

	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_JOB_ERRORINFO.id
     *
     * @return the value of FIN_JOB_ERRORINFO.id
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_JOB_ERRORINFO.id
     *
     * @param id the value for FIN_JOB_ERRORINFO.id
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_JOB_ERRORINFO.jobKey
     *
     * @return the value of FIN_JOB_ERRORINFO.jobKey
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public String getJobkey() {
        return jobkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_JOB_ERRORINFO.jobKey
     *
     * @param jobkey the value for FIN_JOB_ERRORINFO.jobKey
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public void setJobkey(String jobkey) {
        this.jobkey = jobkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_JOB_ERRORINFO.createTime
     *
     * @return the value of FIN_JOB_ERRORINFO.createTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_JOB_ERRORINFO.createTime
     *
     * @param createtime the value for FIN_JOB_ERRORINFO.createTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_JOB_ERRORINFO.MTime
     *
     * @return the value of FIN_JOB_ERRORINFO.MTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_JOB_ERRORINFO.MTime
     *
     * @param mtime the value for FIN_JOB_ERRORINFO.MTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_JOB_ERRORINFO.CTime
     *
     * @return the value of FIN_JOB_ERRORINFO.CTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_JOB_ERRORINFO.CTime
     *
     * @param ctime the value for FIN_JOB_ERRORINFO.CTime
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_JOB_ERRORINFO.jobInputId
     *
     * @return the value of FIN_JOB_ERRORINFO.jobInputId
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public Integer getJobinputid() {
        return jobinputid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_JOB_ERRORINFO.jobInputId
     *
     * @param jobinputid the value for FIN_JOB_ERRORINFO.jobInputId
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public void setJobinputid(Integer jobinputid) {
        this.jobinputid = jobinputid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_JOB_ERRORINFO.errorInfo
     *
     * @return the value of FIN_JOB_ERRORINFO.errorInfo
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public String getErrorinfo() {
        return errorinfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_JOB_ERRORINFO.errorInfo
     *
     * @param errorinfo the value for FIN_JOB_ERRORINFO.errorInfo
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }
    
    private String jobinputidCollection;

    private List<Integer> jobinputidList;

	public String getJobinputidCollection() {
		return jobinputidCollection;
	}

	public void setJobinputidCollection(String jobinputidCollection) {
		this.jobinputidCollection = jobinputidCollection;
	}

	public List<Integer> getJobinputidList() {
		return jobinputidList;
	}

	public void setJobinputidList(List<Integer> jobinputidList) {
		this.jobinputidList = jobinputidList;
	}
    
}