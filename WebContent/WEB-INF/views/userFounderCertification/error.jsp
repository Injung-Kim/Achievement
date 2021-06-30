<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/bootHeader.jsp" %>
<%@ include file="/WEB-INF/views/layout/bootNavigation.jsp" %>
<%String text = (String)request.getAttribute("text"); %>

<script type="text/javascript">
$(document).ready(function() {
	alert('<%=text%>');
	history.back(-1);
})
</script>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>