<%@page import="dto.Challenge"%>
<%@page import="dto.Mypage"%>
<%@page import="dto.Member"%>
<%@page import="dto.Refunds"%>
<%@page import="dto.Participation"%>
<%@page import="dto.Payback"%>
<%@page import="dto.Payment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/session/sessionCheck.jsp" %>
<%
	Member memberInfo =(Member) request.getAttribute("memberInfo");
	Mypage mypageInfo = (Mypage) request.getAttribute("mypageInfo");
	List<Challenge> challList =(List)request.getAttribute("challList");
	
	List<Payment> paymentList = (List<Payment>)request.getAttribute("paymentList"); // 유저 누적 결재액
	List<Payback> paybackList = (List<Payback>)request.getAttribute("paybackList"); // 유저 누적 환급액
	List<Participation> parList = (List<Participation>)request.getAttribute("parList"); //유저 참가챌린지
	List<Refunds> refundsList = (List<Refunds>)request.getAttribute("refundsList"); // 전체 누적상금액
		
	List<Refunds> reqRefundsList = (List<Refunds>)request.getAttribute("reqRefundsList"); // 월별누적상금액
	List<Payment> reqPaymentList = (List<Payment>)request.getAttribute("reqPaymentList"); // 월별누적결재액
	List<Payback> reqPaybackList = (List<Payback>)request.getAttribute("reqPaybackList"); // 월별누적환급액
	
	int refundsTotal = (int)request.getAttribute("refundsTotal"); //누적 총상금
	int paymentTotal = (int)request.getAttribute("paymentTotal"); //누적 결재액
	int refundsMonthTotal = (int)request.getAttribute("refundsMonthTotal"); //당월누적 상금액
	int paymentMonthTotal = (int)request.getAttribute("paymentMonthTotal"); //당월누적 결재액
	int paybackMonthTotal = (int)request.getAttribute("paybackMonthTotal"); //당월누적 환급액

%>

<script type="text/javascript">
$(document).ready(function(){
	/* 현재 월을 구하는 자바스크립트 */
	var now = new Date();
	var month = now.getMonth() + 1;
	console.log(now);
	console.log(month);
	
	$(".nowMonthRe").text(month + "월상금");
	$(".nowMonthPa").text(month + "월결재");
	/*------------------------------------  */
	
	$(document).ready(function(){
		$("#periodBtn").click(function(){
			console.log("Clicked")
			$.ajax({
				type: "get"
				,url: "/mypage/reword/ajax"
				,data: {
					yearSelected:$("#yearSelected").val()
					,monthSelected:$("#monthSelected").val()
				}
				,dataType: "html"
				,success: function(res){
					$("#periodTradeDiv").html(res)
				}
				,error: function(){
					console.log("AJA실패")
				}
			});
		})

	})
})
</script>


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


<style type="text/css">
/* 금전거래 타임라인 table css  */
table.type09 {
	border-collapse: collapse;
	text-align: left;
	line-height: 1.35;

}
table.type09 thead th {
	padding: 10px;
	font-weight: bold;
	vertical-align: top;
	color: #143642;
	font-size: large;
	border-bottom: 3px solid #143642;
}
table.type09 tbody th {
	width: 150px;
	padding: 10px;
	font-weight: bold;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
	background: #f3f6f7;
}
table.type09 td {
	width: 350px;
	padding: 10px;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
}

/*-----------------------------  */
</style>


<style type="text/css">

/* 버튼과 거래내역 타임라인 */
#periodTradeDiv{
	height: 400px;
}

#paybackTableDiv, #paymentTableDiv. #refundTableDiv{
	overflow: auto; /* 거래내역이 많으면 스크롤나타나게 설정  */
}

/* 테이블 보이는지 여부 설정 */
#paymentTableDiv, #paybackTableDiv{
	display: none;
}

/* 버튼 css 부여 */
#btn-group{
	margin-top: 12px;
	text-align: center;
}


#btn-group .timelineBtn {
  background-color: #8C8C8C; /* Green */
  color: white;
  padding: 3px 8px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 13px;
  cursor: pointer;
  border-radius: 5px;
}


#btn-group .timelineBtn:hover {
  background-color: #143642;
}
/* -------------------  */
</style>


<!----------html 태그 시작 --------- -->

