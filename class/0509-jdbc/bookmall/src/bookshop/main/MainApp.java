package bookshop.main;

import java.util.List;

import bookshop.dao.BookDAO;
import bookshop.dao.CartDAO;
import bookshop.dao.CategoryDAO;
import bookshop.dao.DAOManager;
import bookshop.dao.MemberDAO;
import bookshop.dao.OrdersDAO;
import bookshop.vo.BookVO;
import bookshop.vo.CartVO;
import bookshop.vo.CategoryVO;
import bookshop.vo.MemberVO;
import bookshop.vo.OrdersBookVO;
import bookshop.vo.OrdersVO;

public class MainApp {

	public static void main(String[] args) {

		// 테이블 안의 모든 정보 리셋
		System.out.println("-----테이블 안의 모든 데이터를 초기화 합니다.------");
		new DAOManager().resetData();
		
		// 카테고리 생성
		System.out.println("-----카테고리를 생성합니다.------");
		CategoryDAO categoryDAO = new CategoryDAO();
		
		categoryDAO.insert(new CategoryVO("소설"));
		categoryDAO.insert(new CategoryVO("수필"));
		categoryDAO.insert(new CategoryVO("컴퓨터/IT"));
		categoryDAO.insert(new CategoryVO("인문"));
		categoryDAO.insert(new CategoryVO("경제"));
		categoryDAO.insert(new CategoryVO("예술"));

		System.out.println("------카테고리를 출력합니다.--------");
		List<CategoryVO> categoryList = categoryDAO.getList();
		for(CategoryVO vo : categoryList) {
			System.out.println(vo);
		}
		
		// 책 추가
		System.out.println("-------책을 추가 합니다.---------");
		BookDAO bookDAO = new BookDAO();
		
		for(int i=0; i<10; i++) {
			bookDAO.insert(new BookVO("책 제목"+i, i+10000+i*500, (i%6)+1));
		}
		
		System.out.println("-------책을 출력합니다.---------");
		List<BookVO> bookList = bookDAO.getList();
		for(BookVO vo : bookList) {
			System.out.println(vo);
		}
		
		// 회원 정보 추가
		System.out.println("-----회원 정보를 추가 합니다.------");
		MemberDAO memberDAO = new MemberDAO();
		memberDAO.insert(new MemberVO("사용자이름111","010-1234-1234","aaa@aaa.com","hihi","서울시 강남구"));
		memberDAO.insert(new MemberVO("사용자이름222","010-2345-5566","bbb@bbb.com","byby","서울시 마포구"));
		
		System.out.println("------회원 정보를 출력 합니다.------");
		List<MemberVO> memberList = memberDAO.getList();
		for(MemberVO vo : memberList) {
			System.out.println(vo);
		}
		
		// 카트 추가
		System.out.println("------카트를 추가 합니다.------");
		CartDAO cartDAO = new CartDAO();
		cartDAO.insert(new CartVO(1,1,5));
		cartDAO.insert(new CartVO(2,2,3));
		
		System.out.println("------카트 정보를 출력 합니다.------");
		List<CartVO> cartList = cartDAO.getList();
		for(CartVO vo : cartList) {
			System.out.println(vo);
		}
		
		// 주문 추가
		System.out.println("--------주문을 추가합니다.--------");
		OrdersDAO ordersDAO = new OrdersDAO();
		ordersDAO.insert(new OrdersVO(30000,"제주도",1));
		ordersDAO.insert(new OrdersVO(45000,"부산",2));
		
		// 1번 사용자가 이후에 한번 더 주문 (이번 배송지는 서울)
		ordersDAO.insert(new OrdersVO(35000,"서울",1));
		// 2번 사용자가 이후에 한번 더 주문 (이번 배송지는 경기도)
		ordersDAO.insert(new OrdersVO(15000,"경기도",2));
		
		System.out.println("------주문 정보를 출력 합니다.------");
		List<OrdersVO> ordersList = ordersDAO.getList();
		for(OrdersVO vo : ordersList) {
			System.out.println(vo);
		}
		
		// 주문 도서 추가
		System.out.println("-----주문 도서를 추가 합니다.-----");
		// 1번 주문이 각각 2,3,4,5의 책을 1권씩 주문
		ordersDAO.obInsert(new OrdersBookVO(1,2,1));
		ordersDAO.obInsert(new OrdersBookVO(1,3,1));
		ordersDAO.obInsert(new OrdersBookVO(1,4,1));
		ordersDAO.obInsert(new OrdersBookVO(1,5,1));
		
		// 2번 주문이 각각 4,5,6의 책을 2권씩 주문
		ordersDAO.obInsert(new OrdersBookVO(2,4,2));
		ordersDAO.obInsert(new OrdersBookVO(2,5,2));
		ordersDAO.obInsert(new OrdersBookVO(2,6,2));
		
		// 3번 주문(즉, 1번 사용자가 한번 더 주문)이 각각 1,2,5의 책을 3권씩 주문
		ordersDAO.obInsert(new OrdersBookVO(3,1,3));
		ordersDAO.obInsert(new OrdersBookVO(3,2,3));
		ordersDAO.obInsert(new OrdersBookVO(3,5,3));
		
		// 4번 주문(즉, 2번 사용자가 한번 더 주문)이 각각 1,2,3의 책을 1권씩 주문
		ordersDAO.obInsert(new OrdersBookVO(2,1,1));
		ordersDAO.obInsert(new OrdersBookVO(2,2,1));
		ordersDAO.obInsert(new OrdersBookVO(2,3,1));
		
		System.out.println("-----전체 주문 도서 정보를 출력 합니다.------");
		List<OrdersBookVO> ordersBookList = ordersDAO.obGetList();
		for(OrdersBookVO vo : ordersBookList) {
			System.out.println(vo);
		}
		
		System.out.println("----특정 사용자의 주문 도서 정보만 출력 합니다. (1번 사용자)");
		List<OrdersBookVO> ordersBookList2 = ordersDAO.obGetList(1L);
		for(OrdersBookVO vo : ordersBookList2) {
			System.out.println(vo);
		}
		
		System.out.println("-----프로그램 정상 마침--------");
	}

}
