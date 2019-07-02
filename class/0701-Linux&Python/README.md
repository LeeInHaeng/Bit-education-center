# Linux(CentOS 6)���� Python ȯ�漳��

### �ʿ� ���̺귯�� �ٿ�
- yum -y install openssl
- yum -y install openssl-devel
- yum -y install bzip2-devel
- yum -y install sqlite-devel
- yum -y install zlib-devel
- yum -y install libffi-devel
- ���̽� ���� 3.7 �̻� ���������� libressl 2.6.4 �̻��� ������ �ʿ�

### libressl ��ġ
- wget https://ftp.openbsd.org/pub/OpenBSD/LibreSSL/libressl-2.9.2.tar.gz
- tar xvfz libressl-2.9.2.tar.gz
- ./configure --prefix=/usr/local/ssl
  - prefix�� �̿��� �ش� ��ο� ��ġ
- make && make install
- vi /etc/ld.so.conf.d/ssl.conf
```
/usr/local/ssl/lib
```
- ldconfig -v | grep ssl
  - �Ʒ��� ȭ���� ���̸� ����
```
/usr/local/ssl/lib:
	libssl.so.47 -> libssl.so.47.0.5
	libssl3.so -> libssl3.so
	libssl.so.10 -> libssl.so.1.0.1e
```
- ldconfig -v | grep pg

### Python3 ��ġ
- wget https://www.python.org/ftp/python/3.7.3/Python-3.7.3.tgz
- tar xvfz Python-3.7.3.tgz
- ./configure --prefix=/usr/local/cafe24/python3.7.3 --with-openssl=/usr/local/ssl --enable-shared
- make && make install
- vi /etc/ld.so.conf.d/python.conf
- ldconfig -v | grep python
  - �Ʒ��� ȭ���� ���̸� ����
```
/usr/local/cafe24/python3.7.3/lib:
	libpython3.so -> libpython3.so
	libpython3.7m.so.1.0 -> libpython3.7m.so.1.0
	libpython2.6.so.1.0 -> libpython2.6.so.1.0
```
- vi /etc/profile
```
export PATH=$PATH:/usr/local/cafe24/python3.7.3/bin
```
- source /etc/profile
- python3 --version �� ���� ȯ�� ������ ����� �������� Ȯ��

### Python Virtual ȯ��
- venv : python3.3 ���� ���� ���� �⺻ ���
- virtualenv : python2 ���� ����ؿ��� ���� ȯ�� ���̺귯�� (�ش� ���̺귯�� ��ġ)
- pyenv : Python Interpretor Version Manager
- conda : Anaconda Python ��ġ ���� �� ����� �� �ִ�
- pip3 install virtualenv
- ������Ʈ ����
  - mkdir ������Ʈ��
  - cd ������Ʈ��
  - virtualenv venv
  - ����ȯ�� ���� : source venv/bin/activate
  - ����ȯ�� ������ : deactivate
- postgresql�� centos���� ���� �ذ� ���
  - cd /usr/lib64
  - rm -f libpq.so.5
  - ln -s /usr/local/cafe24/pgsql/lib/libpq.so.5 libpq.so.5

### Pycharm ���� �ø� ������Ʈ�� ���������� ����
- pycharm ���� ������Ʈ ����
- pycharm���� github commit 
  - venv �� ������Ʈ ���� ���� ignore (.idea ������ venv ����)
- ��Ű�� ������ ���� ����
  - pycharm���� pip freeze > requirements.txt
  - �ش� txt ���� github�� commit
- Linux���� git clone���� ������Ʈ �޾ƿ���
- virtualenv venv ��ɾ ���� �ش� ������Ʈ�� ���� ȯ�� ����
- ����ȯ�� ���� : source venv/bin/activate
- ������ ��ġ
  - pip install -r requirements.txt
- venv ȯ���� �ƴ� ���¿��� �����ϴ� ���
  - venv ȯ���� ���� ȯ���̴�.
  - ��, github���� �ٸ� ����� �÷� ���� ���̽� ������Ʈ�� �����ͼ� �����ϱ� ���ؼ��� ���� ȯ������ �����ϴ� ���� �ƴ� �ܼ� python ��ɾ�� ������ �����ϵ��� �ؾ� �Ѵ�.
  - �������� �ϱ� : export PYTHONPATH='/root/dowork/python-projects/������Ʈ��/venv/lib/python3.7/site-packages/'
  - �Ź� �������� ������Ʈ ���̺귯���� ��θ� ����ִ� ���� �Ұ���
  - venv �ƴ� ȯ�濡�� ���̺귯�� Ǯ�� : pip3 install -r python_crawling/requirements.txt --target=python_crawling
  
### ���̽� ������Ʈ �����ϱ� (��Ű¡)
- ���̽� ������Ʈ git���� clone
- venv �ƴ� ȯ�濡�� ���̺귯�� Ǯ�� : pip3 install -r python_crawling/requirements.txt --target=python_crawling
- python3 -m zipapp python_crawling
  - pyz ������ �����ȴ�
- python3 ~~.pyz �� ���̽� ���� ����