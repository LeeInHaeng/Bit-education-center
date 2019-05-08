-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 문제1
-- 현재 평균 연봉보다 많은 월급을 받는 직원은 몇 명이나 있습니까?
select count(*)
from salaries
where salary > (select avg(salary)
				from salaries
				where to_date='9999-01-01')
	and to_date='9999-01-01';
    
-- 문제2
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 연봉을 조회하세요.
-- 단 조회결과는 연봉의 내림차순으로 정렬되어 나타나야 합니다. 
select a.emp_no, a.last_name, d.dept_no, c.salary
from employees a, (select dept_no, max(b.salary) as max_salary
					from employees a
					join salaries b on a.emp_no=b.emp_no
					join dept_emp c on a.emp_no=c.emp_no
					where b.to_date='9999-01-01' and c.to_date='9999-01-01'
					group by dept_no) b,
	salaries c, dept_emp d
where a.emp_no=c.emp_no and a.emp_no=d.emp_no
	and d.dept_no=b.dept_no
	and c.to_date='9999-01-01' and d.to_date='9999-01-01'
    and c.salary=b.max_salary
order by c.salary desc;

-- 문제3.
-- 현재, 자신의 부서 평균 급여보다 연봉(salary)이 많은 사원의 사번, 이름과 연봉을 조회하세요 
select a.emp_no, a.last_name, b.salary
from employees a, salaries b, dept_emp c,
	(select a.dept_no, avg(b.salary) as avg_salary
		from dept_emp a
		join salaries b on a.emp_no=b.emp_no
		where a.to_date='9999-01-01' and b.to_date='9999-01-01'
		group by a.dept_no) d
where a.emp_no=b.emp_no and a.emp_no=c.emp_no
	and c.dept_no=d.dept_no
    and b.to_date='9999-01-01' and c.to_date='9999-01-01'
    and b.salary > d.avg_salary;

-- 문제4.
-- 현재, 사원들의 사번, 이름, 매니저 이름, 부서 이름으로 출력해 보세요.
select a.emp_no, a.last_name, d.last_name, c.dept_name
from employees a, dept_emp b, departments c,
	(select b.dept_no, a.last_name
		from employees a
		join dept_manager b on a.emp_no=b.emp_no
		join dept_emp c on a.emp_no=c.emp_no
		where b.to_date='9999-01-01' and c.to_date='9999-01-01') d
where a.emp_no=b.emp_no and b.dept_no=c.dept_no
	and d.dept_no=b.dept_no
    and b.to_date='9999-01-01';
    
-- 문제5.
-- 현재, 평균연봉이 가장 높은 부서의 사원들의 사번, 이름, 직책, 연봉을 조회하고 연봉 순으로 출력하세요.
select a.emp_no, a.last_name, d.title, c.salary
from employees a
join dept_emp b on a.emp_no=b.emp_no
join salaries c on a.emp_no=c.emp_no
join titles d on a.emp_no=d.emp_no
where b.to_date='9999-01-01' and c.to_date='9999-01-01'
	and d.to_date='9999-01-01'
	and b.dept_no=(select b.dept_no
					from employees a
					join dept_emp b on a.emp_no=b.emp_no
					join salaries c on a.emp_no=c.emp_no
					where b.to_date='9999-01-01' and c.to_date='9999-01-01'
					group by b.dept_no
					order by avg(c.salary) desc
					limit 0,1)
order by c.salary;

-- 문제6.
-- 현재, 평균 연봉이 가장 높은 부서는? 
select b.dept_no, avg(c.salary)
from employees a
join dept_emp b on a.emp_no=b.emp_no
join salaries c on a.emp_no=c.emp_no
where b.to_date='9999-01-01' and c.to_date='9999-01-01'
group by b.dept_no
order by avg(c.salary) desc
limit 0,1;

-- 문제7.
-- 현재, 평균 연봉이 가장 높은 직책?
select b.title, avg(c.salary)
from employees a
join titles b on a.emp_no=b.emp_no
join salaries c on a.emp_no=c.emp_no
where b.to_date='9999-01-01' and c.to_date='9999-01-01'
group by b.title
order by avg(c.salary) desc
limit 0,1;

-- 문제8.
-- 현재 자신의 매니저보다 높은 연봉을 받고 있는 직원은?
-- 부서이름, 사원이름, 연봉, 매니저 이름, 메니저 연봉 순으로 출력합니다.
select c.dept_name '부서이름', a.last_name '사원이름',
	d.salary '사원연봉', e.last_name '매니저이름', e.salary '매니저연봉'
from employees a, dept_emp b, departments c, salaries d,
	(select b.dept_no, a.last_name, d.salary
		from employees a
		join dept_manager b on a.emp_no=b.emp_no
		join dept_emp c on a.emp_no=c.emp_no
        join salaries d on a.emp_no=d.emp_no
		where b.to_date='9999-01-01' and c.to_date='9999-01-01'
			and d.to_date='9999-01-01') e
where a.emp_no=b.emp_no and b.dept_no=c.dept_no
	and a.emp_no=d.emp_no and e.dept_no=b.dept_no
    and b.to_date='9999-01-01' and d.to_date='9999-01-01'
    and d.salary > e.salary
order by e.salary;