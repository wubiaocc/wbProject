<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pop_up_content" style="width:300px;height:350px">
	<form id="saveForm" action="" method="POST">
		<table class="form_grid">		
			<tr>
				<!-- 生成二维码 -->					
				<td><img src="${ticketUrl}" width="180px" height="180px"/></td>		 
			</tr>
			<tr>
				<td align="center">${message}</td>	
			</tr>									
		</table>
		<div class="form_grid_button">
			<a class="cancel button" onclick="MessageBox.hid(0)">关闭</a>	
		</div>
	</form>	
</div>
		
<script type="text/javascript">
	window.onload=function(){
		var top5 = document.getElementById("tab0_t5");
		if(top5)
		{
			top4.className = "active"; 
			top4.style.display = "block";					
		}
	}
</script>

