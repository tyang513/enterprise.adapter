import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.td.common.util.HttpUtil;


public class Test {

	public static void main(String[] args) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("advertiserID", 32);
		param.put("segmentID", 31);
		param.put("adpID", 17);
		param.put("duration", 2);
		JSONObject jsonObject = JSONObject.fromObject(param);
		System.out.println(jsonObject);
		
		String url ="http://10.10.67.40:8082/distribute/create";// "http://10.10.67.40:8082/segment/update";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startdate", "2015-05-21");
		params.put("enddate", "2015-05-22");
		Map<String, Object> requestBodyMap = new HashMap<String, Object>();
		requestBodyMap.put("duration", 2);
		requestBodyMap.put("segmentID", 31);
		requestBodyMap.put("advertiserID", 32);
		requestBodyMap.put("adpID", 17);
//		requestBodyMap.put("id", "31");
//		requestBodyMap.put("name", "TI20150521010");
//		requestBodyMap.put("relation", "0");
		Map<String, Object> returnMap = HttpUtil.doPostJson(url, params, requestBodyMap, "http");
		System.out.println(JSONObject.fromObject(returnMap).toString());
	}

}
