# Bootstrap

### ��Ʈ��Ʈ�� �⺻ ���� ���ø�
```html
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- �� 3���� ��Ÿ �±״� *�ݵ��* head �±��� ó���� �;��մϴ�; � �ٸ� ���������� �ݵ�� �� �±׵� *������* �;� �մϴ� -->

<title>Bootstrap 101 Template</title>

<!-- ��Ʈ��Ʈ�� -->
<link href="assets/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

<!-- IE8 ���� HTML5 ��ҿ� �̵�� ������ ���� HTML5 shim �� Respond.js -->
<!-- WARNING: Respond.js �� ����� file:// �� ���� �������� �� ���� �������� �ʽ��ϴ�. -->
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
- media ����
  - media ������ ���� width�� 768px �̻��̸� container�� 750px �� ���� ����
  - media ������ ���� width�� 992px �̻��̸� container�� 970px �� ���� ����
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

### �׸��� �ý���
- container ---> row ---> col ������ css�� �����Ѵ�.
- row �ȿ��� �ݵ�� col�� ���� �ϱ� ������ �ϳ��� col�� ��� row�� �׳� row�� col�� ���� �ʴ� ���� ����.
- ��Ʈ��Ʈ���� ���� ��(Row)�� �ְ� �� �ȿ� ���� ��(Col)�� 12���� �ɰ� ���Ҵ�.
  - row�� ���� �¿� 15px�� �е��� �ִ�.
```html
<div class="container">

