package com.td.admin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.SysSequence;
import com.td.admin.mapper.SysSequenceMapper;

/** 
 * @description: 获取单据序号
 * @author: cmh  2013-8-29
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class,propagation = Propagation.NOT_SUPPORTED)
public class SysSequenceService {
    
    private static Logger logger = Logger.getLogger(SysSequenceService.class);
    
    @Autowired
    private SysSequenceMapper sysSequenceMapper;
    
    /**
     * 得到序列的当前值 
     * @param record (通过seqName获取)
     * @return
     */
    public String selectSeqCurrVal(SysSequence record) {
        return sysSequenceMapper.selectSeqCurrVal(record);
    }
    
    /**
     * 获得下一个序列值
     * @param record (通过seqName获取)
     * @return
     */
    public String selectSeqNextVal(SysSequence record) {
        return sysSequenceMapper.selectSeqNextVal(record);
    }
    
    /**
     * 设置序列的起始值
     * @param record(通过seqName, increment)
     * @return
     */
    public String selectSeqSetVal(SysSequence record) {
        return sysSequenceMapper.selectSeqSetVal(record);
    }
    
    /**
     * 生成流水号
     * @param type
     * @return
     */
    @Transactional(value = "transactionManager",propagation = Propagation.NOT_SUPPORTED)
    public String getSequenceId(String seqName) {
        SysSequence sysSequence = new SysSequence();
        sysSequence.setSeqName(seqName);
        
        String applyNum = sysSequenceMapper.selectSeqNextVal(sysSequence);
        
        logger.debug("生成流水号" + applyNum);
        return String.valueOf(applyNum);
    }
}
