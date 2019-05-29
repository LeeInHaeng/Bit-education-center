# ��Ƽ ������Ʈ
- Maven ������Ʈ���� �ֻ���� �θ� ������Ʈ�� Packaging�� pom���� ����
  - �θ� ������Ʈ�� pom.xml�� ���������� ���� �κе��� �ۼ�
- �ڽ� ������Ʈ�� New ---> Maven Module�� ������Ʈ ���� ---> Packaging�� �� ������Ʈ�� �°� jar �Ǵ� war�� ����
  - �θ��� pom.xml�� ��� �޴´�.
  - �ڽĿ����� ����ϴ� pom.xml �κ��� �ڽ����� pom.xml�� ���� �ۼ��Ѵ�.
- ����꿡 ���ε��� �� .settings, target, .classpath, .project�� �ø��� �ʵ��� �Ѵ�.

# xml ������ �ƴ� class ���Ϸ� �������� �и�

### �����ͺ��̽� ��������
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
- ������ AppConfig Ŭ���� ���Ͽ��� �ش� ���� ���� Ŭ���� Import
```java
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
```

### ���̹�Ƽ�� ��������
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
  - sqlSessionFactoryBean���� ConfigLocation�� mybatis�� ���� ���� (typeAlias, mapper �� ����)
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
- ������ AppConfig Ŭ���� ���Ͽ��� �ش� ���� ���� Ŭ���� Import
```java
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
```

### aspectj-autoproxy ����
- ������ applicationContext.xml���� aspectj-autoproxy ����
```xml
<aop:aspectj-autoproxy />
```
- �ش� ������ Ŭ���� ���Ϸ� ����
```java
@EnableAspectJAutoProxy
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
@ComponentScan({"com.cafe24.mysite.repository", "com.cafe24.mysite.service", "com.cafe24.mysite.aspect"})
```
### Ŭ���� ���Ϸ� ���������� ���� �� web.xml�� ���� Ŭ���� ���� ��� ����
  - com.cafe24.mysite.config.AppConfig : ���ø����̼ǰ� ���õ� ������
  - com.cafe24.mysite.config.WebConfig : ���� ���õ� ������
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
@EnableWebMvc
@ComponentScan({"com.cafe24.mysite.controller"})
public class WebConfig {
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

### CSS ����
- WebConfig���� WebMvcConfigurerAdapter�� ��� �޴´�.
  - configureDefaultServletHandling �������̵�
```java
public class WebConfig extends WebMvcConfigurerAdapter{
	// ...
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
```












### application ��������
- spring-servlet.xml �ּ�ó��
- web.xml���� ��� �߰� 1
  - servlet�� ���
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
- web.xml���� ��� �߰� 2
  - context-param�� ���
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
- �� ��° init-param(param-value)���� ������ ���� ��Ű�� ��ο� WebConfig Ŭ���� ���� ����
  - ������ application ���� ����(xml)���� �ϴ� ������ Ŭ���� ���Ϸ� ����
  - ������ ���õ� ������̼��� ���δ�.
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
