<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String title = (String)request.getAttribute("title"); %>
<% String chWay = (String)request.getAttribute("chWay"); %>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	 startDate();
	//작성버튼 동작
	$("#btnWrite").click(function() {
		
		$("form").submit();
	});
	
	//취소버튼 동작
	$("#btnCancel").click(function() {
		history.go(-1);
	});
	//이미지 미리보기
	$('#file').change(function(){
	    setImageFromFile(this, '#preview');
	});
	
});

//미리 보기
function setImageFromFile(input, expression) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(expression).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
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
<style type="text/css">
table, td, th{
	  border: none !important;
}
td{
	font-size:27px;
}
</style>

<div class="container">


<form action="/participant/certification/write" method="post" enctype="multipart/form-data">
<table class="table table-responsive text-center">
	<tr><td colspan="2"><i class="far fa-thumbs-up"></i>&nbsp;&nbsp;<%=title %>인증등록</td></tr>
	<tr><td colspan="2" id="date"></td></tr>
	<tr><td colspan="2"><label for="file"><img id="preview" src="/resources/img/challenge.png" width="400" height="300"/></label><input type="file" accept="image/*" id="file" name="file" style="width:100%; display: none;"/></td></tr>	
	<tr><td colspan="2">인증 방법(인증 사진을 올려주세요) <br><%=chWay %></td></tr>
</table>
</form>
<div class="text-center">	
	<button type="button" id="btnWrite" class="btn btn-info">작성</button>
	<button type="button" id="btnCancel" class="btn btn-danger">취소</button>
</div>
</div>


<%@ include file="/WEB-INF/views/layout/footer.jsp"%>