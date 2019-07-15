# Ŀ���� Valid ������̼� ����
- Validator Ŭ����
  - ConstraintValidator �������̽��� �����Ѵ�
  - ù ��° ���ڷδ� ����� ������̼��� ����, �� ��° ���ڷδ� ��ȿ�� ������ �� ������ Ÿ��
  - ������ �ۼ�
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
- ������̼� �ۼ�
  - Constraint�� ������ ������ Validator Ŭ������ ������ش�
  - ��ȿ�� ���н� ������ default �޼��� �ۼ�
```java
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy=TelValidator.class)
public @interface TelValid {

	String message() default "�ùٸ��� ���� ��ȭ��ȣ ���� �Դϴ�.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
```
- Vo���� ������̼��� ����Ѵ�
```java
	@TelValid
	private String tel;
```