<%@page import="dto.Mypage"%>
<%@page import="dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/session/sessionCheck.jsp" %>
<%
	Member memberInfo =(Member) session.getAttribute("memberInfo");
	Mypage mypageInfo =(Mypage) session.getAttribute("mypageInfo");
%>

<!-- 자바스크립트 영역 -->
<script type="text/javascript">
$(document).ready(function(){
	
	var flag1 = false
	var flag2 = false
	var flag3 = false
	var flag4 = false
	
	// pw 검색창 정규식 체크
	$("#mPw").blur(function(){
		var mPw = /^[A-Za-z\d]{4,12}$/ 
			if(!mPw.test($("#mPw").val())){
				$("#sp1").removeClass("green")
				$("#sp1").text("잘못된 비밀번호 형식입니다.").addClass("red")
				$("#mPw").val("")
				flag1 = false
				
		}else{
			$("#sp1").removeClass("red")
			$("#sp1").text("알맞은 비밀번호 형식입니다.").addClass("green")
			flag1 = true
		}
	})
	
	
	// 	email 검색창 정규식 체크
	$("#mEmail").blur(function(){
		var email = $("#mEmail").val();	
		var emailReg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;	
		
		if(email == ""){
			$("#sp2").removeClass("green")
			$("#sp2").text("이메일을 입력해주세요").addClass("red")
			$("#mEmail").val("")
			flag2 = false									
		} else if(!emailReg.test(email))  {
			$("#sp2").removeClass("green")
			$("#sp2").text("잘못된 이메일 형식입니다.").addClass("red")
			$("#mEmail").val("")
			flag2 = false
		}else{
			//중복검사ajax
			$.ajax({
				url : '/mypage/overlap/email?email='+ email,
				type : 'get',
				dataType : 'html', //응답 데이터 형식 JSON
				success : function(data) {
					console.log("1 = 중복o / 0 = 중복x : "+ data);										
// 					console.log("1 = 중복o / 0 = 중복x : "+ JSON.parse(data));										
// 					console.log("1 = 중복o / 0 = 중복x : "+ data.check);										
// 					if (data.check == 1) {
					if (data == 1) {
						$("#sp2").removeClass("green")
						$("#sp2").text("이미 존재하는 이메일입니다.").addClass("red")
						$("#mEmail").val("")
						flag2 = false
					}else if( data == 2){
						$("#sp2").removeClass("red")
						$("#sp2").text("기존과 동일한 이메일입니다.").addClass("green")
						flag2 = true
					}else {
						//사용 가능한 email
						$("#sp2").removeClass("red")
						$("#sp2").text("사용가능, 알맞은 이메일 형식입니다.").addClass("green")
						flag2 = true
					}
				}, error : function() {
					console.log("실패");
				}
			}); //ajax 끝	
		}	
	})// click 이벤트 끝
	
	
	// nick 검색창 정규식 체크
	$("#mNick").blur(function(){
		var nick = $("#mNick").val();
		var nickReg = /^[A-Za-z]{5,12}$/
			
		if(nick == ""){
				$("#sp3").removeClass("green")
				$("#sp3").text("닉네임을 입력해주세요").addClass("red")
				$("#mNick").val("")
				flag3 = false									
			} else if(!nickReg.test(nick))  {
				$("#sp3").removeClass("green")
				$("#sp3").text("잘못된 닉네임 형식입니다.").addClass("red")
				$("#mNick").val("")
				flag3 = false
			}else{
				//중복검사ajax
				$.ajax({
					url : '/mypage/overlap/nick?nick='+ nick,
					type : 'get',
					dataType : 'html', //응답 데이터 형식 JSON
					success : function(data) {
						console.log("1 = 중복o / 0 = 중복x : "+ data);										
//	 					console.log("1 = 중복o / 0 = 중복x : "+ JSON.parse(data));										
//	 					console.log("1 = 중복o / 0 = 중복x : "+ data.check);										
//	 					if (data.check == 1) {
						if (data == 1) {
							$("#sp3").removeClass("green")
							$("#sp3").text("이미 존재하는 닉네임입니다.").addClass("red")
							$("#mNick").val("")
							flag3 = false
						}else if( data == 2){
							$("#sp3").removeClass("red")
							$("#sp3").text("기존과 동일한 닉네임입니다.").addClass("green")
							flag3 = true
						}else {
							//사용 가능한 email
							$("#sp3").removeClass("red")
							$("#sp3").text("멋진 닉네임입니다.").addClass("green")
							flag3 = true
						}
					}, error : function() {
						console.log("실패");
					}
				}); //ajax 끝	
			}	
		})// click 이벤트 끝
	
	
	// phone 검색창 정규식 체크
	$("#mPhone").blur(function(){
		var phone = $("#mPhone").val();
		var phoneReg = /^\d{3}-\d{3,4}-\d{4}$/ 
			
			if(phone == ""){
				$("#sp4").removeClass("green")
				$("#sp4").text("전화번호를 입력해주세요").addClass("red")
				$("#mPhone").val("")
				flag4 = false									
			} else if(!phoneReg.test(phone))  {
				$("#sp4").removeClass("green")
				$("#sp4").text("잘못된 전화번호 형식입니다.").addClass("red")
				$("#mPhone").val("")
				flag4 = false
			}else{
				//중복검사ajax
				$.ajax({
					url : '/mypage/overlap/phone?phone='+ phone,
					type : 'get',
					dataType : 'html', //응답 데이터 형식 JSON
					success : function(data) {
						console.log("1 = 중복o / 0 = 중복x : "+ data);										
//	 					console.log("1 = 중복o / 0 = 중복x : "+ JSON.parse(data));										
//	 					console.log("1 = 중복o / 0 = 중복x : "+ data.check);										
//	 					if (data.check == 1) {
						if (data == 1) {
							$("#sp4").removeClass("green")
							$("#sp4").text("이미 존재하는 전화번호입니다.").addClass("red")
							$("#mPhone").val("")
							flag4 = false
						}else if( data == 2){
							$("#sp4").removeClass("red")
							$("#sp4").text("기존과 동일한 전화번호입니다.").addClass("green")
							flag4 = true
						}else {
							$("#sp4").removeClass("red")
							$("#sp4").text("사용 가능한 전화번호입니다.").addClass("green")
							flag4 = true
							}
						}, error : function() {
						console.log("실패");
						}
				}); //ajax 끝	
			}	
		})// click 이벤트 끝
	
	/*-----------------------------------------------------  */
	
	
	//--------입력된 input Date regex check-----	
	$("input").blur(function(){
		console.log("change")
		console.log(flag1 + flag2 + flag3 + flag4)
		if(flag1 && flag2 && flag3 && flag4){
			$("#modifyBtn").css('background-color', '#143642');
			$("#modifyBtn").text("전송가능");			
			$("#modifyBtn").attr("disabled", false)
			
		}else{
			$("#modifyBtn").css('background-color', '#A8201A');
			$("#modifyBtn").text("전송불가");	
			$("#modifyBtn").attr("disabled", true)
		}	
	})
})
</script>
<!------------------------------------------------------------  -->



