package com.td.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import com.td.common.bean.PageBean;


/**
 * @author Administrator
 * @modify 2013-8-19 
 */
public class UIUtil {
	
	public static HashMap iconsMap;
	
	
	public static String getTree(List<LinkedHashMap<String, Object>> list){
		
		initIcons();
		
		String treeStr = "";

		//收集所有的父节点
		Map parentNodes = new HashMap();
		//非父节点
		List<LinkedHashMap<String, Object>> nParentNodes = new ArrayList();
		//一级菜单顺序
		List<String> levelMenu1 = new ArrayList<String>();
		for (LinkedHashMap<String, Object> linkedHashMap : list) {
			String resourceId = linkedHashMap.get("resourceId").toString();
			if(isParrent(list,resourceId)){
				Map map = new HashMap<String,Object>();
				map.put("node",linkedHashMap);
				parentNodes.put(resourceId,map);
				if(resourceId.length()==3){
					levelMenu1.add(resourceId);
				}
			}
		}
		//将自己挂在父节点之下
		for (LinkedHashMap<String, Object> linkedHashMap : list) {
			String resourceId = linkedHashMap.get("resourceId").toString();
			//获取自己
			Map mySelf = (Map)parentNodes.get(resourceId);
			if(mySelf==null){
				mySelf = new HashMap();
				mySelf.put("node",linkedHashMap);
			}
			resourceId = resourceId.substring(0,resourceId.length()-3);
			Map map = (Map)parentNodes.get(resourceId);
			//判断自己有没有父节点
			List nodes = null;
			if(map!=null){
				nodes = (List)map.get("nodes");
			}else{
				continue;
			}
			
			if(nodes!=null){
				Map m = new HashMap();
				m.put(linkedHashMap.get("resourceId"),mySelf);
				nodes.add(m);
			}else{
				nodes = new ArrayList<Map<String,Object>>();
				Map m = new HashMap();
				m.put(linkedHashMap.get("resourceId"),mySelf);
				nodes.add(m);
				map.put("nodes",nodes);
			}
		}
	
		
		treeStr = splitJoinEasyUiTree(parentNodes,levelMenu1);
		//所有{签都加,
		treeStr = treeStr.replace("{",",{");
		//还原吃
		treeStr = treeStr.replace("\"children\":[,{","\"children\":[{");
		treeStr = "["+treeStr.substring(1)+"]";
		//System.out.println(treeStr);
		return treeStr;
	}
	
	
	public static void getTree(List<LinkedHashMap<String, Object>> list,HttpSession session){
		
		initIcons();
		
		String treeStr = "";

		//收集所有的父节点
		Map parentNodes = new HashMap();
		//非父节点
		List<LinkedHashMap<String, Object>> nParentNodes = new ArrayList();
		//一级菜单顺序
		List<LinkedHashMap> levelMenu1 = new ArrayList<LinkedHashMap>();
		for (LinkedHashMap<String, Object> linkedHashMap : list) {
			String resourceId = linkedHashMap.get("resourceId").toString();
			if(isParrent(list,resourceId)){
				Map map = new HashMap<String,Object>();
				map.put("node",linkedHashMap);
				parentNodes.put(resourceId,map);
				if(resourceId.length()==3){
					levelMenu1.add(linkedHashMap);
				}
			}
		}
		//将自己挂在父节点之下
		for (LinkedHashMap<String, Object> linkedHashMap : list) {
			String resourceId = linkedHashMap.get("resourceId").toString();
			//获取自己
			Map mySelf = (Map)parentNodes.get(resourceId);
			if(mySelf==null){
				mySelf = new HashMap();
				mySelf.put("node",linkedHashMap);
			}
			resourceId = resourceId.substring(0,resourceId.length()-3);
			linkedHashMap.put("parentId",resourceId);
			Map map = (Map)parentNodes.get(resourceId);
			//判断自己有没有父节点
			List nodes = null;
			if(map!=null){
				nodes = (List)map.get("nodes");
			}else{
				continue;
			}
			
			if(nodes!=null){
				Map m = new HashMap();
				m.put(linkedHashMap.get("resourceId"),mySelf);
				nodes.add(m);
			}else{
				nodes = new ArrayList<Map<String,Object>>();
				Map m = new HashMap();
				m.put(linkedHashMap.get("resourceId"),mySelf);
				nodes.add(m);
				map.put("nodes",nodes);
			}
		}
		session.setAttribute("levelmenu1",levelMenu1);
		session.setAttribute("menu",parentNodes);
	}
	/**
	 * @description 判断是否是父节点
	 * @param list
	 * @param resourceId
	 * @return
	 */
	private static boolean isParrent(List<LinkedHashMap<String,Object>> list,String resourceId){
		boolean isParent = false;
		//System.out.println(" 1 : " + resourceId);
		int size = resourceId.length();
		for (LinkedHashMap<String,Object> map : list){
			String resourceId2 = map.get("resourceId").toString();
			//System.out.println(" 2 : " + resourceId2);
			if(resourceId.length()<resourceId2.length()){
				resourceId2 = resourceId2.substring(0,size);
				if(resourceId.equals(resourceId2)){
					isParent = true;
					break;
				}
			}
		}
		return isParent;
	}

	
	private static String splitJoinEasyUiTree(Map parentNodes,List<String> orderNodes){
		int count = 1;
		StringBuffer easyUiTree = new StringBuffer();
		StringBuffer symbol = new StringBuffer(",");
		for (String key : orderNodes) {
			Map m = (Map)parentNodes.get(key);
			getEasyUiResource(m,count,easyUiTree);
			symbol = new StringBuffer(",");
		} 
		return easyUiTree.toString(); 
	}
	
