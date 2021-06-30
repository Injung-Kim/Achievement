
<%@page import="dto.AdminPost"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	AdminPost a = (AdminPost) request.getAttribute("viewPost"); %>

<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	
	$("#btnList").click(function() {
		$(location).attr("href", "/admin/postlist");
	});
	
	
	$("#btnDelete").click(function() {
		
		if( confirm("정말 게시글을 삭제하시겠습니까?") ) {
			$(location).attr("href", "/admin/postdelete?p_no=<%=a.getP_no() %>");
		}
	});
	
});
</script>

<style type="text/css">
.container{
width:900px;
}

.info{
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

.main{
background-color: #A8201A;
color: #FFFFFF;
width:200px;
}
</style>

<div class="container">

<h1>게시글 상세보기</h1>
<hr>

<table class="table table-bordered">
<tr>
<td class="main">글번호</td><td colspan="3"><%=a.getP_no() %></td>
</tr>

<tr>
<td class="main">제목</td><td colspan="3"><%=a.getP_title() %></td>
</tr>

<tr>
<td class="main">아이디</td><td><%=a.getU_no() %></td>

</tr>

<tr>
<td class="main">조회수</td><td><%=a.getP_views() %></td>

</tr>

<tr>
<td class="main">작성일</td><td colspan="3"><%=a.getP_create_date() %></td>
</tr>

<tr><td class="main"  colspan="4">본문</td></tr>

<tr><td colspan="4"><%=a.getP_content() %></td></tr>

</table>

<div class="text-center">	
	<button id="btnList" class="normalbtn">목록</button>	
	<button id="btnDelete" class="exitbtn">삭제</button>
</div>

</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

