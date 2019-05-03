# MariaDB 쿼리 연습

### 파일로 되어있는 데이터를 테이블로 로드
- 데이터의 값들은 탭으로 구분되어야 한다.
- load data 쿼리 사용
```
// 데이터
Fluffy	Harold	cat	f	1993-02-04	\N
Claws	Gwen	cat	m	1994-03-17	\N
...


MariaDB > load data local infile '/root/pet.txt' into table pet;
```

### restore dump 명령어
- dump한 데이터 베이스를 로드하기 위한 명령어
- sql 확장자의 파일에 있는 sql 명령어를 쭉 수행
```
mysql -u root -p < employees.sql
```

### MariaDB 계정 생성, 외부에서 리눅스 MariaDB 연결
- 3307 포트 방화벽 열기
  - vi /etc/sysconfig/iptables
  - service iptables restart
```
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3307 -j ACCEPT
```
- MariaDB에서 접근 계정들 생성하기
  - db에 root 계정으로 접속 후 계정 생성 (mysql -p)
  - localhost는 리눅스 계정, 192.168.*은 윈도우에서 접근하기 위한 계정
```
MariaDB > create user 'hr'@'localhost' identified by 'hr';
MariaDB > create user 'hr'@'192.168.%' identified by 'hr';
```
- 생성한 계정들 권한 부여
  - flush privileges : 권한 즉시 부여
  - employees는 database 이름. 즉, 해당 데이터베이스의 모든 테이블에 대해 권한 허가
```
MariaDB > grant all privileges on employees.* to 'hr'@'localhost';
MariaDB > flush privileges;

MariaDB > grant all privileges on employees.* to 'hr'@'192.168.%';
MariaDB > flush privileges;
```
- 생성한 계정으로 접속
```
mysql -u hr -D employees -p
```
- 워크밴치에서 접속
  - hr계정, ip는 리눅스의 ip, 포트는 3307, schema는 employees로 접속

### sql 쿼리 연습
- 0503query.sql
- 0503homework_1.sql
- 0503homewor_2.sql 참고