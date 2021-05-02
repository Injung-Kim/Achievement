<%@page import="dto.Users"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<Users> list = (List)request.getAttribute("userlist"); %>
<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>

<script type="text/javascript">
$(document).ready(function(){

	$('#member').css({"background":"#EC9A29"})
	$('#member').nextAll().css({"background":"#FFFFFF"})
})

</script>

<body>
<!-- 최고 관리자(manager1) 관리자 생성 권한부여  -->
<div class="content">
<div class="left" style="font-size:30px; font-weight:bold;">회원 목록</div>
<div><span>&nbsp</span></div>

<fieldset class="search">
<form>
<div class="form-group">
	<button id="btnSearch">검색</button>
</div>

<div class="form-group">
	<label for="search" >
	<input type="text" id="search" name="search" class="select textbox"/>
	</label>
</div>

<div class="form-group">
	<select name="grade" class="contoller">
		<option value="U">회원</option>	
		<option value="M">관리자</option>	
	</select>
</div>
<div class="form-group">
	<select name="field" class="contoller">
		<option value="u_id">아이디</option>
		<option value="u_nick">닉네임</option>
	</select>
</div>


</form>
</fieldset>
<table class="table table-striped table-hover table-condensed" id="move">
<tr>
	<th style="width:5%">No.</th>
	<th style="width:5%">등급</th>
	<th style="width:10%">이름</th>
	<th style="width:15%">아이디</th>
	<th style="width:10%">닉네임</th>
	<th style="width:5%">성별</th>
	<th style="width:15%">생일</th>
	<th style="width:15%">가입일</th>
	<th style="width:20%">이메일</th>
</tr>
	<%for(int i = 0; i< list.size(); i++){ %>
<tr>
	<td><%=list.get(i).getUserNo() %></td>
	<td><%=list.get(i).getGrade()%></td>	
	<td><%=list.get(i).getName() %></td>
	<td><a class="view" href="/admin/user/view?userno=<%=list.get(i).getUserNo() %>" ><%=list.get(i).getUserId() %></a></td>
	<td><%=list.get(i).getUserNick() %></td>
	<td><%=list.get(i).getGender() %></td>
	<td><%=list.get(i).getBirth() %></td>
	<td><%=list.get(i).getSignupDate() %></td>
	<td><%=list.get(i).getUserEmail() %></td>
</tr>
	<%} %>
</table>
</div>

</body>
<style type="text/css">

.content{
 width : 900px;
 margin : 0 auto;

}
.search{
	border : none;
}

.form-group{	
	float:right;
	margin:0px 3px 5px 3px;
}

.btn{
background-color: #143642;
color: white;
border-radius: 4px;
}




.text-center{
	width: 900px;
	margin:0 auto;
}
.pagination{
	text-align : center;
	box-sizing: border-box;
	padding : 20px;
}
.pagination button{
	all : unset;
	margin : 0;
	cursor: pointer;
	padding : 2px 10px 2px 10px;
	color : #666666;
}
.pagination .disabled{
	display: none;
}
.pagination .active{
	border : 1px solid #EC9A29;
	border-radius : 100%;
}

th{
background-color: #A8201A;
color : white;
}

#move:hover tbody tr:hover td{
background:#143642;
color:#FFFFFF;
}

#btnSearch{
background-color: #143642;
color: white;
border-radius: 4px;
}
</style>
<%@ include file="/WEB-INF/views/admin/userPaging.jsp" %>
<br>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>

