
<%@page import="dto.AdminComment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	AdminComment c = (AdminComment) request.getAttribute("viewComment"); %>

<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	
	$("#btnList").click(function() {
		$(location).attr("href", "/admin/commentlist");
	});
	
	
	$("#btnDelete").click(function() {
		
		if( confirm("정말 댓글을 삭제하시겠습니까?") ) {
			$(location).attr("href", "/admin/commentdelete?c_no=<%=c.getC_no() %>");
		}
	});
	
});
</script>

<style type="text/css">
.container{
width:900px;
}

.main{
background-color: #A8201A;
color: #FFFFFF;
width:200px;
}

.exitbtn{
background-color: #A8201A;
color: #FFFFFF;
border-radius: 4px;
float: right;
margin:3px;
}

.normalbtn{
background-color: #143642;
color: #FFFFFF;
border-radius: 4px;
float: right;
margin:3px;
}
</style>


<div class="container">

<h1>댓글 상세보기</h1>
<hr>

<table class="table table-bordered">
<tr>
<td class="main">댓글번호</td><td colspan="3"><%=c.getC_no() %></td>
</tr>

<tr>
<td class="main">아이디 숫자</td><td><%=c.getU_no() %></td>

</tr>


<tr>
<td class="main">작성일</td><td colspan="3"><%=c.getC_create_date() %></td>
</tr>

<tr><td class="main"  colspan="4">내용</td></tr>

<tr><td colspan="4"><%=c.getC_content() %></td></tr>

</table>

<div class="text-center">	
	<button id="btnList" class="normalbtn">목록</button>	
	<button id="btnDelete" class="exitbtn">삭제</button>
</div>

</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

