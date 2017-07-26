package com.td.common.exception;

public class CommonException extends RuntimeException {
	
	private String code;
	private Throwable throwable;
	private Object[] msgArgs;

	private String key;//关键值
	
	public Object[] getMsgArgs() {
		return msgArgs;
	}

	public void setMsgArgs(Object[] msgArgs) {
		this.msgArgs = msgArgs;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommonException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommonException(String errorMsg) {
		super(errorMsg);
	}
	
	public CommonException(String code, Throwable t, Object[] msgArgs) {
		super(code, t);
		this.code = code;
		this.throwable = t;
		this.msgArgs = msgArgs;

	}

	public CommonException(String code, Throwable t, Object[] msgArgs, String key) {
		super(code, t);
		this.code = code;
		this.throwable = t;
		this.msgArgs = msgArgs;
		this.key = key;
	}
	
	public CommonException(String code, Object[] msgArgs) {
		super(code);
		this.code = code;
		
		this.msgArgs = msgArgs;

	}

	public CommonException(Throwable t) {
		super(t);

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Throwable getEmbeddedThrowable() {
		return throwable;
	}

	public void setEmbeddedThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
}
