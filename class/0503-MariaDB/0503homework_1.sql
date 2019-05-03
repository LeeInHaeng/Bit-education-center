-- 기본 SQL 문제

select * from employees;
select * from salaries;
select * from departments;
select * from dept_manager;
select * from titles;
select * from dept_emp;

-- Q1
select concat(first_name, ' ', last_name) '이름'
from employees where emp_no=10944;

-- Q2
select concat(first_name, ' ', last_name) '이름',
	gender '성별', hire_date '입사일'
from employees
order by hire_date;

-- Q3
select count(case when gender='F' then 1 end) '여직원',
	count(case when gender='M' then 1 end) '남직원'
from employees;

-- Q4
select count(distinct emp_no)
from salaries;

-- Q5
select count(*) from departments;

-- Q6
select count(*) from dept_manager;

-- Q7
select * from departments
order by length(dept_name) desc;

-- Q8
select count(distinct emp_no)
from salaries where salary>120000
order by from_date desc;

-- Q9
select distinct title
from titles
order by length(title) desc;

-- Q10
select count(*) from titles
where title='Engineer';

-- Q11
select * from titles
where emp_no=13250
order by from_date;