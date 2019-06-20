# 0423-Linux
### VirtualBox CentOS6 환경
- NAT
- Bridge
- 파티션
- 네트워크 설정
- 패키지 설치 및 업데이트
- 환경변수 설정

# 0424-Linux
### CentOS6 기본 설정
- 서버 시간 동기화
- 계정 추가
- 계정 보안
- 자동 로그아웃
- history 포맷 설정
- group 추가
- 부팅시 자동으로 tomcat 서버 시작

# 0425-Linux
### CentOS6 환경설정
- 서버에 오는 ping 막기
- github 설치 및 설정

# 0426-Java Network
### CentOS6 환경설정
- 고정 IP 설정
### IO 작업
- IO(KeyboardTest)
- 파일 스트림 (PhoneList01)
- 토크나이저 (Scanner 사용 X) (PhoneList01)
- Scanner 사용 (PhoneList02)
### 네트워크 프로그래밍
- InetAddress 클래스 (Localhost)
- TCP 서버 프로그래밍 (TCPServer)

# 0429-Java Network
### Java SocketProgram
- java에서 final 의미
- cmd에서 클래스 수행
- 클라이언트 소켓 프로그래밍
- socket에서 보조스트림 사용
### Java SocketProgram 멀티 쓰레드
- 기존 소스의 문제
- Java 쓰레드의 문제
- 멀티 쓰레드 구현
### 트래픽 처리
- 웹 서버
- 아파치 : 쓰레드를 통해서 WAS 수행
- nginx : 이벤트 드리븐 방식의 비동기 처리
- 서킷 브레이커
- VRRP
- API 서버
- DB 서버
- Stateless

# 0430-Java Network
### UDP
- UDP Echo 서버/클라이언트

# 0501-Java Network
### Maven
- 이클립스에서 Maven 빌드 1
- 이클립스에서 Maven 빌드 2
- 리눅스에서 Maven 설치 및 연동

# 0502-MariaDB
### 데이터베이스
- 리눅스 환경에 MariaDB 설치

# 0503-MariaDB
### MariaDB 쿼리 연습
- 파일로 되어있는 데이터를 테이블로 로드
- restore dump 명령어
- MariaDB 계정 생성, 외부에서 리눅스 MariaDB 연결
- sql 쿼리 연습

# 0507-MariaDB
### MariaDB 쿼리 연습
- 조인
### 데이터베이스 설계
- 정규화

# 0508-MariaDB
### MariaDB 쿼리 연습
- 서브쿼리
- 다중행
- DDL, DML 연습
### JDBC
- Java에서 JDBC로 MariaDB 연동
- DAO
- DAO statement 객체 개선

# 0509-jdbc
### 다대다 관계 이해
- 도서 관리 프로그램에서 주문과 책 사이의 관계
- bookmall 예제와 bookmall 실습 과제 수행

# 0510-Java Programming(Basic)
### Java 기본
- Object Test1
- Object Test2

# 0513-Servlet-JSP
### JSP, Servlet
- Maven Project 빌드
- JSP 사용
- Servlet 사용

# 0514-Servlet-Jsp
### Servlet JSP 사용
- Servlet에서 JSP로 데이터 넘기기
- jsp 포함 시키기
- java 쿠키 및 세션 읽기, 쓰기
- Servlet 생명주기
- Servlet filter

# 0515-JSTL&Spring
### EL-JSTL
- Java 변수 Scope
- EL의 다양한 표현식
- JSTL 사용
### Spring 사용
- Spring 초기 설정
- Spring 동작과정

# 0516-Spring
### Spring Controller 기본 사용
### Spring Controller 어노테이션 사용
- RequestMapping 어노테이션 사용
- RequestParam 어노테이션 사용
### Root Application Context (비즈니스 로직)
- 최종 xml 설정 파일들 기본 형태
### 어플리케이션 아키텍처 예제
- 비즈니스 분석 (사용자 스토리 도출)
- 서비즈 정의

