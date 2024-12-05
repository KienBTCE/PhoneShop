<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="//cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css"/>
        <script src="//cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
        <style>
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
            table {
                width: 90% !important;
            }
            td {
                width: fit-content;
            }
            #confirm {
                background: #33ff33;
            }
            #confirming {
                background-color: #ff0033;
            }
        </style>
        <script>
            $(document).ready(function () {
                $('#example').DataTable();
            });
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <div class="container mt-4">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/management">Management</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Orders</li>
                </ol>
            </nav>
        </div>
        <div class="container my-5">
            <h1 class="text-center mb-4">Orders Management</h1>

            <table id="example" class="table table-striped table-bordered">
                <thead class="table-info">
                    <tr>
                        <th>Id</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Actions</th>
                        <th>Confirm Status</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="oi" items="${mapOrderOI}">
                        <tr>
                            <td>#${oi.key.o_Id}</td>
                            <c:set var="stas" value="${oi.key.o_Status}"></c:set>
                            <c:choose>
                                <c:when test='${oi.key.o_Status eq "Success"}'>
                                    <td class="text-success fw-bold">${oi.key.o_Status}</td>
                                </c:when>
                                <c:otherwise>
                                    <td class="text-danger fw-bold">${oi.key.o_Status}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${oi.key.o_DateOrder}</td>
                            <c:forEach var="od" items="${mapOrderOD}">
                                <c:if test="${oi.key.o_Id eq od.key.o_Id}">
                                    <c:set var="listod" value="${od.value}"></c:set>
                                    <c:set var="odquan" value="${fn:length(listod)}"></c:set>
                                </c:if>
                            </c:forEach>
                            <td>${odquan}</td>
                            <td>$${oi.value.oi_TotalPrice}</td>
                            <td>
                                <button type="button" class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#staticBackdrop2${oi.key.o_Id}">
                                    Detail
                                </button>
                                <div class="modal fade" id="staticBackdrop2${oi.key.o_Id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="staticBackdropLabel">Order #${oi.key.o_Id}</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <h4>Buyer Info</h4>
                                                <p>${oi.value.oi_Fullname}<br>${oi.value.oi_Phone}<br>${oi.value.oi_Address}</p>
                                                <hr>
                                                <h4>List of Products</h4>
                                                <c:forEach var="eachOD" items="${listod}">
                                                    <div class="row mb-3">
                                                        <c:forEach var="product" items="${listP}">
                                                            <c:if test="${product.pd_Id eq eachOD.pd_Id}">
                                                                <div class="col-2">
                                                                    <img width="50" height="auto" src="${product.pd_Img}" alt="Product Image"/>
                                                                </div>
                                                                <div class="col-10">
                                                                    <h6 class="text-black text-truncate"><em>${product.pd_Name}</em></h6>
                                                                    <div class="d-flex justify-content-between">
                                                                        <span>Quantity: x${eachOD.od_quantity}</span>
                                                                        <span><b>Price: $${product.pd_Price}</b></span>
                                                                    </div>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                    <hr>
                                                </c:forEach>
                                                <div class="d-flex justify-content-between">
                                                    <h4>Total Price:</h4>
                                                    <h4>$${oi.value.oi_TotalPrice}</h4>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                <input type="submit" class="btn btn-primary" name="btnUpdate" value="Update">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <c:choose>
                                <c:when test='${oi.key.o_Status eq "Success"}'>
                                    <td id="confirm" class="bg-opacity-75 text-black">
                                        <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Success</a>
                                        <ul class="dropdown-menu bg-danger">
                                            <li><a class="dropdown-item" href="/management/order/update/${oi.key.o_Id}">Processing</a></li>
                                        </ul>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td id="confirming" class="h5">
                                        <a onclick="return confirm('Confirm Order?')" class="text-black nav-link text-decoration-none" href="/management/order/confirm/${oi.key.o_Id}">Confirm</a>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <a onclick="return confirm('Are you sure you want to delete this from the cart?')" href="/management/order/delete/${oi.key.o_Id}" class="btn btn-outline-danger btn-circle">X</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>