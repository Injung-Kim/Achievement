
<%@page import="dto.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%	Notice n = (Notice) request.getAttribute("updateNotice"); %>


<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>

<!-- 스마트에디터 2 -->
<script type="text/javascript"
 src="/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>

<!-- <form>태그의 submit을 수행하면 editor에 작성한 내용을 <textarea>에 반영 -->
<script type="text/javascript">
function submitN_contents( elClickedObj ) {
	
	//에디터의 내용을 #content에 반영한다
	oEditors.getById["n_content"].exec("UPDATE_CONTENTS_FIELD", []);
	
	try {
		// <form>태그의 submit 수행
		elClickedObj.form.submit();
	} catch(e) {}
	
}
</script>

<script type="text/javascript">
$(document).ready(function() {
	
	//수정버튼 동작
	$("#btnUpdate").click(function() {
		
		//스마트 에디터의 내용을 <textarea>에 적용하는 함수를 호출한다
		submitN_contents( $("#btnUpdate") )
		
		//<form> submit
		$("form").submit();
	});
	
	//취소버튼 동작
	$("#btnCancel").click(function() {
		history.go(-1);
	});
});
</script>

<style type="text/css">
.container{
width:900px;

}
#content {
/* 	width: 100%; */
	width: 98%;
}


.main{
background-color: #A8201A;
color: #FFFFFF;
width:200px;
}

.exitbtn{
background-color: #A8201A;
color: #FFFFFF;
border-radius: 4px;
float: right;
margin:3px;
}

.normalbtn{
background-color: #143642;
color: #FFFFFF;
border-radius: 4px;
float: right;
margin:3px;
}

</style>

<div class="container">

<h3>공지사항 수정</h3>
<hr>


<form action="/admin/updatenot" method="post">
<input type="hidden" name="n_no" value="<%=request.getParameter("n_no") %>" />

<table class="table table-bordered">
<tr><td class="main">아이디 숫자</td><td><%=n.getU_no() %></td></tr>
<tr><td class="main">제목</td><td><input type="text" name="n_title" style="width:100%" value="<%=n.getN_title() %>"/></td></tr>
<tr><td class="main" colspan="2">본문</td></tr>
<tr><td colspan="2"><textarea id="n_content" name="n_content"><%=n.getN_content() %></textarea></td></tr>
</table>


</form>


<div class="text-center">	
	<button type="button" id="btnUpdate" class="normalbtn">수정</button>
	<button type="button" id="btnCancel" class="exitbtn">취소</button>
</div>
</div>



<script type="text/javascript">
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "n_content", 
	sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	fCreator: "createSEditor2"
})
</script>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>




