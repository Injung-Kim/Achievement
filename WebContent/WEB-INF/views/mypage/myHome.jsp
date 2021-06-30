<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="dto.Challenge"%>
<%@page import="dto.Mypage"%>
<%@page import="dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ include file="/WEB-INF/views/layout/navigation.jsp" %>
<%@ include file="/WEB-INF/views/layout/session/sessionCheck.jsp" %>

<%
	Member memberInfo =(Member) request.getAttribute("memberInfo");
	Mypage mypageInfo = (Mypage) request.getAttribute("mypageInfo");
	int refundsTotal = (int)request.getAttribute("refundsTotal"); //누적 총상금
	int paymentTotal = (int)request.getAttribute("paymentTotal"); //누적 결재액
%>

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
<script src="https://www.gstatic.com/charts/loader.js"></script>



<script type="text/javascript">
$(document).ready(function(){
	$("#contentDiv").load("/mypage/info") //첫 로드 화면

	
	$("#my1").click(function(){
		$("#contentDiv").load($(this).attr("href")) //메뉴 구현
		
		$("#my1").css({
			"text-decoration" : "underline"
			,"font-weight" : "bolder"
		})	
		$("#my2, #my3, #my4").css({
			"text-decoration" : "none"
				,"font-weight" : "normal"
		})
		
		return false;
	})
	$("#my2").click(function(){
		$("#contentDiv").load($(this).attr("href")) //메뉴 구현
		
		$("#my2").css({
			"text-decoration" : "underline"
			,"font-weight" : "bolder"
		})	
		$("#my1, #my3, #my4, #my5").css({
			"text-decoration" : "none"
				,"font-weight" : "normal"
		})
		
		return false;
	})
	$("#my3").click(function(){
		$("#contentDiv").load($(this).attr("href")) //메뉴 구현
		
		$("#my3").css({
			"text-decoration" : "underline"
			,"font-weight" : "bolder"
		})	
		$("#my1, #my2, #my4, #my5").css({
			"text-decoration" : "none"
				,"font-weight" : "normal"
		})
		
		return false;
	})
	$("#my4").click(function(){
		$("#contentDiv").load($(this).attr("href")) //메뉴 구현
		
		$("#my4").css({
			"text-decoration" : "underline"
			,"font-weight" : "bolder"
		})	
		$("#my1, #my2, #my3, #my5").css({
			"text-decoration" : "none"
				,"font-weight" : "normal"
		})
		
		return false;
	})
	$("#my5").click(function(){
		$("#contentDiv").load($(this).attr("href")) //메뉴 구현
		
		$("#my5").css({
			"text-decoration" : "underline"
			,"font-weight" : "bolder"
		})	
		$("#my1, #my2, #my3, #my4").css({
			"text-decoration" : "none"
				,"font-weight" : "normal"
		})
		
		return false;
	})
	
	
})
</script>

<style type="text/css">
/* 마이페이지 전체 영역  */ 
#container{
	width: 900px;
	height: 900px;
	margin: 0 auto;
		
} 

/*-------------------------  */


/* 마이페이지 카테고리/프로필 영역 css  */
#categoryDiv{
    width: 30%;
    float: left;
    box-sizing: border-box;

}

/* 프로필  */

.imgDiv{/*프로필 사진 감싸는 div  */
	width: 200px;
	heigth: 110px;
	box-sizing: border-box;
	text-align: center;	 
}

.profileImg{/*프로필 이미지 속성  */
	margin-top: 10px;
	width: 60%;
	height: 80%;
	border-radius: 30px;
}

.profileCard {
  box-shadow: 4px 4px 8px 4px rgba(0, 0, 0, 0.2);
  width: 200px;
  margin: 5px 0px;
  text-align: center;
  
}
.profileCard .title {
  color: grey;
  font-size: 18px;
}

#mPointInput{ /* 인풋창 내부  */
  width: 80%;
  padding: 4px 7px;
  margin: 2px 0;
  display: inline-block;
  border: 2px solid #EC9A29;
  border-radius: 4px;
  box-sizing: border-box;
}



#goToChall{
  border: none;
  outline: 0;
  display: inline-block;
  padding: 8px;
  color: white;
  background-color: #000;
  text-align: center;
  cursor: pointer;
  width: 100%;
  font-size: 15px;
}

#usePoint{
  border: none;
  outline: 0;
  padding: 5px;
  color: white;
  background-color: #000;
  text-align: center;
  cursor: pointer;
  width: 20%;
  font-size: 12px;
  border-radius: 5px;
}


#goToChall:hover, #usePoint:hover{
  opacity: 0.7;
}

/* 카테고리  */
#myCategory {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: 200px;
    background-color: #DAD2D8;
}
#myCategory li a {
    display: block;
    color: #000;
    padding: 8px 16px;
    text-decoration: none;
}
#myCategory  li a.myHome {
    background-color: #EC9A29;
    color: white;
} 

#myCategory li a:hover {
    background-color: #143642;
    color: white;
}
/* ------------------------------*/

/* 마이페이지 제목 영역 */
#mypageTitle{
	margin: 0px auto;
	text-align: left;
	border-bottom: 2px solid #ccc;
}

#mypageTitle > div{ /* 마이페이지 텍스트  */
	font-size: 35px;
	margin-bottom: 10px;
}

/* 마이페이지 내용 영역 */
#contentDiv{
    width: 70%;
    float: right;
    box-sizing: border-box;
    boder: 1px solid #ccc;
}

/* ------------------------------*/
</style>

<div id="container">
	<div id="mypageTitle"> 
	<div><strong>마이페이지</strong></div>
	</div>
	
	<!--마이페이지 프로필/카테고리  -->
	<div id="categoryDiv">
		<!--마이페이지 프로필  -->
		<div class="profileCard">
		 <% File proImg = new File(request.getServletContext().getRealPath("upload") + "\\" + mypageInfo.getmStoredname()); %>
		  <% if(mypageInfo.getmStoredname() != null && proImg.exists()) {%>
			<div class="imgDiv"><img src="/upload/<%=mypageInfo.getmStoredname() %>" class="profileImg" ></div>
			<% } else {%>
			<div class="imgDiv"><img src="/resources/img/logoRed.png" style="width:200px;" class="profileImg"></div>
			<% } %>
		 
		 <h2><%=memberInfo.getNick() %></h2>
		 
		 <span>Total Reword: <%=refundsTotal %> &#8361;</span>
		
		<!-- 포인트 사용을 위한 폼 영역  --> 
		 <form action="/user/use/point/list" method="get">		 	
		 	<div style="text-align: center; margin-top: 10px;"><strong style="color:#EC9A29">포인트</strong> <button id="usePoint">사용</button></div>	 
		 </form>
 		 <!-----------------------------  --> 
 		  
 		  <p><a href="/user/challenge/list"><button id="goToChall">Go to Challenge</button></a></p>
			
		</div>
			
		<!--마이페이지 카테고리  -->
		<ul id="myCategory">
			<li><a href="/mypage/info" class="myHome" id= "my1">Home</a></li>
			<li><a href="/mypage/update" id= "my2">회원정보수정</a></li>
			<li><a href="/mypage/delete" id= "my3">회원탈퇴</a></li>
			<li><a href="/mypage/challenge"id= "my4">회원목표</a></li>
			<li><a href="/mypage/reword"id= "my5">회원상금</a></li>
		</ul>
	</div>	
	<!-- -------------------- -->
	
	
	<div id="contentDiv">
	</div>	
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>