package com.td.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;



public class WorkFlowManageUtil {
	
	public static String activitiUrl;
	
	private static final Logger logger = Logger.getLogger(WorkFlowManageUtil.class);
	
	/**
	 * 停止流程操作 
	 * @param processInstanceId
	 * @return 返回Json {status:'200' ， message:''}  status 200表示成功，0 表示失败 ， message 提示信息
	 * @throws Exception
	 */
	public static String stopProcessInstance(Long processInstanceId)throws Exception{
		if(processInstanceId!=null){
			String url = activitiUrl+"/workflow/stopProcessInstance.do?finProcessInstanceId="+processInstanceId;
			return request(url);
		}else{
			throw new Exception("停止流程参数不允许为 null");
		}
	}

	/**
	 * @description 完成审批
	 */
	public static String completeApprove(String finTaskId,String approve,String memo)throws Exception{
		if(memo==null){
			memo = "";
		}else{
			memo = URLEncoder.encode(memo,"UTF-8");
		}
		if(!CommonUtil.isEmpty(finTaskId)&&!CommonUtil.isEmpty(approve)){
			String url = activitiUrl+"/workflow/completeApprove.do?finTaskId="+finTaskId+"&approve="+approve+"&memo="+memo;
			return request(url);
		}else{
			throw new Exception("任务分配参数不允许为 null");
		}
	}
	
	/**
	 * 用于完成任务 
	 * @param finTaskId
	 * @param memo 备注  
	 * @param var  
	 * @return 返回Json {status:'200' ， message:''}  status 200表示成功，0 表示失败 ， message 提示信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public static String completeFinTask(String finTaskId,String memo,Map<String,Object> var)throws Exception{
		if(memo==null){
			memo = "";
		}else{
			memo = URLEncoder.encode(memo,"UTF-8");
		}
		String json = "";
		if(var!=null){
			json = new ObjectMapper().writeValueAsString(var);
			json = URLEncoder.encode(json,"UTF-8");
		}
		if(finTaskId!=null){
			String url = activitiUrl+"/workflow/completeFinTask.do?finTaskId="+finTaskId+"&memo="+memo+"&var="+json;
			String responseString = request(url);
			 JSONObject paramjs = JSONObject.fromObject(responseString);
             Map<String, String> map = (Map<String, String>) paramjs;
			if (!"200".equals(map.get("status"))){
			    throw new Exception(map.get("message"));
			}
			return responseString;
		}else{
			throw new Exception("任务分配参数不允许为 null");
		}
	}
	
	/**
	 * @description 任务分配
	 */
	public static String reassgin(String finTaskId,String userId)throws Exception{
		if(!CommonUtil.isEmpty(finTaskId)&&!CommonUtil.isEmpty(userId)){
			userId = URLEncoder.encode(userId,"UTF-8");
			String url = activitiUrl+"/workflow/reassignee.do?finTaskId="+finTaskId+"&userId="+userId;
			return request(url);
		}else{
			throw new Exception("任务分配参数不允许为 null");
		}
	}
	
	/**
	 * @description 领取任务
	 */
	public static String claimFinTask(Long taskId,String userId)throws Exception{
		if(taskId!=null&&!CommonUtil.isEmpty(userId)){
			userId = URLEncoder.encode(userId,"UTF-8");
			String url = activitiUrl+"/workflow/claimFinTask.do?finTaskId="+taskId+"&userId="+userId;
			return request(url);
		}else{
			throw new Exception("任务领取参数不允许为 null");
		}
	}
	

