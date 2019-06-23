# Django ���ø� ���
### ���� : ���ʹ� ���������� ����Ͽ� ������ ����� ���� ���Ϳ� ����ǵ��� �� �� �ִ�.
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
### ���ø����� math�� ���õ� ��ɵ� ���
- pip install django-mathfilters
- settings.py �� INSTALLED_APPS �� �߰� 'mathfilters',
- ����� html�� {% load mathfilters %} �߰� �� ���

### forloop ���� 
- counter : 1���� ���� ����
- counter0 : 0���� ���� ����
- first : ù��°�� True �������� False ��ȯ
- revcounter : �Ųٷ� ���� ���� ---> 3, 2, 1
```html
<td>{{ forloop.counter }} : {{ forloop.counter0 }} : {{ forloop.first }}</td>
{{ forloop.revcounter }}
```

# ORM ��ȭ
### aggregate
- group by�� ���� count, max, first ���� ����ϱ� ���� ���
```py
max_groupno = Board.objects.aggregate(max_groupno=Max('groupno'))
board.groupno = 0 if max_groupno["max_groupno"] is None else max_groupno["max_groupno"]+1
```
### F �Լ�
- ������ �ϴ� �ڽ��� ���� ����
- orderno�� board.orderno���� ū ���鿡 ���ؼ� �ڽ��� orderno�� +1�� �Ͽ� ������Ʈ
```py
Board.objects.filter(groupno=board.groupno).filter(orderno__gt=board.orderno).update(orderno=F('orderno')+1)
```
### Q �Լ�
- filter���� �ٸ� �÷��� ���� OR, AND, NOT ������ ����ϱ� ���� �Լ�
- contains : sql���� like�� ���� �ǹ�
```py
    board_list_all = Board.objects.filter(
        Q(title__contains=search) |
        Q(content__contains=search)).order_by('-groupno', 'orderno')
```

# Page ó��
- select ���� ����� ���� list�� Paginator�� ù ��° ���ڷ� �־��ش�.
  - �� ��° ���ڴ� �� �������� ������ �Խñ��� ��
```py
paginator = Paginator(board_list_all, 5)
```
- ������ ���� �� ���� ó��
  - page ���� : paginator �� page �޼ҵ带 ȣ��
  - PageNotAnInteger : page�� ���� ��Ʈ Ÿ���� �ƴ� ���
  - EmptyPage : page�� paginator.page_range �� ���� ���� ���� ���
  - num_pages �� �� ������ �������� �ǹ�
```py
    try:
        board_list_page = paginator.page(page)
    except PageNotAnInteger:
        board_list_page = paginator.page(1)
    except EmptyPage:
        board_list_page = paginator.page(paginator.num_pages)
```
- template���� ���
  - has_previous : ���� �������� �����ϴ��� ���� True, False, previous_page_number ���
  - paginator.page_range : �������� ����
  - has_next : ���� �������� �����ϴ��� ���� True, False, next_page_number ���
```html
				<div class="pager">
					<ul>
						{% if board_list.has_previous %}
							<li><a href="?search={{request.GET.search}}&page={{board_list.previous_page_number}}">��</a></li>
						{% endif %}
						{% for i in board_list.paginator.page_range %}
							{% if board_list.number == i %}
								<li class="selected">{{ i }}</li>
							{% else %}
								<li><a href="?search={{request.GET.search}}&page={{i}}">{{ i }}</a></li>
						      {% endif %}
    					{% endfor %}
						{% if board_list.has_next %}
							<li><a href="?search={{request.GET.search}}&page={{ board_list.next_page_number }}">��</a></li>
						{% endif %}
					</ul>
				</div>
```

### page_range�� ������
- ���� �������� 1 ���������� 500 ���������� �ִٰ� �ϸ� 1~500 ���������� �� ������ �ȴ�
- ��, < 1~5 >  /  < 6~ 10 > ������ ����¡ ó���� �Ұ����ϴ�
- �̸� �ذ��ϱ� ���� page_range�� Ŀ���͸���¡ �Ѵ�.
- views.py
  - page_range�� ���� �ε����� �� �ε����� ������ ����Ͽ� �Ѱ��ش�.
```py
    page_size = 5
    paginator = Paginator(board_list_all, page_size)

    try:
        if int(page) < 1:  page = 1
        if int(page) > paginator.num_pages:  page = paginator.num_pages
        board_list_page = paginator.page(page)
    except ValueError:
        page = 1
        board_list_page = paginator.page(page)
    page_range_size = 3
    page_range_begin = (math.floor((int(page) - 1) / page_range_size) * page_range_size) + 1
    page_range_end = page_range_begin + page_range_size - 1
    page_range = tuple(range(page_range_begin, page_range_end+1))

    data = {
        'board_list': board_list_page,
        'page_range': page_range
    }
    return render(request, 'board/list.html', data)
```
- template
  - ����Ͽ� �Ѿ�� page_range�� ���ؼ� for���� �����Ѵ�.
  - �� �������� �Ѵ� ���ڰ� for���� ���� ������ ���� ���� �ϱ� ���� num_pages(�� ������) �� �������� �۰ų� ���� ��쿡�� for���� �����ϵ��� �Ѵ�.
```html
						{% for i in page_range %}
							{% if board_list.number == i %}
								<li class="selected">{{ i }}</li>
							{% else %}
								{% if i <= board_list.paginator.num_pages %}
									<li><a href="?search={{request.GET.search}}&page={{i}}">{{ i }}</a></li>
								{% endif %}
							{% endif %}
    					{% endfor %}
```