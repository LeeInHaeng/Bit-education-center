# PostgreSQL
- 객체 관계형 데이터 베이스(ORDBMS)
- 오픈소스 RDBMS로 SQL 표준 대부분을 지원한다.
- 주요 최신 기능
  - 복합쿼리
  - 외래키
  - 트리거
  - 업데이트 가능한 뷰
  - 트랜잭션 무결성
- 다양한 확장성 제공
  - 데이터 타입
  - 함수
  - 연산자
  - 집계함수
  - 인덱스 메소드
  - 프로시저 언어

### PostgreSQL Linux 설치
- yum -y install readline-devel.x86_64
- yum -y install python-devel
- https://www.postgresql.org/ftp/source/ 에서 버전 선택 후 wget (10.2 버전)
- wget https://ftp.postgresql.org/pub/source/v10.2/postgresql-10.2.tar.gz
- tar xvfz postgresql-10.2.tar.gz
- cd postgresql-10.2.tar.gz
- ./configure --prefix=/usr/local/cafe24/pgsql --with-python --with-openssl --enable-nls=ko
- make && make install
- prefix 경로에 제대로 설치 되어 있는지 확인
- adduser -M postgres
  - postgresql은 root 계정으로 실행하지 못한다.
- chown -R postgres:postgres /usr/local/cafe24/pgsql
- 환경변수 PATH 잡기 (/etc/profile)
```
#postgres
export POSTGRES_HOME=/usr/local/cafe24/pgsql
export PGLIB=$POSTGRES_HOME/lib
export PGDATA=$POSTGRES_HOME/data
export PATH=$PATH:$POSTGRES_HOME/bin
```
- 기본 데이터베이스 생성
  - root 계정에서 postgres 계정으로 바꿔서 해당 명령어를 수행하는 것
```
sudo -u postgres /usr/local/cafe24/pgsql/bin/initdb -E UTF8 --locale=ko_KR.UTF-8 /usr/local/cafe24/pgsql/data
```
- postgres 서버실행
  - 로그 파일의 경로를 /usr/local/cafe24/pgsql/data/logfile 로 지정
```
sudo -u postgres /usr/local/cafe24/pgsql/bin/pg_ctl -D /usr/local/cafe24/pgsql/data -l /usr/local/cafe24/pgsql/data/logfile start
```
- ps -ef | grep postgres
- netstat -nlp | grep 5432
- 접속하기
```
psql -U postgres
```
- postgres 사용자 비밀번호 수정
```
alter user postgres with password '비밀번호';
```
- 접근 설정 (/usr/local/cafe24/psql/data/pg_hba.conf)
  - 해당 파일에 설정하지 않으면 암호를 설정 했어도 그냥 접속 된다.
```
# TYPE  DATABASE        USER            ADDRESS                 METHOD
# "local" is for Unix domain socket connections only
local   all           postgres                                 password
```
- \! 리눅스 명령어 : 리눅스 명령어를 postgres 안에서 수행 가능

### 기본 쿼리 사용
- \l : 현재 서버에 존재하는 데이터베이스 목록
- create database webdb;
- \c webdb : webdb 데이터베이스를 사용 (mysql에서 use 명령어)
- \dt : 사용하기를 선택한 데이터베이스의 전체 테이블 목록 출력
- 테이블 생성
```
create table pet(
name varchar(20),
owner varchar(20),
species varchar(20),
gender char(1),
birth date,
death date);
```
- \d 테이블이름 : 테이블 구조 확인
- txt 파일을 로드
  - 한 레코드의 값들은 탭으로 구분되어야 한다.
  - 순서는 테이블을 생성할 때의 컬럼 순서대로 되어 있어야 한다.
  - \copy 명령어를 사용하여 pet 이라는 테이블에 pet.txt 데이터를 로드
```
\copy pet from '/root/pet.txt';
```
- 자동증가 컬럼 사용
```
create sequence seq_author start 1;

select nextval('seq_author');

혹은 SMALLSERIAL, SERIAL, BIGSERIAL 타입 사용
```

