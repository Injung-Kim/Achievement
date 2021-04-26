<%@page import="dto.Challenge"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<Challenge> list = (List)request.getAttribute("challengeList"); %>
<% String category = (String)request.getAttribute("ch_category"); %>

<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<style type="text/css">
#tableHeader{
	/* header정의 코드 */
 	text-align: center;
 	width: 100%;
 	hegight: 70px;
 	margin: 15px auto;
 	display: flex;
 	flex-flow : row nowrap;
 	justify-content: space-between;

}
.container{
	width : 900px;
	height : 900px;
	padding : 15px;
	text-align : center;
}

#btnTitle{
	font-size:19px;
}
#chTitle{
	font-size:30px; 
	font-weight:bold;
}

.wrap {
	/* 내부 정렬 */
	width : 750px;
	height: 630px;
	/* 외부 정렬 */
	/* width: 900px; */
	margin: 0 auto;
	
}
.wrap > div{
	/* 인라인블록 요소로 설정하기 */
	/* display: inline-block; */
	
	/* height: 100px;
	padding: 10px; 
	margin: 20px;
	width:400px; */
}
.imgarr  img{
 	width : 250px;
 	height: 180px;	
	border-radius : 5px;
}
.imgarr  p{
	font-size : larger;
	margin: 10px 0 10px;
	color: #8C8C8C;
	font-weight: bold;
}

.imgarr{
	text-align:center;
	float: left;
	width: 300px;
    float: left;
    box-sizing: border-box;
    margin: 37px;
    /* border: 1px solid #ccc; */
}
.imgarr > a{
	/* 글자색상 */
	color: #8C8C8C;
	/* 글자 꾸밈선 없애기(underline) */
	text-decoration: none;
	/* 글자 스타일 지정 */
	margin: 0 5px;
}


</style>
<script type="text/javascript">
$(document).ready(function(){
 $('.imgarr  img').click(function(){
	 $(this).css("border","2px dotted #8C8C8C")
 })
 
})
</script>
<div class="container">
<div id="tableHeader">
	<div class="left" style="font-size:30px; font-weight:bold;">챌린지 목록 <a href="/user/challenge/write"><i class="fas fa-plus"></i></a></div>
	<!-- <div><span>&nbsp</span></div> -->	
	<form action="/user/challenge/list" method="get" class="form-inline">
	<div class="right">제목:&nbsp;<input type="text" id="title" name="title" class="form-control"/>
			<button id="btnTitle" class="btn"><i class="fas fa-search"></i></button>		
	</div>
	</form>

	<!-- <div><span>&nbsp</span></div> -->
</div>
<div><hr></div>
	<form>
	<div>
		<div id=chTitle class="text-center"><i class="far fa-thumbs-up"></i>&nbsp;&nbsp;<%if(category!=null) {%>
			<%=category %>
		<%} %>
		챌린지</div>
		
	</div>
	</form>


<div class="wrap">
	<%for(int i=0; i<list.size(); i++){ %>
		<div class="imgarr">
		<a href="/user/challenge/view?chNo=<%=list.get(i).getChNo()%>">
		
		<%if(list.get(i).getChStoredName().contains("저장")){ %>
			<img src="/resources/img/AchievementWhite.png"  height="150"/>
		<%}else{ %>
			<img src="/upload/<%=list.get(i).getChStoredName()%>" height="150" />
		<%} %>
		<p><%=list.get(i).getChTitle() %></p>
		</a>
		<a href="/user/challenge/review/list?chNo=<%=list.get(i).getChNo()%>"><button type="button" id="reviewBtn" class="btn btn-info" >리뷰보기</button></a></div>
	<%} %>
</div>

<%if(session.getAttribute("ch_user_title")==null) {%> <%--일반적인 페이징 --%> 
<%@ include file="/WEB-INF/views/layout/paging.jsp" %>
<%}else{ %> <%-- title이 존재 했을시 페이징--%>
<%@ include file="/WEB-INF/views/userChallenge/userChallengePaging.jsp" %>
<%} %>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>