# AOP

### AOP 개요
- Aspect Oriented Programming : 관점 지향 프로그래밍
- 가장 기초가 되는 개념은 관심의 분리
  - 핵심관심 : 시스템의 핵심 가치와 목적이 그대로 드러난 관심영역
  - 횡단관심: 핵심관심 전반에 걸쳐 반복적으로 나오게 되는 로깅, 트랜잭션, 보안, 인증, 리소스 풀링, 에러체크 등의 관심영역
  - 관심의 분리: 여러 핵심관심에 걸쳐 등장하는 횡단관심을 분리하여 독립적인 모듈로 만들고 핵심관심이 실행되는 동안 횡단관심을 호출하는 코드를 직접 명시하지 않고 선언적으로 처리
  - 핵심관심 모듈의 중간중간에서 필요한 횡단관심 모듈을 직접 호출하지 않고 위빙(Weaving)이라 불리는 작업을 이용하여 횡단관심 코드가 삽입되도록 만든다. 
  - 핵심관심모듈에서는 횡단관심모듈이 무엇인지 조차 인식할 필요가 없음

### AOP 구성요소
- JoinPoint (언제)
  - 횡단관심모듈은 코드의 아무 때나 삽입이 되는 건 아니다.
  - 조인포인트라 불리는 특정 시점에서만 삽입이 가능하다.
  - AOP 프레임워크에 따라 제공되는 조인포인트는 다르며 스프링은 메서드 조인포인트만 제공함 
  - 어떤 메서드인지만 지정해 주면 된다. (ex 메서드가 호출되거나 리턴되는 시점)
  - After, Before, After Returning, After Throwing, Around
- PointCut (어디에서)
  - 어느 조인포인트에 횡단관심모듈을 삽입할지를 결정하는 기능
  - 횡단관심이 삽입될 특정 클래스의 특정 메소드를 선택하는 방법 정의 
- Advice (or Interceptor, 무엇을)
  - 횡단관심모듈(로깅, 보안, 트랜잭션 등)
- Aspect (Advisor)
  - 어디에서 무엇을 언제 할 것인가?
  - PointCut + Advice를 정의 

### AOP 사용하기
- applicationContext.xml에 설정
```xml
    <!-- auto proxy -->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```
- pom.xml에서 필요한 라이브러리 다운
```xml
<!-- spring aspect -->
<dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-aspects</artifactId>
    	<version>${org.springframework-version}</version>
</dependency>
```
- Aspect로 만들 클래스에 Aspect 어노테이션 작성
```java
@Aspect
@Component
public class MyAspect {

}
```
- Aspect 예제
```java

// *..* : 모든 패키지
// 모든 패키지의 ProductService에서 모든 메서드에 대해 수행 (리턴타입, 매개변수 모두 생략)

	@Before("execution(ProductVo com.cafe24.aoptest.ProductService.find(String))")
	public void beforeAdvice() {
		System.out.println("--- before advice ---");
	}
	
	@After("execution(* *..*.ProductService.*(..))")
	public void afterAdvice() {
		System.out.println("--- after advice ---");
	}
	
	@AfterReturning("execution(* *..*.ProductService.*(..))")
	public void afterReturningAdvice() {
		System.out.println("--- afterReturning advice ---");
	}
	
	@AfterThrowing(value="execution(* *..*.ProductService.*(..))", throwing="ex")
	public void afterThrowingAdvice(Throwable ex) {
		System.out.println("--- afterThrowing advice ---" + ex);
	}
	
	@Around("execution(* *..*.ProductService.*(..))")
	public Object roundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		
		/* before advice */
		System.out.println("--- around before advice ---");
		
		// PointCut 되는 메서드 호출
		Object [] parameters = {"Camera"};
		Object result = pjp.proceed(parameters); // 중간에 파라미터 변경 가능
		
		//Object result = pjp.proceed(); // 파라미터가 없으면 그대로 호출

		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		
		/* after advice */
		System.out.println("--- around after advice ---");
		
		return result;
	}

/* 결과
--- around before advice ---
--- before advice ---
finding.......
--- around after advice ---
--- after advice ---
--- afterReturning advice ---
*/
```

# Valid
- mvc:annotation-driven 을 통해 간단하게 Bean Validation을 사용할 수 있다.
- 검증을 위한 Bean에는 @Valid 를 사용하여 자동 검증을 하게 된다.
- 실제 검증은 모델 오브젝트에 달린 제약조건  Annotation을 이용해 검증 작업이 이루어 진다.
- 클라이언트, 서버 양쪽에서 체크하는 것이 좋다.
  - Javascript는 양식 검증 및 UI UX 확보, 서버에서는 보안 검증

