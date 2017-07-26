package com.td.common.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.td.admin.entity.SysSequence;
import com.td.admin.entity.SysSequenceSub;
import com.td.admin.mapper.SysSequenceMapper;
import com.td.admin.service.SysSequenceSubService;


/**
 * @description: 生成序列号
 * @author: cmh 2013-8-30
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权拥有
 */
public class SequenceUtil {
    private static final Logger logger = Logger.getLogger(SequenceUtil.class);

    /**
     * 生成订单号
     * @param type
     * @return
     */
    public static String getSequenceCode(String seqName) {
        SysSequenceMapper sysSequenceMapper = (SysSequenceMapper) ApplicationContextManager.getBean("sysSequenceMapper");
        SysSequence sysSequence = new SysSequence();
        sysSequence.setSeqName(seqName);

        String applyNum = sysSequenceMapper.selectSeqNextVal(sysSequence);

        logger.debug("生成订单号" + applyNum);
        return String.valueOf(applyNum);
    }

    /**
     * 生成订单号 通过JDBC
     * @param seqName
     * @return
     */
    public static String getSequenceIdByJDBC(String seqName) {
        String applyNum = "";
        Connection con = null;
        CallableStatement cst = null;

        try {
            DataSource datasource = (DataSource) ApplicationContextManager.getBean("dataSource");

            con = datasource.getConnection();
            String sql = "{?= call fun_seq_nextval('" + seqName + "')}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, Types.VARCHAR);

            cst.execute();

            applyNum = cst.getString(1);
            logger.debug("生成订单号" + applyNum);

            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            try {
                if (con != null) {
                    con.close();
                }
                if (cst != null) {
                    cst.close();
                }
            }
            catch (SQLException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }

        }
        return applyNum;
    }
    
    /**
     * 获取相对主键的完整序列号，支持分隔符
     * 
     * @param seqSubName
     * @param connectSign
     * @return
     */
    public static String getSubSequenceCode(String seqSubName, String connectSign) {
    	SysSequenceSubService sysSequenceSubService = (SysSequenceSubService) ApplicationContextManager.getBean("sysSequenceSubService");
    	
    	SysSequenceSub sysSequenceSub = new SysSequenceSub();
    	sysSequenceSub.setSeqSubName(seqSubName);
    	sysSequenceSub.setConnectSign(connectSign);

        String sequenceNo = sysSequenceSubService.selectSeqSubNextVal(sysSequenceSub);

        logger.debug("生成序列号" + sequenceNo);
        return sequenceNo;
    }
    
    public static Integer getSubSequenceNum(String seqSubName) {
    	SysSequenceSubService sysSequenceSubService = (SysSequenceSubService) ApplicationContextManager.getBean("sysSequenceSubService");
    	
    	SysSequenceSub sysSequenceSub = new SysSequenceSub();
    	sysSequenceSub.setSeqSubName(seqSubName);

    	Integer sequenceNo = sysSequenceSubService.selectSeqSubNextNum(sysSequenceSub);

        logger.debug("生成数字" + sequenceNo);
        return sequenceNo;
    }
}
