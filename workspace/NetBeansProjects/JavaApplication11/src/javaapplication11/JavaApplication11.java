
package javaapplication11;


import java.sql.*;


public class JavaApplication11 {

    
    public static void main(String[] args) {
        
        
              try {
                   Class.forName("com.mysql.jdbc.Driver") ;
                    Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/c9", "root", "") ;
                    Statement stmt =  conn.createStatement() ;
                    ResultSet rs;

                    rs = stmt.executeQuery("SELECT * FROM jugadores");
                    while ( rs.next() ) {
                        String mensaje = rs.getString("username");
                        System.out.println(mensaje);
                    }
                    conn.close();
                } catch (Exception e) {
                    System.err.println("Got an exception! ");
                    System.err.println(e.getMessage());
                }

            }
    
}
