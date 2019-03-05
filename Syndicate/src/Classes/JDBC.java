/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author randika
 */
public class JDBC {
    private String url="jdbc:mysql://localhost:3307/syndicate";
    private String username="root";
    private String password="1234";
    Connection c=null;
    private String sql;
    public Connection connector()throws Exception{
        if (c==null) {
            Class.forName("com.mysql.jdbc.Driver");
            c=DriverManager.getConnection(url, "root", "1234");
            return c;
        } else {
            return c;
        }
    
    
    }

    
    public void putData(String SQL) throws Exception{
        Statement s=connector().createStatement();
        s.executeUpdate(SQL);
        
    }
    
    public ResultSet getData(String SQL) throws Exception{
        Statement s=connector().createStatement();
        ResultSet rs=s.executeQuery(SQL);
        return rs;
    }
    
    
}
