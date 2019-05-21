# Spring에서 로그 사용

### Logback 사용
- pom.xml의 spring-context의 dependency에서 JCL은 제외 시킨다.
  - SLF4J 기반의 Logback 라이브러리를 로거로 사용할 것이기 때문에
```xml
		<!-- spring -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${org.springframework-version}</version>
			
			<!-- JCL 제외 -->
			<exclusions>
			   <exclusion>
				  <groupId>commons-logging</groupId>
				  <artifactId>commons-logging</artifactId>
			   </exclusion>
			</exclusions>
			
		</dependency>
```
- pom.xml에서 Logback 라이브러리를 다운 받는다.
```xml
		<!-- Logback --> 
		<dependency>                                    
			<groupId>org.slf4j</groupId>                
			<artifactId>jcl-over-slf4j</artifactId>     
			<version>${jcloverslf4j.version}</version>  
		</dependency>
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
			<version>${logback.version}</version>
		</dependency>
```
- pom.xml에서 logback 버전 관리
```xml
	<properties>
		<org.springframework-version>4.2.1.RELEASE</org.springframework-version>
		<jcloverslf4j.version>1.7.6</jcloverslf4j.version>		
		<logback.version>1.1.1</logback.version>
	</properties>
```
- resource에 logback.xml이 해당 로그의 설정 파일
  - fileAppender2를 예로 보면
  - file 태그에 의해 해당 경로에 .log 파일이 생성된다.
  - triggeringPolicy에서 logex2.log 파일의 크기가 MaxFileSize를 넘으면
  - rollingPolicy에서 설정한 것 처럼 logex2.1.log.zip ~ logex.2.10.log.zip까지 압축 파일로 묶는다.
  - 해당 appender를 logger에서 appender-ref로 등록한다.
  - 찍을 log의 level을 설정한다.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

	<appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
		<encoder>
			<charset>UTF-8</charset>
			<Pattern>
				%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n
			</Pattern>
		</encoder>
	</appender>

	<appender name="fileAppender2" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>/logex/logex2.log</file>
		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<Pattern>
				%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n
			</Pattern>
		</encoder>
		<rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
			<FileNamePattern>/logex/logex2.%i.log.zip</FileNamePattern>
			<MinIndex>1</MinIndex>
			<MaxIndex>10</MaxIndex>
		</rollingPolicy>
		<triggeringPolicy
			class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
			<MaxFileSize>50KB</MaxFileSize>
		</triggeringPolicy>
	</appender>
	
	<appender name="fileAppender3" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>/logex/logex3.log</file>
		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<Pattern>
				%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n
			</Pattern>
		</encoder>
		<rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
			<FileNamePattern>/logex/logex3.%i.log.zip</FileNamePattern>
			<MinIndex>1</MinIndex>
			<MaxIndex>10</MaxIndex>
		</rollingPolicy>
		<triggeringPolicy
			class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
			<MaxFileSize>1MB</MaxFileSize>
		</triggeringPolicy>
	</appender>	


	<!--
		com.cafe24.logex2 아래 패키지 로그들만  \logex\logex2.log 파일에만  출력하는 로거
	-->
	<logger name="com.cafe24.logex2"  level="debug" additivity="false">
            <appender-ref ref="fileAppender2" />
    </logger>
    
	<!--
		com.cafe24.logex3 아래 패키지 로그들만  \logex\logex3.log 파일과 콘솔로 출력하는 로거
	-->
	<logger name="com.cafe24.logex3"  level="warn" additivity="false">
            <appender-ref ref="fileAppender3" />
			<appender-ref ref="consoleAppender" />
    </logger>    
	
	<!-- 루트(글로벌) 로거 -->
	<root level="debug">
		<appender-ref ref="consoleAppender" />
	</root>

</configuration>
```
- Controller에서 로거 생성
  - .class는 해당 클래스 이름을 적어준다.
  - import org.apache.commons.logging.Log;
  - import org.apache.commons.logging.LogFactory;
```java
private static final Log LOG = LogFactory.getLog( ExampleController.class );
```
- 로거 사용
```java
		LOG.debug( "#ex1 - debug log" );
		LOG.info( "#ex1 - info log" );
		LOG.warn( "#ex1 - warn log" );
		LOG.error( "#ex1 - error log" );
```
- DEBUG  > INFO > WARN > ERROR 순으로 로그가 출력 되는 것을 확인할 수 있다.
  - 가령, WARN 설정하면, warn, error 메서드의 로그 메세지만 출력 된다.

# 파일 업로드
- Multipart Resolver
- pom.xml에 필요한 라이브러리 다운
```xml
		<!-- common fileupload -->
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.2.1</version>
		</dependency>
		
		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>1.4</version>
		</dependency>
```
- spring-servlet.xml에 멀티파트 리졸버를 설정한다.
```xml
	<!-- 멀티파트 리졸버 -->
	<bean id="multipartResolver" 
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<!-- 최대업로드 가능한 바이트크기 -->
		<property name="maxUploadSize" value="52428800" />
		<!-- 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기 -->
		<!-- property name="maxInMemorySize" value="52428800" /-->
		<!-- defaultEncoding -->
		<property name="defaultEncoding" value="utf-8" />
	</bean>	
```
- Controller에서 MultipartFile로 객체를 받는다.
```java
	@PostMapping("/upload")
	public String upload(
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam(value="file1") MultipartFile multipartFile) {
		
		// db 처리
		return "form";
	}
```
- view에서 form의 post로 데이터를 보내며, enctype="multipart/form-data"을 지정해야 한다.
- 또한 input 태그의 타입은 file로 지정한다.
```jsp
<form method="post" action="upload" enctype="multipart/form-data">
	<label>파일1:</label>
	<input type="file" name="file1">

	<input type="submit" value="upload">
</form>
```

### File 저장을 위한 Service 클래스 예제
- 파일 저장은 외부에
- DB에 파일의 정보들을 저장 (실제 파일이 존재하는 로컬 URL과 파일에 접근할 수 있는 가상 URL 등)
- 파일 명을 저장할 때는 겹치지 않도록 밀리세컨드로 파일 이름을 변경해서 추가
- 아래는 서비스 클래스 예제
```java
@Service
public class FileuploadService {
	
	// 로컬에 저장될 파일 경로
	private static final String SAVE_PATH = "/mysite-uploads";
	// URL에 생성할 경로
	private static final String URL = "/images";
	
	public String restore(MultipartFile multipartFile) {
		String url = "";
		
		if(multipartFile.isEmpty()) {
			return url;
		}
		
		String originalFilename = multipartFile.getOriginalFilename();
		String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);
		String saveFileName = generateSaveFileName(extName);
		long fileSize = multipartFile.getSize();

		try {
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			
			url = URL + "/" + saveFileName;
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error : " + e);
		}
		
		return url;
	}
	
	private String generateSaveFileName(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}
}
```
- spring-servlet.xml에 가상 파일 URL과 실제 파일이 존재하는 로컬 경로의 URL을 맵핑
  - mapping : 가상 파일의 URL
  - location : 실제 파일이 저장되어 있는 로컬 파일의 URL
```xml
	<!-- the mvc resources tag does the magic -->
	<mvc:resources mapping="/images/**" location="file:/mysite-uploads/" />
```