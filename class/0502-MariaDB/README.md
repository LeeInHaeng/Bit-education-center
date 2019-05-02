# 데이터베이스
- **데이터의 집합**
- 통합된 정보들을 **저장**하여 **운영**할 수 있는 **공용** 데이터의 집합
- 데이터 집합들끼리 **연관**시키고 **조직화** 되어야 한다.
- DB <--- (DDL, DML, DCL) ---> DBMS <---> 외부 프로그램
- 1 대 1 관계 : 두 개의 객체가 중복된 데이터가 아닌지 확인이 필요
  - 하나는 필요 없는 객체일 수 있다.
- M 대 N 관계 : 중간 테이블을 만들어 1 대 다 관계로 풀어 주는것이 좋다.

### 리눅스 환경에 MariaDB 설치
- yum으로 의존 라이브러리 및 유틸 설치
```
yum install -y gcc gcc-c++ libtermcap-devel gdbm-devel zlib* libxml* freetype* libpng* libjpeg* iconv flex gmp ncurses-devel cmake.x86_64 libaio
```
- 계정 생성
```
groupadd mysql
useradd -M -g mysql mysql
```
- 소스 받기
  - CentOS6 ---> 10.1버전, CentOS7 ---> 10.2 이상 버전
```
wget https://downloads.mariadb.org/interstitial/mariadb-10.1.38/source/mariadb-10.1.38.tar.gz 
tar xvfz mariadb-10.1.38.tar.gz 
cd mariadb-10.1.38
```
- 컴파일 환경 설정
```
cmake -DCMAKE_INSTALL_PREFIX=/usr/local/cafe24/mariadb -DMYSQL_USER=mysql -DMYSQL_TCP_PORT=3307 -DMYSQL_DATADIR=/usr/local/cafe24/mariadb/data -DMYSQL_UNIX_ADDR=/usr/local/cafe24/mariadb/tmp/mariadb.sock -DINSTALL_SYSCONFDIR=/usr/local/cafe24/mariadb/etc -DINSTALL_SYSCONF2DIR=/usr/local/cafe24/mariadb/etc/my.cnf.d -DDEFAULT_CHARSET=utf8 -DDEFAULT_COLLATION=utf8_general_ci -DWITH_EXTRA_CHARSETS=all -DWITH_ARIA_STORAGE_ENGINE=1 -DWITH_XTRADB_STORAGE_ENGINE=1 -DWITH_ARCHIVE_STORAGE_ENGINE=1 -DWITH_INNOBASE_STORAGE_ENGINE=1 -DWITH_PARTITION_STORAGE_ENGINE=1 -DWITH_BLACKHOLE_STORAGE_ENGINE=1 -DWITH_FEDERATEDX_STORAGE_ENGINE=1 -DWITH_PERFSCHEMA_STORAGE_ENGINE=1 -DWITH_READLINE=1 -DWITH_SSL=bundled -DWITH_ZLIB=system
```
- 컴파일 및 설치
```
make && make install
```
- 설치 디렉토리 권한 변경
```
chown -R mysql:mysql /usr/local/cafe24/mariadb
```
- 설정 파일 복사
```
cp -R /usr/local/cafe24/mariadb/etc/my.cnf.d/ /etc
```
- 기본 데이터베이스 생성
```
/usr/local/cafe24/mariadb/scripts/mysql_install_db --user=mysql --basedir=/usr/local/cafe24/mariadb/ --defaults-file=/usr/local/cafe24/mariadb/etc/my.cnf --datadir=/usr/local/cafe24/mariadb/data
```
- tmp 디렉토리 생성
```
mkdir /usr/local/cafe24/mariadb/tmp
chown mysql:mysql /usr/local/cafe24/mariadb/tmp
```
- 서버 구동해보기
```
/usr/local/cafe24/mariadb/bin/mysqld_safe &
```
- root 패스워드 설정
  - 1234로 설정
```
/usr/local/cafe24/mariadb/bin/mysqladmin -u root password '적절한 비밀번호'
```
- root 접속
  - u 옵션 생략시 현재 접속한 터미널 계정으로 접속
```
/usr/local/cafe24/mariadb/bin/mysql -p
```
- 스크립트 데몬으로 데이터베이스 서비스 실행
```
cp /usr/local/cafe24/mariadb/support-files/mysql.server /etc/init.d/mariadb
```
- chkconfig 등록
```
chkconfig mariadb on
```
- 환경변수 등록
```
vi /etc/profile

#mariadb
export PATH=$PATH:/usr/local/cafe24/mariadb/bi

source /etc/profile
```
- mysql -p 로 접속 확인
