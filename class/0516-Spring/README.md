# Spring Controller 기본 사용

- Controller에서 View로 데이터 넘기기
  - Model 객체 사용
  - Model에 addAttribute를 사용해 키, 값을 셋팅하면 request의 setAttribute에 설정된다.
  - View에서 request의 Attribute를 받는 것과 같은 방식으로 받으면 된다.
```java
// controller
	@RequestMapping("/hello3")
	public String hello3(Model model) {
		model.addAttribute("email", "dooly@gmail.com");
		return "/WEB-INF/views/hello.jsp";
	}

// JSP
	<h1>${email }</h1>
```

- URL의 파라미터를 Controller에서 받고 View로 넘기기
  - RequestParam 어노테이션을 사용한다.
  - 해당 어노테이션에서 value 생략시 controller의 매개변수 이름으로 파라미터를 찾는다.
  - RequestParam 어노테이션 자체를 생략하면 controller의 매개변수 이름으로 파라미터를 찾는다.
  - 매개변수로 HttpServletRequest를 받고 request.getParameter를 받아도 되지만 비추천
```java
	@RequestMapping("/hello4")
	public String hello4(Model model,
			@RequestParam("e") String email,
			@RequestParam("n") String name) {
		model.addAttribute("email", email);
		System.out.println(name);
		return "/WEB-INF/views/hello.jsp";
	}

// 요청 url : http://localhost:8080/springEx1/hello4?e=aa@aa.com&n=내이름
```

# Spring Controller 어노테이션 사용
- Autowired
  - 같은 패키지에 있나 확인 ---> Web Application Context의 bean에 등록되어 있는지 확인 ---> Root Application Context의 bean에 등록되어 있는지 확인

### RequestMapping 어노테이션 사용
- method 부분 생략시 GET, POST 등 모든 request로 접근 가능
```java
@RequestMapping(value="/user/join", method=RequestMethod.GET)
@RequestMapping(value="/user/join", method=RequestMethod.POST)
```
- value 값을 여러개 줄 수도 있다.
```java
@RequestMapping(value= {"/user/join", "/user/j"}, method=RequestMethod.POST)
```
- Controller에 맵핑 후 각 메서드에 맵핑 하는 것도 가능하다.
```java
@Controller
@RequestMapping("/user")
public class UserController {
	// ...
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
```

### RequestParam 어노테이션 사용
- RequestParam 어노테이션 생략
- RequestParam 어노테이션에서 value 생략
- RequestParam 어노테이션에 value값 포함 3개 다 모두 같은 방식으로 동작
- 마지막 방식을 추천
- POST 같이 body에 RequestParam 값이 있는 경우에도 똑같이 동작 
```java
	public String update(
			/*String name*/
			/*@RequestParam String name*/
			@RequestParam("name") String name) {
		System.out.println(name);
		return "BoardController:update";
	}
```
- 필요 여부와 디폴트 값 셋팅 가능
  - required를 true로 한 상태로 URL에 파라미터를 지정하지 않아도 defaultValue를 설정하면 에러가 발생하지 않는다.
  - 아래의 예제에서 age는 int형이고 defaultValue가 String이어도 int로 자동 형변환이 발생한다.
```java
public String write(
		@RequestParam(value="n", required=true, defaultValue="익명 사용자") String name,
		@RequestParam(value="age", required=true, defaultValue="0") int age)
```
- 패스 기반 파라미터 맵핑
  - view?no=10 보다는 view/10 의 방식이 Restful 방식에 적합하다.
  - 파라미터의 맵핑을 위해 ```{ }```를 사용하며 PathVariable 어노테이션을 함께 사용한다.
  - 타입에 맞지 않는 값이 들어오면 Bad Request가 발생한다.
```java
	@RequestMapping("/board/view/{no}")
	public String view(@PathVariable(value="no") int no) {
```
- ModelAttribute 어노테이션
  - 해당 어노테이션을 사용하면 Model에 해당 값이 추가되어 View로 자동적으로 넘어간다.
  - form으로 데이터 submit 실패시 input 태그의 모든 value 지워지는 것을 방지할 수 있다.
```java
public String join(@ModelAttribute UserVo userVo) {

// JSP
<input id="name" name="name" type="text" value="${userVo.name }">
```

