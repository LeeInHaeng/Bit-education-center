# Spring Error ����ó��

### web.xml�� http ���� �ڵ忡 ���� view ��ġ ����
- �ش� view�� ���������� URL�� �ٲ����� ����
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
### �۷ι� �ڵ鷯(������¡ ��Ʈ�ѷ�)�� �̿��� ���� ó��
- ��ü ���ø����̼ǿ� �����ϴ� ��� exception�� �� Ŭ������ ���´�.
- ControllerAdvice�� ExceptionHandler ������̼��� ���
- ���������� AOP �۵�
```java
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler( Exception.class )
	public String handlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) {

		return "error/exception";
	}
}
```
- ����ó�� Ŭ���� �α�ó��
```java
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());
```
- ����ó�� Ŭ���� �ȳ������� ���� + ��������(response)
```java
		request.setAttribute("uri", request.getRequestURI());
		request.setAttribute("exception", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
```
- ��ĵ�Ǵ� �׸� �۷ι� ����ó�� Ŭ���� �߰�
  - spring-servlet.xml ���� �ش� Ŭ������ ��ĳ�� �ǵ��� ����
```xml
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.mysite.controller, com.cafe24.mysite.exception" />
```
- view���� request�� �Ѱ� ���� �޽����� ó��
```jsp
	<h1>Ooooops! - ${uri }</h1>
	<p>
		���ܹ߻�!!!<br>
		======================<br>
		<pre>
${exception }
		</pre>
	</p>
```

# Spring MyBatis ���

### DataSource�� �̿��� DB ����
- pom.xml���� �ʿ��� ���̺귯�� �ٿ�
```xml
<!-- spring jdbc -->
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-jdbc</artifactId>
	<version>${org.springframework-version}</version>
</dependency>

<!-- Common DBCP -->
<dependency>
	<groupId>commons-dbcp</groupId>
	<artifactId>commons-dbcp</artifactId>
	<version>1.4</version>
</dependency>
```
- Root Application Context ���������� applicationContext.xml�� Datasource�� bean�� ���
  - �ش� ������ mariadb ����
```xml
	<!-- Connection Pool DataSource-->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
		<property name="url" value="jdbc:mariadb://192.168.1.51:3307/webdb" />
		<property name="username" value="webdb" />
		<property name="password" value="webdb" />
	</bean>
```

### Spring���� Mybatis ���� �� DAO���� ���
- pom.xml���� MyBatis ���� ���̺귯�� �ٿ�
```xml
		<!-- MyBatis -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.2.2</version>
		</dependency>
		
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.2.0</version>
		</dependency>
```
- Root Application Context ���������� applicationContext.xml�� SqlSessionFactoryBean ����
  - dataSource�� property���� ref�� ������ bean���� ������ dataSource�� id�� �ִ´�.
  - configLocation�� mybatis ���� ��ü ���� ������ �ִ´�.
```xml
<!-- MyBatis SqlSessionFactoryBean --> 
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
	<property name="dataSource" ref="oracleDatasource" /> 
	<property name="configLocation" value="classpath:mybatis/configuration.xml" /> 
</bean>
```
- Root Application Context ���������� applicationContext.xml�� SqlSessionTemplete ����
  - �������� arg���� ref�� SqlSessionFactoryBean�� ������ ������ id�� �ִ´�.
```xml
	<!-- MyBatis SqlSessionTemplate --> 
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg index="0" ref="sqlSessionFactory" />
	</bean>
```
- SqlSessionFactoryBean���� ������ MyBatis ��ü ���� ������ configuration.xml ������ �����.
  - typeAliases �±׿��� VO Ŭ�����鿡 ���� alias ���
  - alias�� ������� ���� ��� VO�� ��� �� ��ü ��Ű������ �� �����־�� �Ѵ�.
  - mappers���� mapper resource�� ������ SQL�� ���� ��ų xml ���ϵ��� �����.
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<typeAliases>
		<typeAlias alias="uservo" type="com.cafe24.mysite.vo.UserVo"></typeAlias>
		<typeAlias alias="guestbookvo" type="com.cafe24.mysite.vo.GuestbookVo"></typeAlias>
	</typeAliases>	
	<mappers>
		<mapper resource="mybatis/mapper/user.xml" />
		<mapper resource="mybatis/mapper/guestbook.xml" />
	</mappers>
