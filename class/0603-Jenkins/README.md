# Springboot jenkins 배포
- pom.xml에 내용 추가
```xml
	<build>
		<finalName>${artifactId}</finalName>
		<plugins>

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
					<path>/springbootMysite</path>
					<server>TomcatServer</server>
				</configuration>
			</plugin>

		</plugins>
	</build>
```

- Boot를 실행하는(Main 클래스가 있는) 위치에 해당 클래스 추가
  - SpringBootServletInitializer 클래스를 상속 받는다.
  - builder.sources에 Springboot를 실행하는 Main 클래스를 넣어준다.
```java
@EnableAutoConfiguration
public class BootInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BootApp.class);
	}
}
```
