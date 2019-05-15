# EL-JSTL

### Java 변수 Scope
- 변수 찾는 순서 : Page ---> Request ---> Session ---> Application
  - PageContext ---> HttpServletRequest ---> HttpSession ---> getServletContext
- 유지되는 생명 : Application > Session > Request > Page
- 포함 범위 : Application > Session > Request > Page

### EL의 다양한 표현식
- JSP에서 자바 문법을 없애기 위해 사용한다.
- ```${ }```을 사용한다.
- Servlet에서 넘어온 데이터 확인 (request.setAttribute)
```jsp
${ iVal }
```
- 연산 및 null 체크
```jsp
	${ iVal > lVal && fVal < 5 }<br>
	${ empty obj }<br>
	${ not empty obj }<br>
```
- 요청 파라미터 가져오기
  - 기존에는 request.getParameter을 사용했지만, EL은 param으로 접근하면 된다.
```jsp
	<%= request.getParameter("a") %><br>
	${ param.a + 10 }
```
- 객체 접근
  - Scope 생략시 충돌이 날 수도 있기 때문에 이름을 설정할 때 주의할 필요가 있다.
  - pageScope, requestScope, sessionScope, applicationScope
```jsp
	${ requestScope.vo.no }<br>
	${ vo.no }<br>
```
- Map (HashMap) 접근
```jsp
${ map.iVal }
```

### JSTL 사용
- 사용을 위해서 WEB-INF 아래의 lib 아래에 jstl.jar 파일을 복사한다.
  - 혹은 maven으로 다운로드
- 기본 기능 사용
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
```
- 형식화 사용
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
```
- 함수 처리 사용
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
```
- if elseif else 처리
  - choose와 when, otherwise를 사용한다.
```jsp
	<c:choose>
		<c:when test='${param.c == "red" }'>
			<h1 style='color:#f00'>Hello World</h1>
		</c:when>
		<c:when test='${param.c == "blue" }'>
			<h1 style='color:#00f'>Hello World</h1>
		</c:when>
		<c:otherwise>
			<h1>Hello World</h1>
		</c:otherwise>
	</c:choose>
```
- request.getContextPath()와 같은 표현식
```jsp
${pageContext.servletContext.contextPath }
```
- 페이지에 임시 변수 셋팅
```jsp
<c:set var='count' value='10/>
```
- 함수 사용(길이, replace)
```jsp
<c:set var='count' value='${fn:length(list) }'/>
${fn:replace(vo.contents, newline, "<br>" )}
```
- 반복문 사용
```jsp
<c:forEach items='${list }' var ='vo' varStatus='status'>
	<td>[${count-status.index }]</td>
	<td>${vo.name }</td>
</c:forEach>
```
- 반복문 사용 : items가 아닌 sequence
  - begin과 end 사용
```jsp
		<c:forEach begin="1" end="10" step='1' var='i'>
			<li>${i }번째 아이템</li>
		</c:forEach>
```
- jsp:include와 같은 표현식 ---> c:import
  - import 시에 param을 넘길 수도 있다.
  - import 하면서 포함한 jsp에 넘긴 param 값을 참조해서 조건 완성
```jsp
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<c:import url="/WEB-INF/views/includes/header.jsp">
	<c:param name="menu" value="guestbook" />
</c:import>

<!-- header.jsp에서 -->
<c:choose>
	<c:when test='${param.menu == "guestbook" }'>
</c:choose>
```

# Spring 사용

### Spring 초기 설정
- Spring 사용을 위해 필요한 라이브러리들 추가
  - pom.xml의 properties를 이용해 버전 관리
```
// pom.xml
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<org.springframework-version>4.3.1.RELEASE</org.springframework-version>
	</properties>

		<!-- Spring Core Library -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${org.springframework-version}</version>
		</dependency>

		<!-- Spring Web Library -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-web</artifactId>
			<version>${org.springframework-version}</version>
		</dependency>

		<!-- Spring MVC Library -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>${org.springframework-version}</version>
		</dependency>

// web.xml
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>spring</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```
- WEB-INF 폴더 아래에 spring-servlet.xml 파일을 생성
  - component-scan의 base-package에 있는 패키지 생성
  - 해당 패키지에서 컨트롤러를 생성
```
<?xml version="1.0" encoding="UTF-8"?>
<beans
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop" 
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:p="http://www.springframework.org/schema/p" 
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

	<context:annotation-config />
	<context:component-scan base-package="com.cafe24.springex.controller" />

</beans>
```
- Controller를 만들어서 테스트
```java
@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String hello() {
		return "/WEB-INF/views/hello.jsp";
	}
}
```

### Spring 동작과정
- 모든 요청을 DispatcherServlet이 받음
- DispatcherServlet에서 init 메서드 수행
- init Param이 없으면 configPath의 디폴트를 /WEB-INF/**web.xml의 servlet-name**-servlet.xml로 셋팅
  - web.xml에 servlet-mapping의 servlet-name이 spring으로 되어 있으면 spring-servlet.xml이 설정파일
- ApplicationContext 객체를 생성하면서 설정파일.xml을 매개변수로 전달하며 컨테이너를 만든다. 
  - Web Application Context 라고 부른다.
- 설정파일에 컨트롤러를 탐색할 패키지를 지정하며, 해당 패키지에 컨트롤러를 만든다.
- HandlerMapping 클래스를 만든다.
  - url, controller, 메서드 이름, 파라미터 등의 정보가 해당 클래스의 객체에 저장된다.
  - 해당 객체는 위에서 만들어진 컨테이너 안에서 생성되는 것이다.
- ViewResolver 클래스를 만든다.
  - View 이름을 주면 요청에 맞는 View를 리턴해주는 역할
  - 해당 객체 또한 위에서 만들어진 컨테이너 안에서 생성되는 것이다.
- Servlet의 init이 끝난다. (init에서 MVC 객체가 모두 생성된 상태)
- Servlet의 Service 진행
  - 사용자의 request에 header와 body부분을 파싱 후 get, post 등의 요청에 따라 doGet 등을 실행한다.
- Servlet의 doGet 실행
  - DispatcherServlet에서 requestURL, 메서드 이름, 파라미터 ---> HandlerMapping에 요청 ---> 요청에 맞는 Controller --->  Controller가 View 이름 반환 ---> ViewResolver에 요청 ---> Render 메서드 호출 ---> Render 안에서 포워딩
