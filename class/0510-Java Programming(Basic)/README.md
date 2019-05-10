# Java �⺻

### Object Test1
```java
		Point p = new Point(10,20);
		
		System.out.println(p.getClass()); // reflection
		System.out.println(p.hashCode()); // reference value X, address X
										// address ����� hashing ��
		System.out.println(p);
		System.out.println(p.toString()); // getClass + "@" + hashCode
```

### Object Test2
```java
		Point p1 = new Point(10,20);
		Point p2 = new Point(10,20);
		Point p3 = p2;
		
		// == : �� ��ü�� ���ϼ�
		System.out.println(p1 == p2); // false
		System.out.println(p2 == p3); // true
		
		// equals : �� ��ü�� �������� �� (���� ��)
		  // �������̵带 ���߱� ������ ���ϼ� �񱳿� ���� ����� �Ѵ�.
		System.out.println(p1.equals(p2)); // false
		System.out.println(p2.equals(p3)); // true
		
		// Point ��ü�� hashCode�� equals�� �������̵� �ϸ� equals���� ���� �񱳿� true�� ���´�.
		// ���⼭ hashCode �������̵��� ���� ������ ������� hash ������ �����Ѵ�.(�ּұ�� X)
		// ���Ŀ� ������ ������ Ȯ�ν� �ؽ��ڵ��� ���� ���� ���Ѵ�.
		// ��, equals�� �������̵� �ϴ� ���� �ǹ̰� ����. ---> hashCode�� ���� �������̵�
		
		// String
		String s1 = new String("ABC");
		String s2 = new String("ABC");
		System.out.println(s1 == s2); // true
		System.out.println(s1.equals(s2)); // false
		System.out.println(s1.hashCode() + " : " + s2.hashCode()); // ����
		System.out.println(System.identityHashCode(s1) + " : " + System.identityHashCode(s2)); // �ٸ�
		
		String s3 = "ABC";
		String s4 = "ABC";
		System.out.println(s3 == s4); // true
		System.out.println(s3.equals(s4)); // true
		System.out.println(s3.hashCode() + " : " + s4.hashCode()); // ����
		System.out.println(System.identityHashCode(s3) + " : " + System.identityHashCode(s4)); // ����
		// �ܽ�źƮǮ
```