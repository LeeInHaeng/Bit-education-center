# web.xml ���������� Ŭ���� ���Ϸ� ��ü
- ������ applicationContext.xml�� spring-servlet.xml�� ��� Ŭ���� ���Ϸ� ������ ���� �۾�
- �� ���� xml ���� ������ ��� Ŭ���� ���Ϸ� ��ȯ�� �� web.xml�� ���뵵 ���� �� �ִ�.
- ���� web.xml ������ ������ ����.
```xml
<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>

	<!-- Context Loader Listener (Business) -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- Dispatcher Server(Front Controller) -->
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>spring</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

	<!-- Encoding Filter -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>

		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>

	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	<!-- ���� ���� ������ -->

	<error-page>
		<error-code>404</error-code>
		<location>/WEB-INF/views/error/404.jsp</location>
	</error-page>
		<error-page>
		<error-code>500</error-code>
		<location>/WEB-INF/views/error/500.jsp</location>
	</error-page>
```
- ������ web.xml ������ ���� ���� ������ �κи� ����� ��� �����Ѵ�.
```xml
	<!-- ���� ���� ������ -->
	<error-page>
		<error-code>404</error-code>
		<location>/WEB-INF/views/error/404.jsp</location>
	</error-page>
	<error-page>
		<error-code>500</error-code>
		<location>/WEB-INF/views/error/500.jsp</location>
	</error-page>
```
- Ŭ���� ������ �����.
  - �ش� Ŭ���� ������ AbstractAnnotationConfigDispatcherServletInitializer �� ��� �޴´�.
```java
public class MysiteWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new CharacterEncodingFilter("UTF-8", true) };
	}

	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		DispatcherServlet dispatcherServlet = (DispatcherServlet)super.createDispatcherServlet(servletAppContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}
}
```

# SpringBoot
- SpringBoot �� �̿��� ������ �������ϵ�(xml���� Ȥ�� Ŭ���� ����)�� ������ Ȯ ���� �� �ִ�.
- ����� ���� �⺻���� pom.xml ������ ������ ����.
```xml
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>

		<!-- Spring Web(MVC) -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
		</dependency>
		
		<!-- JSTL -->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
		</dependency>

		<!-- Spring AOP(AspectJ) -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
```
- SpringBoot�� ������Ű�� ���ؼ��� main�� �ִ� Ŭ���� ������ ������ �Ѵ�.
  - �ش� Ŭ������ SpringBootApplication ������̼��� ����Ѵ�.
```java
@SpringBootApplication
public class BootApp {
	public static void main(String[] args) {
		SpringApplication.run(BootApp.class, args);
	}
}
```
- SpringBoot�� ���õ� ���� ������ �⺻������ src/main/resources�� application.properties ������ �о� ���δ�.
```
spring.profile.active=develope

# server
server.port=8080
server.context-path=/

# devtools
spring.devtools.livereload.enabled=true

# http
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=true
spring.http.encoding.force-request=true
spring.http.encoding.force-response=true

# AOP
# add @EnableAspectJAutoProxy
spring.aop.auto=true
spring.aop-proxy-target-class=true

# logging
logging.config=classpath:logback.xml
```

### �����ͺ��̽�(DataSource), MyBatis ����
- ������ Ŭ���� ���Ϸ� �����ߴ� DataSource �κ��� SpringBoot �������� ��ü�Ѵ�.
- pom.xml���� ���� ���̺귯�� �ٿ�
```xml
		<!-- Mybatis Starter -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.2</version>
		</dependency>
		
		<!-- Common DBCP -->
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>1.4</version>
		</dependency>
		
		<!-- mariadb java client -->
		<dependency>
			<groupId>org.mariadb.jdbc</groupId>
			<artifactId>mariadb-java-client</artifactId>
		</dependency>
```
- application.properties ���� datasource ����
```
# data source
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://192.168.1.51:3307/webdb
spring.datasource.username=webdb
spring.datasource.password=webdb

# mybatis
mybatis.config-location=classpath:mybatis/configuration.xml
```

### ViewResolver ����
- application.properties ���� ����
```
# mvc
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

### MultipartResolver ����
- application.properties ���� ����
```
# multipart
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```

### MessageSource ����
- application.properties ���� ����
```
# international (Message Source)
spring.messages.always-use-message-format=true
spring.messages.basename=classpath:messages/messages_ko.properties
spring.messages.encoding=UTF-8
```

### Message-Converters �� ArgumentResolver, interceptors ����
- �ش� �������� ������ ���� Ŭ���� ���Ϸ� �����Ѵ�.
- Ŭ���� ���Ϸ� ������ WebMvcConfigurer �������̽��� �����ϰ�, Configuration ������̼��� ����Ѵ�.
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
	//
	// Messaage Converter
	//
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
			.indentOutput( true )
			.dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
			.modulesToInstall(new ParameterNamesModule() );
		
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(builder.build());  		
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));	

		return converter;		
	}
	
	@Bean
	public StringHttpMessageConverter  stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", Charset.forName("UTF-8"))));	
		
		return converter;
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
		converters.add(stringHttpMessageConverter());
	}
	
	//
	// Argument Resolver
	//
	@Bean
	public AuthUserHandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}
	
	@Bean
	public AuthLoginInterceptor authLoginInterceptor() {
		return new AuthLoginInterceptor();
	}

	@Bean
	public AuthLogoutInterceptor authLogoutInterceptor() {
		return new AuthLogoutInterceptor();
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}
	
	//
	// Interceptor
	//
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(authLoginInterceptor())
		.addPathPatterns("/user/auth");
		
		registry
		.addInterceptor(authLogoutInterceptor())
		.addPathPatterns("/user/logout");
		
		registry
		.addInterceptor(authInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/user/auth")
		.excludePathPatterns("/user/logout")
		.excludePathPatterns("/assets/**");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:/mysite-uploads/");
	}
}
```