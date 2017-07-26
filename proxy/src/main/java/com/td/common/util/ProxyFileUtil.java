package com.td.common.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ProxyFileUtil {

	public static String getFileName(String fileName) {
		return fileName.substring(0, fileName.indexOf("."));
	}

	/**
	 * 根据文件路径获取父目录名称
	 * 
	 * @param filePath
	 *            文件路径
	 * @return String 父目录名称
	 */
	public static String getParentName(String filePath) {
		File file = new File(filePath);
		return file.getParentFile().getName();
	}

	/**
	 * 获取父目录路径
	 * @param filePath
	 * @return
	 */
	public static String getParentPath(String filePath) {
		File file = new File(filePath);
		return file.getParentFile().getPath();
	}

	/**
	 * 强制创建目录 
	 * @param filePath
	 * @throws IOException
	 */
	public static void createFolderByFilePath(String filePath) throws IOException {
		FileUtils.forceMkdir(new File(getParentPath(filePath)));
	}
	
	/**
	 * 强制创建目录 
	 * @param filePath
	 * @throws IOException
	 */
	public static void createFolderByPath(String path) throws IOException {
		FileUtils.forceMkdir(new File(path));
	}
	
	/**
	 * 
	 * 获取最后两段文件路径
	 * 如：/dmp/download/20150514-0002/1123456789.txt
	 * 将返回 20150514-0002/1123456789.txt
	 * @param filePath 
	 */
	public static String getFilePathLast2Paragraphs(String filePath){
		File file = new File(filePath);
		String[] paths= null;
		if ("\\".equals(File.separator)){ // win32目录分隔符号
			paths = file.getAbsolutePath().split("\\" + File.separator);
		}
		else { // unix目录分隔符号
			paths = file.getAbsolutePath().split(File.separator);
		}
		return paths[paths.length-2] + File.separator + paths[paths.length - 1];
	}
	
	public static void main(String[] args) throws IOException {
		String filePath = "d:/dmp/download/20150514-0002/1123456789.txt";
		File file = new File(filePath);
		System.out.println(file.getPath());
		System.out.println(File.separatorChar);
		System.out.println(file.getAbsolutePath());
		String[] paths = file.getAbsolutePath().split("\\" + File.separator);
		System.out.println(paths.length);
		System.out.println(paths[paths.length-2] + File.separator + paths[paths.length - 1]);
		
		if ("\\".equals(File.separator)){
			System.out.println("win32");
		}

		String path = "/dmp/download/20150514-0002/1123456789.txt";
		paths = path.split("/");
		System.out.println(paths.length);
		System.out.println(paths[paths.length-2] + File.separator + paths[paths.length - 1]);
	}

}
