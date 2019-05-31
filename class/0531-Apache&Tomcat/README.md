# jenkins 톰캣, 프로젝트용 톰캣 분리

### tomcat-jenkins 설정
- cd /usr/local/cafe24, mkdir tomcat-jenkins, cd tomcat-jenkins
- cp -R /usr/local/cafe24/tomcat8/* .
- rm -rf bin lib LICENSE NOTICE RELEASE-NOTES RUNNING.txt
  - bin과 lib은 jenkins용으로 만들기 때문에
- webapps 에서 자신이 올렸던 프로젝트 다 삭제 (jenkins 빼고)
- 설정 : vi conf/server.xml
  - 기존 톰캣과의 포트 충돌 방지 (젠킨스용 포트 설정)
  - 8로 시작하는 포트를 모두 9로 변경
``` xml
<Server port="8005" shutdown="SHUTDOWN">
  <!-- ex) 8005 -> 9005 변경
  다음과 같이 다음부분 변경 --> 		
  <Service name="Catalina">
   <Connector port="9090" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="9443" URIEncoding="UTF-8"/>
        <Engine name="Catalina" defaultHost="localhost">
        <Host name="hmo.sunnyvale.co.kr"  appBase="webapps" unpackWARs="true" autoDeploy="true">
                </Host>
          </Engine>
     </Service>
```
- mkdir bin
- startup.sh 만들기 : vi bin/startup.sh
  - 첫 번째 줄의 export에서 해당 디렉터리 경로로 변경
  - echo $CATALINA_HOME 명령어를 통해 톰캣의 환경 변수가 설정되어 있는 것을 확인
```sh
#!/usr/bin/env bash
export CATALINA_BASE=/usr/local/cafe24/tomcat-jenkins
export JAVA_OPTS="-Djava.awt.headless=true -server -Xms512m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:+DisableExplicitGC"
export CATALINA_OPTS="-Denv=product -Denv.servername=jenkins"
$CATALINA_HOME/bin/catalina.sh start
```
- chmod 750 bin/startup.sh
- shutdown.sh 만들기 : vi bin/shutdown.sh
```sh
#!/usr/bin/env bash

export CATALINA_BASE=/usr/local/cafe24/tomcat-jenkins
export JAVA_OPTS="-Djava.awt.headless=true -server -Xms512m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:+DisableExplicitGC"
export CATALINA_OPTS="-Denv=product -Denv.servername=jenkins"
$CATALINA_HOME/bin/catalina.sh stop
```
- chmod 750 bin/shutdown.sh
- 9090 포트 열기 : vi /etc/sysconfig/iptables
```
-A INPUT -m state --state NEW -m tcp -p tcp --dport 9090 -j ACCEPT
```
- 톰캣 죽이기 : service tomcat stop
- jenkins 올리기 : bin/startup.sh

### tomcat-cafe24 설정
- 위와 동일하게 설정
- startup.sh 와 shutdown.sh 에서 servername을 cafe24로 변경

### tomcat 서비스 등록
- cd /etc/init.d
- vi tomcat-cafe24
```
#!/bin/sh 
# 
# Startup script for Tomcat for Cafe24
# 
# chkconfig: 35 85 35 
# description: Start Tomcat 
# 
# processname: tomcat 
# 
# Source function library. 

. /etc/rc.d/init.d/functions 

export JAVA_HOME=/usr/local/cafe24/jdk
export CLASSPATH=.:$JAVA_HOME/lib/tools.jar
export CATALINA_HOME=/usr/local/cafe24/tomcat
export PATH=$PATH:$JAVA_HOME/bin

case "$1" in 

	start) 

		echo -n "Starting tomcat-cafe24: " 
		daemon /usr/local/cafe24/tomcat-cafe24/bin/startup.sh
		touch /var/lock/subsys/tomcat-cafe24 
		echo
		;; 
	stop) 
		echo -n "Shutting down tomcat-hmo: " 
		daemon /usr/local/cafe24/tomcat-cafe24/bin/shutdown.sh 
		rm -f /var/lock/subsys/tomcat-cafe24
		echo 
		;; 
	restart) 
		$0 stop
		sleep 2 
		$0 start 
		;; 
	*) 
		echo "Usage: $0 {start|stop|restart}" 
		exit 1 
esac 
exit 0
```
- chmod 755 tomcat-cafe24
- 기존에 init.d에 있던 tomcat을 삭제하고 tomcat-cafe24로 서비스 구동
- 동일한 방식으로 init.d 에 tomcat-jenkins도 등록
```
#!/bin/sh 
# 
# Startup script for Tomcat for Cafe24
# 
# chkconfig: 35 87 37 
# description: Start Tomcat 
# 
# processname: tomcat 
# 
# Source function library. 

. /etc/rc.d/init.d/functions 

export JAVA_HOME=/usr/local/cafe24/jdk
export CLASSPATH=.:$JAVA_HOME/lib/tools.jar
export CATALINA_HOME=/usr/local/cafe24/tomcat
export PATH=$PATH:$JAVA_HOME/bin:/usr/local/cafe24/maven/bin

case "$1" in 

	start) 

		echo -n "Starting tomcat-jenkins: " 
		daemon /usr/local/cafe24/tomcat-jenkins/bin/startup.sh 
		touch /var/lock/subsys/tomcat-jenkins 
		echo
		;; 
	stop) 
		echo -n "Shutting down tomcat-jenkins: " 
		daemon /usr/local/cafe24/tomcat-jenkins/bin/shutdown.sh 
		rm -f /var/lock/subsys/tomcat-jenkins 
		echo 
		;; 
	restart) 
		$0 stop
		sleep 2 
		$0 start 
		;; 
	*) 
		echo "Usage: $0 {start|stop|restart}" 
		exit 1 
esac 
exit 0
```

# 아파치 톰캣 연동

- iptables에서 80포트 열기
  - vi /etc/sysconfig/iptables

- 필요 모듈 설치
```
yum -y install net-tools
yum -y install bind-utils
yum -y install ntsysv
yum -y install wget
yum -y install unzip
yum -y install make cmake 
yum -y install gcc g++ cpp gcc-c++ 
yum -y install perl 
yum -y install ncurses-devel 
yum -y install bison 
yum -y install zlib curl 
yum -y install openssl openssl-devel 
yum -y install bzip2-devel 
yum -y install libtermcap-devel libc-client-devel
yum -y install httpd-devel
yum -y install apr*
```

- SELINUX 설정 disabled로 변경
  - vi /etc/selinux/config
  - SELINUX=disabled 
  - 재부팅

- 아파치 다운로드
  - https://httpd.apache.org/download.cgi, http://archive.apache.org/dist/httpd/
  - wget http://archive.apache.org/dist/httpd/httpd-2.2.8.tar.gz
  - tar xvfz httpd-2.2.8.tar.gz
  - cd httpd-2.2.8
  - ln -s /usr/lib64/apr-1/build/libtool shlibtool

- Apache 컴파일 설치
  - --prefix 경로를 /usr/local/cafe24/apache 로 설정
  - 브락켓 에디터로 여는것을 추천
```
CFLAGS=" -DHARD_SERVER_LIMIT=1024 -DDEFAULT_SERVER_LIMIT=1024 -DHARD_SERVER_LIMIT=1024 -DDEFAULT_SERVER_LIMIT=1024"; export CFLAGS 
./configure --prefix=/usr/local/apache --with-mpm=worker --enable-so --disable-access --enable-access=shared --disable-auth --enable-auth=shared --disable-include --enable-include=shared --disable-log-config --enable-log-config=shared --disable-env --enable-env=shared --disable-setenvif --enable-setenvif=shared --disable-mime --enable-mime=shared --disable-status --enable-status=shared --disable-autoindex --enable-autoindex=shared -disable-asis --enable-asis=shared --disable-cgi --disable-cgid --enable-cgid=shared --disable-negotiation --enable-negotiation=shared --disable-dir --enable-dir=shared --disable-imap --enable-imap=shared --disable-actions --enable-actions=shared --disable-userdir --enable-userdir=shared --disable-alias --enable-alias=shared --enable-mods-shared=all
```
- 이후에 make, make install
  - /usr/local/cafe24에 apache 디렉터리가 생겼는지 확인

- 아파치 서비스 시작 : /usr/local/cafe24/apache/bin/apachectl start
  - 브라우저로 아이피의 80번 포트 들어가지는지 확인

### mod-jk로 아파치 톰캣 연동 및 설정으로 로드밸런싱 부하 분산
- 여러 대의 물리적인 기기를 사용할 경우에만 효과가 있지만, 테스트겸 설정
- 설치
  - wget http://us.mirrors.quenda.co/apache/tomcat/tomcat-connectors/jk/tomcat-connectors-1.2.46-src.tar.gz
  - tar xvfz tomcat-connectors-1.2.46-src.tar.gz
  - cd tomcat-connectors-1.2.46-src/native
- 컴파일
  - ./configure --with-apxs=/usr/local/cafe24/apache/bin/apxs
  - make, make install
- httpd.conf 설정
  - vi /usr/local/cafe24/apache/conf/httpd.conf
  - LoadModule jk_module modules/mod_jk.so
  - 맨 마지막에 해당 부분 추가
```
# mod_jk
<IfModule mod_jk.c>
JkWorkersFile conf/workers_jk.properties
JkShmFile run/mod_jk.shm
JkLogFile logs/mod_jk.log

JkLogLevel info
JkLogStampFormat "[%a %b %d %H:%M:%S %Y] "

JkMountFile conf/uriworkermap.properties
</IfModule>
```
- workers_jk.properties 설정
  - vi /usr/local/cafe24/apache/conf/workers_jk.properties
```
worker.list=worker1, worker2

# server1
worker.worker1.port=8009
worker.worker1.host=localhost
worker.worker1.type=ajp13
worker.worker1.lbfactor=1

# server 2
worker.worker2.port=8009
worker.worker2.host=localhost
worker.worker2.type=ajp13
worker.worker2.lbfactor=1
```
- uriworkermap.properties 설정
  - vi /usr/local/cafe24/apache/conf/uriworkermap.properties
  - 어떤 URI가 어떤 워커에 맵핑되는지를 정의
```
## Mapping the URI
/mysite1*=worker1
/mysite2*=worker1

# /service2 요청으로 들어온 것은 worker2 로 mount
# /service2/*=worker2

# png와 jpg 는 apache 가 처리
# !/service2/*.png=worker2
# !/service2/*.jpg=worker2
```

- tomcat의 server.xml 설정
  - vi /usr/local/cafe24/tomcat-cafe24/conf/server.xml
  - URIEncoding 설정
```
<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" URIEncoding="UTF-8"/>
```
- cd /usr/local/cafe24/apache ---> mkdir run
- 80번 포트의 mysite1과 mysite2가 들어가지는지 확인