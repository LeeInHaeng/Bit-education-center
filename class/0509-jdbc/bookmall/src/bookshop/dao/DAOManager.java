package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOManager {

	protected Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.1.51:3307/bookmall";
			conn = DriverManager.getConnection(url,"bookmall","bookmall");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void resetData() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "delete from orders_book where order_no<9999";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			sql = "delete from orders where no<9999";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "delete from cart where book_no<9999";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "delete from member where no<9999";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "delete from book where no<9999";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "delete from category where no<9999";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "ALTER TABLE book AUTO_INCREMENT = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "ALTER TABLE category AUTO_INCREMENT = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "ALTER TABLE member AUTO_INCREMENT = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "ALTER TABLE orders AUTO_INCREMENT = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
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

	}
}
