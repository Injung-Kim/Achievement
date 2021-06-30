<%@page import="dto.Users"%>
<%@page import="dto.Challenge"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/css/ajaxStyle(new).css">
<%List<Object>list = (List<Object>)request.getAttribute("challengeList"); %>
<%List<Users> users = (List<Users>)request.getAttribute("userList"); %>
<%int listnum = 0; %><!--유저정보를 가져올 일련번호  -->
<%int rownum = 1; %><!-- txtarr일련번호  -->
<div class = "container">
<div class="ajaxImage">
<%for(int i= 0; i< list.size(); i++) { %>
<%	if(i%2 == 0){ %> <!--챌린지 정보 가져오기  -->
<%		Challenge challenge = (Challenge)list.get(i); %>
			<div class="imgarr">
<!--  	이미지가 저장되지 않았을 경우 기본 이미지 대체-->
<%		if (challenge.getChStoredName()== null || challenge.getChStoredName().contains("저장")){ %>
			<a href="/user/challenge/view?chNo=<%= challenge.getChNo() %>">
			<img src="./resources/img/AchievementWhite.png" style="width : 100%; height: 150px; border-radius:5px;"/>
			<p class="title"><%=challenge.getChTitle() %></p></a>
<!--  	이미지가 저장되었을 경우 저장된 이미지 대체-->
<% 		} else {%>		
			<a href="/user/challenge/view?chNo=<%= challenge.getChNo() %>">
			<img src="/upload/<%=challenge.getChStoredName() %>" style="width : 100%; height: 150px; border-radius:5px;"/>
			<p class="title"><%=challenge.getChTitle() %></p></a>			
<%		} %>
<%  } else if( i%2 == 1){%><!--참여자 수 가져오기  -->
<% 		int cntParticipant = (Integer)list.get(i);%>
		<p class="status">
		<!--개설자가 유저일 경우  -->
		<%if(users.get(listnum).getGrade().equals("U")){ %>
		<span class="publisher"><%=users.get(listnum).getUserNick() %></span>
		<span><i class="fas fa-user"></i> <%= cntParticipant %> 참여중</span>
		<!--개설자가 관리자일 경우  -->
		<%}else if(users.get(listnum).getGrade().equals("M")){ %>
		<span class="publisher"><i class="fas fa-cog"></i>공식</span>
		<span><i class="fas fa-user"></i> <%= cntParticipant %> 참여중</span>
		<%} listnum++; %>
		</p>
	</div>
<%	} %>
<%} %>
</div>
<div class="ajaxText">
<%for(int i= 0; i< list.size(); i++) { %>
<%	if(i%2 == 0){ %>
<%		Challenge challenge = (Challenge)list.get(i); %>
	<div class="textarr"><a href="/user/challenge/view?chNo=<%= challenge.getChNo() %>"><%= rownum %>.<%=challenge.getChTitle() %></a></div>
<%	rownum++; } %>
<%} %>
</div>
</div>