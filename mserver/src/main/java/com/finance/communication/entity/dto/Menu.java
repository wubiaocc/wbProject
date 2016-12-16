package com.finance.communication.entity.dto;

import java.util.List;

import com.google.common.collect.Lists;

public class Menu
{
	private String id;
	private Long parentId;// 上级资源id
	private String name;// 资源名称
	private String enname;// 英文名称
	private String resourcetype;// 资源类型 1 menu 2 是url
	private String link;// 链接
	private String icon;// 图标
	private String status;// 状态
	private Integer orderid;// 排序号
	private String memo;// 简介
	private List<Menu> children = Lists.newArrayList();

	public void setId(String id)
	{
		this.id = id;
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public String getEnname()
	{
		return enname;
	}

	public void setEnname(String enname)
	{
		this.enname = enname;
	}

	public String getResourcetype()
	{
		return resourcetype;
	}

	public void setResourcetype(String resourcetype)
	{
		this.resourcetype = resourcetype;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Integer getOrderid()
	{
		return orderid;
	}

	public void setOrderid(Integer orderid)
	{
		this.orderid = orderid;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public List<Menu> getChildren()
	{
		return children;
	}

	public void setChildren(List<Menu> children)
	{
		this.children = children;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

}
