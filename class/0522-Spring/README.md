# AOP

### AOP ����
- Aspect Oriented Programming : ���� ���� ���α׷���
- ���� ���ʰ� �Ǵ� ������ ������ �и�
  - �ٽɰ��� : �ý����� �ٽ� ��ġ�� ������ �״�� �巯�� ���ɿ���
  - Ⱦ�ܰ���: �ٽɰ��� ���ݿ� ���� �ݺ������� ������ �Ǵ� �α�, Ʈ�����, ����, ����, ���ҽ� Ǯ��, ����üũ ���� ���ɿ���
  - ������ �и�: ���� �ٽɰ��ɿ� ���� �����ϴ� Ⱦ�ܰ����� �и��Ͽ� �������� ���� ����� �ٽɰ����� ����Ǵ� ���� Ⱦ�ܰ����� ȣ���ϴ� �ڵ带 ���� ������� �ʰ� ���������� ó��
  - �ٽɰ��� ����� �߰��߰����� �ʿ��� Ⱦ�ܰ��� ����� ���� ȣ������ �ʰ� ����(Weaving)�̶� �Ҹ��� �۾��� �̿��Ͽ� Ⱦ�ܰ��� �ڵ尡 ���Եǵ��� �����. 
  - �ٽɰ��ɸ�⿡���� Ⱦ�ܰ��ɸ���� �������� ���� �ν��� �ʿ䰡 ����

### AOP �������
- JoinPoint (����)
  - Ⱦ�ܰ��ɸ���� �ڵ��� �ƹ� ���� ������ �Ǵ� �� �ƴϴ�.
  - ��������Ʈ�� �Ҹ��� Ư�� ���������� ������ �����ϴ�.
  - AOP �����ӿ�ũ�� ���� �����Ǵ� ��������Ʈ�� �ٸ��� �������� �޼��� ��������Ʈ�� ������ 
  - � �޼��������� ������ �ָ� �ȴ�. (ex �޼��尡 ȣ��ǰų� ���ϵǴ� ����)
  - After, Before, After Returning, After Throwing, Around
- PointCut (��𿡼�)
  - ��� ��������Ʈ�� Ⱦ�ܰ��ɸ���� ���������� �����ϴ� ���
  - Ⱦ�ܰ����� ���Ե� Ư�� Ŭ������ Ư�� �޼ҵ带 �����ϴ� ��� ���� 
- Advice (or Interceptor, ������)
  - Ⱦ�ܰ��ɸ��(�α�, ����, Ʈ����� ��)
- Aspect (Advisor)
  - ��𿡼� ������ ���� �� ���ΰ�?
  - PointCut + Advice�� ���� 

### AOP ����ϱ�
- applicationContext.xml�� ����
```xml
    <!-- auto proxy -->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```
- pom.xml���� �ʿ��� ���̺귯�� �ٿ�
```xml
<!-- spring aspect -->
<dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-aspects</artifactId>
    	<version>${org.springframework-version}</version>
</dependency>
```
- Aspect�� ���� Ŭ������ Aspect ������̼� �ۼ�
```java
@Aspect
@Component
public class MyAspect {

}
```
- Aspect ����
```java

// *..* : ��� ��Ű��
// ��� ��Ű���� ProductService���� ��� �޼��忡 ���� ���� (����Ÿ��, �Ű����� ��� ����)

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
		
		// PointCut �Ǵ� �޼��� ȣ��
		Object [] parameters = {"Camera"};
		Object result = pjp.proceed(parameters); // �߰��� �Ķ���� ���� ����
		
		//Object result = pjp.proceed(); // �Ķ���Ͱ� ������ �״�� ȣ��

		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		
		/* after advice */
		System.out.println("--- around after advice ---");
		
		return result;
	}

/* ���
--- around before advice ---
--- before advice ---
finding.......
--- around after advice ---
--- after advice ---
--- afterReturning advice ---
*/
```

