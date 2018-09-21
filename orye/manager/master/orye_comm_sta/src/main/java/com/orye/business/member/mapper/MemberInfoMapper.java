package com.orye.business.member.mapper;

import java.util.List;
import java.util.Map;

import com.core.teamwork.base.mapper.BaseMapper;
import com.orye.business.member.entity.MemberInfo;

/**
 * @author cyl
 * @version 
 */
public interface MemberInfoMapper extends BaseMapper<MemberInfo>{
	
	public List<MemberInfo> getPage(Map<String,Object> map);
	
	public List<MemberInfo> queryMyMember(Map<String,Object> map);
}