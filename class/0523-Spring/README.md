# 인터셉터
- Spring에서 HTTP Request와  HTTP Response를  Controller 앞과 뒤에서 가로채는 역할을 한다. 
- Servlet의 앞과 뒤에서 HTTP Request와  HTTP Response를 가로채는 필터와 유사하다.
- Interceptor를 구현하기 위해서는 HandlerInterceptor 인터페이스를 구현하여야 한다. 
- 서블릿 필터와 차이 : 호출되는 시점이 다르다
  - 사용자 요청 ---> (필터) ---> 디스패처 서블릿 ---> (필터)
  - 디스패처 서블릿 ---> 핸들러 맵핑 ---> (인터셉터) ---> 컨트롤러 ---> (인터셉터) ---> 디스패처 서블릿

### 인터셉터 구현
- preHandle() 메소드는 컨트롤러가 호출되기 전에 실행된다. handler 파라미터는 HandlerMapping 이 찾아준 컨트롤러의 메소드를 참조할 수 있는 HandlerMethod  오브젝트이다. 반환값이 true이면 핸들러의 다음 체인이 실행되지만 false이면 중단하고 남은 인터셉터와 컨트롤러가 실행되지 않는다.
  - 가장 많이 사용
  - Controller까지 요청을 보내려면 true, 그 전에 redirect나 forward를 하려면 false를 반환
- postHandle() 메소드는 컨트롤러가 실행된 후에 호출된다. 
- afterComplete() 은 뷰에서 최종결과가 생성하는 일을 포함한 모든 일이 완료 되었을 때 실행된다.

- HandlerInterceptor 인터페이스를 구현하는 클래스를 만드는 경우
```java
public class MyInterceptor01 implements HandlerInterceptor {

   boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler)
       throws Exception;
    
   void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception;

   void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception;
```
- 인터페이스를 구현하면 3개의 메서드를 모두 구현해야되기 때문에 HandlerInterceptorAdapter 클래스를 상속 받도록 하면 편하다.
  - 가장 많이 구현하는 preHandle 메서드만 오버라이딩하여 구현할 수 있다.
```java
public class MyInterceptor02 extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
```

- 인터셉터를 셋팅한다.
  - spring-servlet.xml 에서 셋팅
  - path에서 모든 URL에 대해 bean에 셋팅한 클래스의 인터셉터가 수행된다.
  - 인터셉터를 적용시키지 않을 경로에 대해서는 exclude-mapping으로 설정한다.
```xml
	<!-- Interceptors -->
    <mvc:interceptors>
        <mvc:interceptor>
           <mvc:mapping path="/**" />
           <mvc:exclude-mapping path="/user/auth"/>
           <mvc:exclude-mapping path="/user/logout"/>
           <mvc:exclude-mapping path="/assets/**"/> 
       		<bean class="com.cafe24.security.AuthInterceptor" />
        </mvc:interceptor>
    </mvc:interceptors>
```

# 어노테이션 만들기
- New ---> Annotation 타입의 클래스로 생성한다.
  - Target(ElementType.METHOD) : 메서드 위에 어노테이션을 붙인다.
  - Target(ElementType.TYPE) : 클래스 위에 어노테이션을 붙인다.
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Auth {

//	String value() default "USER";
//	int test() default 1;
	
	public enum Role {
		USER, ADMIN
	}
	
	public Role role() default Role.USER;
}
```

# 인터셉터 + 어노테이션 활용
```java
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Method에 @Auth 가 없으면
		// Class(Type)에 @Auth를 받아오기
//		if(auth==null) {
//			auth = ...
//		}
		
		// 5. @Auth가 안 붙어있는 경우 (Method에도 없고 클래스에도 없음)
		if(auth==null) {
			return true;
		}
		
		// 6. 클래스나 메서드에 @Auth가 붙어 있기 때문에 인증 여부를 확인
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute("authUser")==null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		// 7. Role 가져오기
		//Auth.Role role = auth.role();
		
		// 8. role이 Auth.Role.USER 라면, 인증된 모든 사용자는 접근 가능
//		if(role==Auth.Role.USER) {
//			return true;
//		}
		
		// 9. Admin Role 권한 체크
		// authUser.getRole().equals("ADMIN")
		
		return true;
	}

}
```
# 커스텀 어노테이션 사용시 어노테이션에 값 셋팅
- AuthUser 어노테이션을 단순하게 표기용으로 만든다.
  - Target을 PARAMETER을 했으므로 메서드의 매개변수에 어노테이션을 붙인다.
```java
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface AuthUser {

}
```
- Controller에서
```java
public String update(Model model, @AuthUser UserVo authUser)
```
- 어노테이션에 값 셋팅을 위해 HandlerMethodArgumentResolver 인터페이스를 구현한다.
```java
public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 붙어있지 않음
		if(authUser == null) {
			return false;
		}
		
		// Parameter 타입이 UserVo 클래스가 아닌 경우
		if(!parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		if(supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}

}
```
- spring-servlet.xml 설정파일의 <mvc:annotation-driven> 태그에서 bean 셋팅
```xml
<mvc:annotation-driven>
	<!-- Argument Resolver -->
	<mvc:argument-resolvers>
		<bean class="com.cafe24.security.AuthUserHandlerMethodArgumentResolver"/>
	</mvc:argument-resolvers>
		
</mvc:annotation-driven>
```
