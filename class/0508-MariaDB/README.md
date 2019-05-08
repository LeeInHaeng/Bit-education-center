# MariaDB ���� ����
- 0508query.sql ���� ����
- practice04.sql ���� ����

### ��������
- ���� ������ �����ϴ� �κ��� ��ȣ�� �����ش�.
- ���� ������ �����ϴ� �κ��� ����� ���� ���� ����� ���;� �Ѵ�.
  - �ش� ����� �������� ������ ����
  - ���� ������ ���� ����� ���� equal�̳� �ε�ȣ���� �̿��Ͽ� ���� ������ ����
- ���� ������ from ������ ����� �� alias�� �ݵ�� ������ �־�� �Ѵ�.
- where ������ �ַ� ����ϴ� ��찡 ����.

### ������
- in (not in)
- any (in �� ����), �ϳ��� ũ�ų�, �ϳ��� �۰ų�, �ϳ��� ���ų� ��
- all ��� ũ�ų� ��� �۰ų� ��� ���ų� ��

### DDL, DML ����
- 0508query2.sql ���� ����

# JDBC
- Java Database Connectivity
- �ڹٸ� �̿��� �����ͺ��̽� ���Ӱ� SQL ������  ����, �׸���  ���� ����� ����� �������� �ڵ鸵�� �����ϴ� ����� ������ ���� �Ծ�
- �ڹ� ���α׷������� SQL���� �����ϱ� ���� �ڹ� API
- SQL�� ���α׷��� ����� ���� ���� �� �� ����
- �����ڸ� ���� ǥ�� �������̽��� JDBC API�� �����ͺ��̽� ����, �Ǵ� ��Ÿ �����Ƽ���� �����ϴ� ����̹�(driver)
- App ---> Oracle ����̹� : �ϳ��� ����̹��� ���ϰ� ��������
- APP ---> JDBC �������̽� <--- JDBC ����̹�, 1 ����̹�, 2 ����̹� ...
  - Ȯ�强�� ������ ����
- ��� : �θ��� ����� �����Ͽ� ����(50��), Ȯ�强�� ���� ���

### Java���� JDBC�� MariaDB ����
- https://downloads.mariadb.org/connector-java/2.4.1/ ���� ������.jar �ٿ�ε�
- ������Ʈ ��Ŭ�� ---> Properties ---> Libraries ---> Add Library
 ---> User Library ---> Add Jars ---> mariadb-java-client-2.4.1.jar �߰�
- JDBC Driver(MariaDB) �ε�
  - Driver Ŭ������ static �������� DriverManager�� registerDriver�� ȣ��
  - �ش� �޼��带 ���� ����̹� ��ü�� ���
```java
Class.forName("org.mariadb.jdbc.Driver");
```
- �����ϱ�
  - ��ϵ� ����̹��� connect �޼��带 ȣ���Ѵ�.
  - url�� ȣ��Ʈ �ּ�, ��Ʈ, ��Ű�� �̸� �Է�
  - getConnection�� ���� ���̵�, ���� ��й�ȣ
```java
String url = "jdbc:mariadb://192.168.1.51:3307/webdb";
Connection conn = DriverManager.getConnection(url,"webdb","webdb");
```
- statement ��ü ����
```java
Statement stmt = conn.createStatement();
```
- SQL ���� ����
```java
String sql = "select no, name from Department";
ResultSet rs = stmt.executeQuery(sql);
```
- ��� ��������
```java
while(rs.next()) {
	Long no = rs.getLong(1);
	String name = rs.getString(2);
}
```
- �ڿ� ����
  - finally ������ rs, stmt, conn ������ close�ؼ� ���� (������ ��������)
- select�� ���� : executeQuery�� �����ϰ�, ResultSet���� �޴´�.
- insert�� ���� : executeUpdate�� �����ϰ�, ������ ������ int�� ��ȯ�Ѵ�.
- delete�� ���� : insert�� ����
- update�� ���� : insert�� ����

### DAO
- Data Access Object
- ���ο��� DAO�� ���ϴ� �����͸� �޼���� ��û
- DAO�� �����ͺ��̽��� ���� �ϰ�, ����� VO ��ü�� List�� ��Ƽ� Main�� ��ȯ

### DAO statement ��ü ����
- �������� �ϰ�� ������ �������� ������ �����ߴ�.
- ```+```�� �� �Ű��������� ������ ���� �ϴ� ���� �ſ� ������ �۾��̴�.
- ����� �Է� ���� �״�� ���� ��� ���ȿ� ����� �� �ִ�.
- �̷��� ������ �ذ��ϱ� ���� ���� ���ε� �ϴ� ���� ����.
- JDBC�� SQL�� �̸� �غ��� ����, �ڿ��� �Ű������� ���ε� �Ѵ�.
- JDBC Driver �ε�, �����ϱ�
- SQL �غ�
```java
String sql = " select emp_no, first_name, last_name, hire_date " + 
		" from employees " + 
		" where first_name like ?" + 
		" or last_name like ?";
PreparedStatement pstmt = conn.prepareStatement(sql);
```
- ���ε�
```java
pstmt.setString(1, "%" + keyword + "%");
pstmt.setString(2, "%" + keyword + "%");
```
- ���� ����
  - ���� ����� �Ű� ������ ����� �Ѵ�.
```java
rs = pstmt.executeQuery();
```