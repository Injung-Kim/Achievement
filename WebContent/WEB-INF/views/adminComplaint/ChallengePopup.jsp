<%@page import="dto.ChallengeList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setCharacterEncoding("UTF-8"); %>
<%List<ChallengeList> list = (List)request.getAttribute("challengeList"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
<script type="text/javascript" src="/resources/js/search.js"></script>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#sumbit").click(function(){
		$('#form').submit();
	})
	$("tr").click(function(){
		//등록 페이지 챌린지 번호에 반영하기
		$('#ch_no',parent.opener.document).val($(this).children().children("input[name='ch_no']").val())
		 console.log($(this).children().children("input[name='ch_no']").val())
	})
})
</script>
<style type="text/css">
#word{
width: 188px;
height: 34px;
padding : 5px;
border-radius : 5px;
border : 1px solid #ccc;
}

.form-inline{
	margin-top : 10px;
}
input[name="ch_no"]{
	color : red;
	border : none;
	background-color : inherit;
}

tr > td{
 cursor : pointer;
}

</style>
</head>
<body>
<form action="/popup/challenge" method="get" id ="form" class="form-inline">
	<input type="text" id="word" name="word" />
	<button class="btn btn-default" id="sumbit" type="button"><i class="fas fa-search"></i></button>
	<button type="button" class="btn btn-danger" onclick="window.close()">닫기</button>
</form>
<hr>
<table class="table table-striped table-hover table-condensed">
<tr>
	<th style="width : 8%">번호</th>
	<th>챌린지 제목</th>
	<th>카테고리</th>
	<th>생성날짜</th>
	<th>개설자</th>
</tr>
<% for (int i=0; i<list.size();i++){%>
<tr>
	<td><input type="text" name="ch_no" value="<%=list.get(i).getChNo() %>" readonly style="width : 100%;"/ ></td>
	<td><%=list.get(i).getChTitle() %></td>
	<td><%=list.get(i).getCaTitle() %></td>
	<td><%=list.get(i).getChStartDate() %></td>
	<td><%=list.get(i).getuId() %></td>
</tr>
<%} %>
</table>
<%@ include file="/WEB-INF/views/adminComplaint/popupPaging.jsp" %>
</body>
</html>