﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="sidebar" class="sidebar responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<%-- <div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						
						<button class="btn btn-info" onclick="changeMenus();" title="切换菜单">
							<i class="ace-icon fa fa-pencil"></i>
						</button>
						
						<button class="btn btn-success" title="UI实例" onclick="window.open('${ctx}/static2/html_UI/html');">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning" title="" id="adminzidian">
							<i class="ace-icon fa fa-book"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts --> --%>

				<ul class="nav nav-list">
					<li class="">
						<a href="${ctx}/mUser/index">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text">主页</span>
						</a>
					</li>
					
					<!-- <li class=""  id="leftBar0">
						<a style="cursor:pointer;" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop blue"></i>
							<span class="menu-text">
								商户管理
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<ul class="submenu">
							<li id="leftBar0_1" class="">
								<a onclick="siMenu('leftBar0_1','leftBar0','商户进件','merchantIntoApplication/list')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									商户进件
								</a>
							</li>
							<li id="leftBar0_2" class="">
								<a onclick="siMenu('leftBar0_2','leftBar0','商户管理','merchantDef/list')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									商户管理
								</a>
							</li>
							<li id="leftBar0_3" class="">
								<a onclick="siMenu('leftBar0_3','leftBar0','商户交易汇总','tranLog/totalPayment')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									商户交易汇总
								</a>
							</li>
						</ul>
					</li>
					<li class=""  id="leftBar1">
						<a style="cursor:pointer;" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop blue"></i>
							<span class="menu-text">
								代理商管理
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<ul class="submenu">
							<li id="leftBar1_1" class="">
								<a onclick="siMenu('leftBar1_1','leftBar1','下级代理商管理','agentManage/list')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									下级代理商管理
								</a>
							</li>
						</ul>
					</li>
					<li class=""  id="leftBar3">
						<a style="cursor:pointer;" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop blue"></i>
							<span class="menu-text">
								分润管理
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li id="leftBar3_1" class="">
								<a onclick="siMenu('leftBar3_1','leftBar3','分润明细','profit/query')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									分润明细
								</a>
								<b class="arrow"></b>
							</li>
							<li id="leftBar3_2" class="">
								<a onclick="siMenu('leftBar3_2','leftBar3','分润汇总','profit/queryTotal')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									分润汇总
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					<li class=""  id="leftBar4">
						<a style="cursor:pointer;" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop blue"></i>
							<span class="menu-text">
								提现审核
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li id="leftBar4_1" class="">
								<a onclick="siMenu('leftBar4_1','leftBar4','提现审核','virtAssetLog/queryPayLog')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									提现审核
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					 -->
					 <li class=""  id="leftBar0">
						<a style="cursor:pointer;" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop blue"></i>
							<span class="menu-text">
								会员
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<ul class="submenu">
							<li id="leftBar0_1" class="">
								<a onclick="siMenu('leftBar0_1','leftBar0','会员权益','memberInterests/list')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									会员权益
								</a>
							</li>
							<li id="leftBar0_2" class="">
								<a onclick="siMenu('leftBar0_2','leftBar0','会员等级','memberGrade/list')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									会员等级
								</a>
							</li>
							<li id="leftBar0_3" class="">
								<a onclick="siMenu('leftBar0_3','leftBar0','会员管理','merchantCard/query')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									会员管理
								</a>
							</li>
							<li id="leftBar0_4" class="">
								<a onclick="siMenu('leftBar0_4','leftBar0','会员认证','merchantCard/query')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									会员认证
								</a>
							</li>
							<li id="leftBar0_5" class="">
								<a onclick="siMenu('leftBar0_5','leftBar0','会员缴费','merchantCard/query')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									会员缴费
								</a>
							</li>
						</ul>
					</li>
					<li class=""  id="leftBar1">
						<a style="cursor:pointer;" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop blue"></i>
							<span class="menu-text">
								活动
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<ul class="submenu">
							<li id="leftBar1_1" class="">
								<a onclick="siMenu('leftBar1_1','leftBar1','活动培训','memberInterests/list')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
								   活动培训
								</a>
							</li>
						</ul>
					</li>
					<li class=""  id="leftBar2">
						<a style="cursor:pointer;" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop blue"></i>
							<span class="menu-text">
								系统管理
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li id="leftBar2_1" class="">
								<a onclick="siMenu('leftBar2_1','leftBar2','用户管理','mUser/list')" target="mainFrame" style="cursor:pointer;">
								<i class="menu-icon fa fa-key orange"></i>
									用户管理
								</a>
							</li>
						</ul>
					</li>
					
<c:forEach items="${menuList}" var="menu1">
	<c:if test="${menu1.checked&& '0' == menu1.status}">
		<li class=""  id="lm${menu1.id}">
			<a style="cursor:pointer;" class="dropdown-toggle">
				<i class="menu-icon fa ${menu1.icon == null ? 'fa-leaf black' : menu1.icon} blue"></i>
				<span class="menu-text">
					${menu1.name}
				</span>
				<c:if test="${'[]' != menu1.children}"><b class="arrow fa fa-angle-down"></b></c:if>
			</a>
			<b class="arrow"></b>
			<c:if test="${'[]' != menu1.children}">
			<ul class="submenu">
			<c:forEach items="${menu1.children}" var="menu2">
				<c:if test="${menu2.checked && '0' == menu2.status}">
				<li class="" id="z${menu2.id}">
					<a style="cursor:pointer;" <c:if test="${not empty menu2.link && '#' != menu2.link}">target="mainFrame" onclick="siMenu('z${menu2.id}','lm${menu1.id}','${menu2.name}','${menu2.link}')"</c:if>>
						<i class="menu-icon fa ${menu2.icon == null ? 'fa-leaf black' : menu2.icon} orange"></i>
							${menu2.name}
					</a>
					<b class="arrow"></b>
				</li>
				</c:if>
			</c:forEach>
			</ul>
			</c:if>
		</li>
	</c:if>
</c:forEach>
				</ul><!-- /.nav-list -->


				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>
