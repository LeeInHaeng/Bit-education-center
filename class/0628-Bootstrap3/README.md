### ������ ����
- glyphicon : Ư�����ڸ� �̿��� ������ ó��(180���� ������ ����)
- ������ ���� ������ �׷����� ���� �������� ����� ũ�⸦ ������ �� �ִ�.
- ���� ��
```html
<span class="glyphicon glyphicon-phone-alt"> </span> 123-4567-8912
```
- �ش� �������� ������ �ٲܷ��� button �±׷� ���ΰ� ������ �����ϰų� glyphicon Ŭ������ �ִ� �κп� ������ �����ص� �ȴ�
```html
.red {
	color: #FF0000
}

<button type="button" class="btn btn-default red">
	<span class="glyphicon glyphicon-thumbs-up"> </span> ���ƿ�
</button>

<span class="glyphicon glyphicon-thumbs-up red"> </span> ���ƿ�
```
- �ٸ� ������ ����
  - https://fontawesome.com/icons

### ��Ӵٿ�
- dropdown, data-toggle=dropdown, dropdown-menu, menuitem ������ �ۼ��Ѵ�
```html
<h4>�⺻���� ��Ӵٿ�</h4>
<div class="dropdown">
	<a data-toggle="dropdown" href="#">���⸦ Ŭ���ϼ���.</a>
	<ul class="dropdown-menu" role="menu">
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">�޴� 1</a></li>
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">�޴� 2</a></li>
		<li role="presentation" class="divider"></li>
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">�и��� �޴� </a></li>
	</ul>
</div>
```
- dropdown-menu Ŭ������ pull-right�� �߰��� ��� menuitem ���� �����ʿ� ��Ÿ��
- menuitem ���� ����� �߰��� �� ����
```html
<li role="presentation" class="dropdown-header">���</li>
<li role="presentation"><a role="menuitem" tabindex="-1" href="#">�޴� 1</a></li>
```
- menuitem�� ���ΰ� �ִ� li �±׿� �߰��� disabled ���� ����
```html
<li role="presentation" class="disabled"><a role="menuitem" tabindex="-1" href="#">�޴� 2</a></li>
```

### �׷���
- ��ư�� ��Ӵٿ��� ��ĥ �� ����
```html
<div class="btn-group">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">��ư1 
		<span class="caret"></span>
	</button>
	<ul class="dropdown-menu" role="menu">
		<li><a href="#">�޴� 1</a></li>
		<li class="divider"></li>
		<li><a href="#">�ٸ� �޴� </a></li>
	</ul>
</div>
```
- btn-group Ŭ������ dropup Ŭ������ �߰��ϸ� �޴��� ���� ����
```html
<div class="btn-group dropup">
```
- input �±׿� span �±׸� ��ġ�� ���
  - input �±� ���� �ٸ� �±׸� input-group-addon Ŭ������ �־��ش�
  - span �±� ���ο� �ٸ� �±׵��� �������ν� �ٸ� �±׵���� ��ĥ �� �ִ� (üũ�ڽ�, ���� ��ư ��)
```html
<div class="input-group">
	<span class="input-group-addon">
		<span class="glyphicon glyphicon-user"> </span>
	</span> 
	<input type="text" class="form-control" placeholder="���̵�">
</div>

<div class="input-group">
	<input type="text" class="form-control">
	<span class="input-group-addon"> Cm </span>
</div>
```

### �׺���̼�
- ���� ������ ���� �޴�,  ���� �޴��� ���� �� ���ȴ�.
- ������ �˾�(pill) ������ �ַ� ������ ����. ���̴� ���������� ���̹ۿ� ����.
- Ŭ���� ������  nav nav-tabs �� nav nav-pills �̴�.
- ul�� li �±׸� ����Ѵ�
  - �⺻������ ���� �������� �����ȴ�
