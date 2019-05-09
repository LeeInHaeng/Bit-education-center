package bookshop.test;

import java.util.List;

import bookshop.dao.BookDAO;
import bookshop.vo.BookVO;

public class BookDAOTest {

	public static void main(String[] args) {

		for(int i=0; i<20; i++) {
			insertTest(new BookVO("제목"+i, i+10000+i*50, (i%6)+1));
		}
		getListTest();
		System.out.println("-------------");
		
		updateTest(new BookVO(4,"수정된 제목",12341234,1));
		getListTest();
		System.out.println("-------------");
		
		deleteTest(5L);
		getListTest();
	}

	// create test
	public static void insertTest(BookVO vo) {
		new BookDAO().insert(vo);
	}

	// read test
	public static void getListTest() {
		List<BookVO> list = new BookDAO().getList();
		
		for(BookVO vo : list) {
			System.out.println(vo);
		}
	}
	
	// update test
	public static void updateTest(BookVO vo) {
		new BookDAO().update(vo);
	}
	
	// delete test
	public static void deleteTest(Long no) {
		new BookDAO().delete(no);
	}
}
