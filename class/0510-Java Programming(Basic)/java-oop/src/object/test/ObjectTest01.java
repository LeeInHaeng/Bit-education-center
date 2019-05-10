package object.test;

public class ObjectTest01 {

	public static void main(String[] args) {
		Point p = new Point(10,20);
		
		System.out.println(p.getClass()); // reflection
		System.out.println(p.hashCode()); // reference value X, address X
										// address 기반의 hashing 값
		System.out.println(p);
		System.out.println(p.toString()); // getClass + "@" + hashCode
	}

}
