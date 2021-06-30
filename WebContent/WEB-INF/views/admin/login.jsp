<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %><br>  


<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %><br><br> 
 


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<%
String id = "";
String checked = "";

Cookie[] cookies = request.getCookies(); 
for(Cookie c : cookies){
	if("ID".equals(c.getName())){
		id = c.getValue();
	}else if("CHECK".equals(c.getName())){
		checked = c.getValue();
	}
}



%>
<title>로그인 폼</title>
<style type="text/css">
	form {
		width: 400px;
		margin: 0 auto;
	}
</style>

<script type="text/javascript">
$(document).ready(function() {
	//페이지 첫 접속 시 입력창으로 포커스 이동
	$("input").eq(0).focus();
	
	//패스워드 입력 창에서 엔터 입력 시 form submit
	$("input").eq(1).keydown(function(key) {
		if(key.keyCode == 13) {
			$(this).parents("form").submit();
		}
	})

	//로그인 버튼 클릭 시 form submit
	$("#btnLogin").click(function() {
		$(this).parents("form").submit();
	})
	
	//취소 버튼 누르면 뒤로가기
	$("#btnCancel").click(function() {
		history.go(-1);
	})

});
</script>
</head>
 <body>

<div class="container">

	<form class="form-horizontal" action="/admin/login" method="post">
	  
	  <div class="form-group">
	    <label for="userid" class="col-sm-2 control-label">ID</label>
	    <div class="col-sm-10">
	    <%--쿠키에 값이 있을경우 --%>
	    <%if(id!="" && !"".equals(id)){%>
	    	<input type="text" class="form-control" id="userid" name="userid" placeholder="ID" value="<%=id%>"/>	 
	    <%--쿠키에 값이 없을경우 --%>
	    <%}else{ %>
	    	<input type="text" class="form-control" id="userid" name="userid" placeholder="ID" value=""/>
	    <%} %>
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <label for="userpwd" class="col-sm-2 control-label">Password</label>
	    <div class="col-sm-10">
	      <input type="password" class="form-control" id="userpwd" name="userpwd" placeholder="Password"/>
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <div class="checkbox">
	        <label>
	          <%--쿠키에 값이 있을 경우 --%>
	          <%if(checked!="" && !"".equals(checked)){ %>
	          <input type="checkbox" id="idRemember" name="idRemember" value="check" checked="checked"> 아이디 기억하기
	        	<%--쿠키에 값이 없을 경우 --%>
	        	<%}else{ %>
	        	<input type="checkbox" id="idRemember" name="idRemember" value="check"> 아이디 기억하기
	        	<%} %>
	        </label>
	      </div>
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <button type="button" id="btnLogin" class="btn btn-primary">로그인</button>
	      <button type="button" id="btnCancel" class="btn btn-danger">뒤로가기</button>
	    </div>
	  </div>
</form>
</div>

	
  </body>
</html>