<!-- css 영역  -->
<style type="text/css">   
      
/* 폼 영역 css  */      
            
      #updatebox { /* 폼 전체 감싸는 div */
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 20px;
      overflow:auto; 
      height: 700px;
      }
      
      #updateForm { /* 폼 태그 */
      width: 100%;
      padding: 20px;
      border-radius: 6px;
      background: #fff;
      }     
      
      .banner { /* 이미지 배너  */
      position: relative;
      height: 130px;
      background-image: url("/resources/img/logoWhite.png");      
      background-size: 95%;
      background-position: center;
      display: flex;
      justify-content: center;
      align-items: center;
      }
      
      .updateInput{ /* 인풋창 내부  */
	  width: 100%;
	  padding: 12px 20px;
	  margin: 8px 0;
	  display: inline-block;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  box-sizing: border-box;
	  }
      
      input[type="text"]:disabled {
  		background: red;
		}
      
      .updateBtn{ /* 버튼  */
      width: 100px;
      padding: 5px;
      border: none;
      background-color: #A8201A; 
      border-radius: 5px; 
      font-size: 14px;
      color: #fff;
      cursor: pointer;
      }
      
      .item{ /* 각 인풋을 감싸는 div  */
      	margin: 10px;
      }
      
      .must::after{
      	content: " *";
      	color: red;
      	
      }
