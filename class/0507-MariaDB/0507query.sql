select * from salaries
where emp_no=11007;

select emp_no, avg(salary) from salaries
group by emp_no order by avg(salary) desc;

select emp_no, title from titles
where title='Manager';

select emp_no, count(title)
from titles group by emp_no;

select emp_no, avg(salary) from salaries
group by emp_no having avg(salary) > 50000;

select * from salaries;

-- 현재 부서별로 현재 직책이 Engineer인 직원들에 대해서만 평균급여를 구하세요.
select c.dept_no, d.dept_name, avg(a.salary)
from salaries a, titles b, dept_emp c, departments d
where a.emp_no=b.emp_no and b.emp_no=c.emp_no and c.dept_no=d.dept_no
and a.to_date='9999-01-01' and b.to_date='9999-01-01'
and b.title='Engineer'
group by c.dept_no;

-- 현재 직책별로 급여의 총합을 구하되 Engineer직책은 제외하세요
-- 단, 총합이 2,000,000,000이상인 직책만 나타내며 급여총합에
-- 대해서 내림차순(DESC)로 정렬하세요.   
select title, sum(salary)
from titles a, salaries b
where a.emp_no=b.emp_no
  and a.to_date='9999-01-01' and b.to_date='9999-01-01'
group by title
having sum(salary)>=2000000000
order by sum(salary) desc;

-- employees 테이블과 titles 테이블를 join하여 직원의 이름과 직책을 출력하되 여성 엔지니어만 출력하세요. 
select a.first_name, b.title, a.gender
from employees a
join titles b on a.emp_no=b.emp_no
where a.gender='F';

-- natural join은 컬럼명이 같기 때문에 on 조건을 생략한다.
select a.first_name, b.title, a.gender
from employees a
join titles b
where a.gender='F';

-- join~using은 natural join시 이름이 같은 컬럼이 많은 경우 
-- 특정 컬럼과 조인하고 싶은 컬럼에 대해 using절을 사용한다.
select a.first_name, b.title, a.gender
from employees a
join titles b using(emp_no)
where a.gender='F';

-- 현재 회사 상황을 반영한 직원 근무부서를
-- 사번, 직원 전체이름, 근무부서 형태로 출력해 보세요.
select a.emp_no, a.first_name, c.dept_name
from employees a, dept_emp b, departments c
where a.emp_no=b.emp_no and b.dept_no=c.dept_no
	and b.to_date='9999-01-01';
    
-- 위의 쿼리를 조인절을 사용하는 경우
select a.emp_no, a.first_name, c.dept_name
from employees a
join dept_emp b on a.emp_no=b.emp_no
join departments c on b.dept_no=c.dept_no
where b.to_date='9999-01-01';
