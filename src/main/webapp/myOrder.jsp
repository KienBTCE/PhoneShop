<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
        <style>
            a {
                text-decoration: none !important;
            }
            h4 {
                color: black;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <c:choose>
            <c:when test='${empty mapOrderOI}'>
                <h2 class='text-center m-4'>Seem like you don't have any order, let's go and <a class="" href="/home">buy some!</a></h2>
            </c:when>
            <c:otherwise>
                <div class="container-fluid bg-light row">
                    <c:forEach var="o" items="${mapOrderOI}">
                        <div class="card m-auto col-lg-5 col-md-5 col-sm-12 p-4 mt-3 mb-3">
                            <div class="card-header">
                                <c:forEach var="na" items="${AllName}">
                                    <c:if test="${o.key.o_Id eq na.key.o_Id}">
                                        <h4 class="text-truncate">#${o.key.o_Id} ${na.value}</h4>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="card-body row">
                                <!-- Display product details directly -->
                                <div>
                                    <h4>Customer Information</h4>
                                    <p class="text-black">${o.value.oi_Fullname}<br>${o.value.oi_Phone}<br>${o.value.oi_Address}</p>
                                    <hr>
                                    <h4>List of Products</h4>
                                    <c:forEach var="od" items="${mapOrderOD}">
                                        <c:if test="${o.key.o_Id eq od.key.o_Id}">
                                            <c:set var="listod" value="${od.value}"></c:set>
                                            <c:forEach var="eachOD" items="${listod}">
                                                <div class="row ps-3 pe-3">
                                                    <c:forEach var="product" items="${listP}">
                                                        <c:if test="${product.pd_Id eq eachOD.pd_Id}">
                                                            <img class="col-2" width="50px" height="auto" src="${product.pd_Img}" alt="alt"/>
                                                            <div class="col-10 row">
                                                                <h5 class="col-10 text-black text-truncate"><em>${product.pd_Name}</em></h5>
                                                                <p class="col-2 text-end ms-auto text-black">x${eachOD.od_quantity}</p>
                                                                <p class="col-4 text-end ms-auto text-black"><b>$${product.pd_Price}</b></p>
                                                            </div>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                                <hr>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                    <div class="row ps-3 pe-3">
                                        <h4 class="col-6 text-start"><p class="text-black">$ Total Price: </p></h4>
                                        <h4 class="col-6 text-end">$${o.value.oi_TotalPrice}</h4>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer row">
                                <div class="col-6">Order's date: ${o.key.o_DateOrder}</div>
                                <c:set var="ostatus" value="Success"></c:set>
                                <c:choose>
                                    <c:when test="${ostatus eq o.key.o_Status}">
                                        <div class="col-6 text-end"><label class="h5 bg-success p-2 text-light">Success</label></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-6 text-end">
                                            <label class="h5 bg-danger p-2 text-light">Processing</label>
                                            <a href="/order/delete/${o.key.o_Id}" class="bg-warning p-2" onclick="return confirm('Do you want to stop order this?')">Cancel</a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js"></script>
</html>
