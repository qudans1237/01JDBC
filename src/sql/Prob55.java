package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Prob55 {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void printEmployeeList(String cityName,String deptName) throws Exception{
		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection conn =DriverManager.getConnection(dburl,"hr","hr");
		PreparedStatement pstmt = conn.prepareStatement("select\r\n"
				+ "l.city, d.department_name, e.first_name, e.salary\r\n"
				+ "from employees e,departments d, locations l\r\n"
				+ "where e.department_id = d.department_id\r\n"
				+ "and d.location_id = l.location_id\r\n"
				+ "and lower(l.city) like ?\r\n"
				+ "and lower(d.department_name) like ?");
		pstmt.setString(1, "%"+cityName+"%");
		pstmt.setString(2, "%"+deptName+"%");
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String city = rs.getString("city");
			String deft_name = rs.getString("department_name");
			String first_name = rs.getString("first_name");
			int sal = rs.getInt("salary");
			
			System.out.println(city + "\t"+deft_name+ "\t"+first_name+ "\t"+sal);
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
	
	
	public static void main(String[] args) throws Exception{
		printEmployeeList("lon","resource");
	}
}
