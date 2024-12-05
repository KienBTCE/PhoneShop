/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class orderDetail {
    private String pd_Id;
    private int o_Id;
    private int od_quantity;

    public orderDetail() {
    }

    public orderDetail(String pd_Id, int o_Id, int od_quantity) {
        this.pd_Id = pd_Id;
        this.o_Id = o_Id;
        this.od_quantity = od_quantity;
    }

    public String getPd_Id() {
        return pd_Id;
    }

    public void setPd_Id(String pd_Id) {
        this.pd_Id = pd_Id;
    }

    public int getO_Id() {
        return o_Id;
    }

    public void setO_Id(int o_Id) {
        this.o_Id = o_Id;
    }

    public int getOd_quantity() {
        return od_quantity;
    }

    @Override
    public String toString() {
        return "orderDetail{" + "pd_Id=" + pd_Id + ", o_Id=" + o_Id + ", od_quantity=" + od_quantity + '}';
    }

    
    public void setOd_quantity(int od_quantity) {
        this.od_quantity = od_quantity;
    }
    
    
}
