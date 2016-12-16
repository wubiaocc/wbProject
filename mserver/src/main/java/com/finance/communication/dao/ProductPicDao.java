package com.finance.communication.dao;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.finance.communication.entity.ProductPic;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

@Component
public class ProductPicDao extends HibernateDao<ProductPic, String> {

	public Boolean insert(ProductPic entity) {
		String sql = "insert into erp_product_pic values(?,?,?,?,?,?,?,?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, entity.getId());
		query.setString(1, entity.getOriginalName());
		query.setString(2, entity.getName());
		query.setString(3, entity.getAddress());
		query.setString(4, entity.getFileExt());
		query.setLong(5, entity.getSize());
		query.setString(6, entity.getDescn());
		query.setString(7, entity.getType());
		query.setDate(8, entity.getCreateTime());
		return query.executeUpdate() > 0;
	}

	public void delProductPic(String id) {
		String sql = "delete from erp_product_pic where id=?";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, id);
		query.executeUpdate();
	}
	public ProductPic getPicById(String id) {
		String hql = "from  ProductPic where id=?";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = getSession().createQuery(hql);
		query.setString(0, id);
		 return (ProductPic) query.uniqueResult();
	}
}
