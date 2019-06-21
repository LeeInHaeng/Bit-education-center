# Django 사용

### Model 정의
- ORM 방식을 사용하며, class 안에다가 모델을 정의
- models.Model을 상속 받는다
```py
class Emaillist(models.Model):
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=100)
    email = models.CharField(max_length=200)

    def __str__(self):
        return f'EmailList{self.first_name}, {self.last_name}, {self.email}'
```
- 시간 관련 모델
  - auto_now=True 로 하면 해당 값을 셋팅하지 않아도 자동으로 현재 날짜로 셋팅된다.
```py
joindate = models.DateTimeField(auto_now=True)
```
- Join을 위한 외래키 설정
  - 한 명의 사용자가 여러 개의 board
```py
class Board(models.Model):
    ...
    user = models.ForeignKey(User, to_field='id', on_delete=models.CASCADE)
```

### Migrations 이름의 DDL python 모듈을 생성
- 어플리케이션의 models.py 에 정의된 모델을 데이터베이스에 자동으로 생성 (ORM)
- admin.py 에서 admin.site.register(Emaillist)
  - Emaillist는 models.py에 모델로 정의한 클래스 이름
```py
from emaillist.models import Emaillist

admin.site.register(Emaillist)
```
- [터미널] : python manage.py makemigrations
- 물리 DB와 스키마 동기화 작업 수행
  - [터미널] : python manage.py migrate

### views에서 파라미터 뽑기
- form 태그에서 csrf 토큰 생성하기
  - form 태그 안에 input type hidden 값으로 csrf token 값이 셋팅된다.
```html
<form action="/emaillist/add" method="post">{% csrf_token %}
</form>
```
- POST 에서 넘어온 데이터 얻기 (name 값으로)
```py
def add(request):
    firstname = request.POST['fn']
```
- GET으로 넘어온 데이터 얻기 (name 값으로)
```py
email = request.GET['email']
```

### views와 기본적인 통신
- views.py 에서 ORM을 이용해 DB에서 데이터를 얻어온 후 dictionary 형태로 html에 전달
```py
def index(request):
    emaillist = Emaillist.objects.all().order_by('-id')
    data = {
        'emaillist': emaillist
    }
    return render(request, 'emaillist/index.html', data)
```
- 리다이렉트를 하고싶은 경우
```py
    return HttpResponseRedirect('/user/joinsuccess')
```
- html 에서 넘어온 객체 사용
  - {% %} : 코드가 수행될 때, {{ }} : 값을 바로 사용할 때
```html
	{% for email in emaillist %}
	<table border="1" cellpadding="5" cellspacing="2">
		<tr>
			<td align=right>First name: </td>
			<td>{{ email.first_name }}</td>
		</tr>
		<tr>
			<td align=right width="110">Last name: </td>
			<td width="110">{{ email.last_name }}</td>
		</tr>
		<tr>
			<td align=right>Email address: </td>
			<td>{{ email.email }}</td>
		</tr>
	</table>
	{% endfor %}
```
- 데이터베이스 개행을 그대로 표현하고 싶은 경우
```html
{{ guestbook.contents | linebreaksbr }}
```
- html에서 GET 데이터 얻기
```html
{% if request.GET.result == 'fail' %}
```

### html 기본 틀에 변경되는 내용만 각각 html에 맞게 변경하기 (include 같은 기능)
- 기본 틀인 base.html 을 만들고, 변경되는 부분만 block 처리해준다.
  - block 아이디, endblock 으로 묶어준다.
```html
<meta http-equiv="content-type" content="text/html; charset=utf-8">
     {% block csslink %} {% endblock %}
</head>


...

      <div id="wrapper">
         <div id="content">

                {% block content %}
                {% endblock %}

         </div>
      </div>
```
- 해당 block 사용하기
  - 기본 틀인 base.html 경로를 extends를 사용한다
  - 해당 html에서 변경되는 부분을 block 아이디 endblock으로 셋팅한다.
```html
{% extends '../base.html' %}
 {% block csslink %}
<link href="/assets/css/main.css" rel="stylesheet" type="text/css">
{% endblock %}

         {% block content %}
		해당 html에서만 보여줄 내용
         {% endblock %}
```

