package ${bussPackage}.service.${entityPackage};

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pingan.jinke.dc.base.service.BaseService;
import ${bussPackage}.dao.${entityPackage}.${className}Dao;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Service<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("$!{lowerName}Service")
public class ${className}Service<T> extends BaseService<T> {
	private final static Logger logger = Logger.getLogger(${className}Service.class);
	
	@Autowired
	private ${className}Dao<T> dao;

	public ${className}Dao<T> getDao() {
		return dao;
	}
}
