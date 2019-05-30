# ��Ƽ ������Ʈ
- Maven ������Ʈ���� �ֻ���� �θ� ������Ʈ�� Packaging�� pom���� ����
  - �θ� ������Ʈ�� pom.xml�� ���������� ���� �κе��� �ۼ�
- �ڽ� ������Ʈ�� New ---> Maven Module�� ������Ʈ ���� ---> Packaging�� �� ������Ʈ�� �°� jar �Ǵ� war�� ����
  - �θ��� pom.xml�� ��� �޴´�.
  - �ڽĿ����� ����ϴ� pom.xml �κ��� �ڽ����� pom.xml�� ���� �ۼ��Ѵ�.
- ����꿡 ���ε��� �� .settings, target, .classpath, .project�� �ø��� �ʵ��� �Ѵ�.

# xml ������ �ƴ� class ���Ϸ� �������� �и� - applicationContext.xml

### Ŭ���� ���Ϸ� ���������� ���� �� web.xml�� ���� Ŭ���� ���� ��� ����
- com.cafe24.mysite.config.AppConfig : ���ø����̼ǰ� ���õ� ������
- com.cafe24.mysite.config.WebConfig : ���� ���õ� ������
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
- ���Ŀ� ���� ������ Import
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.service", "com.cafe24.mysite.repository", "com.cafe24.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
}
```

### DataSource(�����ͺ��̽�) ����
- ������ applicationContext.xml���� DataSource ����
```xml
	<!-- Connection Pool DataSource-->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
		<property name="url" value="jdbc:mariadb://192.168.1.51:3307/webdb" />
		<property name="username" value="webdb" />
		<property name="password" value="webdb" />
	</bean>
```
- �ش� ������ Ŭ���� ���Ϸ� ����
  - PropertySource�� �̿��� jdbc ����� ���õ� ���� �������� �о� ���δ�. (driverClassName, url, username, password ��)
  - �ش� �������� �б� ���ؼ��� Environment ��ü�� �̿��ؾ� �Ѵ�.
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
- jdbc.properties ������ ������ ����.
```
jdbc.diriverClassName=org.mariadb.jdbc.Driver
jdbc.url=jdbc:mariadb://192.168.1.51:3307/webdb
jdbc.username=webdb
jdbc.password=webdb
jdbc.initialSize=10
jdbc.maxActive=20
```

### MyBatis ����
- ������ applicationContext.xml���� SqlSessionFactoryBean�� SqlSessionTemplate ����
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
- �ش� ������ Ŭ���� ���Ϸ� ����
  - sqlSessionFactoryBean���� setConfigLocation�� mybatis�� ���� ���� (typeAlias, mapper �� ����)
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

### aspectj-autoproxy ����
- ������ applicationContext.xml���� aspectj-autoproxy ����
```xml
<aop:aspectj-autoproxy />
```
- �ش� ������ Ŭ���� ���Ϸ� ����
```java
@Configuration
@EnableAspectJAutoProxy
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig { }
```

### component-scan ����
- ������ applicationContext.xml���� component-scan ����
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
- �ش� ������ Ŭ���� ���Ϸ� ����
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.service", "com.cafe24.mysite.repository", "com.cafe24.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig { }
```

