<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Models.account" %>
<%@ page import="DAOs.accountDAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Profile</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

        <style>
            .container {
                display: flex;
                justify-content: center;
                align-items: center;
                
            }
            .form-container {
                width: 400px;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            .form-container h1 {
                text-align: center;
            }
            .form-container label {
                font-weight: bold;
            }
            .form-container input[type="text"],
            .form-container input[type="email"],
            .form-container input[type="password"] {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            .form-container input[type="submit"] {
                width: 100%;
                padding: 8px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .form-container button[type="submit"] {
                width: 100%;
                padding: 8px;
                background-color: #6c757d;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .error-message {
                color: red;
                margin-bottom: 10px;
            }
            .success-message {
                color: green;
                margin-bottom: 10px;
                font-weight: bold;
                text-align: center;
                background-color: #d4edda;
                border: 1px solid #c3e6cb;
                border-radius: 5px;
                padding: 10px;
            }
        </style>
        <script>
            // Tự động đóng thông báo sau 3 giây
            setTimeout(function () {
                var successElement = document.getElementById("success-message");
                if (successElement) {
                    successElement.style.display = "none";
                }
            }, 3000);
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <div class="container">
            <div class="form-container">
                <h1>My Profile</h1>
                <%-- Lấy thông tin người dùng từ cookie --%>
                <%
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

                    // Kiểm tra nếu username đã được lưu trong cookie
                    if (username != null) {
                        // Tạo đối tượng accountDAO để truy vấn thông tin người dùng
                        accountDAO dao = new accountDAO();

                        // Lấy thông tin người dùng từ database
                        account user = dao.getAccount(username);

                        // Hiển thị thông tin người dùng
                        if (user != null) {
                %>
                <form method="POST" action="/profile">
                    <div class="form-group">
                        <label for="fullname">Full Name:</label>
                        <input type="text" id="fullname" name="fullname" value="<%= user.getA_Fullname()%>" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="<%= user.getA_Email()%>" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="text" id="phone" name="phone" value="<%= user.getA_Phone()%>" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" value="<%= user.getA_Address()%>" class="form-control">
                    </div>
<!--                    <div class="form-group">
                        <label for="password">Password required to update:</label>
                        <input type="password" id="password" name="password" class="form-control" required>
                    </div>-->
                    <p id="error-message" class="error-message">${error}</p>
                    
                    <%-- Hiển thị thông báo thành công --%>
                    <c:if test="${not empty requestScope.successMessage}">
                        <p id="success-message" class="success-message">${requestScope.successMessage}</p>
                    </c:if>
                    <input type="submit" value="Update" class="btn btn-primary">
                </form>
                    <br>
                <a href="/profile/changePassword" class="btn btn-primary btn-block" style="background-color: green">Change Password</a>
                
                <form method="GET" action="/home">
                    <br>
                    <button type="submit" class="btn btn-secondary">Back to Home</button>
                </form>
                <%
                        }
                    }
                %>
            </div>
        </div>
            <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
