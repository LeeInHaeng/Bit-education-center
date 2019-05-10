# Java 기본

### Object Test1
```java
		Point p = new Point(10,20);
		
		System.out.println(p.getClass()); // reflection
		System.out.println(p.hashCode()); // reference value X, address X
										// address 기반의 hashing 값
		System.out.println(p);
		System.out.println(p.toString()); // getClass + "@" + hashCode
```

### Object Test2
```java
		Point p1 = new Point(10,20);
		Point p2 = new Point(10,20);
		Point p3 = p2;
		
		// == : 두 객체의 동일성
		System.out.println(p1 == p2); // false
		System.out.println(p2 == p3); // true
		
		// equals : 두 객체의 동질성을 비교 (내용 비교)
		  // 오버라이드를 안했기 때문에 동일성 비교와 같은 기능을 한다.
		System.out.println(p1.equals(p2)); // false
		System.out.println(p2.equals(p3)); // true
		
		// Point 객체의 hashCode와 equals를 오버라이드 하면 equals에서 내용 비교에 true가 나온다.
		// 여기서 hashCode 오버라이딩은 안의 내용을 기반으로 hash 값으로 변경한다.(주소기반 X)
		// 이후에 내용이 같은지 확인시 해시코드의 정수 값을 비교한다.
		// 즉, equals만 오버라이딩 하는 것은 의미가 없다. ---> hashCode와 같이 오버라이딩
		
		// String
		String s1 = new String("ABC");
		String s2 = new String("ABC");
		System.out.println(s1 == s2); // true
		System.out.println(s1.equals(s2)); // false
		System.out.println(s1.hashCode() + " : " + s2.hashCode()); // 같음
		System.out.println(System.identityHashCode(s1) + " : " + System.identityHashCode(s2)); // 다름
		
		String s3 = "ABC";
		String s4 = "ABC";
		System.out.println(s3 == s4); // true
		System.out.println(s3.equals(s4)); // true
		System.out.println(s3.hashCode() + " : " + s4.hashCode()); // 같음
		System.out.println(System.identityHashCode(s3) + " : " + System.identityHashCode(s4)); // 같음
		// 콘스탄트풀
```