### annotation-config(���� ��ĳ��)�� component-scan ����
- ������ spring-servlet.xml���� annotation-config�� component-scan ����
```xml
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.mysite.controller" />
```
- �ش� ������ Ŭ���� ���Ϸ� ����
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.controller"})
public class WebConfig {
```

# xml ������ �ƴ� class ���Ϸ� �������� �и� - spring-servlet.xml
- ���� ������ Import
```java
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.controller", "com.cafe24.mysite.exception"})
@Import({MVCConfig.class, SecurityConfig.class, FileuploadConfig.class, MessageConfig.class})
public class WebConfig {
}
```

### ViewResolver ����
- ������ spring-servlet.xml���� InternalResourceViewResolver ����
```xml
	<!-- ViewResolver ���� -->
	<bean id="viewResolver"
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.JstlView" />
		<property name="prefix" value="/WEB-INF/views/" />
		<property name="suffix" value=".jsp" />
	</bean>
```
- �ش� ������ Ŭ���� ���Ϸ� ����
  - WebMvcConfigurerAdapter �� ��� �޴´�.
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

### default-servlet-handler ����
- ������ spring-servlet.xml���� ����
```xml
	<!-- ���� �����̳��� ����Ʈ ���� ���� �ڵ鷯 -->
	<mvc:default-servlet-handler />
```
- �ش� ������ Ŭ���� ���Ϸ� ����
  - WebMvcConfigurerAdapter �� ��� �޴´�.
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

### Message-Converters ����
- ������ spring-servlet.xml���� StringHttpMessageConverter�� MappingJackson2HttpMessageConverter ����
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
- �ش� ������ Ŭ���� ���Ϸ� ����
  - WebMvcConfigurerAdapter �� ��� �޴´�.
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
                                    
### MultipartResolver ����
- ���� ���ε�� ���� URL�� ����ϱ� ���� �����߾���.
- ������ spring-servlet.xml���� CommonsMultipartResolver ����
```xml
	<!-- the mvc resources tag does the magic -->
	<mvc:resources mapping="/images/**" location="file:/mysite-uploads/" />
	
	<!-- ��Ƽ��Ʈ ������ -->
	<bean id="multipartResolver" 
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">  -->
		<!-- �ִ���ε� ������ ����Ʈũ�� -->
		<property name="maxUploadSize" value="52428800" />
		<!-- ��ũ�� �ӽ� ������ �����ϱ� ���� �޸𸮿� �����Ҽ��ִ� �ִ� ����Ʈ ũ�� -->
		<!-- property name="maxInMemorySize" value="52428800" /-->
		<!-- defaultEncoding -->
		<property name="defaultEncoding" value="utf-8" />
	</bean>
```
- �ش� ������ Ŭ���� ���Ϸ� ����
  - PropertySource�� �̿��� ��Ƽ��Ʈ �������� ���õ� ���� ������ �о� ���δ�.
  - �ش� ���������� �б� ���� Environment ��ü�� ����Ѵ�.
  - WebMvcConfigurerAdapter �� ��� �޴´�.
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
- multipart.properties �� ������ ������ ����.
```
maxUploadSize=52428800
maxInMemorySize=4096
defaultEncoding=utf-8
uploadLocation=/mysite-uploads/
resourceMapping=/images/**
```

### MessageSource ����
- �ش� ������Ʈ������ Valid �˻�� �޼����� ������ ���� �����ֱ� ���� ����߾���.
- ������ spring-servlet.xml���� ResourceBundleMessageSource ����
```xml
	<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	   <property name="basenames">
	      <list>
		<value>messages/messages_ko</value>
	      </list>
	   </property>
	</bean>
```
- �ش� ������ Ŭ���� ���Ϸ� ����
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

### ArgumentResolver ����
- ������̼ǿ� ���� �����ϱ� ���� ��� �߾���.
- ������ spring-servlet.xml���� AuthUserHandlerMethodArgumentResolver(Ŀ���� Ŭ����) ����
  - �ش� Ŭ������ HandlerMethodArgumentResolver �������̽��� ������ Ŀ���� Ŭ����
```xml
		<mvc:argument-resolvers>
			<bean class="com.cafe24.security.AuthUserHandlerMethodArgumentResolver"/>
		</mvc:argument-resolvers>
```
- �ش� ������ Ŭ���� ���Ϸ� ����
  - WebMvcConfigurerAdapter �� ��� �޴´�.
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

### interceptors ����
- ������ spring-servlet.xml���� mvc:interceptors ����
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
- �ش� ������ Ŭ���� ���Ϸ� ����
  - bean�� ���ԵǴ� Ŭ���� ������ Bean ������̼��� �̿��� ��ü�� �����Ѵ�.
  - addInterceptors �޼��带 �������̵� �Ͽ� ���ͼ��Ϳ� ���õ� �������� ������ �����Ѵ�.
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