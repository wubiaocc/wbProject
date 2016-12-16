package com.finance.communication.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.collect.Lists;

/**
 * 资源表
 * 
 * @author LiXiaoPeng
 *
 *         2015年4月24日 下午1:59:47
 */
@Entity
@Table(name = "sys_resource")
public class Resource extends IdEntity
{
	/**
	 * 上级资源id
	 */
	private Long parentId;
	/**
	 * 资源名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String enname;
	/**
	 * 资源类型: 0正常菜单，1代收付菜单，2过滤三级代理商
	 */
	private String resourcetype;
	/**
	 * 链url
	 */
	private String link;
	/**
	 * 菜单图片src/activeClass
	 */
	private String icon;// 图标
	/**
	 * 资源状态 0 可用 1不可用
	 */
	private String status;
	/**
	 * 排序号/序列号
	 */
	private Integer orderid;
	/**
	 * 备注
	 */
	private String memo;
	private List<Resource> children = Lists.newArrayList();

	private boolean checked = false;
	@Transient
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Column(name = "enname", length = 30)
	public String getEnname()
	{
		return this.enname;
	}

	public void setEnname(String enname)
	{
		this.enname = enname;
	}

	@Column(name = "resourcetype", length = 2)
	public String getResourcetype()
	{
		return this.resourcetype;
	}

	public void setResourcetype(String resourcetype)
	{
		this.resourcetype = resourcetype;
	}

	@Column(name = "link", length = 100)
	public String getLink()
	{
		return this.link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	@Column(name = "icon", length = 30)
	public String getIcon()
	{
		return this.icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	@Column(name = "status", nullable = false, length = 30)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	@Column(name = "orderid")
	public Integer getOrderid()
	{
		return this.orderid;
	}

	public void setOrderid(Integer orderid)
	{
		this.orderid = orderid;
	}

	@Column(name = "memo", length = 50)
	public String getMemo()
	{
		return this.memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentId", cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<Resource> getChildren()
	{
		return this.children;
	}

	public void setChildren(List<Resource> children)
	{
		this.children = children;
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
