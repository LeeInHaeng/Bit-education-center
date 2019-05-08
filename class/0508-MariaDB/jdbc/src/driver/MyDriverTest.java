package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDriverTest {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			// 1. JDBC Driver(MariaDB) 로딩
			Class.forName("driver.MyDriver");
			
			// 2. 연결하기
			String url = "jdbc:mydb://192.168.1.51:3307/webdb";
			conn = DriverManager.getConnection(url,"webdb","webdb");
			
			System.out.println("연결 성공" + conn);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패"+e);
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
