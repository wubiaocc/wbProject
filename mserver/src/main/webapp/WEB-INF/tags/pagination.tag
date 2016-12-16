<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.wizarpos.wx.core.orm.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<%
int current =  page.getPageNo();
int begin = Math.max(1, current - paginationSize/2);
int end = Math.min(begin + (paginationSize - 1), new Long(page.getTotalPages()).intValue());
request.setAttribute("pageSize", paginationSize);
request.setAttribute("totalPage", page.getTotalPages());
request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
%>

<script type="text/javascript">
function checkPageNo(obj) {
	var pNo=obj.value;
	var totalPage="${totalPage}";
	if (pNo<=0) {
		if (totalPage==0) {
			$("#toGoPage").val(0);
		}else{
			$("#toGoPage").val(1);
		}
	} else if(pNo>totalPage) {
		$("#toGoPage").val(totalPage);
	}
}

function changeCount(pageSize) {
	top.jzts();
	var url = window.location.href.split("?")[0];
	window.location.href=url+"?pageNo=${current-1}&pageSize="+pageSize+"&sortType=${sortType}&${searchParams}";
}

function toTZ() {
	top.jzts();
	var pageNo = $("#toGoPage").val();
	var totalPage="${totalPage}";
	if (totalPage>0&&totalPage<pageNo) {
		pageNo=totalPage;
	}
	var url = window.location.href.split("?")[0];
	window.location.href=url+"?pageNo="+pageNo+"&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}";
}

function nextPage(pageNo) {
	top.jzts();
	var url = window.location.href.split("?")[0];
	window.location.href=url+"?pageNo="+pageNo+"&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}";
}
</script>

<ul class="pagination pull-right no-margin">
	<li><a>共<font color=red>${page.totalCount}</font>条</a></li>
	<li><input type="number" value="" id="toGoPage" onblur="checkPageNo(this)" onchange="checkPageNo(this)" style="width:50px;text-align:center;float:left" placeholder="页码"/></li>
	<li style="cursor:pointer;"><a onclick="toTZ();" class="btn btn-mini btn-success">跳转</a></li>
	<li><a href="?pageNo=1&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}">首页</a></li>
	<% if (page.isHasPre()){%>
		<li><a href="?pageNo=${current-1}&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}">上页</a></li>
	<%}else{%>
		<li><a href="javascript:void(0)">上页</a></li>
	<%} %>
	<c:forEach var="i" begin="${begin}" end="${end}">
		<c:choose>
			<c:when test="${i == current}">
				<li class="active"><a href="?pageNo=${i}&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}"><font color="white">${i}</font></a></li>
			</c:when>
			<c:otherwise>
				<li><a href="?pageNo=${i}&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}">${i}</a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<% if (page.isHasNext()){%>
		<li><a href="?pageNo=${current+1}&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}">下页</a></li>
	<%}else{%>
		<li><a href="javascript:void(0)">下页</a></li>
	<%} %>
	<li><a href="?pageNo=${page.totalPages}&pageSize=${paginationSize}&sortType=${sortType}&${searchParams}">末页</a></li>
	<li><a>共${totalPage}页</a></li>
	<li><select title="显示条数" style="width:55px;float:left;margin-top:1px;" onchange="changeCount(this.value)"><option value="${paginationSize}">${paginationSize}</option><option value='3'>3</option><option value='10'>10</option><option value='20'>20</option><option value='30'>30</option><option value='40'>40</option><option value='50'>50</option><option value='60'>60</option><option value='70'>70</option><option value='80'>80</option><option value='90'>90</option><option value='99'>99</option></select></li>
</ul>