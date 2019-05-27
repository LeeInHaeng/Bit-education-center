package com.cafe24.springContainer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.cafe24.springContainer.user.User;
import com.cafe24.springContainer.user.User1;

public class ContainerTest {

	public static void main(String[] args) {
		//testBeanFactory();
		testApplicationContext();
	}
	
	public static void testApplicationContext() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("config/user/applicationContext2.xml");
		
		// 빈 설정은 id나 name 설정을 해야 한다.
		//User1 user = (User1)appContext.getBean("user1");
		//System.out.println(user.getName());
		
//		User1 user1 = appContext.getBean(User1.class);
//		System.out.println(user1.getName());
		
		// name으로 가져오기
		User user = (User)appContext.getBean("user");
		System.out.println(user);
		
		// id로 가져오기
		user = (User)appContext.getBean("usr");
		System.out.println(user);
		
		// 오류 : 같은 타입의 빈이 2개 이상 존재하면 타입으로 빈을 가져올 수 없다.
		//user = appContext.getBean(User.class);
		
		User user2 = (User)appContext.getBean("usr2");
		System.out.println(user2);
		
		User user3 = (User)appContext.getBean("usr3");
		System.out.println(user3);
		
		User user4 = (User)appContext.getBean("usr4");
		System.out.println(user4);
		
		User user5 = (User)appContext.getBean("usr5");
		System.out.println(user5);
		
		User user6 = (User)appContext.getBean("usr6");
		System.out.println(user6);
		
		User user7 = (User)appContext.getBean("usr7");
		System.out.println(user7);
		
		((ConfigurableApplicationContext)appContext).close();
	}

	public static void testBeanFactory() {
		BeanFactory bf1 = 
				new XmlBeanFactory( new ClassPathResource( "config/user/applicationContext.xml" ) );
		
		// Auto-Configuration(Scanning)인 경우
		// Bean의 id가 자동으로 만들어 진다. (앞의 문자가 소문자로해서 클래스 이름으로)
		User1 user = (User1)bf1.getBean("user1");
		System.out.println(user.getName());
		
		// 수동 설정일 경우 자동으로 id가 부여되지 않는다.
		BeanFactory bf2 = 
				new XmlBeanFactory( new ClassPathResource( "config/user/applicationContext2.xml" ) );
		//user = (User1)bf2.getBean("user1");
		user = (User1)bf2.getBean(User1.class);
		System.out.println(user.getName());
	}
}
