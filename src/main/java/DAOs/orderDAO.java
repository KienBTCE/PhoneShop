/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.order;
import Models.orderInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class orderDAO {

    private Connection conn;

    public orderDAO() {
        conn = DB.DbConnection.GetConnection();
    }

    public List<order> GetAllOrders() {
        List<order> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "Select * from [Orders]";
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                list.add(new order(rs.getInt("o_Id"),rs.getDate("o_DateOrder"), rs.getString("o_Status"), rs.getString("a_Id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getMaxId() {
        int o_Id = 0;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Orders WHERE o_Id = (SELECT MAX(o_Id) FROM Orders)";

            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                o_Id = rs.getInt("o_Id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o_Id;
    }

    public List<order> getOrderByAId(String a_Id) {
        List<order> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            String sql = "SELECT * FROM Orders WHERE a_Id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a_Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new order(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public order getOnGoingOrder(String a_Id) {
        order or = null;
        try {
            ResultSet rs = null;
            String sql = "SELECT * FROM Orders WHERE a_Id=? AND o_Status=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a_Id);
            ps.setString(2, "On_going");
            rs = ps.executeQuery();
            while (rs.next()) {
                or = new order(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return or;
    }
    public order getOrderByOid(int o_Id) {
        order or = null;
        try {
            ResultSet rs = null;
            String sql = "SELECT * FROM Orders WHERE o_Id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o_Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                or = new order(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return or;
    }

    public orderInfo getOrderInfo(int o_Id) {
        order or = null;
        orderInfo oi = null;
        try {
            ResultSet rs = null;
            String sql = "SELECT * FROM Orders_Info WHERE o_Id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o_Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                oi = new orderInfo(rs.getString("oi_Fullname"), rs.getString("oi_Phone"), rs.getString("oi_Address"), rs.getDouble("oi_TotalPrice"), rs.getInt("o_Id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oi;
    }

    public void addOrder(order newOd) {
        try {
            String sql = "Insert into [Orders] values(?,?,?,?)";
            int kq = 0;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newOd.getO_Id());
            ps.setDate(2, newOd.getO_DateOrder());
            ps.setString(3, newOd.getO_Status());
            ps.setString(4, newOd.getA_Id());
            kq = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateOrder(order od,String line) {
        int ketqua = 0;
        String sql = "UPDATE Orders SET o_Status=? where o_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, line);
            ps.setInt(2, od.getO_Id());
            ketqua = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(orderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addOrderInfo(orderInfo oi) {
        try {
            String sql = "Insert into Orders_Info values(?,?,?,?,?)";
            int kq = 0;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, oi.getOi_Fullname());
            ps.setString(2, oi.getOi_Phone());
            ps.setString(3, oi.getOi_Address());
            ps.setDouble(4, oi.getOi_TotalPrice());
            ps.setInt(5, oi.getO_Id());
            kq = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteOrder(int o_id) {
        int ketqua = 0;
        String sql = "DELETE FROM Orders WHERE o_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o_id);
            ketqua = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteOrderInfo(int o_id) {
        int ketqua = 0;
        String sql = "DELETE FROM Orders_Info WHERE o_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o_id);
            ketqua = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(orderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
