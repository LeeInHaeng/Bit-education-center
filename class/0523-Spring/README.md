# ���ͼ���
- Spring���� HTTP Request��  HTTP Response��  Controller �հ� �ڿ��� ����ä�� ������ �Ѵ�. 
- Servlet�� �հ� �ڿ��� HTTP Request��  HTTP Response�� ����ä�� ���Ϳ� �����ϴ�.
- Interceptor�� �����ϱ� ���ؼ��� HandlerInterceptor �������̽��� �����Ͽ��� �Ѵ�. 
- ���� ���Ϳ� ���� : ȣ��Ǵ� ������ �ٸ���
  - ����� ��û ---> (����) ---> ����ó ���� ---> (����)
  - ����ó ���� ---> �ڵ鷯 ���� ---> (���ͼ���) ---> ��Ʈ�ѷ� ---> (���ͼ���) ---> ����ó ����

### ���ͼ��� ����
- preHandle() �޼ҵ�� ��Ʈ�ѷ��� ȣ��Ǳ� ���� ����ȴ�. handler �Ķ���ʹ� HandlerMapping �� ã���� ��Ʈ�ѷ��� �޼ҵ带 ������ �� �ִ� HandlerMethod  ������Ʈ�̴�. ��ȯ���� true�̸� �ڵ鷯�� ���� ü���� ��������� false�̸� �ߴ��ϰ� ���� ���ͼ��Ϳ� ��Ʈ�ѷ��� ������� �ʴ´�.
  - ���� ���� ���
  - Controller���� ��û�� �������� true, �� ���� redirect�� forward�� �Ϸ��� false�� ��ȯ
- postHandle() �޼ҵ�� ��Ʈ�ѷ��� ����� �Ŀ� ȣ��ȴ�. 
- afterComplete() �� �信�� ��������� �����ϴ� ���� ������ ��� ���� �Ϸ� �Ǿ��� �� ����ȴ�.

- HandlerInterceptor �������̽��� �����ϴ� Ŭ������ ����� ���
```java
public class MyInterceptor01 implements HandlerInterceptor {

   boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler)
       throws Exception;
    
   void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception;

   void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception;
```
- �������̽��� �����ϸ� 3���� �޼��带 ��� �����ؾߵǱ� ������ HandlerInterceptorAdapter Ŭ������ ��� �޵��� �ϸ� ���ϴ�.
  - ���� ���� �����ϴ� preHandle �޼��常 �������̵��Ͽ� ������ �� �ִ�.
```java
public class MyInterceptor02 extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
```

- ���ͼ��͸� �����Ѵ�.
  - spring-servlet.xml ���� ����
  - path���� ��� URL�� ���� bean�� ������ Ŭ������ ���ͼ��Ͱ� ����ȴ�.
  - ���ͼ��͸� �����Ű�� ���� ��ο� ���ؼ��� exclude-mapping���� �����Ѵ�.
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

# ������̼� �����
- New ---> Annotation Ÿ���� Ŭ������ �����Ѵ�.
  - Target(ElementType.METHOD) : �޼��� ���� ������̼��� ���δ�.
  - Target(ElementType.TYPE) : Ŭ���� ���� ������̼��� ���δ�.
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

# ���ͼ��� + ������̼� Ȱ��
```java
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. handler ���� Ȯ��
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. Method�� @Auth �޾ƿ���
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Method�� @Auth �� ������
		// Class(Type)�� @Auth�� �޾ƿ���
//		if(auth==null) {
//			auth = ...
//		}
		
		// 5. @Auth�� �� �پ��ִ� ��� (Method���� ���� Ŭ�������� ����)
		if(auth==null) {
			return true;
		}
		
		// 6. Ŭ������ �޼��忡 @Auth�� �پ� �ֱ� ������ ���� ���θ� Ȯ��
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute("authUser")==null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		// 7. Role ��������
		//Auth.Role role = auth.role();
		
		// 8. role�� Auth.Role.USER ���, ������ ��� ����ڴ� ���� ����
//		if(role==Auth.Role.USER) {
//			return true;
//		}
		
		// 9. Admin Role ���� üũ
		// authUser.getRole().equals("ADMIN")
		
		return true;
	}

}
```
# Ŀ���� ������̼� ���� ������̼ǿ� �� ����
- AuthUser ������̼��� �ܼ��ϰ� ǥ������� �����.
  - Target�� PARAMETER�� �����Ƿ� �޼����� �Ű������� ������̼��� ���δ�.
```java
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface AuthUser {

}
```
- Controller����
```java
public String update(Model model, @AuthUser UserVo authUser)
```
- ������̼ǿ� �� ������ ���� HandlerMethodArgumentResolver �������̽��� �����Ѵ�.
```java
public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser�� �پ����� ����
		if(authUser == null) {
			return false;
		}
		
		// Parameter Ÿ���� UserVo Ŭ������ �ƴ� ���
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
- spring-servlet.xml ���������� <mvc:annotation-driven> �±׿��� bean ����
```xml
<mvc:annotation-driven>
	<!-- Argument Resolver -->
	<mvc:argument-resolvers>
		<bean class="com.cafe24.security.AuthUserHandlerMethodArgumentResolver"/>
	</mvc:argument-resolvers>
		
</mvc:annotation-driven>
```
