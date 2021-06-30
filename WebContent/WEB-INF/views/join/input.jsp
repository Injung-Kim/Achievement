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



<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>
<script type="text/javascript">
$(document).ready(function(){
	//페이지 첫 접속 시 입력창으로 포커스 이동
	$("input").eq(0).focus();
	
	  $("#uid").focus()  
	
	  $("#uid").blur(function(){
		  
		  $("#idMsg").removeClass("color-correct")
		  
		  console.log( $(this).val() )
		  console.log( $(this).val().length )
		  
		  
		  if($(this).val() =='' || $(this).val() ==null){
		     $("#idMsg").html("필수항목입니다")
		  } else if( $(this).val().length < 5) {
	          $("#idMsg").html("5자 이상으로 입력하세요")
		  } else {
			
		  }
	  })  //end #uid
	  
	  

	$("#btn1").click(function() {
		$.ajax({
			url : "/member/idcheck",
			type : "get",
			data : {
				uid : $("#uid").val()
			},
			dataType : "json",
			success : function(res) {
				console.log("ID중복체크 성공")
				console.log(res.result )
				
				if(res.result) {
		    		 $("#idMsg").removeClass("color-correct").html("이미 사용중인 아이디입니다")

				} else {
		    		 $("#idMsg").addClass("color-correct").html("사용 가능한 아이디입니다")
				}
 			},
			error : function() {
				console.log("ID중복체크 에러")
			}
		})

		// 	    	 if($("uid").val()){
		// 	    		 $("#idMsg").html("이미 사용중인 아이디입니다")
		// 	    	 }else{
		// 	    		 $("#idMsg").addClass("color-correct").html("사용 가능한 아이디입니다")
		// 	    	 }

	})
	
	$("#btn2").click(function(){
		$.ajax({
			url : "/member/nickcheck",
			type : "get",
			data : {
				nick : $("#nick").val()
			},
			dataType : "json",
			success : function(res) {
 				console.log("별명중복체크 성공")
 				console.log(res.result )
				
				if(res.result) {
		    		 $("#nickMsg").removeClass("color-correct").html("이미 사용중인 별명입니다")

				} else {
		    		 $("#nickMsg").addClass("color-correct").html("사용 가능한 별명입니다")
				}
 			},
			error : function() {
				console.log("ID중복체크 에러")
				}
		})
	})

		$("#upassword").blur(function() {

			$("#pwMsg").removeClass("color-correct")

			if ($(this).val() == '' || $(this).val() == null) {
				$("#pwMsg").html("필수항목입니다")
			} else {
				$("#pwMsg").addClass("color-correct").html("사용 가능한 비밀번호입니다")
			}
		})//end #upassword

		$("#username").blur(function() {
			console.log("username")
			$("#nameMsg").removeClass("color-correct")

			var regex = /^[가-힣]{1,4}$/

			if ($(this).val() == '' || $(this).val() == null) {
				$("#nameMsg").html("필수항목입니다")
			} else if (!regex.test($(this).val())) {
				$("#nameMsg").html("한글 4자이하로 입력하세요")
			} else {
				$("#nameMsg").addClass("color-correct").html("사용 가능한 이름입니다")
			}

		})//end#username

		$("#phone").blur(function() {

			$("#phoneMsg").removeClass("color-correct")

			var regex = /^[0-9]{10,11}$/

			if ($(this).val() == '' || $(this).val() == null) {
				$("#phoneMsg").html("필수항목입니다")
			} else if (!regex.test($(this).val())) {
				$("#phoneMsg").html("올바른 형식이 아닙니다")
			} else {
				$("#phoneMsg").addClass("color-correct").html("사용 가능한 번호입니다")
			}

		})//end"#phone"

		$("#Email").blur(function() {

			$("#emailMsg").removeClass("color-correct")

			var regex = /.+@[a-z]+(\.[a-z]+){1,2}$/;

			if ($(this).val() == '' || $(this).val() == null) {
				$("#eamilMsg").html("필수항목입니다")
			} else if (!regex.test($(this).val())) {
				$("#emailMsg").html("올바른 형식이 아닙니다")
			} else {
				$("#emailMsg").addClass("color-correct").html("사용 가능한 이메일입니다")
			}

		})
	
	$("#btn3").click(function() {
		$.ajax({
			url : "/member/emailcheck",
			type : "get",
			data : {
				email : $("#email").val()
			},
			dataType : "json",
			success : function(res) {
				console.log("EMAIL중복체크 성공")
				console.log(res.result )
				
				if(res.result) {
		    		 $("#emailMsg").removeClass("color-correct").html("이미 사용중인 이메일입니다")

				} else {
		    		 $("#emailMsg").addClass("color-correct").html("사용 가능한 이메일입니다")
				}
 			},
			error : function() {
				console.log("EMAIL중복체크 에러")
			}
	
		})	
	})
		
})//end script
 	
