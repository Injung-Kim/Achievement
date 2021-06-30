
<%@page import="dto.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	Notice n = (Notice) request.getAttribute("viewNotice"); %>

<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	//목록버튼 동작
	$("#btnList").click(function() {
		$(location).attr("href", "/admin/notice");
	});
	
	//수정버튼 동작
	$("#btnUpdate").click(function() {
		$(location).attr("href", "/admin/updatenot?n_no=<%=n.getN_no() %>");
	});

	//삭제버튼 동작
	$("#btnDelete").click(function() {
		
		if(confirm("정말 공지사항을 삭제하시겠습니까?")){
			$(location).attr("href", "/admin/deletenot?n_no=<%=n.getN_no()%>");
		}
	});
	
});
</script>
<style type="text/css">
.container{
width:900px;
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

<h1>공지사항 상세보기</h1>
<hr>

<table class="table table-bordered">
<tr>
<td class="main">글번호</td><td colspan=1"><%=n.getN_no() %></td>
</tr>

<tr>
<td class="main">제목</td><td colspan="1"><%=n.getN_title() %></td>
</tr>

<tr>
<td class="main">아이디 숫자</td><td><%=n.getU_no() %></td>
</tr>

<tr>
<td class="main">조회수</td><td><%=n.getN_views() %></td>
</tr>

<tr>
<td class="main">작성일</td><td colspan="1"><%=n.getN_create_date() %></td>
</tr>

<tr><td class="main"  colspan="4">내용</td></tr>

<tr><td colspan="4"><%=n.getN_content() %></td></tr>

</table>

<div class="text-center">	
	<button id="btnList" class="normalbtn">목록</button>
	
	<button id="btnUpdate" class="normalbtn">수정</button>
	<button id="btnDelete" class="exitbtn">삭제</button>
</div>



</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

