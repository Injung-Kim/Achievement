<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--세션 연장 / 만료 구현 자바스크립트  -->
<script type="text/javascript">

var interval = <%=session.getMaxInactiveInterval() * 1000 - 2000%>;
// 최초 알림창이 까지의 시간을 설정함

// setTimeout 설정으로 알림창 뜨는 여부 선택
var tid = setTimeout(renew, interval);

function renew() {
	
	var res = confirm("로그인을 연장하시겠습니까?");
	
	if( res ) { // true 일경우 세션 갱신을 위해 ajax 실행
		$.ajax({
			url : '/mypage/session',
			type : 'post',
			dataType : 'json',				
			success : function( res ) {
				if( !res.intervalTime ) {
					interval = res.intervalTime * 1000 -2000;
				}
			}, error : function() {
				console.log("실패");
			}
		}) //ajax 종료

		clearTimeout(tid); // 현재 타임아웃 삭제
		tid = setTimeout(renew, interval); // 기존 타임아웃 재설정
		
	} else {
	 	alert("로그인이 만료되어 메인으로 이동합니다.")
	 	<%-- <% session.invalidate(); %> --%>
		location.href="/member/logout"; // 세션만료될거기 때문에 홈으로 이동
	}
	
}

/* 세션 테스트 코드 세션시간 10초  */
// $(document).ready(function(){
// 	/* 최초 setTime 설정  */
<%-- 	var timePlus = setTimeout('plusSession()', <%= request.getSession().getMaxInactiveInterval() * 1000 - 5000 %>); --%>
<%-- 	var sessionTime = setTimeout('expireSession()', <%= request.getSession().getMaxInactiveInterval() * 1000 %>); --%>

	
// 	function sessionCheck(check){
// 		if(check){
// 			console.log("check")
// 			$.ajax({
// 				url : '/mypage/session',
// 				type : 'post',
// 				dataType : 'html',				
// 				success : function() {
// 					console.log("ajax")
// 					clearTimeout(timePlus);
<%-- 					timePlus = setTimeout('plusSession()', <%= request.getSession().getMaxInactiveInterval() * 1000 - 5000 %>); --%>
					
// 					clearTimeout(sessionTime);
<%-- 					sessionTime = setTimeout('expireSession()', <%= request.getSession().getMaxInactiveInterval() * 1000 %>); // 세션초기화 함수 호출 --%>
// 				}, error : function() {
// 					console.log("실패");
// 				}
// 			}) //ajax 종료
// 		}//if 종료

// 	}
		
// })
// var check = true
// function plusSession() { //로그인 연장 메소드
// 	check = confirm("로그인을 연장하시겠습니까?")
// 	/* setTimeout(self.close(), 30000) // 자동 닫힘 설정 */
// 	sessionCheck(check)
// }


// function expireSession() { // 만료시 메인이동 메소드
// 	alert("로그인이 만료되어 메인으로 이동합니다.")
// 	window.location = "/"; 	
// }
</script>