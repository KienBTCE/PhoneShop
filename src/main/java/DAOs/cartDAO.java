/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.cart;
import Models.orderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class cartDAO {

    private Connection conn;

    public cartDAO() {
        conn = DB.DbConnection.GetConnection();
    }

    public ResultSet getAllCarts() {
        ResultSet rs = null;
        try {
            String sql = "Select * from Carts";
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(cartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public List<cart> getCartByUser(String a_Id) {
        List<cart> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "Select * from Carts WHERE a_Id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a_Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new cart(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(cartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void addCartItem(cart ct) {
        int kq = 0;
        String sql = "Insert INTO Carts VALUES(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ct.getA_Id());
            ps.setString(2, ct.getPd_Id());
            ps.setInt(3, ct.getCt_quantity());
            kq = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(cartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateCartItem(cart ct) {
        int kq = 0;
        String sql = "UPDATE Carts SET ct_quantity=? where pd_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ct.getCt_quantity());
            ps.setString(2, ct.getPd_Id());
            kq = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(cartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearCart(String a_Id) {
        int kq = 0;
        try {
            String sql = "DELETE FROM Carts WHERE a_Id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a_Id);
            kq = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(cartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void deleteCartItem(String a_Id, String pd_Id) {
        int kq = 0;
        try {
            String sql = "DELETE FROM Carts WHERE a_Id=? AND pd_Id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a_Id);
            ps.setString(2, pd_Id);
            kq = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(cartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
