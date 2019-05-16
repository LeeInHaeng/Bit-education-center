# Spring Controller �⺻ ���

- Controller���� View�� ������ �ѱ��
  - Model ��ü ���
  - Model�� addAttribute�� ����� Ű, ���� �����ϸ� request�� setAttribute�� �����ȴ�.
  - View���� request�� Attribute�� �޴� �Ͱ� ���� ������� ������ �ȴ�.
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

- URL�� �Ķ���͸� Controller���� �ް� View�� �ѱ��
  - RequestParam ������̼��� ����Ѵ�.
  - �ش� ������̼ǿ��� value ������ controller�� �Ű����� �̸����� �Ķ���͸� ã�´�.
  - RequestParam ������̼� ��ü�� �����ϸ� controller�� �Ű����� �̸����� �Ķ���͸� ã�´�.
  - �Ű������� HttpServletRequest�� �ް� request.getParameter�� �޾Ƶ� ������ ����õ
```java
	@RequestMapping("/hello4")
	public String hello4(Model model,
			@RequestParam("e") String email,
			@RequestParam("n") String name) {
		model.addAttribute("email", email);
		System.out.println(name);
		return "/WEB-INF/views/hello.jsp";
	}

// ��û url : http://localhost:8080/springEx1/hello4?e=aa@aa.com&n=���̸�
```

# Spring Controller ������̼� ���
- Autowired
  - ���� ��Ű���� �ֳ� Ȯ�� ---> Web Application Context�� bean�� ��ϵǾ� �ִ��� Ȯ�� ---> Root Application Context�� bean�� ��ϵǾ� �ִ��� Ȯ��

### RequestMapping ������̼� ���
- method �κ� ������ GET, POST �� ��� request�� ���� ����
```java
@RequestMapping(value="/user/join", method=RequestMethod.GET)
@RequestMapping(value="/user/join", method=RequestMethod.POST)
```
- value ���� ������ �� ���� �ִ�.
```java
@RequestMapping(value= {"/user/join", "/user/j"}, method=RequestMethod.POST)
```
- Controller�� ���� �� �� �޼��忡 ���� �ϴ� �͵� �����ϴ�.
```java
@Controller
@RequestMapping("/user")
public class UserController {
	// ...
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
```

### RequestParam ������̼� ���
- RequestParam ������̼� ����
- RequestParam ������̼ǿ��� value ����
- RequestParam ������̼ǿ� value�� ���� 3�� �� ��� ���� ������� ����
- ������ ����� ��õ
- POST ���� body�� RequestParam ���� �ִ� ��쿡�� �Ȱ��� ���� 
```java
	public String update(
			/*String name*/
			/*@RequestParam String name*/
			@RequestParam("name") String name) {
		System.out.println(name);
		return "BoardController:update";
	}
```
- �ʿ� ���ο� ����Ʈ �� ���� ����
  - required�� true�� �� ���·� URL�� �Ķ���͸� �������� �ʾƵ� defaultValue�� �����ϸ� ������ �߻����� �ʴ´�.
  - �Ʒ��� �������� age�� int���̰� defaultValue�� String�̾ int�� �ڵ� ����ȯ�� �߻��Ѵ�.
```java
public String write(
		@RequestParam(value="n", required=true, defaultValue="�͸� �����") String name,
		@RequestParam(value="age", required=true, defaultValue="0") int age)
```
- �н� ��� �Ķ���� ����
  - view?no=10 ���ٴ� view/10 �� ����� Restful ��Ŀ� �����ϴ�.
  - �Ķ������ ������ ���� ```{ }```�� ����ϸ� PathVariable ������̼��� �Բ� ����Ѵ�.
  - Ÿ�Կ� ���� �ʴ� ���� ������ Bad Request�� �߻��Ѵ�.
```java
	@RequestMapping("/board/view/{no}")
	public String view(@PathVariable(value="no") int no) {
```
- ModelAttribute ������̼�
  - �ش� ������̼��� ����ϸ� Model�� �ش� ���� �߰��Ǿ� View�� �ڵ������� �Ѿ��.
  - form���� ������ submit ���н� input �±��� ��� value �������� ���� ������ �� �ִ�.
```java
public String join(@ModelAttribute UserVo userVo) {

// JSP
<input id="name" name="name" type="text" value="${userVo.name }">
```

