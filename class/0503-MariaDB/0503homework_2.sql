-- 집계(통계) SQL 문제

select * from employees;
select * from salaries;
select * from departments;
select * from dept_manager;
select * from titles;
select * from dept_emp;

-- Q1
select max(salary) '최고임금',
	min(salary) '최저임금',
    max(salary)-min(salary) '최고임금-최저임금'
from salaries;

-- Q2
select date_format(max(hire_date), '%Y년 %m월 %d일')
from employees;

-- Q3
select date_format(min(hire_date), '%Y년 %m월 %d일')
from employees;

-- Q4
select avg(salary*12)
from salaries;

-- Q5
select max(salary)*12 '최고연봉',
	min(salary)*12 '최저연봉'
from salaries;

-- Q6
select substring(now(),1,4)-substring(max(birth_date),1,4) '어린사원',
	substring(now(),1,4)-substring(min(birth_date),1,4) '최연장자'
from employees;