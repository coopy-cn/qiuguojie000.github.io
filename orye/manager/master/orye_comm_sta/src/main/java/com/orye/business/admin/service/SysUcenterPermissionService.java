package com.orye.business.admin.service;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.service.BaseService;
import com.orye.business.admin.entity.SysUcenterPermission;
import com.orye.business.admin.mapper.SysUcenterPermissionMapper;

/**
 * @author sine
 * @version
 */
public interface SysUcenterPermissionService extends BaseService<SysUcenterPermission, SysUcenterPermissionMapper> {

    /**
     * 
     * @author buyuer
     * @Title: addPermissToRole
     * @Description: 增加权限给角色
     */
    void addPermissToRole(Map<String, Object> map, Long userId);
    
    /**
     * @author buyuer
     * @Title: selectFunByRole
     * @Description: 根据角色查询权限列表
     * @param id 角色id
     */
    List<Long> selectFunByRole(Long id);
}
