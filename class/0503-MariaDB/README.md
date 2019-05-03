# MariaDB ���� ����

### ���Ϸ� �Ǿ��ִ� �����͸� ���̺�� �ε�
- �������� ������ ������ ���еǾ�� �Ѵ�.
- load data ���� ���
```
// ������
Fluffy	Harold	cat	f	1993-02-04	\N
Claws	Gwen	cat	m	1994-03-17	\N
...


MariaDB > load data local infile '/root/pet.txt' into table pet;
```

### restore dump ��ɾ�
- dump�� ������ ���̽��� �ε��ϱ� ���� ��ɾ�
- sql Ȯ������ ���Ͽ� �ִ� sql ��ɾ �� ����
```
mysql -u root -p < employees.sql
```

### MariaDB ���� ����, �ܺο��� ������ MariaDB ����
- 3307 ��Ʈ ��ȭ�� ����
  - vi /etc/sysconfig/iptables
  - service iptables restart
```
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3307 -j ACCEPT
```
- MariaDB���� ���� ������ �����ϱ�
  - db�� root �������� ���� �� ���� ���� (mysql -p)
  - localhost�� ������ ����, 192.168.*�� �����쿡�� �����ϱ� ���� ����
```
MariaDB > create user 'hr'@'localhost' identified by 'hr';
MariaDB > create user 'hr'@'192.168.%' identified by 'hr';
```
- ������ ������ ���� �ο�
  - flush privileges : ���� ��� �ο�
  - employees�� database �̸�. ��, �ش� �����ͺ��̽��� ��� ���̺� ���� ���� �㰡
```
MariaDB > grant all privileges on employees.* to 'hr'@'localhost';
MariaDB > flush privileges;

MariaDB > grant all privileges on employees.* to 'hr'@'192.168.%';
MariaDB > flush privileges;
```
- ������ �������� ����
```
mysql -u hr -D employees -p
```
- ��ũ��ġ���� ����
  - hr����, ip�� �������� ip, ��Ʈ�� 3307, schema�� employees�� ����

### sql ���� ����
- 0503query.sql
- 0503homework_1.sql
- 0503homewor_2.sql ����