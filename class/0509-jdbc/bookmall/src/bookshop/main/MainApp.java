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
			bookDAO.insert(new BookVO("책 제목"+i, i+10000+i*50, (i%6)+1));
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
		
		System.out.println("------주문 정보를 출력 합니다.------");
		List<OrdersVO> ordersList = ordersDAO.getList();
		for(OrdersVO vo : ordersList) {
			System.out.println(vo);
		}
		
		// 주문 도서 추가
		System.out.println("-----주문 도서를 추가 합니다.-----");
		ordersDAO.obInsert(new OrdersBookVO(1,2,5));
		ordersDAO.obInsert(new OrdersBookVO(2,4,6));
		
		System.out.println("-----주문 도서 정보를 출력 합니다.------");
		List<OrdersBookVO> ordersBookList = ordersDAO.obGetList();
		for(OrdersBookVO vo : ordersBookList) {
			System.out.println(vo);
		}
	}

}
