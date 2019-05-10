package bookshop.test;

import java.util.List;

import bookshop.dao.BookDAO;
import bookshop.vo.BookVO;

public class BookDAOTest {

	public static void main(String[] args) {

		System.out.println("-------책을 추가합니다.-------");
		insertTest(new BookVO("책 제목222", 11000, 2));
		insertTest(new BookVO("책 제목333", 12000, 3));
		insertTest(new BookVO("책 제목444", 13000, 4));
		insertTest(new BookVO("책 제목555", 14000, 5));
		insertTest(new BookVO("책 제목2222", 15000, 2));
		insertTest(new BookVO("책 제목3333", 16000, 3));
		insertTest(new BookVO("책 제목4444", 17000, 4));
		insertTest(new BookVO("책 제목5555", 18000, 5));
		getListTest();
		System.out.println("-------------");
		
		System.out.println("-------책을 변경합니다.-------");
		updateTest(new BookVO(4,"수정된 제목",12341234,2));
		getListTest();
		System.out.println("-------------");
		
		System.out.println("-------책을 삭제합니다.-------");
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
