package com.orye.business.admin.mapper;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.admin.entity.SysUcenterFiles;

/**
 * @author cyl
 * @version 
 */
public interface SysUcenterFilesMapper extends BaseMapper<SysUcenterFiles>{

	SysUcenterFiles selectMD5(String md5);
}