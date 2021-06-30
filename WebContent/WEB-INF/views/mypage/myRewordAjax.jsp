<%@page import="dto.Refunds"%>
<%@page import="dto.Participation"%>
<%@page import="dto.Payback"%>
<%@page import="dto.Payment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/session/sessionCheck.jsp" %>
<%
List<Refunds> reqRefundsPeriodList = (List<Refunds>)request.getAttribute("reqRefundsPeriodList"); // 선택된기간 누적상금액
List<Payment> reqPaymentPeriodList = (List<Payment>)request.getAttribute("reqPaymentPeriodList"); // 선택된기간 누적결재액
List<Payback> reqPaybackPeriodList = (List<Payback>)request.getAttribute("reqPaybackPeriodList"); // 선택된기간 누적환급액

int reqRefundsTotal = (int)request.getAttribute("reqRefundsTotal"); // 선택된기간 상금합계
int reqPaymentTotal = (int)request.getAttribute("reqPaymentTotal"); // 선택된기간 결제합계
int reqPaybackTotal = (int)request.getAttribute("reqPaybackTotal"); // 선택된기간 환급합계

%>	

<script type="text/javascript">
$(document).ready(function(){

	/* 결재 버튼 클릭시 */
	$("#paymentBtn").click(function(){
		$("#refundTableDiv").css("display", "none")
		$("#paybackTableDiv").css("display", "none")
		$("#paymentTableDiv").css("display", "block");
	})

	/* 상금 버튼 클릭시 */
	$("#refundsBtn").click(function(){
		$("#paymentTableDiv").css("display", "none")
		$("#paybackTableDiv").css("display", "none")
		$("#refundTableDiv").css("display", "block");
	})
	
	/* 환급 버튼 클릭시 */
	$("#paybackBtn").click(function(){
		$("#paymentTableDiv").css("display", "none")
		$("#refundTableDiv").css("display", "none")
		$("#paybackTableDiv").css("display", "block");
	})

})
</script>

<!----------html 태그 시작 --------- -->

<!-- 금전거래 타임라인 선택버튼  -->
<div id="btn-group">
	<button id="refundsBtn" class="timelineBtn">상금내역</button>
	<button id="paymentBtn" class="timelineBtn">결재내역</button>
	<button id="paybackBtn" class="timelineBtn">환급내역</button>
</div>


<!-------------상금테이블---------------- -->
<div id="refundTableDiv">	
<table class="type09">
  <thead>
  <tr>
    <th colspan="4">타임라인</th>
  </tr>
  </thead>
  
  <tbody> 
  <% if(reqRefundsPeriodList.get(0).getPaNo() == 0 ) { %> <%--데이터가 없으면 --%>
 	  <tr>
	    <th scope="row" colspan="1">상금내역</th>   
	    <td colspan="3"> 해당 기간의 상금 내역은 존재하지 않습니다. <br>
	    </td>
	  </tr> 			
  <% } else{ %>
	  <%for(int i =0; i < reqRefundsPeriodList.size(); i++) {%> 
	  <tr>
	    <th scope="row" colspan="1"> <%=reqRefundsPeriodList.get(i).getPaybDate() %><br>
	    </th>   
	    <td colspan="3">획득상금 <%=reqRefundsPeriodList.get(i).getReAmount() %> (원) 을 획득했습니다.<br>
	    상금내역 : <%=reqRefundsPeriodList.get(i).getPaybReason() %></td>
	  </tr>
	  <% } %>
  <% } %>
  </tbody>
</table>
</div>

<!-------------결재테이블---------------- -->
<div id="paymentTableDiv" >	

<table class="type09">
  <thead>
  <tr>
    <th colspan="4">타임라인</th>
  </tr>
  </thead>
  
  <tbody> 
  <% if(reqPaymentPeriodList.get(0).getuNo() == 0) { %> <%--데이터가 없으면 --%>
 	  <tr>
	    <th scope="row" colspan="1">결제내역</th>   
	    <td colspan="3"> 해당 기간의 결제 내역은 존재하지 않습니다. <br>
	    </td>
	  </tr> 			
  <% } else{ %>
	  <%for(int i =0; i < reqPaymentPeriodList.size(); i++) {%> 
  	   <tr>
  	    <th scope="row" colspan="1"> <%=reqPaymentPeriodList.get(i).getPaymDate() %><br>
	    </th>   
	    <td colspan="3">결재금액 <%=reqPaymentPeriodList.get(i).getPaymAmount() %> (원) 을 결재했습니다.<br>
	    결재내역 : <%=reqPaymentPeriodList.get(i).getPaymName() %></td>
	  </tr>
	  <% } %>
  <% } %>
  </tbody>
</table>
</div>

<!-------------환급테이블---------------- -->
<div id="paybackTableDiv" >	

<table class="type09">
  <thead>
  <tr>
    <th colspan="4">타임라인</th>
  </tr>
  </thead>
  
  <tbody> 
  <% if(reqPaybackPeriodList.get(0).getuNo() == 0) { %> <%--데이터가 없으면 --%>
 	  <tr>
	    <th scope="row" colspan="1">환급내역</th>   
	    <td colspan="3"> 해당 기간의 환급 내역은 존재하지 않습니다. <br>
	    </td>
	  </tr> 			
  <% } else{ %>
	  <%for(int i =0; i < reqPaybackPeriodList.size(); i++) {%> 
   	   <tr>
  	    <th scope="row" colspan="1"> <%=reqPaybackPeriodList.get(i).getPaybDate() %><br>
	    </th>   
	    <td colspan="3">환급금액 <%=reqPaybackPeriodList.get(i).getPaybAmount() %> (원) 을 환급했습니다.<br>
	    결재내역 : <%=reqPaybackPeriodList.get(i).getPaybReason() %></td>
	  </tr>
	  <% } %>
  <% } %>
  </tbody>
</table>
</div>

<!-- 하단 상금 관련 토탈 div 영역  -->
<div style="height: 30px;"></div>
<div id="totalTrade">

<table class="type09">
  <thead>
  <tr>
    <th colspan="4">타임라인 합계</th>
  </tr>
  </thead> 
  <tbody> 
 	  <tr>   
	  </tr> 
	  <tr>			    
	   <td><strong>상금 : <%=reqRefundsTotal %> (원)</strong></td>
	   <td><strong>결제 : <%=reqPaymentTotal %> (원)</strong></td>
	   <td><strong>환급 : <%=reqPaybackTotal %> (원)</strong></td>
	  </tr>
  </tbody>
</table>

</div>