</script> 
<script type="text/javascript"> 
 $(document).ready(function(){ 
	
 $("#btncancel").click(function(){ 
 		   history.go(-1);
	   }); 
 

//  $("#btnnext").click(function(){
 $("#myform").submit(function(){


	var isEmpty = false;
	 if($("#uid").val() == '' || $("#uid").val() == null){
		 isEmpty = true;
		 $("#uid").focus(); //포커스 이동
		 
	 }else if($("#upassword").val() == '' || $("#upassword").val() == null){
		 isEmpty = true;
		 $("#upassword").focus(); //포커스 이동
		 
	 }else if($("#username").val() == '' || $("#username").val() == null){
		 isEmpty = true;
		 $("#username").focus(); //포커스 이동
		 
	 }else if($("#nick").val() == '' || $("#nick").val() == null){
		 isEmpty = true;
		 $("#nick").focus(); //포커스 이동
		 
	 }else if($("#phone").val() == '' || $("#phone").val() == null){
		 isEmpty = true;
		 $("#phone").focus(); //포커스 이동
		 
	 }else if($("#email").val() == '' || $("#email").val() == null){
		 isEmpty = true;
		 $("#email").focus(); //포커스 이동
		 
	 }else if($("#m").val() == '' || $("#m").val() == null){
		 isEmpty = true;
		 $("#m").focus(); //포커스 이동
	 }else if($("#f").val() == '' || $("#f").val() == null){
			 isEmpty = true;
			 $("#f").focus(); //포커스 이동
	 }else if($("#birthYear").val() == '' || $("#birthYear").val() == null){
			isEmpty = true;
			$("#birthYear").focus(); //포커스 이동
	 }else if($("#birthMonth").val() == '' || $("#birthMonth").val() == null){
			isEmpty = true;
			$("#birthMonth").focus(); //포커스 이동
	 }else if($("#birthDay").val() == '' || $("#birthDay").val() == null){
			isEmpty = true;
			$("#birthDay").focus(); //포커스 이동
	 }else if($("#account").val() == '' || $("#account").val() == null){
			isEmpty = true;
			$("#account").focus(); //포커스 이동
	 }
	 
	 if(isEmpty) {
		 return false;
	 }
		 
 
}); //$("#myform").submit(function()

});//$(document).ready(function()
</script>


<style type="text/css">
.container{
   border: 1px solid #ccc;
   width: 900px;
   height: 600px;
   margin: 0 auto;
   
}
.container1{
   border: 1px solid #ccc;
   width: 900px;
   height: 250px;
   margin: 0 auto;
}
.color{
   color: red;
}
.color-correct {
	color: green;
}
#fn{
 
}

</style>

<br><br>
<div class="text-center">
<h1> 회원가입 - 회원정보 입력</h1>

</div>
<h3 id="fn">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp기본정보</h3>

