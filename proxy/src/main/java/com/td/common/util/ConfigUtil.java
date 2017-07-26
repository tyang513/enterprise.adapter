package com.td.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ConfigUtil {
	private static String configRootFolderPath = "D:/workspace-sts-3.3.0.RELEASE/Finance/WebRoot/js/app/config";
	// private static String configRootFolderPath =
	// "D:/dev/workspaces/pajrkj/finance/WebRoot/jsx/app/config";

	private static String excludeFileNames[] = { "log-config.json", "formview.json", "searchBar.json", "service.json" };

	private static String authorizedSearchBar[] = { "partnerList", "contactList", "accountList", "pointAccountList" };

	private static String getFileAsText(File file, boolean ignoreAnnotation) {
		String jsonStr = null;
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				if(ignoreAnnotation){
					if(!line.contains("//")){
						sb.append(line);
					}
				} else {
					sb.append(line);
				}				
				line = reader.readLine();
			}
			reader.close();
			jsonStr = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	private static List<Map<String, String>> getSearchBarButtonResources(String rootFolderPath, List<String> includeList) {
		File dir = new File(rootFolderPath);
		File file[] = dir.listFiles();
		int id1, id2, id3, id4;
		String rid, rlabel, rdesc;
		id1 = 100;
		Map<String, String> resource;
		List<Map<String, String>> resourceList = new ArrayList<Map<String, String>>();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile() && file[i].getName().equals("searchBar.json")) {
				id1++;
				id2 = 100;

				String key1 = "searchBar";
				rid = String.valueOf(id1);
				rlabel = key1;
				rdesc = key1;

				resource = new HashMap<String, String>();
				resource.put("rlevel", "L1");
				resource.put("rid", rid);
				resource.put("rlabel", rlabel);
				resource.put("rdesc", rdesc);
				resourceList.add(resource);

				String jsonStr = getFileAsText(file[i], false);
				JSONObject jsonConfig = JSONObject.fromObject(jsonStr);
				Iterator it2 = jsonConfig.keys();
				id3 = 100;
				while (it2.hasNext()) {
					String key2 = (String) it2.next();
					if (!includeList.contains(key2)) {
						continue;
					}
					id2++;
					JSONObject l2Obj = jsonConfig.getJSONObject(key2);

					rid = String.valueOf(id1) + String.valueOf(id2);
					rlabel = l2Obj.containsKey("text") ? l2Obj.getString("text") : key2;
					rdesc = key1 + "/" + key2;

					resource = new HashMap<String, String>();
					resource.put("rlevel", "L2");
					resource.put("rid", rid);
					resource.put("rlabel", rlabel);
					resource.put("rdesc", rdesc);
					resourceList.add(resource);

					boolean isArray = true;
					try {
						JSONObject buttonsObj = l2Obj.getJSONObject("buttons");
						isArray = false;
					} catch (Exception e) {
						isArray = true;
					}

					if (!isArray) {
						JSONObject buttonsObj = l2Obj.getJSONObject("buttons");
						Iterator it3 = buttonsObj.keys();
						id4 = 100;
						while (it3.hasNext()) {
							id3++;
							String key3 = (String) it3.next();
							rid = String.valueOf(id1) + String.valueOf(id2) + String.valueOf(id3);
							rlabel = key3;
							rdesc = key1 + "/" + key2 + "/" + key3;

							resource = new HashMap<String, String>();
							resource.put("rlevel", "L3");
							resource.put("rid", rid);
							resource.put("rlabel", rlabel);
							resource.put("rdesc", rdesc);
							resourceList.add(resource);

							JSONArray buttonsArray = buttonsObj.getJSONArray(key3);
							Iterator it4 = buttonsArray.iterator();
							while (it4.hasNext()) {
								id4++;
								JSONObject buttonObj = (JSONObject) it4.next();
								String key4 = buttonObj.getString("eventId");

								rid = String.valueOf(id1) + String.valueOf(id2) + String.valueOf(id3) + String.valueOf(id4);
								rlabel = buttonObj.containsKey("text") ? buttonObj.getString("text") : key4;
								rdesc = key1 + "/" + key2 + "/" + key3 + "/" + key4;

								resource = new HashMap<String, String>();
								resource.put("rlevel", "L4");
								resource.put("rid", rid);
								resource.put("rlabel", rlabel);
								resource.put("rdesc", rdesc);
								resourceList.add(resource);
							}
						}
					} else {
						JSONArray buttonsArray = l2Obj.getJSONArray("buttons");
						Iterator it3 = buttonsArray.iterator();
						id4 = 100;
						while (it3.hasNext()) {
							id3++;
							JSONObject buttonObj = (JSONObject) it3.next();
							String key3 = buttonObj.getString("eventId");

							rid = String.valueOf(id1) + String.valueOf(id2) + String.valueOf(id3);
							rlabel = buttonObj.containsKey("text") ? buttonObj.getString("text") : key3;
							rdesc = key1 + "/" + key2 + "/" + key3;

							resource = new HashMap<String, String>();
							resource.put("rlevel", "L3");
							resource.put("rid", rid);
							resource.put("rlabel", rlabel);
							resource.put("rdesc", rdesc);
							resourceList.add(resource);
						}
					}
				}

				break;
			}
		}

		return resourceList;
	}

	private static List<Map<String, String>> getFormButtonResources(String rootFolderPath, List<String> excludeList) {
		File dir = new File(rootFolderPath);
		File file[] = dir.listFiles();
		int id1, id2, id3, id4;
		String rid, rlabel, rdesc;
		id1 = 101;
		Map<String, String> resource;
		List<Map<String, String>> resourceList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				String fileName = file[i].getName();
				System.out.println("=========Parse " + fileName + " =========");
				if (!excludeList.contains(fileName)) {
					String jsonStr = getFileAsText(file[i], false);
					try {
						JSONObject jsonConfig = JSONObject.fromObject(jsonStr);
						Iterator it1 = jsonConfig.keys();
						id2 = 100;
						while (it1.hasNext()) {
							id1++;
							String key1 = (String) it1.next();
							JSONObject l1Obj = jsonConfig.getJSONObject(key1);
							rid = String.valueOf(id1);
							rlabel = l1Obj.containsKey("text") ? l1Obj.getString("text") : key1;
							rdesc = key1;

							resource = new HashMap<String, String>();
							resource.put("rlevel", "L1");
							resource.put("rid", rid);
							resource.put("rlabel", rlabel);
							resource.put("rdesc", rdesc);
							resourceList.add(resource);

							Iterator it2 = l1Obj.keys();
							id3 = 100;
							while (it2.hasNext()) {
								id2++;
								String key2 = (String) it2.next();
								JSONObject l2Obj = l1Obj.getJSONObject(key2);
								JSONArray northArray = l2Obj.getJSONArray("north");
								JSONObject optionsObj = northArray.getJSONObject(0).getJSONObject("options");
								JSONArray buttonsArray = optionsObj.getJSONArray("buttons");

								rid = String.valueOf(id1) + String.valueOf(id2);
								rlabel = l2Obj.containsKey("text") ? l2Obj.getString("text") : key2;
								rdesc = key1 + "/" + key2;

								resource = new HashMap<String, String>();
								resource.put("rlevel", "L2");
								resource.put("rid", rid);
								resource.put("rlabel", rlabel);
								resource.put("rdesc", rdesc);
								resourceList.add(resource);

								Iterator it3 = buttonsArray.iterator();
								id4 = 100;
								while (it3.hasNext()) {
									id3++;
									JSONObject buttonObj = (JSONObject) it3.next();
									String key3 = buttonObj.getString("eventId");

									rid = String.valueOf(id1) + String.valueOf(id2) + String.valueOf(id3);
									rlabel = buttonObj.containsKey("text") ? buttonObj.getString("text") : key3;
									rdesc = key1 + "/" + key2 + "/" + key3;

									resource = new HashMap<String, String>();
									resource.put("rlevel", "L3");
									resource.put("rid", rid);
									resource.put("rlabel", rlabel);
									resource.put("rdesc", rdesc);
									resourceList.add(resource);

									if (key3.equals("moreAction")) {
										JSONArray menuArray = buttonObj.getJSONArray("menus");
										Iterator it4 = menuArray.iterator();
										while (it4.hasNext()) {
											id4++;
											JSONObject menuObj = (JSONObject) it4.next();
											String key4 = menuObj.getString("eventId");

											rid = String.valueOf(id1) + String.valueOf(id2) + String.valueOf(id3) + String.valueOf(id4);
											rlabel = menuObj.containsKey("text") ? menuObj.getString("text") : key4;
											rdesc = key1 + "/" + key2 + "/" + key3 + "/" + key4;

											resource = new HashMap<String, String>();
											resource.put("rlevel", "L4");
											resource.put("rid", rid);
											resource.put("rlabel", rlabel);
											resource.put("rdesc", rdesc);
											resourceList.add(resource);

										}
									}
								}
							}
						}
					} catch (Exception e) {
						// e.printStackTrace();
						System.out.println("Error " + fileName);
					}
				} else {
					System.out.println("    -- Ignore");
				}
			}
		}
		return resourceList;
	}

	public static List<Map<String, String>> getResources() {
		List<Map<String, String>> resourceList = getSearchBarButtonResources(configRootFolderPath, Arrays.asList(authorizedSearchBar));
		// List<Map<String, String>> resourceList1 =
		// getFormButtonResources(rootFolderPath,
		// Arrays.asList(excludeFileNames));
		// resourceList.addAll(resourceList1);
		return resourceList;
	}

	public static List<Map<String, String>> getConfigServices(String serviceFilePath) {
		List<Map<String, String>> services = new ArrayList<Map<String, String>>();
		String jsonStr = getFileAsText(new File(serviceFilePath), true);
		try {
			JSONObject jsonConfig = JSONObject.fromObject(jsonStr);
			Iterator it1 = jsonConfig.keys();
			while (it1.hasNext()) {
				String key1 = (String) it1.next();
				JSONObject l1Obj = jsonConfig.getJSONObject(key1);
				Map<String, String> service = new HashMap<String, String>();
				service.put("type", l1Obj.getString("type"));
				service.put("url", l1Obj.getString("url"));
				service.put("dataType", l1Obj.getString("dataType"));
				if (l1Obj.containsKey("contentType")) {
					service.put("contentType", l1Obj.getString("contentType"));
				}
				services.add(service);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e);
		}

		return services;
	}

	public static List<Map<String, String>> getJSServices(String jsLogFilePath) {
		List<Map<String, String>> services = new ArrayList<Map<String, String>>();
		File file = new File(jsLogFilePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				if (line.contains(".do")) {
					// System.out.println(line);

					if (line.contains("//")) {
						// System.out.println("Ignore : " + line);
						line = reader.readLine();
						continue;
					}
					Map<String, String> service = new HashMap<String, String>();

					Pattern pattern;
					Matcher matcher;
					boolean flag = false;

					if (!flag) {
						// 匹配 双引号 + 问号
						pattern = Pattern.compile("(?<=\")(.+?)(?=do\\?)");
						matcher = pattern.matcher(line);
						while (matcher.find()) {
							String url = matcher.group() + "do?";
							// System.out.println("url -- " + url);
							if (url.contains("\\\"")) {
								pattern = Pattern.compile("(?<=\\\")(.+?)(?=do\\?)");
								matcher = pattern.matcher(url);
								while (matcher.find()) {
									service.put("url", matcher.group() + "do");
									// System.out.println("----" +
									// matcher.group() + "do");
									flag = true;
									break;
								}
							} else if (url.contains("href=\"")) {
								pattern = Pattern.compile("(?<=href=\")(.+?)(?=do\\?)");
								matcher = pattern.matcher(url);
								while (matcher.find()) {
									service.put("url", matcher.group() + "do");
									// System.out.println("----" +
									// matcher.group() + "do");
									flag = true;
									break;
								}
							} else {
								service.put("url", matcher.group() + "do");
								// System.out.println("----" + matcher.group() +
								// "do");
								flag = true;
							}
							break;
						}
					}

					if (!flag) {
						// 匹配双引号 + 双引号
						pattern = Pattern.compile("(?<=\")(.+?)(?=do\")");
						matcher = pattern.matcher(line);
						while (matcher.find()) {
							service.put("url", matcher.group() + "do");
							// System.out.println("----" + matcher.group() +
							// "do");
							flag = true;
						}
					}

					if (!flag) {
						// 匹配 单引号 + 问号
						pattern = Pattern.compile("(?<=\')(.+?)(?=do\\?)");
						matcher = pattern.matcher(line);
						while (matcher.find()) {
							service.put("url", matcher.group() + "do");
							// System.out.println("----" + matcher.group() +
							// "do");
							flag = true;
						}
					}

					if (!flag) {
						// 匹配 单引号 + 单引号
						pattern = Pattern.compile("(?<=\')(.+?)(?=do\')");
						matcher = pattern.matcher(line);
						while (matcher.find()) {
							service.put("url", matcher.group() + "do");
							// System.out.println("----" + matcher.group() +
							// "do");
							flag = true;
						}
					}

					if (!flag) {
						System.out.println("Error: not find!!!");
					} else {
						services.add(service);
					}

				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return services;
	}

	public static List<Map<String, String>> getHtmlServices(String htmlLogFilePath) {
		List<Map<String, String>> services = new ArrayList<Map<String, String>>();
		File file = new File(htmlLogFilePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				if (line.contains(".do")) {
					// System.out.println(line);

					if (line.contains("<!--")) {
						// System.out.println("Ignore : " + line);
						line = reader.readLine();
						continue;
					}
					Map<String, String> service = new HashMap<String, String>();

					Pattern pattern;
					Matcher matcher;
					boolean flag = false;

					if (!flag) {
						pattern = Pattern.compile("(?<=url:\')(.+?)(?=do)");
						matcher = pattern.matcher(line);
						while (matcher.find()) {
							service.put("url", matcher.group() + "do");
							// System.out.println("----" + matcher.group() +
							// "do");
							flag = true;
						}
					}

					if (!flag) {
						pattern = Pattern.compile("(?<=href=\")(.+?)(?=do\")");
						matcher = pattern.matcher(line);
						while (matcher.find()) {
							service.put("url", matcher.group() + "do");
							// System.out.println("----" + matcher.group() +
							// "do");
							flag = true;
						}
					}

					if (!flag) {
						System.out.println("Error: not find!!!");
					} else {
						services.add(service);
					}

				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return services;
	}

	public static List<Map<String, String>> getControllerMappings(String logFilePath) {
		List<Map<String, String>> mappings = new ArrayList<Map<String, String>>();
		String jsonStr = getFileAsText(new File(logFilePath), false);
		Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
		Matcher matcher = pattern.matcher(jsonStr);
		while (matcher.find()) {
			String matchStr = matcher.group();
			String[] params = matchStr.split(",");
			Map<String, String> mapping = new HashMap<String, String>();
			mapping.put("url", params[0].substring(1, params[0].length() - 1));
			for (int i = 1; i < params.length; i++) {
				String param = params[i];
				String[] kvs = param.split("=");
				mapping.put(kvs[0], kvs[1].substring(1, kvs[1].length() - 1));
			}
			mappings.add(mapping);
		}
		return mappings;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i;

		String serviceFilePath = configRootFolderPath + "/service.json";
		List<Map<String, String>> services = getConfigServices(serviceFilePath);
		System.out.println("配置条目数(JSON) -- " + services.size());
		for (i = 0; i < services.size(); i++) {
//			System.out.println(services.get(i));
		}

		String jsLogFilePath = "D:/tmp/jslog.txt";
		List<Map<String, String>> services1 = getJSServices(jsLogFilePath);
		System.out.println("配置条目数(JS) -- " + services1.size());
		for (i = 0; i < services1.size(); i++) {
			// System.out.println(services1.get(i));
		}

		String htmlLogFilePath = "D:/tmp/htmllog.txt";
		List<Map<String, String>> services2 = getHtmlServices(htmlLogFilePath);
		System.out.println("配置条目数(HTML) -- " + services2.size());
		for (i = 0; i < services2.size(); i++) {
			// System.out.println(services2.get(i));
		}

		List<String> usedURLs = new ArrayList<String>();
		String url = "";
		for (i = 0; i < services.size(); i++) {
			url = services.get(i).get("url");
//			System.out.println(url);
			
			if (!usedURLs.contains(url)) {
				usedURLs.add(url);
			}
		}
//		System.out.println(usedURLs.size());
//		System.out.println(usedURLs);
		
		for (i = 0; i < services1.size(); i++) {
			url = services1.get(i).get("url");
			if (!usedURLs.contains(url)) {
				usedURLs.add(url);
			}
		}
		for (i = 0; i < services2.size(); i++) {
			url = services2.get(i).get("url");
			if (!usedURLs.contains(url)) {
				usedURLs.add(url);
			}
		}
		System.out.println("配置条目数(USED) -- " + usedURLs.size());

		String logFilePath = "D:/tmp/startlog.txt";
		List<Map<String, String>> mappings = getControllerMappings(logFilePath);
		System.out.println("配置条目数(Controller)--" + mappings.size());
		
//		System.out.println(mappings);

		String servicesStr = usedURLs.toString();
		
		List<String> mappingURLs = new ArrayList<String>();
		List<String> withoutDotDoURLs = new ArrayList<String>();
		
		List<String> matchedURLs = new ArrayList<String>();
		List<String> notMatchedURLs = new ArrayList<String>();

		for (i = 0; i < mappings.size(); i++) {
			String mappingUrl = mappings.get(i).get("url");
			mappingUrl = mappingUrl.substring(1);
			if(!mappingUrl.contains(".do")){
				withoutDotDoURLs.add(mappingUrl);
				mappingUrl += ".do";
			}
			
			mappingURLs.add(mappingUrl);
			
			if (servicesStr.contains(mappingUrl)) {
				matchedURLs.add(mappingUrl);
			} else {
				notMatchedURLs.add(mappings.get(i).get("url"));
			}
		}
//		System.out.println(mappingURLs.size());
//		System.out.println("With out .do size -- " + withoutDotDoURLs.size());
		
		System.out.println("有效URL数 -- " + matchedURLs.size());
		
		String mappingURLStr = mappingURLs.toString();
				
		List<String> matchedURLs_ok = new ArrayList<String>();
		List<String> matchedURLs_error = new ArrayList<String>();
		
		for (i = 0; i < usedURLs.size(); i++) {
			url = usedURLs.get(i);
			if(url.indexOf(".do") == -1){
				System.out.println("without .do -- " + url);
			}
			
			if (!mappingURLStr.contains(url)) {
				matchedURLs_error.add(url);				
			} else {
				matchedURLs_ok.add(url);
			}
		}
		
		System.out.println("完全匹配数 -- " + matchedURLs_ok.size());
		System.out.println("不匹配数 -- " + matchedURLs_error.size());
		
		for (i = 0; i < matchedURLs_error.size(); i++) {
			System.out.println(matchedURLs_error.get(i));
		}
		
		//后端未调用的Controller
		System.out.println("未使用的Controller数 -- " + notMatchedURLs.size());
		for (i = 0; i < notMatchedURLs.size(); i++) {
			System.out.println(notMatchedURLs.get(i));
		}

		// System.out.println(services);
		// System.out.println(mappings);

		// List<Map<String, String>> resourceList = getResources();
		// System.out.println(resourceList.size());
		//
		// Iterator it = resourceList.iterator();
		// while (it.hasNext()) {
		// Map<String, String> resource = (Map<String, String>) it.next();
		// // System.out.println(resource.get("rlevel") + " -- rid=" +
		// resource.get("rid") + ",rlabel=" + resource.get("rlabel") + ",rdesc="
		// + resource.get("rdesc"));
		// System.out.println(resource.get("rid") + "," + resource.get("rlabel")
		// + "," + resource.get("rdesc"));
		// }

	}
}
