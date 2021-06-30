<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %><br>  

<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %><br><br>      



<!--  jquery를 추가해 주어야 부트스트랩 이용할수 있다 -->
<script type="text/javascript" src="http://code.jquery.com/jquery-2.2.4.min.js"></script>


<!--  부트스트랩 3.2.2 -->
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>    

<script type="text/javascript">
$(document).ready(function(){
	//취소버튼
	$("#btncancel").click(function(){
		  history.go(-1);
	})

})


</script>

<div class="text-center">
<h1>아이디 찾기</h1>
</div><br>

<div class="text-center">
<h3>존재하지 않는 이름입니다</h3>

 <div class="text-center">
    <button type="button" id="btncancel" class="btn btn-danger">뒤로</button>

  </div>
</div><br><br>  

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>    
    

