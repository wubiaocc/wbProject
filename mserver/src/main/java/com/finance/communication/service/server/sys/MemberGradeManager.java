package com.finance.communication.service.server.sys;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.communication.dao.MemberGradeDao;
import com.finance.communication.entity.MemberGrade;
import com.wizarpos.wx.core.orm.Page;

/**
 */
@Service
public class MemberGradeManager {
	
	@Autowired
	private MemberGradeDao memberGradeDao;
	
	/**
	 * 第三方交易查询
	 * 2016年11月14日
	 * @param searchParams
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<MemberGrade> queryForPage(Map<String, Object> searchParams,Integer pageNo,Integer pageSize){
		return memberGradeDao.queryForPage(searchParams, pageNo, pageSize);
	}
}
