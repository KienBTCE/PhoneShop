/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.account;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class accountDAO {

    private Connection conn;

    public accountDAO() {
        conn = DB.DbConnection.GetConnection();
    }

    public ResultSet GetAll() {
        ResultSet rs = null;
        String sql = "SELECT * From Accounts";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public account getAccount(String us) {
        account a = null;
        String sql = "Select * from Accounts where a_Username=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, us);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = new account(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    public boolean login(String username, String password) {
        ResultSet rs = null;
        account acc = null;

        String sql = "Select * from Accounts where a_Username=? and a_Password=?";
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashtext);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public account checkAccountExist(String username) {
        ResultSet rs = null;
        account acc = null;

        String sql = "SELECT * FROM Accounts WHERE a_Username=?";
        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Account exists
                acc = new account(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return acc;
    }

    public boolean checkEmailExist(String email) {
        ResultSet rs = null;
        String sql = "SELECT * FROM Accounts WHERE a_Email=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Email tồn tại
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private int getNextUserId() {

        int maxId = getMaxUserIdFromDatabase();
        return maxId + 1;
    }

    private int getMaxUserIdFromDatabase() {

        String sql = "SELECT MAX(CAST(SUBSTRING(a_Id, 5, LEN(a_Id)-4) AS INT)) FROM Accounts WHERE a_Id LIKE 'user%'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int maxId = rs.getInt(1);
                return maxId;
            }
        } catch (SQLException e) {
            // Xử lý các exception ở đây
        }
        return 0; // Trả về giá trị mặc định nếu không tìm thấy
    }

    public void signup(String username, String password, String fullname, String email, String phone) {
        String sql = "insert into Accounts values(?,?,?,?,?,?,0,0)";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            int nextId = getNextUserId();
            String userId = "user" + nextId;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, username);
            ps.setString(3, hashtext);
            ps.setString(4, fullname);
            ps.setString(5, email);
            ps.setString(6, phone);

            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public account getUserByUsername(String username) {
        String sql = "SELECT * FROM Accounts WHERE a_Username = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new account(rs.getString("a_Id"), rs.getString("a_Username"), rs.getString("a_Password"),
                        rs.getString("a_Fullname"), rs.getString("a_Email"), rs.getString("a_Phone"),
                        rs.getString("a_Address"), rs.getString("a_Avatar"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateAccount(account user) {
        String sql = "UPDATE Accounts SET a_Fullname = ?, a_Email = ?, a_Phone = ?, a_Address = ? WHERE a_Username = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getA_Fullname());
            ps.setString(2, user.getA_Email());
            ps.setString(3, user.getA_Phone());
            ps.setString(4, user.getA_Address());
            ps.setString(5, user.getA_Username());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkPhoneExist(String phone) {
        ResultSet rs = null;
        String sql = "SELECT * FROM Accounts WHERE a_Phone=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Số điện thoại tồn tại
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean hasUserInfoChanged(String username, String fullname, String email, String phone, String address) {
        account user = getAccount(username);
        return user != null && (!user.getA_Fullname().equals(fullname)
                || !user.getA_Email().equals(email)
                || !user.getA_Phone().equals(phone)
                || !user.getA_Address().equals(address));
    }

    public void saveRecoveryCode(String email, String recoveryCode) {
        String sql = "INSERT INTO RecoveryCodes (email, recoveryCode) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, recoveryCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(accountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE Accounts SET a_Password = ? WHERE a_Username = ?";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(newPassword.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hashtext);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            // Xử lý các exception ở đây
        }
    }

}
