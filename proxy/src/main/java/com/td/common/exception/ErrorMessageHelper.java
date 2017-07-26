package com.td.common.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ErrorMessageHelper {
	
	//private static Logger logger = Logger.getLogger(ErrorMessageHelper.class);
	
	private static ResourceBundle rb = ResourceBundle.getBundle(
			"com.pingan.jinke.finance.common.exception.errorMessage",
			new Locale("zh","CN"));
	
	
	public static String getErrorMessage(String code){
		try {
			String ret = rb.getString(code);
			return ret;
		} catch (Exception e) {
			//loger.error("get message error",e);
			return null;
		}
	}
	
	public static String getErrorMessage(String code,Object[] msgArgs){
		try {
			String ret = rb.getString(code);
			if(ret == null)
				return null;
			MessageFormat formatter = new MessageFormat("");
		      formatter.setLocale(Locale.getDefault());
		      formatter.applyPattern(ret);
		      String output = formatter.format(msgArgs);
			return output;
		} catch (Exception e) {
			//loger.error("get message error",e);
			return null;
		}
	}
	
	public static String getErrorMessage(CommonException e){
		if(e == null)
			return null;
		if(e.getCode() != null){
			if(e.getMsgArgs() != null)
				return getErrorMessage(e.getCode(),e.getMsgArgs());
			else
				return getErrorMessage(e.getCode());
		}
		return null;
	}


}
