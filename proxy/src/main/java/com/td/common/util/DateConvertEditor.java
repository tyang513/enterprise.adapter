package com.td.common.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * @description: 自定义时间字段处理器
 * @author: cmh 2013-5-27
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权拥有
 */
public class DateConvertEditor extends PropertyEditorSupport {
    private static final Logger logger = Logger.getLogger(DateConvertEditor.class);

    private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setAsText(String text) throws IllegalArgumentException {
	    logger.debug("时间字段处理器=====" + text);
		if (org.springframework.util.StringUtils.hasText(text)) {
			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					setValue(this.dateFormat.parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					setValue(this.datetimeFormat.parse(text));
				}else{
					throw new IllegalArgumentException("Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}

//    public void setAsText(String text) throws IllegalArgumentException {
//        logger.debug("时间字段处理器=====" + text);
//
//        if (!StringUtils.isNotBlank(text)) {
//            setValue(null);
//        }
//        else {
//            try {
//                setValue(this.format.parse(text));
//            }
//            catch (ParseException e) {
//                throw new IllegalArgumentException("不能被转换的日期字符串，请检查!", e);
//            }
//        }
//    }
//
//    public String getAsText() {
//        if (getValue() == null)
//            return "";
//        return this.format.format(getValue());
//    }
//
//    public static void main(String[] args) {
//	    DateConvertEditor dateConvertEditor = new DateConvertEditor();
//	    try {
//            System.out.println(dateConvertEditor.format.(1369906170000);
//            System.out.println(dateConvertEditor.format.parse("2012-01-01 11:11:11"));
//            
//        }
//        catch (ParseException e) {
//            // TODO 自动生成 catch 块
//            e.printStackTrace();
//        }
	    
	    
//	}
}
