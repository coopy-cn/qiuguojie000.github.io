package com.orye.business.admin.service;

import com.core.teamwork.base.service.BaseService;
import com.orye.business.admin.entity.SysUcenterFiles;
import com.orye.business.admin.mapper.SysUcenterFilesMapper;

/**
 * @author cyl
 * @version 
 */
public interface SysUcenterFilesService extends BaseService<SysUcenterFiles,SysUcenterFilesMapper>  {

	
	SysUcenterFiles selectMD5(String fileMD5);
}