</div>
```
- ȭ�� ���� ���� ��� ����ϴ� ���
```html
<div class="container-fluid">
</div>
```
- �ſ� ���� ��� ����� �� (768px �̸�)
  - �����̳� �ʺ� : ���� (auto)
  - Ŭ���� ���λ� : col-xs-
- ���� ��� �׺� (768px �̻�)
  - �����̳� �ʺ� : 750px
  - Ŭ���� ���λ� : col-sm-
- �߰� ��� ����ũž (992px �̻�)
  - �����̳� �ʺ� : 970px
  - Ŭ���� ���λ� : col-md-
- ū ��� ����ũž (1200px �̻�)
  - �����̳� �ʺ� : 1170px
  - Ŭ���� ���λ� : col-lg-
- offset : Į���� Į�� ���̿� ������ �ֱ� ���ؼ� ����ϴ� Ŭ����
```html
<div class="col-md-8 col-md-offset-1">
```
- row ---> col ---> row ---> col �̷� ������ ��ø�ؼ��� ��� ����
  - �ѱ� �������� �ٴܰ� ���� �������� ���̰� ���� ���
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

### ��Ʈ
- ��Ʈ��Ʈ���� �⺻ ��Ʈ
  - �⺻ �۲� : Helvetica Neue
  - ��ü���� �۲� ũ�� : 14px
  - ��ü �ణ : 1.42857443
  - �۲� �� : ���� ȸ��
  - ��� �� : ���
- ��ǻ�Ϳ� �̸� ��ġ�� ��Ʈ ���
  - body�� ��Ʈ�� �����Ѵ�
```html
body{
	font-family: "Helvetica Neue", Helvetica, Arial, "���� ���", "Malgun gothic", sans-serif;
}
```
- ���̹����� �����ϴ� ��Ʈ�� �ٿ� �޾Ƽ� CSS3���� �����ϴ� font-face�� ����ϴ� ���
  - ��ǻ�Ϳ� ��Ʈ�� ��ġ�Ǿ� ���� ���� ��� ���
  - font-face���� NanumGothic �̶�� �����ϰ� body ���� NanumGothic �� ���
  - �ܺ� css ���� (custom.css)
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

- ���ۿ��� �����ϴ� ������� ��Ʈ ���
  - import�� ���� ���ۿ��� �����ϴ� URL���� �޾ƿ´�.
```css
@import url(http://fonts.googleapis.com/earlyaccess/nanumgothic.css);

body { font-family: "Helvetica Neue", Helvetica, Arial,'Nanum Gothic', sans-serif; }
```

### Ÿ�����׷���
- h1���� h6���� ��� ����
- 36px, 30px, 24px, 18px, 14px, 12px�� ���� �ȴ�.
- <small></small> �ױ׸� �����ϸ� ���� ũ���� 65%(h1, h2, h3), 75%(h4, h5, h6)ũ��� ���
- ������ �۾� ũ��� 14px�� �⺻���� �Ѵ�.
- �ϴܿ� 10px�� ���� ���� ������.
- �ణ�� 1.42857143 ������ ����
- ������ ������ ���� .text-left, .text-center, .text-right Ŭ������ ����Ѵ�.
  - pull-right Ŭ������ float : right �������� ������ ������ �ζ��� �������� �۵��ȴ�
  - �ζ��� ������ ���� <div class="clearfix"></div> �� ����� �� �ִ�
- ������ �پ��ϰ� �ٹ� ���� ������ Ŭ������ ����� �� �ִ�.
  - text-muted, text-primary, text-success, text-info, text-warning, text-danger Ŭ���� ����� ����
  - ȸ��, �Ķ���, �ʷϻ�, ����, Ȳ���, ������ 
- <abbr> �±׸� ����Ͽ� ���(���Ӹ�)�� ó�� �� �� �ִ�
  - ��� �۾� ���� ���콺�� �ø��� ��Ż �ر���� �۾��� �� �� ����
```css
<abbr title="��Ż �ر�">���
```
- em �±� : ���Ÿ� ü�� ǥ���Ѵ�
- strong : ������ ������ �� ����Ѵ� (���� �۾�ü)
- lead Ŭ���� : �±� �ȿ� �ִ� �۾��� ������ �������� ���� Ŀ����
```css
<span class="lead">���� �����ڸ� �̿� </span>
```
- �ּҸ� ��Ÿ������ address �±׸� �̿��Ѵ�
```css
<address>
	<strong>����:��뼮</strong> <br/> 
	�ּ�:���ֽ� ������ <br/> 
	��ȭ��ȣ:010-234-7895 <br/>
</address>
```
- �ο뱸�� blockquote �±׸� ����Ѵ�
  - ���ڸ� ��Ÿ�� ���� cite �±׸� ����Ѵ�
```css
<blockquote>
	������� ���� CSS �Ӽ��� ��Ʈ ��Ʈ�� �� ����Ʈ������ �����ϰ� ������ �Ǿ� �ֽ��ϴ�. 
	<small>CSS �Ӽ� <cite>http://bootstrapk.com/BS3/css</cite></small>
</blockquote>
```

### ���̺�
- table class="table" : �⺻ ���̺�
- class="table table-striped" : ���� ���� �ִ� ���̺�
- class="table table-bordered" : ���̺��� ��� ���鿡 ���� ������ ����� ����
- class="table table-hover" : hover ȿ��
- class="table table-condensed" : row�� ���� ������
- class="active" class="success" class="warning" class="danger" : �� ���󺰷� row ǥ��

### ��
- ���� �ڽ� ��Ҵ� ���� .form-control�̶�� Ŭ���� �����ڸ� �����ϰ� �ȴ�.
- width�� �⺻������ 100%�� ��ȯ, height�� 34px �� ����ȴ�.  
- �⺻ padding: 6px 12px �� border-radius: 4px ���� ����Ǿ� �ε巯�� ������� ���Ѵ�.
- ���� �⺻ �Է� ������ .form-group �̶�� Ŭ������ ���� ���� div�±׷� ���� �ϴ����� 15px ������ ������ ����� �Ѵ�.
```html
<form role="form">
	<div class="form-group">
		<label for="Name">�̸�</label>
		<input type="text" class="form-control" placeholder="�̸�">
	</div>
	<div class="form-group">
		<label for="emailaddress">�̸���</label>
		<input type="email" class="form-control" placeholder="�̸���">
	</div>
	<div class="form-group">
		<button type="submit">Ȯ��</button>
	</div>
</form>
```
- form-inline Ŭ���� ����
  - form-group���� ������ �ִ� Ŭ������ �� �ٷ� ǥ��
```html
<form role="form" class="form-inline">
```
- label �±׿� sr-only �±׸� �ָ� label�� ǥ�õ��� �ʴ´�
```html
<div class="form-group">
	<label for="Name" class="sr-only">�̸�</label>
	<input type="text" class="form-control" placeholder="�̸�">
</div>
```
- form-horizontal Ŭ���� ����
  - col�� ���� �׸��� �ý����� ������ �� �ִ�
```html
<form class="form-horizontal" role="form">
	<div class="form-group">
		<label for="Name" class="col-xs-2 col-lg-2 control-label">�̸�</label>
		<div class="col-xs-10 col-lg-10">
			<input type="text" class="form-control" placeholder="�̸�">
		</div>
	</div>
	<div class="form-group">
		<div class="col-xs-offset-2 col-xs-10 col-lg-offset-2 col-lg-10 ">
			<button type="submit">Ȯ��</button>
		</div>
	</div>

</form>
```

### ��ư
- ��ư Ŭ������ btn�� �⺻������ ����Ѵ�
- btn btn-default : ȸ�� ��ư
- btn btn-primary : �Ķ� ��ư
- btn btn-success : �ʷ� ��ư
- btn btn-info : �ϴû� ��ư
- btn btn-warning : ��Ȳ ��ư
- btn btn-danger : ���� ��ư
- btn btn-link : �����۸�ũ
- <button type="button" class="btn btn-default" disabled="disabled"> : �������� �ʴ� ��ư