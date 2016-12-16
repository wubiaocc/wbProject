package com.finance.communication.service.server.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.finance.communication.dao.ProductPicDao;
import com.finance.communication.entity.ProductPic;


/**
 * 产品附件
 * 
 * @author:Kirk Zhou
 * @date:2013-6-29下午04:40:58
 */
@Component
@Transactional(readOnly = true)
public class ProductPicManager {
	@Autowired
	private ProductPicDao productPicDao;

	public List<ProductPic> getProductPicByIds(String ids) {
		List<ProductPic> list = new ArrayList<ProductPic>();
		if (ids != null && !"".equals(ids)) {
			Matcher m = Pattern.compile(Pattern.quote(",")).matcher(ids);
			int j = 0;
			while (m.find()) {
				j++;
			}
			System.out.println("J:" + j);
			String[] idList = new String[j];
			
			for (int i = 0; i < j; i++) {
				if (ids.indexOf(",") <= 0) {
					break;
				}
				String id = ids.substring(0, ids.indexOf(","));
				ids = ids.substring(ids.indexOf(",") + 1, ids.length());
				idList[i] = id;
				System.out.println(idList[i]);
				ProductPic productPic = productPicDao.findUniqueBy("id",
						idList[i]);
				list.add(productPic);
			}
			// return productPicDao.findByIdIn(idList);
		}
		return list;
	}

	@Transactional(readOnly = true)
	public ProductPic getProductPic(String id) {
		return productPicDao.get(id);
	}

	@Transactional(readOnly = false)
	public Boolean addProductPic(ProductPic entity) {
		return productPicDao.insert(entity);
	}

	@Transactional(readOnly = false)
	public void delProductPic(String id) {
		productPicDao.delete(id);
	}
	
	public ProductPic getPicById(String id) {
		return productPicDao.getPicById(id);
	}

	/**
	 * 获取所有的附件
	 */
	@Transactional(readOnly = true)
	public List<ProductPic> getProductPicList() {
		return (List<ProductPic>) productPicDao.getAll();
	}
}
