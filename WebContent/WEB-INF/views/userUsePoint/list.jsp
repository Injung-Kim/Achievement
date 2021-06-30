<%@page import="java.util.List"%>
<%@page import="dto.RefundPoint"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<%List<RefundPoint> list = (List<RefundPoint>)request.getAttribute("list"); %>
<div class="container">
<div id="tableHeader">
	<div><span>&nbsp</span></div>
	<div class="left" style="font-size:30px; font-weight:bold;">포인트 사용 </div>
	<div><hr></div>
</div>


<table class="table table-striped table-hover table-condensed text-center">
<tr>
	<th style="width: 10%; text-align:center;">실패 챌린지번호</th>
	<th style="width: 40%; text-align:center;">실패 챌린지 제목</th>
	<th style="width: 10%; text-align:center;">환불 가능 금액</th>
	<th style="width: 10%; text-align:center;">예금주</th>
	<th style="width: 10%; text-align:center;">은행</th>
	<th style="width: 20%; text-align:center;">계좌번호</th>
</tr>
<%	for(int i=0; i<list.size(); i++) { %>
<tr>
	<td><%=list.get(i).getChNo() %></td>
	<td>
		<a href="/user/use/point/view?chNo=<%=list.get(i).getChNo() %>">
		<%=list.get(i).getChTitle() %>
		</a>
	</td>
	<td><%=list.get(i).getRefundableAmount() %></td>
	<td><%=list.get(i).getPaybRefundHolder() %></td>
	<td><%=list.get(i).getPaybRefundBank() %></td>
	<td><%=list.get(i).getPaybRefundAccount() %></td>
	
</tr>
<%	} %>
</table>


</div>


<%@ include file="/WEB-INF/views/layout/paging.jsp" %>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>