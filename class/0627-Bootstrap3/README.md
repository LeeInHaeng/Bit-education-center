# Bootstrap

### 부트스트랩 기본 시작 템플릿
```html
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->

<title>Bootstrap 101 Template</title>

<!-- 부트스트랩 -->
<link href="assets/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

<!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
<!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<h1>Hello, world!</h1>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="assets/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

</body>
</html>
```
- media 쿼리
  - media 쿼리로 인해 width가 768px 이상이면 container가 750px 인 것을 적용
  - media 쿼리로 인해 width가 992px 이상이면 container가 970px 인 것을 적용
```css
@media (min-width: 768px)
.container {
	width: 750px;
}
@media (min-width: 992px)
.container {
	width: 970px;
}
```

### 그리드 시스템
- container ---> row ---> col 순으로 css를 셋팅한다.
- row 안에는 반드시 col이 들어가야 하기 때문에 하나의 col일 경우 row를 그냥 row와 col을 쓰지 않는 것이 좋다.
- 부트스트랩은 가로 행(Row)이 있고 그 안에 세로 행(Col)을 12개로 쪼개 놓았다.
  - row에 대해 좌우 15px씩 패딩이 있다.
```html
<div class="container">

</div>
```
- 화면 가로 폭을 모두 사용하는 경우
```html
<div class="container-fluid">
</div>
```
- 매우 작은 기기 모바일 폰 (768px 미만)
  - 콘테이너 너비 : 없음 (auto)
  - 클래스 접두사 : col-xs-
- 작은 기기 테블릿 (768px 이상)
  - 콘테이너 너비 : 750px
  - 클래스 접두사 : col-sm-
- 중간 기기 데스크탑 (992px 이상)
  - 콘테이너 너비 : 970px
  - 클래스 접두사 : col-md-
- 큰 기기 데스크탑 (1200px 이상)
  - 콘테이너 너비 : 1170px
  - 클래스 접두사 : col-lg-
- offset : 칼럼과 칼럼 사이에 간격을 주기 위해서 사용하는 클래스
```html
<div class="col-md-8 col-md-offset-1">
```
- row ---> col ---> row ---> col 이런 식으로 중첩해서도 사용 가능
  - 한글 문서에서 다단과 같은 형식으로 보이고 싶은 경우
```html
<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-9  cont">
		<div class="row">
			<div class="col-md-7">
			</div>
			<div class="col-md-5">
			</div>
		</div>
	</div>
</div>
```

### 폰트
- 부트스트랩의 기본 폰트
  - 기본 글꼴 : Helvetica Neue
  - 전체적인 글꼴 크기 : 14px
  - 전체 행간 : 1.42857443
  - 글꼴 색 : 진한 회색
  - 배경 색 : 흰색
- 컴퓨터에 미리 설치된 폰트 사용
  - body에 폰트를 지정한다
```html
body{
	font-family: "Helvetica Neue", Helvetica, Arial, "맑은 고딕", "Malgun gothic", sans-serif;
}
```
- 네이버에서 제공하는 폰트를 다운 받아서 CSS3에서 제공하는 font-face를 사용하는 방법
  - 컴퓨터에 폰트가 설치되어 있지 않은 경우 사용
  - font-face에서 NanumGothic 이라고 지정하고 body 에서 NanumGothic 을 사용
  - 외부 css 파일 (custom.css)
```css
@font-face{
  font-family:'NanumGothic';
  src:url("../fonts/NanumGothic.eot");
  src:local(""), url("../fonts/NanumGothic.woff") format("woff");
}

body{
	font-family: "Helvetica Neue", Helvetica, Arial, "NanumGothic", "Malgun gothic", sans-serif;
}
```

- 구글에서 제공하는 나눔고딕 폰트 사용
  - import를 통해 구글에서 제공하는 URL에서 받아온다.
```css
@import url(http://fonts.googleapis.com/earlyaccess/nanumgothic.css);

body { font-family: "Helvetica Neue", Helvetica, Arial,'Nanum Gothic', sans-serif; }
```

### 타이포그래피
- h1에서 h6까지 사용 가능
- 36px, 30px, 24px, 18px, 14px, 12px로 설정 된다.
- <small></small> 테그를 지정하면 원래 크기의 65%(h1, h2, h3), 75%(h4, h5, h6)크기롤 축소
- 본문의 글씨 크기는 14px를 기본으로 한다.
- 하단에 10px의 마진 값을 가진다.
- 행간은 1.42857143 간격을 가짐
- 문장을 정렬할 때는 .text-left, .text-center, .text-right 클래스를 사용한다.
  - pull-right 클래스는 float : right 형식으로 들어오기 때문에 인라인 형식으로 작동된다
  - 인라인 방지를 위해 <div class="clearfix"></div> 를 사용할 수 있다
