### 아이콘 적용
- glyphicon : 특수문자를 이용한 아이콘 처리(180개의 아이콘 포함)
- 사용법이 쉽고 별도의 그래픽툴 없이 아이콘을 만들고 크기를 조절할 수 있다.
- 사용법 예
```html
<span class="glyphicon glyphicon-phone-alt"> </span> 123-4567-8912
```
- 해당 아이콘의 색상을 바꿀려면 button 태그로 감싸고 색상을 적용하거나 glyphicon 클래스가 있는 부분에 색상을 적용해도 된다
```html
.red {
	color: #FF0000
}

<button type="button" class="btn btn-default red">
	<span class="glyphicon glyphicon-thumbs-up"> </span> 좋아요
</button>

<span class="glyphicon glyphicon-thumbs-up red"> </span> 좋아요
```
- 다른 아이콘 참고
  - https://fontawesome.com/icons

### 드롭다운
- dropdown, data-toggle=dropdown, dropdown-menu, menuitem 순으로 작성한다
```html
<h4>기본적인 드롭다운</h4>
<div class="dropdown">
	<a data-toggle="dropdown" href="#">여기를 클릭하세요.</a>
	<ul class="dropdown-menu" role="menu">
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">메뉴 1</a></li>
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">메뉴 2</a></li>
		<li role="presentation" class="divider"></li>
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">분리된 메뉴 </a></li>
	</ul>
</div>
```
- dropdown-menu 클래스에 pull-right를 추가할 경우 menuitem 들이 오른쪽에 나타남
- menuitem 위에 헤더를 추가할 수 있음
```html
<li role="presentation" class="dropdown-header">헤더</li>
<li role="presentation"><a role="menuitem" tabindex="-1" href="#">메뉴 1</a></li>
```
- menuitem을 감싸고 있는 li 태그에 추가로 disabled 적용 가능
```html
<li role="presentation" class="disabled"><a role="menuitem" tabindex="-1" href="#">메뉴 2</a></li>
```

### 그룹핑
- 버튼과 드롭다운을 합칠 수 있음
```html
<div class="btn-group">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">버튼1 
		<span class="caret"></span>
	</button>
	<ul class="dropdown-menu" role="menu">
		<li><a href="#">메뉴 1</a></li>
		<li class="divider"></li>
		<li><a href="#">다른 메뉴 </a></li>
	</ul>
</div>
```
- btn-group 클래스에 dropup 클래스를 추가하면 메뉴가 위로 나옴
```html
<div class="btn-group dropup">
```
- input 태그와 span 태그를 합치는 경우
  - input 태그 외의 다른 태그를 input-group-addon 클래스를 넣어준다
  - span 태그 내부에 다른 태그들을 넣음으로써 다른 태그들과도 합칠 수 있다 (체크박스, 라디오 버튼 등)
```html
<div class="input-group">
	<span class="input-group-addon">
		<span class="glyphicon glyphicon-user"> </span>
	</span> 
	<input type="text" class="form-control" placeholder="아이디">
</div>

<div class="input-group">
	<input type="text" class="form-control">
	<span class="input-group-addon"> Cm </span>
</div>
```

### 네비게이션
- 문서 내부의 메인 메뉴,  서브 메뉴를 만들 때 사용된다.
- 탭형과 알약(pill) 형으로 주로 나누어 진다. 차이는 디자인적인 차이밖에 없다.
- 클래스 조합은  nav nav-tabs 와 nav nav-pills 이다.
- ul과 li 태그를 사용한다
  - 기본적으로 수평 방향으로 생성된다
```html
<ul class="nav nav-tabs">
	<li><a href="#">메뉴1</a></li>
	<li class="active"><a href="#">메뉴2</a></li>
</ul>

<ul class="nav nav-pills">
	<li class="active"><a href="#">메뉴1</a></li>
	<li><a href="#">메뉴2</a></li>
	<li class="disabled"><a href="#">메뉴3</a></li>
</ul>
```
- ul 태그에 nav-stacked 클래스를 추가하면 수직 방향, nav-justified를 추가하면 양쪽 정렬이 가능하다
```html
<ul class="nav nav-pills nav-stacked">

<ul class="nav nav-tabs nav-justified">
```
- 네비게이션과 드롭다운을 같이 사용하는 경우
```html
<ul class="nav navbar-nav">
	<li class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown">메뉴 1 <b class="caret"></b></a>
		<ul class="dropdown-menu">
			<li><a href="#">서브메뉴 1</a></li>
			<li><a href="#">서브메뉴 2</a></li>
			<li><a href="#">서브메뉴 3</a></li>
		</ul>
	</li>
	<li class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown">메뉴 2 <b class="caret"></b></a>
		<ul class="dropdown-menu">
			<li><a href="#">서브메뉴 1</a></li>
			<li><a href="#">서브메뉴 2</a></li>
			<li><a href="#">서브메뉴 3</a></li>
		</ul>
	</li>
</ul>
```

### nav 태그 사용
- 기본적인 네비게이션 바 형태
```html
		<nav class="navbar navbar-default" role="navigation">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">로고 </a>
			</div>

			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">메뉴1 </a></li>
					<li><a href="#">메뉴2</a></li>
					<li><a href="#">메뉴3</a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="검색">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
			
		</nav>
```
- 네비게이션 안의 드롭다운을 오른쪽 정렬 하고 싶은 경우
```html
		<nav class="navbar navbar-default" role="navigation">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">로고 </a>
			</div>

			<div class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">메뉴1 </a></li>
					<li><a href="#">메뉴2</a></li>
					<li><a href="#">메뉴3</a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="검색">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
			
		</nav>
```
- navbar 내부에 단순 텍스트, 단순 링크를 사용하는 경우
```html
<p class="navbar-text">텍스트 부분</p>

<p class="navbar-text pull-right">
	여기는 <a href="#" class="navbar-link">단순 링크</a>
</p>
```
- 네비게이션을 탑에 고정시키고 싶은 경우
```html
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
```

### 페이징
- 기본 페이징 class 형태
```html
<ul class="pagination">
	<li><a href="#">1</a></li>
</ul>
```
- 활성화 및 비활성화
  - li 태그에 disabled 클래스와 active 클래스 사용
```html
<li class="disabled"><a href="#">1</a></li>
<li class="active"><a href="#">2</a></li>
```
- 페이지 크기 조절
  - pagination 클래스에 pagination-lg, sm 등의 클래스를 추가
```html
<ul class="pagination pagination-lg">

<ul class="pagination pagination-sm">
```
- 페이지 가운데 정렬
  - pagination 클래스가 아닌 pager 클래스 사용
```html
<ul class="pager">
	<li><a href="#">이전 페이지 </a></li>
	<li><a href="#">다음 페이지 </a></li>
</ul>
```
- 페이저 양 끝 정렬 및 비활성화
  - 양끝 정렬을 위해서는 li 태그에 previous와 next 클래스를 사용한다
  - 비활성화를 위해서는 disabled 클래스를 추가한다
```html
<ul class="pager">
	<li class="previous disabled"><a href="#">← 이전 글 </a></li>
	<li class="next"><a href="#">새로운 글 →</a></li>
</ul>
```

### 라벨과 배지
- label의 label-default, label-primary 등등의 클래스가 있음
```html
<span class="label label-default"></span>
```
- 배지 사용 (ex 댓글 수 등을 표시)
```html
<span class="badge pull-right">42</span>
```

### 프로그레스 바
- 실제 프로그레스 바의 채워짐 정도는 aria-valuenow 가 아닌 width 에서 결정
```html
<div class="progress progress-striped active">
	<div class="progress-bar" role="progressbar" aria-valuenow="45"
		aria-valuemin="0" aria-valuemax="100" style="width: 45%">
		<span class="sr-only">45% Complete</span>
	</div>
</div>
```