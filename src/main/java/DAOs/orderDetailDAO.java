/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.orderDetail;
import Models.product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class orderDetailDAO {

    private Connection conn;

    public orderDetailDAO() {
        conn = DB.DbConnection.GetConnection();
    }

    public ResultSet getAllOrdersDetail() {
        ResultSet rs = null;
        try {
            String sql = "Select * from Orders_Detail";
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(orderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public List<orderDetail> getOrdersDetail(int o_Id) {
        List<orderDetail> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql = "Select * from Orders_Detail WHERE o_Id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o_Id);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new orderDetail(rs.getString(1), rs.getInt(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(orderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void addOrderDetail(orderDetail od) {
        int kq = 0;
        String sql = "Insert INTO Orders_Detail VALUES(?,?,?)";
        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, od.getPd_Id());
            ps.setInt(2, od.getO_Id());
            ps.setInt(3, od.getOd_quantity());
            kq = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(orderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateOrderDetail(orderDetail od) {
        int ketqua = 0;
        String sql = "UPDATE Orders_Detail SET od_quantity=? where pd_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, od.getOd_quantity());
            ps.setString(2, od.getPd_Id());
            ketqua = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(orderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteOrderDetail(int o_id) {
        int ketqua = 0;
        String sql = "DELETE FROM Orders_Detail WHERE o_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o_id);
            ketqua = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(orderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