- 문장을 다양하게 꾸밀 때는 다음의 클래스를 사용할 수 있다.
  - text-muted, text-primary, text-success, text-info, text-warning, text-danger 클래스 사용이 가능
  - 회색, 파란색, 초록색, 남색, 황토색, 빨간색 
- <abbr> 태그를 사용하여 약어(줄임말)를 처리 할 수 있다
  - 멘붕 글씨 위에 마우스를 올리면 멘탈 붕괴라는 글씨를 볼 수 있음
```css
<abbr title="멘탈 붕괴">멘붕
```
- em 태그 : 이탤릭 체로 표시한다
- strong : 문장을 강조할 때 사용한다 (굵은 글씨체)
- lead 클래스 : 태그 안에 있는 글씨가 공백을 기준으로 점점 커진다
```css
<span class="lead">리드 선택자를 이용 </span>
```
- 주소를 나타낼때는 address 태그를 이용한다
```css
<address>
	<strong>저자:양용석</strong> <br/> 
	주소:제주시 노형동 <br/> 
	전화번호:010-234-7895 <br/>
</address>
```
- 인용구는 blockquote 태그를 사용한다
  - 저자를 나타낼 때는 cite 태그를 사용한다
```css
<blockquote>
	현재까지 쓰인 CSS 속성은 부트 스트랩 웹 사이트에서도 동일하게 설명이 되어 있습니다. 
	<small>CSS 속성 <cite>http://bootstrapk.com/BS3/css</cite></small>
</blockquote>
```

### 테이블
- table class="table" : 기본 테이블
- class="table table-striped" : 가로 선이 있는 테이블
- class="table table-bordered" : 테이블의 모든 셀들에 대해 보더가 적용된 상태
- class="table table-hover" : hover 효과
- class="table table-condensed" : row의 폭이 좁아짐
- class="active" class="success" class="warning" class="danger" : 각 색상별로 row 표시

### 폼
- 폼의 자식 요소는 전부 .form-control이라는 클래스 선택자를 적용하게 된다.
- width가 기본적으로 100%로 전환, height는 34px 이 적용된다.  
- 기본 padding: 6px 12px 과 border-radius: 4px 값이 적용되어 부드러운 모양으로 변한다.
- 폼의 기본 입력 참고에는 .form-group 이라는 클래스를 가진 보통 div태그로 감싸 하단으로 15px 정도의 여백이 생기게 한다.
```html
<form role="form">
	<div class="form-group">
		<label for="Name">이름</label>
		<input type="text" class="form-control" placeholder="이름">
	</div>
	<div class="form-group">
		<label for="emailaddress">이메일</label>
		<input type="email" class="form-control" placeholder="이메일">
	</div>
	<div class="form-group">
		<button type="submit">확인</button>
	</div>
</form>
```
- form-inline 클래스 적용
  - form-group으로 감싸져 있는 클래스가 한 줄로 표시
```html
<form role="form" class="form-inline">
```
- label 태그에 sr-only 태그를 주면 label이 표시되지 않는다
```html
<div class="form-group">
	<label for="Name" class="sr-only">이름</label>
	<input type="text" class="form-control" placeholder="이름">
</div>
```
- form-horizontal 클래스 적용
  - col에 대해 그리드 시스템을 적용할 수 있다
```html
<form class="form-horizontal" role="form">
	<div class="form-group">
		<label for="Name" class="col-xs-2 col-lg-2 control-label">이름</label>
		<div class="col-xs-10 col-lg-10">
			<input type="text" class="form-control" placeholder="이름">
		</div>
	</div>
	<div class="form-group">
		<div class="col-xs-offset-2 col-xs-10 col-lg-offset-2 col-lg-10 ">
			<button type="submit">확인</button>
		</div>
	</div>

</form>
```

### 버튼
- 버튼 클래스로 btn을 기본적으로 사용한다
- btn btn-default : 회색 버튼
- btn btn-primary : 파란 버튼
- btn btn-success : 초록 버튼
- btn btn-info : 하늘색 버튼
- btn btn-warning : 주황 버튼
- btn btn-danger : 빨간 버튼
- btn btn-link : 하이퍼링크
- <button type="button" class="btn btn-default" disabled="disabled"> : 동작하지 않는 버튼