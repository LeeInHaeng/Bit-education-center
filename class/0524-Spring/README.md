# Spring Container

### BeanFactory (XMLBeanFactory)
- �⺻���� Bean�� ������ �Ҹ��� ����Ѵ�.
- XML ���Ͽ� ����Ǿ� �ִ� ���Ǹ� ��������  Bean�� �����Ѵ�.
- bean�� �����ϱ� ���� ���������� �����, �ش� ������ �����´�
  - ClassPathResource�� src/main/resources �� ��θ� �����´�.
```java
BeanFactory bf1 = 
		new XmlBeanFactory( new ClassPathResource( "config/user/applicationContext.xml" ) );
```
- Auto-Configuration(Scanning)�� ��� xml ��������
```xml
	<context:annotation-config />
	<context:component-scan
		base-package="com.cafe24.springContainer.user">
		<context:include-filter type="annotation"
			expression="org.springframework.stereotype.Component" />
	</context:component-scan>
```
- Bean�� id�� �ڵ����� ����� ����. (���� ���ڰ� �ҹ��ڷ��ؼ� Ŭ���� �̸�����)
```java
User1 user = (User1)bf1.getBean("user1");
System.out.println(user.getName());
```
- Auto-Configuration�� �ƴ� ��� xml ��������
  - ��Ű���� Ŭ���� ������ ��θ� ���� �����Ѵ�.
```xml
<bean class="com.cafe24.springContainer.user.User1" />
```
- bean�� ���� �����ϴ� ��쿡�� id�� �ڵ����� �������� �ʴ´�.
  - �׷��� ������ Ŭ����.class�� Ŭ������ ���� �����´�.
  - Ȥ�� id�� name ������ �ؾ� �Ѵ�.
```java
user = (User1)bf1.getBean(User1.class);
System.out.println(user.getName());
```

### ApplicationContext (�ַ� ���)
- BeanFactory�� �������� �����̳� ����� ����������  Spring Framework�� �Ϻ��� ����� ����ϱ� ���ؼ��� ApplicationContext�� ����ؾ� �Ѵ�.     
- BeanFactory�� ���������� Bean�� �ε��ϴ� ����� ���̰� �ִ�.
- �����ʷ� ��ϵ� �� �̺�Ʈ�� �߻� ��ų �� �ִ�. 
- �߰����� ��ɰ� ���� ���̷� ApplicationContext�� Spring Container�� ����Ѵ�.
- ����
  - ClassPathXmlApplicationContext
  - XmlWebApplicationContext
  - FileSystemXmlApplicationContext
- ����ϴ� ����� BeanFactory �� ���� ������ ���������� ������ �� BeanFactory(XmlBeanFactory) �� �ƴ� ApplicationContext(ClassPathXmlApplicationContext) �� ����Ѵ�.
  - ClassPathResource �� �̿��� resources ��θ� ���� �ʿ�� ����.
  - Auto-Configuration(Scanning)���� xml ���� ����
```java
ApplicationContext appContext = new ClassPathXmlApplicationContext("config/user/applicationContext.xml");
User1 user = (User1)appContext.getBean("user1");
System.out.println(user.getName());
		
user = appContext.getBean(User1.class);
System.out.println(user.getName());
		
((ConfigurableApplicationContext)appContext).close();
```
- Auto-Configuration(Scanning)���� �������� ���� ��� xml ���� ������ bean�� ���� id Ȥ�� name�� �����Ѵ�.
```xml
<bean id="usr" name="user" class="com.cafe24.springContainer.user.User" />
```
- id�� name���� �������� ����� �����ϰ� getBean���� ������ �� �ִ�.
  - �ַ� id�� ����ϴ� ���� ����
```java
// name���� ��������
User user = (User)appContext.getBean("user");
System.out.println(user);
		
// id�� ��������
user = (User)appContext.getBean("usr");
System.out.println(user);
```
- �����̳��� bean�� �⺻������ Ŭ������ ������ �� �Ű������� ���� �⺻ �����ڷ� �����Ѵ�.
- �Ű������� �ִ� �����ڷ� �����ϱ⸦ ���ϸ� bean���� �Ű������� �� �� �ִ�.
  - �������� �Ű������� ������� �����־�� �ȴٴ� ������ �ִ�. ���Ŀ� �ذ�
```xml
	<bean id="usr3" class="com.cafe24.springContainer.user.User">
		<constructor-arg value="1" />
		<constructor-arg value="�Ѹ�" />
	</bean>
```
- property�� setter�� ȣ���Ѵ�.
  - name�� Ŭ�������� setter�� ������ �ʵ��� �̸�, value�� setter�� ���� ��, ref�� ������ bean�� id
  - friend ��ü�� �����ϰ� name�� setter�� ��ġ�� �����Ѵ�.
  - friend ��ü�� ���� friend ��� id�� ���� bean�� �����ϵ��� �Ѵ�.
