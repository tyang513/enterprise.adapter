package com.td.common.util;



/**
 * Cos的工具类
 * 
 * @author 张雷
 * 
 * 
 */
//import com.oreilly.servlet.MultipartRequest;

public class CosUtil {

//	@SuppressWarnings({ "deprecation", "unchecked" })
//	public static String upload(HttpServletRequest request) throws IOException
//	{
//		//存绝对路径
//		//String filePath = "C://upload";
//		//存相对路径
//		String filePath = request.getRealPath("/")+"upload";
//		File uploadPath = new File(filePath);
//		//检查文件夹是否存在 不存在 创建一个
//		if(!uploadPath.exists())
//		{
//			uploadPath.mkdir();
//		}
//		//文件最大容量 5M
//		int fileMaxSize = 5*1024*1024;
//	
//		//文件名
//		String fileName = null;
//		//上传文件数
//		int fileCount = 0;
//		//重命名策略
//		RandomFileRenamePolicy rfrp=new RandomFileRenamePolicy();
//		//上传文件
//		MultipartRequest mulit = new MultipartRequest(request,filePath,fileMaxSize,"UTF-8",rfrp);
//		
//		String userName = mulit.getParameter("userName");
//		System.out.println(userName);
//		
//		Enumeration filesname = mulit.getFileNames();
//	      while(filesname.hasMoreElements()){
//	           String name = (String)filesname.nextElement();
//	           fileName = mulit.getFilesystemName(name);
//	           String contentType = mulit.getContentType(name);
//	           
//	           if(fileName!=null){
//	        	   fileCount++;
//	           }
//	           System.out.println("文件名：" + fileName);
//	           System.out.println("文件类型： " + contentType);
//	           
//	      }
//	      System.out.println("共上传" + fileCount + "个文件！");
//	      
//	      return fileName;
//	}

}