# EL-JSTL

### Java ���� Scope
- ���� ã�� ���� : Page ---> Request ---> Session ---> Application
  - PageContext ---> HttpServletRequest ---> HttpSession ---> getServletContext
- �����Ǵ� ���� : Application > Session > Request > Page
- ���� ���� : Application > Session > Request > Page

### EL�� �پ��� ǥ����
- JSP���� �ڹ� ������ ���ֱ� ���� ����Ѵ�.
- ```${ }```�� ����Ѵ�.
- Servlet���� �Ѿ�� ������ Ȯ�� (request.setAttribute)
```jsp
${ iVal }
```
- ���� �� null üũ
```jsp
	${ iVal > lVal && fVal < 5 }<br>
	${ empty obj }<br>
	${ not empty obj }<br>
```
- ��û �Ķ���� ��������
  - �������� request.getParameter�� ���������, EL�� param���� �����ϸ� �ȴ�.
```jsp
	<%= request.getParameter("a") %><br>
	${ param.a + 10 }
```
- ��ü ����
  - Scope ������ �浹�� �� ���� �ֱ� ������ �̸��� ������ �� ������ �ʿ䰡 �ִ�.
  - pageScope, requestScope, sessionScope, applicationScope
```jsp
	${ requestScope.vo.no }<br>
	${ vo.no }<br>
```
- Map (HashMap) ����
```jsp
${ map.iVal }
```

### JSTL ���
- ����� ���ؼ� WEB-INF �Ʒ��� lib �Ʒ��� jstl.jar ������ �����Ѵ�.
  - Ȥ�� maven���� �ٿ�ε�
- �⺻ ��� ���
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
```
- ����ȭ ���
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
```
- �Լ� ó�� ���
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
```
- if elseif else ó��
  - choose�� when, otherwise�� ����Ѵ�.
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
- request.getContextPath()�� ���� ǥ����
```jsp
${pageContext.servletContext.contextPath }
```
- �������� �ӽ� ���� ����
```jsp
<c:set var='count' value='10/>
```
- �Լ� ���(����, replace)
```jsp
<c:set var='count' value='${fn:length(list) }'/>
${fn:replace(vo.contents, newline, "<br>" )}
```
- �ݺ��� ���
```jsp
<c:forEach items='${list }' var ='vo' varStatus='status'>
	<td>[${count-status.index }]</td>
	<td>${vo.name }</td>
</c:forEach>
```
- �ݺ��� ��� : items�� �ƴ� sequence
  - begin�� end ���
```jsp
		<c:forEach begin="1" end="10" step='1' var='i'>
			<li>${i }��° ������</li>
		</c:forEach>
```
- jsp:include�� ���� ǥ���� ---> c:import
  - import �ÿ� param�� �ѱ� ���� �ִ�.
  - import �ϸ鼭 ������ jsp�� �ѱ� param ���� �����ؼ� ���� �ϼ�
```jsp
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<c:import url="/WEB-INF/views/includes/header.jsp">
	<c:param name="menu" value="guestbook" />
</c:import>

<!-- header.jsp���� -->
<c:choose>
	<c:when test='${param.menu == "guestbook" }'>
</c:choose>
```

# Spring ���

### Spring �ʱ� ����
- Spring ����� ���� �ʿ��� ���̺귯���� �߰�
  - pom.xml�� properties�� �̿��� ���� ����
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
- WEB-INF ���� �Ʒ��� spring-servlet.xml ������ ����
  - component-scan�� base-package�� �ִ� ��Ű�� ����
  - �ش� ��Ű������ ��Ʈ�ѷ��� ����
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
- Controller�� ���� �׽�Ʈ
```java
@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String hello() {
		return "/WEB-INF/views/hello.jsp";
	}
}
```

### Spring ���۰���
- ��� ��û�� DispatcherServlet�� ����
- DispatcherServlet���� init �޼��� ����
- init Param�� ������ configPath�� ����Ʈ�� /WEB-INF/**web.xml�� servlet-name**-servlet.xml�� ����
  - web.xml�� servlet-mapping�� servlet-name�� spring���� �Ǿ� ������ spring-servlet.xml�� ��������
- ApplicationContext ��ü�� �����ϸ鼭 ��������.xml�� �Ű������� �����ϸ� �����̳ʸ� �����. 
  - Web Application Context ��� �θ���.
- �������Ͽ� ��Ʈ�ѷ��� Ž���� ��Ű���� �����ϸ�, �ش� ��Ű���� ��Ʈ�ѷ��� �����.
- HandlerMapping Ŭ������ �����.
  - url, controller, �޼��� �̸�, �Ķ���� ���� ������ �ش� Ŭ������ ��ü�� ����ȴ�.
  - �ش� ��ü�� ������ ������� �����̳� �ȿ��� �����Ǵ� ���̴�.
- ViewResolver Ŭ������ �����.
  - View �̸��� �ָ� ��û�� �´� View�� �������ִ� ����
  - �ش� ��ü ���� ������ ������� �����̳� �ȿ��� �����Ǵ� ���̴�.
- Servlet�� init�� ������. (init���� MVC ��ü�� ��� ������ ����)
- Servlet�� Service ����
  - ������� request�� header�� body�κ��� �Ľ� �� get, post ���� ��û�� ���� doGet ���� �����Ѵ�.
- Servlet�� doGet ����
  - DispatcherServlet���� requestURL, �޼��� �̸�, �Ķ���� ---> HandlerMapping�� ��û ---> ��û�� �´� Controller --->  Controller�� View �̸� ��ȯ ---> ViewResolver�� ��û ---> Render �޼��� ȣ�� ---> Render �ȿ��� ������
