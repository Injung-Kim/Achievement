<%@page import="dto.Participation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<% String title = (String)request.getAttribute("title"); %>
<script type="text/javascript"
src ="/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
function submitContents(elClickedObj){
	//에디터의 내용을 #content에 반영한다
	oEditors.getById["complaintReason"].exec("UPDATE_CONTENTS_FIELD",[]);
	
	try{
		//<form>태그의 submit수행
		elClickedObj.form.submit();
	}catch(e){
		
	}
}
</script>
<script type="text/javascript">
$(document).ready(function() {
	
	//작성버튼 동작
	$("#btnWrite").click(function() {
		if( confirm("신고를 등록하면 수정 및 삭제가 불가능합니다.\n 등록하시겠습니까?") ) {
			//스마트 에디터의 내용을 <textarea>에 적용하는 함수를 호출한다
			submitContents($("#btnWrite"))
			//<form> submit
			$("form").submit();	
		}
		
	});
	
	//취소버튼 동작
	$("#btnCancel").click(function() {
		history.go(-1);
	});
	
});
</script>
<style type="text/css">
#complaintReason {
	width: 88%;
}
</style>
<div class="container">

<h3>신고 등록</h3>


<form action="/participant/complaint/write" method="post">
<table class="table table-responsive text-center">
		<tr><td>제목</td><td><%=title %></td></tr>
		<tr><td >신고 사유</td><td colspan="2"><textarea id="complaintReason" name="complaintReason"></textarea></td></tr>
	</table>
</form>
</div>

<div class="text-center">	
	<button type="button" id="btnWrite" class="btn btn-info">작성</button>
	<button type="button" id="btnCancel" class="btn btn-danger">취소</button>
</div>


</div>
<script type="text/javascript">
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "complaintReason", //에디터가 적용될<textarea>의 ID를 입력
	sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	fCreator: "createSEditor2"
})

</script>


<%@ include file="/WEB-INF/views/layout/footer.jsp"%>