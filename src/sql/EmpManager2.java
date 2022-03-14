package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpManager2 {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void printEmployee(String jobs[]) throws SQLException{
		
		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection conn =DriverManager.getConnection(dburl,"hr","hr");
		PreparedStatement pstmt = conn.prepareStatement("select\r\n"
				+ "e.employee_id,e.first_name,e.salary\r\n"
				+ "from employees e,(Select\r\n"
				+ "				e.employee_id,j.job_id\r\n"
				+ "				from employees e, jobs j\r\n"
				+ "				where e.job_id = j.job_id\r\n"
				+ "				and j.job_title = ? \r\n"
				+ "				or j.job_title = ?) je\r\n"
				+ "where e.employee_id = je.employee_id\r\n"
				+ "and e.job_id = je.job_id");
		pstmt.setString(1, jobs[0]);
		pstmt.setString(2, jobs[1]);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int employeeId = rs.getInt("employee_id");
			String first_name = rs.getString("first_name");
			int sal = rs.getInt("salary");
			
			System.out.println(employeeId+"\t"+first_name+"\t"+sal);
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
		
		String[] jobs = {"Accountant","Stock Clerk"};
		new EmpManager2().printEmployee(jobs);
	}
	
}
