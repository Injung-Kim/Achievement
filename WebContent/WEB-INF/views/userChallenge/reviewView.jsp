<%@page import="dto.Participation"%>
<%@page import="dto.Certification"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<% Participation participation = (Participation)request.getAttribute("participation"); %>

<script type="text/javascript">
$(document).ready(function() {
	$("#btnList").click(function(){
		history.go(-1);
		
	})
})
</script>
<style type="text/css">
td{
	height:80px;
	vertical-align:middle !important;
}
talbe, th, td{
	
	font-size: 20px;
}
</style>
<div class="container">
<table class="table table-responsive text-center">
	<tr><td colspan="2">리뷰 보기</td></tr>
	
	<tr><td colspan="2">참가자 번호:<%=participation.getuNo() %></td></tr>
	<tr><td colspan="2">챌린지 번호:<%=participation.getChNo() %></td></tr>
	<tr><td colspan="2">개설날짜:<%=participation.getPaCreateDate() %></td></tr>
	<tr><td colspan="2">성공 여부:
	<%if("Y".equals(participation.getPaIsSuccess())){ %>
	성공
	<%}else{ %>
	실패
	<%} %>
	</td>
	</tr>
	<tr><td colspan="2">좋아요 여부:
	<%if("Y".equals(participation.getPaLike())) {%>
		누름
	<%}else{ %>
		누르지 않음
	<%} %>
	</td>
	</tr>
	<tr><td>리뷰:<%=participation.getPaReview() %></td></tr>
</table>

<div class="text-center">
<button id="btnList" class="btn btn-primary">돌아가기</button>
</div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>