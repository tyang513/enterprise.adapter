package com.td.admin.entity;

import java.util.Date;

public class SysDic {
	private String systemcode;
	private String systemName;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_DIC.id
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_DIC.name
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_DIC.description
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_DIC.dicKey
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    private Long dickey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_DIC.MTime
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    private Date mtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIN_DIC.CTime
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    private Date ctime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_DIC.id
     *
     * @return the value of FIN_DIC.id
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_DIC.id
     *
     * @param id the value for FIN_DIC.id
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_DIC.name
     *
     * @return the value of FIN_DIC.name
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_DIC.name
     *
     * @param name the value for FIN_DIC.name
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_DIC.description
     *
     * @return the value of FIN_DIC.description
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_DIC.description
     *
     * @param description the value for FIN_DIC.description
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_DIC.dicKey
     *
     * @return the value of FIN_DIC.dicKey
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public Long getDickey() {
        return dickey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_DIC.dicKey
     *
     * @param dickey the value for FIN_DIC.dicKey
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public void setDickey(Long dickey) {
        this.dickey = dickey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_DIC.MTime
     *
     * @return the value of FIN_DIC.MTime
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_DIC.MTime
     *
     * @param mtime the value for FIN_DIC.MTime
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIN_DIC.CTime
     *
     * @return the value of FIN_DIC.CTime
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIN_DIC.CTime
     *
     * @param ctime the value for FIN_DIC.CTime
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
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
}