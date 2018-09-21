package com.orye.business.member.service;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.service.BaseService;
import com.core.teamwork.base.util.page.PageObject;
import com.orye.business.member.entity.MemberInfo;
import com.orye.business.member.mapper.MemberInfoMapper;

/**
 * @author cyl
 * @version 
 */
public interface MemberInfoService extends BaseService<MemberInfo,MemberInfoMapper>  {
	
	public PageObject<MemberInfo> getPage(Map<String,Object> map);
	
	public List<MemberInfo> queryMyMember(Map<String,Object> map);
}
