<%@page import="dto.Certification"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<% String title = (String)request.getAttribute("title"); %>
<% String chWay = (String)request.getAttribute("chWay"); %>
<% Certification certification = (Certification)request.getAttribute("certification"); %>


<script type="text/javascript">
$(document).ready(function() {
	//수정버튼 동작
	
	$("#btnUpdate").click(function() {
		$(location).attr("href", "/participant/certification/update?ceNo=<%=certification.getCeNo() %>");
	});

	//삭제버튼 동작
	$("#btnDelete").click(function() {
		if( confirm("인증을 삭제하시겠습니까?") ) {
			$(location).attr("href", "/participant/certification/delete?ceNo=<%=certification.getCeNo() %>");
		}
	});
	$("#btnList").click(function(){
		$(location).attr("href","/participant/certification/list?chNo=<%=(Integer)session.getAttribute("chNo")%>");
		
	})
})
</script>
<style type="text/css">
td{
	height:80px;
	vertical-align:middle !important;
}
talbe, th, td{
	border: none !important;
	font-size: 20px;
}
</style>
<div class="container">
<table class="table table-responsive text-center">
	<tr><td colspan="2"><i class="far fa-thumbs-up"></i>&nbsp;&nbsp;<%=title %>인증 상세보기</td></tr>
	<tr><td colspan="2" id="date"></td></tr>
	
	<tr><td colspan="2">	
	<%--더미 데이터와 구분하기 위하여 --%>
		<%if(certification.getCeStoredName().contains("저장")){ %> 
			<img src="/resources/img/challenge.png" width="400" height="300"/>
		<%}else{ %>
			<img src="/upload/<%=certification.getCeStoredName() %>" width="400" height="300"/>
		<%} %>
	</td></tr>
	<tr>
	<%if(certification.getCeIsSuccess().equals("W")) {%>
	<td colspan="2">대기</td>
	<%}else if(certification.getCeIsSuccess().equals("Y")){ %>
	<td colspan="2" style="color:blue">성공</td>
	<%}else{ %>
	<td colspan="2" style="color:red">실패</td>
	<%} %>
	</tr>
	<tr><td colspan="2">인증 방법 <br><%=chWay %></td></tr>
</table>

<div class="text-center">
<button id="btnList" class="btn btn-primary">목록</button>
<%if("W".equals(certification.getCeIsSuccess())){%>
<button id="btnUpdate" class="btn btn-info">수정</button>
<button id="btnDelete" class="btn btn-danger">삭제</button>
<%}else{ %>
<button id="btnUpdate" class="btn btn-info disabled">수정</button>
<button id="btnDelete" class="btn btn-danger disabled">삭제</button>
<%} %>
</div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>