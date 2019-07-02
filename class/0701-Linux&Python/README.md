# Linux(CentOS 6)에서 Python 환경설정

### 필요 라이브러리 다운
- yum -y install openssl
- yum -y install openssl-devel
- yum -y install bzip2-devel
- yum -y install sqlite-devel
- yum -y install zlib-devel
- yum -y install libffi-devel
- 파이썬 버전 3.7 이상 버전에서는 libressl 2.6.4 이상의 버전이 필요

### libressl 설치
- wget https://ftp.openbsd.org/pub/OpenBSD/LibreSSL/libressl-2.9.2.tar.gz
- tar xvfz libressl-2.9.2.tar.gz
- ./configure --prefix=/usr/local/ssl
  - prefix를 이용해 해당 경로에 설치
- make && make install
- vi /etc/ld.so.conf.d/ssl.conf
```
/usr/local/ssl/lib
```
- ldconfig -v | grep ssl
  - 아래의 화면이 보이면 정상
```
/usr/local/ssl/lib:
	libssl.so.47 -> libssl.so.47.0.5
	libssl3.so -> libssl3.so
	libssl.so.10 -> libssl.so.1.0.1e
```
- ldconfig -v | grep pg

### Python3 설치
- wget https://www.python.org/ftp/python/3.7.3/Python-3.7.3.tgz
- tar xvfz Python-3.7.3.tgz
- ./configure --prefix=/usr/local/cafe24/python3.7.3 --with-openssl=/usr/local/ssl --enable-shared
- make && make install
- vi /etc/ld.so.conf.d/python.conf
- ldconfig -v | grep python
  - 아래의 화면이 보이면 정상
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
- python3 --version 을 통해 환경 변수가 제대로 잡혔는지 확인

### Python Virtual 환경
- venv : python3.3 버전 이후 부터 기본 모듈
- virtualenv : python2 부터 사용해오던 가상 환경 라이브러리 (해당 라이브러리 설치)
- pyenv : Python Interpretor Version Manager
- conda : Anaconda Python 설치 했을 때 사용할 수 있다
- pip3 install virtualenv
- 프로젝트 생성
  - mkdir 프로젝트명
  - cd 프로젝트명
  - virtualenv venv
  - 가상환경 들어가기 : source venv/bin/activate
  - 가상환경 나오기 : deactivate
- postgresql와 centos버전 에러 해결 방법
  - cd /usr/lib64
  - rm -f libpq.so.5
  - ln -s /usr/local/cafe24/pgsql/lib/libpq.so.5 libpq.so.5

### Pycharm 에서 올린 프로젝트를 리눅스에서 실행
- pycharm 에서 프로젝트 생성
- pycharm에서 github commit 
  - venv 및 프로젝트 설정 파일 ignore (.idea 폴더와 venv 폴더)
- 패키지 의존성 파일 생성
  - pycharm에서 pip freeze > requirements.txt
  - 해당 txt 파일 github에 commit
- Linux에서 git clone으로 프로젝트 받아오기
- virtualenv venv 명령어를 통해 해당 프로젝트의 가상 환경 생성
- 가상환경 들어가기 : source venv/bin/activate
- 의존성 설치
  - pip install -r requirements.txt
- venv 환경이 아닌 상태에서 수행하는 방법
  - venv 환경은 개발 환경이다.
  - 즉, github에서 다른 사람이 올려 놓은 파이썬 프로젝트를 가져와서 수행하기 위해서는 가상 환경으로 실행하는 것이 아닌 단순 python 명령어로 실행이 가능하도록 해야 한다.
  - 수동으로 하기 : export PYTHONPATH='/root/dowork/python-projects/프로젝트명/venv/lib/python3.7/site-packages/'
  - 매번 수동으로 프로젝트 라이브러리의 경로를 잡아주는 것은 불가능
  - venv 아닌 환경에서 라이브러리 풀기 : pip3 install -r python_crawling/requirements.txt --target=python_crawling
  
### 파이썬 프로젝트 압축하기 (패키징)
- 파이썬 프로젝트 git에서 clone
- venv 아닌 환경에서 라이브러리 풀기 : pip3 install -r python_crawling/requirements.txt --target=python_crawling
- python3 -m zipapp python_crawling
  - pyz 파일이 생성된다
- python3 ~~.pyz 로 파이썬 파일 수행