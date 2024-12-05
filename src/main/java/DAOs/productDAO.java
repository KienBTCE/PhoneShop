/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class productDAO {

    private Connection conn;

    public productDAO() {
        conn = DB.DbConnection.GetConnection();
    }

    public List<product> GetAllProduct() {
        List<product> list = new ArrayList<>();
        ResultSet rs = null;
        String sql = "Select * From Products";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                list.add(new product(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<product> getProductByCateID(int c_Id) {
        List<product> list = new ArrayList<>();
        ResultSet rs = null;
        String sql = "Select * From Products where c_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c_Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<product> getProductByName(String txtSearch) {
        List<product> list = new ArrayList<>();
        ResultSet rs = null;
        String sql = "Select * From Products where pd_Name like '%" + txtSearch + "%'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public product getProductByID(String id) {
        ResultSet rs = null;
        String sql = "Select * From Products where pd_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int addnew(product newPd) {
        String sql = "Insert into Products values(?,?,?,?,?,?,?)";
        int kq = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPd.getPd_Id());
            ps.setString(2, newPd.getPd_Name());
            ps.setDouble(3, newPd.getPd_Price());
            ps.setString(4, newPd.getPd_Des());
            ps.setInt(5, newPd.getPd_Quan());
            ps.setString(6, newPd.getPd_Img());
            ps.setInt(7, newPd.getC_Id());

//            System.out.println(newPd.getPd_Id() + newPd.getPd_Name()+newPd.getPd_Price()+newPd.getPd_Des()+newPd.getPd_Quan()+newPd.getPd_Img()+ "CID" +newPd.getC_Id() + "CID");
            kq = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;

    }

    public int Delete(String pd_id) {
        String sql = "DELETE FROM Products WHERE pd_Id=?";
        int ketqua = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pd_id);
            ketqua = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketqua;
    }

    public int Update(product pd) {
        int ketqua = 0;

        String sql = "UPDATE Products SET pd_Name=?,pd_Quan=?, pd_Price=?,pd_Img=?,pd_Des=?,c_Id=? where pd_Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pd.getPd_Name());
            ps.setInt(2, pd.getPd_Quan());
            ps.setDouble(3, pd.getPd_Price());
            ps.setString(4, pd.getPd_Img());
            ps.setString(5, pd.getPd_Des());
            ps.setInt(6, pd.getC_Id());
            ps.setString(7, pd.getPd_Id());
            ketqua = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketqua;
    }
}