/* --------------------------------------- */

/* 폰트 크기 색 부여 클래스  */
.red{
	font-size: 0.8em;
	color: red;
}

.green{
	font-size: 0.8em;
	color: green;
}
/* --------------------------------------- */

</style>

<!-- html 태그 영역  -->
<h2>회원정보수정</h2>	
<div id="updatebox">
<form action="/mypage/update" method="post" enctype= "multipart/form-data" id="updateForm">
	
	<div class="banner"></div>
	
	<div style="text-align: center; color: #143642; font-size: 17px;">"기존의 회원 정보 입력도 허용합니다."</div>	
	
	<div class="item">
	<span class="must">비밀번호</span><br>
	<input type= "text" id="mPw" name="mPw" class="updateInput" placeholder="password.."><br>
	<span id="sp1"></span>
	</div>
	
	<div class="item" >
	<span class="must">이메일</span><br>
	<input type= "text" id="mEmail" name="mEmail" class="updateInput" placeholder="email.."><br>
	<span id="sp2"></span>
	</div>
	
	<div class="item">
	<span class="must">닉네임</span><br>
	<input type= "text" id="mNick" name="mNick" class="updateInput" placeholder="nick.."><br>
	<span id="sp3"></span>
	</div>
	
	<div class="item">
	<span class="must">휴대폰번호</span><br>
	<input type= "text" id="mPhone" name="mPhone" class="updateInput" placeholder="phone.."><br>
	<span id="sp4"></span>
	</div>
	
	<div class="item">
	선호챌린지<br>
	<input type= "text" id="mChall" name="mChall" class="updateInput" placeholder="ex)기본챌린지"><br>
	</div>
	
	<div class="item">
	프로필사진<br>
	<input type="file" id ="proImg" name ="proImg" accept="image/jpeg, image/png"><br>
	<span style="font-size: 9px;"> 10MB 이하의 이미지 파일만 업로드 가능합니다.</span>
	</div>
	
	<div class="item" style="text-align: center;">
	<button class="updateBtn" id="modifyBtn" disabled="disabled">전송불가</button>
	</div>
</form>
</div>








<%--
// 		$.ajax({
// 			url : '/mypage/overlap/email?email='+ email,
// 			type : 'get',
// 			dataType : 'json', //응답 데이터 형식 JSON
// 			success : function(data) {
// 				console.log("1 = 중복o / 0 = 중복x : "+ data);										
// 				console.log("1 = 중복o / 0 = 중복x : "+ data..check);										
// 				if (data.check == 1) {
// // 				if (data == 1) {
// 					if(!email.test($("#mEmail").val())){
// 						$("#sp2").removeClass("green")
// 						$("#sp2").text("이미 존재하는 이메일입니다.").addClass("red")
// 						$("#mEmail").val("")
// 						flag2 = false}
// 				}else {		
// 						if(email == ""){
// 							$("#sp2").removeClass("green")
// 							$("#sp2").text("이메일을 입력해주세요").addClass("red")
// 							$("#mEmail").val("")
// 							flag2 = false									
// 						} else if(!email.test($("#mEmail").val()))  {
// 							$("#sp2").removeClass("green")
// 							$("#sp2").text("잘못된 이메일 형식입니다.").addClass("red")
// 							$("#mEmail").val("")
// 							flag2 = false
// 						}else{
// 							$("#sp2").removeClass("red")
// 							$("#sp2").text("알맞은 이메일 형식입니다.").addClass("green")
// 							flag2 = true
// 						}
						
// 					} // 바깥 else 끝
// 				}, error : function() {
// 						console.log("실패");
// 					}
// 			}); //ajax 끝	

 --%>
