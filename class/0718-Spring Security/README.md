# Spring Security Taglib ���
### ���� ���̺귯�� �ٿ�
```
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-taglibs</artifactId>
    <version>5.0.7.RELEASE</version>
</dependency>
```
### jsp���� ���þ� �߰�
```
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
```
### ���
- ���� ���� Ȯ��
```jsp
<sec:authorize access="isAuthenticated()">
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
</sec:authorize>
```
- ���� ���� ��������
  - username(���̵�), password, authorities ---> principal X
  - �� ���� �ڽ��� �߰��� ������ ����� ���� ---> principal.
```jsp
<sec:authentication property="username"/>
<sec:authentication property="principal.name"/>
```
- ���ѿ� ���� ����
  - script�� �±׸� ���μ� �ڹٽ�ũ��Ʈ �κе� ���ѿ� ���� ��Ʈ�� ����
```jsp
<sec:authorized access="hasRole('ADMIN')" />
```

# Spring Security ���
### Spring Security�� ����� �α��ε� ����� ���� ��������
```java
		Object principal = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			principal = authentication.getPrincipal();
		}
		
		if(principal == null || principal.getClass() == String.class) {
			return null;
		}

		// SecurityUser user = (SecurityUser)principal;
		
		return principal;
```

### Access Denial Handler ����
```java
	// Interceptor URL�� ��û�� �����ϰ� ��ȣ(����)
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Access Denial Handler
		http
		.exceptionHandling()
		.accessDeniedPage("/WEB-INF/views/error/403.jsp");
	}
```

### Remember-me ����
- java
```java
	// Interceptor URL�� ��û�� �����ϰ� ��ȣ(����)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.rememberMe()
		.key("mysite3")
		.rememberMeParameter("remember-me");
	}
```
- jsp
```jsp
<form>
	<label class="block-label" >�ڵ��α���</label>
	<input name="remember-me" type="checkbox">
</form>
```