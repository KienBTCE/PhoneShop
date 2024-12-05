/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.accountDAO;
import DAOs.cartDAO;
import DAOs.categoryDAO;
import DAOs.productDAO;
import Models.cart;
import Models.category;
import Models.product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class detailController extends HttpServlet {

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
            out.println("<title>Servlet detailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet detailController at " + request.getContextPath() + "</h1>");
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
        cartDAO cdao = new cartDAO();
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        List<cart> listCart = new ArrayList<>();
        String path = request.getRequestURI();
        if (path.endsWith("/detail")) {
            String id = request.getParameter("productID");
            productDAO dao = new productDAO();
            categoryDAO dao2 = new categoryDAO();
            product pro = dao.getProductByID(id);
            List<category> listCate = dao2.GetAllCategory();
            try {
                Cookie cookie = cookies[0];
                String a_Id = adao.getAccount(cookie.getValue()).getA_Id();
                listCart = cdao.getCartByUser(a_Id);
                for (cart object : listCart) {
                    if (object.getPd_Id().equals(id)) {
                        request.setAttribute("cartQuan", object.getCt_quantity());
                    }
                }
            } catch (Exception e) {
            }

            request.setAttribute("listCate", listCate);
            request.setAttribute("detail", pro);

            request.getRequestDispatcher("detail.jsp").forward(request, response);
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
