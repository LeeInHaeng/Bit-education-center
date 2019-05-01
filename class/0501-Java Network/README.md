# Maven

### ��Ŭ�������� Maven ���� 1
- ��Ŭ�������� ���� Java ������Ʈ�� Maven���� ����
  - ������Ʈ ��Ŭ�� ---> Configure ---> Convert to Maven Project
- ��Ŭ�������� ����
  - ������Ʈ ��Ŭ�� ---> Properties ---> Java Build Path ---> Libraries
  - Add Library ---> JRE System Library ---> jdk ��� �˻� �� Finish (jre ���� jdk!!)
  - pom.xml�� �Ʒ� ����� �߰� 
  - ������Ʈ ��Ŭ�� ---> Maven ---> Project Update
  - ������Ʈ ��Ŭ�� ---> Run As ---> Maven Build
  - Goals : clean package ---> Apply ---> Run (���� �ٲܷ��� Run As���� Run Configurations)
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
- ���� ������ ������Ʈ ---> target ---> jar ���� ����
  - src �κ��� ���� �������� ���δ�.
- Maven ������Ʈ�� ����� �������� ���� ���
  - C:\Users\������̸�\.m2 ���� repository ���� �� ��Ŭ���� �����

### ��Ŭ�������� Maven ���� 2
- ��Ŭ�������� Maven ����� src �κ��� jar ���Ϸ� ���δ�
  - ��, �� ������ �ʿ��� webapp �κ��� ������ �ʴ´�.
- ������Ʈ���� New ---> Folder ---> resources ���� ����
- resources �������� webapp ��Ű���� ���� �� webapp �ȿ� �ִ� ������ ��� �̵�
- pom.xml ���� ����
  - ������Ʈ ��Ŭ�� ---> Maven ---> Project Update
  - resources�� resource �±��� directory �±׿� �ָ�
  - resources�� ���� ����� src ���� �Ӹ� �ƴ϶� resources ������ webapp�� �Բ� ���δ�.
  - ��������� jar ���Ͽ� src �Ʒ� ������ META-INF, resources ���� �Ʒ��� ������� ���̰� �ȴ�.
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
- ����� resources�� �����ϴ� ���
  - �ʵ� �κп� static ����� �����Ѵ�.
  - Ŭ�����̸�.class.getClass()�� �̿��� Ŭ���� ������ ��´�.
  - System.out�� E:\cafe24\dowork\eclipseWorkspace\Network\target\classes/webapp ������ ����
```java
	private static String documentRoot = "";
	
	static {
		documentRoot = RequestHandler.class
				.getClass().getResource("/webapp").getPath();
	}
	
	private Socket socket;

	// ���� //
```

### ���������� Maven ��ġ �� ����
- wget http://apache.mirror.cdnetworks.com/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
- tar xvfz apache-maven-3.3.9-bin.tar.gz
- mv apache-maven-3.3.9 /usr/local/cafe24/
- ln -s apache-maven-3.3.9 maven
- /etc/profile ����
```
#maven
export M2_HOME=/usr/local/cafe24/maven
export PATH=$PATH:$M2_HOME/bin
```
- �������� ������Ʈ �ű� �� ������Ʈ�� �̵�
- mvn clean package ---> jar ���� ����
- java -cp ~~.jar ��Ű��.Ŭ����




1. ���� �����
2. iostream
3. finish���� socket ����
update
sendMessage
show �ȿ��� thread ����
6�� �׸��� quit ��ɾ �ƴ� x ������ �ϸ� ������ ó��
