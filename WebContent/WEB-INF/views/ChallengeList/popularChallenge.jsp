<%@page import="dto.ChallengeList"%>
<%@page import="dto.Challenge"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<ChallengeList> popularList = (List<ChallengeList>)request.getAttribute("popularList"); %>
<% List<ChallengeList> popParticipant = (List<ChallengeList>)request.getAttribute("popParticipant"); %>

<!DOCTYPE html>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>

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
	/* border : 1px solid #ccc; */
	cursor: pointer;
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
	content:"인기";
	width: 5px;
	background: #307F9C;
	color:white;
	font-size:small;
	font-weight: bold;
	padding : 2px;
	margin-left: 5px;
	border-radius : 5px;
}
.publisher{
 	color: #8C8C8C;
	margin-right: 20px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
 $('.imgarea > img').click(function(){
	 $(this).css("border","2px dotted #8C8C8C")
 })
})
</script>
<body>
<%@ include file="./subjects.jsp" %>

	<div class="wrapper">
		<%for(int i = 0; i< popularList.size(); i++){ %>
		<%if(popularList.get(i).getChStoredName()== null || popularList.get(i).getChStoredName().contains("저장")){ %>
		<div class = "imgarea" onclick="location.href='/user/challenge/view?chNo=<%=popularList.get(i).getChNo()%>'">
			<img src="/resources/img/AchievementWhite.png" alt="챌린지 임시페이지"/>
			<p class="title"><%= popularList.get(i).getChTitle() %></p>
			<p class="status">
			<%if(popularList.get(i).getuGrade().equals("U")){ %>
			<span class="publisher"><%=popularList.get(i).getuId() %></span>
			<%}else{ %>
			<span class="publisher"><i class="fas fa-cog"></i>공식</span>
			<%} %>			
	 		<span class="like"><i class="far fa-thumbs-up"></i> <%= popularList.get(i).getChLikes() %> </span>
			</p>
		</div>
		<%}else{ %>
		<div class = "imgarea" onclick="location.href='/user/challenge/view?chNo=<%=popularList.get(i).getChNo()%>'">
			<img src="/upload/<%=popularList.get(i).getChStoredName() %>" alt="챌린지 저장이미지" />
			<p class = "title"><%= popularList.get(i).getChTitle() %></p>
			<p class = "status">
			<%if(popularList.get(i).getuGrade().equals("U")){ %>
			<span class="publisher"><%=popularList.get(i).getuId() %></span>
			<%}else{ %>
			<span class="publisher"><i class="fas fa-cog"></i>공식</span>
			<%} %>	
	 		<span class="like"><i class="far fa-thumbs-up"></i> <%= popularList.get(i).getChLikes() %> </span>
			</p>
		</div>
		<%	} %>
	<%} %>
	</div>
<%@ include file="../ChallengeList/pagings/popularPaging.jsp" %>
</div>
</body>

<%@ include file="/WEB-INF/views/layout/footer.jsp"%>