### 간단한 ORM 예제
- 기본 insert
```py
def join(request):
    user = User()

    user.name = request.POST['name']

    user.save()

```
- 기본 select (여러 개)
  - Emaillist 객체의 모든 데이터를 가져오는데, id를 내림차순으로 정렬해서
```py
def index(request):
    emaillist = Emaillist.objects.all().order_by('-id')
    data = {
        'emaillist': emaillist
    }
    return render(request, 'emaillist/index.html', data)
```
- 기본 select (한 개)
```py
    try:
        check_email = User.objects.get(email=email)
    except User.DoesNotExist:
```
- 기본 select (한 개)
```py
    results = User.objects.filter(email=email) \
                .filter(password=password)
    if len(results) == 0:
        # 쿼리 결과가 없을 경우
```
- 삭제 : 가져온 데이터에 대해서 delete() 메소드 수행
```py
data = Guestbook.objects.get(id=no, password=password)
data.delete()
```

### Ajax 통신
- GET 방식 : 단순하게 request.GET 을 이용해 템플릿에서 넘어온 데이터를 얻는다
  - 이후에 dictionary의 데이터를 json.dumps에 담아서 보낸다.
```py
import json

# Ajax 결과를 dictionary에 담아서 보낸다.

# 이후 return
return HttpResponse(json.dumps(data), content_type="application/json")
```
- POST 방식 : 데이터를 보내기전에 csrf 토큰을 헤더에 셋팅해 준다
  - brforeSend 부분 참고
```js
        var ajax_data = {
            data1: 'hihi1',
            data2: 'hihi2',
            data3: 'hihi3',
         };

    		/* ajax 통신 */
			$.ajax({
				url: "/ajaxtest",
				type: "post",
				dataType: "json",
				data: JSON.stringify(ajax_data),
				beforeSend: function(xhr){
				    xhr.setRequestHeader("X-CSRFToken", '{{ csrf_token }}');
				},
				success: function(response){
				    console.log(response);
				},
				error: function(xhr, error){
					console.error("error:" + error)
				}
			});
```
- 이후에 views 에서 json.loads를 통해 ajax에서 넘어온 데이터를 받는다
  - import json 필수
  - ajax로 넘어오는 데이터를 request.body로 받고, json.loads를 이용하면 python의 dictionary 타입으로 데이터그 들어온다.
```py
def ajaxtest(request):
    if request.method == 'POST':
        ajax_data = json.loads(request.body.decode(encoding='utf-8'))
        print(ajax_data)
        ajax_data['data1'] = ajax_data['data1'] + '_success'
        ajax_data['data2'] = ajax_data['data2'] + '_success'
        ajax_data['data3'] = ajax_data['data3'] + '_success'
    return HttpResponse(json.dumps(ajax_data), content_type="application/json")
```
- python의 dictionary를 JsonResponse를 이용해 json 타입으로 리턴
```py
    jsonresult = {
        'result': 'success',
        'data': ['hello', 1, 2, True, ('a', 'b', 'c')]
    }

    return JsonResponse(jsonresult)
```
- JsonResponse와 model_to_dic를 이용해 객체 자체를 넘기기
```py
    try:
        user = User.objects.get(email=email)
    except Exception as e:
        user = None
    result = {
        'result': 'success',
        'data': 'exist' if user is None else 'not exist'
    }
    
    return JsonResponse(result)
```

### Session 사용
- views 에서 단일 데이터 저장
```py
request.session['auth_user_email'] = user_data.email
```
- views 에서 객체로 저장 : model_to_dict 사용
```py
request.session['authuser'] = model_to_dict(authuser)
```
- views 에서 세션 데이터 가져오기
```py
request.session['authuser']
```
- templates 에서 세션 검사
```html
{% if 'authuser' not in request.session %}
{% endif %}
```
- templates 에서 세션 데이터 가져오기
```html
{{ request.session.authuser.name }}
```
- 세션 파기
  - views 에서 del 을 이용한다
  - settings.py 에서 SESSION_EXPIRE_AT_BROWSER_CLOSE 를 True 로 설정한다
```py
del request.session['authuser']
SESSION_EXPIRE_AT_BROWSER_CLOSE = True
SESSION_SAVE_EVERY_REQUEST = True # 세션의 일부분만 변경하기 위한 옵션
```
- 세션의 일부분만 변경
```py
request.session['authuser']['name'] = user.name
```