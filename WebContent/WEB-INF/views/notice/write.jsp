<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
	
	//작성버튼 동작
	$("#btnWrite").click(function() {
		
		submitN_contents( $("#btnWrite") )
		
		$("form").submit();
	});
	
	//취소버튼 동작
	$("#btnCancel").click(function() {
		history.go(-1);
	});
});
</script>

<style type="text/css">
#content {
	width: 100%;
}

.container{
width: 900px;

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

.main{
background-color: #A8201A;
color: #FFFFFF;
width:200px;
}


</style>

<div class="container">

<h3>게시글 쓰기</h3>
<hr>

<div>
<form action="/admin/writenot" method="post">

<table class="table table-bordered">
<tr><td class="main">아이디번호</td><td><%=session.getAttribute("u_no") %></td></tr>
<tr><td class="main">제목</td><td><input type="text" name="n_title" style="width:100%"/></td></tr>
<tr><td class="main" colspan="2">본문</td></tr>
<tr><td colspan="2"><textarea id="n_content" name="n_content"></textarea></td></tr>
</table>

</form>
</div>

<div class="text-center">	
	<button type="button" id="btnWrite" class="normalbtn">작성</button>
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
