# MariaDB 쿼리 연습
- 0508query.sql 파일 참고
- practice04.sql 파일 참고

### 서브쿼리
- 서브 쿼리를 수행하는 부분을 괄호로 감싸준다.
- 서브 쿼리를 수행하는 부분의 결과는 단일 행의 결과가 나와야 한다.
  - 해당 결과를 바탕으로 쿼리를 진행
  - 서브 쿼리의 수행 결과에 대해 equal이나 부등호들을 이용하여 다음 쿼리를 수행
- 서브 쿼리를 from 절에서 사용할 시 alias를 반드시 지정해 주어야 한다.
- where 절에서 주로 사용하는 경우가 많다.

### 다중행
- in (not in)
- any (in 과 동일), 하나라도 크거나, 하나라도 작거나, 하나라도 같거나 등
- all 모두 크거나 모두 작거나 모두 같거나 등

### DDL, DML 연습
- 0508query2.sql 파일 참고

# JDBC
- Java Database Connectivity
- 자바를 이용한 데이터베이스 접속과 SQL 문장의  실행, 그리고  실행 결과로 얻어진 데이터의 핸들링을 제공하는 방법과 절차에 관한 규약
- 자바 프로그램내에서 SQL문을 실행하기 위한 자바 API
- SQL과 프로그래밍 언어의 통합 접근 중 한 형태
- 개발자를 위한 표준 인터페이스인 JDBC API와 데이터베이스 벤더, 또는 기타 써드파티에서 제공하는 드라이버(driver)
- App ---> Oracle 드라이버 : 하나의 드라이버에 강하게 묶여있음
- APP ---> JDBC 인터페이스 <--- JDBC 드라이버, 1 드라이버, 2 드라이버 ...
  - 확장성이 굉장히 좋음
- 상속 : 부모의 기능을 재사용하여 구현(50점), 확장성을 위해 사용

### Java에서 JDBC로 MariaDB 연동
- https://downloads.mariadb.org/connector-java/2.4.1/ 에서 컨넥터.jar 다운로드
- 프로젝트 우클릭 ---> Properties ---> Libraries ---> Add Library
 ---> User Library ---> Add Jars ---> mariadb-java-client-2.4.1.jar 추가
- JDBC Driver(MariaDB) 로딩
  - Driver 클래스의 static 영역에서 DriverManager의 registerDriver를 호출
  - 해당 메서드를 통해 드라이버 객체를 등록
```java
Class.forName("org.mariadb.jdbc.Driver");
```
- 연결하기
  - 등록된 드라이버의 connect 메서드를 호출한다.
  - url은 호스트 주소, 포트, 스키마 이름 입력
  - getConnection은 계정 아이디, 계정 비밀번호
```java
String url = "jdbc:mariadb://192.168.1.51:3307/webdb";
Connection conn = DriverManager.getConnection(url,"webdb","webdb");
```
- statement 객체 생성
```java
Statement stmt = conn.createStatement();
```
- SQL 쿼리 실행
```java
String sql = "select no, name from Department";
ResultSet rs = stmt.executeQuery(sql);
```
- 결과 가져오기
```java
while(rs.next()) {
	Long no = rs.getLong(1);
	String name = rs.getString(2);
}
```
- 자원 정리
  - finally 절에서 rs, stmt, conn 순으로 close해서 정리 (선언의 역순으로)
- select로 질의 : executeQuery로 수행하고, ResultSet으로 받는다.
- insert로 질의 : executeUpdate로 수행하고, 성공한 갯수를 int로 반환한다.
- delete로 질의 : insert와 동일
- update로 질의 : insert와 동일

### DAO
- Data Access Object
- 메인에서 DAO에 원하는 데이터를 메서드로 요청
- DAO는 데이터베이스에 쿼리 하고, 결과를 VO 객체의 List에 담아서 Main에 반환

### DAO statement 객체 개선
- 이전에는 완결된 쿼리를 바탕으로 쿼리를 수행했다.
- ```+```로 각 매개변수들을 쿼리로 연결 하는 것은 매우 불편한 작업이다.
- 사용자 입력 값을 그대로 넣을 경우 보안에 취약할 수 있다.
- 이러한 문제를 해결하기 위해 값을 바인딩 하는 것이 좋다.
- JDBC에 SQL을 미리 준비해 놓고, 뒤에서 매개변수를 바인딩 한다.
- JDBC Driver 로딩, 연결하기
- SQL 준비
```java
String sql = " select emp_no, first_name, last_name, hire_date " + 
		" from employees " + 
		" where first_name like ?" + 
		" or last_name like ?";
PreparedStatement pstmt = conn.prepareStatement(sql);
```
- 바인딩
```java
pstmt.setString(1, "%" + keyword + "%");
pstmt.setString(2, "%" + keyword + "%");
```
- 쿼리 실행
  - 쿼리 실행시 매개 변수가 없어야 한다.
```java
rs = pstmt.executeQuery();
```