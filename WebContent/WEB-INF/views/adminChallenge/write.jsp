<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>
<%
List<String> cycle = (List<String>)request.getAttribute("cycle");
List<String> category = (List<String>)request.getAttribute("category");
String name = (String)request.getAttribute("name");

%>

<script type="text/javascript"
src ="/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
function submitContents(elClickedObj){
	//에디터의 내용을 #content에 반영한다
	oEditors.getById["ch_way"].exec("UPDATE_CONTENTS_FIELD",[]);
	
	try{
		//<form>태그의 submit수행
		elClickedObj.form.submit();
	}catch(e){
		
	}
}
</script>

<script type="text/javascript">
$(document).ready(function() {
	var flag1 = false
	var flag2 = false
	var flag3 = false
	var flag4 = false
	var flag5 = false
	var flag6 = false
	
	//background
	$('#challenge').css({"background":"#EC9A29"})
	
	//작성버튼 동작
	$("#btnWrite").click(function() {
		
		//스마트 에디터의 내용을 <textarea>에 적용하는 함수를 호출한다
		submitContents($("#btnWrite"))
		//<form> submit
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
	$("#ch_title").blur(function(){ //포커스를 잃었을떄
		var regex =/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s]+$/;
		if(!regex.test($(this).val())){
			$("#messageTitle").css({"color":"#d9534f"})
			$("#messageTitle").html("한글, 영어, 숫자만 가능합니다.")
			flag1 = false
		}else{
			$("#messageTitle").css({"color":"#5cb85c"})
			$("#messageTitle").html("사용하실 수 있는 제목입니다!")
			flag1 = true
		}
		
	})
	$("#ch_content").blur(function(){ //포커스를 잃었을떄
		var regex =/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s]+$/;
		if(!regex.test($(this).val())){
			$("#messageContent").css({"color":"#d9534f"})
			$("#messageContent").html("한글, 영어, 숫자만 가능합니다.")
			flag2 = false
		}else{
			$("#messageContent").css({"color":"#5cb85c"})
			$("#messageContent").html("사용하실 수 있는 내용입니다!")
			flag2 = true
		}
		
	})
	$("#ch_start_date").change(function(){
		var startDate = currentDate();
		
		var startDateArr = startDate.split('-');
		
		var endDate = $( "input[name='ch_start_date']" ).val();
	    var endDateArr = endDate.split('-');
		
	    var startDateCompare = new Date(startDateArr[0], parseInt(startDateArr[1])-1, startDateArr[2]);
        var endDateCompare = new Date(endDateArr[0], parseInt(endDateArr[1])-1, endDateArr[2]-1); //date-1은 최소한 하루에 간격은 있어야 하기 떄문에
        if(startDateCompare.getTime() > endDateCompare.getTime()) {
        	$("#messageStartDate").css({"color":"#d9534f"})
			$("#messageStartDate").html("시작 날짜를 다시 확인해주세요")
			flag3 = false
        }else{
        	$("#messageStartDate").css({"color":"#5cb85c"})
			$("#messageStartDate").html("챌린지가 시작할 수 있는 날짜입니다.")
			flag3 = true
        }
	})
	
	$("#ch_end_date").change(function(){
		var startDate = $( "input[name='ch_start_date']" ).val();
		var startDateArr = startDate.split('-');
		
		var endDate = $( "input[name='ch_end_date']" ).val(); //2017-12-09
	    var endDateArr = endDate.split('-');
		
	    var startDateCompare = new Date(startDateArr[0], parseInt(startDateArr[1])-1, startDateArr[2]);
        var endDateCompare = new Date(endDateArr[0], parseInt(endDateArr[1])-1, endDateArr[2]-1); //date -1은 최소한 하루에 간격은 있어야 하기 때문에
        if(startDateCompare.getTime() > endDateCompare.getTime()) {
        	$("#messageEndDate").css({"color":"#d9534f"})
			$("#messageEndDate").html("끝나는 날짜를 다시 확인해주세요")
			flag4 = false
        }else{
        	$("#messageEndDate").css({"color":"#5cb85c"})
			$("#messageEndDate").html("챌린지를 끝낼 수 있는 날짜입니다")
			flag4 = true
        }
        
	})
	$("#ch_start_time").change(function(){
		var startTime = $( "input[name='ch_start_time']" ).val();
		flag5 = true;
        $("#messageStartTime").css({"color":"#5cb85c"})
        $("#messageStartTime").html("챌린지를 시작할 수 있는 시간입니다.")
	})
	
	$("#ch_end_time").change(function(){
		var startTime = $( "input[name='ch_start_time']" ).val();
		var endTime = $("input[name='ch_end_time']" ).val();
		
		//시간 계산
		var startArr=startTime.split(':');
		var endArr=endTime.split(':');
		
		//초로 나누기
		var start = startArr[0]*3600 + startArr[1]*60 
		var end = endArr[0]*3600 + endArr[1]*60
		
		//끝나는 시간이 맞지 않을때
		if(end - start < 10*60){
			$("#messageEndTime").css({"color":"#d9534f"})
			$("#messageEndTime").html("시간을 다시 확인해주세요(최소 10분)")
			flag6 = false;
		}else{
			$("#messageEndTime").css({"color":"#5cb85c"})
			$("#messageEndTime").html("챌린지를 끝낼 수 있는 시간입니다.")
			flag6 = true;	
		}
    })
    
	$("input").change(function(){
		if(flag1 && flag2 && flag3 && flag4 && flag5 && flag6){
			$("#btnWrite").text("작성가능");			
			$("#btnWrite").attr("disabled", false)
			
		}else{
			$("#btnWrite").text("작성불가");	
			$("#btnWrite").attr("disabled", true)
		}	
	})
	
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
function currentDate(){
	var date = new Date();
	return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2)+"-"+("0"+date.getDate()).slice(-2);
}

</script>

<style type="text/css">
#ch_way {
	width: 88%;
}
span{
	float:left;

}
</style>

