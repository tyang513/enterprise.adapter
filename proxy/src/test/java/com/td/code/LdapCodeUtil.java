package com.td.code;

import com.talkingdata.codegenerate.def.FtlDef;
import com.talkingdata.codegenerate.factory.LdapCodeGenerateFactory;

public class LdapCodeUtil {

	public static void main(String[] args) {
		String tableName = "User";
		String codeName = "用户";
		String entityPackage = "user";
		LdapCodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02, "dataCenter");
	}
}
