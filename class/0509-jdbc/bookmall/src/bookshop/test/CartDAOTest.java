package bookshop.test;

import java.util.List;

import bookshop.dao.CartDAO;
import bookshop.vo.CartVO;

public class CartDAOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		insertTest(new CartVO(1,1,5));
		insertTest(new CartVO(2,2,3));
		getListTest();
		System.out.println("------------");
		
		updateTest(new CartVO(1,1,10,50000));
		getListTest();
		System.out.println("------------");
		
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
