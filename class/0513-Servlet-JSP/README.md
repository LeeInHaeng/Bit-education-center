# JSP, Servlet

### Maven Project 빌드
- pom.xml 수정
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
- 프로젝트 우클릭 ---> Java EE Tools ---> Generate Deployment Descriptor Stub
- 디렉토리 구조
```
src/main/webapp
  |-- WEB-INF
    |-- web.xml
    |-- classes
    |-- lib
```
- 프로젝트 우클릭 ---> Properties ---> Targeted Runtimes ---> Tomcat 등록

### JSP 사용
- ```<% %>``` 안에 자바 코드를 삽입할 수 있다.
- 페이지 전체에 적용될 특징은 page 지시어를 사용한다.
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
- get, post로 넘어오는 데이터 받기
  - request.getParameter을 이용해 input 태그의 name 속성을 받아온다.
```jsp
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String contents = request.getParameter("contents");
%>
```
- 결과 완료 후 redirect 하기
```jsp
<%
	response.sendRedirect("/guestbook");
%>
```
- webapp으로 접근하기
  - 경로가 webapp 아래에 assets 폴더가 있을 경우
  - getContextPath를 이용
```jsp
<link href="<%=request.getContextPath() %>/assets/css/guestbook.css" rel="stylesheet" type="text/css">
```

### Servlet 사용
- Dispatcher의 forward 혹은 sendRedirect를 이용해 페이지 이동
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
- doGet과 doPost에 따라 적절한 이벤트 조합
```java
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}
```
