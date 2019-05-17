# Spring Error 예외처리

### web.xml에 http 상태 코드에 따른 view 위치 맵핑
- 해당 view를 보여주지만 URL을 바꾸지는 않음
```xml
	<!-- 공통 에러 페이지 -->
	<error-page>
		<error-code>404</error-code>
		<location>/WEB-INF/views/error/404.jsp</location>
	</error-page>
		<error-page>
		<error-code>500</error-code>
		<location>/WEB-INF/views/error/500.jsp</location>
	</error-page>
```
### 글로벌 핸들러(어드바이징 컨트롤러)를 이용한 예외 처리
- 전체 어플리케이션에 적용하는 모든 exception이 이 클래스로 들어온다.
- ControllerAdvice와 ExceptionHandler 어노테이션을 사용
- 내부적으로 AOP 작동
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
- 예외처리 클래스 로깅처리
```java
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());
```
- 예외처리 클래스 안내페이지 가기 + 정상종료(response)
```java
		request.setAttribute("uri", request.getRequestURI());
		request.setAttribute("exception", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
```
- 스캔되는 항목에 글로벌 예외처리 클래스 추가
  - spring-servlet.xml 에서 해당 클래스가 스캐닝 되도록 설정
```xml
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.mysite.controller, com.cafe24.mysite.exception" />
```
- view에서 request로 넘겨 받은 메시지들 처리
```jsp
	<h1>Ooooops! - ${uri }</h1>
	<p>
		예외발생!!!<br>
		======================<br>
		<pre>
${exception }
		</pre>
	</p>
```

# Spring MyBatis 사용

### DataSource를 이용해 DB 연동
- pom.xml에서 필요한 라이브러리 다운
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
- Root Application Context 설정파일인 applicationContext.xml에 Datasource를 bean에 등록
  - 해당 예제는 mariadb 연결
```xml
	<!-- Connection Pool DataSource-->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
		<property name="url" value="jdbc:mariadb://192.168.1.51:3307/webdb" />
		<property name="username" value="webdb" />
		<property name="password" value="webdb" />
	</bean>
```

### Spring에서 Mybatis 연동 및 DAO에서 사용
- pom.xml에서 MyBatis 관련 라이브러리 다운
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
- Root Application Context 설정파일인 applicationContext.xml에 SqlSessionFactoryBean 설정
  - dataSource의 property에서 ref는 위에서 bean으로 설정한 dataSource의 id를 넣는다.
  - configLocation은 mybatis 관련 전체 설정 파일을 넣는다.
```xml
<!-- MyBatis SqlSessionFactoryBean --> 
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
	<property name="dataSource" ref="oracleDatasource" /> 
	<property name="configLocation" value="classpath:mybatis/configuration.xml" /> 
</bean>
```
- Root Application Context 설정파일인 applicationContext.xml에 SqlSessionTemplete 설정
  - 생성자의 arg에서 ref로 SqlSessionFactoryBean의 빈으로 설정한 id를 넣는다.
```xml
	<!-- MyBatis SqlSessionTemplate --> 
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg index="0" ref="sqlSessionFactory" />
	</bean>
```
- SqlSessionFactoryBean에서 설정한 MyBatis 전체 설정 파일인 configuration.xml 파일을 만든다.
  - typeAliases 태그에는 VO 클래스들에 대한 alias 등록
  - alias를 등록하지 않을 경우 VO를 사용 시 전체 패키지명을 다 적어주어야 한다.
  - mappers에서 mapper resource는 앞으로 SQL과 맵핑 시킬 xml 파일들을 만든다.
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
- mapper에 user.xml 파일 기본 템플릿
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="user">
</mapper>
```
- DAO에서 SqlSession을 사용한다.
```java
	@Autowired
	private SqlSession sqlSession;
```
- DAO에서 insert, delete, update의 경우에는 int 타입을 반환한다.
  - 위에서 주입한 SqlSession 객체를 사용한다.
  - 첫 번째 인자로 namespace이름.맵핑쿼리id 를 적어준다.
  - 두 번째 인자로 넘겨줄 파라미터가 있을경우 파라미터를 지정한다. (없으면 X)
```java
int count = sqlSession.insert("user.insert",vo);
```
- DAO에서 sqlSession으로 select를 사용하는 경우
  - 반환 타입이 List라면 selectList, 하나라면 selectOne을 사용한다.

### MyBatis mapper xml 파일 설정 예제
- 파라미터가 객체인 쿼리일 경우
  - parameterType에 alias된 객체를 적어준다.
  - ```#{ }```는 DB에 적용되는 컬럼명을 그대로 사용한다.
  - 즉, select에서 컬럼 명이 복잡할 경우 as를 사용해 컬럼 명을 단순하게 바꾼다.
  - 그 후 VO 클래스의 필드에서 컬렴명에 해당하는 필드명의 getter 메서드를 찾아서 값을 얻어온다. 
```xml
	<insert id="insert" parameterType="uservo">
	<![CDATA[
		insert into user
		values(null, #{name}, #{email}, #{password}, #{gender}, now())
	]]>
	</insert>
```
- 파라미터가 map 타입인 쿼리일 경우
  - parameterType에 map을 적어준다.
  - DAO에서 map을 셋팅 후 두 번째 인자로 넘겨준다.
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
- 파라미터가 long 타입이고 리턴 타입이 UserVo일 경우
```xml
	<select id="getByNo" parameterType="long" resultType="uservo">
	<![CDATA[
		select no, name, email, password, gender
		from user where no=#{no}
	]]>
	</select>
```
- 조건을 통한 동적 처리1
  - if test를 사용한다.
  - JSTL 문법과 비슷하게 choose, when 등의 문법이 있다.
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
- 조건을 통한 동적 처리2
  - 동적 처리1의 예제처럼 컬럼명을 사용할 수 있는 경우에는 해당 컬럼명을 사용하면 된다.
  - 하지만 객체가 아닌 String이나 int, long 등이 파라미터로 넘어와 이를 동적처리 할 경우에는 아무 이름을 쓰면 에러가 발생한다.
  - 이를 해결하기 위해 _parameter를 사용한다.
```xml
	<if test='_parameter > 0'>
	<![CDATA[
		where no < #{_parameter}
	]]>
	</if>
```
- 어떤 쿼리를 수행 후 그 결과로 이어서 다음 쿼리를 수행할 경우
  - selectKey를 사용한다.
  - 쿼리수행 후 넘어오는 결과 컬럼 혹은 넘길 결과 컬럼을 keyProperty에 받는다.
  - 반환 타입이 있다면 resultType을 적어준다.
  - order을 이용해 BEFORE, AFTER 등의 순서를 정해준다.
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