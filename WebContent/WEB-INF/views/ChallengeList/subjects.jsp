<%@page import="dto.ChallengeCategory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%ChallengeCategory category = (ChallengeCategory)request.getAttribute("ca_title"); %>
<%int num = Integer.parseInt(request.getParameter("subject")); %>
<div class = "container">
<% if(num == 0){ %>
<h1><span class="fas fa-dumbbell fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num == 1){ %>
<h1><span class="fas fa-dumbbell fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num == 2){ %>
<h1><span class="fas fa-trophy fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num==3){ %>
<h1><span class="fas fa-user-graduate fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num==4){ %>
<h1><span class="fas fa-book-reader fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num==5){ %>
<h1><span class="fas fa-walking fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num==6){ %>
<h1><span class="fas fa-biking fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num==7){ %>
<h1><span class="fas fa-language fa-sm"></span> <%=category.getTitle() %></h1>
<%}else if(num==8){ %>
<h1><span class="fas fa-won-sign fa-sm"></span> <%=category.getTitle() %></h1>
<%} %>
<hr>
