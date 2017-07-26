package com.td.admin.quartz.tempjob;

import java.io.File;

import org.quartz.JobExecutionException;



/**
 *扫描文件任务
 * @author liucaixing
 *
 */
public class ScanDirectoryJob implements ITempJobExecutor {
    
	

	@Override
	public boolean execute(String param) throws Exception {
		// TODO Auto-generated method stub
		String dirName=param;
		if (dirName == null) 
	       {//      
	            throw new JobExecutionException( "Directory not configured" );       
	       }  
	       
	       File dir = new File(dirName);  
	       
	       if (!dir.exists()) 
	       {//
	           throw new JobExecutionException( "Invalid Dir "+ dirName);       
	       }  else{
	    	   
	    	   for(int i=0;i<dir.listFiles().length;i++){
	    		   
	    		   System.out.println("XXX"+dir.listFiles().toString());
	    		   
	    	   }
	       }
		return true;  
	}

}
