package com.td.common.bean;


public class Body {

	private final static String SUCCESS_CODE = "200";
	private final static String ERROR_CODE = "300";
	private final static String CLOSE_CURRENT = "closeCurrent";
	private String statusCode;
	private String message;
	
	public String getStatusCode() {
		return statusCode;
	}

	public Body setStatusCode(String statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Body setMessage(String message) {
		this.message = message;
		return this;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(statusCode == null ? "" : "\"statusCode\" : \"" + statusCode + "\",");
		sb.append(message == null ? "" : "\"message\" : \"" + message + "\",");
		sb = sb.toString().equals("{") ? sb.append("}") : sb.replace(sb.length() - 1, sb.length(),
				"}");
		return sb.toString();
	}

	/**
	 * 设置statusCode值200
	 * 
	 * @return
	 */
	public Body success() {
		return setStatusCode(SUCCESS_CODE);
	}

	/**
	 * 设置statusCode值300
	 * 
	 * @return
	 */
	public Body error() {
		return setStatusCode(ERROR_CODE);
	}

}
