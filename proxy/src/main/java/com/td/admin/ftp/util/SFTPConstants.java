package com.td.admin.ftp.util;

/** 
 * @description: SFTP 静态常量
 * {port=22, protocol=sftp, host=172.17.16.65, rootpath=a:, charset=utf-8, code=test, user=user, servername=172.17.16.65, password=password3}
 * @author: cmh  2013-6-25
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
@Deprecated
public class SFTPConstants {
    public static final String SFTP_REQ_HOST = "host";
    public static final String SFTP_REQ_PORT = "port";
    public static final String SFTP_REQ_USERNAME = "user";
    public static final String SFTP_REQ_PASSWORD = "password";
    public static final int SFTP_DEFAULT_PORT = 22;
    public static final String SFTP_REQ_LOC = "location";
}
