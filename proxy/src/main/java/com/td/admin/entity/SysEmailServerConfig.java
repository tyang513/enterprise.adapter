package com.td.admin.entity;

import java.io.Serializable;
import java.util.Date;

public class SysEmailServerConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String systemcode;
	private String systemName;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.id
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.code
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
	 */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.name
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.mailHost
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private String mailhost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.smtpPort
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private Integer smtpport;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.useSsl
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private Boolean usessl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.USER
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private String user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.passWord
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.MTime
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private Date mtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_EMAIL_SERVER_CONFIG.CTime
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    private Date ctime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.id
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.id
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.id
     *
     * @param id the value for FIN_EMAIL_SERVER_CONFIG.id
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.code
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.code
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.code
     *
     * @param code the value for FIN_EMAIL_SERVER_CONFIG.code
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.name
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.name
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.name
     *
     * @param name the value for FIN_EMAIL_SERVER_CONFIG.name
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.mailHost
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.mailHost
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public String getMailhost() {
        return mailhost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.mailHost
     *
     * @param mailhost the value for FIN_EMAIL_SERVER_CONFIG.mailHost
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setMailhost(String mailhost) {
        this.mailhost = mailhost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.smtpPort
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.smtpPort
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public Integer getSmtpport() {
        return smtpport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.smtpPort
     *
     * @param smtpport the value for FIN_EMAIL_SERVER_CONFIG.smtpPort
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setSmtpport(Integer smtpport) {
        this.smtpport = smtpport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.useSsl
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.useSsl
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
    public Boolean getUsessl() {
        return usessl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.useSsl
     *
     * @param usessl the value for FIN_EMAIL_SERVER_CONFIG.useSsl
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setUsessl(Boolean usessl) {
        this.usessl = usessl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.USER
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.USER
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public String getUser() {
        return user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.USER
     *
     * @param user the value for FIN_EMAIL_SERVER_CONFIG.USER
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.passWord
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.passWord
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.passWord
     *
     * @param password the value for FIN_EMAIL_SERVER_CONFIG.passWord
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.MTime
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.MTime
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.MTime
     *
     * @param mtime the value for FIN_EMAIL_SERVER_CONFIG.MTime
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_EMAIL_SERVER_CONFIG.CTime
     *
     * @return the value of FIN_EMAIL_SERVER_CONFIG.CTime
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_EMAIL_SERVER_CONFIG.CTime
     *
     * @param ctime the value for FIN_EMAIL_SERVER_CONFIG.CTime
     *
     * @mbggenerated Fri Aug 09 11:26:31 CST 2013
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
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

	public Boolean getUsessl() {
		return usessl;
	}
}