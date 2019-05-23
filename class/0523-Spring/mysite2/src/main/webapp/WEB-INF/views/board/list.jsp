<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" 
					action="${pageContext.servletContext.contextPath }/board/list/1" 
					method="get">
					<input type="text" id="keyword" name="keyword" value="${pageInfo.searchKeyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>			
					
					<c:set var='count' value='${fn:length(list) }'/>
					<c:forEach items='${list }' var ='vo' varStatus='status'>
						<tr>
							<td>${count-status.index }</td>
							<td style="text-align:left; padding-left:${10*vo.depth}px">
								<c:if test='${vo.depth!=0 }'>
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png"/>
								</c:if>
								<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}">${vo.title }</a>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no}" class="del">삭제</a></td>
							<td><a href="${pageContext.servletContext.contextPath }/board/writeReply/${vo.no}" class="replyBoard">답글</a></td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test='${pageInfo.beginPageNum!=1 }'>
						<li>
							<c:choose>
								<c:when test='${pageInfo.searchKeyword !="" }'>
									<a href="${pageContext.servletContext.contextPath }/board/list/${pageInfo.beginPageNum-1}?keyword=${pageInfo.searchKeyword}">◀</a>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.servletContext.contextPath }/board/list/${pageInfo.beginPageNum-1}">◀</a>
								</c:otherwise>
							</c:choose>
						</li>
						</c:if>
						<c:forEach begin="${pageInfo.beginPageNum}" end="${pageInfo.endPageNum }" step='1' var='i'>
							
							<c:if test='${pageInfo.lastPageNum>=i }'>
							
							<c:choose>
								<c:when test='${pageInfo.currentPage==i }'>
								
								<li class="selected">
								<c:choose>
									<c:when test='${pageInfo.searchKeyword != "" }'>
										<a href="${pageContext.servletContext.contextPath }/board/list/${i}?keyword=${pageInfo.searchKeyword}">${i }</a>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.servletContext.contextPath }/board/list/${i}">${i }</a>
									</c:otherwise>
								</c:choose>
								</li>
								
								</c:when>
								
								<c:otherwise>
								
								<li>
								<c:choose>
									<c:when test='${pageInfo.searchKeyword != "" }'>
										<a href="${pageContext.servletContext.contextPath }/board/list/${i}?keyword=${pageInfo.searchKeyword}">${i }</a>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.servletContext.contextPath }/board/list/${i}">${i }</a>
									</c:otherwise>
								</c:choose>
								</li>
									
								</c:otherwise>
							</c:choose>
							
							</c:if>
						
						</c:forEach>
						
						<c:if test='${pageInfo.endPageNum <= pageInfo.lastPageNum }'>
						<li>
							<c:choose>
								<c:when test='${pageInfo.searchKeyword != "" }'>
									<a href="${pageContext.servletContext.contextPath }/board/list/${pageInfo.endPageNum+1}?keyword=${pageInfo.searchKeyword}">▶</a>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.servletContext.contextPath }/board/list/${pageInfo.endPageNum+1}">▶</a>
								</c:otherwise>
							</c:choose>
						</li>
						</c:if>
					</ul>
				</div>	
								
				<!-- pager 추가 -->
				<div class="bottom" style="cursor:pointer">
					<a id="new-book">글쓰기</a>
				</div>	
					
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
	
	<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
	<script>
		$("#new-book").click(function(){
			var authUserNo = '${authUser.no}';
			if(authUserNo===''){
				var message = confirm("로그인 하시겠습니까?");
				if(message===true){
					window.location.href = "${pageContext.servletContext.contextPath }/user/login";
				}
				return false;
			}else{
				window.location.href = "${pageContext.servletContext.contextPath }/board/write";
			}
		});
	</script>
</body>
</html>