package bookshop.test;

import java.util.List;

import bookshop.dao.CartDAO;
import bookshop.vo.CartVO;

public class CartDAOTest {

	public static void main(String[] args) {
		System.out.println("-------카트를 추가합니다.-------");
		insertTest(new CartVO(1,2,2));
		insertTest(new CartVO(2,2,3));
		insertTest(new CartVO(3,2,4));
		insertTest(new CartVO(1,3,1));
		insertTest(new CartVO(2,3,2));
		insertTest(new CartVO(3,3,3));
		getListTest();
		System.out.println("------------");
		
		System.out.println("-------카트를 변경합니다.-------");
		updateTest(new CartVO(1,2,10,50000));
		getListTest();
		System.out.println("------------");
		
		System.out.println("-------카트를 삭제합니다.-------");
		deleteTest(1L,1L);
		getListTest();
		
	}

	// create test
	public static void insertTest(CartVO vo) {
		new CartDAO().insert(vo);
	}

	// read test
	public static void getListTest() {
		List<CartVO> list = new CartDAO().getList();
		
		for(CartVO vo : list) {
			System.out.println(vo);
		}
	}
	
	// update test
	public static void updateTest(CartVO vo) {
		new CartDAO().update(vo);
	}
	
	// delete test
	public static void deleteTest(Long bookNo, Long memberNo) {
		new CartDAO().delete(bookNo, memberNo);
	}
}
