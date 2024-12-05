/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


public class orderInfo {
    private String oi_Fullname;
    private String oi_Phone;
    private String oi_Address;
    private double oi_TotalPrice;
    private int o_Id;

    public orderInfo(String oi_Fullname, String oi_Phone, String oi_Address, double oi_TotalPrice, int o_Id) {
        this.oi_Fullname = oi_Fullname;
        this.oi_Phone = oi_Phone;
        this.oi_Address = oi_Address;
        this.oi_TotalPrice = oi_TotalPrice;
        this.o_Id = o_Id;
    }

    public orderInfo() {
    }

    public String getOi_Fullname() {
        return oi_Fullname;
    }

    public void setOi_Fullname(String oi_Fullname) {
        this.oi_Fullname = oi_Fullname;
    }

    public String getOi_Phone() {
        return oi_Phone;
    }

    public void setOi_Phone(String oi_Phone) {
        this.oi_Phone = oi_Phone;
    }

    public String getOi_Address() {
        return oi_Address;
    }

    public void setOi_Address(String oi_Address) {
        this.oi_Address = oi_Address;
    }

    public double getOi_TotalPrice() {
        return oi_TotalPrice;
    }

    public void setOi_TotalPrice(double oi_TotalPrice) {
        this.oi_TotalPrice = oi_TotalPrice;
    }

    public int getO_Id() {
        return o_Id;
    }

    public void setO_Id(int o_Id) {
        this.o_Id = o_Id;
    }

}
