package com.td.admin.mail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.td.admin.entity.SysEmailTemplate;
import com.td.common.util.ApplicationContextManager;

/**
 * @description: 邮件发送
 * @author: LiAnJie 2013-07-25
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权所有
 */
public class MailUtils {
	
	private static final Logger logger = Logger.getLogger(MailUtils.class);
	
	String to = "";// 收件人
	String from = "";// 发件人
	String host = "";// smtp主机
	String port = "";// smtp主机端口
	String username = "";//用户名
	String password = "";//密码
	String filename = "";// 附件文件名
	String subject = "";// 邮件主题
	String content = "";// 邮件正文
	Vector<String> file = new Vector<String>();// 附件文件集合

	/**
	 * TODO 默认构造函数
	 */
	public MailUtils() {
	}
	
	/**
	 * TODO 发送邮件
	 * @return boolean 成功为true，失败为false
	 */
	public boolean sendMail() {
		if(StringUtils.isBlank(to)){
			logger.debug("收件人为空!");
			return false;
		}
		//构造mail session
		Properties props = new Properties();
		//服务器地址
		props.put("mail.smtp.host",host);
		//设置邮件服务器端口  默认端口25
        props.put("mail.smtp.port",port);
		//是否需要验证
		props.put("mail.smtp.auth","true");
		//邮件服务器交互的调试信息
		//props.put("mail.debug", "true");
		//创建session设置验证用户名密码
		Session session = Session.getInstance(props,new MyAuthenticator(username,password));
		logger.debug("mailServerConfig:form="+from+",host="+host+",port="+port+
				",userName="+username+",to="+to+",subject="+subject);
		try {
			// 构造MimeMessage 并设定基本的值
			MimeMessage msg = new MimeMessage(session);
			//设置发件人
			msg.setFrom(new InternetAddress(from));
			//设置收件人  群发用,隔开
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			//设置主题
			//subject = transferChinese(subject);
			msg.setSubject(subject);
			// 构造Multipart
			Multipart mp = new MimeMultipart();
			// 向Multipart添加正文
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setContent(content, "text/html;charset=gbk");
			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbpContent);
			// 向Multipart添加附件
			Enumeration<String> efile = file.elements();
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.nextElement();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				mbpFile.setFileName(MimeUtility.encodeText(fds.getName()));
				// 向MimeMessage添加（Multipart代表附件）
				mp.addBodyPart(mbpFile);
			}
			file.removeAllElements();
			// 向Multipart添加MimeMessage
			msg.setContent(mp);
			msg.setSentDate(new Date());
			msg.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			logger.error("邮件发送异常!",e);
			return false;
		}
		logger.info("邮件发送成功!");
		return true;
	}
	/**
	 * TODO 邮件发送,属性为空时,使用默认配置发送邮件
	 * @param to 收件人
	 * @param content 内容
	 * @return
	 */
	public boolean sendMail(String to,String content){
		if(to!=null)
			setTo(to);
		if(content!=null)
			setContent(content);
		return sendMail();
	}
	
	/**
	 * TODO 邮件发送,属性为空时,使用默认配置发送邮件
	 * @param to 收件人
	 * @param subject 主题
	 * @param content 内容
	 * @param attachFilePath 附件
	 * @return
	 */
	public boolean sendMail(String to,String subject,String content,String attachFilePath){
		if(to!=null)
			setTo(to);
		if(subject!=null)
			setSubject(subject);
		if(content!=null)
			setContent(content);
		if(attachFilePath!=null)
			attachfile(attachFilePath);
		return sendMail();
	}

	/**
	 * TODO 邮件发送
	 * @param host 服务器地址
	 * @param port 服务器地址
	 * @param userName 用户名
	 * @param password 密码
	 * @param toMail 收件人
	 * @param fromMail 发件人
	 * @param subject 主题
	 * @param content 内容
	 * @param attachFilePath 附件
	 * @return
	 */
	public boolean sendMail(String host,Integer port,String userName,String password,String toMail,String fromMail,
			String subject,String content,String attachFilePath){
		setHost(host);
		setPort(port);
		setUserName(userName);
		setPassword(password);
		setTo(toMail);
		setFrom(fromMail);
		setSubject(subject);
		setContent(content);
		attachfile(attachFilePath);
		return sendMail();
	}
	
	/**
	 * 设置邮件服务器地址
	 * @param host服务器地址
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * 设置邮件服务器端口
	 * @param port 服务器端口
	 */
	public void setPort(Integer port) {
		this.port = port.toString();
	}
	/**
	 * 设置登录服务器校验密码 
	 * @param pwd 密码
	 */
	public void setPassword(String pwd) {
		this.password = pwd;
	}

	/**
	 * 设置登录服务器校验用户
	 * @param usn 用户名
	 */
	public void setUserName(String usn) {
		this.username = usn;
	}

	/**
	 * 设置收件人邮箱 
	 * @param to 收件人邮箱 
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 *设置邮件发送源邮箱 
	 * @param from 发件人邮箱
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * 设置邮件主题
	 * @param subject 主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * 设置邮件内容
	 * @param content 内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 把主题转换为中文
	 * @param strText 文本
	 * @return 中文文本
	 */
	public String transferChinese(String strText) {
		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(),"GB2312"), "GB2312", "B");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}
	
	/**
	 * 往附件组合中添加附件
	 * @param fname 附件路径
	 */
	public void attachfile(String fname) {
		file.addElement(fname);
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	    ApplicationContextManager.setAppContext(context);
	    MailUtils mail = new MailUtils();
		//邮件服务器设置
	    mail.setFrom("tasktest@DCVM04");
		mail.setHost("172.17.16.65");
		
		mail.setPort(1025);
 		mail.setUserName("tasktest");
		mail.setPassword("123456");
		//邮件发送内容
		mail.setTo("tasktest@172.17.16.65");
		mail.setSubject("新任务通知_创建积分池—订单号：SO20131205003");
		mail.setContent("<p>尊敬的测试用户");
		//发送邮件
		mail.sendMail();
		mail = new MailUtils();
		//邮件服务器设置
	    mail.setFrom("tasktest@DCVM04");
		mail.setHost("172.17.16.65");

		mail.setPort(1025);
 		mail.setUserName("tasktest");
		mail.setPassword("123456");
		//邮件发送内容
		mail.setTo("tasktest@172.17.16.65");
		mail.setSubject("新任务通知_创建积分池—订单号：SO20131205003");
		mail.setContent("<p>尊敬的测试用户");
		//发送邮件
		mail.sendMail();
		mail = new MailUtils();
		//邮件服务器设置
	    mail.setFrom("tasktest@DCVM04");
		mail.setHost("172.17.16.65");

		mail.setPort(1025);
 		mail.setUserName("tasktest");
		mail.setPassword("123456");
		//邮件发送内容
		mail.setTo("tasktest@172.17.16.65");
		mail.setSubject("新任务通知_创建积分池—订单号：SO20131205003");
		mail.setContent("<p>尊敬的测试用户");
		//发送邮件
		mail.sendMail();
		mail = new MailUtils();
		//邮件服务器设置
	    mail.setFrom("tasktest@DCVM04");
		mail.setHost("172.17.16.65");

		mail.setPort(1025);
 		mail.setUserName("tasktest");
		mail.setPassword("123456");
		//邮件发送内容
		mail.setTo("tasktest@172.17.16.65");
		mail.setSubject("新任务通知_创建积分池—订单号：SO20131205003");
		mail.setContent("<p>尊敬的测试用户");
		//发送邮件
		mail.sendMail();
	}
	 
	public static void sent(String mails, String content) {
		MailUtils mail = new MailUtils();
     	mail.sendMail(mails, content);
    }
	 
	 
	/**
	* @description 格式化Mail内容 
	*/
	public String formatMailContent(SysEmailTemplate fet){
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	/**
	 * 远程调用
	 * @param url
	 * @return
	 */
    public static String request(String url,NameValuePair[] data)throws Exception{
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setFollowRedirects(false);
		String response = null;
		try {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");  
			
			method.setRequestBody(data);
			client.executeMethod(method);
		
			InputStream menuStream = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(menuStream, "UTF-8"));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			br.close();
			menuStream.close();
			response = resBuffer.toString();
			
			
		} catch (Exception e) {
			logger.error("远程请求访问异常 ",e);
			throw e;
		}finally{
			method.releaseConnection();
		}
		return response;
	}
}
