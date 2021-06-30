<%@page import="dto.Comment"%>
<%@page import="java.util.List"%>
<%@page import="dto.Post"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%Post post = (Post)request.getAttribute("post");
   	 String[] boardData = (String[])request.getAttribute("boardData");
   %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		url : "/comment",
		type : "GET",
		data : {pno : <%=post.getP_no() %>},
		dataType : "html",
		success : function(result){
			$("#comment").html(result);
		}
	});
	
	
	$("#btnInsert").click(function(){
		var login = <%=session.getAttribute("login")%>
		if(login){
			$.ajax({
				url : "/comment/insert",
				type : "POST",
				data : {content : $("#c_content").val(), pno: <%=post.getP_no()%>},
				success : function(){
					$.ajax({
						url : "/comment",
						type : "GET",
						data : {pno : <%=post.getP_no() %>},
						dataType : "html",
						success : function(result){
							$("#comment").html(result);
							$("#c_content").val('');
						}
					});
					//내부ajax끝
				}
				//success끝
			});
			//if문 이전
		}else{
			alert('로그인후 등록이 가능합니다');
			location.href="/member/login";
		}
	});
	
	
	$("#boardDelete").click(function(){
		var check = confirm('정말로 삭제 하시겠습니까?');
		if(check){
			$.ajax({
				url:"/board/delete",
				type:"POST",
				data:{pno:<%=post.getP_no()%>, bno:<%=boardData[0]%>},
				success:function(){
					location.replace("/board/" + <%=boardData[0]%>);	
				}
			});
		}
	});
	
})
</script>
<%@include  file="/WEB-INF/views/layout/bootHeader.jsp"%>
<%@include file="/WEB-INF/views/layout/navigation.jsp" %>
</head>
<body>
<div class="container">
	<h2 style="text-align:left;"><%=boardData[1] %></h2>
	
	<div class="content">
		<div class="head">
			<div class="post number"><span>No. <%=post.getP_no() %></span></div>
			<div class="post title"><span><%=post.getP_title() %></span></div>
		</div>
		<div class="head under" style="height:40px">
			<ul class="postUl">
				<li class="post nick"><%=post.getU_nick() %></li>
				<%if(session.getAttribute("u_id") != null){ %>
 				<%if(session.getAttribute("u_id").equals(post.getU_id())){ %> 
 				<li class="dele"><a id="boardDelete" style="cursor:pointer">삭제</a></li>
				<li class="upda"><a href="/board/update?mid=<%=boardData[0] %>&pno=<%=post.getP_no()%>">수정</a></li>
				<%} 
 				}%>
				<li class="post view">조회수 <span><%=post.getP_views() %></span></li>
				<li class="post date">작성일 <span><%=post.getP_create_date() %></span></li>
			</ul>
		</div>
		<br>
		<div class="body">
			<div><%=post.getP_content() %></div>
		</div>
		
		<br>
		<br>
		
	</div><!-- content div 끝 -->
	
	<div style="float:left">
		<%if(post.getP_stored_name() != null && !post.getP_stored_name().equals("null")) {%>
			<span>첨부파일</span>
			<a href="/upload/<%=post.getP_stored_name() %>"
 			download="<%=post.getP_origin_name() %>">
			<%=post.getP_origin_name() %></a>
			<br>
		<%} %>
	</div>
	
	<hr>
	
	<div id="comment" class="comment"><!-- 댓글 ajax로 불러올곳 --></div>
	
	
	<div>
		<h3 style="float:left;">댓글 쓰기</h3>
		<textarea id="c_content" style="width :900px; height:80px;"></textarea><br>
		<button id="btnInsert" style="float : right;">댓글 등록</button>
	</div>
	
	
	
</div><!-- 컨테이너 div끝 -->

<%@include file="/WEB-INF/views/layout/footer.jsp"%>
</body>
<style type="text/css">
.container{
	width:930px; 
	text-align:center; 
	margin:0 auto;
}
.head{
	border:1px solid #e5e5e5;
	position:relative;
}
.under{
	background-color:#f7f7f7;
	padding:10px;
	position:relative;
}
.number{
	position:absolute;
	left:10px;
	top:50%;
	transform: translateY(-50%);
}
.title{
	font-size:20px;
}
.dele,.upda{
	margin-left:15px;
}
.nick{
	position:absolute;
	left:15px;
	top:50%;
	transform: translateY(-50%);
}
.date{
	margin-left:15px;
}
.view{
	margin-left:15px;
}
.postUl{
	list-style:none;
	text-align:right;
}
.postUl>li{
	top:50%;
	float:right;
}

a{	color:#000;}
</style>

</html>