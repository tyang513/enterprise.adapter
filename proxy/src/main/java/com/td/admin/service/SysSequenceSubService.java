package com.td.admin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.SysSequenceSub;
import com.td.admin.mapper.SysSequenceSubMapper;

/** 
 * @description: 获取单据序号
 * @author: cmh  2013-8-29
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
@Service
@Transactional(value = "transactionManager", rollbackFor = RuntimeException.class)
public class SysSequenceSubService {
	private static Logger logger = Logger.getLogger(SysSequenceSubService.class);
	
	@Autowired
	private SysSequenceSubMapper sysSequenceSubMapper;
	
	/**
     * 获得当前的序列号
     * @param record 中只需要：seqSubName,connectSign
     * @return
     */
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
   public Integer selectSeqSubCurrNum(SysSequenceSub record) {
	   return sysSequenceSubMapper.selectSeqSubCurrNum(record);
   }
    
    /**
     * 获得下一个序列号
     * @param record
     * @return 中只需要：seqSubName,connectSign
     */
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    public Integer selectSeqSubNextNum(SysSequenceSub record) {
    	return sysSequenceSubMapper.selectSeqSubNextNum(record);
    }
	
	/**
     * 获得当前的序列号
     * @param record 中只需要：seqSubName,connectSign
     * @return
     */
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
   public String selectSeqSubCurrVal(SysSequenceSub record) {
	   return sysSequenceSubMapper.selectSeqSubCurrVal(record);
   }
    
    /**
     * 获得下一个序列号
     * @param record
     * @return 中只需要：seqSubName,connectSign
     */
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    public String selectSeqSubNextVal(SysSequenceSub record) {
    	return sysSequenceSubMapper.selectSeqSubNextVal(record);
    }
    
    /**
     * 设置序列号
     * @param record 中只需要：seqSubName, curValue, remark connectSign
     * @return
     */
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    public String selectSeqSubSetVal(SysSequenceSub record) {
    	return sysSequenceSubMapper.selectSeqSubSetVal(record);
    }
}
