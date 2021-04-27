<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="failed">
<div class="innerbox">
<h3 id = "word"><span style="color: A8201A;"><%=request.getParameter("key") %> </span>에 대한 검색 결과가 없습니다</h3>
</div>
<div class="buttonbox">
<button type="button" onclick="location.href='/'" class="btn btn-warning btn-lg "> HOME </button>
</div>
</div>

<style type="text/css">
.failed{
 	/* border: 1px solid #ccc; */
	width : 100%;
	display: table;
	padding : 30px;
	top : -70px;
	z-index: -1;
}
.innerbox{
	display: table-cell;
	text-align: center;
	height : 180px;
	vertical-align: middle;
}
.buttonbox{
	display : table-row;
	text-align: center;
}
#word > span{
  color : red;
  font-size: larger;
}
</style>