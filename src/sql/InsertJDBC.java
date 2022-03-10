package sql;

import java.sql.*;

/*
 * FileName : InsertJDBC.java
 */


public class InsertJDBC {

   public static void main(String[] args) throws Exception {
      String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
      String driver = "oracle.jdbc.driver.OracleDriver";
      
      Class.forName(driver);
      
      Connection con = DriverManager.getConnection(url,"scott","tiger");
      
      Statement stmt = con.createStatement();
      
      //DML : INSERT
      String sql1= "INSERT INTO firstJDBC VALUES(1,'홍길동','a@lycos.co.kr','한양')";
      String sql2= "INSERT INTO firstJDBC VALUES(2,'홍길순','b@lycos.co.kr','한양')";
      String sql3= "INSERT INTO firstJDBC VALUES(3,'이순신','c@lycos.co.kr','한양')";
      
      //executeUpdate() : DML 전송
      int i = stmt.executeUpdate(sql1);
      System.out.println("1번 insert 유무 : "+i+"개의 행이 만들어졌습니다.");
      
      i = stmt.executeUpdate(sql2);
      System.out.println("2번 insert 유무 : "+i+"개의 행이 만들어졌습니다.");
      
      i = stmt.executeUpdate(sql3);
      System.out.println("3번 insert 유무 : "+i+"개의 행이 만들어졌습니다.");
      
      if(stmt != null) {
         stmt.cancel();
      }
      if(con!=null) {
         con.close();
      }
   }
}