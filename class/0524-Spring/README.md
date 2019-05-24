# Spring Container

### BeanFactory (XMLBeanFactory)
- 기본적인 Bean의 생성과 소멸을 담당한다.
- XML 파일에 기술되어 있는 정의를 바탕으로  Bean을 생성한다.
- bean을 주입하기 위한 설정파일을 만들고, 해당 파일을 가져온다
  - ClassPathResource는 src/main/resources 의 경로를 가져온다.
```java
BeanFactory bf1 = 
		new XmlBeanFactory( new ClassPathResource( "config/user/applicationContext.xml" ) );
```
- Auto-Configuration(Scanning)인 경우 xml 설정파일
```xml
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.springContainer.user">
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Component" />
	</context:component-scan>
```
- Bean의 id가 자동으로 만들어 진다. (앞의 문자가 소문자로해서 클래스 이름으로)
```java
User1 user = (User1)bf1.getBean("user1");
System.out.println(user.getName());
```
- Auto-Configuration이 아닌 경우 xml 설정파일
  - 패키지의 클래스 파일의 경로를 직접 설정한다.
```xml
<bean class="com.cafe24.springContainer.user.User1" />
```
- bean을 직접 설정하는 경우에는 id가 자동으로 생성되지 않는다.
  - 그렇기 때문에 클래스.class로 클래스를 직접 가져온다.
  - 혹은 id나 name 설정을 해야 한다.
```java
user = (User1)bf1.getBean(User1.class);
System.out.println(user.getName());
```

### ApplicationContext (주로 사용)
- BeanFactory는 기초적인 컨테이너 기능을 제공하지만  Spring Framework의 완벽한 기능을 사용하기 위해서는 ApplicationContext를 사용해야 한다.     
- BeanFactory와 유사하지만 Bean을 로딩하는 방법에 차이가 있다.
- 리스너로 등록된 빈에 이벤트를 발생 시킬 수 있다. 
- 추가적인 기능과 성능 차이로 ApplicationContext를 Spring Container를 사용한다.
- 종류
  - ClassPathXmlApplicationContext
  - XmlWebApplicationContext
  - FileSystemXmlApplicationContext
- 사용하는 방법은 BeanFactory 와 동일 하지만 설정파일을 가져올 때 BeanFactory(XmlBeanFactory) 가 아닌 ApplicationContext(ClassPathXmlApplicationContext) 를 사용한다.
  - ClassPathResource 를 이용해 resources 경로를 얻어올 필요는 없다.
  - Auto-Configuration(Scanning)으로 xml 파일 설정
```java
ApplicationContext appContext = new ClassPathXmlApplicationContext("config/user/applicationContext.xml");
User1 user = (User1)appContext.getBean("user1");
System.out.println(user.getName());
		
user = appContext.getBean(User1.class);
System.out.println(user.getName());
		
((ConfigurableApplicationContext)appContext).close();
```
- Auto-Configuration(Scanning)으로 설정하지 않을 경우 xml 설정 파일의 bean에 직접 id 혹은 name을 설정한다.
```xml
<bean id="usr" name="user" class="com.cafe24.springContainer.user.User" />
```
- id와 name으로 가져오는 방법은 동일하게 getBean으로 가져올 수 있다.
  - 주로 id를 사용하는 것을 권장
```java
// name으로 가져오기
User user = (User)appContext.getBean("user");
System.out.println(user);
		
// id로 가져오기
user = (User)appContext.getBean("usr");
System.out.println(user);
```
- 컨테이너의 bean은 기본적으로 클래스를 생성할 때 매개변수가 없는 기본 생성자로 생성한다.
- 매개변수가 있는 생성자로 생성하기를 원하면 bean에서 매개변수를 줄 수 있다.
  - 생성자의 매개변수의 순서대로 적어주어야 된다는 문제가 있다. 이후에 해결
```xml
	<bean id="usr3" class="com.cafe24.springContainer.user.User">
		<constructor-arg value="1" />
		<constructor-arg value="둘리" />
	</bean>
```
- property는 setter를 호출한다.
  - name은 클래스에서 setter를 수행할 필드의 이름, value는 setter에 넣을 값, ref는 참조할 bean의 id
  - friend 객체를 생성하고 name의 setter로 또치를 셋팅한다.
  - friend 객체에 대해 friend 라는 id를 가진 bean을 참조하도록 한다.
```xml
	<bean id="friend" class="com.cafe24.springContainer.user.Friend">
		<property name="name" value="또치"/>
	</bean>
	
	<bean id="usr5" class="com.cafe24.springContainer.user.User">
		<property name="name" value="도우넛"/>
		<property name="no" value="2"/>
		<property name="friend" ref="friend"/>
	</bean>
```
- 만약 필드에 List 타입이 있다면 xml 설정 파일에서 list 태그를 사용한다.
  - ex) User 클래스에 friends라는 필드의 타입이 List<String>인 경우
  - List 태그의 경우 단순하게 value 태그를 이용해 값을 넣는다.
```xml
	<bean id="usr7" class="com.cafe24.springContainer.user.User">
		<constructor-arg value="둘리" />
		<property name="friends">
			<list>
				<value>마이콜</value>
				<value>도우넛</value>
				<value>또치</value>
			</list>
		</property>
	</bean>
```

# CI (Continuous Integration)
- 개발자가 각각 개발한 소스코드를 모아서 한꺼번에 빌드하는 통합 빌드의 과정을 특정 시점이 아니라 매일이나 매주와 같이 일정 주기마다 수행함으로써,소스코드 통합에서 발생하는 오류와 시간을 줄이기 위한 기법
- 테스트 등과 연계하여 코드 커버리지 분석, 코드 인스펙션 등을 수행하고 Reporting 함으로써 개발일정에 대한 관리나 통제가 가능하고, 소스코드의 일관성 및 테스트나 운영상에서 발생할 수 있는 문제점을 사전에 발견하고 예방
- 자동화되고, 점진적인 빌드를 통해 개발 생산성을 향상시킬 수 있다.

### Jenkins 기반의 CI 환경
![123](https://user-images.githubusercontent.com/20277647/58298944-54e32d00-7e18-11e9-9c1b-cdbf93a3c503.PNG)

### Jenkins 설치 및 셋팅
- 먼저 tomcat, git, maven이 설치되어 있어야 한다.
- https://jenkins.io/download/ 에서 **war 파일**의 링크 주소를 얻은 후 wget으로 다운로드
- mv jenkins.war /usr/local/cafe24/tomcat/webapps/
- tomcat에서 manager 계정으로 접속 후 localhost:8080/jenkins가 제대로 들어가지는지 확인
- cd /root/.jenkins/secrets
  - 재설치를 원하면 .jenkins 폴더를 다 날리면 된다.
- cat initialAdminPassword
  - 나온 암호를 localhost:8080/jenkins 에서 인증하라는 부분에 넣은 후 설치 진행
- Create First Admin User 화면이 나오면 정상 설치 완료
  - 적절하게 계정 생성 후 로그인
- Jenkins 관리 ---> Global Tool Configuration ---> Add JDK로 JDK 경로 지정
- Git에서 경로를 잡아준다. (git 에 'x'권한 있는 실행 경로)
- Add Maven으로 메이븐 경로를 잡아준다.
- 프로젝트 생성 ---> 소스코드 관리 ---> Git ---> Repositories에서 .git 경로 지정
- Build ---> Invoke top-level Maven targets - Goals에 해당 명령어 입력
  - clean package tomcat:redeploy -Pproduction -Dmaven.test.skip=true
- 프로젝트로 돌아가서 Build Now
- 빨간불로 되어 있으면 Console Output을 눌러 에러들을 잡아준다.
- 에러 해결 후 tomcat restart ---> 재로그인 ---> 다시 빌드

### 에러 해결
- mvn 명령을 못잡음
```
#maven
export M2_HOME=/usr/local/cafe24/maven
export PATH=$PATH:$M2_HOME/bin
export PATH=$PATH:$JAVA_HOME/bin:/usr/local/cafe24/maven/bin
```
- 인코딩 관련 문제 pom.xml에 추가
```xml
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
	</properties>
```
- eclipse의 m2에 있는 클래스들을 잡지 못함
- pom.xml에 내용 추가
  - eclipse에서 pom.xml에 dependencies와 build 사이에 profiles 추가
```xml
   <profiles>
      <profile>
         <id>production</id>
         <build>
            <resources>
               <resource>
                  <directory>${project.basedir}/src/main/resources</directory>
                  <excludes>
                     <exclude>**/*.java</exclude>
                  </excludes>
               </resource>
            </resources>
            <plugins>
               <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-resources-plugin</artifactId>
                  <configuration>
                     <encoding>UTF-8</encoding>
                  </configuration>
               </plugin>
            </plugins>
         </build>
         <dependencies>
            <!-- Servlet -->
            <dependency>
               <groupId>javax.servlet</groupId>
               <artifactId>javax.servlet-api</artifactId>
               <version>3.0.1</version>
               <scope>provided</scope>
            </dependency>
            <dependency>
               <groupId>javax.servlet.jsp</groupId>
               <artifactId>jsp-api</artifactId>
               <version>2.0</version>
               <scope>provided</scope>
            </dependency>
         </dependencies>
      </profile>
   </profiles>
```
- pom.xml의 plugins 태그 안에 plugin 추가
  - url : 서버 url
  - path : 잘 설정
  - username과 password : tomcat의 manager을 들어가기 위한 계정
```
         <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>tomcat-maven-plugin</artifactId>
            <configuration>
               <url>http://127.0.0.1:8080/manager/text</url>
               <path>/mysite2</path>
               <username>admin</username>
               <password>manager</password>
            </configuration>
         </plugin>
```
- 에러 해결 후
  - git을 이용해 repository에 프로젝트를 올리고
  - Jenkins에서 Build Now 누른 후 빌드 완료되면
  - 리눅스IP:8080/mysite2 에 접속해보기

### 성공 pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>com.cafe24</groupId>
   <artifactId>mysite2</artifactId>
   <version>0.0.1-SNAPSHOT</version>
   <packaging>war</packaging>


   <properties>
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
      <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
      <org.springframework-version>4.3.1.RELEASE</org.springframework-version>
      <jcloverslf4j.version>1.7.6</jcloverslf4j.version>
      <logback.version>1.1.1</logback.version>
   </properties>

   <dependencies>
      <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-context</artifactId>
         <version>${org.springframework-version}</version>
         <exclusions>
            <exclusion>
               <groupId>commons-logging</groupId>
               <artifactId>commons-logging</artifactId>
            </exclusion>
         </exclusions>
      </dependency>

      <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-web</artifactId>
         <version>${org.springframework-version}</version>
      </dependency>
      <!-- Spring Web -->

      <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-webmvc</artifactId>
         <version>${org.springframework-version}</version>
      </dependency>


      <!-- mariadb java client -->
      <dependency>
         <groupId>org.mariadb.jdbc</groupId>
         <artifactId>mariadb-java-client</artifactId>
         <version>2.4.0</version>
      </dependency>


      <!-- spring jdbc -->
      <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-jdbc</artifactId>
         <version>${org.springframework-version}</version>
      </dependency>

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

      <!-- JSTL -->
      <dependency>
         <groupId>javax.servlet</groupId>
         <artifactId>jstl</artifactId>
         <version>1.2</version>
      </dependency>

      <!-- Common DBCP -->
      <dependency>
         <groupId>commons-dbcp</groupId>
         <artifactId>commons-dbcp</artifactId>
         <version>1.4</version>
      </dependency>

      <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-databind</artifactId>
         <version>2.4.4</version>
         <exclusions>
            <exclusion>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-core</artifactId>
            </exclusion>
            <exclusion>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-annotations</artifactId>
            </exclusion>
         </exclusions>
      </dependency>
      <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-core</artifactId>
         <version>2.4.4</version>
      </dependency>

      <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-annotations</artifactId>
         <version>2.7.4</version>
      </dependency>



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


      <!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
      <dependency>
         <groupId>commons-io</groupId>
         <artifactId>commons-io</artifactId>
         <version>1.4</version>
      </dependency>

      <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
      <dependency>
         <groupId>commons-fileupload</groupId>
         <artifactId>commons-fileupload</artifactId>
         <version>1.4</version>
      </dependency>

      <dependency>
         <!-- spring aspect -->
         <groupId>org.springframework</groupId>
         <artifactId>spring-aspects</artifactId>
         <version>${org.springframework-version}</version>
      </dependency>


      <dependency>
         <!-- validation -->
         <groupId>javax.validation</groupId>
         <artifactId>validation-api</artifactId>
         <version>1.0.0.GA</version>
      </dependency>

      <dependency>
         <!-- hibernate validation -->
         <groupId>org.hibernate</groupId>
         <artifactId>hibernate-validator</artifactId>
         <version>4.2.0.Final</version>
      </dependency>

   </dependencies>
   <profiles>
      <profile>
         <id>production</id>
         <build>
            <resources>
               <resource>
                  <directory>${project.basedir}/src/main/resources</directory>
                  <excludes>
                     <exclude>**/*.java</exclude>
                  </excludes>
               </resource>
               <resource>
                  <directory>${project.basedir}/src/main/production/resources</directory>
                  <excludes>
                     <exclude>**/*.java</exclude>
                  </excludes>
               </resource>
            </resources>
            <plugins>
               <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-resources-plugin</artifactId>
                  <configuration>
                     <encoding>UTF-8</encoding>
                  </configuration>
               </plugin>
            </plugins>
         </build>
         <dependencies>
            <!-- Servlet -->
            <dependency>
               <groupId>javax.servlet</groupId>
               <artifactId>javax.servlet-api</artifactId>
               <version>3.0.1</version>
               <scope>provided</scope>
            </dependency>
            <dependency>
               <groupId>javax.servlet.jsp</groupId>
               <artifactId>jsp-api</artifactId>
               <version>2.0</version>
               <scope>provided</scope>
            </dependency>
         </dependencies>
      </profile>
   </profiles>



   <build>
      <sourceDirectory>src/main/java</sourceDirectory>
      <plugins>
         <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.0</version>
            <configuration>
               <source>1.8</source>
               <target>1.8</target>
            </configuration>
         </plugin>
         <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <version>3.2.1</version>
            <configuration>
               <warSourceDirectory>src/main/webapp</warSourceDirectory>
            </configuration>
         </plugin>

         <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>tomcat-maven-plugin</artifactId>
            <configuration>
               <url>http://127.0.0.1:8080/manager/text</url>
               <path>/mysite2</path>
               <username>admin</username>
               <password>manager</password>
            </configuration>
         </plugin>


      </plugins>
   </build>
</project>
```