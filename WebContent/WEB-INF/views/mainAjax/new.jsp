<%@page import="java.util.Date"%>
<%@page import="dto.Challenge"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/css/ajaxStyle(new).css">
<%List<Challenge>newList = (List<Challenge>)request.getAttribute("newList"); %>
<%List<Long> datediff = (List<Long>)request.getAttribute("datediff"); %>
<%int rownum = 1; %>
<style type="text/css">
.title{
	margin : 0;
}
.imgarr > a::after{
	content: "NEW";
	color: #EC9A29;
}
</style>
<div class = "container">
<div class="ajaxImage">
<%for(int i= 0; i< newList.size(); i++) { %>
<!--  	이미지가 저장되지 않았을 경우 기본 이미지 대체-->
	<div class="imgarr">
<%	if (newList.get(i).getChStoredName()== null || newList.get(i).getChStoredName().contains("저장")){ %>
		<a href="/user/challenge/view?chNo=<%= newList.get(i).getChNo() %>">
		<img src="/resources/img/AchievementWhite.png" width="100%" height="150px;"/>
		<p class="title"><%= newList.get(i).getChTitle() %></p></a>
		<p class="status">
		<span style="color:#143642">
		<%if(datediff.get(i) == 0){ %>
		오늘 등록
		<%}else if(datediff.get(i) == 1){%>
		어제 등록
		<%}else{ %>
		한달 내 등록
		<%} %>
		</span></p>
<% 	} else {%>		
		<a href="/user/challenge/view?chNo=<%= newList.get(i).getChNo() %>">
		<img src="/upload/<%=newList.get(i).getChStoredName() %>" width="100%" height="150px;"/>
		<p class="title"><%=newList.get(i).getChTitle() %></p></a>
		<p class="status">
		<span style="color: #143642">
		<%if(datediff.get(i) == 0){ %>
		오늘 등록
		<%}else if(datediff.get(i) == 1){%>
		어제 등록
		<%}else{ %>
		한달 내 등록
		<%} %></span></p>
<%		} %>
	</div>
<%	} %>
</div>
	<div class="ajaxText">
<%for(int i= 0; i< newList.size(); i++) { %>
	<div class="textarr"><a href="/user/challenge/view?chNo=<%= newList.get(i).getChNo() %>"><%= rownum %>.<%=newList.get(i).getChTitle() %></a></div>
<% rownum++; } %>
</div>