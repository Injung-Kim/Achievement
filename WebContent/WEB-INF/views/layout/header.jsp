<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
<link rel="stylesheet" href="/resources/css/mainPageTest.css">
<script type="text/javascript" src="/resources/js/mainPage.js"></script>

<style type="text/css">
#headerCon{
	position: relative;
	width: 900px;	
}

#headerImg{
	float: left;
	width: 230px;
	height: 100px;
}

/* 검색창 전체 div */
#headerForm{
	float: left;
	width: 350px;
	height: 100px;
	margin-left: 80px;
}

/* 검색창 폼 위치  */
#headerForm > form{
	position:relative;
	padding-top: 20px;
	padding-bottom: 30px;

}

/* 검색창 */
#headerForm > form > input{
	width: 85%;
	padding: 10px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 2px solid #EC9A29;
	border-radius: 5px;
	box-sizing: border-box;
	
}

/* 검색창 버튼 */
#headerForm > form > button{
	position:absolute;
	width: 35px;
	height: 35px;
	border: none;
	margin-top: 27px;
	margin-right:3px;
	background-color: #EC9A29; 
	border-radius: 5px; 
	font-size: 15px;
	color: #fff;
	cursor: pointer;
}
      

/* 마이페이지 로고 및 Two Depth 설정 */
#headerLoginDiv{
	position: absolute;
	float: left;	
	width: 200px;
	height: 100px;
	margin-left: 20px;
	padding-top: 15px;
	margin-left: 730px;
	color: #143642;
}


#headerLoginDiv ul ul {
	display:none; /* 안보이게  */
	position: absolute; /* 위치는 겹쳐지게 */
}

#headerLoginDiv ul li:hover ul {
	display:block; /* 호버시 보이게 */
	margin-left: 30px; /*리스트 옆으로 밀기  */
	text-align: left;
	padding-left: 15px;
	border-left: 10px solid #143642;
}

#headerLoginDiv ul{
	list-style-type:none;
}

#headerLoginDiv ul ul li {
	list-style-type:none;
	
}

#headerLoginDiv ul> li > ul > li > a{
	/* 배경색 지정 */
	background: #FFFFFF;	
	/* 글자색 지정 */
	color: #333;	
	/* 꾸밈선 제거(underline) */
	text-decoration: none;
	/* 부모요소<li>항목을 자식요소 <a>로 가득 채우기 */
	display: block; 
	/* 변화 시작, 종료 둘 다 적용 */
	/* transition: background 2s, color 1s; */
	font-size: 18px;
	padding-top: 4px;
}

#headerLoginDiv ul> li > ul > li > a:hover{
	color:#EC9A29;
}

/*----------------------------------------------  */

</style>


</head>
<body>
<header>
<div id="headerCon">
	
	<div id="headerImg">
		<a href="/"><img alt="메인로그" src="/resources/img/AchievementWhite.png" width="230" height="100"></a>
	</div>
	
	<div id="headerForm">
		<form action="#" method="get" id="searchForm">
			<input type="text" id="search" name="search" />
			<button class="startBtn"><i class="fas fa-search"></i></button>
		</form>
	</div>	
	
	<div id="headerLoginDiv">
	
	<ul>
		<li>
			<a href="#" class="fa fa-user fa-3x"></a>
			<%if(session.getAttribute("login")==null || !(boolean)session.getAttribute("login")){ %>
			<!-- 비로그인 상태 -->
			<ul>
				<li><a href="/member/login">로그인</a></li>
				<li><a href="/member/join">회원가입</a></li>
			</ul>
			<%}else if((boolean)session.getAttribute("login") && "U".equals((String)session.getAttribute("u_grade"))) {%>
				<ul>
					<li><a href="/member/logout">로그아웃</a></li>
					<li><a href="/mypage/home">마이페이지</a></li>
				</ul>
			<%} %>
		</li>
	</ul>	
	</div>
	
</div>
</header>
