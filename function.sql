
sys_sequence | CREATE TABLE `sys_sequence` (
  `seq_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'SEQUENCE名称',
  `current_value` int(11) NOT NULL COMMENT '当前值',
  `current_day` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '当前日期，格式20130917',
  `increment` int(11) NOT NULL DEFAULT '1' COMMENT '增量',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
  `system_code` varchar(50) NOT NULL COMMENT '系统编码',
  `length` int(11) DEFAULT '3',
  PRIMARY KEY (`seq_name`,`system_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统流水号';

sys_sequence_sub | CREATE TABLE `sys_sequence_sub` (
  `seq_sub_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'SEQUENCE名称',
  `current_value` int(11) NOT NULL COMMENT '当前值',
  `increment` int(11) NOT NULL DEFAULT '1' COMMENT '增量',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '备注',
  `system_code` varchar(50) DEFAULT NULL COMMENT '系统编码',
  `length` int(11) DEFAULT '3' COMMENT '序号长度',
  UNIQUE KEY `sys_sequence_sub_seq_sub_name_IDX` (`seq_sub_name`,`system_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子sequence'

delimiter $$

CREATE FUNCTION `fun_seq_currval`(seqName varchar(50), systemCode varchar(50)) RETURNS varchar(50) CHARSET utf8mb4 COMMENT '当前序列号'
BEGIN
    DECLARE number VARCHAR(50);
    DECLARE curDay VARCHAR(10);
    DECLARE curValue VARCHAR(10);
    DECLARE currentDay VARCHAR(10);

    SET number = '';
    set curDay = '';
    select date_format(curdate(), '%Y%m%d') INTO curDay;
    SELECT LPAD(concat(current_value,''), length, '0'),
           CASE WHEN current_day  IS NULL THEN ''
                ELSE current_day
               END
    INTO curValue, currentDay
    FROM sys_sequence
    WHERE seq_name = seqName and system_code = systemCode;

    SELECT concat(seqName, currentDay, curValue) into number;

    RETURN number;
END$$

CREATE  FUNCTION `fun_seq_nextval`(seqName varchar(50), systemCode varchar(50)) RETURNS varchar(50) CHARSET utf8mb4 COMMENT '获得下一个序列号'
BEGIN
    DECLARE curDay VARCHAR(10);
    DECLARE currentDay VARCHAR(10);
    DECLARE dayDiff VARCHAR(10);

    select date_format(curdate(), '%Y%m%d') INTO curDay;
    SELECT current_day INTO currentDay FROM sys_sequence WHERE seq_name = seqName and system_code = systemCode;
    SELECT datediff(curDay, currentDay) INTO dayDiff;

    IF dayDiff > 0 THEN
        UPDATE sys_sequence
        SET current_value = 1 , current_day = curDay
        WHERE seq_name = seqName and system_code = systemCode;

    ELSE
        UPDATE sys_sequence
        SET current_value = current_value + increment
        WHERE seq_name = seqName and system_code = systemCode;

    END IF;

    RETURN fun_seq_currval(seqName, systemCode);
END$$

CREATE FUNCTION `fun_seq_setval`(seqName varchar(50), value int, currentDay varchar(10), systemCode varchar(50)) RETURNS varchar(50) CHARSET utf8mb4 COMMENT '设置序列号，从此序列号开始'
BEGIN
    UPDATE sys_sequence
    SET current_value = value, current_day = currentDay
    WHERE seq_name = seqName and system_code = systemCode;
    RETURN fun_seq_currval(seqName,systemCode);
END$$

CREATE FUNCTION `fun_seq_sub_currnum`(seqSubName varchar(50), systemCode varchar(50)) RETURNS int(11) COMMENT '当前序列号'
BEGIN
    DECLARE curValue INT(11);

    SELECT current_value INTO curValue
    FROM sys_sequence_sub
    WHERE seq_sub_name = seqSubName and system_code = systemCode;

    RETURN curValue;
END$$

CREATE FUNCTION `fun_seq_sub_currval`(seqSubName varchar(50), connectSign varchar(10), systemCode varchar(50)) RETURNS varchar(50) CHARSET utf8mb4 COMMENT '当前序列号'
BEGIN
    DECLARE number VARCHAR(50);
    DECLARE connect_sign VARCHAR(10);
    DECLARE curValue VARCHAR(10);

    SET number = '';
    SET connect_sign = connectSign;
    IF ISNULL(connectSign) || LENGTH(TRIM(connectSign))<1 THEN
        SET connect_sign = '';
    END IF;

    SELECT LPAD(concat(current_value,''), length, '0')
    INTO curValue
    FROM sys_sequence_sub
    WHERE seq_sub_name = seqSubName and system_code = systemCode;

    SELECT CONCAT(seqSubName, connect_sign, curValue) INTO number;

    RETURN number;
END$$

CREATE FUNCTION `fun_seq_sub_nextnum`(seqSubName varchar(50), systemCode varchar(50)) RETURNS int(11)
BEGIN
    DECLARE countRecord int(10);
    DECLARE number INT(11);

    SELECT COUNT(*) INTO countRecord FROM sys_sequence_sub WHERE seq_sub_name = seqSubName and system_code = systemCode;

    IF(countRecord = 0) THEN
        INSERT INTO `sys_sequence_sub` (`seq_sub_name`, `current_value`,`system_code` )
        VALUES (seqSubName, 1, systemCode );
    ELSE
        UPDATE sys_sequence_sub
        SET current_value = current_value + increment
        WHERE seq_sub_name = seqSubName and system_code = systemCode;
    END IF;

    SET number = fun_seq_sub_currnum(seqSubName,systemCode);
    RETURN number;
END$$

CREATE FUNCTION `fun_seq_sub_nextval`(seqSubName varchar(50), connectSign varchar(10), systemCode varchar(50)) RETURNS varchar(50) CHARSET utf8mb4
BEGIN
    DECLARE countRecord int(10);
    DECLARE number VARCHAR(50);

    SELECT COUNT(*) INTO countRecord FROM sys_sequence_sub WHERE seq_sub_name = seqSubName and system_code = systemCode;

    IF(countRecord = 0) THEN
        INSERT INTO `sys_sequence_sub` (`seq_sub_name`, `current_value`,`system_code`)
        VALUES (seqSubName, 1, systemCode);
    ELSE
        UPDATE sys_sequence_sub
        SET current_value = current_value + increment
        WHERE seq_sub_name = seqSubName and system_code = systemCode;
    END IF;


    SET number = fun_seq_sub_currval(seqSubName, connectSign ,systemCode);
    RETURN number;
END$$

CREATE FUNCTION `fun_seq_sub_setval`(seqSubName varchar(50), curValue int, remarkStr varchar(100), connectSign varchar(10), systemCode varchar(50), lengthInt int) RETURNS varchar(50) CHARSET utf8mb4
BEGIN
    DECLARE number VARCHAR(50);
    DECLARE countRecord int(10);
    SELECT COUNT(*) INTO countRecord FROM sys_sequence_sub WHERE seq_sub_name = seqSubName and system_code = systemCode;
    IF(countRecord = 0) THEN
        INSERT INTO `sys_sequence_sub` (`seq_sub_name`, `current_value`,`system_code`,`remark`,`length`)
        VALUES (seqSubName, 1, systemCode, remarkStr,lengthInt);
    ELSE
        UPDATE sys_sequence_sub
        SET current_value = curValue
        WHERE seq_sub_name = seqSubName and system_code = systemCode;
    END IF;
    SET number = fun_seq_sub_currval(seqSubName, connectSign,systemCode);
    RETURN number;
END$$

delimiter ;