# Root Application Context (����Ͻ� ����)
- ���ø����̼� ���� ����� Listener���� contextInitialized�� ����
- contextConfigLocation ���� ������ �������� Root Application Context �����̳ʸ� �����.
- ���Ŀ� Dispatcher Servlet���� ��� ��û�� ������ �Ȱ��� ����
- web.xml ����
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
- web.xml�� ������ contextConfigLocation�� value ���� xml ���� ����
  - �ش� xml ������ ����Ͻ� ������ ���õ� ���� ������ �ȴ�.
  - applicationContext.xml ���� ����
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
- com.cafe24.mysite.repository�� Repository, Service, Component ������̼��� ��ĵ�Ѵ�.
  - �ش� ��Ű���� DAO�� �����, �� DAO�鿡 Repository ������̼��� �ۼ��Ѵ�.
```java
@Repository
public class GuestbookDao {

//...

@Repository
public class UserDao {
```
- �ѱ� ���� ó�� : web.xml�� filter ����
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

### ���� xml ���� ���ϵ� �⺻ ����
- web.xml
  - WEB�� ���õ� ��������
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
  - VO, DAO, Service �� ����Ͻ� ���� ó�� ��������
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
  - Controller �� MVC ó�� ��������
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

	<!-- validator, conversionService, messageConverter�� �ڵ����� ��� -->
	<mvc:annotation-driven />

	<!-- ���� �����̳��� ����Ʈ ���� ���� �ڵ鷯 -->
	<mvc:default-servlet-handler />

	<!-- �� ������ annotation���� �ϰڴ�.(@Controller�� ���� auto scanning) -->
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.mysite.controller" />

	<!-- ViewResolver ���� -->
	<bean id="viewResolver"
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.JstlView" />
		<property name="prefix" value="/WEB-INF/views/" />
		<property name="suffix" value=".jsp" />
	</bean>

</beans>
```

# ���ø����̼� ��Ű��ó ����
### ����Ͻ� �м� (����� ���丮 ����)
- ����ڴ�  ȸ�������� �Ѵ�.
- ����ڴ�  �α����� �Ѵ�.
- ����ڴ�  �α׾ƿ��� �Ѵ�.
- ����ڴ� ���Ͽ� ���� �����.
- ����ڴ� ���� �ڽ��� ���� �����Ѵ�.
- �α����� ����ڴ� �ڽ��� ������ �����Ѵ�.
- �α����� ����ڴ� �Խ��ǿ� ���� �ۼ��Ѵ�.
- �α����� ����ڴ� �ڽ��� ���� �����Ѵ�.
- �α����� ����ڴ� �ڽ��� ���� �����Ѵ�.
- �α����� ����ڴ� �ٸ� ����� �ۿ� ����� �����.
- ����ڴ� �Խ��Ǳ��� ����� �� �� �ִ�.
- ����ڴ� �Խ��Ǳ��� ���� �� �ִ�. 

### ������ ����
- UserService
  - ����ڴ�  ȸ�������� �Ѵ�. ( join )
  - ����ڴ�  �α����� �Ѵ�. (login)
  - ����ڴ�  �α׾ƿ��� �Ѵ�. (logout)
  - �α����� ����ڴ� �ڽ��� ������ �����Ѵ�. (modifyInformation)
- BoardService
  - �α����� ����ڴ� �Խ��ǿ� ���� �ۼ��Ѵ�. ( write )
  - �α����� ����ڴ� �ڽ��� ���� �����Ѵ�. ( modify )
  - �α����� ����ڴ� �ڽ��� ���� �����Ѵ�. ( remove )
  - �α����� ����ڴ� �ٸ� ����� �ۿ� ����� �����. ( write )
  - ����ڴ� �Խ��Ǳ��� ����� �� �� �ִ�. ( list )
  - ����ڴ� �Խ��Ǳ��� ���� �� �ִ�. ( view )
- GuestbookService
  - ����ڴ� ���Ͽ� ���� �����. (write)
  - ����ڴ� ���� �ڽ��� ���� �����Ѵ�. (remove)

![123](https://user-images.githubusercontent.com/20277647/57829407-d66c0700-77e9-11e9-8113-826c956e58bf.PNG)

