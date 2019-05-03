-- select 기본

select * from departments;

select first_name, gender, hire_date from employees;

select concat(first_name, ' ', last_name) '이름',
	gender '성별', hire_date '입사일'
from employees order by hire_date desc;
    
select distinct title from titles;

select * from salaries;

select emp_no, salary, from_date from salaries
where from_date like '2001%'
order by salary desc;

select concat(first_name, ' ', last_name) '이름',
	gender '성별', hire_date '입사일'
from employees
where gender='f' and hire_date < '1991-01-01';

select emp_no, dept_no from dept_emp
where dept_no in ('d005', 'd009');

-- 문자형 함수

select upper('seOUl'), ucase('sEouL');
select lower('SeOUl'), lcase('sEOuL');

select substring('Happy Dat', 3,2);

select concat( first_name, ' ', last_name )  '이름',
	hire_date  '입사일'
FROM employees
WHERE substring( hire_date, 1, 4) = '1989';

select lpad('hi', 5, '?');
select rpad('hi', 5, '?');

SELECT emp_no, LPAD(salary, 10, '*')      
FROM salaries     
WHERE from_date like '2001-%' AND salary < 70000;

select concat('---', ltrim('    hello    '), '---');
select concat('---', rtrim('    hello    '), '---');
select concat('---', trim('    hello    '), '---');
select trim(both 'x' from 'xxxxxxhixxxxxx');

select abs(1), abs(-1);
select mod(234,10), 234%10;
select floor(1.2345), floor(-1.2345);
SELECT CEILING(1.23), CEILING(-1.23);
SELECT ROUND(-1.23), ROUND(-1.58), ROUND(1.58); 
SELECT ROUND(1.298,1),ROUND(1.298,0); 
SELECT POW(2,2),POWER(2,-2); 
SELECT SIGN(-32), SIGN(0), SIGN(234); 

SELECT GREATEST(2,0),GREATEST(4.0,3.0,5.0),GREATEST("B","A","C");
SELECT LEAST(2,0),LEAST(34.0,3.0,5.0),LEAST("b","A","C"); 

SELECT CURDATE(),CURRENT_DATE; 
SELECT NOW(),SYSDATE(),CURRENT_TIMESTAMP; 

select now(), sleep(2), now(); -- 쿼리가 실행되기 전에 계산되기 때문에 결과가 같다.
select sysdate(), sleep(2), sysdate(); -- 쿼리가 진행되면서 결과가 나오기 때문에 결과가 다르다.

select date_format(now(), '%Y년-%c월-%d일 %h시:%i분:%s초');

SELECT concat(first_name, ' ', last_name) AS name,               
       PERIOD_DIFF( DATE_FORMAT(CURDATE(), '%Y%m'),  
                    DATE_FORMAT(hire_date, '%Y%m') )
FROM employees;

select first_name, hire_date,
	date_add(hire_date, interval 5 month) '입사일로부터 5개월 후'
from employees;

select cast(now() as date);
select cast(1-2 as unsigned);

-- 집계(통계) 함수

select avg(salary), sum(salary) from salaries
where emp_no=10060;

select emp_no, avg(salary), sum(salary) from salaries
where from_date like '1985%'
group by emp_no
having avg(salary) > 10000;
