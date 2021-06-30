<%@page import="dto.AdminComplaint" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% AdminComplaint complaint = (AdminComplaint) request.getAttribute("updateComplaint"); %>
<% int caution = (Integer)request.getAttribute("chUcaution"); %>
<% int cntChCaution = (Integer)request.getAttribute("cntChCaution"); %>
<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>


<script type="text/javascript">
$(document).ready(function() {

	//완료버튼 누르면 폼 제출
	$("#btnComplete").click(function() {
		$("form").submit();
	});
	
	//취소버튼 누르면 상세조회 페이지로 이동
	$("#btnCancle").click(function() {
		history.go(-1)
	});
	
});
</script>



<style type="text/css">
@media (min-width: 900px) {
  .container {
    font-family: "Noto Sans KR", sans-serif !important;
    width: 900px;
  }
}
</style>


<div class="container">

<!-- 페이지 타이틀 -->	
<div class="jumbotron">
	<h2 class="display-3">신고 수정</h2>
</div><!-- jumbotron -->


<!-- 수정 폼 -->
<form action="/admin/complaint/update?comNo=<%=request.getParameter("comNo") %>" method="post">
<div class="card-body center-block">
	<div class="table-responsive">
	<table class="table">
		<tbody>
		<tr style="line-height:32px;">
			<tr>
			<td>챌린지 제목</td>
			<td><input type="text" name="chTitle" class="form-control" value="<%=complaint.getChTitle() %>" readonly></td>                               
 			<td>챌린지 경고횟수</td>
			<td><input type="text" name="chCaution" class="form-control" value="<%=cntChCaution %>" readonly></td>                              
     		</tr>                                                 
  
   			<tr>
   			<td>카테고리</td>
 			<td><input type="text" name="caTitle" class="form-control" value="<%=complaint.getCaTitle() %>" readonly></td>                             
			<td>개설자 아이디</td>
 			<td><input type="text" name="chUid" class="form-control" value="<%=request.getAttribute("chUid") %>" readonly></td>                                  			                              
            </tr>                                                          
            
            <tr>
            <td>챌린지 내용</td>                       
 			<td><input type="text" name="chContent" class="form-control" value="<%=complaint.getChContent() %>" readonly></td>                      
            <td>개설자 경고횟수</td>
 			<td><input type="text" name="chUcaution" class="form-control" value="<%=caution%>" readonly></td>                                                                            
            </tr>                        
            
            <tr>
            <td>챌린지 참가비</td>                   
            <td><input type="text" name="chMoney" class="form-control" value="<%=complaint.getChMoney() %>" readonly></td>  
            <td>신고번호</td>
			<td><input type="text" name="comNo" class="form-control" value="<%=complaint.getComNo() %>" readonly></td>                        
            </tr>   
            
            <tr> 
            <td>챌린지 개설일</td>                              
  			<td><input type="text" name="chCreateDate" class="form-control" value="<%=complaint.getChCreateDate() %>" readonly></td>                      
            <td>신고날짜</td>                               
  			<td><input type="text" name="comDate" class="form-control" value="<%=complaint.getComDate() %>" readonly></td>                
            </tr>   
            
            <tr>
            <td>챌린지 시작일</td>                   
            <td><input type="text" name="chStartDate" class="form-control" value="<%=complaint.getChStartDate() %>" readonly></td>                              
 			<td>신고회원 아이디</td>
			<td><input type="text" name="comUid" class="form-control" value="<%=complaint.getComUid() %>" readonly></td>                                                                      
			</tr>                    
            
            <tr>
            <td>챌린지 종료일</td>                                
   			<td><input type="text" name="chEndDate" class="form-control" value="<%=complaint.getChEndDate() %>" readonly></td>                             
   			<td>신고회원 이름</td>                               
  			<td><input type="text" name="comUname" class="form-control" value="<%=complaint.getComUname() %>" readonly></td>                                      
			</tr> 
            
            
            <tr>
            <td>인증 빈도</td>                                
   			<td><input type="text" name="cecTitle" class="form-control" value="<%=complaint.getCecTitle() %>" readonly></td>
   			<td>신고내용</td>                              
    		<td><input type="text" name="comContent" id="address1" class="form-control" value="<%=complaint.getComContent() %>" readonly></td>                                                 
 			</tr> 
 			
 			<tr>
 			<td>인증가능시간</td>                   
            <td><input type="text" name="chStartTime" size="5" class="form-control" readonly value="<%=complaint.getChStartTime() %>" style="display:inline-block;width:150px;">
            <input type="text" name="chEndTime" size="5" class="form-control" readonly value="<%=complaint.getChEndTime() %>" style="display:inline-block;width:150px;"></td>
            <td>관리자 의견</td>                              
    		<td><input type="text" name="comAdminContent" class="form-control" value="<%=complaint.getComAdminContent() %>" ></td>        
 			</tr>                          
            
            <tr>
            <td>인증방법</td>                                
   			<td><input type="text" name="chWay" class="form-control" value="<%=complaint.getChWay() %>" readonly></td>
   			<td>조치내역</td>                              
    		<td><select name="comManage" class="form-control" value="<%=complaint.getComManage() %>" disabled>
				<option>Y</option>
				<option>W</option>
				<option>N</option>
				</select>
			</td>                                              
 			</tr>
		</tbody>                          
	</table>                       
	</div><!-- table-responsive 끝-->                   
</div><!-- card-body 끝 -->
</form>
<p></p>

	<!-- 완료, 취소 버튼 -->
	<div class="text-center">
		<button type="submit" id="btnComplete" class="btn btn-default">완료</button>
		<button id="btnCancle" class="btn btn-default">취소</button>
	</div>

</div><!--.container 끝-->

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

