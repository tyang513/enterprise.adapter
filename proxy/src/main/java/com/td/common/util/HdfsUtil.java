package com.td.common.util;


/**
 * @description: hdfs工具类
 * @author: yangtao 2014-7-25
 * @version: 1.0
 * @modify:
 * @Copyright:
 */
@SuppressWarnings("deprecation")
public class HdfsUtil {
//
//	private static final HdfsUtil instance = new HdfsUtil();
//
//	private HdfsUtil() {
//		// do nothging
//	}
//
//	public static HdfsUtil getInstance() {
//		return instance;
//	}
//
//	public static void deleteExistPath(Path path, Configuration configuration) throws IOException {
//		FileSystem system = FileSystem.get(configuration);
//		if (system.exists(path)) {
//			system.delete(path, true);
//		}
//	}
//
//	public static String getFilePath(String path, Configuration configuration) throws IOException {
//		FileSystem fs = FileSystem.get(configuration);
//		FileStatus[] fileStatus = fs.listStatus(new Path(path));
//		StringBuffer sb = new StringBuffer();
//		for (FileStatus status : fileStatus) {
//			if (!status.isDir()) {
//				sb.append(status.getPath().toUri().getPath()).append(",");
//			}
//			if (status.isDir()) {
//				sb.append(getFilePath(status.getPath().toUri().getPath(), configuration)).append(",");//System.out.println(status.getPath().getName());
//			}
//		}
//		String s = sb.toString();
//		return s.endsWith(",") ? s.substring(0, s.length() - 1) : s;
//	}
//	
//
//	public static List<String> getFilePathList(String path, Configuration configuration) throws IOException {
//		FileSystem fs = FileSystem.get(configuration);
//		FileStatus[] fileStatus = fs.listStatus(new Path(path));
//		List<String> returnList = new ArrayList<String>();
//		for (FileStatus status : fileStatus) {
//			if (!status.isDir()) {
//				returnList.add(status.getPath().toUri().getPath());
//			}
//			if (status.isDir()) {
//				returnList.addAll(getFilePathList(status.getPath().toUri().getPath(), configuration));//System.out.println(status.getPath().getName());
//			}
//		}
//		return returnList;
//	}
	
}
