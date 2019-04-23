# tomcat download
- tar.gz 링크 경로 복사
- wget 복사한 경로
```
tar xvfz ~.tar.gz
```

# jdk download
- tar.gz 링크 경로 복사
- wget 복사한 경로
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

# 테스트
```
javac -version
java -version
```