/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;


public class order {
    private int o_Id;
    private Date o_DateOrder;
    private String o_Status;
    private String a_Id;

    public order() {
    }

    public order(int o_Id, Date o_DateOrder, String o_Status, String a_Id) {
        this.o_Id = o_Id;
        this.o_DateOrder = o_DateOrder;
        this.o_Status = o_Status;
        this.a_Id = a_Id;
    }

    public int getO_Id() {
        return o_Id;
    }

    public void setO_Id(int o_Id) {
        this.o_Id = o_Id;
    }

    public Date getO_DateOrder() {
        return o_DateOrder;
    }

    public void setO_DateOrder(Date o_DateOrder) {
        this.o_DateOrder = o_DateOrder;
    }

    public String getO_Status() {
        return o_Status;
    }

    public void setO_Status(String o_Status) {
        this.o_Status = o_Status;
    }

    public String getA_Id() {
        return a_Id;
    }

    public void setA_Id(String a_Id) {
        this.a_Id = a_Id;
    }
    

   
    

    
    
}
