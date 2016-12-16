<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 

<div style="width:700px;height:600px">
	<div class="list_grid_query_conditions">
		<fieldset>
			<legend>查询条件</legend>
			<form id="queryForm" >
			<input type="hidden" name="mid" id="mid" value='<shiro:principal property="mid"/>'/>
			<table class="query_table">
				<tr>
				
					<th>商品代码：</th>
					<td><input type="text" name="code" id="code"  class="query_input"/></td>
					<th>商品条码：</th>
					<td><input type="text" name="barcode" id="barcode" class="query_input"/></td>
					<td colspan="2" rowspan="2" valign="middle" align="right"><a class="query_button button" onclick="queryProduct('${ctx}',1)">查询</a></td>
				</tr>
				<tr>
					<th>商品名称：</th>
					<td><input type="text" name="name" id="name" class="query_input"/></td>
					
				</tr>
			</table>
			</form>
		</fieldset>
	</div>
	<div class="list_grid_out_query">
		<table id="resultTable" class="list_grid">
			<tr>
				<th>商品代码</td>
				<th>商品条码</td>
				<th>商品名称</td>
				<th>商品品类</th>
			</tr>
		</table>
		<div class="list_grid_pagination" id="productPagination" style="display:none">
			<table class="record_info"><tr><td>共计:<label id="totalNo"></label>条</td></tr></table>
			<table class="operate">
				<tr>
					<td><a id="firstPage">首页</a></td>
					<td><a id="prePage">上一页</a></td>
					<td><input class="page_no" type="text" id="pageNo"/> / <label id="totalPageNo"></label></td>
					<td><a id="nextPage">下一页</a></td>
					<td><a id="lastPage">末页</a></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
$(top.hangge());
</script>	

