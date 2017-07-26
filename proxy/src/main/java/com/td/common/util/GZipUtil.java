package com.td.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GZipUtil {

	/**
	 * 日志
	 */
	public static Logger logger = LoggerFactory.getLogger(GZipUtil.class);
	
	/**
	 * 压缩文件成gz格式
	 * 
	 * @param filePath
	 *            被压缩文件路径
	 * @param gzipPath
	 *            存储压缩文件的路径
	 * @param gzipName
	 *            gzip压缩文件的文件名
	 * @throws IOException
	 */
	public static void gzip(String filePath, String gzipPath, String gzipName) throws Exception {
		logger.info("filePath " + filePath);
		logger.info("gzip file path " + gzipPath + File.separatorChar + gzipName);
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new BufferedInputStream(new FileInputStream(new File(filePath)), 1024);
			output = new GzipCompressorOutputStream(new BufferedOutputStream(
					new FileOutputStream(new File(gzipPath + File.separatorChar + gzipName)), 1024));
			IOUtils.copy(input, output);
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}

	/**
	 * 解压文件
	 * @param gzipFilePath gzip文件路径
	 * @param ungzipFilePath 解压文件路径
	 * @throws Exception e
	 */
	public static void ungzip(String gzipFilePath, String ungzipFilePath) throws Exception {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(gzipFilePath), 1024));
			output = new BufferedOutputStream(new FileOutputStream(ungzipFilePath), 1024);
			IOUtils.copy(input, output);
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}

	}

	public static void main(String[] args) throws Exception {
//		String filePath = "D:\\a\\a.txt";
//		String gzipPath = "D:\\a";
//		String gzipName = "a.gz";
		GZipUtil.gzip("/dmp/0123456789.txt", "/dmp/", "0123456789.gz");
//		GZipUtil.ungzip("D:\\a\\a.gz", "d:\\a\\a.csv");
	}

}