### 계정, 권한 관리
- 사용자 추가
```
create user webdb with password 'webdb';
```
- 테이블에 대한 권한 추가
```
grant all privileges on all tables in schema public to webdb;
```
- 연결 설정 (/usr/local/cafe24/pgsql/data/pg_hba.conf)
```
# TYPE  DATABASE        USER            ADDRESS                 METHOD
# "local"  is  for  Unix domain socket  connections  only
local   all             postgres                                password
local   webdb           webdb                                   password
```
- 테이블의 소유주 변경
  - pet 이라는 테이블의 소유주를 webdb로 변경
```
alter table pet owner to webdb;
```
- 서비스 데몬 추가 (/etc/init.d)
```
#!/bin/bash
# chkconfig:2345 90 20

# Installation prefix
prefix=/usr/local/cafe24/pgsql

# Data directory
PGDATA="$prefix/data"

# Who to run the postmaster as, usually "postgres". (NOT "root")
PGUSER=postgres

# Where to keep a log file
PGLOG="$PGDATA/logfile"

## STOP EDITING HERE

# Check for echo -n vs echo \c
if echo '\c' | grep -s c >/dev/null 2>&1 ; then
        ECHO_N="echo -n"
        ECHO_C=""
else
        ECHO_N="echo"
        ECHO_C='\c'
fi

# The path that is to be used for the script
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin

# What to use to start up the postmaster (we do NOT use pg_ctl for this,
# as it adds no value and can cause the postmaster to misrecognize a stale
# lock file)
DAEMON="$prefix/bin/postmaster"

# What to use to shut down the postmaster
PGCTL="$prefix/bin/pg_ctl"

set -e

# Only start if we can find the postmaster.
test -x $DAEMON || exit 0

# Parse command line parameters.
case $1 in
        start)
                $ECHO_N "Starting PostgreSQL: "$ECHO_C
                su - $PGUSER -c "$DAEMON -D $PGDATA" >>$PGLOG 2>/dev/null &
                echo "ok"
                ;;
        stop)
                echo -n "Stopping PostgreSQL: "
                su - $PGUSER -c "$PGCTL stop -D '$PGDATA' -s -m fast" 2>/dev/null
                echo "ok"
                ;;
        restart)
                echo -n "Restarting PostgreSQL: "
                su - $PGUSER -c "$PGCTL stop -D '$PGDATA' -s -m fast -w"
                su - $PGUSER -c "$DAEMON -D $PGDATA" >>$PGLOG 2>&1 &
                echo "ok"
                ;;
        reload)
                echo -n "Reload PostgreSQL: "
                su - $PGUSER -c "$PGCTL reload -D '$PGDATA' -s"
                echo "ok"
                ;;
        status)
                su - $PGUSER -c "$PGCTL status -D '$PGDATA'"
                ;;
        *)

                # Print help
                echo "Usage: $0 {start|stop|restart|reload|status}" 1>&2
                exit 1
                ;;
esac

exit $?
```
- 외부 접속 허용 (/usr/local/cafe24/pgsql/data/postgresql.conf)
```
# - Connection Settings
listen_addresses = '*'         # what IP address(es) to listen on;
```
- 외부 접속 허용 연결 설정 (/usr/local/cafe24/pgsql/data/pg_hba.conf)
```
# IPv4 local connections:
host    all             postgres        127.0.0.1/32            password
host    webdb           webdb           127.0.0.1/32            password
host    webdb           webdb           192.168.1.0/24          password
```
- 5432 포트 열기 : /etc/sysconfig/iptables
- 외부 프로그램 : TablePlus 설치

### JDBC 연결
- postgresql jdbc driver 다운로드
- 혹은 Maven에서 다운로드
```xml
		<!-- postgresql jdbc driver -->
		<dependency>
		    <groupId>org.postgresql</groupId>
		    <artifactId>postgresql</artifactId>
		    <version>42.2.5</version>
		</dependency>
```
- Java에서 연결할 때 class 이름과 url
```
Class.forName("org.postgresql.Driver");
			
String url = "jdbc:postgresql://192.168.1.51:5432/webdb";
```
- date_format이 아닌 to_char 사용
```
to_char(reg_date, 'yyyy-mm-dd')
```