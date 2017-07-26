package ${bussPackage}.entity.${entityPackage};

import com.pingan.jinke.dc.base.entity.BaseEntity;

#foreach($importClasses in $!{entityImportClasses})
import ${importClasses};
#end

/**
 * 
 * <br>
 * <b>功能：</b>${className}Entity<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class ${className} extends BaseEntity {
	
#foreach($po in $!{columnDatas})
	private ${po.shortDataType} ${po.dataName};
#end
#foreach($po in $!{columnDatas})

	public ${po.shortDataType} get${po.upperDataName}() {
		return this.${po.dataName};
	}

	public void set${po.upperDataName}(${po.shortDataType} ${po.dataName}) {
		this.${po.dataName} = ${po.dataName};
	}
#end
}

