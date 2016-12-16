package com.finance.communication.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.communication.entity.MerchantCard;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

@Component
public class MerchantCardDao extends HibernateDao<MerchantCard, String> {


	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MerchantCard> findList(Integer pageNo,Integer pageSize,String gid,Map<String,Object> params) {
		String sql = "select id,card_no,username,mobile_no,balance,freeze,canceled,active_time,points,card_type,expriy_time ";
		sql += " from mrt_merchant_card where 1 = '1' ";
		if(params.containsKey("cardNo")) {
			sql += " and card_no like '%"+params.get("cardNo")+"%'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		if(params.containsKey("canceled")) {
			sql += " and canceled = '"+params.get("canceled")+"'";
		}
		if(params.containsKey("freeze")) {
			sql += " and freeze = '"+params.get("freeze")+"'";
		}
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
		
		sql += " order by active_time desc";
		System.out.println(sql);
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		List list = query.list();
		Object[] arr = null;
		List<MerchantCard> merchantCards = new ArrayList<MerchantCard>();
		for(Object obj : list) {
			MerchantCard m = new MerchantCard();
			arr = (Object[])obj;
			m.setId(String.valueOf(arr[0]));
			String cardNo = "";
			if(arr[1] != null) {
				cardNo = String.valueOf(arr[1]);
			}
			m.setCardNo(cardNo);
			String username = "";
			if(arr[2] != null) {
				username = String.valueOf(arr[2]);
			}
			m.setUsername(username);
			String mobileNo = "";
			if(arr[3] != null) {
				mobileNo = String.valueOf(arr[3]);
			}
			m.setMobileNo(mobileNo);
			Integer balance = 0;
			if(arr[4] != null) {
				balance = Integer.valueOf(String.valueOf(arr[4]));
			}
			m.setBalance(balance);
			m.setFreeze(String.valueOf(arr[5]));
			m.setCanceled(String.valueOf(arr[6]));
			if(arr[7] != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[7]);
				try {
					Date date = sdf.parse(dateStr);
					m.setActiveTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			String points = "0";
			if(arr[8] != null) {
				points = String.valueOf(arr[8]);
			}
			m.setPoints(points);
			if(arr[9] != null) {
				m.setLabelIds(String.valueOf(arr[9]));
			}
			if(arr[10]!=null){
				m.setCardType(String.valueOf(arr[10]));
			}
			if(arr[11]!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[11]);
				try {
					Date date = sdf.parse(dateStr);
					m.setExpriyTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			merchantCards.add(m);
			
		}
		
		return merchantCards;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MerchantCard> findListNew(Integer pageNo,Integer pageSize,String mid,Map<String,Object> params) {
		String sql = "select id,card_no,username,mobile_no,balance,freeze,canceled,active_time,points,card_type,expriy_time,labels";
		sql += " from mrt_merchant_card where gid = '"+mid+"' ";
		if(params.containsKey("cardNo")) {
			sql += " and card_no like '%"+params.get("cardNo")+"%'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		if(params.containsKey("canceled")) {
			sql += " and canceled = '"+params.get("canceled")+"'";
		}
		if(params.containsKey("freeze")) {
			sql += " and freeze = '"+params.get("freeze")+"'";
		}
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
		if(params.containsKey("labelIds")) {
			List<String> labelIds = (List<String>) params.get("labelIds");
			if(labelIds.size() == 0){
				sql += " and (labels like '%-1%'";
			}
			
			for(int i=0;i<labelIds.size();i++) {
				if(i == 0) {
					sql += " and (labels like '%"+labelIds.get(i)+"%'";
				}else {
					sql += " or labels like '%"+labelIds.get(i)+"%'";
				}
			}
			sql += ") ";
		}
		
		sql += " order by active_time desc";
		System.out.println(sql);
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		List list = query.list();
		Object[] arr = null;
		List<MerchantCard> merchantCards = new ArrayList<MerchantCard>();
		for(Object obj : list) {
			MerchantCard m = new MerchantCard();
			arr = (Object[])obj;
			m.setId(String.valueOf(arr[0]));
			String cardNo = "";
			if(arr[1] != null) {
				cardNo = String.valueOf(arr[1]);
			}
			m.setCardNo(cardNo);
			String username = "";
			if(arr[2] != null) {
				username = String.valueOf(arr[2]);
			}
			m.setUsername(username);
			String mobileNo = "";
			if(arr[3] != null) {
				mobileNo = String.valueOf(arr[3]);
			}
			m.setMobileNo(mobileNo);
			Integer balance = 0;
			if(arr[4] != null) {
				balance = Integer.valueOf(String.valueOf(arr[4]));
			}
			m.setBalance(balance);
			m.setFreeze(String.valueOf(arr[5]));
			m.setCanceled(String.valueOf(arr[6]));
			if(arr[7] != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[7]);
				try {
					Date date = sdf.parse(dateStr);
					m.setActiveTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			String points = "0";
			if(arr[8] != null) {
				points = String.valueOf(arr[8]);
			}
			m.setPoints(points);
			/*if(arr[9] != null) {
				m.setLabelIds(String.valueOf(arr[9]));
			}*/
			if(arr[9]!=null){
				m.setCardType(String.valueOf(arr[9]));
			}
			if(arr[10]!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[10]);
				try {
					Date date = sdf.parse(dateStr);
					m.setExpriyTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if(arr[11]!=null){
				m.setLabels(String.valueOf(arr[11]));
			}
			merchantCards.add(m);
			
		}
		
		return merchantCards;
	}
	/**
	 * 根据id查询信息
	 * 
	 * @param id
	 * @return
	 */
	public MerchantCard queryById(String id) {
		String hql = " from MerchantCard where id =?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, id);
		return (MerchantCard) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<MerchantCard> queryByMid(String mid) {
		String sql = "select * from mrt_merchant_card where mid = '" + mid
				+ "' order by create_time";
		Query query = this.getSession().createSQLQuery(sql.toString());
		return query.list();
	}

	/**
	 * 新增
	 * 
	 * @param merchantCard
	 * @return
	 * @throws Exception
	 */
	public int add(MerchantCard merchantCard) throws Exception {
		String sql = "insert into mrt_merchant_card values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString(0, merchantCard.getId());
		query.setString(1, merchantCard.getCardNo());
		query.setString(2, merchantCard.getCardType());
		query.setString(3, merchantCard.getMerchantDef().getMid());
		query.setString(4, merchantCard.getOpenId());
		query.setString(5, merchantCard.getUsername());
		query.setString(6, merchantCard.getMobileNo());
		query.setDate(7, merchantCard.getActiveTime());
		query.setLong(8, merchantCard.getBalance());
		query.setDate(9, merchantCard.getExpriyTime());
		query.setDate(10, merchantCard.getCreateTime());
		return query.executeUpdate();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void delete(String id) {
		String sql = "delete from mrt_merchant_card  where id = '" + id + "'";
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.executeUpdate();
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public void deleteAll(int ids) {
		String sql = "delete from mrt_merchant_card  where id in(" + ids + ")";
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.executeUpdate();
	}

	/**
	 * 当天新增会员数
	 * 
	 * @param mid
	 * @return
	 */
	public String queryTodayNumber(String mid) {
		String sql = "SELECT COUNT(*) FROM mrt_merchant_card WHERE mid = ? and DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return query.uniqueResult().toString();
	}

	/**
	 * 本周新增会员数
	 * 
	 * @param mid
	 * @return
	 */
	public String queryWeekNumber(String mid) {
		String sql = "SELECT COUNT(*) FROM mrt_merchant_card WHERE mid = ? AND YEARWEEK(DATE_FORMAT(create_time,'%Y-%m-%d'),1) = YEARWEEK(NOW(),1)";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return query.uniqueResult().toString();
	}

	/**
	 * 获取会员数
	 * 
	 * @param mid
	 * @return
	 */
	public String queryMemberNumber(String mid) {
		String sql = "SELECT COUNT(DISTINCT  open_id) FROM mrt_merchant_card where mid=? ";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return query.uniqueResult().toString();
	}

	/**
	 * 本月新增会员数
	 * 
	 * @param mid
	 * @return
	 */
	public String queryMonthNumber(String mid) {
		String sql = "SELECT COUNT(*) FROM mrt_merchant_card WHERE mid = ? AND DATE_FORMAT(create_time,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m')";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return query.uniqueResult().toString();
	}

	/**
	 * 总会员数
	 * 
	 * @param mid
	 * @return
	 */
	public String querySumNumber(String mid) {
		String sql = "SELECT COUNT(*) FROM mrt_merchant_card WHERE mid = ?";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return query.uniqueResult().toString();
	}

	/**
	 * 本周每天的新增会员数
	 * 
	 * @param mid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryAWeekNumber(String mid) {
		String sql = "SELECT COUNT(id) FROM mrt_merchant_card "
				+ "WHERE mid = ? AND YEARWEEK(DATE_FORMAT(create_time,'%Y-%m-%d')) > YEARWEEK(NOW())-1 "
				+ "GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return query.list();
	}

	/**
	 * 七天内每天的新增会员数
	 * 
	 * @param mid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> querySevenDayNumber(String mid) {
		String sql = "SELECT DATE_FORMAT(create_time,'%d'), COUNT(id) FROM mrt_merchant_card "
				+ "WHERE  mid = ? AND DATE_FORMAT(create_time,'%Y-%m-%d') BETWEEN DATE_SUB(CURDATE(),INTERVAL 6 DAY) AND CURDATE() "
				+ "GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return (List<Object[]>) query.list();
	}

	/**
	 * 本年每月新增会员数
	 * 
	 * @param mid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryYearNumber(String mid) {
		String sql = "SELECT DATE_FORMAT(create_time,'%m'), COUNT(id) FROM mrt_merchant_card "
				+ "WHERE mid = ? AND (DATE_FORMAT(create_time,'%Y')) > DATE_FORMAT(NOW(),'%Y')-1 "
				+ "GROUP BY DATE_FORMAT(create_time,'%Y-%m')";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, mid);
		return (List<Object[]>) query.list();
	}

	public BigInteger getThisDateNum(String mid, String day) {
		String sql = "SELECT COUNT(*) FROM mrt_merchant_card WHERE mid = ? and DATE_FORMAT(create_time,'%Y-%m-%d') = ?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, mid);
		query.setString(1, day);
		BigInteger res = null;
		Object o = query.uniqueResult();
		if (null != o) {
			res = (BigInteger) o;
		}
		return res;
	}

	
	
	@SuppressWarnings("unchecked")
	public BigInteger findTotalCount(Map<String,Object> params,String mid) {
		String sql = "select count(*) from mrt_merchant_card where mid= '"+mid+"' ";
		if(params.containsKey("cardNo")) {
			sql += " and card_no like '%"+params.get("cardNo")+"%'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		if(params.containsKey("canceled")) {
			sql += " and canceled = '"+params.get("canceled")+"'";
		}
		if(params.containsKey("freeze")) {
			sql += " and freeze = '"+params.get("freeze")+"'";
		}
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
		if(params.containsKey("labelIds")) {
			List<Integer> labelIds = (List<Integer>) params.get("labelIds");
			if(labelIds.size()==0){
				sql += " and (label_ids like '%-1%'";
			}
			for(int i=0;i<labelIds.size();i++) {
				if(i == 0) {
					sql += " and (label_ids like '%"+labelIds.get(i)+"%'";
				}else {
					sql += " or label_ids like '%"+labelIds.get(i)+"%'";
				}
			}
			sql += ") ";
		}
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		BigInteger totcalCount = (BigInteger) query.uniqueResult();
		return totcalCount;
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger findTotalCountNew(Map<String,Object> params,String mid) {
		String sql = "select count(*) from mrt_merchant_card where gid= '"+mid+"' ";
		if(params.containsKey("cardNo")) {
			sql += " and card_no like '%"+params.get("cardNo")+"%'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		if(params.containsKey("canceled")) {
			sql += " and canceled = '"+params.get("canceled")+"'";
		}
		if(params.containsKey("freeze")) {
			sql += " and freeze = '"+params.get("freeze")+"'";
		}
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
		if(params.containsKey("labelIds")) {
			List<Integer> labelIds = (List<Integer>) params.get("labelIds");
			if(labelIds.size()==0){
				sql += " and (labels like '%-1%'";
			}
			for(int i=0;i<labelIds.size();i++) {
				if(i == 0) {
					sql += " and (labels like '%"+labelIds.get(i)+"%'";
				}else {
					sql += " or labels like '%"+labelIds.get(i)+"%'";
				}
			}
			sql += ") ";
		}
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		BigInteger totcalCount = (BigInteger) query.uniqueResult();
		return totcalCount;
	}

	public Boolean update(MerchantCard card) {
		String sql="update mrt_merchant_card set points=?  where mid=? and id=?";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);
		query.setString(0, card.getPoints());
		query.setString(1,card.getMerchantDef().getMid());
		query.setString(2,card.getId());
		return query.executeUpdate()>0;
	}


	
	

	/**
	 * 会员数和会员余额总计
	 * 
	 * @author bin.cheng
	 * @param params
	 * @param mid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Long> getCardMap(Map<String, Object> params, String mid) {
		String sql = "select SUM(CASE WHEN canceled='0' and freeze='0' then 1 ELSE 0 END) cardNum, round(sum(balance)/100,2) totalBalance from mrt_merchant_card where mid= '"+mid+"' ";
		if(params.containsKey("cardType")) {
			String cardType=params.get("cardType").toString();
			sql += " and card_type = '"+cardType+"'";
		}
		if(params.containsKey("cardNo")) {
			sql += " and card_no like '%"+params.get("cardNo")+"%'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		if(params.containsKey("canceled")) {
			sql += " and canceled = '"+params.get("canceled")+"'";
		}
		if(params.containsKey("freeze")) {
			sql += " and freeze = '"+params.get("freeze")+"'";
		}
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
		if(params.containsKey("labelIds")) {
			List<Integer> labelIds = (List<Integer>) params.get("labelIds");
			if(labelIds.size()==0){
				sql += " and (label_ids like '%-1%'";
			}
			for(int i=0;i<labelIds.size();i++) {
				if(i == 0) {
					sql += " and (label_ids like '%"+labelIds.get(i)+"%'";
				}else {
					sql += " or label_ids like '%"+labelIds.get(i)+"%'";
				}
			}
			sql += ") ";
		}
		this.getSession().setFlushMode(FlushMode.MANUAL);
		SQLQuery query=this.getSession().createSQLQuery(sql);	
		List<Map<String, Long>> cardMapList = query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		if (cardMapList.size()>0) {
			Map<String, Long> cardMap= cardMapList.get(0);
			return cardMap;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Page<MerchantCard> getUserList(String mid,Map<String,Object> params,Integer pageNo,Integer pageSize){
		/*String cardNo = (String) params.get("LIKES_cardNo");*/
		String username = (String) params.get("LIKES_username");
		String mobileNo = (String) params.get("LIKES_mobileNo");
		StringBuilder hql = new StringBuilder("from MerchantCard m where mid = :mid and wxCallbackTicketno is not null and wxCallbackTicketno != '' ");
		
		/*if(StringUtils.isNotEmpty(username)){
			hql.append(" and cardNo like :cardNo");
		}*/
		
		if(StringUtils.isNotEmpty(username)){
			hql.append(" and username like :username");
		}
		
		if(StringUtils.isNotEmpty(mobileNo)){
			hql.append(" and mobileNo like :mobileNo");
		}
		hql.append(" order by createTime DESC");
		Query query = this.getSession().createQuery(hql.toString());
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo-1)*pageSize);
		query.setParameter("mid", mid);
		/*if(StringUtils.isNotEmpty(username)){
			query.setParameter("cardNo", "%"+cardNo+"%");
		}*/
		
		if(StringUtils.isNotEmpty(username)){
			query.setParameter("username", "%"+username+"%");
		}
		
		if(StringUtils.isNotEmpty(mobileNo)){
			query.setParameter("mobileNo", "%"+mobileNo+"%");
		}
		List<MerchantCard> cards = query.list();
		String countHql = "select count(m) "+ hql.toString();
		Query countQuery = this.getSession().createQuery(countHql);
		
		/*if(StringUtils.isNotEmpty(username)){
		 * countQuery.setParameter("cardNo", "%"+cardNo+"%");
		}*/
		countQuery.setParameter("mid", mid);
		if(StringUtils.isNotEmpty(username)){
			countQuery.setParameter("username", "%"+username+"%");
		}
		
		if(StringUtils.isNotEmpty(mobileNo)){
			countQuery.setParameter("mobileNo", "%"+mobileNo+"%");
		}
		Object totalCount =  countQuery.uniqueResult();
		Page<MerchantCard> page = new Page<MerchantCard>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResult(cards);
		page.setTotalCount(totalCount == null?0L:Long.parseLong(String.valueOf(totalCount)));
		return page;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MerchantCard> queryList(Integer pageNo,Integer pageSize,String mid,Map<String,Object> params) {
		String sql = "SELECT id as id,card_no as cardNo,card_type as cardType ,open_id as openId,username as username,mobile_no as mobileNo,active_time as activeTime,balance ,create_time as create_time ,points ,label_ids as labelIds ,birthday,sex,labels  ";
		sql += " from mrt_merchant_card where mid = '"+mid+"' ";
		if(params.containsKey("cardNoBegin")) {
			sql += " and CONVERT(card_no,SIGNED) >= '"+params.get("cardNoBegin")+"'";
		}
		if(params.containsKey("cardNoEnd")) {
			sql += " and CONVERT(card_no,SIGNED) <= '"+params.get("cardNoEnd")+"'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		
		if(params.containsKey("cardType")){
			sql += " and card_type = '"+params.get("cardType")+"'";
		}
		
		
		if(params.containsKey("birthday")) {
			String birthDay = (String) params.get("birthday");
			if("week".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 7 DAY),'%m-%d')";
			}else if("month".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 30 DAY),'%m-%d')";
			}
			
		}
		
		if(params.containsKey("balanceBegin")) {
			Float balanceBegin_ = Float.parseFloat(params.get("balanceBegin").toString())*100;
			sql += " and balance >= '"+balanceBegin_.intValue()+"'";
		}
		
		if(params.containsKey("balanceEnd")) {
			Float balanceEnd_ = Float.parseFloat(params.get("balanceEnd").toString())*100;
			sql += " and balance <= '"+ balanceEnd_.intValue()+"'";
		}
		
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
		
		if(params.containsKey("labelIds")) {
			
			List<List<Integer>> labelAllIds = (List<List<Integer>>) params.get("labelIds");
			
			if(labelAllIds.size() >0){
				for(int j=0;j<labelAllIds.size();j++){
					/*List<Integer> labelIds = (List<Integer>) params.get("labelIds");*/
					List<Integer> labelIds = labelAllIds.get(j);
					if(labelIds.size() == 0){
						sql += " and (labels like '%-1%'";
					}
					
					for(int i=0;i<labelIds.size();i++) {
						if(i == 0) {
							sql += " and (labels like '%"+labelIds.get(i)+"%'";
						}else {
							sql += " or labels like '%"+labelIds.get(i)+"%'";
						}
					}
					sql += ") ";
				}
			}
			
			
		}
		
		if(params.containsKey("pointsBegin")) {
			sql += " and points >= '"+params.get("pointsBegin")+"'";
		}
		
		if(params.containsKey("pointsEnd")) {
			sql += " and points <= '"+ params.get("pointsEnd")+"'";
		}
		
		sql += " order by active_time desc";
		System.out.println(sql);
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		List list = query.list();
		Object[] arr = null;
		List<MerchantCard> merchantCards = new ArrayList<MerchantCard>();
		for(Object obj : list) {
			MerchantCard m = new MerchantCard();
			arr = (Object[])obj;
			m.setId(String.valueOf(arr[0]));
			
			String cardNo = "";
			if(arr[1] != null) {
				cardNo = String.valueOf(arr[1]);
			}
			m.setCardNo(cardNo);
			
			String cardType = "";
			if(arr[2] != null) {
				cardType = String.valueOf(arr[2]);
			}
			m.setCardType(cardType);
			
			String openId = "";
			if(arr[3] != null) {
				openId = String.valueOf(arr[3]);
			}
			m.setOpenId(openId);
			
			String username = "";
			if(arr[4] != null) {
				username = String.valueOf(arr[4]);
			}
			m.setUsername(username);
			
			String mobileNo = "";
			if(arr[5] != null) {
				mobileNo = String.valueOf(arr[5]);
			}
			m.setMobileNo(mobileNo);
			
			if(arr[6] != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[6]);
				try {
					Date date = sdf.parse(dateStr);
					m.setActiveTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			Integer balance = 0;
			if(arr[7] != null) {
				balance = Integer.valueOf(String.valueOf(arr[7]));
			}
			m.setBalance(balance);
			
			if(arr[8] != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[8]);
				try {
					Date date = sdf.parse(dateStr);
					m.setCreateTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			String points = "0";
			if(arr[9] != null) {
				points = String.valueOf(arr[9]);
			}
			m.setPoints(points);
			
			if(arr[10] != null) {
				m.setLabelIds(String.valueOf(arr[10]));
			}
			if(arr[11]!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = String.valueOf(arr[11]);
				try {
					Date date = sdf.parse(dateStr);
					m.setBirthday(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if(arr[13]!=null){
				m.setLabels(String.valueOf(arr[13]));
			}
			merchantCards.add(m);
		}
		
		return merchantCards;
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger findTotal(Map<String,Object> params,String mid) {
		String sql = "select count(*) from mrt_merchant_card where mid= '"+mid+"' ";
		if(params.containsKey("cardNoBegin")) {
			sql += " and CONVERT(card_no,SIGNED) >= '"+params.get("cardNoBegin")+"'";
		}
		if(params.containsKey("cardNoEnd")) {
			sql += " and CONVERT(card_no,SIGNED) <= '"+params.get("cardNoEnd")+"'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		
		if(params.containsKey("cardType")){
			sql += " and card_type = '"+params.get("cardType")+"'";
		}
		
		if(params.containsKey("birthday")) {
			String birthDay = (String) params.get("birthday");
			if("week".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 7 DAY),'%m-%d')";
			}else if("month".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 30 DAY),'%m-%d')";
			}
			
		}
		
		if(params.containsKey("balanceBegin")) {
			Float balanceBegin_ = Float.parseFloat(params.get("balanceBegin").toString())*100;
			sql += " and balance >= '"+balanceBegin_.intValue()+"'";
		}
		
		if(params.containsKey("balanceEnd")) {
			Float balanceEnd_ = Float.parseFloat(params.get("balanceEnd").toString())*100;
			sql += " and balance <= '"+ balanceEnd_.intValue()+"'";
		}
		
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
        if(params.containsKey("labelIds")) {
			
			List<List<Integer>> labelAllIds = (List<List<Integer>>) params.get("labelIds");
			
			if(labelAllIds.size() >0){
				for(int j=0;j<labelAllIds.size();j++){
					/*List<Integer> labelIds = (List<Integer>) params.get("labelIds");*/
					List<Integer> labelIds = labelAllIds.get(j);
					if(labelIds.size() == 0){
						sql += " and (labels like '%-1%'";
					}
					
					for(int i=0;i<labelIds.size();i++) {
						if(i == 0) {
							sql += " and (labels like '%"+labelIds.get(i)+"%'";
						}else {
							sql += " or labels like '%"+labelIds.get(i)+"%'";
						}
					}
					sql += ") ";
				}
			}
			
			
		}
		
		
		if(params.containsKey("pointsBegin")) {
			sql += " and points >= '"+params.get("pointsBegin")+"'";
		}
		
		if(params.containsKey("pointsEnd")) {
			sql += " and points <= '"+ params.get("pointsEnd")+"'";
		}
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		BigInteger totcalCount = (BigInteger) query.uniqueResult();
		return totcalCount;
	}


    public Boolean updateCard(MerchantCard card, String mid) {
         String sql="update mrt_merchant_card set  canceled=? ,cancel_time=?,freeze=?,freeze_time=?,username=?,birthday=?,sex=? where id=? and mid=? ";
         this.getSession().setFlushMode(FlushMode.MANUAL);
         Query query=this.getSession().createSQLQuery(sql);
           query.setString(0, card.getCanceled());
           if(null!=card.getCancelTime()){
               query.setDate(1,card.getCancelTime());
           }else{
                query.setString(1,null);
           }
           query.setString(2,card.getFreeze());
           if(null!=card.getFreezeTime()){
               query.setDate(3,card.getFreezeTime());
           }else{
               query.setString(3, null);
           }
           query.setString(4,StringUtils.isEmpty(card.getUsername())==true?"":card.getUsername());
           if(null!=card.getBirthday()){
               query.setDate(5, card.getBirthday());
           }else{
               query.setString(5,null);
           }
           query.setString(6,card.getSex());
           query.setString(7,card.getId());
           query.setString(8,mid);
         return query.executeUpdate()>0;
    }

	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
			Exception.class, RuntimeException.class})
	public boolean setLabel(String id,String mid,String labelIds) throws Exception{
		String sql =" update mrt_merchant_card set label_ids =? where id=? and mid =?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, labelIds);
		query.setString(1, id);
		query.setString(2, mid);
		int result = query.executeUpdate();
		if(result >1){
			throw new Exception("打标签异常!");
		}else if(result == 0){
			return false;
		}else{
			return true;
		}
	}
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
			Exception.class, RuntimeException.class})
	public boolean batchSetLabel(String[] ids,String mid,String labelIds) throws Exception{
		StringBuilder sql = new StringBuilder("");
		sql.append(" update mrt_merchant_card ");
		sql.append(" set label_ids =? ");
		sql.append(" where mid=? ");
		sql.append("and (");
		for(int i=0;i<ids.length;i++){
			if(i == 0){
				sql.append(" id = '"+ids[i].toString()+"' ");
			}else{
				sql.append(" or id = '"+ids[i].toString()+"' ");
			}
		}
		sql.append(" )");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString(0, labelIds);
		query.setString(1, mid);
		int result = query.executeUpdate();
		if(result != ids.length){
			throw new Exception("打标签异常!");
		}else if(result == 0){
			return false;
		}else{
			return true;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MerchantCard> queryList2(Integer pageNo,Integer pageSize,String mid,Map<String,Object> params) {
		String sql = "SELECT id as id,card_no as cardNo,card_type as cardType ,username as username,mobile_no as mobileNo,active_time as activeTime,balance ,create_time as create_time ,points ,birthday,sex,labels";
		sql += " from mrt_merchant_card where mid = '"+mid+"' and canceled='0' and freeze='0' ";
		if(params.containsKey("cardNoBegin")) {
			sql += " and CONVERT(card_no,SIGNED) >= '"+params.get("cardNoBegin")+"'";
		}
		if(params.containsKey("cardNoEnd")) {
			sql += " and CONVERT(card_no,SIGNED) <= '"+params.get("cardNoEnd")+"'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		
		if(params.containsKey("cardType")){
			sql += " and card_type = '"+params.get("cardType")+"'";
		}
		
		
		if(params.containsKey("birthday")) {
			String birthDay = (String) params.get("birthday");
			if("week".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 7 DAY),'%m-%d')";
			}else if("month".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 30 DAY),'%m-%d')";
			}
			
		}
		
		if(params.containsKey("balanceBegin")) {
			Float balanceBegin_ = Float.parseFloat(params.get("balanceBegin").toString())*100;
			sql += " and balance >= '"+balanceBegin_.intValue()+"'";
		}
		
		if(params.containsKey("balanceEnd")) {
			Float balanceEnd_ = Float.parseFloat(params.get("balanceEnd").toString())*100;
			sql += " and balance <= '"+ balanceEnd_.intValue()+"'";
		}
		
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
		
		if(params.containsKey("labelIds")) {
			
			List<List<String>> labelAllIds = (List<List<String>>) params.get("labelIds");
			
			if(labelAllIds.size() >0){
				for(int j=0;j<labelAllIds.size();j++){
					/*List<Integer> labelIds = (List<Integer>) params.get("labelIds");*/
					List<String> labelIds = labelAllIds.get(j);
					if(labelIds.size() == 0){
						sql += " and (labels like '%-1%'";
					}
					
					for(int i=0;i<labelIds.size();i++) {
						if(i == 0) {
							sql += " and (labels like '%"+labelIds.get(i)+"%'";
						}else {
							sql += " or labels like '%"+labelIds.get(i)+"%'";
						}
					}
					sql += ") ";
				}
			}
			
			
		}
		
		if(params.containsKey("pointsBegin")) {
			sql += " and points >= '"+params.get("pointsBegin")+"'";
		}
		
		if(params.containsKey("pointsEnd")) {
			sql += " and points <= '"+ params.get("pointsEnd")+"'";
		}
		
		sql += " order by active_time desc";
		System.out.println(sql);
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		List list = query.list();
		Object[] arr = null;
		List<MerchantCard> merchantCards = new ArrayList<MerchantCard>();
		for(Object obj : list) {
			MerchantCard m = new MerchantCard();
			arr = (Object[])obj;
			m.setId(String.valueOf(arr[0]));
			
			String cardNo = "";
			if(arr[1] != null) {
				cardNo = String.valueOf(arr[1]);
			}
			m.setCardNo(cardNo);
			
			String cardType = "";
			if(arr[2] != null) {
				cardType = String.valueOf(arr[2]);
			}
			m.setCardType(cardType);
			
			/*String openId = "";
			if(arr[3] != null) {
				openId = String.valueOf(arr[3]);
			}
			m.setOpenId(openId);*/
			
			String username = "";
			if(arr[3] != null) {
				username = String.valueOf(arr[3]);
			}
			m.setUsername(username);
			
			String mobileNo = "";
			if(arr[4] != null) {
				mobileNo = String.valueOf(arr[4]);
			}
			m.setMobileNo(mobileNo);
			
			if(arr[5] != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[5]);
				try {
					Date date = sdf.parse(dateStr);
					m.setActiveTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			Integer balance = 0;
			if(arr[6] != null) {
				balance = Integer.valueOf(String.valueOf(arr[6]));
			}
			m.setBalance(balance);
			
			if(arr[7] != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = String.valueOf(arr[7]);
				try {
					Date date = sdf.parse(dateStr);
					m.setCreateTime(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			String points = "0";
			if(arr[8] != null) {
				points = String.valueOf(arr[8]);
			}
			m.setPoints(points);
			
			/*if(arr[10] != null) {
				m.setLabelIds(String.valueOf(arr[10]));
			}*/
			if(arr[9]!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = String.valueOf(arr[9]);
				try {
					Date date = sdf.parse(dateStr);
					m.setBirthday(date);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if(arr[11] != null){
				m.setLabels(String.valueOf(arr[11]));
			}
			merchantCards.add(m);
		}
		
		return merchantCards;
	}
	
	@SuppressWarnings("unchecked")
	public BigInteger findTotal2(Map<String,Object> params,String mid) {
		String sql = "select count(*) from mrt_merchant_card where mid= '"+mid+"' and canceled='0' and freeze='0' ";
		if(params.containsKey("cardNoBegin")) {
			sql += " and CONVERT(card_no,SIGNED) >= '"+params.get("cardNoBegin")+"'";
		}
		if(params.containsKey("cardNoEnd")) {
			sql += " and CONVERT(card_no,SIGNED) <= '"+params.get("cardNoEnd")+"'";
		}
		if(params.containsKey("username")) {
			sql += " and username like '%"+params.get("username")+"%'";
		}
		if(params.containsKey("mobileNo")) {
			sql += " and mobile_no like '%"+params.get("mobileNo")+"%'";
		}
		
		if(params.containsKey("cardType")){
			sql += " and card_type = '"+params.get("cardType")+"'";
		}
		
		if(params.containsKey("birthday")) {
			String birthDay = (String) params.get("birthday");
			if("week".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 7 DAY),'%m-%d')";
			}else if("month".equals(birthDay)){
				sql += " and birthday > '' and DATE_FORMAT(birthday,'%m-%d') > DATE_FORMAT(now(),'%m-%d') and DATE_FORMAT(birthday,'%m-%d') <= DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 30 DAY),'%m-%d')";
			}
			
		}
		
		if(params.containsKey("balanceBegin")) {
			Float balanceBegin_ = Float.parseFloat(params.get("balanceBegin").toString())*100;
			sql += " and balance >= '"+balanceBegin_.intValue()+"'";
		}
		
		if(params.containsKey("balanceEnd")) {
			Float balanceEnd_ = Float.parseFloat(params.get("balanceEnd").toString())*100;
			sql += " and balance <= '"+ balanceEnd_.intValue()+"'";
		}
		
		if(params.containsKey("startTime")) {
			sql += " and active_time >= '" +String.valueOf(params.get("startTime"))+" 00:00:00" +"'";
		}
		if(params.containsKey("endTime")) {
			sql += " and active_time <= '" +String.valueOf(params.get("endTime"))+" 23:59:59" +"'";
		}
        if(params.containsKey("labelIds")) {
			
			List<List<Integer>> labelAllIds = (List<List<Integer>>) params.get("labelIds");
			
			if(labelAllIds.size() >0){
				for(int j=0;j<labelAllIds.size();j++){
					/*List<Integer> labelIds = (List<Integer>) params.get("labelIds");*/
					List<Integer> labelIds = labelAllIds.get(j);
					if(labelIds.size() == 0){
						sql += " and (labels like '%-1%'";
					}
					
					for(int i=0;i<labelIds.size();i++) {
						if(i == 0) {
							sql += " and (labels like '%"+labelIds.get(i)+"%'";
						}else {
							sql += " or labels like '%"+labelIds.get(i)+"%'";
						}
					}
					sql += ") ";
				}
			}
			
			
		}
		
		
		if(params.containsKey("pointsBegin")) {
			sql += " and points >= '"+params.get("pointsBegin")+"'";
		}
		
		if(params.containsKey("pointsEnd")) {
			sql += " and points <= '"+ params.get("pointsEnd")+"'";
		}
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query=this.getSession().createSQLQuery(sql);	
		BigInteger totcalCount = (BigInteger) query.uniqueResult();
		return totcalCount;
	}
	
	
	
	/**
	 * 获取新增会员数
	 * 
	 * @author bin.cheng
	 * @param mid
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	public BigInteger getAddCardCount(String mid, String timeStart,
			String timeEnd) {
		BigInteger num=new BigInteger("0");
		try {
			String sql="SELECT COUNT(*) FROM mrt_merchant_card WHERE mid=? AND create_time>=? AND create_time <=?";
			SQLQuery query = this.getSession().createSQLQuery(sql);
			query.setString(0, mid);
			query.setString(1, timeStart);
			query.setString(2, timeEnd);
			num=(BigInteger) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 获取会员充值金额
	 * 
	 * @author bin.cheng
	 * @param mid
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	public BigDecimal getAddCardRecharge(String mid, String timeStart,
			String timeEnd) {
		BigDecimal amount=new BigDecimal("0");
		try {
			String sql="SELECT ROUND(SUM(tran_amount)/100,2) FROM mrt_merchant_card_detail WHERE mid=? AND tran_code IN ('302','309') AND tran_time>=? AND tran_time <=?";
			SQLQuery query = this.getSession().createSQLQuery(sql);
			query.setString(0, mid);
			query.setString(1, timeStart);
			query.setString(2, timeEnd);
			amount= (BigDecimal) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}

	/**
	 * 获取首页会员卡图表数据
	 * 
	 * @author bin.cheng
	 * @param mid
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> drawCardMonthData(String mid,
			String timeStart, String timeEnd) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			String sql="SELECT card_type cardType, `DAY`(create_time) createDay, COUNT(*) cardNum FROM mrt_merchant_card WHERE mid=? AND card_type in ('1','2','99') AND create_time>=? AND create_time<=? GROUP BY `DAY`(create_time), card_type ORDER BY `DAY`(create_time) ASC, card_type ASC";
//			String sql="SELECT card_type cardType, `DAY`(create_time) createDay, COUNT(*) cardNum FROM mrt_merchant_card WHERE mid=? AND card_type in ('1','2','99') GROUP BY `DAY`(create_time), card_type ORDER BY `DAY`(create_time) ASC, card_type ASC";
			SQLQuery query=this.getSession().createSQLQuery(sql);
			query.setString(0, mid);
			query.setString(1, timeStart);
			query.setString(2, timeEnd);
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			list=query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 会员卡饼图数据查询
	 * 
	 * @author bin.cheng
	 * @param mid
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> drawCardMonthPercentData(String mid, String timeStart,
			String timeEnd) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
			String sql="SELECT card.card_type cardType, COUNT(*) cardNum from mrt_merchant_card card WHERE mid=? AND create_time>=? AND create_time<=? and card.card_type in ('1','2','99') GROUP BY card.card_type order by card.card_type ASC";
			SQLQuery query=this.getSession().createSQLQuery(sql);
			query.setString(0, mid);
			query.setString(1, timeStart);
			query.setString(2, timeEnd);
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			list=query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