### 제공 어노테이션
- @AssertFalse  : 거짓인가?	
- @Max : 지정 값 이하인가?
- @AssertTrue : 참인가?	
- @Min : 지정 값 이상인가?
- @DecimalMax : 지정 값 이하 실수인가?
- @NotNull : Null이  아닌가?
- @DecimalMin : 지정 값 이상 실수인가?
- @Null : Null인가?
- @Digits ( integer=, fraction=) : 대상 수가 지정된 정수, 소수 자리 수 이내 인가?
- @Pattern(regex=, flag=) : 정규식을 만족 하는가?
- @Future :  미래날짜인가?
- @Past : 과거 날짜인가?
- @Size(min=, max=) : 문자열, 배열 등의 크기가 지정크기를 만족 하는가?
- @NotEmpty  : Empty 값이 아닌가?	
- @Email: 이메일 형식
- @URL: URL 형식	
- @Length(min=, max=): 문자열 길이 min과  max 사이인가?
- @Range(min=, max=): 숫자 범위 체크

### Valid 사용
- pom.xml에서 필요 라이브러리 다운
```xml
		<!-- validation -->
		<dependency>
		   <groupId>javax.validation</groupId>
		   <artifactId>validation-api</artifactId>
		   <version>1.0.0.GA</version>
		</dependency>
		
		<dependency>
		   <groupId>org.hibernate</groupId>
		   <artifactId>hibernate-validator</artifactId>
		   <version>4.2.0.Final</version>
		</dependency>
```
- VO 객체에서 유효성 검증을 위한 어노테이션을 설정한다.
```java
	@NotEmpty
	@Length(min=2, max=8)
	public String getName() {
		return name;
	}
```
- Controller에서 유효성 검증을 할려고 하는 파라미터에 Valid 어노테이션을 사용한다.
  - BindingResult에 있는 getModel을 사용하면 에러들이 Map 타입으로 담긴다.
  - Map으로 담은 에러들을 model을 이용해 View에 넘긴다.
```java
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
```
- Message가 기록되어 있는 Resource 파일에서 텍스트를 읽어 JSP에 출력하기 위해 bean 등록
  - spring-servlet.xml에 MessageSource를 bean으로 등록
  - value 값에 셋팅한 파일 생성 (src/main/resources/messages/messages_ko.properties)
```xml
	 <!-- 6. MessageSource -->
	<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	   <property name="basenames">
	      <list>
		<value>messages/messages_ko</value>
	      </list>
	   </property>
	</bean>

// messages_ko.properties
NotEmpty.userVo.name=\uC774\uB984\uC774 \uBE44\uC5B4\uC788\uC2B5\uB2C8\uB2E4.
Length.userVo.name=\uC774\uB984\uC740 2~8\uC790 \uC774\uC5B4\uC57C \uD569\uB2C8\uB2E4.

NotEmpty.userVo.email=\uC774\uBA54\uC77C\uC774 \uBE44\uC5B4\uC788\uC2B5\uB2C8\uB2E4.
Email.userVo.email=\uC774\uBA54\uC77C \uD615\uC2DD\uC774 \uC544\uB2D9\uB2C8\uB2E4.
```
- View(JSP)에서 유효성 검사 사용 1
  - taglib 추가
  - spring:hasBindErrors 태그를 사용한다. (매우 복잡함)
```jsp
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<label class="block-label" for="name">이름</label>
<input id="name" name="name" type="text" value="">
	<spring:hasBindErrors name="userVo">
		<c:if test="${errors.hasFieldErrors('name') }">
		<p style="font-weight:bold; color:red; text-align:left; padding:0">
			<spring:message 
				 code="${errors.getFieldError( 'name' ).codes[0] }"
				text="${errors.getFieldError( 'name' ).defaultMessage }" />
		</p>
		</c:if>
	</spring:hasBindErrors>
```

- View(JSP)에서 유효성 검사 사용 2
  - form 태그를 사용한다.
  - 기존에 있던 form 태그를 form:form으로 변경한다.
  - 기존의 spring:hasBindErrors를 없애고 간단하게 form:errors로 대체한다.
```jsp
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<form:form modelAttribute="userVo"
	id="join-form" name="joinForm"
	method="post" action="${pageContext.servletContext.contextPath }/user/join">

		<form:input path="email" />		

		<p style="font-weight:bold; color:red; text-align:left; padding:0">
			<form:errors path="email" />
		</p>
```
- form:form에서 modelAttribute는 userVo 값을 가져오기 때문에 Controller에서 POST 방식 뿐만 아니라 GET 방식 에서도 ModelAttribute로 UserVo의 빈 객체를 지정해 주어야 한다.
```java
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
```

### 오류가 발생한 사항들
- form:form 태그 안에 input 값은 모두 채워질 수 있어야 한다.
  - Controller에서 POST 방식 뿐만 아니라 GET 방식 에서도 ModelAttribute 어노테이션을 이용해 빈 객체를 넘겨 주어야 한다.
  - 해당 어노테이션을 사용할 수 없다면 model.addAttribute 를 이용한다.
  - 객체 뿐만 아니라 input 태그를 모두 채워야 되기 때문에 GET 방식에서 model.addAttribute로 적절한 값을 넘긴다.
- POST에서 문제 발생시 forward를 해야하기 때문에 POST와 GET 방식의 URL은 같아야 한다.