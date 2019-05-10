package bookshop.test;

import java.util.List;

import bookshop.dao.OrdersDAO;
import bookshop.vo.OrdersBookVO;
import bookshop.vo.OrdersVO;

public class OrdersDAOTest {

	public static void main(String[] args) {
		System.out.println("-------주문을 추가합니다.-------");
		insertTest(new OrdersVO(30000,"제주도1",2));
		insertTest(new OrdersVO(31000,"제주도2",2));
		insertTest(new OrdersVO(45000,"부산1",3));
		insertTest(new OrdersVO(46000,"부산2",3));
		getListTest();
		System.out.println("------------");
		
		System.out.println("-------주문을 변경합니다.-------");
		updateTest(new OrdersVO(2,"1234-1",33333,"화성",2));
		getListTest();
		System.out.println("------------");
		
		System.out.println("-------주문을 삭제합니다.-------");
		deleteTest(1L);
		getListTest();
		System.out.println("------------");
		
		System.out.println("-------주문을 도서를 추가합니다.-------");
		obInsertTest(new OrdersBookVO(2,4,1));
		obInsertTest(new OrdersBookVO(2,6,2));
		obInsertTest(new OrdersBookVO(3,1,3));
		obInsertTest(new OrdersBookVO(3,2,3));
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
