package com.finance.communication.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author Kirk Zhou
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity {

	/**
	 * 
	 */
	protected String id;

	@Id
	@GeneratedValue(generator = "uuid")
	// 指定生成器名称
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 80)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// public void setId(Long id)
	// {
	// this.id = id;
	// }

	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	// @GeneratedValue(generator = "system-uuid")
	// @GenericGenerator(name = "system-uuid", strategy = "uuid")

}