<div class="container">

<h3>챌린지 등록</h3>
<hr>

<div>
<form action="/admin/challenge/write" method="post" enctype="multipart/form-data">
<table class="table table-responsive text-center">
		<tr><td colspan="2"><label for="file"><img id="preview" src="/resources/img/AchievementWhite.png" width="400" height="300"/></label><input type="file" accept="image/*" id="file" name="file" style="width:100%; display: none;"/></td></tr>
		<tr><td>제목</td><td><input type="text" id="ch_title" name="ch_title" style="width:100%" class="form-control"/><span id="messageTitle"></span></td></tr>
		<tr><td>카테고리</td><td>
		<select style="width:100%" id="ch_category" name="ch_category" class="form-control" >
			<%for(int i=0; i<category.size(); i++){ %>
			<option value="<%=category.get(i)%>"><%=category.get(i)%></option>
			<%} %>
		</select>
		</td></tr>
		<tr><td>내용</td><td><input type="text" class="form-control" id="ch_content" name="ch_content" style="width:100%"/><span id="messageContent"></span></td></tr>
		<tr><td>참가비</td><td><input type="number" class="form-control" name="ch_money" step="1000" style="width:100%" /></td></tr>
		<tr><td>개설자</td><td><input type="text" class="form-control" id="ch_user_name" name="ch_user_name" style="width:100%" value="<%=name%>" readonly/></td></tr>
		<tr><td>시작날짜</td><td><input type="date" class="form-control" id="ch_start_date" name="ch_start_date"  style="width:100%"/><span id="messageStartDate"></span></td></tr>
		<tr><td>마감날짜</td><td><input type="date" class="form-control" id="ch_end_date" name="ch_end_date"  style="width:100%"/><span id="messageEndDate"></span></td></tr>
		<tr><td >인증빈도</td><td>
		<select style="width:100%" id="ch_cycle" name="ch_cycle" class="form-control">
			<%for(int i=0; i<cycle.size(); i++){ %>
			<option value="<%=cycle.get(i)%>"><%=cycle.get(i)%></option>
			<%} %>
		</select>
		</td></tr>
		<tr><td >인증 가능 시작 시간</td><td><input type="time" id="ch_start_time" class="form-control"  name="ch_start_time" style="width:100%"/><span id="messageStartTime"></span></td></tr>
		<tr><td >인증 가능 끝나는 시간</td><td><input type="time" id="ch_end_time" class="form-control" name="ch_end_time" style="width:100%"/><span id="messageEndTime"></span></td></tr>
		<tr><td >인증 방법</td><td colspan="2"><textarea id="ch_way" name="ch_way"></textarea></td></tr>
	</table>
</form>
</div>

<div class="text-center">	
	<button type="button" id="btnWrite" class="btn btn-info" disabled="disabled">작성불가</button>
	<button type="button" id="btnCancel" class="btn btn-danger">취소</button>
</div>
</div>

<!-- .container -->
</div>

<script type="text/javascript">
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "ch_way", //에디터가 적용될<textarea>의 ID를 입력
	sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	fCreator: "createSEditor2"
})

</script>


<%@ include file="/WEB-INF/views/layout/footer.jsp"%>