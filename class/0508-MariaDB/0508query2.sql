-- DDL

create table member(
	no int not null primary key auto_increment,
    email varchar(50) not null default  '',
    passwd varchar(64) not null,
    name varchar(25),
    dept_name varchar(25)
);

desc member;

insert into member(passwd, name, dept_name)
values(password('1234'), '이이냉', '시스템개발팀');

select * from member;

alter table member add juminbunho char(13) not null after no;
alter table member drop juminbunho;

alter table member add join_date datetime not null;

alter table member change no 
no int unsigned not null;

alter table member change no 
no int unsigned not null auto_increment;

alter table member change dept_name
department_name varchar(25);

alter table member change name
name varchar(10);

alter table member rename user;

desc user;

insert into user(passwd, name, department_name, join_date)
values(password('1234'), '이이냉', '시스템개발팀', now());

select * from user;

update user
	set join_date = (select now())
where no=1;

delete from user
where no=1;

drop table user;