<form id="myform" action="/member/input" method="post" class="form-horizontal">
<div class="container">



  <div class="form-group">
    <label for="uid" class="col-sm-2 control-label">아이디</label>
       <input type="button" class="btn btn-primary" id="btn1"  value="중복확인"/>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="uid" name="uid" placeholder="아아디를 입력하세요"/>
       <span id="idMsg" class="color"></span>
    </div>
  </div>
  
  <div class="form-group">
    <label for="upassword" class="col-sm-2 control-label">비밀번호</label>
     <div class="col-sm-4">
    <input type="password" class="form-control" id="upassword" name="upassword" placeholder="비밀번호를 입력하세요"/>
    <span id="pwMsg" class="color"></span>
     </div>
  </div>
  
  <div class="form-group">
    <label for="username" class="col-sm-2 control-label">이름</label>
    <div class="col-sm-4">
    <input type="text" class="form-control" id="username" name="username" placeholder="이름을 입력하세요" />
    <span id="nameMsg" class="color"></span>
    </div>
  </div>
  
  <div class="form-group">
    <label for="nick" class="col-sm-2 control-label">별명</label>
    <input type="button" class="btn btn-primary" id="btn2" value="중복확인"/>
     <div class="col-sm-4">
    <input type="text" class="form-control" id="nick"name="nick"  placeholder="별명을 입력하세요"  />
    <span id="nickMsg" class="color"></span>
     </div>
  </div>
  
  <div class="form-group">
    <label for="phone" class="col-sm-2 control-label">휴대전화</label>
     <div class="col-sm-4">
    <input type="text" class="form-control" id="phone" name="phone" placeholder="전화번호'-'빼고 입력하세요"/>
     <span id="phoneMsg" class="color"></span>
    </div>
  </div>
  
  <div class="form-group">
    <label for="email" class="col-sm-2 control-label">이메일</label>
    <input type="button" class="btn btn-primary" id="btn3" value="중복확인"/>
    <div class="col-sm-5">
      <input type="text" class="form-control" id="email" name="email" placeholder="이메일을 입력하세요"/>
       <span id="emailMsg" class="color"></span>
    </div>
    </div>
    
  <div class="form-group">
     <input type="hidden" class="btn btn-primary id="u" value="u"/>
  </div>
  
  <div class="form-group">
  <label for="gender" class="col-sm-2 control-label">성별 </label>
   <label class="radio-inline">
  <input type="radio" name="gender" id="m" value="M"/> 남
</label>
<label class="radio-inline">
  <input type="radio" name="gender" id="f" value="F"/> 여
</label>
  </div>
  
  <div class="form-group" >
    <label for="birth" class="col-sm-2 control-label">생일</label>
     <div class="col-xs-2">
    <input type="text" class="form-control" id="birthYear" name="birthYear" placeholder="년(4자)"/>
     </div>
     <div class="col-xs-2">
     <select class="form-control" id="birthMonth" name="birthMonth">
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
</select>
     </div>
   <div class="col-xs-2">
    <input type="text" class="form-control" id="birthDay" name="birthDay" placeholder="일"/>
     </div>  
  </div>
  
  <div class="form-group">
    <label for="account" class="col-sm-2 control-label">계좌</label>
     <div class="col-sm-4">
    <input type="text" class="form-control" id="account" name="account" placeholder="계좌번호"/>
    </div>
     <div class="col-xs-3" >
     <select class="form-control" id="bank" name="bank">
<option>KB국민은행</option>		  
<option>SC제일은행</option>		
<option>경남은행</option>			
<option>광주은행</option>			
<option>기업은행</option>			
<option>농협</option>			
<option>대구은행</option>			
<option>부산은행</option>		
<option>산업은행</option>			
<option>새마을금고</option>			
<option>수협</option>			
<option>신한은행</option>			
<option>신협</option>			
<option>외환은행</option>			
<option>우리은행</option>			
<option>우체국</option>			
<option>전북은행</option>			
<option>축협 </option>			
<option>카카오뱅크</option> 		
<option>케이뱅크</option>			
<option>하나은행(서울은행)</option>		
<option>한국씨티은행(한미은행)</option> 
</select>
</div>
</div>


</div><br>
    
<div class="container1"><br>


<div class="form-group">
    <label for="challenge" class="col-sm-2 control-label">도전하고 싶은 챌린져</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="challenge" name="challenge" />
    </div>
</div>

<div class="form-group">
    <label for="address" class="col-sm-2 control-label">주소</label>
<input type="text" id="sample6_postcode" name="post" placeholder="우편번호">
<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
<input type="text" id="sample6_address" name="address" placeholder="주소"><br>
    <label class="col-sm-2 control-label"></label>
<input type="text" id="sample6_detailAddress" placeholder="상세주소">
<input type="text" id="sample6_extraAddress" placeholder="참고항목">

</div><br>

<div class="text-center">
    <input type="button" id="btncancel" class="btn btn-danger" value="이전"/>
    <input type="submit" id="btnnext" class="btn btn-info" value="가입완료"/>
</div><br><br>

 
  
  
  

</div> 
</form>


<%@ include file="/WEB-INF/views/layout/footer.jsp" %>