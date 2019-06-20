# BeautifulSoup과 Selenium 크롤링
- https://github.com/LeeInHaeng/python_crawling

# Python에서 Postgres 사용
- 드라이버 설치 (Psycopg2)
  - pip install psycopg2
- 해당 드라이버로 기본적인 연결
```py
import psycopg2

try:
    conn = psycopg2.connect(
        user='webdb',
        password='webdb',
        host='192.168.1.51',
        port='5432',
        database='webdb'
    )
except Exception as e:
    print(f'error: {e}')
finally:
    'conn' in locals() and conn and conn.close()
```
- config를 dictionary 형태로 미리 설정
```py
db = {
    'user': 'webdb',
    'password': 'webdb',
    'host': '192.168.1.51',
    'port': '5432',
    'database': 'webdb'
}
```
- 기본적인 insert 구문 (직접 쿼리 수행)
  - cursor을 사용해 execute로 직접 쿼리를 날리는 방법
```py
def test_insert():
    try:
        conn = psycopg2.connect(**config.db)

        cursor = conn.cursor()
        cursor.execute("insert into pet values('성탄이','대혁이','dog','m','2005-12-31',null)")
    except Exception as e:
        print(f'error: {e}')
    finally:
        'cursor' in locals() and cursor and cursor.close()
        'conn' in locals() and conn and (conn.commit() or conn.close())
```

- 기본적인 select 구문 (직접 쿼리 수행)
  - 결과가 하나일 경우에는 fetchone()을 사용하고 여러개일 경우 fetchall()을 사용한다.
```py
def test_select():
    try:
        conn = psycopg2.connect(**config.db)

        cursor = conn.cursor()
        cursor.execute("select * from pet")
        records = cursor.fetchall()

        for record in records:
            print(record, type(record))

    except Exception as e:
        print(f'error: {e}')
    finally:
        'cursor' in locals() and cursor and cursor.close()
        'conn' in locals() and conn and (conn.commit() or conn.close())
```

# Django 프로젝트 시작 (pycharm community 버전 에서)

### 프로젝트 셋팅 전에 DB 및 계정 생성
- psql -U postgres
- create database djdb
- create user djdb with password 'djdb';
- grant all privileges on all tables in schema public to djdb;
- data/pg_hba.conf 파일 설정
```
# "local" is for Unix domain socket connections only
local   djdb            djdb                                    password

# IPv4 local connections:
host    djdb            djdb            192.168.1.0/24          password
host    djdb            djdb            127.0.0.1/32            password
```

### Django 프로젝트 시작
- pycharm 프로젝트 생성 (일반적인 python 프로젝트)
- Django 설치 (venv 환경에서 수행)
  - [터미널] : pip install django
- 장고 프로젝트 생성
  - [터미널] : django-admin startproject python_ch3(프로젝트 이름)
- 디렉토리 정리
  - pycharm 프로젝트와 django 프로젝트의 디렉토리를 일치시키는 작업
  - django 프로젝트의 manage.py를 맨 위로 (pycharm 프로젝트로 이동)
  - init, settings, urls, wsgi를 하나 위의 디렉토리로 이동
  - 기존에 위의 4개 파일이 있던 디렉토리를 삭제
- Psycopg2 설치 (postgre 사용을 위한 라이브러리)
  - pip install psycopg2
- settings.py 설정
  - 지역 설정, 기본 데이터베이스 설정, 템플릿 디렉토리 설정
```py
TIME_ZONE = 'Asia/Seoul'

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': 'djdb',
	'USER': 'djdb',
	'PASSWORD': 'djdb',
	'HOST': '192.168.1.51',
	'PORT': '5432'
    }
}

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [os.path.join(BASE_DIR, 'templates')],

	...
```
- pycharm 프로젝트 있는 곳(최상위 : manage.py 와 같은 위치)의 최상위 위치에 templates 디렉토리 생성
- 장고 프로젝트의 기본 관리 어플리케이션이 사용하는 테이블을 생성
  - [터미널] : python manage.py migrate
  - djdb 데이터베이스를 살펴보면 장고에서 기본적으로 사용하는 테이블들이 생성된다.
- 장고 프로젝트의 기본 관리 어플리케이션 로그인 계정 생성하기 (관리자 계정 생성)
  - [터미널] : python manage.py createsuperuser
- 지금까지 작업 내용 확인하기 (서버 실행)
  - [터미널] : python manage.py runserver 0.0.0.0:8888
  - localhost:8888 접속하기
  - http://127.0.0.1:8888/admin 에서 등록한 관리자 계정 로그인 해보기

### Application 작업
- 어플리케이션 추가
  - [터미널] : python manage.py startapp helloworld
- 어플리케이션 등록 (settings.py)
```py
INSTALLED_APPS = [
    'helloworld',
	...
```
- urls.py 에서 어플리케이션에 접속하는 URL 셋팅
```py

import helloworld.views as helloworld_views

urlpatterns = [
    path('helloworld/', helloworld_views.hello),
    path('admin/', admin.site.urls)
]
```
- helloworld 디렉토리의 views.py 에서 hello 메소드 생성
```py
def hello(request):
    return render(request, 'helloworld/hello.html')
```
- templates 디렉토리에서 helloworld 디렉토리 생성 후 hello.html 파일 생성
- static 경로 지정 (settings.py)
  - DIRS 부분은 실제 디렉토리가 있는 경로 (튜플 타입이고 마지막에 ,가 들어간다.)
  - URL은 href로 static 디렉토리에 접근하기 위한 경로
  - ex) <link href="/assets/css/main.css" rel="stylesheet" type="text/css">
```py
STATICFILES_DIRS=(os.path.join(BASE_DIR,'statics'),)
STATIC_URL = '/assets/'
```