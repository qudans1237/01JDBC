package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Prob5 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("부서의 아이디를 입력하세요...");

		int dept_id = sc.nextInt();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "hr";
		String password = "hr";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement("select \r\n"
					+ "e.employee_id, d.department_name, e.salary, vt.AVG AVG_SALARY \r\n"
					+ "from employees e,(select \r\n" + "				department_id, round(avg(salary),0) AVG\r\n"
					+ "				from employees\r\n" + "				where department_id = ? \r\n"
					+ "				group by department_id) vt, departments d\r\n"
					+ "where e.department_id = vt.department_id\r\n" + "and vt.department_id = d.department_id\r\n"
					+ "order by salary desc");
			pstmt.setInt(1, dept_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int employeeId = rs.getInt("employee_id");
				String departmentName = rs.getString("department_name");
				int sal = rs.getInt("salary");
				int vaAVG = rs.getInt("AVG_SALARY");

				System.out.println(employeeId + "\t" + departmentName + "\t" + sal + "\t" + vaAVG);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("\n==>Driver Loading시 Exception 발생\n");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("\n==>JDBC 절차 중 Exception 발생 : " + e.getErrorCode());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}