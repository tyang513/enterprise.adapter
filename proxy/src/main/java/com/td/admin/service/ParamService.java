package com.td.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysParam;
import com.td.admin.mapper.SysParamMapper;

@Service
public class ParamService {
	
	@Autowired
	private SysParamMapper finSystemParamMapper;

    /**
     * 获取所有的参数（密码设置为空）
     * @return
     */
	public Map<String,Object> getParams(SysParam param){
		Long total = finSystemParamMapper.findTotal(param);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", total);
		returnMap.put("rows", finSystemParamMapper.findParams(param));
		return returnMap;
//		String gridJson = UIUtil.getEasyUiGridJson(total,UIUtil.getJSONFromList(finSystemParamMapper.findParams(param)));
//		return gridJson;
	}
	
	/**
	 * 获取所有的参数（密码不做处理）
	 * @return
	 */
	public List<SysParam> getAllParamsWithPW(){
		return finSystemParamMapper.selectAllParamsWithPW();
	}

	/**
	 * 修改系统参数
	 * @param record
	 * @return
	 */
	public int  updateParam(SysParam record){
		return finSystemParamMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 根据ID获取参数信息
	 * @param id
	 * @return
	 */
	public SysParam getParamById(Long id){
		return finSystemParamMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据param key获取参数信息
	 * @param key
	 * @return
	 */
	public SysParam getParamByKey(String key){
		return finSystemParamMapper.selectByParamKey(key);
	}
	/**
	 * 修改系统参数
	 * @param record
	 * @return
	 */
	public int  updateParamByMap(Map<String,Object> params){
		return finSystemParamMapper.updateParamByMap(params);
	}
	
	public List<SysParam> selectExcludePW(SysParam param) {
		return finSystemParamMapper.selectExcludePW(param);
	}
	
}
