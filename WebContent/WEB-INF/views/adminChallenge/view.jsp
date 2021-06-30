<%@page import="java.util.Map"%>
<%@page import="dto.Challenge"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
<% Challenge challenge= (Challenge)request.getAttribute("challenge"); %>
<% Map<String, String> result= (Map<String, String>)request.getAttribute("result"); %>
<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>
<script type="text/javascript">
$( document ).ready( function() {
	//background
	$('#challenge').css({"background":"#EC9A29"})
	
	
	//목록버튼 동작
	$("#btnList").click(function() {
		$(location).attr("href", "/admin/challenge/list");
	});
	
	//수정버튼 동작
	$("#btnUpdate").click(function() {
		$(location).attr("href", "/admin/challenge/update?chNo=<%=challenge.getChNo() %>&chState=<%=challenge.getChState()%>");
	});

	//삭제버튼 동작
	$("#btnDelete").click(function() {
		
		if( confirm("챌린지를 삭제하시겠습니까?") ) {
			$(location).attr("href", "/admin/challenge/delete?chNo=<%=challenge.getChNo() %>&chState=<%=challenge.getChState()%>");
		}
		
	});
})

</script>
<style type="text/css">
td{
	height:80px;
	vertical-align:middle !important;
}
talbe, th, td{
	border:none !important;
	font-size: 20px;
}


</style>
<div class="container">
	<table class="table table-responsive text-center">
		<tr><td rowspan="5"><a href="/participant/list"><i class="fas fa-user-friends fa-10x"></i></a><br>참가자</td><td colspan="2">
		<%--더미 데이터와 구분하기 위하여 --%>
		<%if(challenge.getChStoredName().contains("저장")){ %> 
			<img src="/resources/img/AchievementWhite.png" width="400" height="250"/>
		<%}else{ %>
			<img src="/upload/<%=challenge.getChStoredName() %>" width="400" height="250"/>
		<%} %>
		</td></tr>
		<tr><td>챌린지 번호</td><td><%=challenge.getChNo() %></td></tr>
		<tr><td>제목</td><td><%=challenge.getChTitle() %></td></tr>
		<tr><td>카테고리</td><td><%=result.get("category") %></td></tr>
		<tr><td>내용</td><td><%=challenge.getChContent() %></td></tr>
		<%	if( result.get("u_id").equals( (String)session.getAttribute("u_id")) || "M".equals(session.getAttribute("u_grade"))){ %>
		<tr><td rowspan="5"><a href="/founder/certification/list"><i class="far fa-id-card fa-10x"></i></a><br>인증</td><td >참가비</td><td><%=challenge.getChMoney() %></td></tr>
		<%}else{ %>
		<tr><td rowspan="5"><i class="far fa-id-card fa-10x"></i><br>인증</td><td >참가비</td><td><%=challenge.getChMoney() %></td></tr>
		<%} %>
		<tr><td>개설자</td><td><%=result.get("name") %></td></tr>
		<tr><td >개설날짜</td><td><%=challenge.getChCreateDate() %></td></tr>
		<tr><td >시작날짜</td><td><%=challenge.getChStartDate() %></td></tr>
		<tr><td >마감날짜</td><td><%=challenge.getChEndDate() %></td></tr>
		<%	if( result.get("u_id").equals( (String)session.getAttribute("u_id")) || "M".equals(session.getAttribute("u_grade"))){ %>
		<tr><td rowspan="5"><a href="/founder/reward/distribution"><i class="fas fa-money-bill-wave fa-10x"></i></a><br>상금분배</td><td >인증빈도</td><td><%=result.get("title") %></td></tr>
		<%}else{ %>
		<tr><td rowspan="5"><i class="fas fa-money-bill-wave fa-10x"></i><br>상금분배</td><td >인증빈도</td><td><%=result.get("title") %></td></tr>
		<%} %>
		<tr><td >인증 가능 시간</td><td><%=challenge.getChStartTime() %> - <%=challenge.getChEndTime() %></td></tr>
		<tr><td >인증 방법</td><td><%=challenge.getChWay() %></td></tr>
		<tr><td >좋아요</td><td><%=challenge.getChLikes() %></td></tr>
		<tr><td >신고</td><td><%=challenge.getChCaution() %></td></tr>
	</table>
	
<div class="text-center">	
<button id="btnList" class="btn btn-primary">목록</button>

<%	if( result.get("u_id").equals( (String)session.getAttribute("u_id")) || "M".equals((String)session.getAttribute("u_grade"))){ %>
	<%--챌린지 시작전 --%>
	<%if("W".equals(challenge.getChState())){ %>
	<button id="btnUpdate" class="btn btn-info">수정</button>
	<button id="btnDelete" class="btn btn-danger">삭제</button>
	<%--챌린지 시작후 --%>
	<%}else{ %>
	<button id="btnUpdate" class="btn btn-info" disabled="disabled">수정</button>
	<button id="btnDelete" class="btn btn-danger" disabled="disabled">삭제</button>
	<%} %>
<%	} %>
</div>

</div>


<%@ include file="/WEB-INF/views/layout/footer.jsp" %>