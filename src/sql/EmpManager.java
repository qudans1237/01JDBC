package sql;

import java.sql.Connection;
import java.util.*;
import java.lang.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpManager {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	
	public void printEmployee(String name,int salary) throws SQLException{
		String upperCaseFirst=name;
		char[] arr = upperCaseFirst.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		new String(arr);
		
		
		String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection conn =DriverManager.getConnection(dburl,"hr","hr");
		PreparedStatement pstmt = conn.prepareStatement("select \r\n"
				+ "e.employee_id,e.first_name, e.salary\r\n"
				+ "from employees e,(select employee_id from employees where salary > ? ) e1\r\n"
				+ "where e.employee_id = e1.employee_id\r\n"
				+ "and first_name like ? or first_name like ? \r\n"
				+ "and e.employee_id = e1.employee_id");
		pstmt.setInt(1 , salary);
		pstmt.setString(2,new String(arr)+"%");
		pstmt.setString(3,"%"+name+"%");
		
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
		
		new EmpManager().printEmployee("al",4000);
	}
}
