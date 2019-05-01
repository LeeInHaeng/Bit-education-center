# Maven

### 이클립스에서 Maven 빌드 1
- 이클립스에서 기존 Java 프로젝트를 Maven으로 변경
  - 프로젝트 우클릭 ---> Configure ---> Convert to Maven Project
- 이클립스에서 빌드
  - 프로젝트 우클릭 ---> Properties ---> Java Build Path ---> Libraries
  - Add Library ---> JRE System Library ---> jdk 경로 검색 후 Finish (jre 말고 jdk!!)
  - pom.xml에 아래 내용들 추가 
  - 프로젝트 우클릭 ---> Maven ---> Project Update
  - 프로젝트 우클릭 ---> Run As ---> Maven Build
  - Goals : clean package ---> Apply ---> Run (설정 바꿀려면 Run As에서 Run Configurations)
```
// pom.xml

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
		</plugins>
```
- 빌드 성공시 프로젝트 ---> target ---> jar 파일 생성
  - src 부분이 압축 형식으로 묶인다.
- Maven 프로젝트가 제대로 생성되지 않을 경우
  - C:\Users\사용자이름\.m2 에서 repository 삭제 후 이클립스 재시작

### 이클립스에서 Maven 빌드 2
- 이클립스에서 Maven 빌드시 src 부분이 jar 파일로 묶인다
  - 즉, 웹 구동시 필요한 webapp 부분은 묶이지 않는다.
- 프로젝트에서 New ---> Folder ---> resources 폴더 생성
- resources 폴더에서 webapp 패키지를 만든 후 webapp 안에 있는 내용을 모두 이동
- pom.xml 내용 수정
  - 프로젝트 우클릭 ---> Maven ---> Project Update
  - resources의 resource 태그의 directory 태그에 주목
  - resources에 의해 빌드시 src 하위 뿐만 아니라 resources 하위의 webapp도 함께 묶인다.
  - 결과적으로 jar 파일에 src 아래 내용들과 META-INF, resources 폴더 아래의 내용들이 묶이게 된다.
```
// pom.xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.cafe24</groupId>
	<artifactId>Network</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
	</dependencies>

	<build>
		<sourceDirectory>src</sourceDirectory>
		<resources>
			<resource>
				<directory>${project.basedir}/resources</directory>
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
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<version>2.3.1</version>
				<configuration>
					<finalName>networkprogramming</finalName>
					<outputDirectory>working</outputDirectory>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
```
- 빌드된 resources에 접근하는 방법
  - 필드 부분에 static 블록을 선언한다.
  - 클래스이름.class.getClass()를 이용해 클래스 정보를 얻는다.
  - System.out에 E:\cafe24\dowork\eclipseWorkspace\Network\target\classes/webapp 나오면 성공
```java
	private static String documentRoot = "";
	
	static {
		documentRoot = RequestHandler.class
				.getClass().getResource("/webapp").getPath();
	}
	
	private Socket socket;

	// 생략 //
```

### 리눅스에서 Maven 설치 및 연동
- wget http://apache.mirror.cdnetworks.com/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
- tar xvfz apache-maven-3.3.9-bin.tar.gz
- mv apache-maven-3.3.9 /usr/local/cafe24/
- ln -s apache-maven-3.3.9 maven
- /etc/profile 설정
```
#maven
export M2_HOME=/usr/local/cafe24/maven
export PATH=$PATH:$M2_HOME/bin
```
- 리눅스에 프로젝트 옮긴 후 프로젝트로 이동
- mvn clean package ---> jar 파일 생성
- java -cp ~~.jar 패키지.클래스




1. 소켓 만들고
2. iostream
3. finish에서 socket 정리
update
sendMessage
show 안에서 thread 생성
6번 항목은 quit 명령어가 아닌 x 아이콘 하면 나가기 처리