# Root Application Context (비즈니스 로직)
- 어플리케이션 최초 실행시 Listener에서 contextInitialized를 수행
- contextConfigLocation 설정 파일을 바탕으로 Root Application Context 컨테이너를 만든다.
- 이후에 Dispatcher Servlet에서 모든 요청을 받으며 똑같이 진행
- web.xml 설정
```xml
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/applicationContext.xml</param-value>
	</context-param>

	<!-- Context Loader Listener (Business)-->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
```
- web.xml에 설정한 contextConfigLocation의 value 값의 xml 파일 생성
  - 해당 xml 파일이 비즈니스 로직과 관련된 설정 파일이 된다.
  - applicationContext.xml 파일 예제
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
        http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
        http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd">

	<context:annotation-config />

	<context:component-scan base-package="com.cafe24.mysite.repository">
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Repository" />
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Service" />
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Component" />			
	</context:component-scan>


</beans>
```
- com.cafe24.mysite.repository의 Repository, Service, Component 어노테이션을 스캔한다.
  - 해당 패키지에 DAO를 만들고, 각 DAO들에 Repository 어노테이션을 작성한다.
```java
@Repository
public class GuestbookDao {

//...

@Repository
public class UserDao {
```
- 한글 깨짐 처리 : web.xml에 filter 설정
```xml
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>

		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>

	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```

### 최종 xml 설정 파일들 기본 형태
- web.xml
  - WEB과 관련된 설정파일
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">
	<display-name>mysite2</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>

	<!-- Context Loader Listener (Business) -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/applicationContext.xml</param-value>
	</context-param>

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- Dispatcher Server(Front Controller) -->
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>spring</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

	<!-- Encoding Filter -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>

		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>

	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

</web-app>
```
- WEB-INF/applicationContext.xml
  - VO, DAO, Service 등 비즈니스 로직 처리 설정파일
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
        http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
        http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd">

	<context:annotation-config />

	<context:component-scan
		base-package="com.cafe24.mysite.repository, com.cafe24.mysite.service">
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Repository" />
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Service" />
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Component" />
	</context:component-scan>

</beans>
```
- WEB-INF/spring-servlet.xml
  - Controller 등 MVC 처리 설정파일
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

	<!-- validator, conversionService, messageConverter를 자동으로 등록 -->
	<mvc:annotation-driven />

	<!-- 서블릿 컨테이너의 디폴트 서블릿 위임 핸들러 -->
	<mvc:default-servlet-handler />

	<!-- 빈 설정을 annotation으로 하겠다.(@Controller에 대한 auto scanning) -->
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.mysite.controller" />

	<!-- ViewResolver 설정 -->
	<bean id="viewResolver"
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.JstlView" />
		<property name="prefix" value="/WEB-INF/views/" />
		<property name="suffix" value=".jsp" />
	</bean>

</beans>
```

# 어플리케이션 아키텍처 예제
### 비즈니스 분석 (사용자 스토리 도출)
- 사용자는  회원가입을 한다.
- 사용자는  로그인을 한다.
- 사용자는  로그아웃을 한다.
- 사용자는 방명록에 글을 남긴다.
- 사용자는 방명록 자신의 글을 삭제한다.
- 로그인한 사용자는 자신의 정보를 수정한다.
- 로그인한 사용자는 게시판에 글을 작성한다.
- 로그인한 사용자는 자신의 글을 수정한다.
- 로그인한 사용자는 자신의 글을 삭제한다.
- 로그인한 사용자는 다른 사람의 글에 댓글을 남긴다.
- 사용자는 게시판글의 목록을 볼 수 있다.
- 사용자는 게시판글을 읽을 수 있다. 

### 서비즈 정의
- UserService
  - 사용자는  회원가입을 한다. ( join )
  - 사용자는  로그인을 한다. (login)
  - 사용자는  로그아웃을 한다. (logout)
  - 로그인한 사용자는 자신의 정보를 수정한다. (modifyInformation)
- BoardService
  - 로그인한 사용자는 게시판에 글을 작성한다. ( write )
  - 로그인한 사용자는 자신의 글을 수정한다. ( modify )
  - 로그인한 사용자는 자신의 글을 삭제한다. ( remove )
  - 로그인한 사용자는 다른 사람의 글에 댓글을 남긴다. ( write )
  - 사용자는 게시판글의 목록을 볼 수 있다. ( list )
  - 사용자는 게시판글을 읽을 수 있다. ( view )
- GuestbookService
  - 사용자는 방명록에 글을 남긴다. (write)
  - 사용자는 방명록 자신의 글을 삭제한다. (remove)

![123](https://user-images.githubusercontent.com/20277647/57829407-d66c0700-77e9-11e9-8113-826c956e58bf.PNG)

