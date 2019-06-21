### Django 템플릿 언어
- 필터 : 필터는 연쇄적으로 사용하여 필터의 결과가 다음 필터에 적용되도록 할 수 있다.
  - guestbook_list의 길이를 얻고 싶은 경우
  - 내용의 개행을 br로 표시하고 싶은 경우
  - 덧셈, 뺄셈이 필요한 경우
  - 날짜 관련 필터
```html
{{ guestbook_list | length }}

{{ guestbook.contents | linebreaksbr }}

{{ guestbook_list | length | add:1 }}
{{ guestbook_list | length | add:-5 }}

<td>{{ guestbook.reg_date | date:'Y-m-d H:i:s' }}</td>
```
- 템플릿에서 math와 관련된 기능들 사용
  - pip install django-mathfilters
  - settings.py 의 INSTALLED_APPS 에 추가 'mathfilters',
  - 사용할 html에 {% load mathfilters %} 추가 후 사용

- forloop 관련 
  - counter : 1부터 숫자 시작
  - counter0 : 0부터 숫자 시작
  - first : 첫번째만 True 나머지는 False 반환
  - revcounter : 거꾸로 숫자 시작 ---> 3, 2, 1
```html
<td>{{ forloop.counter }} : {{ forloop.counter0 }} : {{ forloop.first }}</td>
{{ forloop.revcounter }}
```