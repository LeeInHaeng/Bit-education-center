-- ex1) 현재 Fai Bale이 근무하는 부서에서 근무하는 직원의 사번, 전체 이름을 출력해보세요. 
-- 서브쿼리 미사용시
select b.dept_no
from employees a, dept_emp b
where a.emp_no=b.emp_no and b.to_date='9999-01-01'
	and concat(a.first_name, ' ', a.last_name)='Fai Bale';
    
select a.emp_no, a.last_name
from employees a, dept_emp b
where a.emp_no=b.emp_no and b.to_date='9999-01-01'
	and b.dept_no='d004';
    
-- 서브쿼리 사용시
select a.emp_no, a.last_name, b.dept_no
from employees a, dept_emp b
where a.emp_no=b.emp_no and b.to_date='9999-01-01'
	and b.dept_no=(select b.dept_no
					from employees a, dept_emp b
					where a.emp_no=b.emp_no and b.to_date='9999-01-01'
					and concat(a.first_name, ' ', a.last_name)='Fai Bale');
        
-- ex2) 현재 전체사원의 평균 연봉보다 적은 급여를 받는 사원의  이름, 급여를 나타내세요.
select a.last_name, b.salary
from employees a, salaries b
where a.emp_no=b.emp_no and b.to_date='9999-01-01'
	and b.salary < (select avg(salary)
					from salaries
                    where to_date='9999-01-01');
                    
-- ex3) 현재 가장적은 평균 급여를 받고 있는 직책에해서  평균 급여를 구하세요
-- 직책에서 평균 급여
select b.title, avg(a.salary)
from salaries a, titles b
where a.emp_no=b.emp_no and a.to_date='9999-01-01'
	and b.to_date='9999-01-01'
group by b.title;

select min(avg(a.salary)) -- min(avg 쿼리 에러
from salaries a, titles b
where a.emp_no=b.emp_no and a.to_date='9999-01-01'
	and b.to_date='9999-01-01'
group by b.title;

-- 해결법 1
select min(avg_salary)
from (select avg(a.salary) as avg_salary
		from salaries a, titles b
		where a.emp_no=b.emp_no and a.to_date='9999-01-01'
			and b.to_date='9999-01-01'
		group by b.title) as a; -- alias가 반드시 필요하다.
        
-- 해결법 2 top-k 사용하는 방법 (limit)
select b.title, avg(a.salary) as avg_salary
from salaries a, titles b
where a.emp_no=b.emp_no and a.to_date='9999-01-01'
	and b.to_date='9999-01-01'
group by b.title
order by avg_salary
limit 0,1;

-- ex4) 현재 급여가 50000 이상인 직원 이름 출력
-- 다중행 사용하는 경우
SELECT a.first_name, b.salary
FROM employees a, salaries b
where a.emp_no=b.emp_no and b.to_date='9999-01-01'
	and (a.emp_no, b.salary) = any(SELECT emp_no, salary
									FROM salaries
									WHERE salary > 50000
									AND to_date = '9999-01-01');
                                    
-- from절에 서브 쿼리를 사용하는 경우
select a.first_name, b.salary
from employees a, (SELECT emp_no, salary
					FROM salaries
					WHERE salary > 50000
					AND to_date = '9999-01-01') b
where a.emp_no=b.emp_no;

-- join 사용하는 경우
select a.first_name, b.salary
from employees a, salaries b
where a.emp_no=b.emp_no
	and b.to_date='9999-01-01'
    and b.salary>50000;
    
-- ex5) 현재 각 부서별로 최고 월급을 받는 직원의 이름과 월급을 출력
select c.dept_no, max(b.salary) as max_salary
from employees a, salaries b, dept_emp c
where a.emp_no=b.emp_no and a.emp_no=c.emp_no
	and b.to_date='9999-01-01' and c.to_date='9999-01-01'
group by c.dept_no;

-- where절에 서브 쿼리
select a.first_name , c.dept_no, b.salary
from employees a, salaries b, dept_emp c
where a.emp_no=b.emp_no and a.emp_no=c.emp_no
	and b.to_date='9999-01-01' and c.to_date='9999-01-01'
    and (c.dept_no, b.salary)=any(select c.dept_no, max(b.salary) as max_salary
									from employees a, salaries b, dept_emp c
									where a.emp_no=b.emp_no and a.emp_no=c.emp_no
									and b.to_date='9999-01-01' and c.to_date='9999-01-01'
									group by c.dept_no);

-- from절에 서브쿼리
select a.first_name , c.dept_no, b.salary
from employees a, salaries b, dept_emp c,
	(select c.dept_no, max(b.salary) as max_salary
		from employees a, salaries b, dept_emp c
		where a.emp_no=b.emp_no and a.emp_no=c.emp_no
			and b.to_date='9999-01-01' and c.to_date='9999-01-01'
		group by c.dept_no) d
where a.emp_no=b.emp_no and a.emp_no=c.emp_no
	and b.to_date='9999-01-01' and c.to_date='9999-01-01'
    and c.dept_no=d.dept_no
    and b.salary=d.max_salary;