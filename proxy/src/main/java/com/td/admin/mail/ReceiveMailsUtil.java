package com.td.admin.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.td.admin.entity.SysEmailReceiveJobInput;
import com.td.admin.entity.SysEmailServerConfig;
import com.td.admin.mapper.SysEmailServerConfigMapper;
import com.td.common.constant.CommonConstant;
import com.td.common.util.ApplicationContextManager;

/**
 * @description:邮件接收工具类
 * @author: LiAnJie 2013-10-18
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权所有
 */
public class ReceiveMailsUtil {
	//日志
	private static final Logger logger = Logger.getLogger(ReceiveMailsUtil.class);
	
	public static List<SysEmailReceiveJobInput> receiveMails(String mailServer,String protocol,String port,String user,String pwd) throws Exception{
		// 创建一个有具体连接信息的Properties对象
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", protocol);
		//设置邮件服务器 无端口号使用默认端口
		if(protocol.equalsIgnoreCase("pop3")){
			props.setProperty("mail.pop3.host", mailServer);
			if(StringUtils.isNotBlank(port))
				props.setProperty("mail.pop3.port", port);
		}else if(protocol.equalsIgnoreCase("imap")){
			props.setProperty("mail.imap.host", mailServer);
			if(StringUtils.isNotBlank(port))
				props.setProperty("mail.imap.port", port);
		}
		// 使用Properties对象获得Session对象
		Session session = Session.getInstance(props,new MyAuthenticator(user,pwd));
		session.setDebug(false); //是否启用debug模式 

		// 利用Session对象获得Store对象，并连接pop3服务器
		Store store = session.getStore();
		store.connect();

		// 获得邮箱内的邮件夹Folder对象，以"读-写"打开
		Folder folder = store.getFolder("inbox");
		folder.open(Folder.READ_WRITE);

		// 搜索未读邮件
		SearchTerm st = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

		// 获得邮件夹Folder内的所有邮件Message对象
		//		 Message [] messages = folder.getMessages();

		// 不是像上面那样直接返回所有邮件，而是使用Folder.search()方法
		Message[] messages = folder.search(st);
		int mailCounts = messages.length;
		logger.debug("搜索过滤到" + mailCounts + " 封符合条件的邮件！");
		
		List<SysEmailReceiveJobInput> emailReceiveList = new ArrayList<SysEmailReceiveJobInput>();
		for (int i = 0; i < mailCounts; i++) {
			AnalyseMimeMessage receiveMail = new AnalyseMimeMessage((MimeMessage) messages[i]);
			//设置邮件接收BEAN
			SysEmailReceiveJobInput emailReceiveJobInput = new SysEmailReceiveJobInput();
			emailReceiveJobInput.setSubject(receiveMail.getSubject());
			emailReceiveJobInput.setAddresser(receiveMail.getFrom());
			emailReceiveJobInput.setContent(receiveMail.getBodyText());
			emailReceiveList.add(emailReceiveJobInput);
			
			logger.debug(" subject: " + emailReceiveJobInput.getSubject());
			logger.debug(" hasRead: " + receiveMail.isNew());
			logger.debug(" form: " + emailReceiveJobInput.getAddresser());
			logger.debug(" bodycontent: " + emailReceiveJobInput.getContent());
			// 设置删除标记
			messages[i].setFlag(Flags.Flag.DELETED, true);
			logger.debug("成功设置了删除标记！");
		}
		//true 删除邮件  关闭连接时设置了删除标记的邮件才会被真正删除，相当于"QUIT"命令
		folder.close(true);
		store.close();
		return emailReceiveList;
	}
	
	public static void main(String[] args) throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	    ApplicationContextManager.setAppContext(context);
	    
		SysEmailServerConfigMapper finEmailServerConfigMapper = (SysEmailServerConfigMapper) ApplicationContextManager.getBean("finEmailServerConfigMapper"); ;
		//设置邮件发送信息
		SysEmailServerConfig mailServerConfig = finEmailServerConfigMapper.find(CommonConstant.FINANCE_EMAIL_RECEIVE_SERVER_CODE);
		//协议
		String protocol = "pop3";
		//端口
		String port = mailServerConfig.getSmtpport()!=null && mailServerConfig.getSmtpport()>0?mailServerConfig.getSmtpport().toString():null;
		//接收邮件
		String userName = mailServerConfig.getUser().substring(0, mailServerConfig.getUser().indexOf("@"));
		
		ReceiveMailsUtil.receiveMails(mailServerConfig.getMailhost(),protocol,port,userName,mailServerConfig.getPassword());
		
	}
}