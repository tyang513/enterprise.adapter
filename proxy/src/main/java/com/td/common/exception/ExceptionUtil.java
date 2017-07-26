package com.td.common.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.td.admin.entity.SysJobErrorInfo;
import com.td.admin.mapper.SysJobConfigMapper;
import com.td.admin.mapper.SysJobErrorInfoMapper;
import com.td.common.util.ApplicationContextManager;

public class ExceptionUtil {
     private static final Logger logger = Logger.getLogger(ExceptionUtil.class);
     
    /**
     * 异常信息DAO
     */
    private static SysJobErrorInfoMapper jobErrorInfoMapper;
    
    /**
     * 异常信息DAO
     */
    private static SysJobConfigMapper jobConfigMapper;
    
    /**
     * 获得异常信息
     * @param e 异常对象
     * @return 异常信息
     */
    public static String getStackTrace(Exception e){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        return  baos.toString();
    }
    
    /**
     * 获得异常信息
     * @param e 异常对象
     * @return 异常信息
     */
    public static String getStackTrace(Throwable e){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        return  baos.toString();
    }
    
    /**
     * 创建任务异常对象
     * @param jobName 任务名
     * @param errorMessage 异常信息
     * @param jobInputId job的输入的Id 没有传入0
     */
    public static void createJobErrorInfo(String className,String errorMessage,Integer jobInputId){
        logger.debug("===createJobErrorInfo====[className=]" + className + " ;[errorMessage]=" + errorMessage + " ;[jobInputId]" + jobInputId);
        SysJobErrorInfo jobErrorInfo = new SysJobErrorInfo();
        Map<String, Object> condMap = new HashMap<String, Object>();
        condMap.put("className", className);
        List<String> jobKey = getJobConfigMapper().findJobKeyByJobName(condMap);
        if(jobKey.size()>0){
            jobErrorInfo.setJobkey(jobKey.get(0));
        }
        jobErrorInfo.setCreatetime(new Date());
        jobErrorInfo.setErrorinfo(errorMessage);
        if(jobInputId>0){
            jobErrorInfo.setJobinputid(jobInputId);
        }
        getJobErrorInfoMapper().insert(jobErrorInfo);
    }
    
    public static SysJobConfigMapper getJobConfigMapper() {
        if(jobConfigMapper==null)
            jobConfigMapper = (SysJobConfigMapper)ApplicationContextManager.getBean("sysJobConfigMapper");
       return jobConfigMapper;
    }
    
    public static SysJobErrorInfoMapper getJobErrorInfoMapper() {
        if(jobErrorInfoMapper==null)
            jobErrorInfoMapper = (SysJobErrorInfoMapper)ApplicationContextManager.getBean("sysJobErrorInfoMapper");
       return jobErrorInfoMapper;
    }
}
