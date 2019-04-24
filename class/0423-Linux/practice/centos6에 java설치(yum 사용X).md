# jdk download
- tar.gz ��ũ ��� ����
- wget ������ ���
```
tar xvfz ~.tar.gz

mv jdk1.8~ jdk1.8

ln -s jdk1.8 jdk
```

# java ȯ�溯�� ����
```
vi /etc/profile

### java

export JAVA_HOME=/usr/local/cafe24/jdk
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/tools.jar
```
- source /etc/profile

# �׽�Ʈ
```
javac -version
java -version
```

# tomcat download
- tar.gz ��ũ ��� ����
- wget ������ ���
```
tar xvfz ~.tar.gz

mv apache-tomcat~ /usr/local/cafe24/tomcat8

ln -s tomcat8/ tomcat

cd tomcat8/conf

vi server.xml

8080 �˻�

<Connector port="8080" URIEncoding="UTF-8" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />

/usr/local/cafe24/tomcat8/bin/catalina.sh start
```
- 8080 ��Ʈ ����
```
vi /etc/sysconfig/iptables

-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT

/etc/init.d/iptables restart
```
- ������:8080 �����ؼ� ��Ĺ ������ Ȯ��
- ������:8080/manager ���� ���
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

# ������ �ִ� �κ� �ּ� ó�� �ؾߵ�

<Context privileged= "true" antiResourceLocking= "false" docBase= "${catalina.home}/webapps/manager">
	 <Valve className= "org.apache.catalina.valves.RemoteAddrValve" allow= "^.*$" /> 
</Context>

/usr/local/cafe24/tomcat8/bin/catalina.sh start
```

# eclipse encoding ����
- Window ---> Preferences
- encoding �˻�
- UTF-8�� �� ����
- �����쿡�� tomcat ��ġ
- Window ---> Preferences ---> Server �˻� ---> Runtime Environment
  - ��Ĺ ���� �� ��� ����
  
# ��Ĺ�� WAR ���� ���ε�
- ������Ʈ ��Ŭ�� ---> export ---> WAR file
  - WebContent �κ��� war ���Ϸ� ������
- ������:8080/manager�� ����
- �߰��뿡 WAR file ���ε� �� Deploy
- ������:8080/WAR���ϸ� ���� Ȯ��