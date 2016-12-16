package com.finance.communication.web.controller.editor;

import java.beans.PropertyEditorSupport;

import com.finance.communication.entity.Role;
import com.finance.communication.service.server.sys.RoleManager;

/**
 * 区域，数据绑定
 * 
 * @author lixiaopeng
 * @date 2013-6-30
 * 
 */
public class RoleEditor extends PropertyEditorSupport {
	private RoleManager roleManager;

	public RoleEditor(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (text != null && !"".equals(text)) {
			Role role = roleManager.getRoleById(text);

			setValue(role);
		} else {

			setValue(null);

		}

	}

}
