<%@page import="dto.Challenge"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<Challenge> list = (List)request.getAttribute("challengeList"); %>

<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>
<style type="text/css">

.container{
width:900px;
}


th{
background-color: #A8201A;
}

#move:hover tbody tr:hover td{
background:#143642;
color:#FFFFFF;
}

#btnTitle{
background-color: #143642;
color: white;
border-radius: 4px;
}

.right{

margin:0px 3px 5px 3px;
}

</style>
<script type="text/javascript">
$( document ).ready( function() {
	$('#challenge').css({"background":"#EC9A29"})
	
})
</script>

<div class="container">
<div id="tableHeader">
	<div class="left" style="font-size:30px; font-weight:bold;">챌린지 목록 <a href="/admin/challenge/write"><i class="fas fa-plus"></i></a></div>
	<div><span>&nbsp</span></div>
		
	
	<form action="/admin/challenge/list" method="get">
	<div class="right">제목:&nbsp;<input type="text" id="title" name="title" />
			<button id="btnTitle">검색</button>
			
	</div>
	</form>
	<div><span>&nbsp</span></div>

</div>


<table class="table table-striped table-hover table-condensed" id="move">
<tr>
	<th>챌린지번호</th>
	<th>제목</th>
	<th>시작날짜</th>
	<th>끝나는 날짜</th>
	<th>참가비</th>
	<th>좋아요</th>
</tr>
<%	for(int i=0; i<list.size(); i++) { %>
<tr>
	<td><%=list.get(i).getChNo() %></td>
	<td>
		<a href="/admin/challenge/view?chNo=<%=list.get(i).getChNo() %>">
		<%=list.get(i).getChTitle() %>
		</a>
	</td>
	<td><%=list.get(i).getChStartDate() %></td>
	<td><%=list.get(i).getChEndDate() %></td>
	<td><%=list.get(i).getChMoney() %>원</td>
	<td><%=list.get(i).getChLikes() %></td>
	
</tr>
<%	} %>
</table>


</div>
<%if(session.getAttribute("ch_admin_title")==null) {%> <%--일반적인 페이징 --%> 
<%@ include file="/WEB-INF/views/layout/paging.jsp" %>
<%}else{ %> <%-- title이 존재 했을시 페이징--%>
<%@ include file="/WEB-INF/views/adminChallenge/adminChallengePaging.jsp" %>
<%} %>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>