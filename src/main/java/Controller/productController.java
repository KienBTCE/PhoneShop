/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.accountDAO;
import DAOs.categoryDAO;
import DAOs.productDAO;
import Models.account;
import Models.category;
import Models.product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@MultipartConfig
public class productController extends HttpServlet {

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
        //Using 'path' to get URL
        String path = request.getRequestURI();
        //Call object need
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        //Set up DAO
        accountDAO adao = new accountDAO();
        productDAO pdao = new productDAO();
        categoryDAO cdao = new categoryDAO();

        //Load data of Products & Categories
        List<product> listProduct = pdao.GetAllProduct();
        List<category> listCate = cdao.GetAllCategory();
        session.setAttribute("listCate", listCate);
        session.setAttribute("listProduct", listProduct);
        String username = "";
        String temp = String.valueOf(request.getSession().getAttribute("temp"));
        //check if cookie account exist or not
        if (cookies == null || (cookies != null && !cookies[0].getName().equals("account")) ) {
            //if not login, just load data
            session.setAttribute("usname", "none");
            session.setAttribute("management", "collapse");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            //if login success: 

            //show the list with account information
            if (path.endsWith("/home")) {
                Cookie cookie = cookies[0];
                account us = adao.getAccount(cookie.getValue());
                session.setAttribute("management", "collapse");
                if (us.getA_Id().startsWith("admin")) {
                    session.setAttribute("management", "");
                }
                session.setAttribute("usname", us.getA_Fullname());
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                response.sendRedirect("/home");
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

        HttpSession session = request.getSession();
        productDAO pdao = new productDAO();
        //-------START-Get information from form-------
        String p_id = request.getParameter("txtId");
        String p_name = request.getParameter("txtName");
        int p_quan = Integer.parseInt(request.getParameter("txtQuan"));
        double p_price = Double.parseDouble(request.getParameter("txtPrice"));
        String p_des = request.getParameter("txtDes").trim();
        int c_Id = Integer.parseInt(request.getParameter("txtCate"));
        //hinhanh
        String p_pic = "";
        try {
            Part part = request.getPart("txtPic");
            String picFolder = "img";
            String[] context = request.getServletContext().getRealPath("").split("target");
            String realPath = context[0] + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + picFolder;
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            part.write(realPath + File.separator + fileName);
            p_pic = File.separator + picFolder + File.separator + fileName;
        } catch (Exception e) {
            p_pic = request.getParameter("txtPicOr");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(productController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //-------END-Get information from form-------

        //If using add project function FOR admin
        if (request.getParameter("btnAdd") != null && request.getParameter("btnAdd").equals("Submit")) {
            product newPd = new product(p_id, p_name, p_price, p_des, p_quan, p_pic, c_Id);
            int ketqua = pdao.addnew(newPd);
            if (ketqua == 0) {
                response.sendRedirect("/home/management");
            } else {
                response.sendRedirect("/home/management");
            }
        }
        //if using update product function FOR admin
        if (request.getParameter("btnUpdate") != null && request.getParameter("btnUpdate").equals("Update")) {
            product pd = new product(p_id, p_name, p_price, p_des, p_quan, p_pic, c_Id);
            int ketqua = pdao.Update(pd);
            if (ketqua == 0) {
                response.sendRedirect("/home/management");
            } else {
                response.sendRedirect("/home/management");
            }
        }
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
