# Springboot jenkins ����
- pom.xml�� ���� �߰�
```xml
	<build>
	
		<finalName>${project.artifactId}</finalName>
		<plugins>
		
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>

			<plugin>
				<artifactId>maven-war-plugin</artifactId>
				<configuration>
					<warSourceDirectory>src/main/webapp</warSourceDirectory>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>tomcat-maven-plugin</artifactId>
				<configuration>
					<url>http://127.0.0.1:8080/manager/text</url>
					<path>/jblog5</path>
					<server>TomcatServer</server>
				</configuration>
			</plugin>

		</plugins>
		
	</build>
```

- Boot�� �����ϴ�(Main Ŭ������ �ִ�) ��ġ�� �ش� Ŭ���� �߰�
  - SpringBootServletInitializer Ŭ������ ��� �޴´�.
  - builder.sources�� Springboot�� �����ϴ� Main Ŭ������ �־��ش�.
```java
@EnableAutoConfiguration
public class BootInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BootApp.class);
	}
}
```
