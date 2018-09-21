package com.orye.business.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.teamwork.base.service.impl.BaseServiceImpl;
import com.orye.business.admin.entity.SysUcenterFiles;
import com.orye.business.admin.mapper.SysUcenterFilesMapper;
import com.orye.business.admin.service.SysUcenterFilesService;

/**
 * @author cyl
 * @version 
 */
@Service("sysUcenterFilesService")
public class SysUcenterFilesServiceImpl extends BaseServiceImpl<SysUcenterFiles, SysUcenterFilesMapper> implements SysUcenterFilesService {
	// 注入当前dao对象
    @Autowired
    private SysUcenterFilesMapper sysUcenterFilesMapper;

    public SysUcenterFilesServiceImpl() {
        setMapperClass(SysUcenterFilesMapper.class, SysUcenterFiles.class);
    }

	/* (non-Javadoc)
	 */
	public SysUcenterFiles selectMD5(String fileMD5) {
		SysUcenterFiles sysUcenterFiles = sysUcenterFilesMapper.selectMD5(fileMD5);
		return sysUcenterFiles;
	}
}