	/**
	 * 发起流程操作， 
	 * @param processKey 流程定义Key
	 * @param sheetId
	 * @param sheetType
	 * @return 返回Json {status:'200' ， message:''}  status 200表示成功，0 表示失败 ， message 提示信息
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
    public static String startProcess(String processKey, String sheetId, String sheetType) throws Exception {
        if (!CommonUtil.isEmpty(processKey) && !CommonUtil.isEmpty(sheetId) && !CommonUtil.isEmpty(sheetType)) {
            String url = activitiUrl + "/workflow/startProcessInstance.do?processCode=" + processKey + "&sheetId=" + sheetId + "&sheetType=" + sheetType;
            String responseString = request(url);
            JSONObject paramjs = JSONObject.fromObject(responseString);
            Map<String, String> map = (Map<String, String>) paramjs;
            if (!"200".equals(map.get("status"))) {
                throw new Exception(map.get("message"));
            }
            return responseString;
        } else {
            throw new Exception("启动流程不允许为空异常");
        }
    }
	
	/**
	 * 转发OaTask 
	 */
	public static String forwardOaTask(String finTaskId,String userId,String memo)throws Exception{
		String memo_ = "";
		if(memo!=null){
			memo_ = "&memo="+URLEncoder.encode(memo,"UTF-8");
		}
		if(!CommonUtil.isEmpty(finTaskId)&&!CommonUtil.isEmpty(userId)){
			userId = URLEncoder.encode(userId,"UTF-8");
			String url = activitiUrl +"/workflow/forwardOaTask.do?finTaskId="+finTaskId+"&userId="+userId+memo_;
			return request(url);
		}else{
			throw new Exception("oatask 任务转发");
		}
	}
	
	
	/**
	 * 转发OaTask 
	 */
	public static String askOther(String finTaskId,String userId,String memo)throws Exception{
		String memo_ = "";
		if(memo!=null){
			memo_ = "&memo="+URLEncoder.encode(memo,"UTF-8");
		}
		if(!CommonUtil.isEmpty(finTaskId)&&!CommonUtil.isEmpty(userId)){
			userId = URLEncoder.encode(userId,"UTF-8");
			String url = activitiUrl +"/workflow/askother.do?finTaskId="+finTaskId+"&userId="+userId+memo_;
			return request(url);
		}else{
			throw new Exception("oatask 任务转发");
		}
	}
	
	/**
	 * 退回审批
	 */
	public static String rollbackApprove(String finTaskId,String backUserId,String memo, String approveIndex)throws Exception{
		String memo_ = "";
		backUserId = URLEncoder.encode(backUserId,"UTF-8");
		if(memo!=null){
			memo_ = "&memo="+URLEncoder.encode(memo,"UTF-8");
		}
		if(!CommonUtil.isEmpty(finTaskId)&&!CommonUtil.isEmpty(backUserId)){
			backUserId = URLEncoder.encode(backUserId,"UTF-8");
			String url = activitiUrl +"/workflow/rollbackApprove.do?finTaskId="+finTaskId+"&backUserId="+backUserId + "&approveIndex=" + approveIndex+memo_;
			return request(url);
		}else{
			throw new Exception("审批退回参数不允许为空");
		}
	}
	

    /**
     * 结束流程,会是多个流程
     */
    public static String killProcess(String sheetId, String sheetType)throws Exception{
        if(!CommonUtil.isEmpty(sheetId)&&!CommonUtil.isEmpty(sheetId)&&!CommonUtil.isEmpty(sheetType)){
            String url = activitiUrl +"/workflow/killProcess.do?sheetId="+sheetId+"&sheetType="+sheetType;
            return request(url);
        }else{
            throw new Exception("杀死流程不允许为空异常");
        }
    }
    
    /**
     * 结束流程
     */
    public static String killProcessByCode(String sheetId, String sheetType, String processCode)throws Exception{
        if(!CommonUtil.isEmpty(sheetId)&&!CommonUtil.isEmpty(sheetId)&&!CommonUtil.isEmpty(sheetType)){
            String url = activitiUrl +"/workflow/killProcessByCode.do?sheetId="+sheetId+"&sheetType="+sheetType + "&processCode="+processCode;
            return request(url);
        }else{
            throw new Exception("杀死流程不允许为空异常");
        }
    }
	
	/**
	 * @param url
	 * @return
	 */
	@SuppressWarnings("deprecation")
    public static String request(String url)throws Exception{
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setFollowRedirects(false);
		String response = null;
		try {
			method.setRequestBody(" ");
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
