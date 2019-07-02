### apache vhost 설정
- 도메인 기준 : 같은 포트, 다른 도메인
- 포트 기준 : 다른 포트, 다른 도메인 : 아파치 멀티 포트 서비스
  - 로컬에서 도메인이 없기 때문에 해당 방법 설정
- httpd.conf
  - Listen 포트를 추가한다
  - DocumentRoot 부분을 주석처리 한다
  - DocumentRoot 권한 설정 부분인 <Directory "~~/htdocs"> 부분을 주석처리
  - Include conf/extra/httpd-vhosts.conf 주석처리 되어 있는 부분 주석 제거
  - JkMountFile conf/uriworkermap.properties 부분을 주석 처리
- conf/extra/httpd-vhosts.conf
  - NameVirtualHost 포트 추가
  - VirtualHost 포트별로 설정
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
- JKMountFile 설정을 httpd.conf가 아닌 vhosts.conf에 셋팅
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

### apache django 연동하기
- mod_wsgi 설치
  - wget https://github.com/GrahamDumpleton/mod_wsgi/archive/4.6.4.tar.gz
- tar xvfz 4.6.4.tar.gz
- ./configure --with-apxs=/usr/local/cafe24/apache/bin/apxs --with-python=/usr/local/cafe24/python3.7.3/bin/python3
- make && make install
- httpd.conf 에서 모듈 로드
```
LoadModule wsgi_module modules/mod_wsgi.so
```
- extra/httpd-vhosts.conf 에서 설정
  - WSGIScriptAlias와 WSGIDaemonProcess 설정
  - Directory 부분에는 wsgi.py 설정
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
