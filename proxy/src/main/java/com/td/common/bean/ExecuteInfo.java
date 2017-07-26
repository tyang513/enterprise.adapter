package com.td.common.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 操作返回信息
 * @author apple
 */
public class ExecuteInfo{
	
	private String status;
	private String info;
	private String desc;
	
	public ExecuteInfo error(){
		this.status = "error";
		return this;
	}
	
	public ExecuteInfo success(){
		this.status = "success";
		return this;
	}

	public ExecuteInfo info(String info){
		this.info = info;
		return this;
	}

	public ExecuteInfo desc(String desc){
		this.desc = desc;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String toJsonStr(){			
		String s = "{}";
		try {
			ObjectMapper mapper = new ObjectMapper();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(bos, JsonEncoding.UTF8);
			mapper.writeValue(generator, this);			
			s= bos.toString("UTF8");
		} catch (Exception e) {
			s = "{status:'error',info:'Transform object 2 json string error:"+e.getMessage()+"'}";
		}
		return s;
	}

	public void writeJsonStrToServletOutputstream(HttpServletResponse response){			
		String s = "{}";
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
			mapper.writeValue(generator, this);					
		} catch (Exception e) {
			s = "{status:'error',info:'Transform object 2 json string error:"+e.getMessage()+"'}";
			try {
				response.getOutputStream().write(s.getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}