
<%@page import="dto.AdminPost"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%List<AdminPost> list = (List) request.getAttribute("postList");%>

<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
	$('#board').css({"background":"#EC9A29"})
	$('#board').nextAll().css({"background":"#FFFFFF"})
	// + 버튼 누르면 신고등록 페이지로 이동
});
</script>
<style type="text/css">
.container{
width: 900px;

}

.searchform{
float:right; 
margin:0px 3px 5px 3px;
}

#btnSearch{
background-color: #143642;
color: white;
border-radius: 4px;
}

th{
background-color: #A8201A;
}

#move:hover tbody tr:hover td{
background:#143642;
color:#FFFFFF;
}


</style>

<div class="container">

<div class="left" style="font-size:30px; font-weight:bold;">게시글 목록 </div>
	<div><span>&nbsp</span></div>

<div class="searchform">
<form action="/admin/postlist" method="get">
<select id="select" name="select" class="">
<option>제목</option>
<option>내용</option>
</select>
<input type="text" name="search" id="search"/>
<button id="btnSearch">검색</button>
</form>
</div>

<table class="table table-striped table-hover table-condensed" id="move">
<tr>
	<th>글번호</th>
	<th>제목</th>
	<th>아이디숫자</th>
	<th>조회수</th>
	<th>작성일</th>
</tr>
<%	for(int i=0; i<list.size(); i++) { %>
<tr>

<td><%=list.get(i).getP_no() %></td>
<td>
		<a href="/admin/postview?p_no=<%=list.get(i).getP_no() %>">
		<%=list.get(i).getP_title() %>
		</a>
	</td>
	<td><%=list.get(i).getU_no() %></td>
	<td><%=list.get(i).getP_views() %></td>
	<td><%=list.get(i).getP_create_date() %></td>
	
</tr>
<%	} %>
</table>



</div>

<%if(request.getParameter("search") == null){ %>
<%@ include file="/WEB-INF/views/layout/paging.jsp" %>
<%}else{ %>
<%@include file="/WEB-INF/views/adminPost/adminNoticePaging.jsp"%>
<%} %>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

