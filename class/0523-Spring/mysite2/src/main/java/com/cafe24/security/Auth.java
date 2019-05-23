package com.cafe24.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Auth {

//	String value() default "USER";
//	int test() default 1;
	
	public enum Role {
		USER, ADMIN
	}
	
	public Role role() default Role.USER;
}
