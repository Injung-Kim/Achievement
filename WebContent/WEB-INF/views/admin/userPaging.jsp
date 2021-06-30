<%@page import="util.Paging"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	Paging paging = (Paging) request.getAttribute("paging"); %>
<% String grade = request.getParameter("grade"); %>
<% String field = request.getParameter("field"); %>
<% String search = request.getParameter("search"); %>
<div class="text-center pagination">
	
		<!-- 첫 페이지로 이동 -->
		<%	if(paging.getCurPage() != 1) { //첫 페이지가 아닐 때 보임 %>
		<button onclick="location.href='/admin/user/list?grade=<%=grade==null ? "" : grade %>&field=<%=field==null ? "" : field%>&search=<%=search==null ? "" : search %>'">&larr;</button>
		<%	} %>
		
		<!-- 이전 페이징 리스트로 가기 -->
		<%	if(paging.getStartPage() > paging.getPageCount()) { %>
		<button onclick="location.href='/admin/user/list?curPage=<%=paging.getStartPage() - paging.getPageCount() %>&grade=<%=grade==null ? "" : grade %>&field=<%=field==null ? "" : field%>&search=<%=search==null ? "" : search %>'">&laquo;</button>
		<%	} else { %>
		<button class="disabled">&laquo;</button>
		<%	} %>
		
		<!-- 이전 페이지로 가기 -->
		<%	if(paging.getCurPage() != 1) { %>
		<button onclick="location.href='/admin/user/list?curPage=<%=paging.getCurPage() - 1 %>&grade=<%=grade==null ? "" : grade %>&field=<%=field==null ? "" : field %>&search=<%=search==null ? "" : search %>'">&lt;</button>
		<%	} %>
		
		
		
		
		<!-- 페이징 리스트 -->
		<%	for(int i=paging.getStartPage(); i<=paging.getEndPage(); i++) { %>
		<%		if( i == paging.getCurPage() ) { %>
		<button class="active" onclick="location.href='/admin/user/list?curPage=<%=i %>&grade=<%=grade==null ? "" : grade%>&field=<%=field==null ? "" : field %>&search=<%=search==null ? "" : search%>'"><%=i %></button>
		<%		} else { %>
		<button onclick="location.href='/admin/user/list?curPage=<%=i %>&grade=<%= grade==null ? "" : grade %>&field=<%= field==null ? "" : field %>&search=<%=search==null ? "" : search %>'"><%=i %></button>
		<%		} %>
		<%	} %>
		



		<!-- 다음 페이지로 가기 -->
		<%	if(paging.getCurPage() != paging.getTotalPage()) { %>
		<button onclick="location.href='/admin/user/list?curPage=<%=paging.getCurPage() + 1 %>&grade=<%=grade==null ? "" : grade %>&field=<%=field==null ? "" : field %>&search=<%=search==null ? "" : search %>'">
		&gt;</button>		
		<%	} %>




		<!-- 다음 페이징 리스트로 가기 -->
		<%	if(paging.getEndPage() != paging.getTotalPage()) { %>
		<button onclick="location.href='/admin/user/list?curPage=<%=paging.getStartPage() + paging.getPageCount() %>&grade=<%=grade==null ? "" : grade %>&field=<%=field==null ? "" : field %>&search=<%=search==null ? "" : search %>'">
		&raquo;</button>		
		<%	} else { %>
		<button class="disabled">&raquo;</button>
		<%	} %>




		<!-- 마지막 페이지로 가기 -->
		<%	if(paging.getCurPage() != paging.getTotalPage()) { %>
		<button onclick="location.href='/admin/user/list?curPage=<%=paging.getTotalPage() %>&grade=<%=grade==null ? "" : grade %>&field=<%=field==null ? "" : field %>&search=<%=search==null ? "" : search %>'">
		&rarr;</button>		
		<%	} %>
		
</div>