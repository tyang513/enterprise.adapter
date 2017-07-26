package com.td.admin.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysJobConfig;
import com.td.admin.entity.SysJobErrorInfo;
import com.td.admin.entity.SysJobTempJobInput;
import com.td.admin.mapper.SysJobConfigMapper;
import com.td.admin.mapper.SysJobErrorInfoMapper;
import com.td.admin.mapper.SysJobTempJobInputMapper;
import com.td.common.bean.Body;


@Service
public class JobConfigManageDao {
	
	private static final Logger logger = LoggerFactory.getLogger(JobConfigManageDao.class);
	
	@Autowired
	private SysJobConfigMapper sysJobConfigMapper; 
	
	@Autowired
	private SysJobErrorInfoMapper sysJobErrorInfoMapper; 
	
	@Autowired
	private SysJobTempJobInputMapper sysJobTempjobinputMapper; 
	
	/**
	 * @description 查询定时任务表
	 * @return
	 */
	public List<SysJobConfig> queryJobConfig(SysJobConfig record){
		logger.info("查询定时任务");
		//List<FinJobConfig> result=finJobConfigMapper。;
		List<SysJobConfig> result=sysJobConfigMapper.queryJobConfig(record);
		return result;
	}
	
	/**
	 * @description 查询定时任务记录数
	 * @param record
	 * @return
	 */
	public Long queryJobConfigTotalCount(SysJobConfig record){
		logger.info("查询定时任务记录数");	
		Long result = sysJobConfigMapper.getPagingCount(record);
		return result;
	}
	
	/**
	 * @description 查询定时任务表错误信息表
	 * @return
	 */
	public List<SysJobErrorInfo> queryJobErrorInfo(SysJobErrorInfo record){
		logger.info("查询定时任务");
		//List<FinJobConfig> result=finJobConfigMapper。;
		List<SysJobErrorInfo> result=sysJobErrorInfoMapper.queryJobError(record);
		return result;
	}
	
	/**
	 * @description 查询定时任务错误信息记录数
	 * @param record
	 * @return
	 */
	public Long queryJobErrorInfoTotalCount(SysJobErrorInfo record){
		logger.info("错误任务记录数");	
		Long result = sysJobErrorInfoMapper.getPagingCount(record);
		return result;
	}
	
	/**
	 * @description 查询临时任务信息表
	 * @return
	 */
	public List<SysJobTempJobInput> queryJobTempInfo(SysJobTempJobInput record){
		logger.info("查询临时任务");
		//List<FinJobConfig> result=finJobConfigMapper。;
		List<SysJobTempJobInput> result=sysJobTempjobinputMapper.queryJobTemp(record);
		return result;
	}
	
	/**
	 * @description 查询临时任务记录数
	 * @param record
	 * @return
	 */
	public Long queryJobTempInfoTotalCount(SysJobTempJobInput record){
		logger.info("查询临时任务记录数");	
		Long result = sysJobTempjobinputMapper.getPagingCount(record);
		return result;
	}
	
	public Body updateJobConfig(SysJobConfig record){
		Body body = new Body();
		
		int result=sysJobConfigMapper.updateByPrimaryKeySelective(record);
		
		if(result>0){
			body.success();
			body.setMessage("修改定时任务配置成功!");
		
		}else{
			body.error();
			body.setMessage("修改定时任务配置失败!");
		}
		return body;
	}
	
	
	public Body updateJobConfigTemp(Long id){
		Body body = new Body();
		logger.debug("JobConfigManageDao------"+id);
		int result=sysJobTempjobinputMapper.updateJobConfigTemp(id);
		logger.debug("result--------:"+result);
		if(result>0){
			body.success();
			body.setMessage("update jobconfigtemp success");
		
		}else{
			body.error();
			body.setMessage("update jobconfig error");
		}
		return body;
	}
	
	
}