	/**
	 * @description 返回一个资源
	 * 
	 */
	private static void getEasyUiResource(Map m,Integer count,StringBuffer treeStr){
		Map map = (Map)m.get("node");
		String resourceLabel = (String)map.get("resourceLabel");
		String resourceDesc = (String)map.get("resourceDesc");
		List nodes = (List)m.get("nodes");
		if(nodes!=null){
			String icon = getIcon(resourceLabel);
			if(icon.equals("")){
				treeStr.append("{\"text\":\""+resourceLabel+"\",\"state\":\"closed\",\"children\":[");
			}else{
				treeStr.append("{\"text\":\""+resourceLabel+"\",\"state\":\"closed\",\"iconCls\":\""+icon+"\",\"children\":[");
			}
			for (Object object : nodes) {
				Map node = (Map)object;
				Set set = node.keySet();
				for (Object object2 : set) {
					Map mmm = (Map)node.get(object2);
					getEasyUiResource(mmm,count,treeStr);
				}
			}
			treeStr.append("]}");
		}else{
			treeStr.append("{\"text\":\"<A onclick=openTab("+count+",\'"+resourceLabel+"\',\'"+resourceDesc+"\')>"+resourceLabel+"</A>\",\"checked\":true,\"icon-class\":\"icon-point\"}");
			count++;
		}
	}
	
	
	
	/**
	 * @description 方法用于被JSONArray解析过的list
	 * @param total
	 * @param json 有JSONARRAY解析出的json数据
	 * @return
	 */
	public static String getEasyUiGridJson(Long total,PageBean pageBean){
//		val = json.substring(1,json.length()-1);
		String json = "";
		json = getJSONFromList((List)pageBean.getExtend().get("list"));
		json = "{\"total\":"+total+",\"rows\":"+json+"}";
		return json;
	}
	
	
	public static String getEasyUiGridJson(Long total,String json){
		//json = json.substring(1,json.length()-1);
		json = "{\"total\":"+total+",\"rows\":"+json+"}";
		return json;
	}
	
 
	public static List getListFormJSON(String jsonData){
		try {
			return new ObjectMapper().readValue(jsonData,List.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getJSONFromList(List list){
		ObjectMapper objectMapper = new ObjectMapper();
		
//		ObjectMapper mapper = new ObjectMapper();  
//		14.        StringWriter sw = new StringWriter();  
//		15.        try {  
//		16.            mapper.writeValue(sw, list);  

		try {
			return objectMapper.writeValueAsString(list);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getJSONFromList(Object obj){
		ObjectMapper objectMapper = new ObjectMapper();
		
//		ObjectMapper mapper = new ObjectMapper();  
//		14.        StringWriter sw = new StringWriter();  
//		15.        try {  
//		16.            mapper.writeValue(sw, list);  

		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description 方法用于被JSONArray解析过的list
	 * @param total
	 * @param json 有JSONARRAY解析出的json数据
	 * @return
	 */
	public static String getEasyUiGridJson(Long total,String json,String error){
		String val = "";
		//val = json.substring(1,json.length()-1);
		val=json;
		val = "{\"total\":"+total+",\"rows\":"+val+",\"message\":\""+error+"\"}";
		return val;
	}
	
	public static String getIcon(String resourceLabel){
		//System.out.println(resourceLabel);
		String icon = "";
		if(resourceLabel!=null){
			resourceLabel = resourceLabel.trim();
			icon = (String)iconsMap.get(resourceLabel);
			if(icon==null){
				icon="";
			}
		}
		return icon;
	}
	
	
	public static void initIcons(){
		if(iconsMap==null){
			iconsMap = new HashMap();
			iconsMap.put("销售收入查询","icon-consumptionSearch");
			iconsMap.put("积分账户管理","icon-integralAccountManage");
			iconsMap.put("积分相关","icon-integralAssociated");
			iconsMap.put("积分管理","icon-integralManage");
			iconsMap.put("积分销售","icon-integralSale");
			iconsMap.put("发票开票","icon-invoice");
			iconsMap.put("开发票申请","icon-invoiceApply");
			iconsMap.put("发票相关","icon-invoiceAssociated");
			iconsMap.put("Email配置管理","icon-mail");
			iconsMap.put("我的任务","icon-myTask");
			iconsMap.put("合作伙伴管理","icon-partner");
			iconsMap.put("系统管理","icon-systemManage");		
		}
	}
	
	public static JavaType getCollectionType(ObjectMapper mapper,Class<?> collectionClass, Class<?>... elementClasses) {           
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
   }   
}
