<%@page import="dto.ChallengeList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%List<ChallengeList> challenge = (List<ChallengeList>)request.getAttribute("DetailSearch"); %>
<script type="text/javascript">
$(document).ready(function(){
	$('.imgarea  img').click(function(){
	 	 $(this).css("border","2px dotted #8C8C8C")
	 })
})
</script>
<div class="result">
<% for(int i = 0; i< challenge.size(); i++){ %>	
	<%if(challenge.get(i).getChTitle() != null){ %>
	<div class = "imgarea" >
	<%if(challenge.get(i).getChStoredName().contains("저장")){ %>
		<a class="scroll" href="/user/challenge/view?chNo=<%=challenge.get(i).getChNo()%>"><img src="/resources/img/AchievementWhite.png" width="225px" height="225px" alt="챌린지 임시페이지"/></a>
	<%} else {%>
		<a class="scroll" href="/user/challenge/view?chNo=<%=challenge.get(i).getChNo()%>"><img src="/upload/<%=challenge.get(i).getChStoredName() %>" alt="챌린지 저장페이지"/></a>
	<%} %>
			<p class="title"><%=challenge.get(i).getChTitle() %></p>
			<p class="status">
			<%if(challenge.get(i).getuId().contains("manager")){ %>
			<span class="name"><i class="fas fa-cog"></i>공식</span>
			<%}else{ %>
			<span class="name"><%=challenge.get(i).getuId() %> </span>
			<%} %>
	 		<span class="like"><i class="far fa-thumbs-up"></i><%=challenge.get(i).getChLikes() %></span>
			</p>
	</div>
	<%} else{ %>
	<%} %>
<%} %>	
	<%@ include file="/WEB-INF/views/userSearch/searchPaging/detailPaging.jsp" %>
	</div>