package com.td.admin.entity;

import java.io.Serializable;

public class SysDicView implements Serializable{
    /**
     * TODO 添加字段注释
     */
    private static final long serialVersionUID = 7799499574060936560L;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.id
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.name
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.description
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.dicKey
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private Long dickey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.systemCode
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private String systemcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.dicItemKey
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private String dicitemkey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.dicItemValue
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private String dicitemvalue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fin_dic_view.parentId
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    private Long parentid;

    private Long itemId;
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.id
     *
     * @return the value of fin_dic_view.id
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.id
     *
     * @param id the value for fin_dic_view.id
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.name
     *
     * @return the value of fin_dic_view.name
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.name
     *
     * @param name the value for fin_dic_view.name
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.description
     *
     * @return the value of fin_dic_view.description
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.description
     *
     * @param description the value for fin_dic_view.description
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.dicKey
     *
     * @return the value of fin_dic_view.dicKey
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public Long getDickey() {
        return dickey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.dicKey
     *
     * @param dickey the value for fin_dic_view.dicKey
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setDickey(Long dickey) {
        this.dickey = dickey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.systemCode
     *
     * @return the value of fin_dic_view.systemCode
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public String getSystemcode() {
        return systemcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.systemCode
     *
     * @param systemcode the value for fin_dic_view.systemCode
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setSystemcode(String systemcode) {
        this.systemcode = systemcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.dicItemKey
     *
     * @return the value of fin_dic_view.dicItemKey
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public String getDicitemkey() {
        return dicitemkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.dicItemKey
     *
     * @param dicitemkey the value for fin_dic_view.dicItemKey
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setDicitemkey(String dicitemkey) {
        this.dicitemkey = dicitemkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.dicItemValue
     *
     * @return the value of fin_dic_view.dicItemValue
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public String getDicitemvalue() {
        return dicitemvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.dicItemValue
     *
     * @param dicitemvalue the value for fin_dic_view.dicItemValue
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setDicitemvalue(String dicitemvalue) {
        this.dicitemvalue = dicitemvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fin_dic_view.parentId
     *
     * @return the value of fin_dic_view.parentId
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public Long getParentid() {
        return parentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fin_dic_view.parentId
     *
     * @param parentid the value for fin_dic_view.parentId
     *
     * @mbggenerated Wed Aug 28 09:45:43 CST 2013
     */
    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    
}