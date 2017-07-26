package com.td.common.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.td.common.exception.CommonException;

public class JsshUtil {

	/**
	 * 日志
	 */
	private static final Logger logger = Logger.getLogger(JsshUtil.class);

	public static void main(String[] arg) {

		String commandStr = "sh /home/hadoop/sms/preprocess.sh 1_1 3 2 2  >run.log 2>&1 &";

		String userName = "hadoop";
		String ipAddress = "172.17.16.70";
		int port = 22;
		String password = "hadoop";

		// String userName = CacheManager.getValue(Constant.SYSTEM_PARAM_CACHE,
		// Constant.HIVEEXEC_SERVER_USERNAME);
		//
		// String ipAddress = CacheManager.getValue(Constant.SYSTEM_PARAM_CACHE,
		// Constant.HIVEEXEC_SERVER_IPADDRESS);
		//
		// int port =
		// StringUtils.isNotBlank(CacheManager.getValue(Constant.SYSTEM_PARAM_CACHE,
		// Constant.HIVEEXEC_SERVER_IPADDRESS))
		// ? Integer.valueOf(CacheManager.getValue(Constant.SYSTEM_PARAM_CACHE,
		// Constant.HIVEEXEC_SERVER_IPADDRESS)) : 22;
		//
		//
		// String password = CacheManager.getValue(Constant.SYSTEM_PARAM_CACHE,
		// Constant.HIVEEXEC_SERVER_PASSWORD);

		try {
			execScript(userName, ipAddress, port, password, commandStr);
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 链接服务器，执行脚本
	 * 
	 * @param userName
	 *            服务器用户名
	 * @param ipAddress
	 *            服务器Ip地址
	 * @param port
	 *            服务器端口
	 * @param password
	 *            服务器登录密码
	 * @param commandStr
	 *            执行脚本命令
	 * @throws CommonException
	 */
	public static void execScript(String userName, String ipAddress, int port, String password, String commandStr) throws CommonException {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(userName, ipAddress, port);
			session.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(commandStr);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
				while (in.available() > 0) {

					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;

				}
				if (channel.isClosed()) {
					logger.info("exit-status: " + channel.getExitStatus());
					break;
				}

			}

			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			throw new CommonException("hive.exec.fail", e, new Object[] {});
		}
	}

}
