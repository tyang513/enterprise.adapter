<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${bussPackage}.dao.${entityPackage}.${className}Dao" > 
  <!-- Result Map-->
  <resultMap id="BaseResultMap" type="${bussPackage}.entity.${entityPackage}.$!{className}" >
#foreach($item in $!{columnDatas})
	<result column="$!item.columnName" property="$item.dataName"/>
#end
  </resultMap>
       
  <!-- $!{tableName} table all fields -->
  <sql id="Base_Column_List" >
	 $!{SQL.columnFields}
  </sql>
   
  <!-- 查询条件 -->
  <sql id="Base_Where_Clause">
    where 1=1
    <trim suffixOverrides="," >
#foreach($item in $!{columnDatas})
  	#set($testStr = $!item.dataName + " != null and " + $!item.dataName + " != ''")
	#if($!item.dataType == 'String')
		#set($testStr = $!testStr + " and " + $!item.columnName + " != ''")
 	#end
      <if test="$!testStr" >
        and $!item.columnName =  #{$!item.dataName}
	  </if>
#end
    </trim>
  </sql>
   
  <!-- 插入记录 -->
  <insert id="insert" parameterType="Object" >
#if($keyType =='02')
    <selectKey resultType="java.lang.Integer" order="AFTER" keyProperty="id">
	  SELECT LAST_INSERT_ID()
    </selectKey>
#end
    $!{SQL.insert}
  </insert>

  <!-- 根据id，修改记录-->  
  <update id="updateByPrimaryKey" parameterType="Object" >
    $!{SQL.update}
  </update>
 
  <!-- 修改记录，只修改只不为空的字段 -->
  <update id="updateByPrimaryKeySelective" parameterType="Object" >
    $!{SQL.updateSelective}
  </update>
 
  <!-- 根据id查询 ${codeName} -->
  <select id="selectByPrimaryKey"  resultMap="BaseResultMap" parameterType="Object">
    $!{SQL.selectById}
  </select>
  
  <!-- 删除记录 -->
  <delete id="deleteByPrimaryKey" parameterType="Object">
    $!{SQL.delete}
  </delete>

  <!-- ${codeName} 列表总数-->
  <select id="queryByCount" resultType="java.lang.Integer"  parameterType="Object">
	select count(1) from ${tableName} 
	<include refid="Base_Where_Clause"/>
  </select>
  	
  <!-- 查询${codeName}列表 -->
  <select id="queryByList" resultMap="BaseResultMap"  parameterType="Object">
	select 
	<include refid="Base_Column_List"/>
	from ${tableName} 
	<include refid="Base_Where_Clause"/>
	<if test="pager.orderCondition != null and pager.orderCondition != ''" >
       ${pager.orderCondition}
    </if>
    <if test="pager.mysqlQueryCondition != null and pager.mysqlQueryCondition != ''" >
       ${pager.mysqlQueryCondition}
    </if>
  </select>
</mapper>   
