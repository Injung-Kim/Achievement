<%@page import="dto.UserPoint"%>
<%@page import="dto.Users"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% Users users = (Users)request.getAttribute("userinfo"); %>
<% Map<String, Integer> activeinfo = (Map<String, Integer>)request.getAttribute("activeinfo"); %>
<% UserPoint userpoint = (UserPoint)request.getAttribute("cntPayment"); %>
<% String currChallenge = (String)request.getAttribute("currChallenge"); %>

<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
	$('#user').css({"background":"#EC9A29"})
	$('#user').nextAll().css({"background":"#FFFFFF"})
	$(".delete").click(function(){
		if(confirm("정말 삭제하시겠습니까?")){
			$.ajax({
				type : "post"
				,url : "/admin/user/delete?userno="+<%=users.getUserNo()%>
				, dataType: "html"
				, success : function(result){
					console.log("ajax성공")
					if(result === "success"){
						alert("삭제되었습니다")
						location.href = "/admin/user/list"
					}else{
						alert("삭제할 수 없는 데이터입니다")
					}
				}
				, error : function(){
					console.log("실패")
				}
			})
		}
	}) //ajax delete end
	$('.update').click(function(){
		var grade = '<%=users.getGrade()%>'
		
		if(confirm("등급을 전환하시겠습니까?")){
			$.ajax({
				type : "post"
				, url : "/admin/update?userno="+<%=users.getUserNo()%>
				, data :{'grade' : grade}
				, dataType : "html"
				, success : function(result){
					console.log("update성공")
					if(result === "success"){
						alert("등급이 변경되었습니다")
						setTimeout('location.reload()',1000)
					}
				}
				, error : function(){
					console.log("update실패")
				}
			})
		}
	})//ajax update end
	
})
</script>
<body>
<div class="content">
<div style="all : unset; display: block;">
<button type="button" onclick="location.href='/admin/user/list'" class="btn">목록으로</button>
<!-- manager1은 전체 등급변환, 삭제 권한 부여 -->
<%if( session.getAttribute("u_id").equals("manager1")){ %>
<button type="button" class="btn delete">회원정보 삭제</button>
<button type="button" class="btn update">등급 전환</button>
<!-- manager1 이외 관리자는 회원에만 한하여 삭제 권한 부여 -->
<%} else if( session.getAttribute("u_grade").equals("M") && !users.getGrade().equals("M")){%>
<button type="button" class="btn delete">회원정보 삭제</button>
<%} %>

</div>
<div class="maininfo">
<h3>회원 정보</h3>

<dl>	
	<dt>이름</dt>
		<dd><%= users.getName() %></dd>
	<dt>닉네임</dt>
		<dd><%= users.getUserNick() %></dd>
	<dt>등급</dt>
		<% if(users.getGrade().equals("U")){ %>
		<dd><label>회원<input type="checkbox" checked  onclick="return false"></label>
		<label>관리자<input type="checkbox" disabled="disabled"></label></dd>
		<% }else if (users.getGrade().equals("M")){%>
		<dd><label>회원<input type="checkbox" disabled="disabled"></label>
		<label>관리자<input type="checkbox" checked = "checked"  onclick="return false"></label></dd>
		<%} %>
	<dt>계정</dt>
		<dd><%=users.getUserEmail() %></dd>
	<dt>우편번호</dt>
		<dd><%=users.getPostNum() %></dd>
	<dt>주소</dt>
		<dd><%=users.getAddress() %></dd>
	<dt>참여하고 싶은 챌린지</dt>
		<dd><%=users.getUserChallenge() %></dd>
</dl>
</div>

<div class = "activeInfo">
<h3>활동정보</h3>
<dl class="active">
	<dt>가입일</dt>
		<dd><%=users.getSignupDate() %></dd>
</dl>
<dl class="active challenge">
	<dt>참여중인 챌린지</dt>
	<%if(currChallenge.equals("")){ %>
		<dd>없음</dd>
	<%} else { %>
		<dd><%= currChallenge %></dd>
	<%} %>
</dl>
<dl>
	<dt>작성</dt>
		<dd><table>
		<tr>
			<th>게시글</th>
			<td><%= activeinfo.get("cntPost") %>개</td>
		</tr>
		<tr>
			<th>댓글</th>
			<td><%= activeinfo.get("cntComment") %>개</td>
		</tr>
		<tr>
			<th>좋아요</th>
			<td><%=activeinfo.get("cntLikes") %>개</td>
		</tr>
		<tr>
			<th>신고</th>
			<td><%= activeinfo.get("cntComplaint") %>개</td>
		</tr>
		</table></dd>
</dl>
</div>
<div class="pointInfo">
<h3>결제 정보</h3>
<dl>
	<dt>누적 결제금액</dt>
		<dd><%= userpoint.getPayments() %></dd>
	<dt>보유 포인트</dt>
		<dd><%=userpoint.getSumPoint() %></dd>
</dl>
<dl>
	<dt>최근 포인트 적립일</dt>
	<% if(userpoint.getEarnedDate() != null){ %>
		<dd><%=userpoint.getEarnedDate() %></dd>
	<%}else{ %>
		<dd>이력없음</dd>
	<%} %>
	<dt>최근 포인트 지급일</dt>
	<% if(userpoint.getUsedDate() != null){ %>
		<dd><%=userpoint.getUsedDate() %></dd>
	<%}else{ %>
		<dd>이력없음</dd>
	<%} %>	
</dl>
</div>
</div>
</body>
<style type="text/css">
.content{
	 width : 900px;
	 /* border: 1px solid #ccc; */
	 margin: 0 auto;
	 text-align: justify;
	 padding-top : 15px;
}

.content > div{
	 border : 1px solid #bfbfbf;
	 display: inline-block;
	 box-sizing:border-box;
	 padding: 20px 20px 10px 20px;
	 border-radius : 5px;
	 box-shadow: 0 2px 2px rgba(0,0,0,0.25), 0 1px 3px rgba(0,0,0,0.22);
	 font-size: 13px;
 
}
.maininfo{
	 width : 450px;
 	float : left;
 	margin : 0px 15px;
 
}
.activeInfo{
	 width : 400px;
 	text-align:left;

}
.pointInfo{
 	width : 400px;
 	margin-top : 15px;
 	margin-bottom : 15px;
}
dt{
 	font-size : small;
 	color : #8C8C8C;
 	margin-top:15px;
	text-align: left;
}
dd{
	text-align: left;
	margin : 5px 0px 0px 60px;
}
.pointInfo dl{
	display : inline-block;
	margin-left: 4em;
}
.pointInfo dt{
	text-align: right;	
}
.active{
	display: inline-block;
}
.challenge{
	margin-left : 3em;
}
h3{
	margin : 0;
	text-align: left;
}
table{
	margin-top : 5px;
}
th{
 	font-weight: normal;
 	text-align: left;
}
td{
	 width : 60%;
 	text-align: right;
}
.btn{
	all:unset;
	background-color: #143642;
	color:white;
	cursor: pointer;
	padding : 8px 8px;
	border-radius : 5px;
	margin : 0 0 5px 15px;
}
.btn:hover{
	color : white;
	background-color : rgba(20, 54, 66, 0.9);
}

</style>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>