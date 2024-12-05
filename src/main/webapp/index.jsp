<%@page import="Models.product"%>
<%@page import="java.util.List"%>
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
            body{
                background-color: black;
            }
            .bloc_left_price {
                color: #c01508;
                text-align: center;
                font-weight: bold;
                font-size: 150%;
            }
            .category_block li:hover {
                background-color: green;
            }
            .category_block li:hover a {
                color: #ffffff;
            }
            .category_block li a {
                color: #343a40;
            }
            .add_to_cart_block .price {
                color: #c01508;
                text-align: center;
                font-weight: bold;
                font-size: 200%;
                margin-bottom: 0;
            }
            .add_to_cart_block .price_discounted {
                color: #343a40;
                text-align: center;
                text-decoration: line-through;
                font-size: 140%;
            }
            .product_rassurance {
                padding: 10px;
                margin-top: 15px;
                background: #ffffff;
                border: 1px solid #6c757d;
                color: #6c757d;
            }
            .product_rassurance .list-inline {
                margin-bottom: 0;
                text-transform: uppercase;
                text-align: center;
            }
            .product_rassurance .list-inline li:hover {
                color: #343a40;
            }
            .reviews_product .fa-star {
                color: gold;
            }
            .pagination {
                margin-top: 20px;
            }
            footer {
                background: #343a40;
                padding: 40px;
                margin-top: 20px;
            }
            footer a {
                color: #f8f9fa!important
            }
            .bgc{
                background-image: url(image/Clothes+and+shoes-74_banner.jpg);
                /*background-image: url("https://envato-shoebox-0.imgix.net/a553/ba21-ce80-45ee-82d4-120907cdb414/Clothes+and+shoes-74_banner.jpg?auto=compress%2Cformat&fit=max&mark=https%3A%2F%2Felements-assets.envato.com%2Fstatic%2Fwatermark2.png&markalign=center%2Cmiddle&markalpha=18&w=1600&s=a9cc1545e602fe3d3e6c9faed39f0a84");*/
            }
            .show_txt{
                display: inline-block;
                width: 100%;
                white-space: nowrap;
                overflow: hidden !important;
                text-overflow: ellipsis;
            }

            .show_txt a{
                color: white;
            }

            a .active{
                color: white;
            }
            a{
                text-decoration: none;
            }

            .category_block .list-group-item {
                border: none;
                margin: 0 5px;
                padding: 5px 10px;
            }
            .category_block .list-group-item.active {
                background-color: #007bff;
                color: #fff;
            }

        </style>
    </head>
    <body>



        <jsp:include page="header.jsp"></jsp:include>
            <hr>
            <hr>
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="card bg-light mb-3">
                            <div class="card-header bg-success text-white text-uppercase">
                                Categories
                            </div>
                            <ul class="list-group category_block d-flex flex-row justify-content-center">
                            <c:forEach items="${listCate}" var="o">
                                <li class="list-group-item text-white ${tag == o.c_Id ? "active" : ""}">
                                    <a href="/category?cateID=${o.c_Id}">${o.c_Name}</a>
                                </li>
                            </c:forEach>         
                        </ul>
                    </div>
                </div>

                <div class="col-12">
                    <div class="row">
                        <c:forEach items="${listProduct}" var="o">
                            <div class="col-sm-12 col-md-6 col-lg-3">
                                <div class="card bg-dark">
                                    <a href="/detail?productID=${o.pd_Id}" title="View Product">
                                        <img class="card-img-top" src="${o.pd_Img}" alt="none" width="200px" height="300px">
                                    </a>

                                    <div class="card-body">
                                        <h4 class="card-title show_txt">
                                            <a href="/detail?productID=${o.pd_Id}" title="View Product">${o.pd_Name}</a>
                                        </h4>

                                        <c:if test="${o.pd_Quan  != 0 }">
                                            <p class="card-text show_txt h5 text-success">Available</p>
                                        </c:if>
                                        <c:if test="${o.pd_Quan  == 0}">
                                            <p class="card-text show_txt h5 text-danger">Contact Us</p>
                                        </c:if>

                                        <div class="row">
                                            <div class="col-6">
                                                <p class="p-2 btn btn-danger btn-block">${o.pd_Price} $</p>
                                            </div>
                                            <div class="col-6 text-end">
                                                <c:choose>
                                                    <c:when test="${o.pd_Quan eq 0}">
                                                        <a class="p-2 btn btn-light btn-block">Out of stock</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="/cart/add/${o.pd_Id}?from=home" class="p-2 btn btn-success btn-block">Add to cart</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>


            </div>
        </div>
        <jsp:include    page="footer.jsp"></jsp:include>
    </body>

</html>