# 0517-Exception&MyBatis
### Spring Error 예외처리
- web.xml에 http 상태 코드에 따른 view 위치 맵핑
- 글로벌 핸들러(어드바이징 컨트롤러)를 이용한 예외 처리
### Spring MyBatis 사용
- DataSource를 이용해 DB 연동
- Spring에서 Mybatis 연동 및 DAO에서 사용
- MyBatis mapper xml 파일 설정 예제

# 0520-Spring
### 메시지 컨버터
- 메시지 컨버터 등록하기
- Ajax 사용하기

# 0521-Spring
### Spring에서 로그 사용
- Logback 사용
### 파일 업로드
- File 저장을 위한 Service 클래스 예제

# 0522-Spring
### AOP
- AOP 개요
- AOP 구성요소
- AOP 사용하기
### Valid
- 제공 어노테이션
- Valid 사용

# 0523-Spring
### 인터셉터
- 인터셉터 구현
### 어노테이션 만들기
### 인터셉터 + 어노테이션 활용
### 커스텀 어노테이션 사용시 어노테이션에 값 셋팅

# 0524-Spring
### Spring Container
- BeanFactory (XMLBeanFactory)
- ApplicationContext (주로 사용)
### CI (Continuous Integration)
- Jenkins 기반의 CI 환경
- Jenkins 설치 및 셋팅
- 에러 해결
- 성공 pom.xml
- pom.xml에 톰캣 manager의 username과 password 가 나오는 것을 방지
- github의 private Repository를 Jenkins로 빌드

# 0527-Spring
### XML 파일을 이용한 빈 주입 17가지

# 0529-Spring
### 멀티 프로젝트
### xml 파일이 아닌 class 파일로 설정파일 분리 - applicationContext.xml
- 클래스 파일로 설정파일을 만들 시 web.xml에 설정 클래스 파일 경로 설정
- DataSource(데이터베이스) 설정
- MyBatis 설정
- aspectj-autoproxy 설정
- component-scan 설정
- annotation-config(오토 스캐닝)의 component-scan 설정
### xml 파일이 아닌 class 파일로 설정파일 분리 - spring-servlet.xml
- ViewResolver 설정
- default-servlet-handler 설정
- Message-Converters 설정
- MultipartResolver 설정
- MessageSource 설정
- ArgumentResolver 설정
- interceptors 설정

# 0530-SpringBoot
### web.xml 설정파일을 클래스 파일로 대체
### SpringBoot
- 데이터베이스(DataSource), MyBatis 설정
- ViewResolver 설정
- MultipartResolver 설정
- MessageSource 설정
- Message-Converters 와 ArgumentResolver, interceptors 설정

# 0531-Apache&Tomcat
### jenkins 톰캣, 프로젝트용 톰캣 분리
- tomcat-jenkins 설정
- tomcat-cafe24 설정
- tomcat 서비스 등록
### 아파치 톰캣 연동
- mod-jk로 아파치 톰캣 연동 및 설정으로 로드밸런싱 부하 분산

# 0603-Jenkins
### Springboot jenkins 배포

# 0604-Linux
### 가상 파일 시스템 (VFS)
- 주요 특수 파일 시스템
- 리눅스 하드 디스크 설정

# 0610-PostgreSQL
### PostgreSQL
- PostgreSQL Linux 설치
- 기본 쿼리 사용
- 계정, 권한 관리
- JDBC 연결

# 0619-Django
### BeautifulSoup과 Selenium 크롤링
- https://github.com/LeeInHaeng/python_crawling
### Python에서 Postgres 사용
### Django 프로젝트 시작 (pycharm community 버전 에서)
- 프로젝트 셋팅 전에 DB 및 계정 생성
- Django 프로젝트 시작
- Application 작업

# 0620-Django
### Django 사용
- Model 정의
- Migrations 이름의 DDL python 모듈을 생성
- views에서 파라미터 뽑기
- views와 기본적인 통신
- html 기본 틀에 변경되는 내용만 각각 html에 맞게 변경하기 (include 같은 기능)
- 간단한 ORM 예제
- Ajax 통신
- Session에 데이터 저장