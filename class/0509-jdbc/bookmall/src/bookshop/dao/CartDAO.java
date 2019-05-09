package bookshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.CartVO;

public class CartDAO extends DAOManager {

	// create
	public boolean insert(CartVO vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "insert into cart values(?,?,?," + 
					"  (select price from book where no=?)*?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getBookNo());
			pstmt.setLong(2, vo.getMemberNo());
			pstmt.setLong(3, vo.getCnt());
			pstmt.setLong(4, vo.getBookNo());
			pstmt.setLong(5, vo.getCnt());
			
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return result;
	}
	
	
	// read
	public List<CartVO> getList(){
		List<CartVO> result = new ArrayList<CartVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select a.member_no, b.title, a.cnt, a.price " + 
					" from cart a " + 
					" join book b on a.book_no=b.no order by a.member_no";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				long no = rs.getLong(1);
				String title = rs.getString(2);
				long cnt = rs.getLong(3);
				long price = rs.getLong(4);
				
				CartVO vo = new CartVO();
				vo.setMemberNo(no);
				vo.setBookTitle(title);
				vo.setCnt(cnt);
				vo.setPrice(price);
				
				result.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return result;
	}
	
	// update
	public boolean update(CartVO vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "update cart set cnt=?, price=? where book_no=? and member_no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getCnt());
			pstmt.setLong(2, vo.getPrice());
			pstmt.setLong(3, vo.getBookNo());
			pstmt.setLong(4, vo.getMemberNo());
			
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return result;
	}
	
	// delete
	public boolean delete(Long bookNo, Long memberNo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "delete from cart where book_no=? and member_no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, bookNo);
			pstmt.setLong(2, memberNo);
			
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return result;
	}
}
