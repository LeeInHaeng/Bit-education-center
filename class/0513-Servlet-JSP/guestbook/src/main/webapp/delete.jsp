<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cafe24.guestbook.DAO.GuestbookDAO" %>
<%@page import="com.cafe24.guestbook.VO.GuestbookVO" %>

<%
	String no = request.getParameter("no");
	String password = request.getParameter("password");
	
	GuestbookVO vo = new GuestbookVO();
	vo.setNo(Long.parseLong(no));
	vo.setPassword(password);
	
	new GuestbookDAO().delete(vo);
	
	response.sendRedirect("/guestbook");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>