package bookshop.test;

import java.util.List;

import bookshop.dao.CategoryDAO;
import bookshop.vo.CategoryVO;


public class CategoryDAOTest {

	public static void main(String[] args) {
		
		System.out.println("-------카테고리를 추가합니다.-------");
		insertTest(new CategoryVO("카테고리1"));
		insertTest(new CategoryVO("카테고리2"));
		insertTest(new CategoryVO("카테고리3"));
		insertTest(new CategoryVO("카테고리4"));
		insertTest(new CategoryVO("카테고리5"));
		getListTest();
		System.out.println("-----------------");
		
		System.out.println("-------카테고리를 변경합니다.-------");
		updateTest(new CategoryVO(1,"카테고리 업데이트"));
		getListTest();
		System.out.println("-----------------");
		
		System.out.println("-------카테고리를 삭제합니다.-------");
		deleteTest(1L);
		getListTest();
	}

	// create test
	public static void insertTest(CategoryVO vo) {
		new CategoryDAO().insert(vo);
	}

	// read test
	public static void getListTest() {
		List<CategoryVO> list = new CategoryDAO().getList();
		
		for(CategoryVO vo : list) {
			System.out.println(vo);
		}
	}
	
	// update test
	public static void updateTest(CategoryVO vo) {
		new CategoryDAO().update(vo);
	}
	
	// delete test
	public static void deleteTest(Long no) {
		new CategoryDAO().delete(no);
	}
}
