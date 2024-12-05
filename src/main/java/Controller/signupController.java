/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.accountDAO;
import Models.account;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static sun.security.krb5.KrbException.errorMessage;


@WebServlet(name = "signupController", urlPatterns = {"/signup"})
public class signupController extends HttpServlet {

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
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        String repass = request.getParameter("repass");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Kiểm tra và xử lý đăng kí tại đây
        accountDAO accountDao = new accountDAO();
        account existingAccount = accountDao.checkAccountExist(username);
        if (existingAccount != null) {
            // Tài khoản đã tồn tại, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Username already exists.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);
        } else if (accountDao.checkEmailExist(email)) {
            // Email đã tồn tại, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Email already exists.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);
        } else if (!email.contains("@")) {
            // Email không chứa ký tự '@', hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Invalid email format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);

        } else if (!password.equals(repass)) {
            // Mật khẩu và xác nhận mật khẩu không khớp, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Password and Confirm Password do not match.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);
        } else if (phone.length() < 10 || !phone.startsWith("0") || !phone.matches("\\d+")) {
            // Số điện thoại không hợp lệ, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Invalid phone number.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);
        } else if (accountDao.checkPhoneExist(phone)) {
            // Số điện thoại đã tồn tại, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Phone number already exists.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);
        } else {
            // Tạo tài khoản mới
            accountDao.signup(username, password, fullname, email, phone);
            request.setAttribute("successMessage", "Sign up successfully!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
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
