package com.finance.communication.service.server.realm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.finance.communication.entity.Role;
import com.finance.communication.entity.SysUser;
import com.finance.communication.service.server.sys.UserManager;
import com.google.common.base.Objects;

public class ShiroDbRealm extends AuthorizingRealm {
	protected UserManager userManager;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUser user = userManager.findUserByloginName(token.getUsername());
		if (user == null) {
			throw new NoSuchUserException("用户不存在");
		}
		if ("1".equals(user.getEnable())) {
			throw new DisabledAccountException();
		}

		String captcha = ((UsernamePasswordCaptchaToken) token).getCaptcha();
		String exitCode = (String) SecurityUtils.getSubject().getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
			throw new CaptchaException("验证码错误");
		}

		SecurityUtils.getSubject().getSession().setAttribute("currentUser",
				new ShiroUser(user.getId(), user.getLoginName(), user.getName()));
		return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getName()),
				user.getPassword(), getName());
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		SysUser user = userManager.findUserByloginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> strings = new ArrayList<String>();
		if (user != null) {
			Set<Role> roles = user.getRoles();
			if (roles != null && roles.size() > 0) {
				for (Iterator<Role> iter = roles.iterator(); iter.hasNext();) {
					Role role = iter.next();
					strings.add(role.getName());
				}
			}
		}
		info.addRoles(strings);
		return info;
	}

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String id;
		public String loginName;
		public String userName;

		public ShiroUser(String id, String loginName, String userName) {
			this.id = id;
			this.loginName = loginName;
			this.userName = userName;
		}

		public String getUserName() {
			return userName;
		}

		public String getLoginName() {
			return loginName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null)
					return false;
			} else if (!loginName.equals(other.loginName))
				return false;
			return true;
		}
	}
}
