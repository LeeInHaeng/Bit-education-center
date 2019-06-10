# PostgreSQL
- ��ü ������ ������ ���̽�(ORDBMS)
- ���¼ҽ� RDBMS�� SQL ǥ�� ��κ��� �����Ѵ�.
- �ֿ� �ֽ� ���
  - ��������
  - �ܷ�Ű
  - Ʈ����
  - ������Ʈ ������ ��
  - Ʈ����� ���Ἲ
- �پ��� Ȯ�强 ����
  - ������ Ÿ��
  - �Լ�
  - ������
  - �����Լ�
  - �ε��� �޼ҵ�
  - ���ν��� ���

### PostgreSQL Linux ��ġ
- yum -y install readline-devel.x86_64
- yum -y install python-devel
- https://www.postgresql.org/ftp/source/ ���� ���� ���� �� wget (10.2 ����)
- wget https://ftp.postgresql.org/pub/source/v10.2/postgresql-10.2.tar.gz
- tar xvfz postgresql-10.2.tar.gz
- cd postgresql-10.2.tar.gz
- ./configure --prefix=/usr/local/cafe24/pgsql --with-python --with-openssl --enable-nls=ko
- make && make install
- prefix ��ο� ����� ��ġ �Ǿ� �ִ��� Ȯ��
- adduser -M postgres
  - postgresql�� root �������� �������� ���Ѵ�.
- chown -R postgres:postgres /usr/local/cafe24/pgsql
- ȯ�溯�� PATH ��� (/etc/profile)
```
#postgres
export POSTGRES_HOME=/usr/local/cafe24/pgsql
export PGLIB=$POSTGRES_HOME/lib
export PGDATA=$POSTGRES_HOME/data
export PATH=$PATH:$POSTGRES_HOME/bin
```
- �⺻ �����ͺ��̽� ����
  - root �������� postgres �������� �ٲ㼭 �ش� ��ɾ �����ϴ� ��
```
sudo -u postgres /usr/local/cafe24/pgsql/bin/initdb -E UTF8 --locale=ko_KR.UTF-8 /usr/local/cafe24/pgsql/data
```
- postgres ��������
  - �α� ������ ��θ� /usr/local/cafe24/pgsql/data/logfile �� ����
```
sudo -u postgres /usr/local/cafe24/pgsql/bin/pg_ctl -D /usr/local/cafe24/pgsql/data -l /usr/local/cafe24/pgsql/data/logfile start
```
- ps -ef | grep postgres
- netstat -nlp | grep 5432
- �����ϱ�
```
psql -U postgres
```
- postgres ����� ��й�ȣ ����
```
alter user postgres with password '��й�ȣ';
```
- ���� ���� (/usr/local/cafe24/psql/data/pg_hba.conf)
  - �ش� ���Ͽ� �������� ������ ��ȣ�� ���� �߾ �׳� ���� �ȴ�.
```
# TYPE  DATABASE        USER            ADDRESS                 METHOD
# "local" is for Unix domain socket connections only
local   all           postgres                                 password
```
- \! ������ ��ɾ� : ������ ��ɾ postgres �ȿ��� ���� ����

### �⺻ ���� ���
- \l : ���� ������ �����ϴ� �����ͺ��̽� ���
- create database webdb;
- \c webdb : webdb �����ͺ��̽��� ��� (mysql���� use ��ɾ�)
- \dt : ����ϱ⸦ ������ �����ͺ��̽��� ��ü ���̺� ��� ���
- ���̺� ����
```
create table pet(
name varchar(20),
owner varchar(20),
species varchar(20),
gender char(1),
birth date,
death date);
```
- \d ���̺��̸� : ���̺� ���� Ȯ��
- txt ������ �ε�
  - �� ���ڵ��� ������ ������ ���еǾ�� �Ѵ�.
  - ������ ���̺��� ������ ���� �÷� ������� �Ǿ� �־�� �Ѵ�.
  - \copy ��ɾ ����Ͽ� pet �̶�� ���̺� pet.txt �����͸� �ε�
```
\copy pet from '/root/pet.txt';
```
- �ڵ����� �÷� ���
```
create sequence seq_author start 1;

select nextval('seq_author');

Ȥ�� SMALLSERIAL, SERIAL, BIGSERIAL Ÿ�� ���
```

### ����, ���� ����
- ����� �߰�
```
create user webdb with password 'webdb';
```
- ���̺� ���� ���� �߰�
```
grant all privileges on all tables in schema public to webdb;
```
- ���� ���� (/usr/local/cafe24/pgsql/data/pg_hba.conf)
```
# TYPE  DATABASE        USER            ADDRESS                 METHOD
# "local"  is  for  Unix domain socket  connections  only
local   all             postgres                                password
local   webdb           webdb                                   password
```
- ���̺��� ������ ����
  - pet �̶�� ���̺��� �����ָ� webdb�� ����
```
alter table pet owner to webdb;
```
- ���� ���� �߰� (/etc/init.d)
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
- �ܺ� ���� ��� (/usr/local/cafe24/pgsql/data/postgresql.conf)
```
# - Connection Settings
listen_addresses = '*'         # what IP address(es) to listen on;
```
- �ܺ� ���� ��� ���� ���� (/usr/local/cafe24/pgsql/data/pg_hba.conf)
```
# IPv4 local connections:
host    all             postgres        127.0.0.1/32            password
host    webdb           webdb           127.0.0.1/32            password
host    webdb           webdb           192.168.1.0/24          password
```
- 5432 ��Ʈ ���� : /etc/sysconfig/iptables
- �ܺ� ���α׷� : TablePlus ��ġ

### JDBC ����
- postgresql jdbc driver �ٿ�ε�
- Ȥ�� Maven���� �ٿ�ε�
```xml
		<!-- postgresql jdbc driver -->
		<dependency>
		    <groupId>org.postgresql</groupId>
		    <artifactId>postgresql</artifactId>
		    <version>42.2.5</version>
		</dependency>
```
- Java���� ������ �� class �̸��� url
```
Class.forName("org.postgresql.Driver");
			
String url = "jdbc:postgresql://192.168.1.51:5432/webdb";
```
- date_format�� �ƴ� to_char ���
```
to_char(reg_date, 'yyyy-mm-dd')
```