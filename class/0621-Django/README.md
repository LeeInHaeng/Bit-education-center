# Django 템플릿 언어
### 필터 : 필터는 연쇄적으로 사용하여 필터의 결과가 다음 필터에 적용되도록 할 수 있다.
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
### 템플릿에서 math와 관련된 기능들 사용
- pip install django-mathfilters
- settings.py 의 INSTALLED_APPS 에 추가 'mathfilters',
- 사용할 html에 {% load mathfilters %} 추가 후 사용

### forloop 관련 
- counter : 1부터 숫자 시작
- counter0 : 0부터 숫자 시작
- first : 첫번째만 True 나머지는 False 반환
- revcounter : 거꾸로 숫자 시작 ---> 3, 2, 1
```html
<td>{{ forloop.counter }} : {{ forloop.counter0 }} : {{ forloop.first }}</td>
{{ forloop.revcounter }}
```

# ORM 심화
### aggregate
- group by를 통한 count, max, first 등을 사용하기 위한 방법
```py
max_groupno = Board.objects.aggregate(max_groupno=Max('groupno'))
board.groupno = 0 if max_groupno["max_groupno"] is None else max_groupno["max_groupno"]+1
```
### F 함수
- 쿼리를 하는 자신의 값을 참조
- orderno가 board.orderno보다 큰 값들에 대해서 자신의 orderno에 +1를 하여 업데이트
```py
Board.objects.filter(groupno=board.groupno).filter(orderno__gt=board.orderno).update(orderno=F('orderno')+1)
```
### Q 함수
- filter에서 다른 컬럼에 대해 OR, AND, NOT 조건을 사용하기 위한 함수
- contains : sql에서 like와 같은 의미
```py
    board_list_all = Board.objects.filter(
        Q(title__contains=search) |
        Q(content__contains=search)).order_by('-groupno', 'orderno')
```

# Page 처리
- select 수행 결과로 나온 list를 Paginator의 첫 번째 인자로 넣어준다.
  - 두 번째 인자는 한 페이지에 보여줄 게시글의 수
```py
paginator = Paginator(board_list_all, 5)
```
- 페이지 셋팅 및 예외 처리
  - page 셋팅 : paginator 의 page 메소드를 호출
  - PageNotAnInteger : page의 값이 인트 타입이 아닌 경우
  - EmptyPage : page가 paginator.page_range 의 범위 내에 없는 경우
  - num_pages 는 맨 마지막 페이지를 의미
```py
    try:
        board_list_page = paginator.page(page)
    except PageNotAnInteger:
        board_list_page = paginator.page(1)
    except EmptyPage:
        board_list_page = paginator.page(paginator.num_pages)
```
- template에서 사용
  - has_previous : 이전 페이지가 존재하는지 여부 True, False, previous_page_number 사용
  - paginator.page_range : 페이지의 범위
  - has_next : 다음 페이지가 존재하는지 여부 True, False, next_page_number 사용
```html
				<div class="pager">
					<ul>
						{% if board_list.has_previous %}
							<li><a href="?search={{request.GET.search}}&page={{board_list.previous_page_number}}">◀</a></li>
						{% endif %}
						{% for i in board_list.paginator.page_range %}
							{% if board_list.number == i %}
								<li class="selected">{{ i }}</li>
							{% else %}
								<li><a href="?search={{request.GET.search}}&page={{i}}">{{ i }}</a></li>
						      {% endif %}
    					{% endfor %}
						{% if board_list.has_next %}
							<li><a href="?search={{request.GET.search}}&page={{ board_list.next_page_number }}">▶</a></li>
						{% endif %}
					</ul>
				</div>
```

### page_range의 문제점
- 만약 페이지가 1 페이지부터 500 페이지까지 있다고 하면 1~500 페이지까지 다 나오게 된다
- 즉, < 1~5 >  /  < 6~ 10 > 형태의 페이징 처리가 불가능하다
- 이를 해결하기 위해 page_range를 커스터마이징 한다.
- views.py
  - page_range의 시작 인덱스와 끝 인덱스를 스스로 계산하여 넘겨준다.
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
  - 계산하여 넘어온 page_range에 대해서 for문을 수행한다.
  - 끝 페이지를 넘는 숫자가 for문을 통해 나오는 것을 방지 하기 위해 num_pages(끝 페이지) 를 조건으로 작거나 같을 경우에만 for문을 수행하도록 한다.
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