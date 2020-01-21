package pboevoy;

import java.sql.*;

public class ConnectDB {
    public String user = "";
    public String pwd = "";
    final String DB_URL = "jdbc:mysql://localhost/evoyproduction";
    Connection conn;
    Statement stmt;
    
    public ConnectDB(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }
    
    public void connect() {
        try { 
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, user, pwd);
            stmt = conn.createStatement();
            
            System.out.println("Koneksi ke database ...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection() {
        try {
            stmt.close();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public Statement getStatement() {
        return stmt;
    }
    
    public Connection getConnection() {
        return conn;
    }
}
