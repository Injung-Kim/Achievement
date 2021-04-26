<%@page import="dto.ChallengeList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<% List<ChallengeList> list = (List<ChallengeList>)request.getAttribute("searchWord"); %>
<script type="text/javascript">
$(document).ready(function(){
	$('#key').attr("value","<%=request.getParameter("key")%>")
})
</script>
<link rel="stylesheet" href="/resources/css/searchView.css">
<script type="text/javascript">
$(document).ready(function(){
	$('.imgarea  img').click(function(){
	 	 $(this).css("border","2px dotted #8C8C8C")
	 })
	 
	 //상세검색 단추를 누르면 체크한 카테고리별, 정렬기준별 상세조회
	$('#btndetail').click(function(){ 
	//카테고리 배열로 저장
	 var lists = [];
	 $("input[name='ca_no']:checked").each(function(i){
		 if($(this).val() === "0"){
		 lists = ["1","2","3","4","5","6","7"]
		 }else{
		 lists.push($(this).val())
		 }
	 })
	//정렬기준
	 var select = $("select[name='alignby']").val() 
	 console.log(select)
	 $.ajax({
		 type : "post"
		 ,url : "/search?key=<%=request.getParameter("key")%>"
	 	 , data :{
	 		 'ca_no':lists
	 		 ,'alignby': select
	 	 }
	 	 , dataType : "html" 
	 	 , success : function(result){
	 		console.log("ajax성공") 
			$('.result').hide()
			//$('.detailResult').css("display"=="none")
			$('.detailResult').html(result)
	 	 }
	 	 , error : function(request, status, error){
	 		 console.log("ajax실패")
	 		 alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	 	 }
		})
	 })//btn click event end
	 
	 
	$("input:checkbox:not([id = 'all'])").click(function(){
		$("input:checkbox[id='all']").attr('checked',false)
	})// 전체 이외 카테고리 체크할 경우 전체 체크박스 체크 해제
	$("input:checkbox[id='all']").click(function(){
		$("input:checkbox:not([id = 'all'])").attr('checked',false)
	})// 전체 체크박스 체크할 경우 이외 카테고리 체크 해제 
	
	 $("select[name='alignby']").change(function(){ //정렬박스 변경될때 실행
		 console.log("select changed")
		 var lists = [];
		 $("input[name='ca_no']:checked").each(function(i){
			 if($(this).val() === "0"){
			 lists = ["1","2","3","4","5","6","7"]
			 }else{
			 lists.push($(this).val())
			 }
		 })
		 var order = $("select[name='alignby']").val()
		  $.ajax({
			 type : "post"
			 ,url : "/search?key=<%=request.getParameter("key")%>"
		 	 , data :{
		 		 'ca_no':lists
		 		 ,'alignby': order
		 	 }
		 	 , dataType : "html" 
		 	 , success : function(result){
		 		console.log("ajax성공") 
				$('.result').hide()
				//$('.detailResult').css("display"=="none")
				$('.detailResult').html(result)
		 	 }
		 	 , error : function(request, status, error){
		 		 console.log("ajax실패")
		 		 alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		 	 }
		  })
	 })
})//ready function() end
</script>
<body>
<%@ include file="/WEB-INF/views/userSearch/detailCheckbox.jsp" %>
	<div class="detailResult" ></div>
	<div class="result">
<% for(int i = 0; i< list.size(); i++){ %>
	<div class = "imgarea" >
	<%if(list.get(i).getChStoredName().contains("저장")){ %>
		<a class="scroll" href="/user/challenge/view?chNo=<%=list.get(i).getChNo()%>"><img src="/resources/img/AchievementWhite.png" width="225px" height="225px" alt="챌린지 임시페이지"/></a>
	<%} else {%>
		<a class="scroll" href="/user/challenge/view?chNo=<%=list.get(i).getChNo()%>"><img src="/upload/<%=list.get(i).getChStoredName() %>" alt="챌린지 저장페이지"/></a>
	<%} %>
			<p class="title"><%=list.get(i).getChTitle() %></p>
			<p class="status">
			<%if(list.get(i).getuId().contains("manager")){ %>
			<span class="name"><i class="fas fa-cog"></i>공식</span>
			<%}else{ %>
			<span class="name"><%=list.get(i).getuId() %> </span>
			<%} %>
	 		<span class="like"><i class="far fa-thumbs-up"></i><%=list.get(i).getChLikes() %></span>
			</p>
	</div>
<%} %>
	<%@ include file="/WEB-INF/views/userSearch/searchPaging/searchPaging.jsp" %>
	</div>
</div>
</body>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>