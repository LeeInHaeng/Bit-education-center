### apache vhost ����
- ������ ���� : ���� ��Ʈ, �ٸ� ������
- ��Ʈ ���� : �ٸ� ��Ʈ, �ٸ� ������ : ����ġ ��Ƽ ��Ʈ ����
  - ���ÿ��� �������� ���� ������ �ش� ��� ����
- httpd.conf
  - Listen ��Ʈ�� �߰��Ѵ�
  - DocumentRoot �κ��� �ּ�ó�� �Ѵ�
  - DocumentRoot ���� ���� �κ��� <Directory "~~/htdocs"> �κ��� �ּ�ó��
  - Include conf/extra/httpd-vhosts.conf �ּ�ó�� �Ǿ� �ִ� �κ� �ּ� ����
  - JkMountFile conf/uriworkermap.properties �κ��� �ּ� ó��
- conf/extra/httpd-vhosts.conf
  - NameVirtualHost ��Ʈ �߰�
  - VirtualHost ��Ʈ���� ����
```
<VirtualHost *:80>
    ServerAdmin tmdwlgk0109@naver.com
    DocumentRoot "/usr/local/cafe24/apache/htdocs"
   # ServerName dummy-host.example.com
   # ServerAlias www.dummy-host.example.com
    ErrorLog "logs/localhost.80-error_log"
    CustomLog "logs/localhost.80-access_log" common
        <Directory "/usr/local/cafe24/apache/htdocs">
            Options Indexes FollowSymLinks
            AllowOverride None
            Order allow,deny
            Allow from all
        </Directory>

</VirtualHost>

<VirtualHost *:8888>
    ServerAdmin tmdwlgk0109@daum.net
    DocumentRoot "/home/django/python_ch3"
    ErrorLog "logs/localhost.8888-error_log"
    CustomLog "logs/localhost.8888-access_log" common
        <Directory "/home/django/python_ch3">
            Options Indexes FollowSymLinks
            AllowOverride None
            Order allow,deny
            Allow from all
        </Directory>
</VirtualHost>
```
- JKMountFile ������ httpd.conf�� �ƴ� vhosts.conf�� ����
```
<VirtualHost *:80>
    ServerAdmin tmdwlgk0109@naver.com
    DocumentRoot "/usr/local/cafe24/apache/htdocs"
   # ServerName dummy-host.example.com
   # ServerAlias www.dummy-host.example.com
    ErrorLog "logs/localhost.80-error_log"
    CustomLog "logs/localhost.80-access_log" common
        <Directory "/usr/local/cafe24/apache/htdocs">
            Options Indexes FollowSymLinks
            AllowOverride None
            Order allow,deny
            Allow from all
        </Directory>
        JkMountFile conf/uriworkermap.properties
</VirtualHost>
```

### apache django �����ϱ�
- mod_wsgi ��ġ
  - wget https://github.com/GrahamDumpleton/mod_wsgi/archive/4.6.4.tar.gz
- tar xvfz 4.6.4.tar.gz
- ./configure --with-apxs=/usr/local/cafe24/apache/bin/apxs --with-python=/usr/local/cafe24/python3.7.3/bin/python3
- make && make install
- httpd.conf ���� ��� �ε�
```
LoadModule wsgi_module modules/mod_wsgi.so
```
- extra/httpd-vhosts.conf ���� ����
  - WSGIScriptAlias�� WSGIDaemonProcess ����
  - Directory �κп��� wsgi.py ����
```
<VirtualHost *:8888>
    ServerAdmin tmdwlgk0109@daum.net
    DocumentRoot "/home/django/python_ch3"
    ErrorLog "logs/localhost.8888-error_log"
    CustomLog "logs/localhost.8888-access_log" common
        
    WSGIScriptAlias / /home/django/python_ch3/python_chap03/wsgi.py
    WSGIDaemonProcess python_chap03 python-path=/home/django/python_ch3/python_chap03

        <Directory "/home/django/python_ch3">
            <Files wsgi.py>
                Options Indexes FollowSymLinks
                AllowOverride None
                Order allow,deny
                Allow from all
            </Files>
        </Directory>
</VirtualHost>
```
