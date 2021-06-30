<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="container" style="width : 900px;">
	<div class="detail">
		<h4 id="title">카테고리</h4>
		<form id="detailform" action="/search?key=<%=request.getParameter("key")%>" method = "get">
		<div class="checkdiv">
		<div><label class="checkbox-inline"><input type="checkbox" id = "all" class="checkbox" name="ca_no" value = "0" checked="checked">전체</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "exercise" class="checkbox" name="ca_no" value = "1">운동</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "selfincrement" class="checkbox" name="ca_no" value = "2">자기관리</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "habbits" class="checkbox" name="ca_no" value = "3">생활습관</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "hobby" class="checkbox" name="ca_no" value = "4">취미</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "study" class="checkbox" name="ca_no" value = "5">공부</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "reading" class="checkbox" name="ca_no" value = "6">독서</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "language" class="checkbox" name="ca_no" value = "7">외국어</label></div>
		<div><label class="checkbox-inline"><input type="checkbox" id = "financial" class="checkbox" name="ca_no" value = "8">외국어</label></div>
		</div>
		<div class="selectbox">
		<button type="button" class="btn btnsuccess" type="button" id="btndetail">상세검색</button>
		</div>
		</form>
	</div>
	<div class="align">
		<form id="orderform" method = "get">
		<select name = "alignby">
			<option value ="ch_create_date" selected = "selected">최근순</option>
			<option value ="ch_likes">좋아요순</option>
			<option value ="u_no">참여인원순</option>
		</select>
		</form>
	</div>