package com.cafe24.springContainer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cafe24.springContainer.user.User;

import config.user.UserConfig01;

public class JavaConfigTest01 {

	public static void main(String[] args) {
		testUser01();
		testUser02();
	}

	public static void testUser01() {
		ApplicationContext appCtx = 
				new AnnotationConfigApplicationContext(UserConfig01.class);
		
		User user = appCtx.getBean(User.class);
		System.out.println(user);

	}
	
	public static void testUser02() {
		ApplicationContext appCtx = 
				new AnnotationConfigApplicationContext("config.user");
		
		User user = appCtx.getBean(User.class);
		System.out.println(user);
	
	}
}
