# �����ͺ��̽�
- **�������� ����**
- ���յ� �������� **����**�Ͽ� **�**�� �� �ִ� **����** �������� ����
- ������ ���յ鳢�� **����**��Ű�� **����ȭ** �Ǿ�� �Ѵ�.
- DB <--- (DDL, DML, DCL) ---> DBMS <---> �ܺ� ���α׷�
- 1 �� 1 ���� : �� ���� ��ü�� �ߺ��� �����Ͱ� �ƴ��� Ȯ���� �ʿ�
  - �ϳ��� �ʿ� ���� ��ü�� �� �ִ�.
- M �� N ���� : �߰� ���̺��� ����� 1 �� �� ����� Ǯ�� �ִ°��� ����.

### ������ ȯ�濡 MariaDB ��ġ
- yum���� ���� ���̺귯�� �� ��ƿ ��ġ
```
yum install -y gcc gcc-c++ libtermcap-devel gdbm-devel zlib* libxml* freetype* libpng* libjpeg* iconv flex gmp ncurses-devel cmake.x86_64 libaio
```
- ���� ����
```
groupadd mysql
useradd -M -g mysql mysql
```
- �ҽ� �ޱ�
  - CentOS6 ---> 10.1����, CentOS7 ---> 10.2 �̻� ����
```
wget https://downloads.mariadb.org/interstitial/mariadb-10.1.38/source/mariadb-10.1.38.tar.gz 
tar xvfz mariadb-10.1.38.tar.gz 
cd mariadb-10.1.38
```
- ������ ȯ�� ����
```
cmake -DCMAKE_INSTALL_PREFIX=/usr/local/cafe24/mariadb -DMYSQL_USER=mysql -DMYSQL_TCP_PORT=3307 -DMYSQL_DATADIR=/usr/local/cafe24/mariadb/data -DMYSQL_UNIX_ADDR=/usr/local/cafe24/mariadb/tmp/mariadb.sock -DINSTALL_SYSCONFDIR=/usr/local/cafe24/mariadb/etc -DINSTALL_SYSCONF2DIR=/usr/local/cafe24/mariadb/etc/my.cnf.d -DDEFAULT_CHARSET=utf8 -DDEFAULT_COLLATION=utf8_general_ci -DWITH_EXTRA_CHARSETS=all -DWITH_ARIA_STORAGE_ENGINE=1 -DWITH_XTRADB_STORAGE_ENGINE=1 -DWITH_ARCHIVE_STORAGE_ENGINE=1 -DWITH_INNOBASE_STORAGE_ENGINE=1 -DWITH_PARTITION_STORAGE_ENGINE=1 -DWITH_BLACKHOLE_STORAGE_ENGINE=1 -DWITH_FEDERATEDX_STORAGE_ENGINE=1 -DWITH_PERFSCHEMA_STORAGE_ENGINE=1 -DWITH_READLINE=1 -DWITH_SSL=bundled -DWITH_ZLIB=system
```
- ������ �� ��ġ
```
make && make install
```
- ��ġ ���丮 ���� ����
```
chown -R mysql:mysql /usr/local/cafe24/mariadb
```
- ���� ���� ����
```
cp -R /usr/local/cafe24/mariadb/etc/my.cnf.d/ /etc
```
- �⺻ �����ͺ��̽� ����
```
/usr/local/cafe24/mariadb/scripts/mysql_install_db --user=mysql --basedir=/usr/local/cafe24/mariadb/ --defaults-file=/usr/local/cafe24/mariadb/etc/my.cnf --datadir=/usr/local/cafe24/mariadb/data
```
- tmp ���丮 ����
```
mkdir /usr/local/cafe24/mariadb/tmp
chown mysql:mysql /usr/local/cafe24/mariadb/tmp
```
- ���� �����غ���
```
/usr/local/cafe24/mariadb/bin/mysqld_safe &
```
- root �н����� ����
  - 1234�� ����
```
/usr/local/cafe24/mariadb/bin/mysqladmin -u root password '������ ��й�ȣ'
```
- root ����
  - u �ɼ� ������ ���� ������ �͹̳� �������� ����
```
/usr/local/cafe24/mariadb/bin/mysql -p
```
- ��ũ��Ʈ �������� �����ͺ��̽� ���� ����
```
cp /usr/local/cafe24/mariadb/support-files/mysql.server /etc/init.d/mariadb
```
- chkconfig ���
```
chkconfig mariadb on
```
- ȯ�溯�� ���
```
vi /etc/profile

#mariadb
export PATH=$PATH:/usr/local/cafe24/mariadb/bi

source /etc/profile
```
- mysql -p �� ���� Ȯ��
