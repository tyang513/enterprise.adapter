package com.td.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.td.proxy.ProxyConstant;

/**
 * http 协议工具类
 * 
 * @author yangtao
 */
@SuppressWarnings("unchecked")
public class HttpUtil {

	/**
	 * 日志
	 */
	public static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> doUploadFile(String url, Map<String, Object> param, String type) throws Exception {

		CloseableHttpClient httpclient = ProxyConstant.TASK_SERVICE_PROTOCOL_TYPE_HTTPS.equalsIgnoreCase(type) ? getHttpClient() : HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			for (Entry<String, Object> entry : param.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof File) {
					FileBody fileBody = new FileBody((File) value);
					builder.addPart(key, fileBody);
				} else {
					StringBody stringBody = new StringBody(String.valueOf(value), ContentType.TEXT_PLAIN);
					builder.addPart(key, stringBody);
				}
			}
			HttpEntity reqEntity = builder.build();
			
			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity resEntity = response.getEntity();
				
				InputStream inputStream = resEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				String line = null;
				StringBuffer sb = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				inputStream.close();
				logger.info("收到响应对象" + sb.toString());
				if (response.getStatusLine().getStatusCode() >= 400){
					param.remove("file");
					param.remove("token");
					param.remove("appkey");
					throw new RuntimeException("调用接口出错 url=" + url + "参数为：" + JSONObject.fromObject(param) + "服务器返回" + response.getStatusLine().getStatusCode());
				}
				// JSON to Map
				Map<String, Object> returnMap = JSONObject.fromObject(sb.toString());
				Integer httpStatus = getResponseStatus(returnMap);
				EntityUtils.consume(resEntity);
				if (httpStatus != 200){
					param.remove("file");
					param.remove("token");
					param.remove("appkey");
					logger.info(JSONObject.fromObject(param).toString());
					throw new Exception("调用接口出错 url=" + url + "参数为：" + JSONObject.fromObject(param).toString() + "服务器返回" + sb.toString());
				}
				return returnMap;
			} catch (Exception e) {
				logger.error("获取响应出错", e);
				throw e;
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("文件上传出错", e);
			throw e;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("关闭httpclinet异常", e);
			}
		}
	}

	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> doPostJson(String url, Map<String, Object> params, Map<String, Object> requestBodyMap, String type) throws Exception {
		CloseableHttpClient httpclient = ProxyConstant.TASK_SERVICE_PROTOCOL_TYPE_HTTPS.equalsIgnoreCase(type) ? getHttpClient() : HttpClients.createDefault();
		try {
			if (params != null && !params.isEmpty()) {
				List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(params.size());
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					Object value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(value)));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));
			}
			logger.info("do post url : " + url);
			HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Content-Type", "application/json");
			JSONObject jsonObject = JSONObject.fromObject(requestBodyMap);
			logger.info(jsonObject.toString());
			StringEntity entity = new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON);
			httppost.setEntity(entity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity resEntity = response.getEntity();
				InputStream inputStream = resEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				String line = null;
				StringBuffer sb = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				inputStream.close();
				logger.info("收到响应对象" + sb.toString());
				if (response.getStatusLine().getStatusCode() >= 400) {
					params.remove("file");
					params.remove("token");
					params.remove("appkey");
					throw new RuntimeException("调用接口出错 url=" + url + "参数为：" + JSONObject.fromObject(params) + "服务器返回"
							+ response.getStatusLine().getStatusCode());
				}
				// JSON to Map
				Map<String, Object> returnMap = JSONObject.fromObject(sb.toString());
				Integer httpStatus = getResponseStatus(returnMap);
				EntityUtils.consume(resEntity);
				if (httpStatus != 200) {
					params.remove("file");
					params.remove("token");
					params.remove("appkey");
					logger.info(JSONObject.fromObject(params).toString());
					throw new Exception("调用接口出错 url=" + url + "参数为：" + JSONObject.fromObject(params).toString() + "服务器返回" + sb.toString());
				}
				return returnMap;
			} catch (Exception e) {
				logger.error("获取响应出错", e);
				throw e;
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("文件上传出错", e);
			throw e;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("关闭httpclinet异常", e);
			}
		}
	}
	
	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	public static Map<String, Object> doGetUploadFile(String url, Map<String, Object> param, String type) throws Exception {
//
//		CloseableHttpClient httpclient = ProxyConstant.TASK_SERVICE_PROTOCOL_TYPE_HTTPS.equalsIgnoreCase(type) ? getHttpClient() : HttpClients.createDefault();
//		try {
//			HttpGet http = new HttpGet(url);
//			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//
//			for (Entry<String, Object> entry : param.entrySet()) {
//				String key = entry.getKey();
//				Object value = entry.getValue();
//				if (value instanceof File) {
//					FileBody fileBody = new FileBody((File) value);
//					builder.addPart(key, fileBody);
//				} else {
//					StringBody stringBody = new StringBody(String.valueOf(value), ContentType.TEXT_PLAIN);
//					builder.addPart(key, stringBody);
//				}
//			}
//			HttpEntity reqEntity = builder.build();
//			http.setEntity(reqEntity);
//			CloseableHttpResponse response = httpclient.execute(http);
//			try {
//				HttpEntity resEntity = response.getEntity();
//				if (response.getStatusLine().getStatusCode() >= 400){
//					throw new RuntimeException("调用接口出错 url=" + url + "参数为：" + JSONObject.fromObject(param) + "服务器返回" + response.getStatusLine().getStatusCode());
//				}
//				InputStream inputStream = resEntity.getContent();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//				String line = null;
//				StringBuffer sb = new StringBuffer();
//				while ((line = reader.readLine()) != null) {
//					sb.append(line);
//				}
//				reader.close();
//				inputStream.close();
//				logger.info("收到JSON对象" + sb.toString());
//				// JSON to Map
//				Map<String, Object> returnMap = JSONObject.fromObject(sb.toString());
//				Integer httpStatus = getResponseStatus(returnMap);
//				EntityUtils.consume(resEntity);
//				if (httpStatus != 200){
//					param.remove("file");
//					param.remove("token");
//					param.remove("appkey");
//					logger.info(JSONObject.fromObject(param).toString());
//					throw new Exception("调用接口出错 url=" + url + "参数为：" + JSONObject.fromObject(param).toString() + "服务器返回" + sb.toString());
//				}
//				return returnMap;
//			} catch (Exception e) {
//				logger.error("获取响应出错", e);
//				throw e;
//			} finally {
//				response.close();
//			}
//		} catch (Exception e) {
//			logger.error("文件上传出错", e);
//			throw e;
//		} finally {
//			try {
//				httpclient.close();
//			} catch (IOException e) {
//				logger.error("关闭httpclinet异常", e);
//			}
//		}
//	}
	
	public static Integer getResponseStatus(Map<String, Object> param) {
		Integer returnCode = null;
		if (param.containsKey("code")) {
			returnCode = (Integer) param.get("code");
		} else if (param.containsKey("status")) {
			Object status = param.get("status");
			if (status instanceof Integer) {
				returnCode = (Integer) status;
			} else if (status instanceof String) {
				returnCode = Integer.valueOf((String) status);
			}
		}
		return returnCode;
	}
	
	/**
	 * 下载文件
	 * 
	 * @param url
	 *            URL地址
	 * @param paramMap
	 *            参数
	 * @param destFilePath
	 *            下载文件保存路径
	 * @throws Exception
	 *             异常
	 */
	public static void doDownloadFile(String url, Map<String, Object> paramMap, String destFilePath, String type) throws Exception {
		
		CloseableHttpClient httpclient = ProxyConstant.TASK_SERVICE_PROTOCOL_TYPE_HTTPS.equalsIgnoreCase(type) ? getHttpClient() : HttpClients
				.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			for (Entry<String, Object> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				StringBody stringBody = new StringBody(String.valueOf(value), ContentType.TEXT_PLAIN);
				builder.addPart(key, stringBody);
			}
			HttpEntity reqEntity = builder.build();
			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity resEntity = response.getEntity();
				// 下载文件
				InputStream inputStream = resEntity.getContent();
				FileOutputStream outputStream = new FileOutputStream(new File(destFilePath));

				int i;
				while ((i = inputStream.read()) != -1) {
					outputStream.write(i);
				}
				outputStream.close();
				inputStream.close();

				EntityUtils.consume(resEntity);
			} catch (Exception e) {
				logger.error("获取响应出错", e);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("文件上传出错", e);
			throw e;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("关闭httpclinet异常", e);
			}
		}
	}

	/**
	 * 创建基于https的客户端访问连接
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
		ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
		// 指定信任密钥存储对象和连接套接字工厂
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			// 信任任何链接
			TrustStrategy anyTrustStrategy = new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) {
					return true;
				}
			};
			SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
			LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			registryBuilder.register("https", sslSF);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		Registry<ConnectionSocketFactory> registry = registryBuilder.build();
		// 设置连接管理器
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
		// connManager.setDefaultConnectionConfig(connConfig);
		// connManager.setDefaultSocketConfig(socketConfig);
		// 构建客户端
		return HttpClientBuilder.create().setConnectionManager(connManager).build();
	}

	public static void main(String[] args) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("token", "3bmH5mfNZURYIg");
		paramMap.put("appkey", "555c59cd88805ede3192e072");
		paramMap.put("callBackUrl", "http://211.151.127.94:8080/proxy/common/callBackUrl.do?taskCode=TI20150515012");
		paramMap.put("file", new File("/dmp/0123456789.gz"));
		paramMap.put("type", ProxyConstant.API_TALKINGDATA_IDUPLPAD_TYPE);
		paramMap.put("inputIdType", ProxyConstant.API_TALKINGDATA_IDUPLPAD_INPUT_ID_TYPE);
		paramMap.put("outputIdType", "1,2,4,5,6");
		String url = "http://localhost:8080/proxy/common/upload2.do";//"https://api.talkingdata.com/dmp/batch/common/idupload";
		Map<String, Object> returnMap = HttpUtil.doUploadFile(url, paramMap, "https");
		Integer taskid = (Integer) returnMap.get("taskid");
		System.out.println(taskid);
		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("a", "1");
//		map.put("b", "2");
//		System.out.println(JSONObject.fromObject(map));
//		
//		Map<String, Object> jsonMap = JSONObject.fromObject("{   \"status\" :401,   \"msg\" :\"Unauthorized\"}");
//		System.out.println(jsonMap.get("msg"));
//		System.out.println(jsonMap.get("status"));
		//
		// url = "http://localhost:8080/proxy/common/download.do";
		// Map<String, Object> downloadParamMap = new HashMap<String, Object>();
		// downloadParamMap.put("token", "token");
		// downloadParamMap.put("appkey" , "appkey");
		// downloadParamMap.put("taskid" , taskid);
		// HttpUtil.doDownloadFile(url, downloadParamMap, "d://" + taskid +
		// ".gz");

		// if (args.length != 1) {
		// System.out.println("File path not given");
		// System.exit(1);
		// }
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// try {
		// HttpPost httppost = new HttpPost("http://localhost:8080" +
		// "/servlets-examples/servlet/RequestInfoExample");
		//
		// FileBody bin = new FileBody(new File(args[0]));
		// StringBody comment = new StringBody("A binary file of some kind",
		// ContentType.TEXT_PLAIN);
		//
		// HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin",
		// bin).addPart("comment", comment).build();
		//
		// httppost.setEntity(reqEntity);
		//
		// System.out.println("executing request " + httppost.getRequestLine());
		// CloseableHttpResponse response = httpclient.execute(httppost);
		// try {
		// System.out.println("----------------------------------------");
		// System.out.println(response.getStatusLine());
		// HttpEntity resEntity = response.getEntity();
		// if (resEntity != null) {
		// System.out.println("Response content length: " +
		// resEntity.getContentLength());
		// }
		// EntityUtils.consume(resEntity);
		// } finally {
		// response.close();
		// }
		// } finally {
		// httpclient.close();
		// }
	}

}
