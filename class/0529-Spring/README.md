# 멀티 프로젝트
- Maven 프로젝트에서 최상단의 부모 프로젝트의 Packaging은 pom으로 지정
  - 부모 프로젝트의 pom.xml에 공통적으로 들어가는 부분들은 작성
- 자식 프로젝트는 New ---> Maven Module로 프로젝트 생성 ---> Packaging은 각 프로젝트에 맞게 jar 또는 war로 지정
  - 부모의 pom.xml을 상속 받는다.
  - 자식에서만 사용하는 pom.xml 부분은 자식쪽의 pom.xml에 따로 작성한다.
- 깃허브에 업로드할 시 .settings, target, .classpath, .project는 올리지 않도록 한다.

# xml 파일이 아닌 class 파일로 설정파일 분리 - applicationContext.xml

### 클래스 파일로 설정파일을 만들 시 web.xml에 설정 클래스 파일 경로 설정
- com.cafe24.mysite.config.AppConfig : 어플리케이션과 관련된 설정들
- com.cafe24.mysite.config.WebConfig : 웹과 관련된 설정들
```xml
    <!-- AppConfig -->
	<context-param>
		<param-name>contextClass</param-name>
		<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	</context-param>
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>com.cafe24.mysite.config.AppConfig</param-value>
	</context-param>

	<!-- Context Loader Listener -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- Dispatcher Server(Front Controller), WebConfig -->	
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextClass</param-name>
			<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
		</init-param>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>com.cafe24.mysite.config.WebConfig</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>	
	</servlet>
```
- 이후에 설정 파일을 Import
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.service", "com.cafe24.mysite.repository", "com.cafe24.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
}
```

### DataSource(데이터베이스) 설정
- 기존의 applicationContext.xml에서 DataSource 설정
```xml
	<!-- Connection Pool DataSource-->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
		<property name="url" value="jdbc:mariadb://192.168.1.51:3307/webdb" />
		<property name="username" value="webdb" />
		<property name="password" value="webdb" />
	</bean>
```
- 해당 설정을 클래스 파일로 변경
  - PropertySource를 이용해 jdbc 연결과 관련된 설정 정보들을 읽어 들인다. (driverClassName, url, username, password 등)
  - 해당 정보들을 읽기 위해서는 Environment 객체를 이용해야 한다.
```java
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:com/cafe24/config/app/properties/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource basicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env.getProperty("jdbc.diriverClassName"));
		basicDataSource.setUrl(env.getProperty("jdbc.url"));
		basicDataSource.setUsername(env.getProperty("jdbc.username"));
		basicDataSource.setPassword(env.getProperty("jdbc.password"));
		basicDataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));
		basicDataSource.setMaxActive(env.getProperty("jdbc.initialSize", Integer.class));
		
		return basicDataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager( DataSource dataSource ) {
		return new DataSourceTransactionManager(dataSource);
	}
}
```
- jdbc.properties 내용은 다음과 같다.
```
jdbc.diriverClassName=org.mariadb.jdbc.Driver
jdbc.url=jdbc:mariadb://192.168.1.51:3307/webdb
jdbc.username=webdb
jdbc.password=webdb
jdbc.initialSize=10
jdbc.maxActive=20
```

### MyBatis 설정
- 기존의 applicationContext.xml에서 SqlSessionFactoryBean과 SqlSessionTemplate 설정
```xml
	<!-- MyBatis SqlSessionFactoryBean --> 
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
		<property name="dataSource" ref="dataSource" /> 
		<property name="configLocation" value="classpath:mybatis/configuration.xml" /> 
	</bean>
	
	<!-- MyBatis SqlSessionTemplate --> 
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg index="0" ref="sqlSessionFactory" />
	</bean>
