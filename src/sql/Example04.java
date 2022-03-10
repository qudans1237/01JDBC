package sql;

import java.sql.*;
import java.util.Scanner;

public class Example04 {
	public static void main(String[] args) throws Exception {

		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String driver = "oracle.jdbc.driver.OracleDriver";

		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, "scott", "tiger");
		PreparedStatement pstmt = con.prepareStatement("SELECT id,name,salary FROM emp_test WHERE salary<?");
		pstmt.setInt(1, 10000);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int userId = rs.getInt("ID");
			String userName = rs.getString("NAME");
			int usersl = rs.getInt("SALARY");

			System.out.println("번호: " + userId + "\t이름: " + userName + "\t급여: " + usersl);
		}

		rs.close();
		pstmt.close();
		con.close();
	}
}
