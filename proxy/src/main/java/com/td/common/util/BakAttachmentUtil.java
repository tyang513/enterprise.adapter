package com.td.common.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * 备份附件工具类
 * 备份文件 : 将原文件重命名为.bak文件,之后复制一份文件(去掉.bak)
 * 恢复文件：直接根据源文件名加.bak文件找备份文件,然后复制一份文件(去掉.bak)做为恢复文件
 * @author yangtao
 */
public class BakAttachmentUtil {

	private static BakAttachmentUtil instance = new BakAttachmentUtil();

	/**
	 * 备份文件格式
	 */
	public static final String BAK = ".bak";

	public static BakAttachmentUtil getInstance() {
		return instance;
	}

	private BakAttachmentUtil() {

	}

	/**
	 * 备份文件
	 * 将原文件重命名为.bak文件,之后复制一份文件出来
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public void backupFile(String filePath) throws IOException {
		File srcFile = new File(filePath);
		File bakFile = new File(filePath + BAK);
		boolean b = srcFile.renameTo(bakFile);
		if (b) {
			FileUtils.copyFile(bakFile, srcFile);
		} else {
			throw new IOException("重命名文件出错");
		}
	}

	/**
	 * 恢复文件,直接根据源文件名去找bak文件,然后恢复
	 * @param sourceFilePath 源文件
	 * @param targetFilePath 目标文件
	 * @return
	 * @throws IOException 
	 */
	public void recoverFile(String filePath) throws IOException {
		File srcFile = new File(filePath);
		File bakFile = new File(filePath + BAK);
		FileUtils.copyFile(bakFile, srcFile);
	}

}
