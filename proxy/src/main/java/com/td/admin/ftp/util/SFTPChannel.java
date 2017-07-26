package com.td.admin.ftp.util;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.common.exception.CommonException;

/**
 * 创建ChannelSftp对象
 * 根据ip，用户名及密码得到一个SFTP channel对象，即ChannelSftp的实例对象，在应用程序中就可以使用该对象来调用SFTP的各种操作方法。
 * @author cmh
 * @create 2013-6-24
 * @modify
 * Copyright: Copyright (c) 
 */
public class SFTPChannel {
    Session session = null;
    Channel channel = null;

    private static final Logger LOG = Logger.getLogger(SFTPChannel.class.getName());

    public ChannelSftp getChannel(SysFtpServerConfig ftpServerConfig, int timeout) throws JSchException {

        String ftpHost = ftpServerConfig.getHost();
        int ftpPort = ftpServerConfig.getPort();
        String ftpUserName = ftpServerConfig.getUser();
        String ftpPassword = ftpServerConfig.getPassword();

        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        LOG.debug("Session created.");
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");//防止远程主机公钥改变导致 SSH 连接失败
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        LOG.debug("Session connected.");

        LOG.debug("Opening Channel.");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        LOG.info("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                + ", returning: " + channel);
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws CommonException {
        LOG.debug("close Channel.");
        try {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception e) {
            LOG.error("断开连接异常！", e);
            throw new CommonException("sftp.channel.close.exception", e, new Object[]{"压缩不同目录下的多个文件"});
        }
        
    }
}