package bookshop.test;

import java.util.List;

import bookshop.dao.OrdersDAO;
import bookshop.vo.OrdersBookVO;
import bookshop.vo.OrdersVO;

public class OrdersDAOTest {

	public static void main(String[] args) {
		insertTest(new OrdersVO(30000,"제주도",1));
		insertTest(new OrdersVO(45000,"부산",2));
		getListTest();
		System.out.println("------------");
		
		updateTest(new OrdersVO(2,"1234-1",33333,"화성",1));
		getListTest();
		System.out.println("------------");
		
		deleteTest(1L);
		getListTest();
		
		obInsertTest(new OrdersBookVO(2,4,10));
		obInsertTest(new OrdersBookVO(2,3,4));
		obGetListTest();
	}

	// create test
	public static void insertTest(OrdersVO vo) {
		new OrdersDAO().insert(vo);
	}
	
	// create ordersbook test
	public static void obInsertTest(OrdersBookVO vo) {
		new OrdersDAO().obInsert(vo);
	}

	// read test
	public static void getListTest() {
		List<OrdersVO> list = new OrdersDAO().getList();
		
		for(OrdersVO vo : list) {
			System.out.println(vo);
		}
	}
	
	// read ordersbook test
	public static void obGetListTest() {
		List<OrdersBookVO> list = new OrdersDAO().obGetList();
		
		for(OrdersBookVO vo : list) {
			System.out.println(vo);
		}
	}
	
	// update test
	public static void updateTest(OrdersVO vo) {
		new OrdersDAO().update(vo);
	}
	
	// delete test
	public static void deleteTest(Long no) {
		new OrdersDAO().delete(no);
	}
}
