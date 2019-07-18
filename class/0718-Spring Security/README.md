# Spring Security Taglib 사용
### 관련 라이브러리 다운
```
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-taglibs</artifactId>
    <version>5.0.7.RELEASE</version>
</dependency>
```
### jsp에서 지시어 추가
```
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
```
### 사용
- 인증 여부 확인
```jsp
<sec:authorize access="isAuthenticated()">
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
</sec:authorize>
```
- 인증 정보 가져오기
  - username(아이디), password, authorities ---> principal X
  - 그 외의 자신이 추가로 정의한 사용자 정보 ---> principal.
```jsp
<sec:authentication property="username"/>
<sec:authentication property="principal.name"/>
```
- 권한에 따른 설정
  - script를 태그를 감싸서 자바스크립트 부분도 권한에 따른 컨트롤 가능
```jsp
<sec:authorized access="hasRole('ADMIN')" />
```

# Spring Security 사용
### Spring Security에 저장된 로그인된 사용자 정보 가져오기
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

### Access Denial Handler 예제
```java
	// Interceptor URL의 요청을 안전하게 보호(보안)
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Access Denial Handler
		http
		.exceptionHandling()
		.accessDeniedPage("/WEB-INF/views/error/403.jsp");
	}
```

### Remember-me 구현
- java
```java
	// Interceptor URL의 요청을 안전하게 보호(보안)
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
	<label class="block-label" >자동로그인</label>
	<input name="remember-me" type="checkbox">
</form>
```