# 멀티 프로젝트
- Maven 프로젝트에서 최상단의 부모 프로젝트의 Packaging은 pom으로 지정
  - 부모 프로젝트의 pom.xml에 공통적으로 들어가는 부분들은 작성
- 자식 프로젝트는 New ---> Maven Module로 프로젝트 생성 ---> Packaging은 각 프로젝트에 맞게 jar 또는 war로 지정
  - 부모의 pom.xml을 상속 받는다.
  - 자식에서만 사용하는 pom.xml 부분은 자식쪽의 pom.xml에 따로 작성한다.
- 깃허브에 업로드할 시 .settings, target, .classpath, .project는 올리지 않도록 한다.

# xml 파일이 아닌 class 파일로 설정파일 분리

### 데이터베이스 설정파일
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
```java
@Configuration
@EnableTransactionManagement
public class DBConfig {

	@Bean
	public DataSource basicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mariadb://192.168.1.51:3307/webdb");
		basicDataSource.setUsername("webdb");
		basicDataSource.setPassword("webdb");
		basicDataSource.setInitialSize(10);
		basicDataSource.setMaxActive(100);

		return basicDataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}
}
```
- 이후이 AppConfig 클래스 파일에서 해당 설정 파일 클래스 Import
```java
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
```

### 마이바티스 설정파일
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
  - sqlSessionFactoryBean에서 ConfigLocation은 mybatis의 설정 파일 (typeAlias, mapper 등 설정)
```java
@Configuration
public class MyBatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(
			DataSource dataSource, 
			ApplicationContext applicationContext) throws Exception {
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(
				applicationContext.getResource("classpath:com/cafe24/config/app/mybatis/configuration.xml"));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
	      return new SqlSessionTemplate(sqlSessionFactory);
	   }
}
```
- 이후이 AppConfig 클래스 파일에서 해당 설정 파일 클래스 Import
```java
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
```

### aspectj-autoproxy 설정
- 기존의 applicationContext.xml에서 aspectj-autoproxy 설정
```xml
<aop:aspectj-autoproxy />
```
- 해당 설정을 클래스 파일로 변경
```java
@EnableAspectJAutoProxy
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
@ComponentScan({"com.cafe24.mysite.repository", "com.cafe24.mysite.service", "com.cafe24.mysite.aspect"})
```
### 클래스 파일로 설정파일을 만들 시 web.xml에 설정 클래스 파일 경로 설정
  - com.cafe24.mysite.config.AppConfig : 어플리케이션과 관련된 설정들
  - com.cafe24.mysite.config.WebConfig : 웹과 관련된 설정들
```xml
	<context-param>
		<param-name>contextClass</param-name>
		<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	</context-param>
	<!-- Context Loader Listener (Business) -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>com.cafe24.mysite.config.AppConfig</param-value>
	</context-param>

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- Dispatcher Server(Front Controller) -->
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
@EnableWebMvc
@ComponentScan({"com.cafe24.mysite.controller"})
public class WebConfig {
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
```java
public class WebConfig {
	
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

### CSS 적용
- WebConfig에서 WebMvcConfigurerAdapter를 상속 받는다.
  - configureDefaultServletHandling 오버라이딩
```java
public class WebConfig extends WebMvcConfigurerAdapter{
	// ...
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
```












### application 설정파일
- spring-servlet.xml 주석처리
- web.xml에서 기능 추가 1
  - servlet인 경우
```xml
	<!-- Dispatcher Server(Front Controller) -->	
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextClass</param-name>
			<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
		</init-param>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>com.cafe24.springex.config.WebConfig</param-value>
		</init-param>
	</servlet>
```
- web.xml에서 기능 추가 2
  - context-param인 경우
```xml
	<context-param>
		<param-name>contextClass</param-name>
		<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	</context-param>
	<!-- Context Loader Listener (Business) -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:com.cafe24.mysite.config.AppConfig</param-value>
	</context-param>
```
- 두 번째 init-param(param-value)에서 설정해 놓은 패키지 경로에 WebConfig 클래스 파일 생성
  - 기존의 application 설정 파일(xml)에서 하던 동작을 클래스 파일로 설정
  - 설정과 관련된 어노테이션을 붙인다.
```java
@Configuration
@EnableWebMvc
@ComponentScan({"com.cafe24.springex.controller"})
public class WebConfig {
	
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
