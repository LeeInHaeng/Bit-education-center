# jenkins ��Ĺ, ������Ʈ�� ��Ĺ �и�

### tomcat-jenkins ����
- cd /usr/local/cafe24, mkdir tomcat-jenkins, cd tomcat-jenkins
- cp -R /usr/local/cafe24/tomcat8/* .
- rm -rf bin lib LICENSE NOTICE RELEASE-NOTES RUNNING.txt
  - bin�� lib�� jenkins������ ����� ������
- webapps ���� �ڽ��� �÷ȴ� ������Ʈ �� ���� (jenkins ����)
- ���� : vi conf/server.xml
  - ���� ��Ĺ���� ��Ʈ �浹 ���� (��Ų���� ��Ʈ ����)
  - 8�� �����ϴ� ��Ʈ�� ��� 9�� ����
``` xml
<Server port="8005" shutdown="SHUTDOWN">
  <!-- ex) 8005 -> 9005 ����
  ������ ���� �����κ� ���� --> 		
  <Service name="Catalina">
   <Connector port="9090" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="9443" URIEncoding="UTF-8"/>
        <Engine name="Catalina" defaultHost="localhost">
        <Host name="hmo.sunnyvale.co.kr"  appBase="webapps" unpackWARs="true" autoDeploy="true">
                </Host>
          </Engine>
     </Service>
```
- mkdir bin
- startup.sh ����� : vi bin/startup.sh
  - ù ��° ���� export���� �ش� ���͸� ��η� ����
  - echo $CATALINA_HOME ��ɾ ���� ��Ĺ�� ȯ�� ������ �����Ǿ� �ִ� ���� Ȯ��
```sh
#!/usr/bin/env bash
export CATALINA_BASE=/usr/local/cafe24/tomcat-jenkins
export JAVA_OPTS="-Djava.awt.headless=true -server -Xms512m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:+DisableExplicitGC"
export CATALINA_OPTS="-Denv=product -Denv.servername=jenkins"
$CATALINA_HOME/bin/catalina.sh start
```
- chmod 750 bin/startup.sh
- shutdown.sh ����� : vi bin/shutdown.sh
```sh
#!/usr/bin/env bash

export CATALINA_BASE=/usr/local/cafe24/tomcat-jenkins
export JAVA_OPTS="-Djava.awt.headless=true -server -Xms512m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:+DisableExplicitGC"
export CATALINA_OPTS="-Denv=product -Denv.servername=jenkins"
$CATALINA_HOME/bin/catalina.sh stop
```
- chmod 750 bin/shutdown.sh
- 9090 ��Ʈ ���� : vi /etc/sysconfig/iptables
```
-A INPUT -m state --state NEW -m tcp -p tcp --dport 9090 -j ACCEPT
```
- ��Ĺ ���̱� : service tomcat stop
- jenkins �ø��� : bin/startup.sh

### tomcat-cafe24 ����
- ���� �����ϰ� ����
- startup.sh �� shutdown.sh ���� servername�� cafe24�� ����

### tomcat ���� ���
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
- ������ init.d�� �ִ� tomcat�� �����ϰ� tomcat-cafe24�� ���� ����
- ������ ������� init.d �� tomcat-jenkins�� ���
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

# ����ġ ��Ĺ ����

- iptables���� 80��Ʈ ����
  - vi /etc/sysconfig/iptables

- �ʿ� ��� ��ġ
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

- SELINUX ���� disabled�� ����
  - vi /etc/selinux/config
  - SELINUX=disabled 
  - �����

- ����ġ �ٿ�ε�
  - https://httpd.apache.org/download.cgi, http://archive.apache.org/dist/httpd/
  - wget http://archive.apache.org/dist/httpd/httpd-2.2.8.tar.gz
  - tar xvfz httpd-2.2.8.tar.gz
  - cd httpd-2.2.8
  - ln -s /usr/lib64/apr-1/build/libtool shlibtool

- Apache ������ ��ġ
  - --prefix ��θ� /usr/local/cafe24/apache �� ����
  - ����� �����ͷ� ���°��� ��õ
```
CFLAGS=" -DHARD_SERVER_LIMIT=1024 -DDEFAULT_SERVER_LIMIT=1024 -DHARD_SERVER_LIMIT=1024 -DDEFAULT_SERVER_LIMIT=1024"; export CFLAGS 
./configure --prefix=/usr/local/apache --with-mpm=worker --enable-so --disable-access --enable-access=shared --disable-auth --enable-auth=shared --disable-include --enable-include=shared --disable-log-config --enable-log-config=shared --disable-env --enable-env=shared --disable-setenvif --enable-setenvif=shared --disable-mime --enable-mime=shared --disable-status --enable-status=shared --disable-autoindex --enable-autoindex=shared -disable-asis --enable-asis=shared --disable-cgi --disable-cgid --enable-cgid=shared --disable-negotiation --enable-negotiation=shared --disable-dir --enable-dir=shared --disable-imap --enable-imap=shared --disable-actions --enable-actions=shared --disable-userdir --enable-userdir=shared --disable-alias --enable-alias=shared --enable-mods-shared=all
```
- ���Ŀ� make, make install
  - /usr/local/cafe24�� apache ���͸��� ������� Ȯ��

- ����ġ ���� ���� : /usr/local/cafe24/apache/bin/apachectl start
  - �������� �������� 80�� ��Ʈ �������� Ȯ��

### mod-jk�� ����ġ ��Ĺ ���� �� �������� �ε�뷱�� ���� �л�
- ���� ���� �������� ��⸦ ����� ��쿡�� ȿ���� ������, �׽�Ʈ�� ����
- ��ġ
  - wget http://us.mirrors.quenda.co/apache/tomcat/tomcat-connectors/jk/tomcat-connectors-1.2.46-src.tar.gz
  - tar xvfz tomcat-connectors-1.2.46-src.tar.gz
  - cd tomcat-connectors-1.2.46-src/native
- ������
  - ./configure --with-apxs=/usr/local/cafe24/apache/bin/apxs
  - make, make install
- httpd.conf ����
  - vi /usr/local/cafe24/apache/conf/httpd.conf
  - LoadModule jk_module modules/mod_jk.so
  - �� �������� �ش� �κ� �߰�
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
- workers_jk.properties ����
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
- uriworkermap.properties ����
  - vi /usr/local/cafe24/apache/conf/uriworkermap.properties
  - � URI�� � ��Ŀ�� ���εǴ����� ����
```
## Mapping the URI
/mysite1*=worker1
/mysite2*=worker1

# /service2 ��û���� ���� ���� worker2 �� mount
# /service2/*=worker2

# png�� jpg �� apache �� ó��
# !/service2/*.png=worker2
# !/service2/*.jpg=worker2
```

- tomcat�� server.xml ����
  - vi /usr/local/cafe24/tomcat-cafe24/conf/server.xml
  - URIEncoding ����
```
<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" URIEncoding="UTF-8"/>
```
- cd /usr/local/cafe24/apache ---> mkdir run
- 80�� ��Ʈ�� mysite1�� mysite2�� �������� Ȯ��