```
- 해당 설정을 클래스 파일로 변경
  - sqlSessionFactoryBean에서 setConfigLocation은 mybatis의 설정 파일 (typeAlias, mapper 등 설정)
```java
@Configuration
public class MyBatisConfig {
	
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean 
			= new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:com/cafe24/config/app/mybatis/configuration.xml"));

		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
```

### aspectj-autoproxy 설정
- 기존의 applicationContext.xml에서 aspectj-autoproxy 설정
```xml
<aop:aspectj-autoproxy />
```
- 해당 설정을 클래스 파일로 변경
```java
@Configuration
@EnableAspectJAutoProxy
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig { }
```

### component-scan 설정
- 기존의 applicationContext.xml에서 component-scan 설정
```xml
	<context:component-scan
		base-package="com.cafe24.mysite.repository, com.cafe24.mysite.service,
			com.cafe24.mysite.aspect">
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Repository" />
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Service" />
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Component" />
	</context:component-scan>
```
- 해당 설정을 클래스 파일로 변경
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.service", "com.cafe24.mysite.repository", "com.cafe24.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig { }
```

### annotation-config(오토 스캐닝)의 component-scan 설정
- 기존의 spring-servlet.xml에서 annotation-config와 component-scan 설정
```xml
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.mysite.controller" />
```
- 해당 설정을 클래스 파일로 변경
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.controller"})
public class WebConfig {
```

# xml 파일이 아닌 class 파일로 설정파일 분리 - spring-servlet.xml
- 설정 파일을 Import
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.controller", "com.cafe24.mysite.exception"})
@Import({MVCConfig.class, SecurityConfig.class, FileuploadConfig.class, MessageConfig.class})
public class WebConfig {
}
```

### ViewResolver 설정
- 기존의 spring-servlet.xml에서 InternalResourceViewResolver 설정
```xml
	<!-- ViewResolver 설정 -->
	<bean id="viewResolver"
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.JstlView" />
		<property name="prefix" value="/WEB-INF/views/" />
		<property name="suffix" value=".jsp" />
	</bean>
```
- 해당 설정을 클래스 파일로 변경
  - WebMvcConfigurerAdapter 를 상속 받는다.
```java
@Configuration
@EnableWebMvc
public class MVCConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);

		return resolver;
	}
}
```

### default-servlet-handler 설정
- 기존의 spring-servlet.xml에서 설정
```xml
	<!-- 서블릿 컨테이너의 디폴트 서블릿 위임 핸들러 -->
	<mvc:default-servlet-handler />
```
- 해당 설정을 클래스 파일로 변경
  - WebMvcConfigurerAdapter 를 상속 받는다.
```java
@Configuration
@EnableWebMvc
public class MVCConfig extends WebMvcConfigurerAdapter {
    @Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
```

### Message-Converters 설정
- 기존의 spring-servlet.xml에서 StringHttpMessageConverter와 MappingJackson2HttpMessageConverter 설정
```xml
	<mvc:annotation-driven>
		<mvc:message-converters>
			<bean
				class="org.springframework.http.converter.StringHttpMessageConverter">
				<property name="supportedMediaTypes">
					<list>
						<value>text/html; charset=UTF-8</value>
					</list>
				</property>
			</bean>
			<bean
				class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
				<property name="supportedMediaTypes">
					<list>
						<value>application/json; charset=UTF-8</value>
					</list>
				</property>
			</bean>
		</mvc:message-converters>
		
	</mvc:annotation-driven>
```
- 해당 설정을 클래스 파일로 변경
  - WebMvcConfigurerAdapter 를 상속 받는다.
```java
@Configuration
@EnableWebMvc
public class MVCConfig extends WebMvcConfigurerAdapter {
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
}
```
                                    
### MultipartResolver 설정
- 파일 업로드와 가상 URL을 사용하기 위해 설정했었다.
- 기존의 spring-servlet.xml에서 CommonsMultipartResolver 설정
```xml
	<!-- the mvc resources tag does the magic -->
	<mvc:resources mapping="/images/**" location="file:/mysite-uploads/" />
	
	<!-- 멀티파트 리졸버 -->
	<bean id="multipartResolver" 
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">  -->
		<!-- 최대업로드 가능한 바이트크기 -->
		<property name="maxUploadSize" value="52428800" />
		<!-- 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기 -->
		<!-- property name="maxInMemorySize" value="52428800" /-->
		<!-- defaultEncoding -->
		<property name="defaultEncoding" value="utf-8" />
	</bean>
```
- 해당 설정을 클래스 파일로 변경
  - PropertySource를 이용해 멀티파트 리졸버와 관련된 설정 파일을 읽어 들인다.
  - 해당 설정파일을 읽기 위해 Environment 객체를 사용한다.
  - WebMvcConfigurerAdapter 를 상속 받는다.
```java
@Configuration
@EnableWebMvc
@PropertySource("classpath:com/cafe24/config/web/properties/multipart.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(env.getProperty("maxUploadSize", Long.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("defaultEncoding"));
		
		return multipartResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:/mysite-uploads/");
	}
}
```
- multipart.properties 의 내용은 다음과 같다.
```
maxUploadSize=52428800
maxInMemorySize=4096
defaultEncoding=utf-8
uploadLocation=/mysite-uploads/
resourceMapping=/images/**
```

### MessageSource 설정
- 해당 프로젝트에서는 Valid 검사시 메세지를 설정해 놓고 보여주기 위해 사용했었다.
- 기존의 spring-servlet.xml에서 ResourceBundleMessageSource 설정
```xml
	<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	   <property name="basenames">
	      <list>
		<value>messages/messages_ko</value>
	      </list>
	   </property>
	</bean>
```
- 해당 설정을 클래스 파일로 변경
```java
@Configuration
public class MessageConfig {
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("com/cafe24/config/web/messages/messages_ko");
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;
	}
}
```

### ArgumentResolver 설정
- 어노테이션에 값을 셋팅하기 위해 사용 했었다.
- 기존의 spring-servlet.xml에서 AuthUserHandlerMethodArgumentResolver(커스텀 클래스) 설정
  - 해당 클래스는 HandlerMethodArgumentResolver 인터페이스를 구현한 커스텀 클래스
```xml
		<mvc:argument-resolvers>
			<bean class="com.cafe24.security.AuthUserHandlerMethodArgumentResolver"/>
		</mvc:argument-resolvers>
```
- 해당 설정을 클래스 파일로 변경
  - WebMvcConfigurerAdapter 를 상속 받는다.
```java
@Configuration
@EnableWebMvc
public class SecurityConfig extends WebMvcConfigurerAdapter {
	@Bean
	public AuthUserHandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}
```

### interceptors 설정
- 기존의 spring-servlet.xml에서 mvc:interceptors 설정
```xml
    <mvc:interceptors>
        <mvc:interceptor>
        	<mvc:mapping path="/user/auth" /> 
    		<bean class="com.cafe24.security.AuthLoginInterceptor" />
        </mvc:interceptor>
        
        <mvc:interceptor>
        	<mvc:mapping path="/user/logout" /> 
    		<bean class="com.cafe24.security.AuthLogoutInterceptor" />
        </mvc:interceptor>
        
        <mvc:interceptor>
           <mvc:mapping path="/**" />
           <mvc:exclude-mapping path="/user/auth"/>
           <mvc:exclude-mapping path="/user/logout"/>
           <mvc:exclude-mapping path="/assets/**"/> 
       		<bean class="com.cafe24.security.AuthInterceptor" />
        </mvc:interceptor>
    </mvc:interceptors>
```
- 해당 설정을 클래스 파일로 변경
  - bean에 주입되는 클래스 파일을 Bean 어노테이션을 이용해 객체를 생성한다.
  - addInterceptors 메서드를 오버라이딩 하여 인터셉터와 관련된 여러가지 설정을 진행한다.
```java
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
```