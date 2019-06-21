### Django ���ø� ���
- ���� : ���ʹ� ���������� ����Ͽ� ������ ����� ���� ���Ϳ� ����ǵ��� �� �� �ִ�.
  - guestbook_list�� ���̸� ��� ���� ���
  - ������ ������ br�� ǥ���ϰ� ���� ���
  - ����, ������ �ʿ��� ���
  - ��¥ ���� ����
```html
{{ guestbook_list | length }}

{{ guestbook.contents | linebreaksbr }}

{{ guestbook_list | length | add:1 }}
{{ guestbook_list | length | add:-5 }}

<td>{{ guestbook.reg_date | date:'Y-m-d H:i:s' }}</td>
```
- ���ø����� math�� ���õ� ��ɵ� ���
  - pip install django-mathfilters
  - settings.py �� INSTALLED_APPS �� �߰� 'mathfilters',
  - ����� html�� {% load mathfilters %} �߰� �� ���

- forloop ���� 
  - counter : 1���� ���� ����
  - counter0 : 0���� ���� ����
  - first : ù��°�� True �������� False ��ȯ
  - revcounter : �Ųٷ� ���� ���� ---> 3, 2, 1
```html
<td>{{ forloop.counter }} : {{ forloop.counter0 }} : {{ forloop.first }}</td>
{{ forloop.revcounter }}
```