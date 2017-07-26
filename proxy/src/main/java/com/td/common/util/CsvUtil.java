package com.td.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtil {

	/**
	 * 导出csv
	 * @param exportData 数据list
	 * @param metaStr 表头字段别名 如 （FULL_DATE , sqldimexp_0 , sqldimexp_1）
	 * @param columnNameStrs 	数据列中文名称数组(如 全选，日期)， 以逗号分隔的字符串
	 * @param outPutPath 输出路径，不包含文件名 （如c:/aaa/)
	 * @param filename 文件名
	 * @return
	 */
	public static String exportCSV(List<Map<String, Object>> exportData, String metaStr, List<String> columnNameList, String outPutPath,
			String filename) {

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFile = new File(outPutPath + filename);
			// csvFile.getParentFile().mkdir();
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
			//            // 写入文件头部
			String[] metaArr = metaStr.split(",");

			//输出中文表头

			for (int i = 0; i < columnNameList.size(); i++) {
				csvFileOutputStream.write("\"" + columnNameList.get(i) + "\"");

				if (i + 1 < columnNameList.size()) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();

			// 写入文件内容
			for (int i = 0; i < exportData.size(); i++) {
				Map<String, Object> row = exportData.get(i);

				for (int j = 0; j < metaArr.length; j++) {

					Object value = "";
					if (metaArr[j] == null) {
						continue;
					}

					value = row.get(metaArr[j].toString().trim());

					csvFileOutputStream.write("\"" + ((value == null) ? " " : value) + "\"");

					if (j + 1 < metaArr.length) {
						csvFileOutputStream.write(",");
					}
				}
				csvFileOutputStream.newLine();

			}
			csvFileOutputStream.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				csvFileOutputStream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile.getPath();
	}

	/**
	 * 解析csv文件
	 * @param filePath
	 * @param split
	 * @return
	 * @throws IOException 
	 */
	public static List<String[]> readCsv(String filePath) throws Exception {
		BufferedReader reader = null;
		List<String[]> rowList = new ArrayList<String[]>();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			String line = null;
			String[] rows = null;
			String split = "";
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (i == 0 && "".equals(split) && line.indexOf(",") >= 0) {
					split = ",";
				}
				else if (i == 0 && "".equals(split) && line.indexOf("\t") >= 0) {
					split = "\t";
				}
				i++;
				if (",".equals(split)) {
					// 逗号转换
					if (line.indexOf("\"") >= 0) {
						String[] lineTemp = line.split("\"", 3);
						lineTemp[1] = lineTemp[1].replaceAll(",", "");
						line = lineTemp[0] + lineTemp[1] + lineTemp[2];
					}
					rows = line.split(split); //rowsList.toArray(new String[rowList.size()])
				}
				else if ("\t".equals(split)) {
					// 逗号转换
					rows = line.split(split);
					line = "";
					for (String cell : rows) {
						line += cell.replaceAll(",", "").replaceAll("\"", "") + ",";
					}
					rows = line.substring(0, line.length() - 1).split(",");
				}
				rowList.add(rows);
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			try {
				if (reader != null)
					reader.close();
			}
			catch (IOException ex) {

			}
		}
		return rowList;
	}

	/**
	 * @param line
	 * @param heads
	 * @return
	 */
	public static Map<String, String> parseLine2Map(String line, String[] heads) {
		String[] lines = parseLine2Array(line, null);
		Map<String, String> returnMap = new HashMap<String, String>();
		for (int i = 0; i < heads.length; i++){
			String column = "";
			if (i < lines.length){
				column = lines[i];
			}
			returnMap.put(heads[i], column);
		}
		return returnMap;
	}

	/**
	 * @param line
	 * @param heads
	 * @return
	 */
	public static Map<String, String> array2Map(String[] lines, String[] heads) {
		Map<String, String> returnMap = new HashMap<String, String>();
		for (int i = 0; i < heads.length; i++){
			String column = "";
			if (i < lines.length){
				column = lines[i];
			}
			returnMap.put(heads[i], column);
		}
		return returnMap;
	}
	
//	/**
//	 * 返回长度和headers长度一样的数组
//	 * @param line
//	 * @param headers
//	 * @return
//	 */
//	public static String[] parseLine2Array(String line, String[] headers) {
//		String[] lines = parseLine2Array(line, null);
//		if (lines == null) {
//			return null;
//		}
//		if (lines.length == headers.length) {
//			return lines;
//		}
//		if (lines.length > headers.length){
//			throw new CommonException("CSV文件解析错误,文件解析行数大于标题行数");
//		}
//		for (int i = 0; i < headers.length - lines.length; i++){
//			lines[lines.length - 1 + i] = "";
//		}
//		return lines;
//	}
	
	/**
	 * 返回长度由
	 * @param line
	 * @return
	 */
	public static String[] parseLine2Array(String line, String separatorChar) {
		if (separatorChar != null && !"".equals(separatorChar)) {
			return line.split(separatorChar);
		}
		if (line.indexOf("\t") >= 0) {
			return line.split("\t");
		}
		else if (line.indexOf(",") >= 0) {
			return line.split(",");
		}
		return null;
	}
	
	

	public static void main(String[] args) throws Exception {
		List<String[]> statRecord = CsvUtil.readCsv("D:\\42_50.stat");
		for (String[] recordList : statRecord) {
			for (int i = 0; i < recordList.length; i++) {
				System.out.println(recordList[i]);
			}
		}
	}

//	/**
//	 * @param line
//	 * @param heads
//	 * @return
//	 */
//	public static Map<FileParseColumnConf, String> parseLine2Map(String line, FileParseConf parseConf,
//			List<FileParseColumnConf> fileParseColumnConfList) {
//		String[] lines = parseLine2Array(line, parseConf.getSeparatorChar());
//		Map<FileParseColumnConf, String> returnMap = new HashMap<FileParseColumnConf, String>();
//		for (int i = 0; i <fileParseColumnConfList.size(); i++) {
//			FileParseColumnConf columnConf = fileParseColumnConfList.get(i);
//			String columnValue = "";
//			if (i < lines.length) {
//				columnValue = lines[i];
//			}
//			System.out.println(columnValue);
//			returnMap.put(columnConf, columnValue);
//		}
//		return returnMap;
//	}
//	
//	public static Object str2Object(FileParseColumnConf columnConf, String value) {
//		if ("1".equalsIgnoreCase(columnConf.getType())) {
//			return String.valueOf(value);
//		} else if ("2".equalsIgnoreCase(columnConf.getType())) {
//			return Integer.valueOf(value);
//		} else if ("3".equalsIgnoreCase(columnConf.getType())) {
//			return Double.valueOf(value);
//		}
//		return null;
//	}
	
}
