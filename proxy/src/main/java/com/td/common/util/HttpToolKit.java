package com.td.common.util;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;

/**
 * @author shuqing
 * @version 创建时间：2014年11月14日 上午10:25:09
 * @desc 类说明
 */

public class HttpToolKit {
	
//	private static final CloseableHttpClient httpClient;
//	public static final String CHARSET = "UTF-8";
//
//	static {
//		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
//		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
//	}
//
//	public static ResponseEntity doGet(String url, Map<String, String> params) {
//		return doGet(url, params, CHARSET);
//	}
//
//	public static ResponseEntity doPost(String url, Map<String, String> params) {
//		return doPost(url, params, CHARSET);
//	}
//
//	public static ResponseEntity doPut(String url, Map<String, Object> params) {
//		return doPut(url, params, CHARSET);
//	}
//
//	public static ResponseEntity doDelete(String url) {
//		return doDelete(url, CHARSET);
//	}
//
//	/**
//	 * HTTP Get 获取内容
//	 * 
//	 * @param url
//	 *            请求的url地址 ?之前的地址
//	 * @param params
//	 *            请求的参数
//	 * @param charset
//	 *            编码格式
//	 * @return 页面内容
//	 */
//	public static ResponseEntity doGet(String url, Map<String, String> params, String charset) {
//		if (StringUtils.isBlank(url)) {
//			return null;
//		}
//		try {
//			if (params != null && !params.isEmpty()) {
//				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
//				for (Map.Entry<String, String> entry : params.entrySet()) {
//					String value = entry.getValue();
//					if (value != null) {
//						pairs.add(new BasicNameValuePair(entry.getKey(), value));
//					}
//				}
//				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
//			}
//			System.out.println("do get url : " + url);
//			HttpGet httpGet = new HttpGet(url);
//			CloseableHttpResponse response = httpClient.execute(httpGet);
//			int statusCode = response.getStatusLine().getStatusCode();
//			if (statusCode != 200) {
//				httpGet.abort();
//			}
//			HttpEntity entity = response.getEntity();
//			String result = null;
//			if (entity != null) {
//				try {
//					result = EntityUtils.toString(entity, "utf-8");
//				} catch (Exception e) {
//					e.printStackTrace();
//					result = e.getMessage();
//				}
//			}
//			EntityUtils.consume(entity);
//			response.close();
//			ResponseEntity responseEntity = new ResponseEntity();
//			responseEntity.setCode(statusCode);
//			responseEntity.setEntity(JSONObject.fromObject(result));
//			return responseEntity;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * HTTP Post 获取内容
//	 * 
//	 * @param url
//	 *            请求的url地址 ?之前的地址
//	 * @param params
//	 *            请求的参数
//	 * @param charset
//	 *            编码格式
//	 * @return 页面内容
//	 */
//	public static ResponseEntity doPost(String url, Map<String, String> params, String charset) {
//		if (StringUtils.isBlank(url)) {
//			return null;
//		}
//		try {
//			List<NameValuePair> pairs = null;
//			if (params != null && !params.isEmpty()) {
//				pairs = new ArrayList<NameValuePair>(params.size());
//				for (Map.Entry<String, String> entry : params.entrySet()) {
//					String value = entry.getValue();
//					if (value != null) {
//						pairs.add(new BasicNameValuePair(entry.getKey(), value));
//					}
//				}
//			}
//			HttpPost httpPost = new HttpPost(url);
//			if (pairs != null && pairs.size() > 0) {
//				httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
//			}
//			CloseableHttpResponse response = httpClient.execute(httpPost);
//			int statusCode = response.getStatusLine().getStatusCode();
//
//			HttpEntity entity = response.getEntity();
//			String result = null;
//			if (entity != null) {
//				try {
//					result = EntityUtils.toString(entity, "utf-8");
//				} catch (Exception e) {
//					e.printStackTrace();
//					result = e.getMessage();
//				}
//			}
//
//			if (statusCode != 200) {
//				httpPost.abort();
//				// throw new RuntimeException("HttpClient,error status code :"
//				// + statusCode + " result : " + result);
//			}
//
//			EntityUtils.consume(entity);
//			response.close();
//			ResponseEntity responseEntity = new ResponseEntity();
//			responseEntity.setCode(statusCode);
//			responseEntity.setEntity(JSONObject.fromObject(result));
//			return responseEntity;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static ResponseEntity doPost(String url, String requestBody, String charset, int timeout) {
//		System.out.println("url " + url);
//		System.out.println(requestBody);
//		if (StringUtils.isBlank(url)) {
//			return null;
//		}
//		try {
//			HttpPost httpPost = new HttpPost(url);
//
//			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout)
//					.setSocketTimeout(timeout).build();
//			httpPost.setConfig(requestConfig);
//
//			httpPost.setHeader("Content-Type", "application/json");
//
//			if (StringUtils.isNotBlank(requestBody))
//				httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
//
//			CloseableHttpResponse response = httpClient.execute(httpPost);
//			int statusCode = response.getStatusLine().getStatusCode();
//
//			HttpEntity entity = response.getEntity();
//			String result = null;
//			if (entity != null) {
//				try {
//					result = EntityUtils.toString(entity, "utf-8");
//				} catch (Exception e) {
//					e.printStackTrace();
//					result = e.getMessage();
//				}
//			}
//
//			System.out.println("status code " + statusCode);
//			if (statusCode != 200) {
//				httpPost.abort();
//				// throw new RuntimeException("HttpClient,error status code :"
//				// + statusCode + " result : " + result);
//			}
//
//			EntityUtils.consume(entity);
//			response.close();
//			ResponseEntity responseEntity = new ResponseEntity();
//			responseEntity.setCode(statusCode);
//			System.out.println("result " + result);
//			responseEntity.setEntity(JSONObject.fromObject(result));
//			return responseEntity;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static ResponseEntity doDelete(String url, String charset) {
//		if (StringUtils.isBlank(url)) {
//			return null;
//		}
//		try {
//			HttpDelete httpDelete = new HttpDelete(url);
//			CloseableHttpResponse response = httpClient.execute(httpDelete);
//			int statusCode = response.getStatusLine().getStatusCode();
//
//			HttpEntity entity = response.getEntity();
//			String result = null;
//			if (entity != null) {
//				try {
//					result = EntityUtils.toString(entity, "utf-8");
//				} catch (Exception e) {
//					e.printStackTrace();
//					result = e.getMessage();
//				}
//			}
//
//			if (statusCode != 200) {
//				httpDelete.abort();
//				// throw new RuntimeException("HttpClient,error status code :"
//				// + statusCode + " result : " + result);
//			}
//
//			EntityUtils.consume(entity);
//			response.close();
//			ResponseEntity responseEntity = new ResponseEntity();
//			responseEntity.setCode(statusCode);
//			responseEntity.setEntity(JSONObject.fromObject(result));
//			return responseEntity;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static ResponseEntity doPut(String url, Map<String, Object> params, String charset) {
//		if (StringUtils.isBlank(url)) {
//			return null;
//		}
//		try {
//			HttpPut httpPost = new HttpPut(url);
//
//			httpPost.setHeader("Content-Type", "application/json");
//
//			if (params != null) {
//				httpPost.setEntity(new StringEntity(JSONObject.fromObject(params).toString(), ContentType.APPLICATION_JSON));
//			}
//
//			CloseableHttpResponse response = httpClient.execute(httpPost);
//			int statusCode = response.getStatusLine().getStatusCode();
//
//			HttpEntity entity = response.getEntity();
//			String result = null;
//			if (entity != null) {
//				try {
//					result = EntityUtils.toString(entity, "utf-8");
//				} catch (Exception e) {
//					e.printStackTrace();
//					result = e.getMessage();
//				}
//			}
//
//			if (statusCode != 200) {
//				httpPost.abort();
//				// throw new RuntimeException("HttpClient,error status code :"
//				// + statusCode + " result : " + result);
//			}
//
//			EntityUtils.consume(entity);
//			response.close();
//			ResponseEntity responseEntity = new ResponseEntity();
//			responseEntity.setCode(statusCode);
//			responseEntity.setEntity(JSONObject.fromObject(result));
//			return responseEntity;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
