<%@page import="dto.Comment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%List<Comment> cList = (List)request.getAttribute("cList"); %>
    
<script type="text/javascript">
	$(document).ready(function(){
		<%for(int i=0; i<cList.size();i++){%>
		
		$("#cmtDel<%=i%>").click(function(){
			var check = confirm('댓글을 삭제하시겠습니까?');
			if(check){
				$.ajax({
					url:"/comment/delete",
					type:"POST",
					data:{cno: <%=cList.get(i).getCno()%>, pno:<%=cList.get(i).getPno()%>},
					success:function(){
						location.reload();
					}
				});
			}
		});
		
		
		//대댓글 버튼클릭
		$("#btnCiC<%=i%>").click(function(){
			$("#divCiC<%=i%>").html("");
			$("#divCiC<%=i%>").append("<textarea id='textCiC' style='width:900px; height:80px; '></textarea><button id='cancelCiC'>취소</button>");
			$("#divCiC<%=i%>").append("<button id='insertCiC'>댓글 등록</button>");
			$("#textCiC").focus();
			
			//대댓글 취소버튼
			$("#cancelCiC").click(function(){
				$("#divCiC<%=i%>").html("");
			});
			
			var login = <%=session.getAttribute("login")%>
			
			//대댓글 등록 버튼
			$("#insertCiC").click(function(){
				if(login){
					$.ajax({
						url:"/comment/cic",
						type:"POST",
						data:{pno:<%=cList.get(i).getPno()%>, cno:<%=cList.get(i).getCno()%>, content:$("#textCiC").val()},
						success:function(){
							location.reload();
						}
					})
				}else{
					alert('로그인 후 등록 가능합니다');
					location.href="/member/login";
					}
			});
			
		});
		
		
		//댓글 수정 
		$("#cmtUp<%=i%>").click(function(){
			$("#cmtDiv<%=i%>").html("");
			$("#cmtDiv<%=i%>").append("<p></p><textarea id='cmtContent' style='width:900px; height:80px; '><%=cList.get(i).getC_content()%></textarea>");
			$("#cmtDiv<%=i%>").append("<button id='cmtCancel'>취소</button><button id='cmtUpdate'>댓글 등록</button><hr>");
			
			//댓글수정 취소버튼
			$("#cmtCancel").click(function(){
				location.reload();
			});
			
			//댓글수정 등록버튼
			$("#cmtUpdate").click(function(){
				$.ajax({
					url:"/comment/update",
					type:"POST",
					data:{cno:<%=cList.get(i).getCno()%>, c_content:$("#cmtContent").val()},
					success:function(result){
						
						location.reload();
					}
				});
			});
			
			
		});
		
		<%}%>
		
	})
</script>
    
    
    
    
    
    
    
    <!-- 댓글 생성 -->
<%for(int i=0; i<cList.size(); i++){ %>

<%if(cList.get(i).getDepth() == 0){ %>
<div id="cmtDiv<%=i%>">

<%}else{ %>
<div id="cmtDiv<%=i %>" style="padding-left:40px;">
<%} %>

	<span style="float:left;"><%=cList.get(i).getU_nick() %></span>
	<div>
	<span><%=cList.get(i).getC_Create_date() %></span>
	</div>
	<div style="float:right;">
	
	<%if(session.getAttribute("u_id") != null){
	if(session.getAttribute("u_id").equals(cList.get(i).getU_id())){ %>
	
	<a id="cmtUp<%=i%>"><span style="cursor:pointer">수정</span></a>
	<a id="cmtDel<%=i%>"><span style="cursor:pointer">삭제</span></a>
	
	<%}} %>
	</div>
	
	<p style="font-size:large; text-align:left;"><%=cList.get(i).getC_content() %></p>
	<a style="float: left;"><button id="btnCiC<%=i%>">댓글</button></a>
	<br>
	<div id="divCiC<%=i%>"></div>
	<hr>
	
</div>
<%}%>

