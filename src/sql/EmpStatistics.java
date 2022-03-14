package sql;

import java.sql.*;

public class EmpStatistics {
	
	static {
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	
	public void printStatistics(int maxSalary) throws SQLException {
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		
		Connection con = DriverManager.getConnection(url, "hr", "hr");
		PreparedStatement pstmt = con.prepareStatement("select \r\n"
				+ "j.job_title, AVG(e.salary) \r\n"
				+ "from employees e, jobs j \r\n"
				+ " where e.job_id = j.job_id and e.salary >= ? \r\n"
				+ " GROUP BY j.JOB_TITLE \r\n"
				+ " ORDER BY AVG(e.salary) desc");
		pstmt.setInt(1,maxSalary);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("=====================================");
		System.out.println("Max Salary : " + maxSalary);
		System.out.println("=====================================");
		
		while(rs.next()) {
		String jobTitle = rs.getString("job_title");
		int avgsal = rs.getInt("AVG(e.salary)") ;
		
		
		
		System.out.println("["+jobTitle+"]"+"\t"+avgsal);
		}
		
		if(rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (con != null) {
			con.close();
		}

	}
	
	public static void main(String[] args) throws SQLException {
		
		int maxSalary = 10000;
		new EmpStatistics().printStatistics(maxSalary);
		
		maxSalary= 15000;
		new EmpStatistics().printStatistics(maxSalary);
	}
}

