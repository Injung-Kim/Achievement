<%@page import="java.util.Map"%>
<%@page import="dto.Certification"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<Certification> list = (List)request.getAttribute("certificationList"); %>
<% String title = (String)request.getAttribute("title"); %>
<% Map<String, Boolean> whethers = (Map<String, Boolean>)request.getAttribute("whethers"); %>
<% int cerCount = (Integer)request.getAttribute("cerCount"); //현재 구간 내가 인증한 횟수  %>
<% int cecCount = (Integer)request.getAttribute("cecCount"); //현재 구간 총 인증 횟수 %>

<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<style type="text/css">
.tableHeader{
	/* header정의 코드 */
 	text-align: center;
 	width: 900px;
 	margin: 0 auto;
 	padding: 15px;

}
#btnTitle{
	font-size:19px;
}
#chTitle{
	font-size:30px; 
	font-weight:bold;
}

#bottomButtons{
	width: 900px;
	margin: 0 auto;
}
#bottomButtons  > div{
	display: inline-block;
	height: 30px;
	padding: 10px;
	margin: 20px;
	width:100px;
}

.wrap {
	/* 내부 정렬 */	
	/* 외부 정렬 */
	width: 900px;
	margin: 0 auto;
	
}
.wrap > div{
	/* 인라인블록 요소로 설정하기 */
	display: inline-block;
	height: 100px;
	padding: 10px;
	margin: 20px;
	width:250px;
}

.imgarr{
	text-align:center;
}
.imgarr > a{
	/* 글자색상 */
	color: #8C8C8C;
	/* 글자 꾸밈선 없애기(underline) */
	text-decoration: none;
	/* 글자 스타일 지정 */
	margin: 0 5px;
}


</style>
<script type="text/javascript">
$(document).ready(function () { 
    startDate();
    
    $("#paLike").click(function(){
    	$.ajax({
    		type:"get"
    		, url: "/participant/like"
    		,data:{
    			paLike : <%=whethers.get("paLike")%>
    		}, dataType: "html"
    		, success: function(res){
    			
    			$("#paLikeResult").html(res) //결과값 반영
    		}
    		,error: function(){
    			console.log("실패");
    		}
    		
    	});
    });
    $("#btnComplaint").click(function(){
    	$(location).attr("href", "/participant/complaint/write");
    })
   	$("#btnReview").click(function(){
   	 	$(location).attr("href", "/participant/review/write");  		
   	})
   
}); 

function startDate() { 
    date = setInterval(function () { 
    var dateString = ""; 

    var newDate = new Date(); 

        //String.slice(-2) : 문자열을 뒤에서 2자리만 출력한다. (문자열 자르기) 
        dateString += newDate.getFullYear() + "/"; 
        dateString += ("0" + (newDate.getMonth() + 1)).slice(-2) + "/"; //월은 0부터 시작하므로 +1을 해줘야 한다. 
        dateString += ("0" + newDate.getDate()).slice(-2) + " "; 
        dateString += ("0" + newDate.getHours()).slice(-2) + ":"; 
        dateString += ("0" + newDate.getMinutes()).slice(-2) + ":"; 
        dateString += ("0" + newDate.getSeconds()).slice(-2);
        //document.write(dateString); 문서에 바로 그릴 수 있다. 
        $("#date").text(dateString); 
    }, 1000); 
} 
</script>
<div class="container">
<div id="tableHeader">
	<div class="left" style="font-size:30px; font-weight:bold;">인증글 목록 
	<%--현재 구간이 총 구간 보다 클때  --%>
	<%if(cecCount - cerCount<=0){ %>
		<i class="fas fa-plus"></i>
	<%}else{ %>
		<a href="/participant/certification/write"><i class="fas fa-plus"></i></a>
	<%} %>
	</div>
	<div><span>&nbsp</span></div>
	<div><span>&nbsp</span></div>
	<div><hr></div>
</div>


	<div>
		<div id=chTitle class="text-center"><i class="far fa-thumbs-up"></i>&nbsp;&nbsp;<%=title %>인증</div>
		<div class="h6 left">현재 주기에 남은 인증 횟수는 <%=cecCount - cerCount%>번입니다.</div>
		<div id="date" class="h6 right"></div>
	</div>
	
<div class="wrap">
	<%for(int i=0; i<list.size(); i++){ %>
		
		<div class="imgarr"><a href="/participant/certification/view?ceNo=<%=list.get(i).getCeNo()%>">
		
		<%if(list.get(i).getCeStoredName().contains("저장")){ %>
			<img src="/resources/img/challenge.png" height="150"/>
		<%}else{ %>
			<img src="/upload/<%=list.get(i).getCeStoredName()%>" height="150" />
		<%} %>
		
		<%if(list.get(i).getCeIsSuccess().equals("W")) {%>
		<p>대기</p>
		<%}else if(list.get(i).getCeIsSuccess().equals("Y")){ %>
		<p style="color: blue;">성공</p>
		<%}else if(list.get(i).getCeIsSuccess().equals("N")){ %>
		<p style="color: red;">실패</p>
		<%} %>
		<p><%=list.get(i).getCeCreateDate() %></p>
		</a></div>
	<%} %>
</div>

<!-- 위치 조정 -->
<div><br><br><br></div>
<div id="bottomButtons" class="text-center">
<div id="paLikeResult">
<%if(whethers.get("paLike")){ %>
	<button id="paLike" class="btn btn-info" style="color:red;"><i class="far fa-thumbs-up fa-2x"></i></button>
<%}else{ %>
	<button id="paLike" class="btn btn-info" style="color:#ccc"><i class="far fa-thumbs-up fa-2x"></i></button>
<%} %>
</div>
<%if(whethers.get("paComplaint")){ %>
	<div><button id="btnComplaint" class="btn btn-danger disabled">신고</button></div>
<% }else{%>
	<div><button id="btnComplaint" class="btn btn-danger">신고</button></div>
<%} %>
<div><button id="btnReview" class="btn btn-info">후기</button></div>
</div>


</div>
<%@ include file="/WEB-INF/views/participantCertification/participantCertificationPaging.jsp" %>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>