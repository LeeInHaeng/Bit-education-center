# jdk download
- tar.gz 링크 경로 복사
- wget 복사한 경로
```
tar xvfz ~.tar.gz

mv jdk1.8~ jdk1.8

ln -s jdk1.8 jdk
```

# java 환경변수 설정
```
vi /etc/profile

### java

export JAVA_HOME=/usr/local/cafe24/jdk
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/tools.jar
```
- source /etc/profile

# 테스트
```
javac -version
java -version
```

# tomcat download
- tar.gz 링크 경로 복사
- wget 복사한 경로
```
tar xvfz ~.tar.gz

mv apache-tomcat~ /usr/local/cafe24/tomcat8

ln -s tomcat8/ tomcat

cd tomcat8/conf

vi server.xml

8080 검색

<Connector port="8080" URIEncoding="UTF-8" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />

/usr/local/cafe24/tomcat8/bin/catalina.sh start
```
- 8080 포트 열기
```
vi /etc/sysconfig/iptables

-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT

/etc/init.d/iptables restart
```
- 아이피:8080 접속해서 톰캣 페이지 확인
- 아이피:8080/manager 접속 허용
```
vi tomcat8/conf/tomcat-users.xml

  <role rolename="manager"/>
  <role rolename="manager-gui" />
  <role rolename="manager-script" />
  <role rolename="manager-jmx" />
  <role rolename="manager-status" />
  <role rolename="admin"/>
  <user username="admin" password="manager" roles="admin,manager,manager-gui, manager-script, manager-jmx, manager-status"/>

vi tomcat8/webapps/manager/META-INF/context.xml

# 기존에 있던 부분 주석 처리 해야됨

<Context privileged= "true" antiResourceLocking= "false" docBase= "${catalina.home}/webapps/manager">
	 <Valve className= "org.apache.catalina.valves.RemoteAddrValve" allow= "^.*$" /> 
</Context>

/usr/local/cafe24/tomcat8/bin/catalina.sh start
```

# eclipse encoding 변경
- Window ---> Preferences
- encoding 검색
- UTF-8로 다 변경
- 윈도우에서 tomcat 설치
- Window ---> Preferences ---> Server 검색 ---> Runtime Environment
  - 톰캣 버전 및 경로 설정
  
# 톰캣에 WAR 파일 업로드
- 프로젝트 우클릭 ---> export ---> WAR file
  - WebContent 부분이 war 파일로 생성됨
- 아이피:8080/manager로 접속
- 중간쯤에 WAR file 업로드 후 Deploy
- 아이피:8080/WAR파일명 접속 확인