<%@page import="dto.Mypage"%>
<%@page import="dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/session/sessionCheck.jsp" %>
<%
	Member memberInfo =(Member) request.getAttribute("memberInfo");
	Mypage mypageInfo =(Mypage) request.getAttribute("mypageInfo");
%>  
    
<script type="text/javascript">
$(document).ready(function(){
	
	var flag2 = false
	
	// pw 검색창 정규식 체크
	$("#mPw").blur(function(){
		var mPw = /^[A-Za-z\d]{4,12}$/ 
		var sessionPw = <%=memberInfo.getUpassword() %>
			if(!mPw.test($("#mPw").val())){
				$("#sp2").removeClass("green")
				$("#sp2").text("잘못된 비밀번호 형식입니다.").addClass("red")
				$("#mPw").val("")
				flag2 = false
				
			}else if(sessionPw != $("#mPw").val()){
				$("#sp2").removeClass("green")
				$("#sp2").text("비밀번호가 틀렸습니다.").addClass("red")
				$("#mPw").val("")
				flag2 = false				
			}else{
				$("#sp2").removeClass("red")
				$("#sp2").text("알맞은 비밀번호 형식입니다.").addClass("green")
				flag2 = true
		}
	})    

	//--------입력된 input Date regex check-----	
	$("input").blur(function(){
		console.log("change")
		if(flag2 ){
			$("#deleteBtn").css('background-color', '#143642');
			$("#deleteBtn").text("전송가능");			
			$("#deleteBtn").attr("disabled", false)
			
		}else{
			$("#deleteBtn").css('background-color', '#A8201A');
			$("#deleteBtn").text("전송불가");	
			$("#deleteBtn").attr("disabled", true)
		}	
	})
	
	// 삭제 버튼 옵션
	$("#deleteBtn").click(function(){
			var conUser = confirm("정말 탈퇴 하시겠습니까?")
			if(conUser == true){
				$("#deleteForm").submit();
			}else{
				$(location).attr("href", "/mypage/home")
			}
		})


	/*  뒤로가기 버튼 클릭시 */
	$("#backBtn").click(function(){
		$(location).attr("href", "/mypage/home")
	})

   
})


</script>
    
<!-- css 영역  -->
<style type="text/css">   
      
/* 폼 영역 css  */      
            
      #deletebox { /* 폼 전체 감싸는 div */
      display:flex;
      justify-content: center;
      padding: 20px;
      overflow:auto; 
      height: 600px;
      }
      
      #deleteForm { /* 폼 태그 */
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
      
      #mId{ /* 인풋창 내부  */
	  width: 100%;
	  padding: 12px 20px;
	  margin: 8px 0;
	  display: inline-block;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  box-sizing: border-box;
	  background-color: #ccc;
	  }
      
      #mPw{ /* 인풋창 내부  */
	  width: 100%;
	  padding: 12px 20px;
	  margin: 8px 0;
	  display: inline-block;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  box-sizing: border-box;
	  }
      
      #deleteBtn{ /* 삭제버튼  */
      width: 100px;
      padding: 5px;
      border: none;
      background-color: #A8201A; 
      border-radius: 5px; 
      font-size: 14px;
      color: #fff;
      cursor: pointer;
      }
      
   	  #backBtn{ /* 뒤로가기버튼  */
      width: 100px;
      padding: 5px;
      border: none;
      background-color: #143642; 
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
      
/* -------------------------------------- */      

/* 경고 느낌표 / 텍스트 영역 css */

#ExcMarkArea{
	text-align: center;
}

#ExcMark{
	background-color: #A8201A;
	color: white;
	font-size: 30px;
	padding-left: 25px;
	padding-right: 25px;
	border-radius: 20px;
	font-weight: 900;
	
}

#ExcMarkText{
	text-align: center;
	font-size: larger;
	margin: 20px;
	color: #A8201A;
	font-weight: 700;
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

<h2>회원탈퇴</h2>
<div id="deletebox">
<form action="/mypage/delete" method="post" id="deleteForm">
	
	<div class="banner"></div>
	
	<div id="ExcMarkArea">
		<span id="ExcMark"> ! </span>
	</div>
	
	<div id="ExcMarkText">
		"회원 탈퇴시 해당 계정은 복구되지 않습니다."
	</div>
	
	<div class="item">
	<span>아이디</span><br>
	<input type= "text" id="mId" name="mId" class="deleteInput" value="<%=memberInfo.getUid() %>" readonly="readonly"><br>
	<span id="sp1"></span>
	</div>	
	
	<div class="item">
	<span class="must">비밀번호</span><br>
	<input type= "text" id="mPw" name="mPw" class="deleteInput" placeholder="password.."><br>
	<span id="sp2"></span>
	</div>
	
	<div class="item" style="text-align: center;">
	<button id="deleteBtn" disabled="disabled" type="button">전송불가</button>
	<button id="backBtn" type="button">홈으로</button>	
	</div>
</form>
</div>




