package com.td.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysParam;
import com.td.common.constant.CommonConstant;

/**
 * @description:
 * @version: 1.0
 * @modify: 2013-10-15 陈明华 add getUUID
 * @modify: 2013-10-17 陈明华 add getCommitBatchNum
 * @modify: 2013-10-18 陈明华 add getAttachmentFilePath
 * @Copyright: 公司版权拥有
 */
public class CommonUtil {
	/**
	 * @description 用于Format邮件
	 * @param content
	 * @param map
	 *            (Key将的内容将替换，参数content${}中与Key值相同)
	 * @return
	 */
	public static String transformContent(String content, Map map) {
		StringBuffer str = new StringBuffer();
		if (content != null && !content.equals("")) {
			String[] strs = StringUtils.split(content, "{$");
			boolean isFirst = true;
			for (String string : strs) {
				int index = StringUtils.indexOf(string, "}");
				if (isFirst) {
					str.append(string);
					isFirst = false;
				}
				if (index != -1) {
					String var = string.substring(0, index);
					str.append(map.get(var));
					String s = string.substring(index + 1);
					str.append(s);
				}
			}
		}
		return str.toString();
	}

	public static boolean isEmpty(String s) {
		if (s == null || s.length() == 0)
			return true;
		return false;
	}

	
	public static boolean isNullOrEmpty(String s) {
		if (s == null || "".equals(s.trim()))
			return true;
		return false;
	}
	
	public static String ldapFilterTransfer(Object obj) {
		if (obj == null)
			return "*";
		return "".equals(String.valueOf(obj).trim()) ? "*" : String.valueOf(obj).trim();
	}
	
	/**
	 * 得到UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 获取批量提交数据时，每次提交记录数。
	 * 
	 * @return
	 */
	public static Integer getCommitBatchNum() {
		Integer commitBatchNum = 0;

		SysParam finSystemParam = (SysParam) SysCacheWrapper.getValue(CommonConstant.SYSTEM_PARAM_CACHE_KEY, CommonConstant.BATCH_COMMIT_NUMBER);

		if (finSystemParam == null) {
			commitBatchNum = CommonConstant.COMMIT_BATCH_NUM;
		} else {
			commitBatchNum = Integer.valueOf(finSystemParam.getParamvalue().toString());
		}

		return commitBatchNum;
	}
	
	/**
	 * @description 获取异常跟踪
	 */
	public static String getExceptionTrace(Throwable e){
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     PrintStream ps = new PrintStream(baos);
	     e.printStackTrace(ps);
	     return  baos.toString();
	}

	/**
	 * 得到dtafile 文件路径 每月生成一个目录yyyyMM 例如D:\webapps\datafile\201307\
	 * 特别提示：生成的路径最后带有文件分割符
	 * 
	 * @return
	 */
	public static String getAttachmentFilePath() {
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CommonConstant.SYSTEM_PARAM_CACHE_KEY, CommonConstant.SYSTEM_PARAM_ATTACHMENT_PATH);

		String attachmentpath = sysParam.getParamvalue();

		String dataFile = attachmentpath + DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");

		File attachmentFileDir = new File(dataFile);

		if (!attachmentFileDir.exists()) {
			attachmentFileDir.mkdir();
		}
		return dataFile + File.separator;
	}

	public static Integer getJobMaxRetry() {
		return getJobMaxRetryByKey(CommonConstant.SYSTEM_PARAM_JOB_MAX_RETRY);
	}

	public static Integer getJobMaxRetryByKey(String jobKey) {
		Integer jobMaxRetry = 0;
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CommonConstant.SYSTEM_PARAM_CACHE_KEY, jobKey);
		if (sysParam != null) {
			jobMaxRetry = Integer.valueOf(sysParam.getParamvalue().toString());
		}
		return jobMaxRetry;
	}

	/**
	 * 属性对拷
	 * 
	 * @param source
	 * @param dest
	 * @throws Exception
	 */
	public static void Copy(Object source, Object dest) throws Exception {
		// 获取属性
		BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), java.lang.Object.class);
		PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

		BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(), java.lang.Object.class);
		PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

		try {
			for (int i = 0; i < sourceProperty.length; i++) {
				for (int j = 0; j < destProperty.length; j++) {

					if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
						// 调用source的getter方法和dest的setter方法
						destProperty[j].getWriteMethod().invoke(dest, sourceProperty[i].getReadMethod().invoke(source));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("属性复制失败:" + e.getMessage());
		}
	}
}
