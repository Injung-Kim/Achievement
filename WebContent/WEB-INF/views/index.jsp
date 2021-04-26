<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>



<div style="background-color: #2E75B6;" class="floating">
	<div id="floatingBest"><a href="#resultAjax">베스트챌린지</a></div>
	<div id="floatingPopularity"><a href="#resultAjax">인기 챌린지</a></div>
	<div id="floatingNew"><a href="#resultAjax">신규 챌린지</a></div>
	<div id="floatingChallenge"><a href="#resultAjax">챌린지</a></div>
</div>


<section id="summery">
	<div id="summeryDiv">
		<img src="/resources/img/AchievementWhite.png" width="400px" height="300px">
	</div>
	<div>
		'어치브먼트는-전국민 목표달성 프로젝트'는 4조 세미 프로젝트에서 서비스하는 목표 달성 웹페이지이다. 참가자들은 책읽기, 운동하기, 영어 공부하기 등 같은 목표에 원하는 만큼 금액을 걸고 참가한다. 정해진 기간 동안 같은 목표를 선택한 사람들과 목표를 수행하면서 인증샷을 남기는데, 목표 달성률이 50% 이상이면 걸었던 돈을 모두 돌려받는다. 목표 달성률이 60%로 걸었던 금액과 10포인트를 70%이상이면 20포인트 80%이상이면 30포인트 90프로 이상이면 40포인트 95프로 이상이면 50포인트를 받고 포인트는 자신이 실패한 챌린지의 상금을 돌려받을 수 있다. 하지만 달성률이 50%보다 낮으면 돈을 돌려 받지 못한다 참가자들이 받지 못한 돈은 다른 챌린지에서 성공한 포인트로 돌려 받을 수 있으며 10포인트당 1000원으로 책정한다.
	
	</div>
</section>
<div id="nbutton">
	<div>
		<button id="mainBest">베스트 챌린지</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="mainPopularity">인기 챌린지</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="mainNew">신규 챌린지</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="mainChallenge">챌린지</button>
	</div>
</div>

<section id="resultAjax">
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>