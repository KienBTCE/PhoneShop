/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


public class cart {
    private String a_Id; 
    private String pd_Id; 
    private int ct_quantity; 

    public cart() {
    }

    public cart(String a_Id, String pd_Id, int ct_quantity) {
        this.a_Id = a_Id;
        this.pd_Id = pd_Id;
        this.ct_quantity = ct_quantity;
    }

    public String getA_Id() {
        return a_Id;
    }

    public void setA_Id(String a_Id) {
        this.a_Id = a_Id;
    }

    public String getPd_Id() {
        return pd_Id;
    }

    public void setPd_Id(String pd_Id) {
        this.pd_Id = pd_Id;
    }

    public int getCt_quantity() {
        return ct_quantity;
    }

    public void setCt_quantity(int ct_quantity) {
        this.ct_quantity = ct_quantity;
    }
    
}
