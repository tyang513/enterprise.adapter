package com.td.admin.entity;

import java.util.Date;

public class SysEmailReceiveJobInput {
	private Integer status;
	private Long taskId;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_RECEIVE_JOB_INPUT.id
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_RECEIVE_JOB_INPUT.subject
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    private String subject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_RECEIVE_JOB_INPUT.addresser
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    private String addresser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_RECEIVE_JOB_INPUT.content
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_RECEIVE_JOB_INPUT.createTime
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    private Date createtime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.id
     *
     * @return the value of FIN_EMAIL_RECEIVE_JOB_INPUT.id
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.id
     *
     * @param id the value for FIN_EMAIL_RECEIVE_JOB_INPUT.id
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.subject
     *
     * @return the value of FIN_EMAIL_RECEIVE_JOB_INPUT.subject
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.subject
     *
     * @param subject the value for FIN_EMAIL_RECEIVE_JOB_INPUT.subject
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.addresser
     *
     * @return the value of FIN_EMAIL_RECEIVE_JOB_INPUT.addresser
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public String getAddresser() {
        return addresser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.addresser
     *
     * @param addresser the value for FIN_EMAIL_RECEIVE_JOB_INPUT.addresser
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public void setAddresser(String addresser) {
        this.addresser = addresser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.content
     *
     * @return the value of FIN_EMAIL_RECEIVE_JOB_INPUT.content
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.content
     *
     * @param content the value for FIN_EMAIL_RECEIVE_JOB_INPUT.content
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.createTime
     *
     * @return the value of FIN_EMAIL_RECEIVE_JOB_INPUT.createTime
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_RECEIVE_JOB_INPUT.createTime
     *
     * @param createtime the value for FIN_EMAIL_RECEIVE_JOB_INPUT.createTime
     *
     * @mbggenerated Fri Oct 18 16:29:23 CST 2013
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}