<h2>회원상금</h2>
<table class="type09">
  <thead>
  <tr>
    <th colspan="4"><%=memberInfo.getNick()%>님의 상금/결재 정보</th>
  </tr>
  </thead>
  
  <tbody>
  <tr>
    <th scope="row">누적상금</th>   
    <td><%=refundsTotal %> (원)</td>
  	<th scope="row" class="nowMonthRe">월상금</th>
  	<td><%=refundsMonthTotal %> (원)</td>
  </tr>
  
  <tr>
    <th scope="row">누적결재</th>   
    <td><%=paymentTotal %> (원)</td>
  	<th scope="row" class="nowMonthPa">월결재</th>
  	<td><%=paymentMonthTotal %> (원)</td>
  </tr>
  
  <tr>
    <th scope="row">거래내역</th>   
  	<td colspan="3">
  		<select name="yearSelected" id ="yearSelected">
  			<option value="2019">2019</option>
  			<option value="2020">2020</option>
  			<option value="2021">2021</option>  		
  		</select>
  		<select name="monthSelected" id ="monthSelected">
  			<option value="01">1</option>
  			<option value="02">2</option>
  			<option value="03">3</option>
  			<option value="04">4</option>
  			<option value="05">5</option>
  			<option value="06">6</option>  		
  			<option value="07">7</option>  		
  			<option value="08">8</option>  		
  			<option value="09">9</option>  		
  			<option value="10">10</option>  		
  			<option value="11">11</option>  		
  			<option value="12">12</option>  		
  		</select>
		<button id="periodBtn">확인</button>
		<span style="font-size: 12px;"> "기간을 선택하고 타임라인을 확인하세요."</span>
  	</td>
  </tr>	
  </tbody>
</table>
	
<!--------------------------------------------------------  -->




<div id="periodTradeDiv"> <!-- 기간별 ajax데이터가 들어갈 전체 div  -->

<!-- 금전거래 타임라인 선택버튼  -->
<div id="btn-group">
	<button id="refundsBtn" class="timelineBtn">상금내역</button>
	<button id="paymentBtn" class="timelineBtn">결재내역</button>
	<button id="paybackBtn" class="timelineBtn">환급내역</button>
</div>

<!-- 처음 접속시 나타나는 상금 내역 -->
<div id="refundTableDiv">	
<table class="type09">
  <thead>
  <tr>
    <th colspan="4">타임라인</th>
  </tr>
  </thead>
  
  <tbody> 
  <% if(reqRefundsList.get(0).getPaNo() == 0) { %> <%--데이터가 없으면 --%>
 	  <tr>
	    <th scope="row" colspan="1">상금내역</th>   
	    <td colspan="3"> 해당 기간의 상금 내역은 존재하지 않습니다. <br>
	    </td>
	  </tr> 			
  <% } else{ %>
	  <%for(int i =0; i < reqRefundsList.size(); i++) {%> 
	  <tr>
	    <th scope="row" colspan="1"> <%=reqRefundsList.get(i).getPaybDate() %><br>
	    </th>   
	    <td colspan="3">획득상금 <%=reqRefundsList.get(i).getReAmount() %> (원) 을 획득했습니다.<br>
	    상금내역 : <%=reqRefundsList.get(i).getPaybReason() %></td>
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
  <% if(reqPaymentList.get(0).getuNo() == 0) { %> <%--데이터가 없으면 --%>
 	  <tr>
	    <th scope="row" colspan="1">결제내역</th>   
	    <td colspan="3"> 해당 기간의 결제 내역은 존재하지 않습니다. <br>
	    </td>
	  </tr> 			
  <% } else{ %>
	  <%for(int i =0; i < reqPaymentList.size(); i++) {%>   
  	   <tr>
  	    <th scope="row" colspan="1"> <%=reqPaymentList.get(i).getPaymDate() %><br>
	    </th>   
	    <td colspan="3">결재금액 <%=reqPaymentList.get(i).getPaymAmount() %> (원) 을 결재했습니다.<br>
	    결재내역 : <%=reqPaymentList.get(i).getPaymName() %></td>
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
  <% if(reqPaybackList.get(0).getuNo() == 0) { %> <%--데이터가 없으면 --%>
 	  <tr>
	    <th scope="row" colspan="1">환급내역</th>   
	    <td colspan="3"> 해당 기간의 환급 내역은 존재하지 않습니다. <br>
	    </td>
	  </tr> 			
  <% } else{ %>
	  <%for(int i =0; i < reqPaybackList.size(); i++) {%> 	  
   	   <tr>
  	    <th scope="row" colspan="1"> <%=reqPaybackList.get(i).getPaybDate() %><br>
	    </th>   
	    <td colspan="3">환급금액 <%=reqPaybackList.get(i).getPaybAmount() %> (원) 을 환급했습니다.<br>
	    결재내역 : <%=reqPaybackList.get(i).getPaybReason() %></td>
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
	   <td><strong>상금 : <%=refundsMonthTotal %> (원)</strong></td>
	   <td><strong>결제 : <%=paymentMonthTotal %> (원)</strong></td>
	   <td><strong>환급 : <%=paybackMonthTotal %> (원)</strong></td>
	  </tr>
  </tbody>
</table>

</div>

</div>

