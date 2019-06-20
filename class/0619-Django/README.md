# BeautifulSoup�� Selenium ũ�Ѹ�
- https://github.com/LeeInHaeng/python_crawling

# Python���� Postgres ���
- ����̹� ��ġ (Psycopg2)
  - pip install psycopg2
- �ش� ����̹��� �⺻���� ����
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
- config�� dictionary ���·� �̸� ����
```py
db = {
    'user': 'webdb',
    'password': 'webdb',
    'host': '192.168.1.51',
    'port': '5432',
    'database': 'webdb'
}
```
- �⺻���� insert ���� (���� ���� ����)
  - cursor�� ����� execute�� ���� ������ ������ ���
```py
def test_insert():
    try:
        conn = psycopg2.connect(**config.db)

        cursor = conn.cursor()
        cursor.execute("insert into pet values('��ź��','������','dog','m','2005-12-31',null)")
    except Exception as e:
        print(f'error: {e}')
    finally:
        'cursor' in locals() and cursor and cursor.close()
        'conn' in locals() and conn and (conn.commit() or conn.close())
```

- �⺻���� select ���� (���� ���� ����)
  - ����� �ϳ��� ��쿡�� fetchone()�� ����ϰ� �������� ��� fetchall()�� ����Ѵ�.
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

# Django ������Ʈ ���� (pycharm community ���� ����)

### ������Ʈ ���� ���� DB �� ���� ����
- psql -U postgres
- create database djdb
- create user djdb with password 'djdb';
- grant all privileges on all tables in schema public to djdb;
- data/pg_hba.conf ���� ����
```
# "local" is for Unix domain socket connections only
local   djdb            djdb                                    password

# IPv4 local connections:
host    djdb            djdb            192.168.1.0/24          password
host    djdb            djdb            127.0.0.1/32            password
```

### Django ������Ʈ ����
- pycharm ������Ʈ ���� (�Ϲ����� python ������Ʈ)
- Django ��ġ (venv ȯ�濡�� ����)
  - [�͹̳�] : pip install django
- ��� ������Ʈ ����
  - [�͹̳�] : django-admin startproject python_ch3(������Ʈ �̸�)
- ���丮 ����
  - pycharm ������Ʈ�� django ������Ʈ�� ���丮�� ��ġ��Ű�� �۾�
  - django ������Ʈ�� manage.py�� �� ���� (pycharm ������Ʈ�� �̵�)
  - init, settings, urls, wsgi�� �ϳ� ���� ���丮�� �̵�
  - ������ ���� 4�� ������ �ִ� ���丮�� ����
- Psycopg2 ��ġ (postgre ����� ���� ���̺귯��)
  - pip install psycopg2
- settings.py ����
  - ���� ����, �⺻ �����ͺ��̽� ����, ���ø� ���丮 ����
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
- pycharm ������Ʈ �ִ� ��(�ֻ��� : manage.py �� ���� ��ġ)�� �ֻ��� ��ġ�� templates ���丮 ����
- ��� ������Ʈ�� �⺻ ���� ���ø����̼��� ����ϴ� ���̺��� ����
  - [�͹̳�] : python manage.py migrate
  - djdb �����ͺ��̽��� ���캸�� ����� �⺻������ ����ϴ� ���̺���� �����ȴ�.
- ��� ������Ʈ�� �⺻ ���� ���ø����̼� �α��� ���� �����ϱ� (������ ���� ����)
  - [�͹̳�] : python manage.py createsuperuser
- ���ݱ��� �۾� ���� Ȯ���ϱ� (���� ����)
  - [�͹̳�] : python manage.py runserver 0.0.0.0:8888
  - localhost:8888 �����ϱ�
  - http://127.0.0.1:8888/admin ���� ����� ������ ���� �α��� �غ���

### Application �۾�
- ���ø����̼� �߰�
  - [�͹̳�] : python manage.py startapp helloworld
- ���ø����̼� ��� (settings.py)
```py
INSTALLED_APPS = [
    'helloworld',
	...
```
- urls.py ���� ���ø����̼ǿ� �����ϴ� URL ����
```py

import helloworld.views as helloworld_views

urlpatterns = [
    path('helloworld/', helloworld_views.hello),
    path('admin/', admin.site.urls)
]
```
- helloworld ���丮�� views.py ���� hello �޼ҵ� ����
```py
def hello(request):
    return render(request, 'helloworld/hello.html')
```
- templates ���丮���� helloworld ���丮 ���� �� hello.html ���� ����
- static ��� ���� (settings.py)
  - DIRS �κ��� ���� ���丮�� �ִ� ��� (Ʃ�� Ÿ���̰� �������� ,�� ����.)
  - URL�� href�� static ���丮�� �����ϱ� ���� ���
  - ex) <link href="/assets/css/main.css" rel="stylesheet" type="text/css">
```py
STATICFILES_DIRS=(os.path.join(BASE_DIR,'statics'),)
STATIC_URL = '/assets/'
```