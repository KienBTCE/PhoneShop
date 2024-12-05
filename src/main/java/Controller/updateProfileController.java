package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAOs.accountDAO;
import Models.account;
import jakarta.servlet.http.Cookie;

public class updateProfileController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Lấy thông tin từ form
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        // Lấy thông tin người dùng từ cookie
        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("account")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        // Kiểm tra mật khẩu người dùng
        accountDAO dao = new accountDAO();
//        if (!dao.login(username, password)) {
//            // Mật khẩu không đúng, thông báo lỗi và chuyển hướng trở lại trang cập nhật thông tin
//            request.setAttribute("error", "Incorrect password!");
//            request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
//            return;
//        }

        // Kiểm tra nếu username không tồn tại hoặc không có thay đổi thông tin
        if (username == null || !dao.hasUserInfoChanged(username, fullname, email, phone, address)) {
            response.sendRedirect("/home"); // Chuyển hướng đến trang chính
            return;
        }

        // Kiểm tra nếu thông tin không rỗng
        if (fullname != null || email != null || phone != null || address != null) {
            // Kiểm tra email và số điện thoại đã tồn tại trong database
            boolean emailExists = dao.checkEmailExist(email);
            boolean phoneExists = dao.checkPhoneExist(phone);

            // Kiểm tra điều kiện và thông báo lỗi tương ứng
            if (emailExists && !dao.getUserByUsername(username).getA_Email().equals(email)) {
                // Email đã tồn tại trong database
                request.setAttribute("error", "Email already exists!");
                request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
                return;
            }

            if (phoneExists && !dao.getUserByUsername(username).getA_Phone().equals(phone)) {
                // Số điện thoại đã tồn tại trong database
                request.setAttribute("error", "Phone number already exists!");
                request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
                return;
            }

            if (phone != null && !phone.matches("0\\d{9}")) {
                // Số điện thoại không hợp lệ
                request.setAttribute("error", "Invalid phone number! Phone number must start with 0 and have at least 10 digits.");
                request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
                return;
            }

            if (phone != null && !phone.matches("\\d+")) {
                // Số điện thoại chứa ký tự không hợp lệ
                request.setAttribute("error", "Invalid phone number! Phone number must contain only digits.");
                request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
                return;
            }

            // Lấy thông tin người dùng từ database
            account user = dao.getAccount(username);

            // Kiểm tra từng ô thông tin và chỉ cập nhật khi có sự thay đổi
            if (user != null) {
                boolean hasChanged = false; // Biến đánh dấu xem có sự thay đổi hay không

                if (fullname != null && !user.getA_Fullname().equals(fullname)) {
                    user.setA_Fullname(fullname);
                    hasChanged = true;
                }
                if (email != null && !user.getA_Email().equals(email)) {
                    user.setA_Email(email);
                    hasChanged = true;
                }
                if (phone != null && !user.getA_Phone().equals(phone)) {
                    user.setA_Phone(phone);
                    hasChanged = true;
                }
                if (address != null && !user.getA_Address().equals(address)) {
                    user.setA_Address(address);
                    hasChanged = true;
                }

                // Thực hiện cập nhật thông tin người dùng nếu có sự thay đổi
                if (hasChanged) {
                    dao.updateAccount(user);
                }
            }
        }

        // Chuyển hướng về trang cập nhật thông tin thành công hoặc trang profile với thông báo lỗi
        if (request.getAttribute("error") == null) {
            response.sendRedirect("/home");
        } else {
            request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("account")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        // Kiểm tra nếu cookie chứa thông tin người dùng đã đăng nhập
        if (username == null) {
            response.sendRedirect("/login"); // Chuyển hướng đến trang đăng nhập
        } else {
            request.getRequestDispatcher("/updateProfile.jsp").forward(request, response); // Chuyển hướng đến trang cập nhật thông tin
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
