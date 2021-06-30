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
	//페이지 첫 접속 시 입력창으로 포커스 이동
	$("input").eq(1).focus();
	
	  $("#uid").focus()  
	
	//취소버튼
	$("#btncancel").click(function(){
		location.href="/member/login";
	})

})


</script>
    
<style type="text/css">
.container{
  border : 3px solid #ccc;
  Width: 500px;
  height: 300px;
}

</style>

<div class="text-center">
<h1>아이디 찾기</h1>
</div><br><br>

<div class="container"><br><br>
 <form id="myform" action="/member/idfind" method="post" class="form-horizontal">
 <div class="form-group">
    <label for="username" class="col-sm-3 control-label">이름</label>
    <div class="col-sm-5">
    <input type="text" class="form-control" id="username" name="username" placeholder="이름을 입력하세요" />
    <span id="nameMsg" class="color"></span>
    </div>
  </div><br>
  
  <div class="form-group">
    <label for="phone" class="col-sm-3 control-label">휴대전화</label>
     <div class="col-sm-6">
    <input type="text" class="form-control" id="phone" name="phone" placeholder="전화번호 '-' 빼고 입력하세요"">
     <span id="phoneMsg" class="color"></span>
    </div>
  </div><br><br>
  
  <div class="text-center">
    <button type="button" id="btncancel" class="btn btn-danger">취소</button>
    <input type="submit" id="btnnext" class="btn btn-info" value="확인"/>
  </div>
</form>
</div><br><br>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>



