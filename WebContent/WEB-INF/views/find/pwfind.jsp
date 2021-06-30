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
//이메일 인증값 확인 상태
var cert = false;

$(document).ready(function(){
	//페이지 첫 접속 시 입력창으로 포커스 이동
	$("input").eq(1).focus();
	
	  $("#username").focus()  
	
	//취소버튼
	$("#btncancel").click(function(){
		location.href="/member/login";
	})
		
	
	//인증번호 받기
	$("#accept").click(function(){
		
		$.ajax({
			type: "get"
			, url: "/send"
			, data: {
				email: $("#email").val()
			}
			, success: function() {
				console.log("ajax success")
			}
			, error: function() {
				console.log("ajax fail")
			}
		
		})
	})//end $("#accept").click(function()
			
	$("#chk").click(function(){
		$.ajax({
			type: "get"
			, url: "/certification"
			, data: {
				num: $("#num").val()
			}
			, dataType: "json"
			, success: function(res) {
				console.log("ajax success")
				
				cert = res.result
				console.log("cert", cert)
				if(cert) {
		    		 $("#emailMsg").addClass("color-correct").html("인증되었습니다")
				} else {
					 $("#emailMsg").removeClass("color-correct").html("인증되지않았습니다")
				}
			}
			, error: function(res) {
				console.log("ajax fail")
				
			}
		
		}) 
	 
		
	})	//end $("#chk").click(function()
	
			
	$("#myform").submit(function() {
		if(!cert) {
			alert("이메일 인증이 완료되지 않았습니다")
			return false;
		}
	})
	
	
})//$(document).ready(function()

</script> 


<style type="text/css">
.container{
  border : 3px solid #ccc;
  Width: 500px;
  height: 350px;
}
.color {
   color: red;
}
.color-correct {
	color: green;
}

</style>


<div class="text-center">
<h1>비밀번호 찾기</h1>
</div><br>

<div class="container"><br><br>
 <form id="myform" action="/member/pwfind" method="post" class="form-horizontal">
  <div class="form-group">
    <label for="username" class="col-sm-2 control-label">이름</label>
    <div class="col-sm-5">
    <input type="text" class="form-control" id="username" name="username" placeholder="이름을 입력하세요" />
    <span id="nameMsg" class="color"></span>
    </div>
  </div>
  <div class="form-group">
    <label for="uid" class="col-sm-2 control-label">아이디</label>
    <div class="col-sm-5">
      <input type="text" class="form-control" id="uid" name="uid"  placeholder="아아디를 입력하세요"/>
       <span id="idMsg" class="color"></span>
    </div>
  </div>
  <div class="form-group">
    <label for="Email" class="col-sm-2 control-label">이메일</label><br>
    <div class="col-sm-6">
      <input type="email" class="form-control" id="email" name="email" placeholder="이메일을 입력하세요">
     </div>
      <input type="button" class="btn btn-primary" id="accept" name="accept"  value="인증번호 받기"/><br><br>
     <label class="col-sm-2 control-label"></label>
       <div class="col-sm-5">
      <input type="text" class="form-control"  id="num" name="num" placeholder="인증번호4자리"/>
     </div>
      <input type="button" class="btn btn-success" id="chk" name="chk"  value="인증확인"/><br>
      <div class="col-sm-10">
      <label class="col-sm-3 control-label"></label>
      <span id="emailMsg" class="color"></span>
      </div>
    </div>
    
    </div><br>
    <div class="text-center">
    <button type="button" id="btncancel" class="btn btn-danger">취소</button>
    <input type="submit" id="btnnext" class="btn btn-info" value="확인"/>
  </div>
  </form>
</div><br><br>



<%@ include file="/WEB-INF/views/layout/footer.jsp" %> 
   


