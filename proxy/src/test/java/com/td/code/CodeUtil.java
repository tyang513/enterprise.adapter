package com.td.code;
import com.talkingdata.codegenerate.def.FtlDef;
import com.talkingdata.codegenerate.factory.CodeGenerateFactory;

public class CodeUtil {

	public CodeUtil() {

	}

	public static void main(String args[]) {
		String tableName = "tdp_task_response_file_process_job_input";
		String codeName = "用户";
		String entityPackage = "admin";
		CodeGenerateFactory.codeGenerate(tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);
	}
}
