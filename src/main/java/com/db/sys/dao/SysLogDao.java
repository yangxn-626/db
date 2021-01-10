package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysLog;

public interface SysLogDao {
	int deleteObjects(@Param("ids")Integer... id);
	//查询日志列表
	List<SysLog> findPageObjects(@Param("username")String username,
			@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	//数量计算
	int getRowCount(@Param("username")String username);
	
	int insertObject(SysLog entity);
}
