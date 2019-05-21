# Spring���� �α� ���

### Logback ���
- pom.xml�� spring-context�� dependency���� JCL�� ���� ��Ų��.
  - SLF4J ����� Logback ���̺귯���� �ΰŷ� ����� ���̱� ������
```xml
		<!-- spring -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${org.springframework-version}</version>
			
			<!-- JCL ���� -->
			<exclusions>
			   <exclusion>
				  <groupId>commons-logging</groupId>
				  <artifactId>commons-logging</artifactId>
			   </exclusion>
			</exclusions>
			
		</dependency>
```
- pom.xml���� Logback ���̺귯���� �ٿ� �޴´�.
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
- pom.xml���� logback ���� ����
```xml
	<properties>
		<org.springframework-version>4.2.1.RELEASE</org.springframework-version>
		<jcloverslf4j.version>1.7.6</jcloverslf4j.version>		
		<logback.version>1.1.1</logback.version>
	</properties>
```
- resource�� logback.xml�� �ش� �α��� ���� ����
  - fileAppender2�� ���� ����
  - file �±׿� ���� �ش� ��ο� .log ������ �����ȴ�.
  - triggeringPolicy���� logex2.log ������ ũ�Ⱑ MaxFileSize�� ������
  - rollingPolicy���� ������ �� ó�� logex2.1.log.zip ~ logex.2.10.log.zip���� ���� ���Ϸ� ���´�.
  - �ش� appender�� logger���� appender-ref�� ����Ѵ�.
  - ���� log�� level�� �����Ѵ�.
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
		com.cafe24.logex2 �Ʒ� ��Ű�� �α׵鸸  \logex\logex2.log ���Ͽ���  ����ϴ� �ΰ�
	-->
	<logger name="com.cafe24.logex2"  level="debug" additivity="false">
            <appender-ref ref="fileAppender2" />
    </logger>
    
	<!--
		com.cafe24.logex3 �Ʒ� ��Ű�� �α׵鸸  \logex\logex3.log ���ϰ� �ַܼ� ����ϴ� �ΰ�
	-->
	<logger name="com.cafe24.logex3"  level="warn" additivity="false">
            <appender-ref ref="fileAppender3" />
			<appender-ref ref="consoleAppender" />
    </logger>    
	
	<!-- ��Ʈ(�۷ι�) �ΰ� -->
	<root level="debug">
		<appender-ref ref="consoleAppender" />
	</root>

</configuration>
```
- Controller���� �ΰ� ����
  - .class�� �ش� Ŭ���� �̸��� �����ش�.
  - import org.apache.commons.logging.Log;
  - import org.apache.commons.logging.LogFactory;
```java
private static final Log LOG = LogFactory.getLog( ExampleController.class );
```
- �ΰ� ���
```java
		LOG.debug( "#ex1 - debug log" );
		LOG.info( "#ex1 - info log" );
		LOG.warn( "#ex1 - warn log" );
		LOG.error( "#ex1 - error log" );
```
- DEBUG  > INFO > WARN > ERROR ������ �αװ� ��� �Ǵ� ���� Ȯ���� �� �ִ�.
  - ����, WARN �����ϸ�, warn, error �޼����� �α� �޼����� ��� �ȴ�.

# ���� ���ε�
- Multipart Resolver
- pom.xml�� �ʿ��� ���̺귯�� �ٿ�
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
- spring-servlet.xml�� ��Ƽ��Ʈ �������� �����Ѵ�.
```xml
	<!-- ��Ƽ��Ʈ ������ -->
	<bean id="multipartResolver" 
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<!-- �ִ���ε� ������ ����Ʈũ�� -->
		<property name="maxUploadSize" value="52428800" />
		<!-- ��ũ�� �ӽ� ������ �����ϱ� ���� �޸𸮿� �����Ҽ��ִ� �ִ� ����Ʈ ũ�� -->
		<!-- property name="maxInMemorySize" value="52428800" /-->
		<!-- defaultEncoding -->
		<property name="defaultEncoding" value="utf-8" />
	</bean>	
```
- Controller���� MultipartFile�� ��ü�� �޴´�.
```java
	@PostMapping("/upload")
	public String upload(
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam(value="file1") MultipartFile multipartFile) {
		
		// db ó��
		return "form";
	}
```
- view���� form�� post�� �����͸� ������, enctype="multipart/form-data"�� �����ؾ� �Ѵ�.
- ���� input �±��� Ÿ���� file�� �����Ѵ�.
```jsp
<form method="post" action="upload" enctype="multipart/form-data">
	<label>����1:</label>
	<input type="file" name="file1">

	<input type="submit" value="upload">
</form>
```

### File ������ ���� Service Ŭ���� ����
- ���� ������ �ܺο�
- DB�� ������ �������� ���� (���� ������ �����ϴ� ���� URL�� ���Ͽ� ������ �� �ִ� ���� URL ��)
- ���� ���� ������ ���� ��ġ�� �ʵ��� �и�������� ���� �̸��� �����ؼ� �߰�
- �Ʒ��� ���� Ŭ���� ����
```java
@Service
public class FileuploadService {
	
	// ���ÿ� ����� ���� ���
	private static final String SAVE_PATH = "/mysite-uploads";
	// URL�� ������ ���
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
- spring-servlet.xml�� ���� ���� URL�� ���� ������ �����ϴ� ���� ����� URL�� ����
  - mapping : ���� ������ URL
  - location : ���� ������ ����Ǿ� �ִ� ���� ������ URL
```xml
	<!-- the mvc resources tag does the magic -->
	<mvc:resources mapping="/images/**" location="file:/mysite-uploads/" />
```