<%@page import="dto.Participation"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<%List<Participation> list = (List<Participation>)request.getAttribute("reviewList"); %>
<div class="container">
<div id="tableHeader">
	<div><span>&nbsp</span></div>
	<div class="left" style="font-size:30px; font-weight:bold;">리뷰 보기 </div>
	<div><hr></div>
</div>


<table class="table table-striped table-hover table-condensed text-center">
<tr>
	<th style="width: 20%; text-align:center;">참여자 번호</th>
	<th style="width: 20%; text-align:center;">리뷰 내용</th>
	<th style="width: 20%; text-align:center;">참여 날짜</th>
	<th style="width: 20%; text-align:center;">좋아요 여부</th>
	<th style="width: 20%; text-align:center;">성공 여부</th>
</tr>
<%	for(int i=0; i<list.size(); i++) { %>
<tr>
	<td><%=list.get(i).getPaNo() %></td>
	<td><a href="/user/challenge/review/view?paNo=<%=list.get(i).getPaNo() %>">
	<%if(list.get(i).getPaReview().length()>=6){%>
		<%=list.get(i).getPaReview().substring(0,6)+"..."%>	
	<%}else{ %>
		<%=list.get(i).getPaReview()%>	
	<%} %>
	</a>
	</td>
	<td><%=list.get(i).getPaCreateDate() %></td>
	<%if("Y".equals(list.get(i).getPaLike())) {%>
		<td>누름</td>
	<%}else{ %>
		<td>누르지 않음</td>
	<%} %>
	<%if("Y".equals(list.get(i).getPaIsSuccess())){ %>
	<td>성공</td>
	<%}else{ %>
	<td>실패</td>
	<%} %>	
</tr>
<%	} %>
</table>


</div>


<%@ include file="/WEB-INF/views/userChallenge/reviewPaging.jsp" %>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>