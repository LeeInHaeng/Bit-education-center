# Django ���

### Model ����
- ORM ����� ����ϸ�, class �ȿ��ٰ� ���� ����
- models.Model�� ��� �޴´�
```py
class Emaillist(models.Model):
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=100)
    email = models.CharField(max_length=200)

    def __str__(self):
        return f'EmailList{self.first_name}, {self.last_name}, {self.email}'
```
- �ð� ���� ��
  - auto_now=True �� �ϸ� �ش� ���� �������� �ʾƵ� �ڵ����� ���� ��¥�� ���õȴ�.
```py
joindate = models.DateTimeField(auto_now=True)
```

### Migrations �̸��� DDL python ����� ����
- ���ø����̼��� models.py �� ���ǵ� ���� �����ͺ��̽��� �ڵ����� ���� (ORM)
- admin.py ���� admin.site.register(Emaillist)
  - Emaillist�� models.py�� �𵨷� ������ Ŭ���� �̸�
```py
from emaillist.models import Emaillist

admin.site.register(Emaillist)
```
- [�͹̳�] : python manage.py makemigrations
- ���� DB�� ��Ű�� ����ȭ �۾� ����
  - [�͹̳�] : python manage.py migrate

### views���� �Ķ���� �̱�
- form �±׿��� csrf ��ū �����ϱ�
  - form �±� �ȿ� input type hidden ������ csrf token ���� ���õȴ�.
```html
<form action="/emaillist/add" method="post">{% csrf_token %}
</form>
```
- POST ���� �Ѿ�� ������ ��� (name ������)
```py
def add(request):
    firstname = request.POST['fn']
```
- GET���� �Ѿ�� ������ ��� (name ������)
```py
email = request.GET['email']
```

### views�� �⺻���� ���
- views.py ���� ORM�� �̿��� DB���� �����͸� ���� �� dictionary ���·� html�� ����
```py
def index(request):
    emaillist = Emaillist.objects.all().order_by('-id')
    data = {
        'emaillist': emaillist
    }
    return render(request, 'emaillist/index.html', data)
```
- �����̷�Ʈ�� �ϰ���� ���
```py
    return HttpResponseRedirect('/user/joinsuccess')
```
- html ���� �Ѿ�� ��ü ���
  - {% %} : �ڵ尡 ����� ��, {{ }} : ���� �ٷ� ����� ��
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
- �����ͺ��̽� ������ �״�� ǥ���ϰ� ���� ���
```html
{{ guestbook.contents | linebreaksbr }}
```

### html �⺻ Ʋ�� ����Ǵ� ���븸 ���� html�� �°� �����ϱ� (include ���� ���)
- �⺻ Ʋ�� base.html �� �����, ����Ǵ� �κи� block ó�����ش�.
  - block ���̵�, endblock ���� �����ش�.
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
- �ش� block ����ϱ�
  - �⺻ Ʋ�� base.html ��θ� extends�� ����Ѵ�
  - �ش� html���� ����Ǵ� �κ��� block ���̵� endblock���� �����Ѵ�.
```html
{% extends '../base.html' %}
 {% block csslink %}
<link href="/assets/css/main.css" rel="stylesheet" type="text/css">
{% endblock %}

         {% block content %}
		�ش� html������ ������ ����
         {% endblock %}
```

### ������ ORM ����
- �⺻ insert
```py
def join(request):
    user = User()

    user.name = request.POST['name']

    user.save()

```
- �⺻ select (���� ��)
  - Emaillist ��ü�� ��� �����͸� �������µ�, id�� ������������ �����ؼ�
```py
def index(request):
    emaillist = Emaillist.objects.all().order_by('-id')
    data = {
        'emaillist': emaillist
    }
    return render(request, 'emaillist/index.html', data)
```
- �⺻ select (�� ��)
```py
    try:
        check_email = User.objects.get(email=email)
    except User.DoesNotExist:
```
- ���� : ������ �����Ϳ� ���ؼ� delete() �޼ҵ� ����
```py
data = Guestbook.objects.get(id=no, password=password)
data.delete()
```

### Ajax ���
- GET ��� : �ܼ��ϰ� request.GET �� �̿��� ���ø����� �Ѿ�� �����͸� ��´�
  - ���Ŀ� dictionary�� �����͸� json.dumps�� ��Ƽ� ������.
```py
import json

# Ajax ����� dictionary�� ��Ƽ� ������.

# ���� return
return HttpResponse(json.dumps(data), content_type="application/json")
```
- POST ��� : �����͸� ���������� csrf ��ū�� ����� ������ �ش�
  - brforeSend �κ� ����
```js
        var ajax_data = {
            data1: 'hihi1',
            data2: 'hihi2',
            data3: 'hihi3',
         };

    		/* ajax ��� */
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
- ���Ŀ� views ���� json.loads�� ���� ajax���� �Ѿ�� �����͸� �޴´�
  - import json �ʼ�
  - ajax�� �Ѿ���� �����͸� request.body�� �ް�, json.loads�� �̿��ϸ� python�� dictionary Ÿ������ �����ͱ� ���´�.
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

### Session�� ������ ����
- views ���� ����
```py
request.session['auth_user_email'] = user_data.email
```
- views ���� ���� ������ ��������
```py
request.session['auth_user_email']
```
- templates ���� ���� �˻�
```html
{% if 'auth_user_email' not in request.session %}
{% endif %}
```
- templates ���� ���� ������ ��������
```html
{{ request.session.auth_user_email }}
```