# Valid
- mvc:annotation-driven �� ���� �����ϰ� Bean Validation�� ����� �� �ִ�.
- ������ ���� Bean���� @Valid �� ����Ͽ� �ڵ� ������ �ϰ� �ȴ�.
- ���� ������ �� ������Ʈ�� �޸� ��������  Annotation�� �̿��� ���� �۾��� �̷�� ����.
- Ŭ���̾�Ʈ, ���� ���ʿ��� üũ�ϴ� ���� ����.
  - Javascript�� ��� ���� �� UI UX Ȯ��, ���������� ���� ����

### ���� ������̼�
- @AssertFalse  : �����ΰ�?	
- @Max : ���� �� �����ΰ�?
- @AssertTrue : ���ΰ�?	
- @Min : ���� �� �̻��ΰ�?
- @DecimalMax : ���� �� ���� �Ǽ��ΰ�?
- @NotNull : Null��  �ƴѰ�?
- @DecimalMin : ���� �� �̻� �Ǽ��ΰ�?
- @Null : Null�ΰ�?
- @Digits ( integer=, fraction=) : ��� ���� ������ ����, �Ҽ� �ڸ� �� �̳� �ΰ�?
- @Pattern(regex=, flag=) : ���Խ��� ���� �ϴ°�?
- @Future :  �̷���¥�ΰ�?
- @Past : ���� ��¥�ΰ�?
- @Size(min=, max=) : ���ڿ�, �迭 ���� ũ�Ⱑ ����ũ�⸦ ���� �ϴ°�?
- @NotEmpty  : Empty ���� �ƴѰ�?	
- @Email: �̸��� ����
- @URL: URL ����	
- @Length(min=, max=): ���ڿ� ���� min��  max �����ΰ�?
- @Range(min=, max=): ���� ���� üũ

### Valid ���
- pom.xml���� �ʿ� ���̺귯�� �ٿ�
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
- VO ��ü���� ��ȿ�� ������ ���� ������̼��� �����Ѵ�.
```java
	@NotEmpty
	@Length(min=2, max=8)
	public String getName() {
		return name;
	}
```
- Controller���� ��ȿ�� ������ �ҷ��� �ϴ� �Ķ���Ϳ� Valid ������̼��� ����Ѵ�.
  - BindingResult�� �ִ� getModel�� ����ϸ� �������� Map Ÿ������ ����.
  - Map���� ���� �������� model�� �̿��� View�� �ѱ��.
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
- Message�� ��ϵǾ� �ִ� Resource ���Ͽ��� �ؽ�Ʈ�� �о� JSP�� ����ϱ� ���� bean ���
  - spring-servlet.xml�� MessageSource�� bean���� ���
  - value ���� ������ ���� ���� (src/main/resources/messages/messages_ko.properties)
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
- View(JSP)���� ��ȿ�� �˻� ��� 1
  - taglib �߰�
  - spring:hasBindErrors �±׸� ����Ѵ�. (�ſ� ������)
```jsp
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<label class="block-label" for="name">�̸�</label>
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

- View(JSP)���� ��ȿ�� �˻� ��� 2
  - form �±׸� ����Ѵ�.
  - ������ �ִ� form �±׸� form:form���� �����Ѵ�.
  - ������ spring:hasBindErrors�� ���ְ� �����ϰ� form:errors�� ��ü�Ѵ�.
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
- form:form���� modelAttribute�� userVo ���� �������� ������ Controller���� POST ��� �Ӹ� �ƴ϶� GET ��� ������ ModelAttribute�� UserVo�� �� ��ü�� ������ �־�� �Ѵ�.
```java
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
```

### ������ �߻��� ���׵�
- form:form �±� �ȿ� input ���� ��� ä���� �� �־�� �Ѵ�.
  - Controller���� POST ��� �Ӹ� �ƴ϶� GET ��� ������ ModelAttribute ������̼��� �̿��� �� ��ü�� �Ѱ� �־�� �Ѵ�.
  - �ش� ������̼��� ����� �� ���ٸ� model.addAttribute �� �̿��Ѵ�.
  - ��ü �Ӹ� �ƴ϶� input �±׸� ��� ä���� �Ǳ� ������ GET ��Ŀ��� model.addAttribute�� ������ ���� �ѱ��.
- POST���� ���� �߻��� forward�� �ؾ��ϱ� ������ POST�� GET ����� URL�� ���ƾ� �Ѵ�.