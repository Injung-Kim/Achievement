<%@page import="dto.Mypage"%>
<%@page import="dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ include file="/WEB-INF/views/layout/session/sessionCheck.jsp" %>    
    
<%
	Member memberInfo =(Member) request.getAttribute("memberInfo");
	Mypage mypageInfo =(Mypage) request.getAttribute("mypageInfo");
%>
<!-- 구글 차트 스크립트 코드  -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
<!-- <script src="https://www.gstatic.com/charts/loader.js"></script> -->
<!-- --------------------------  -->


<!-- 구글 차트 구현 코드  -->
<script>
google.charts.load('current', {
	  packages: ['corechart', 'bar']
	});
	google.charts.setOnLoadCallback(drawBarColors);
	
	function drawBarColors() {
		  var data = google.visualization.arrayToDataTable([
		    ['마이페이지', '좋아요', '작성댓글', '작성게시글','달성챌린지'],
		    [''
		    	, <%=mypageInfo.getmLikes()%>
		    	, <%=mypageInfo.getmComment()%>
		    	, <%=mypageInfo.getmPost()%>
		    	,<%=mypageInfo.getmAcchall()%>
		    	],
		    
		  ]);

	  var options = {
	    title: '<%=memberInfo.getNick()%> 님의 활동 그래프',
	    chartArea: {width: '66%', left: 8},
	    colors: ['#143642', '#EC9A29', '#A8201A', '#DAD2D8',],
	    hAxis: {title: '점수', minValue: 100},
	    animation: {
	          duration: 2000,
	          easing: 'out',
	          startup: true
	      }
	    /* vAxis: {title: '점수', minValue: 100}, */
	  };
	  
	  var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
	  chart.draw(data, options);
	}
</script>


<script>
google.charts.load('current', {
	  packages: ['corechart', 'bar']
	});
	google.charts.setOnLoadCallback(drawBarColors);

	function drawBarColors() {
		  var data = google.visualization.arrayToDataTable([
		    ['마이페이지', '현재포인트', '누적포인트'],
		    [''
		    	, <%=mypageInfo.getmCpoint()%>
		    	, <%=mypageInfo.getmApoint()%>],
		    
		  ]);
			  
	  var options = {
	    title: '<%=memberInfo.getNick()%> 님의 포인트 그래프',
	    chartArea: {width: '66%', left: 8},
	    colors: ['#EC9A29', '#A8201A'],
	    hAxis: {title: '점수', minValue: 100},
	    animation: {
	          duration: 2000,
	          easing: 'out',
	          startup: true
	      }
	    /* vAxis: {title: '점수', minValue: 100}, */
	  };
	  
	  var chart = new google.visualization.BarChart(document.getElementById('chart_div2'));
	  chart.draw(data, options);
	}
</script>
<!-- --------------------------  -->


<style type="text/css">

#infobox { /* 전체 감싸는 div */
	align-items: center;
	padding: 20px;
	overflow:auto; 
	height: 700px;
}


/* 활동점수 table css  */
table.type09 {
	border-collapse: collapse;
	text-align: left;
	line-height: 1.35;

}
table.type09 thead th {
	padding: 10px;
	font-weight: bold;
	vertical-align: top;
	color: #143642;
	font-size: large;
	border-bottom: 3px solid #143642;
}
table.type09 tbody th {
	width: 150px;
	padding: 10px;
	font-weight: bold;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
	background: #f3f6f7;
}
table.type09 td {
	width: 350px;
	padding: 10px;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
}
/*-----------------------------  */


</style>

<h2>회원정보</h2>	
<div id="infobox">
<!--------html 코드 내용 ------- -->
<!--Home 상단 점수 영역  -->

<table class="type09">
  <thead>
  <tr>
    <th colspan="2"><%=memberInfo.getNick()%>님의 활동점수</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <th scope="row">누른좋아요</th>
    <td><%=mypageInfo.getmLikes()%> (번)</td>
  </tr>
  <tr>
    <th scope="row">작성댓글</th>
    <td><%=mypageInfo.getmComment()%> (개)</td>
  </tr>
  <tr>
    <th scope="row">작성게시글</th>
    <td><%=mypageInfo.getmPost()%> (개)</td>
  </tr>
  <tr>
    <th scope="row">누적달성챌린지</th>
    <td><%=mypageInfo.getmAcchall()%> (개)</td>
  </tr>
  <tr>
    <th scope="row">현재포인트</th>
    <td><%=mypageInfo.getmCpoint()%> (P)</td>
  </tr>
  <tr>
    <th scope="row">누적포인트</th>
    <td><%=mypageInfo.getmApoint()%> (P)</td>
  </tr>
  </tbody>
</table>
<br>
<!-------------------------  -->

<!--Home 중단 구글 그래프 영역  -->
<div id="chart_div"></div>
<br><br>
<div id="chart_div2"></div>

</div>

<!-------------------------  -->