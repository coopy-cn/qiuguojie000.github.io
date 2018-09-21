package com.orye.business.member.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orye.business.course.entity.CourseTableConfig;
import com.orye.business.member.entity.MemberInfo;
import com.orye.business.member.mapper.MemberInfoMapper;
import com.orye.business.member.service.MemberInfoService;
import com.core.teamwork.base.service.impl.BaseServiceImpl;
import com.core.teamwork.base.util.page.PageHelper;
import com.core.teamwork.base.util.page.PageObject;

/**
 * @author cyl
 * @version 
 */
@Service("memberInfoService")
public class MemberInfoServiceImpl extends BaseServiceImpl<MemberInfo, MemberInfoMapper> implements MemberInfoService {
	// 注入当前dao对象
    @Autowired
    private MemberInfoMapper memberInfoMapper;

    public MemberInfoServiceImpl() {
        setMapperClass(MemberInfoMapper.class, MemberInfo.class);
    }
    
    /**
     * 所有会员卡
     */
    public PageObject<MemberInfo> getPage(Map<String,Object> map){
    	int totalData = memberInfoMapper.getCount(map);
        PageHelper pageHelper = new PageHelper(totalData, map);
        List<MemberInfo> list = memberInfoMapper.getPage(pageHelper.getMap());
        PageObject<MemberInfo> pageObject = pageHelper.getPageObject();
        pageObject.setDataList(list);
        return pageObject;
    }
    
    /**
     * 我的会员卡列表
     */
    public List<MemberInfo> queryMyMember(Map<String,Object> map){
    	return memberInfoMapper.queryMyMember(map);
    }
}
