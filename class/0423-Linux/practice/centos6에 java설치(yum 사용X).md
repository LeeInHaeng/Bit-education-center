# tomcat download
- tar.gz ��ũ ��� ����
- wget ������ ���
```
tar xvfz ~.tar.gz
```

# jdk download
- tar.gz ��ũ ��� ����
- wget ������ ���
```
tar xvfz ~.tar.gz

mv jdk1.8~ jdk1.8

ln -s jdk1.8 jdk
```

# vi /etc/profile
```
#java

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