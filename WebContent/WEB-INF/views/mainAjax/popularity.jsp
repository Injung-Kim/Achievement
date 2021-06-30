<%@page import="dto.Users"%>
<%@page import="dto.Challenge"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%List<Challenge>list = (List<Challenge>)request.getAttribute("popularList"); %>
<%List<Users>userList = (List<Users>)request.getAttribute("userList"); %>
<%int rownum = 1; %>
<link rel="stylesheet" href="/resources/css/ajaxStyle(new).css">
<div class = "container">
<div class="ajaxImage">
<%for(int i= 0; i< list.size(); i++) { %>
	<div class="imgarr">
<!--  	이미지가 저장되지 않았을 경우 기본 이미지 대체-->
<%	if (list.get(i).getChStoredName()== null || list.get(i).getChStoredName().contains("저장")){ %>
		<!--이미지 정보 -->
		<a href="/user/challenge/view?chNo=<%= list.get(i).getChNo() %>">
		<img src="./resources/img/AchievementWhite.png" width="100%" height="150px;"/>
		<p class="title"><%= list.get(i).getChTitle() %></p></a>
		
		<!--챌린지 정보 -->
		<p class="status">
		<%if(userList.get(i).getGrade().equals("U")){ %><!--개설자가 유저일 경우  -->
		<span class ="publisher"><%=userList.get(i).getUserNick() %></span>
		<%}else if(userList.get(i).getGrade().equals("M")){ %><!--개설자가 관리자일 경우  -->
		<span class="publisher"><i class="fas fa-cog"></i>공식</span>
		<%} %>
		<span><i class="far fa-thumbs-up"></i> <%= list.get(i).getChLikes() %></span></p>
<!--  	이미지가 저장되었을 경우 저장 이미지 대체-->
<% 	} else {%>		
		<!--이미지 정보 -->
		<a href="/user/challenge/view?chNo=<%= list.get(i).getChNo() %>">
		<img src="/upload/<%=list.get(i).getChStoredName() %>" width="100%" height="150px;"/>
		<p class="title"><%=list.get(i).getChTitle() %></p></a>
		
		<!--챌린지 정보 -->
		<p class="status">
		<%if(userList.get(i).getGrade().equals("U")){ %><!--개설자가 유저일 경우  -->
		<span class ="publisher"><%=userList.get(i).getUserNick() %></span>
		<%}else if(userList.get(i).getGrade().equals("M")){ %><!--개설자가 관리자일 경우  -->
		<span class="publisher"><i class="fas fa-cog"></i>공식</span>
		<%} %>
		<span><i class="far fa-thumbs-up"></i> <%= list.get(i).getChLikes()%></span></p>
<%		} %>
	</div>
<%	} %>
</div>
	<div class="ajaxText">
<%for(int i= 0; i< list.size(); i++) { %>
	<div class="textarr"><a href="/user/challenge/view?chNo=<%= list.get(i).getChNo() %>"><%= rownum %>.<%=list.get(i).getChTitle() %></a></div>
<% rownum++; } %>
	</div>
</div>