```html
<ul class="nav nav-tabs">
	<li><a href="#">�޴�1</a></li>
	<li class="active"><a href="#">�޴�2</a></li>
</ul>

<ul class="nav nav-pills">
	<li class="active"><a href="#">�޴�1</a></li>
	<li><a href="#">�޴�2</a></li>
	<li class="disabled"><a href="#">�޴�3</a></li>
</ul>
```
- ul �±׿� nav-stacked Ŭ������ �߰��ϸ� ���� ����, nav-justified�� �߰��ϸ� ���� ������ �����ϴ�
```html
<ul class="nav nav-pills nav-stacked">

<ul class="nav nav-tabs nav-justified">
```
- �׺���̼ǰ� ��Ӵٿ��� ���� ����ϴ� ���
```html
<ul class="nav navbar-nav">
	<li class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown">�޴� 1 <b class="caret"></b></a>
		<ul class="dropdown-menu">
			<li><a href="#">����޴� 1</a></li>
			<li><a href="#">����޴� 2</a></li>
			<li><a href="#">����޴� 3</a></li>
		</ul>
	</li>
	<li class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown">�޴� 2 <b class="caret"></b></a>
		<ul class="dropdown-menu">
			<li><a href="#">����޴� 1</a></li>
			<li><a href="#">����޴� 2</a></li>
			<li><a href="#">����޴� 3</a></li>
		</ul>
	</li>
</ul>
```

### nav �±� ���
- �⺻���� �׺���̼� �� ����
```html
		<nav class="navbar navbar-default" role="navigation">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">�ΰ� </a>
			</div>

			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">�޴�1 </a></li>
					<li><a href="#">�޴�2</a></li>
					<li><a href="#">�޴�3</a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="�˻�">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
			
		</nav>
```
- �׺���̼� ���� ��Ӵٿ��� ������ ���� �ϰ� ���� ���
```html
		<nav class="navbar navbar-default" role="navigation">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">�ΰ� </a>
			</div>

			<div class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">�޴�1 </a></li>
					<li><a href="#">�޴�2</a></li>
					<li><a href="#">�޴�3</a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="�˻�">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
			
		</nav>
```
- navbar ���ο� �ܼ� �ؽ�Ʈ, �ܼ� ��ũ�� ����ϴ� ���
```html
<p class="navbar-text">�ؽ�Ʈ �κ�</p>

<p class="navbar-text pull-right">
	����� <a href="#" class="navbar-link">�ܼ� ��ũ</a>
</p>
```
- �׺���̼��� ž�� ������Ű�� ���� ���
```html
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
```

### ����¡
- �⺻ ����¡ class ����
```html
<ul class="pagination">
	<li><a href="#">1</a></li>
</ul>
```
- Ȱ��ȭ �� ��Ȱ��ȭ
  - li �±׿� disabled Ŭ������ active Ŭ���� ���
```html
<li class="disabled"><a href="#">1</a></li>
<li class="active"><a href="#">2</a></li>
```
- ������ ũ�� ����
  - pagination Ŭ������ pagination-lg, sm ���� Ŭ������ �߰�
```html
<ul class="pagination pagination-lg">

<ul class="pagination pagination-sm">
```
- ������ ��� ����
  - pagination Ŭ������ �ƴ� pager Ŭ���� ���
```html
<ul class="pager">
	<li><a href="#">���� ������ </a></li>
	<li><a href="#">���� ������ </a></li>
</ul>
```
- ������ �� �� ���� �� ��Ȱ��ȭ
  - �糡 ������ ���ؼ��� li �±׿� previous�� next Ŭ������ ����Ѵ�
  - ��Ȱ��ȭ�� ���ؼ��� disabled Ŭ������ �߰��Ѵ�
```html
<ul class="pager">
	<li class="previous disabled"><a href="#">�� ���� �� </a></li>
	<li class="next"><a href="#">���ο� �� ��</a></li>
</ul>
```

### �󺧰� ����
- label�� label-default, label-primary ����� Ŭ������ ����
```html
<span class="label label-default"></span>
```
- ���� ��� (ex ��� �� ���� ǥ��)
```html
<span class="badge pull-right">42</span>
```

### ���α׷��� ��
- ���� ���α׷��� ���� ä���� ������ aria-valuenow �� �ƴ� width ���� ����
```html
<div class="progress progress-striped active">
	<div class="progress-bar" role="progressbar" aria-valuenow="45"
		aria-valuemin="0" aria-valuemax="100" style="width: 45%">
		<span class="sr-only">45% Complete</span>
	</div>
</div>
```