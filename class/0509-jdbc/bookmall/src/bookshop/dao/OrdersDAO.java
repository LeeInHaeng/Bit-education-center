package bookshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.OrdersBookVO;
import bookshop.vo.OrdersVO;

public class OrdersDAO extends DAOManager {

	// create
	public boolean insert(OrdersVO vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "insert into orders values(null," + 
					"	(select concat(date_format(now(), '%Y%m%d'),'-'," + 
					"		(select `auto_increment` from INFORMATION_SCHEMA.TABLES" + 
					"			where table_name = 'orders')))," + 
					"	?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getPrice());
			pstmt.setString(2, vo.getAddr());
			pstmt.setLong(3, vo.getMemberNo());
			
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
	
	// ordersbook insert
	public boolean obInsert(OrdersBookVO vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "insert into orders_book values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getOrdersNo());
			pstmt.setLong(2, vo.getBookNo());
			pstmt.setLong(3, vo.getCnt());
			
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
	public List<OrdersVO> getList(){
		List<OrdersVO> result = new ArrayList<OrdersVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select a.orderNo, b.name, b.email, a.price, a.addr" + 
					"  from orders a" + 
					"  join member b on a.member_no=b.no order by a.orderNo";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String orderNo = rs.getString(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				long price = rs.getLong(4);
				String addr = rs.getString(5);
				
				OrdersVO vo = new OrdersVO();
				vo.setOrderNo(orderNo);
				vo.setMemberName(name);
				vo.setMemberEmail(email);
				vo.setPrice(price);
				vo.setAddr(addr);
				
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
	
	// ordersbook read
	public List<OrdersBookVO> obGetList(){
		List<OrdersBookVO> result = new ArrayList<OrdersBookVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select a.order_no, a.book_no, b.title, a.cnt" + 
					"  from orders_book a" + 
					"  join book b on a.book_no=b.no order by a.order_no";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long ordersNo = rs.getLong(1);
				Long bookNo = rs.getLong(2);
				String title = rs.getString(3);
				Long cnt = rs.getLong(4);
				
				OrdersBookVO vo = new OrdersBookVO();
				vo.setOrdersNo(ordersNo);
				vo.setBookNo(bookNo);
				vo.setBookTitle(title);
				vo.setCnt(cnt);
				
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
	public boolean update(OrdersVO vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "update orders set orderNo=?, price=?, addr=?, member_no=? where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getOrderNo());
			pstmt.setLong(2, vo.getPrice());
			pstmt.setString(3, vo.getAddr());
			pstmt.setLong(4, vo.getMemberNo());
			pstmt.setLong(5, vo.getNo());
			
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
	public boolean delete(Long no) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "delete from orders where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
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
