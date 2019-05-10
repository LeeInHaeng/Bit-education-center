package bookshop.test;

import java.util.List;

import bookshop.dao.MemberDAO;
import bookshop.vo.MemberVO;

public class MemberDAOTest {

	public static void main(String[] args) {
		System.out.println("-------맴버를 생성합니다.-------");;
		insertTest(new MemberVO("사용자이름111","010-1234-1234","aaa@aaa.com","hihi","서울시 강남구"));
		insertTest(new MemberVO("사용자이름222","010-2345-5566","bbb@bbb.com","byby","서울시 마포구"));
		insertTest(new MemberVO("사용자이름333","010-1212-2323","Ccc@ccc.com","asdf","서울시 서대문구"));
		getListTest();
		System.out.println("------------");
		
		System.out.println("-------맴버를 바꿉니다.-------");
		updateTest(new MemberVO(1,"바뀐사용자1234","010-1111-2222","uuu@uuu.com","asdf","서울시 바뀐주소"));
		getListTest();
		System.out.println("------------");
		
		System.out.println("-------맴버를 삭제합니다.-------");
		deleteTest(1L);
		getListTest();
	}

	// create test
	public static void insertTest(MemberVO vo) {
		new MemberDAO().insert(vo);
	}

	// read test
	public static void getListTest() {
		List<MemberVO> list = new MemberDAO().getList();
		
		for(MemberVO vo : list) {
			System.out.println(vo);
		}
	}
	
	// update test
	public static void updateTest(MemberVO vo) {
		new MemberDAO().update(vo);
	}
	
	// delete test
	public static void deleteTest(Long no) {
		new MemberDAO().delete(no);
	}
}
