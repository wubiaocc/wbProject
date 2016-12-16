package com.finance.communication.service.server.sys;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.communication.dao.MemberInterestsDao;
import com.finance.communication.entity.MemberInterests;
import com.wizarpos.wx.core.orm.Page;

/**
 */
@Service
public class MemberInterestsManager {
	
	@Autowired
	private MemberInterestsDao memberInterestsDao;
	
	/**
	 * 第三方交易查询
	 * 2016年11月14日
	 * @param searchParams
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<MemberInterests> queryForPage(Map<String, Object> searchParams,Integer pageNo,Integer pageSize){
		return memberInterestsDao.queryForPage(searchParams, pageNo, pageSize);
	}
}