</configuration>
```
- mapper�� user.xml ���� �⺻ ���ø�
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="user">
</mapper>
```
- DAO���� SqlSession�� ����Ѵ�.
```java
	@Autowired
	private SqlSession sqlSession;
```
- DAO���� insert, delete, update�� ��쿡�� int Ÿ���� ��ȯ�Ѵ�.
  - ������ ������ SqlSession ��ü�� ����Ѵ�.
  - ù ��° ���ڷ� namespace�̸�.��������id �� �����ش�.
  - �� ��° ���ڷ� �Ѱ��� �Ķ���Ͱ� ������� �Ķ���͸� �����Ѵ�. (������ X)
```java
int count = sqlSession.insert("user.insert",vo);
```
- DAO���� sqlSession���� select�� ����ϴ� ���
  - ��ȯ Ÿ���� List��� selectList, �ϳ���� selectOne�� ����Ѵ�.

### MyBatis mapper xml ���� ���� ����
- �Ķ���Ͱ� ��ü�� ������ ���
  - parameterType�� alias�� ��ü�� �����ش�.
  - ```#{ }```�� DB�� ����Ǵ� �÷����� �״�� ����Ѵ�.
  - ��, select���� �÷� ���� ������ ��� as�� ����� �÷� ���� �ܼ��ϰ� �ٲ۴�.
  - �� �� VO Ŭ������ �ʵ忡�� �÷Ÿ� �ش��ϴ� �ʵ���� getter �޼��带 ã�Ƽ� ���� ���´�. 
```xml
	<insert id="insert" parameterType="uservo">
	<![CDATA[
		insert into user
		values(null, #{name}, #{email}, #{password}, #{gender}, now())
	]]>
	</insert>
```
- �Ķ���Ͱ� map Ÿ���� ������ ���
  - parameterType�� map�� �����ش�.
  - DAO���� map�� ���� �� �� ��° ���ڷ� �Ѱ��ش�.
```
// DAO
Map<String, String> map = new HashMap<String,String>();
map.put("email",email);
map.put("password",password);
UserVo result = sqlSession.selectOne("user.getByEmailAndPassword", map);

// xml
	<select id="getByEmailAndPassword" parameterType="map"
		resultType="uservo">
	<![CDATA[
		select no, name
		from user where email=#{email} and password=#{password}
	]]>
	</select>
```
- �Ķ���Ͱ� long Ÿ���̰� ���� Ÿ���� UserVo�� ���
```xml
	<select id="getByNo" parameterType="long" resultType="uservo">
	<![CDATA[
		select no, name, email, password, gender
		from user where no=#{no}
	]]>
	</select>
```
- ������ ���� ���� ó��1
  - if test�� ����Ѵ�.
  - JSTL ������ ����ϰ� choose, when ���� ������ �ִ�.
```xml
	<update id="update" parameterType="uservo">
	<![CDATA[
	update user set
	name=#{name},
	]]>
	 
	<if test="password != ''">
		<![CDATA[
			password=#{password},
		]]>
	</if>
	
	<![CDATA[
	gender=#{gender}
	where no=#{no}
	]]>
	</update>
```
- ������ ���� ���� ó��2
  - ���� ó��1�� ����ó�� �÷����� ����� �� �ִ� ��쿡�� �ش� �÷����� ����ϸ� �ȴ�.
  - ������ ��ü�� �ƴ� String�̳� int, long ���� �Ķ���ͷ� �Ѿ�� �̸� ����ó�� �� ��쿡�� �ƹ� �̸��� ���� ������ �߻��Ѵ�.
  - �̸� �ذ��ϱ� ���� _parameter�� ����Ѵ�.
```xml
	<if test='_parameter > 0'>
	<![CDATA[
		where no < #{_parameter}
	]]>
	</if>
```
- � ������ ���� �� �� ����� �̾ ���� ������ ������ ���
  - selectKey�� ����Ѵ�.
  - �������� �� �Ѿ���� ��� �÷� Ȥ�� �ѱ� ��� �÷��� keyProperty�� �޴´�.
  - ��ȯ Ÿ���� �ִٸ� resultType�� �����ش�.
  - order�� �̿��� BEFORE, AFTER ���� ������ �����ش�.
```xml
	<insert id="insert" parameterType="uservo">
	<![CDATA[
		insert into user
		values(null, #{name}, #{email}, #{password}, #{gender}, now())
	]]>
	<selectKey keyProperty="no" resultType="long" order="AFTER">
	<![CDATA[
		select last_insert_id()
	]]>
	</selectKey>
	</insert>
```