<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>MySite</h1>
			<ul>
				<li><a href="">ë¡ê·¸ì¸</a><li>
				<li><a href="">íìê°ì</a><li>
				<li><a href="">íìì ë³´ìì </a><li>
				<li><a href="">ë¡ê·¸ìì</a><li>
				<li>ë ìëíì¸ì ^^;</li>
			</ul>
		</div>
		<div id="content">
			<div id="guestbook" class="delete-form">
				<form method="post" action="/guestbook">
					<input type="hidden" name="a" value="delete">
					<input type='hidden' name="no" value="">
					<label>ë¹ë°ë²í¸</label>
					<input type="password" name="password">
					<input type="submit" value="íì¸">
				</form>
				<a href="">ë°©ëªë¡ ë¦¬ì¤í¸</a>
			</div>
		</div>
		<div id="navigation">
			<ul>
				<li><a href="">ìëí</a></li>
				<li><a href="">ë°©ëªë¡</a></li>
				<li><a href="">ê²ìí</a></li>
			</ul>
		</div>
		<div id="footer">
			<p>(c)opyright 2015, 2016, 2017, 2018</p>
		</div>
	</div>
</body>
</html>