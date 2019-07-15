# 커스텀 Valid 어노테이션 예제
- Validator 클래스
  - ConstraintValidator 인터페이스를 구현한다
  - 첫 번째 인자로는 사용할 어노테이션을 맵핑, 두 번째 인자로는 유효성 검증을 할 데이터 타입
  - 패턴을 작성
```java
public class TelValidator implements ConstraintValidator<TelValid, String> {

	private Pattern pattern = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || value.length()==0 || "".equals(value))
			return false;
	
		return pattern.matcher(value).matches();
	}

}
```
- 어노테이션 작성
  - Constraint에 위에서 구현한 Validator 클래스를 등록해준다
  - 유효성 실패시 보여줄 default 메세지 작성
```java
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy=TelValidator.class)
public @interface TelValid {

	String message() default "올바르지 않은 전화번호 형식 입니다.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
```
- Vo에서 어노테이션을 사용한다
```java
	@TelValid
	private String tel;
```