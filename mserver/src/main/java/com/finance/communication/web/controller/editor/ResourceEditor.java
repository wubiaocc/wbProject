package com.finance.communication.web.controller.editor;

import java.beans.PropertyEditorSupport;

import com.finance.communication.entity.Resource;
import com.finance.communication.service.server.sys.ResourceManager;


/**
 * 资源
 * @author lixiaopeng
 * @date 2013-7-20
 *
 */
public class ResourceEditor extends PropertyEditorSupport
{

	private ResourceManager resourceManager;
	
	public ResourceEditor(ResourceManager resourceManager)
	{
		this.resourceManager = resourceManager;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{

		if (text != null&&!"".equals(text))
		{
			Resource resource= resourceManager.gerResourceById(text);

			setValue(resource);
		} else
		{

			setValue(null);

		}

	}

}
