<%@page import="dto.ChallengeList"%>
<%@page import="dto.Challenge"%>
<%@page import="java.util.List"%>
<%@page import="dto.ChallengeCategory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<ChallengeList> list = (List<ChallengeList>)request.getAttribute("newChallenges"); %>
<% List<Integer> participant = (List<Integer>)request.getAttribute("cntParticipant"); %>
<% List<Integer> like = (List<Integer>)request.getAttribute("cntLikes"); %>
<!DOCTYPE html>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
 $('.imgarea > img').click(function(){
	 $(this).css("border","2px dotted #8C8C8C")
 })
})
</script>
<style type="text/css">
.container{
	width : 900px;
	height : 900px;
	text-align : center;
	padding : 15px;
}
.participant{
	text-align : right;
	margin-right : 15pxs;
}
.wrapper{
	/* border : 1px solid #ccc; */
	width : 750px;
	height: 630px;
	margin : 0 auto;
}
.imgarea{
	width : 300px;
	float: left;
	box-sizing: border-box;
	margin : 37px;
	cursor : pointer;
	/* border : 1px solid #ccc; */
}
.imgarea > img{
 	width : 250px;
 	height: 180px;	
	border-radius : 5px;
}
.imgarea > p{
	font-size : larger;
	margin: 10px 0 10px;
}
.title{
	font-weight : bold;
	color : #8C8C8C;
}
.imgarea > .title::after{
	content:"new!";
	width: 5px;
	background: #A8201A;
	color:white;
	font-size:small;
	font-weight: bold;
	padding : 2px;
	margin-left: 5px;
	border-radius : 5px;
}
.people{
	margin-right : 20px;
}
</style>
<body>
<%@ include file="./subjects.jsp" %>
<div class="participant">
</div>
	<div class="wrapper">
		<%for(int i = 0; i< list.size(); i++){ %>
		<%if(list.get(i).getChStoredName()== null || list.get(i).getChStoredName().contains("저장")){ %>
		<div class = "imgarea" onclick = "location.href='/user/challenge/view?chNo=<%= list.get(i).getChNo()%>'">
			<img src="/resources/img/AchievementWhite.png" alt="챌린지 임시페이지"/>
			<p class="title"><%= list.get(i).getChTitle() %></p>
			<p class="status">
			<span class="people"><i class="fas fa-user"></i> <%= participant.get(i) %> </span>
	 		<span class="like"><i class="far fa-thumbs-up"></i> <%= like.get(i) %> </span>
			</p>
		</div>
		<%}else{ %>
		<div class = "imgarea"  onclick = "location.href='/user/challenge/view?chNo=<%= list.get(i).getChNo()%>'">
			<img src="/upload/<%=list.get(i).getChStoredName() %>" alt="챌린지 저장이미지" />
			<p class = "title"><%= list.get(i).getChTitle() %></p>
			<p class = "status">
			<span class="people"><i class="fas fa-user"></i> <%= participant.get(i) %></span>			
	 		<span class="like"><i class="far fa-thumbs-up"></i> <%= like.get(i) %></span>
			</p>
		</div>
		<%	} %>
	<%} %>
	</div>
<%@ include file="/WEB-INF/views/ChallengeList/pagings/newPaging.jsp" %>
</div>
</body>

<%@ include file="/WEB-INF/views/layout/footer.jsp"%>