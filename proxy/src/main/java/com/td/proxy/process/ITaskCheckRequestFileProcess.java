package com.td.proxy.process;

import java.util.Map;


/**
 * 请求文件检查处理
 *  通过规则配置到指定目录扫描是否上传新的文件
 * @author yangtao
 */
public interface ITaskCheckRequestFileProcess {

	public void execute(Map<String, Object> context) throws Exception;

}
