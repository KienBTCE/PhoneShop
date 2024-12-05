/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.category;
import Models.product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class categoryDAO {

    private Connection conn;

    public categoryDAO() {
        conn = DB.DbConnection.GetConnection();
    }

    public List<category> GetAllCategory() {
        List<category> list = new ArrayList<>();
        ResultSet rs = null;
        String sql = "Select * From Categories";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new category(rs.getInt(1),
                        rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(categoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public String getCateName(int c_id){
        String cname="";
        ResultSet rs = null;
        String sql = "Select * from Categories WHere c_Id = ?";
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                cname = rs.getString("c_Name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(categoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return cname;
    }


}
