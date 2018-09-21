package com.orye.business.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.member.entity.MemberUser;
import com.orye.business.member.mapper.MemberUserMapper;
import com.orye.business.member.service.MemberUserService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("memberUserService")
public class MemberUserServiceImpl extends BaseServiceImpl<MemberUser, MemberUserMapper> implements MemberUserService {
	// 注入当前dao对象
    @Autowired
    private MemberUserMapper memberUserMapper;

    public MemberUserServiceImpl() {
        setMapperClass(MemberUserMapper.class, MemberUser.class);
    }
    
 
}
