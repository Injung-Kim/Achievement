<%@page import="util.Paging"%>
<%@page import="util.AdminComplaintPaging"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- <%	AdminComplaintPaging complaintPaging = (AdminComplaintPaging) request.getAttribute("complaintPaging"); %> --%>
<%	Paging SearchPaging = (Paging)request.getAttribute("SearchPaging"); %>
<% String searchType = request.getParameter("searchType"); %>
<% String searchKeyword = request.getParameter("searchKeyword"); %>
<div class="text-center">
	<ul class="pagination .pagination-lg">
	
		<!-- 이전 페이징 리스트로 가기 -->
		<%	if(SearchPaging.getStartPage() > SearchPaging.getPageCount()) { %>
		<li>
			<a href="/admin/complaint/list?searchType=<%=searchType==null ? "ch_title" : searchType %>&searchKeyword=<%=searchKeyword == null ? "" : searchKeyword %>&curPage=<%=SearchPaging.getStartPage() - SearchPaging.getPageCount() %>">&laquo;</a>
		</li>
		<%	} else { %>
		<li class="disabled"><a>&laquo;</a>
		</li>
		<%	} %>
		
		<!-- 이전 페이지로 가기 -->
		<%	if(SearchPaging.getCurPage() != 1) { %>
		<li>
			<a href="/admin/complaint/list?searchType=<%=searchType==null ? "ch_title" : searchType %>&searchKeyword=<%=searchKeyword == null ? "" : searchKeyword %>&curPage=<%=SearchPaging.getCurPage() - 1 %>">&lt;</a>
		</li>
		<%	} %>
						
		<!-- 페이징 리스트 -->
		<%	for(int i=SearchPaging.getStartPage(); i<=SearchPaging.getEndPage(); i++) { %>
		<%		if( i == SearchPaging.getCurPage() ) { %>
		<li class="active">
			<a href="/admin/complaint/list?searchType=<%=searchType==null ? "ch_title" : searchType %>&searchKeyword=<%=searchKeyword == null ? "" : searchKeyword %>&curPage=<%=i %>"><%=i %></a>
		</li>
		<%		} else { %>
		<li>
			<a href="/admin/complaint/list?searchType=<%=searchType==null ? "ch_title" : searchType %>&searchKeyword=<%=searchKeyword == null ? "" : searchKeyword %>&curPage=<%=i %>"><%=i %></a>
		</li>
		<%		} %>
		<%	} %>
		
		<!-- 다음 페이지로 가기 -->
		<%	if(SearchPaging.getCurPage() != SearchPaging.getTotalPage()) { %>
		<li>
			<a href="/admin/complaint/list?searchType=<%=searchType==null ? "ch_title" : searchType %>&searchKeyword=<%=searchKeyword == null ? "" : searchKeyword %>&curPage=<%=SearchPaging.getCurPage() + 1 %>">&gt;</a>
		</li>
		<%	} %>

		<!-- 다음 페이징 리스트로 가기 -->
		<%	if(SearchPaging.getEndPage() != SearchPaging.getTotalPage()) { %>
		<li>
			<a href="/admin/complaint/list?searchType=<%=searchType==null ? "ch_title" : searchType %>&searchKeyword=<%=searchKeyword == null ? "" : searchKeyword %>&curPage=<%=SearchPaging.getStartPage() + SearchPaging.getPageCount() %>">&raquo;</a>
		</li>
		<%	} else { %>
		<li class="disabled"><a>&raquo;</a></li>
		<%	} %>

	</ul>
</div>