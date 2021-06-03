package net.sqlitetutorial;

import java.sql.*;

/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {
    static int activeUserID;
     /**
     * Connect to a sample database
     */
    private static Connection connect() {
        String url = "jdbc:sqlite:../../db/project.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static String adminLogin(String username, String password){
        String sql = "SELECT NAME,USERNAME,PASSWORD,TAXID FROM admin";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                if(rs.getString("USERNAME").equals(username)) {
                    if(rs.getString("PASSWORD").equals(password)) {
                        activeUserID = rs.getInt("TAXID");
                        return rs.getString("NAME");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String userLogin(String username, String password){
        String sql = "SELECT NAME,USERNAME,PASSWORD,TAXID FROM customer";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                if(rs.getString("USERNAME").equals(username)) {
                    if(rs.getString("PASSWORD").equals(password)) {
                        activeUserID = rs.getInt("TAXID");
                        return rs.getString("NAME");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean registerUser(String name, String username, 
                                        String password, String address, 
                                        String state, String phone, 
                                        String email, String taxid, 
                                        String ssn, boolean admin){
        
        String sql;
        if (admin)
            sql = "INSERT INTO admin(NAME,USERNAME,PASSWORD,ADDRESS,STATE,PHONE,EMAIL,TAXID,SSN) VALUES(?,?,?,?,?,?,?,?,?)";
        else
            sql = "INSERT INTO customer(NAME,USERNAME,PASSWORD,ADDRESS,STATE,PHONE,EMAIL,TAXID,SSN) VALUES(?,?,?,?,?,?,?,?,?)";


        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, address);
            pstmt.setString(5, state);
            pstmt.setString(6, phone);
            pstmt.setString(7, email);
            pstmt.setInt(8, Integer.parseInt(taxid));
            pstmt.setString(9, ssn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}