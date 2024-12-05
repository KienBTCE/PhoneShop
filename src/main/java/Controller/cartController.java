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
import Models.account;
import Models.cart;
import Models.order;
import Models.orderDetail;
import Models.product;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.EOFException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class cartController extends HttpServlet {

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
            out.println("<title>Servlet cartController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet cartController at " + request.getContextPath() + "</h1>");
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

        //Khai bao nhung thanh phan chung
        String path = request.getRequestURI();
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        List<cart> listCart = new ArrayList<>();

        //khai bao DAO
        accountDAO adao = new accountDAO();
        orderDAO odao = new orderDAO();
        orderDetailDAO oddao = new orderDetailDAO();
        productDAO pdao = new productDAO();
        cartDAO cdao = new cartDAO();
        //Lay thong tin chung
        Cookie cookie1 = cookies[0];
        account us = adao.getAccount(cookie1.getValue());
        session.setAttribute("management", "collapse");

        if (cookies == null || (cookies != null && !cookies[0].getName().equals("account"))) {
            //if not login, just load data
            response.sendRedirect("/login");
        } else {
            if (us.getA_Id().startsWith("admin")) {
                session.setAttribute("management", "");
            }
            Cookie cookie = cookies[0];
            String a_Id = adao.getAccount(cookie.getValue()).getA_Id();
            listCart = cdao.getCartByUser(a_Id);
            Map<product, Integer> listProduct = new HashMap<>();
            int error = 0;
            if (path.endsWith("/cart/list")) {
                if (!listCart.isEmpty()) {
                    for (cart cartItem : listCart) {
                        listProduct.put(pdao.getProductByID(cartItem.getPd_Id()), cartItem.getCt_quantity());
                        if (pdao.getProductByID(cartItem.getPd_Id()).getPd_Quan() < cartItem.getCt_quantity()) {
                            session.setAttribute("error_" + cartItem.getPd_Id(), "There are " + pdao.getProductByID(cartItem.getPd_Id()).getPd_Quan() + " left!");
                            error++;
                        } else {
                            session.removeAttribute("error_" + cartItem.getPd_Id());
                        }
                    }
                }
                if (error > 0) {
                    session.setAttribute("openOrder", "disabled=\"\"");
                } else {
                    session.removeAttribute("openOrder");
                }
                session.setAttribute("us", us);
                session.setAttribute("listCart", listProduct);
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
            } else {
                if (path.startsWith("/cart/add/")) {
                    String[] data = path.split("/");
                    String pd_id = data[data.length - 1];
                    try {

                        int cartItemQuan = 1;
                        if (request.getParameter("quan") != null) {
                            cartItemQuan = Integer.parseInt(request.getParameter("quan"));
                        }
                        if (cartItemQuan < 1 || cartItemQuan > pdao.getProductByID(pd_id).getPd_Quan()) {
                            throw new Exception();
                        } else {
                        }
                        int checkAdd = 0;
                        if (listCart.size() > 0) {
                            for (cart cartItem : listCart) {
                                if (pd_id.equals(cartItem.getPd_Id())) {
                                    if (cartItemQuan+cartItem.getCt_quantity()>pdao.getProductByID(pd_id).getPd_Quan()) {
                                        throw new Exception();
                                    }
                                    cdao.updateCartItem(new cart(a_Id, pd_id, cartItem.getCt_quantity() + cartItemQuan));
                                    checkAdd = 0;
                                    break;
                                } else {
                                    checkAdd++;
                                }
                            }
                        }
                        if (listCart.size() == 0 || checkAdd != 0) {
                            cdao.addCartItem(new cart(a_Id, pd_id, cartItemQuan));
                        }
                        if (request.getParameter("from").equals("bn")) {
                            response.sendRedirect("/cart/list");
                        } else if (request.getParameter("from").equals("a2c")) {
                            response.sendRedirect("/detail?productID=" + pd_id);
                        } else {
                            response.sendRedirect("/home");
                        }
                    } catch (Exception e) {
                        if (request.getParameter("from").equals("bn")) {
                            response.sendRedirect("/detail?productID=" + pd_id);
                        } else if (request.getParameter("from").equals("a2c")) {
                            response.sendRedirect("/detail?productID=" + pd_id);
                        } else {
                            response.sendRedirect("/home");
                        }
                    }

                } else {
                    if (path.startsWith("/cart/delete/")) {
                        String[] data = path.split("/");
                        String pd_id = data[data.length - 1];
                        cdao.deleteCartItem(a_Id, pd_id);
                        response.sendRedirect("/cart/list");
                    } else {
                        if (path.startsWith("/cart/update")) {
                            String pd = "";
                            int quan = 0;
                            for (cart cartItem : listCart) {
                                pd = cartItem.getPd_Id();
                                quan = Integer.parseInt(request.getParameter("quan-" + pd));
                                cdao.updateCartItem(new cart(a_Id, pd, quan));
                            }
                            response.sendRedirect("/cart/list");
                        } else {
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
