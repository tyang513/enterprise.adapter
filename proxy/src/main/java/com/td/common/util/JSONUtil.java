package com.td.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;

	
public class JSONUtil {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	
	/**
	 * 复杂对象转json字符串,obj可以为list
	 * 格式化json
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String objToJsonFormat(Object obj) throws Exception{
		
		String str = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		
		return str;
	}
	
	/**
	 * json字符串转换为list对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static List<?> jsonToList(String jsonStr,Class<?> clazz) throws Exception {
		TypeFactory typeFactory = TypeFactory.defaultInstance(); 
		// 指定容器结构和类型（这里是ArrayList和clazz）
		List<?> list = objectMapper.readValue(jsonStr,typeFactory.constructCollectionType(ArrayList.class,clazz));
		
		return list;
	}
	
	/**
	 * json字符串转换为map对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Map<String, List<Object>> jsonToMap(String jsonStr,Class<?> clazz) throws Exception {
		
		Map<String,List<Object>> map = objectMapper.readValue(jsonStr,
				new TypeReference<Map<String,List<Object>>>() {});
		
		return map;
	}
	
	/**
	 * 复杂json串,转Bean对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Object jsonToBean(String jsonStr,Class<?> clazz) throws Exception {
		Object obj = objectMapper.readValue(jsonStr,clazz);
		
		return obj;
	}
	
	/**
	 * json字符串生成待导出文件
	 * @param request
	 * @param json
	 * @return
	 */
	public static File jsonToFile(HttpServletRequest request, String json, String fileName) throws Exception {
		
		File f = createFile(request, fileName);
		
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;
		fileOutputStream = new FileOutputStream(f);
		outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
		bufferedWriter = new BufferedWriter(outputStreamWriter);
		bufferedWriter.write(json);
		bufferedWriter.flush();
		bufferedWriter.close();
		outputStreamWriter.close();
		fileOutputStream.close();
		return f;
	}
	
	public static File createFile(HttpServletRequest request, String fileName) throws IOException {
		
		File dir = new File(request.getServletContext().getRealPath(File.separator + "json"));
		File f = new File(request.getServletContext().getRealPath(File.separator + "json" + File.separator +fileName));
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		if(!f.exists()) {
			f.createNewFile();
		}
		
		return f;
	}
	
	/**
	 * 实体对象转为map，含其父类属性
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> objToHash(Object obj) throws IllegalArgumentException,IllegalAccessException { 
	      
	    Map<String, Object> hashMap = new HashMap<String, Object>(); 
	    Class clazz = obj.getClass(); 
	    List<Class> clazzs = new ArrayList<Class>(); 
	      
	    do { 
	        clazzs.add(clazz); 
	        clazz = clazz.getSuperclass(); 
	    } while (!clazz.equals(Object.class)); 
	      
	    for (Class iClazz : clazzs) { 
	        Field[] fields = iClazz.getDeclaredFields(); 
	        for (Field field : fields) { 
	            Object objVal = null; 
	            field.setAccessible(true); 
	            objVal = field.get(obj); 
	            hashMap.put(field.getName(), objVal); 
	        } 
	    } 
	      
	    return hashMap; 
	}
	
	/**
	 * map转成类对象
	 * @param map
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void mapToObj(Map<String, Object> map, Object obj) throws IllegalArgumentException,IllegalAccessException { 
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields(); 
        for (Field field : fields) {
        	field.setAccessible(true);
        	if(map.containsKey(field.getName())) {
        		if (field.getType().equals(Date.class)) {
        			if(map.get(field.getName()) != null) {
        				field.set(obj, new Date((Long)map.get(field.getName())));
        			}
        		} else {
        			field.set(obj, map.get(field.getName()));
        		}
        	}
        }
	}
	
	public static void main(String[] args) {
		String aa= "-1";
		try {
			System.out.println(JSONUtil.objToJsonFormat(aa));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
