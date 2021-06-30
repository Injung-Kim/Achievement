<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>405ERROR</title>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<body>
	<div id="container404">
		
		<img alt="" src="/resources/img/AchievementWhite.png">
		<br>
		<div>
		<h1>405</h1>
		<h2> Error 발생! </h2>
		<h4> 현재 페이지에는 해당 요청 방법을 지원하지 않습니다.</h4>
		<a href="/"><button id="gotoHome">메인페이지로 이동</button></a>
		</div>
	</div>
</body>

<style type="text/css">

#container404{/* 전체영역  */
	text-align: center;
}

#container404 > img{/*이미지영역 */
	width: 380px;
	height: 180px;
}

#container404 > div{ /* 텍스트 영역  */
	display:inline-block;
	width:700px;
}

#container404 > div > h1{ /* 404 텍스트 */
	margin-top: 0px;
	margin-bottom : 0px;
	font-size: 100px;
}

#gotoHome{ /* 버튼 */
  border: none;
  outline: 0;
  display: inline-block;
  padding: 8px;
  color: white;
  background-color: #000;
  text-align: center;
  cursor: pointer;
  width: 30%;
  font-size: 15px;
  border-radius: 30px;
}

#gotoHome:hover{ /* 버튼 호버  */
  background-color: #EC9A29;
  font-style: italic;
}
</style>

</html>