```xml
	<bean id="friend" class="com.cafe24.springContainer.user.Friend">
		<property name="name" value="��ġ"/>
	</bean>
	
	<bean id="usr5" class="com.cafe24.springContainer.user.User">
		<property name="name" value="�����"/>
		<property name="no" value="2"/>
		<property name="friend" ref="friend"/>
	</bean>
```
- ���� �ʵ忡 List Ÿ���� �ִٸ� xml ���� ���Ͽ��� list �±׸� ����Ѵ�.
  - ex) User Ŭ������ friends��� �ʵ��� Ÿ���� List<String>�� ���
  - List �±��� ��� �ܼ��ϰ� value �±׸� �̿��� ���� �ִ´�.
```xml
	<bean id="usr7" class="com.cafe24.springContainer.user.User">
		<constructor-arg value="�Ѹ�" />
		<property name="friends">
			<list>
				<value>������</value>
				<value>�����</value>
				<value>��ġ</value>
			</list>
		</property>
	</bean>
```

# CI (Continuous Integration)
- �����ڰ� ���� ������ �ҽ��ڵ带 ��Ƽ� �Ѳ����� �����ϴ� ���� ������ ������ Ư�� ������ �ƴ϶� �����̳� ���ֿ� ���� ���� �ֱ⸶�� ���������ν�,�ҽ��ڵ� ���տ��� �߻��ϴ� ������ �ð��� ���̱� ���� ���
- �׽�Ʈ ��� �����Ͽ� �ڵ� Ŀ������ �м�, �ڵ� �ν���� ���� �����ϰ� Reporting �����ν� ���������� ���� ������ ������ �����ϰ�, �ҽ��ڵ��� �ϰ��� �� �׽�Ʈ�� ��󿡼� �߻��� �� �ִ� �������� ������ �߰��ϰ� ����
- �ڵ�ȭ�ǰ�, �������� ���带 ���� ���� ���꼺�� ����ų �� �ִ�.

### Jenkins ����� CI ȯ��
![123](https://user-images.githubusercontent.com/20277647/58298944-54e32d00-7e18-11e9-9c1b-cdbf93a3c503.PNG)

### Jenkins ��ġ �� ����
- ���� tomcat, git, maven�� ��ġ�Ǿ� �־�� �Ѵ�.
- https://jenkins.io/download/ ���� **war ����**�� ��ũ �ּҸ� ���� �� wget���� �ٿ�ε�
- mv jenkins.war /usr/local/cafe24/tomcat/webapps/
- tomcat���� manager �������� ���� �� localhost:8080/jenkins�� ����� �������� Ȯ��
- cd /root/.jenkins/secrets
  - �缳ġ�� ���ϸ� .jenkins ������ �� ������ �ȴ�.
- cat initialAdminPassword
  - ���� ��ȣ�� localhost:8080/jenkins ���� �����϶�� �κп� ���� �� ��ġ ����
- Create First Admin User ȭ���� ������ ���� ��ġ �Ϸ�
  - �����ϰ� ���� ���� �� �α���
- Jenkins ���� ---> Global Tool Configuration ---> Add JDK�� JDK ��� ����
- Git���� ��θ� ����ش�. (git �� 'x'���� �ִ� ���� ���)
- Add Maven���� ���̺� ��θ� ����ش�.
- ������Ʈ ���� ---> �ҽ��ڵ� ���� ---> Git ---> Repositories���� .git ��� ����
- Build ---> Invoke top-level Maven targets - Goals�� �ش� ��ɾ� �Է�
  - clean package tomcat:redeploy -Pproduction -Dmaven.test.skip=true
- ������Ʈ�� ���ư��� Build Now
- �����ҷ� �Ǿ� ������ Console Output�� ���� �������� ����ش�.
- ���� �ذ� �� tomcat restart ---> ��α��� ---> �ٽ� ����

### ���� �ذ�
- mvn ����� ������
```
#maven
export M2_HOME=/usr/local/cafe24/maven
export PATH=$PATH:$M2_HOME/bin
export PATH=$PATH:$JAVA_HOME/bin:/usr/local/cafe24/maven/bin
```
- ���ڵ� ���� ���� pom.xml�� �߰�
```xml
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
	</properties>
```
- eclipse�� m2�� �ִ� Ŭ�������� ���� ����
- pom.xml�� ���� �߰�
  - eclipse���� pom.xml�� dependencies�� build ���̿� profiles �߰�
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
- pom.xml�� plugins �±� �ȿ� plugin �߰�
  - url : ���� url
  - path : �� ����
  - username�� password : tomcat�� manager�� ���� ���� ����
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
- ���� �ذ� ��
  - git�� �̿��� repository�� ������Ʈ�� �ø���
  - Jenkins���� Build Now ���� �� ���� �Ϸ�Ǹ�
  - ������IP:8080/mysite2 �� �����غ���

### ���� pom.xml
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