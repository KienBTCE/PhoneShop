/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.accountDAO;
import DAOs.cartDAO;
import DAOs.orderDAO;
import DAOs.orderDetailDAO;
import DAOs.productDAO;
import Models.cart;
import Models.order;
import Models.orderDetail;
import Models.orderInfo;
import Models.product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class orderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet orderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet orderController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        accountDAO adao = new accountDAO();
        orderDAO odao = new orderDAO();
        orderDetailDAO oddao = new orderDetailDAO();
        productDAO pdao = new productDAO();
        cartDAO cdao = new cartDAO();

        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        List<cart> listCart = new ArrayList<>();

        String path = request.getRequestURI();
        if (cookies == null || (cookies != null && !cookies[0].getName().equals("account"))) {
            //if not login, just load data
            response.sendRedirect("/login");
        } else {
            Cookie cookie = cookies[0];
            String a_Id = adao.getAccount(cookie.getValue()).getA_Id();
            listCart = cdao.getCartByUser(a_Id);
            if (path.endsWith("/order/ordering")) {
                try {

                    product prod = null;
                    String fullname = request.getParameter("txtName");
                    String phone = request.getParameter("txtPhone");
                    String address = request.getParameter("txtAddress");
                    Date date = Date.valueOf(LocalDate.now());
                    double totalPrice = 0;
                    if (odao.getOnGoingOrder(a_Id) == null) {
                        odao.addOrder(new order(odao.getMaxId() + 1, date, "On_going", a_Id));
                    }
                    String pd = "";
                    int kq = 0;
                    int quan = 0;
                    for (cart cartItem : listCart) {
                        pd = cartItem.getPd_Id();
                        prod = pdao.getProductByID(pd);

                        quan = cartItem.getCt_quantity();
                        if (quan < 1 || quan > prod.getPd_Quan()) {
                            throw new Exception();
                        }
                        oddao.addOrderDetail(new orderDetail(pd, odao.getOnGoingOrder(a_Id).getO_Id(), quan)); // error
                        prod.setPd_Quan(prod.getPd_Quan() - quan);
                        kq = pdao.Update(prod);
                        totalPrice += prod.getPd_Price() * quan;
                    }
                    odao.addOrderInfo(new orderInfo(fullname, phone, address, totalPrice, odao.getOnGoingOrder(a_Id).getO_Id()));
                    odao.updateOrder(odao.getOnGoingOrder(a_Id), "Processing");
                    cdao.clearCart(a_Id);
                    response.sendRedirect("/order/list");
                } catch (Exception e) {
                    response.sendRedirect("/cart/list");
                }
            } else {
                if (path.endsWith("/order/list")) {
                    String allName = "";
                    List<order> listOrder = odao.getOrderByAId(a_Id);
                    HashMap<order, orderInfo> mapList = new HashMap<>();
                    HashMap<order, String> mapName = new HashMap<>();
                    HashMap<order, List<orderDetail>> mapOrderDetail = new HashMap<>();
                    for (order object : listOrder) {
                        mapList.put(object, odao.getOrderInfo(object.getO_Id()));
                    }

                    for (order object : listOrder) {
                        List<orderDetail> od = new ArrayList<>();
                        od = oddao.getOrdersDetail(object.getO_Id());
                        for (orderDetail oddt : od) {
                            allName += pdao.getProductByID(oddt.getPd_Id()).getPd_Name() + ",";
                        }
                        allName = allName.substring(0, allName.length() - 1);
                        mapName.put(object, allName);
                        allName = "";
                        mapOrderDetail.put(object, oddao.getOrdersDetail(object.getO_Id()));
                    }

                    
                    session.setAttribute("listP", pdao.GetAllProduct());
                    session.setAttribute("AllName", mapName);
                    session.setAttribute("mapOrderOI", mapList);
                    session.setAttribute("mapOrderOD", mapOrderDetail);
                    request.getRequestDispatcher("/myOrder.jsp").forward(request, response);
                } else {
                    if (path.startsWith("/order/delete")) {
                        String[] data = path.split("/");
                        int o_Id = Integer.parseInt(data[data.length - 1]);
                        if (odao.getOrderByOid(o_Id).getO_Status().equals("Success")) {

                        } else {
                            List<product> listP = pdao.GetAllProduct();
                            product p = null;
                            for (orderDetail item : oddao.getOrdersDetail(o_Id)) {
                                p = pdao.getProductByID(item.getPd_Id());
                                p.setPd_Quan(p.getPd_Quan() + item.getOd_quantity());
                                pdao.Update(p);
                            }
                            odao.deleteOrderInfo(o_Id);
                            oddao.deleteOrderDetail(o_Id);
                            odao.deleteOrder(o_Id);
                        }
                        response.sendRedirect("/order/list");
                    }

                }
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
