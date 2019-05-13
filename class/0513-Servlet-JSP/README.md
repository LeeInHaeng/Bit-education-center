# JSP, Servlet

### Maven Project ����
- pom.xml ����
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.cafe24</groupId>
	<artifactId>guestbook</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>
	
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
	</dependencies>

	<build>
		<sourceDirectory>src/main/java</sourceDirectory>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<version>2.3.1</version>
				<configuration>
					<finalName>networkprogramming</finalName>
					<outputDirectory>working</outputDirectory>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>3.2.1</version>
				<configuration>
					<warSourceDirectory>src/main/webapp</warSourceDirectory>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
```
- ������Ʈ ��Ŭ�� ---> Java EE Tools ---> Generate Deployment Descriptor Stub
- ���丮 ����
```
src/main/webapp
  |-- WEB-INF
    |-- web.xml
    |-- classes
    |-- lib
```
- ������Ʈ ��Ŭ�� ---> Properties ---> Targeted Runtimes ---> Tomcat ���

### JSP ���
- ```<% %>``` �ȿ� �ڹ� �ڵ带 ������ �� �ִ�.
- ������ ��ü�� ����� Ư¡�� page ���þ ����Ѵ�.
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>
<%@page import="com.cafe24.guestbook.DAO.GuestbookDAO" %>
<%@page import="com.cafe24.guestbook.VO.GuestbookVO" %>
<%
	GuestbookDAO dao = new GuestbookDAO();
	List<GuestbookVO> list = dao.getList();
%>

...

	<%
		for(GuestbookVO vo : list){
	%>

		...
		<%=vo.getName()%>

	<%} %>
```
- get, post�� �Ѿ���� ������ �ޱ�
  - request.getParameter�� �̿��� input �±��� name �Ӽ��� �޾ƿ´�.
```jsp
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String contents = request.getParameter("contents");
%>
```
- ��� �Ϸ� �� redirect �ϱ�
```jsp
<%
	response.sendRedirect("/guestbook");
%>
```
- webapp���� �����ϱ�
  - ��ΰ� webapp �Ʒ��� assets ������ ���� ���
  - getContextPath�� �̿�
```jsp
<link href="<%=request.getContextPath() %>/assets/css/guestbook.css" rel="stylesheet" type="text/css">
```

### Servlet ���
- Dispatcher�� forward Ȥ�� sendRedirect�� �̿��� ������ �̵�
```java
	public static void redirect(
			HttpServletRequest request,
			HttpServletResponse response,
			String url) throws IOException, ServletException {
		response.sendRedirect(url);
	}
	
	public static void forward(
			HttpServletRequest request,
			HttpServletResponse response,
			String location) throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(location);
		rd.forward(request, response);
	}
```
- doGet�� doPost�� ���� ������ �̺�Ʈ ����
```java
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}
```
