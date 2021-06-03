package net.sqlitetutorial;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

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

    public static int[] getBalance() {
        int[] sum = {0,0};
        String sql = "SELECT BALANCE FROM market WHERE TAXID = ?";
        
        // market sum
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1,activeUserID);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                sum[0] += rs.getInt("BALANCE");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "SELECT stock.SHARES, asinfo.CURRENTPRICE "
                + "FROM stock INNER JOIN asinfo "
                + "ON stock.ACTORID = asinfo.ACTORID "
                + "WHERE stock.TAXID = ?";
        
        // market sum
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1,activeUserID);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                sum[1] += rs.getInt("SHARES") * rs.getInt("CURRENTPRICE");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return sum;
    }

    public static boolean updateMarket(int amount){

        String sql = "SELECT BALANCE FROM market WHERE TAXID = ?";
        
        int[] balance = getBalance();
        if (balance[0] < 1000+amount) {
            System.out.println("balance below 1000");
            return false;
        }

        sql = "UPDATE market SET BALANCE = BALANCE + ? "
                + "WHERE TAXID = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, amount);
            pstmt.setInt(2, activeUserID);
            
            int r = pstmt.executeUpdate();
            System.out.println(r);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean updateStock(int amount, String actor){

        // check if the stock is real
        String sql = "SELECT COUNT(*) FROM asinfo WHERE ACTORID = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, actor);
            ResultSet rs  = pstmt.executeQuery();
            
            if (rs.getInt("COUNT(*)") == 0) {
                System.out.println("actor doesn't exist");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "UPDATE stock SET SHARES = SHARES + ? "
                + "WHERE TAXID = ? AND ACTORID = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, amount);
            pstmt.setInt(2, activeUserID);
            pstmt.setString(3, actor);
            
            int r = pstmt.executeUpdate();
            System.out.println(r);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
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

    public static String[][] getStocks() {
        ArrayList<String[]> al = new ArrayList<String[]>();

        String sql = "SELECT asinfo.ACTORID, asinfo.CURRENTPRICE, stock.SHARES "
                + "FROM stock INNER JOIN asinfo "
                + "ON stock.ACTORID = asinfo.ACTORID "
                + "WHERE stock.TAXID = ?";
        
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, activeUserID);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                String[] act = new String[3];
                for (int j=0; j < 3; j++){
                    act[j] = rs.getString(j+1);
                }
                al.add(act);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String[][] array = new String[al.size()][];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(i);
        }
        return array;
    }

    public static String[][] getMovies() {
        ArrayList<String[]> al = new ArrayList<String[]>();

        String sql = "SELECT ACTORID,CURRENTPRICE,NAME,DOB,MOVIETITLE,ROLE,YEAR,CONTRACT FROM asinfo";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                String[] act = new String[8];
                for (int j=0; j < 8; j++){
                    act[j] = rs.getString(j+1);
                }
                al.add(act);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String[][] array = new String[al.size()][];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(i);
        }
        return array;
    }

    public static String[][] getActiveUser() {
        ArrayList<String[]> al = new ArrayList<String[]>();

        String sql = "SELECT ACTORID,CURRENTPRICE,NAME,DOB,MOVIETITLE,ROLE,YEAR,CONTRACT FROM asinfo";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                String[] act = new String[8];
                for (int j=0; j < 8; j++){
                    act[j] = rs.getString(j+1);
                }
                al.add(act);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String[][] array = new String[al.size()][];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(i);
        }
        return array;
    }

    public static boolean setStock(double price, String actor) {
        // check if the stock is real
        String sql = "SELECT COUNT(*) FROM asinfo WHERE ACTORID = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, actor);
            ResultSet rs  = pstmt.executeQuery();
            
            if (rs.getInt("COUNT(*)") == 0) {
                System.out.println("actor doesn't exist");

                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "UPDATE asinfo SET CURRENTPRICE = ? "
                + "WHERE ACTORID = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setDouble(1, price);
            pstmt.setString(2, actor);
            
            int r = pstmt.executeUpdate();
            System.out.println(r);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}