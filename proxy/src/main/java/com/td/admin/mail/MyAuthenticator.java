package com.td.admin.mail;

import javax.mail.PasswordAuthentication;
/**
 * @description: 邮件验证类
 * @author: LiAnJie 2013-07-25
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权所有
 */
public class MyAuthenticator extends javax.mail.Authenticator {
	private String strUser; 
    private String strPwd; 
    public MyAuthenticator(String user, String password) { 
      this.strUser = user; 
      this.strPwd = password; 
    } 

    protected PasswordAuthentication getPasswordAuthentication() { 
      return new PasswordAuthentication(strUser, strPwd); 
    } 

}
