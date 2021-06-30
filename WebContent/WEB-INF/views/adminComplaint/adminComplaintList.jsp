<%@page import="dto.AdminComplaint" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% List<AdminComplaint> list = (List) request.getAttribute("complaintList"); %>    
<% List<AdminComplaint> searchComplaint = (List) request.getAttribute("searchComplaint"); %> 
      
<%@ include file="/WEB-INF/views/layout/bootAdminHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootAdminNavigation.jsp" %>


<script type="text/javascript">
$(document).ready(function() {
	$('#complaint').css({"background":"#EC9A29"})
	$('#complaint').nextAll().css({"background":"#FFFFFF"})
	// + 버튼 누르면 신고등록 페이지로 이동
	
		
	//검색 버튼 동작
	$("#btnSearch").click(function(){
		$("form").submit();
	});
});
</script>

<style type="text/css">
@media (min-width: 900px) {
	.container {
	   
	   width: 900px;
	}
}

#move:hover tbody tr:hover td{
background:#143642;
color:#FFFFFF;
}

th{
background-color: #A8201A;
}

.searchform{
float:right; 
margin:0px 3px 5px 3px;
}

#btnSearch{
background-color: #143642;
color: white;
border-radius: 4px;
}

</style>


<div class="container">		

<!-- 페이지 타이틀 -->
<div class="container">
<div class="left" style="font-size:30px; font-weight:bold;">신고 목록 <a href="/admin/complaint/insert"><i class="fas fa-plus"></i></a></div>
	<div><span>&nbsp</span></div>
	
<div class="searchform">
	<form class="seachform" id="searchForm" action="/admin/complaint/list" method="get">
	
	 	<select name="searchType">
			<%-- <option ${(param.searchType == "comNo")?"selected":"" } value="comNo">번호</option>
			<option ${(param.searchType == "caTitle")?"selected":"" } value="caTitle">카테고리</option> --%>
			<option ${(param.searchType == "ch_title")?"selected":"" } value="ch_title">챌린지제목</option>
			<option ${(param.searchType == "ch_content")?"selected":"" } value="ch_content">챌린지내용</option>
			<option ${(param.searchType == "com_manage")?"selected":"" } value="com_manage">조치내역</option>
		</select>
    	
	 
	  <input type="text" name="searchKeyword"  value="${param.searchKeyword}" />
	    
			 <button type="submit" id="btnSearch">검색</button>
	    </form>    
	  </div>
	
	  

	
	
<!-- 목록 테이블 -->
<div class="card-body center-block">
  <div class="row">	
	<table class = "table table-striped table-hover table-condensed" id="move">
					
	<!-- 항목 -->
	<thead>
	<tr>
		<th>번호</th>
		<th>신고날짜</th>
		<th>챌린지 제목</th>
		<th>카테고리</th>
		<th>신고내용</th>
		<th>경고횟수</th>
		<th>조치내역</th>
	</tr>
	</thead>
					
	<!-- 내용 -->
	<tbody>		
	
	<%	for(int i=0; i<searchComplaint.size(); i++) { %>
	<tr>
		<td><%=searchComplaint.get(i).getComNo() %></td>
		<td><%=searchComplaint.get(i).getComDate() %></td>
		<td><a href="/admin/complaint/view?comNo=<%=searchComplaint.get(i).getComNo() %>"><%=searchComplaint.get(i).getChTitle() %></a></td>
		<td><%=searchComplaint.get(i).getCaTitle() %></td>
		<td><%=searchComplaint.get(i).getComContent() %></td>
		<td><%=searchComplaint.get(i).getChCaution() %></td>
		<td><%=searchComplaint.get(i).getComManage() %></td>
	</tr>
	<% } %>
	</tbody>			
   </table>
  </div> <!-- row -->
</div> <!-- card-body -->
	
<!-- 검색창 -->

	</div> <!-- row -->
</form>
</div><!-- container --> 

<%@ include file="/WEB-INF/views/adminComplaint/adminComplaintPaging.jsp" %>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

