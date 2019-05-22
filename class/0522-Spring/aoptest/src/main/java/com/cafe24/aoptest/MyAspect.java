package com.cafe24.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

	@Before("execution(ProductVo com.cafe24.aoptest.ProductService.find(String))")
	public void beforeAdvice() {
		System.out.println("--- before advice ---");
	}
	
	@After("execution(* *..*.ProductService.*(..))")
	public void afterAdvice() {
		System.out.println("--- after advice ---");
	}
	
	@AfterReturning("execution(* *..*.ProductService.*(..))")
	public void afterReturningAdvice() {
		System.out.println("--- afterReturning advice ---");
	}
	
	@AfterThrowing(value="execution(* *..*.ProductService.*(..))", throwing="ex")
	public void afterThrowingAdvice(Throwable ex) {
		System.out.println("--- afterThrowing advice ---" + ex);
	}
	
	@Around("execution(* *..*.ProductService.*(..))")
	public Object roundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		
		/* before advice */
		System.out.println("--- around before advice ---");
		
		// PointCut 되는 메서드 호출
		Object [] parameters = {"Camera"};
		Object result = pjp.proceed(parameters); // 중간에 파라미터 변경 가능
		
		//Object result = pjp.proceed(); // 파라미터가 없으면 그대로 호출
		
		
		/* after advice */
		System.out.println("--- around after advice ---");
		
		return result;
	}
}
