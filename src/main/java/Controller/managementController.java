/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.accountDAO;
import DAOs.cartDAO;
import DAOs.categoryDAO;
import DAOs.orderDAO;
import DAOs.orderDetailDAO;
import DAOs.productDAO;
import Models.category;
import Models.order;
import Models.orderDetail;
import Models.orderInfo;
import Models.product;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;


public class managementController extends HttpServlet {

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
            out.println("<title>Servlet managementController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet managementController at " + request.getContextPath() + "</h1>");
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
        String path = request.getRequestURI();
        //Call object need
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        //Set up DAO
        accountDAO adao = new accountDAO();
        orderDAO odao = new orderDAO();
        orderDetailDAO oddao = new orderDetailDAO();
        productDAO pdao = new productDAO();
        cartDAO cdao = new cartDAO();
        categoryDAO ctdao = new categoryDAO();
        //Load data of Products & Categories
        List<product> listProduct = pdao.GetAllProduct();
        List<category> listCate = ctdao.GetAllCategory();
        session.setAttribute("listCate", listCate);
        session.setAttribute("listProduct", listProduct);
        if (cookies == null || (cookies != null && !cookies[0].getName().equals("account"))) {
            //if not login, just load data
            session.setAttribute("usname", "none");
            session.setAttribute("management", "collapse");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            Cookie cookie = cookies[0];
            String a_Id = adao.getAccount(cookie.getValue()).getA_Id();
            if (path.endsWith("/management")) {
                request.getRequestDispatcher("/admin_management.jsp").forward(request, response);
            } else {
                if (path.endsWith("/management/product")) {
                    List<String> listID = new ArrayList<>();
                    for (product object : listProduct) {
                        listID.add(object.getPd_Id());
                    }
                    request.setAttribute("product", "Products");
                    session.setAttribute("listID", listID);
                    request.getRequestDispatcher("/product_management.jsp").forward(request, response);
                } else {
                    if (path.startsWith("/management/product/delete")) {
                        String[] data = path.split("/");
                        String pd_id = data[data.length - 1];
                        int kq = 0;
                        kq = pdao.Delete(pd_id);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(productController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        response.sendRedirect("/management");
                    } else {
                        if (path.endsWith("/management/order")) {
                            String allName = "";
                            List<order> listOrder = odao.GetAllOrders();
                            HashMap<order, orderInfo> mapList = new HashMap<>();
                            HashMap<order, String> mapName = new HashMap<>();
                            HashMap<order, List<orderDetail>> mapOrderDetail = new HashMap<>();
                            for (order object : listOrder) {
                                mapList.put(object, odao.getOrderInfo(object.getO_Id()));
                            }

                            for (order object : listOrder) {
                                mapOrderDetail.put(object, oddao.getOrdersDetail(object.getO_Id()));
                            }
                            session.setAttribute("listP", pdao.GetAllProduct());
                            session.setAttribute("mapOrderOI", mapList);
                            session.setAttribute("mapOrderOD", mapOrderDetail);
                            request.getRequestDispatcher("/order_management.jsp").forward(request, response);
                        } else {
                            if (path.startsWith("/management/order/confirm")) {
                                String[] data = path.split("/");
                                int o_Id = Integer.parseInt(data[data.length - 1]);
                                odao.updateOrder(odao.getOrderByOid(o_Id), "Success");
                                response.sendRedirect("/management/order");
                            } else if (path.startsWith("/management/order/update")) {
                                String[] data = path.split("/");
                                int o_Id = Integer.parseInt(data[data.length - 1]);
                                odao.updateOrder(odao.getOrderByOid(o_Id), "Processing");
                                response.sendRedirect("/management/order");
                            } else {
                                if (path.startsWith("/management/order/delete")) {
                                    String[] data = path.split("/");
                                    int o_Id = Integer.parseInt(data[data.length - 1]);
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
                                    response.sendRedirect("/management/order");
                                }
                            }
                        }
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
