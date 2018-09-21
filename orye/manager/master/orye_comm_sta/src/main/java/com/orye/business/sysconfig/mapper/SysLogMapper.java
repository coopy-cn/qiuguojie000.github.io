package com.orye.business.sysconfig.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.sysconfig.entity.SysLog;

/**
 * @author cyl
 * @version 
 */
public interface SysLogMapper extends BaseMapper<SysLog>{

	int getSysLogPageListCount(Map<String, Object> map);

	List<SysLog> getSysLogPageList(HashMap<String, Object> map);

}