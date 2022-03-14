package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpManager3 {
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void printEmployee(String city,int lo, int hi)throws SQLException{
		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection conn =DriverManager.getConnection(dburl,"hr","hr");
		PreparedStatement pstmt = conn.prepareStatement("select\r\n"
				+ "e.first_name,d.department_name\r\n"
				+ "from employees e, departments d,(select \r\n"
				+ "					 				d1.location_id lid,l.city\r\n"
				+ "									from departments d1,locations l\r\n"
				+ "									where d1.location_id = l.location_id\r\n"
				+ "									and city = ? \r\n"
				+ "									group by d1.location_id,l.city) vt\r\n"
				+ "where e.department_id = d.department_id\r\n"
				+ "and d.location_id = vt.lid\r\n"
				+ "and e.salary between ? and ?");
		pstmt.setString(1,city);
		pstmt.setInt(2, lo);
		pstmt.setInt(3, hi);
	
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			
			String first_name = rs.getString("first_name");
			String dN = rs.getString("department_name");
			
			System.out.println(first_name+"\t"+dN);
		}
		if(rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
	
	
	
	
	
	public static void main(String[] args) throws SQLException{
		new EmpManager3().printEmployee("South San Francisco", 7000,10000);
	}
}
