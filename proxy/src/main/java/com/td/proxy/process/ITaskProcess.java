package com.td.proxy.process;

import java.util.Map;

public interface ITaskProcess {

	public void execute(Map<String, Object> context) throws Exception;
	
}
