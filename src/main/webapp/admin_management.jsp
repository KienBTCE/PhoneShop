
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
            .bloc_left_price {
                color: #c01508;
                text-align: center;
                font-weight: bold;
                font-size: 150%;
            }
            .category_block li:hover {
                background-color: #007bff;
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
            a .active{
                color: white;
            }
            .mana{
                margin: 2px;
                padding: 15px;
                font-size:larger;
                text-decoration: none;
                width: 500px
            }


            .mana {
                background-color: #F48224;
                color: #fff;
                font-size: 20px;
                font-weight: 800;
                text-transform: uppercase;
                position: relative;
                border-radius: 0 !important;
                border: none;
                transition:all 0.3s ease 0s;
            }
            .mana:hover,
            .mana.active:hover{
                color: #ffffff;
            }
            .mana:before{
                content: "";
                position: absolute;
                top: -7px;
                left: 0;
                background-color:#2E0014;
                width: 30%;
                height: 4px;
                transition:all 0.3s ease 0s;
            }
            .mana:after{
                content: "";
                position: absolute;
                bottom: -7px;
                right: 0;
                background-color:#2E0014;
                width: 30%;
                height: 4px;
                transition:all 0.3s ease 0s;
            }
            .mana:hover:before,
            .mana:hover:after,
            .mana.active:before,
            .mana.active:after{
                width: 100%;
            }

            @media screen and (max-width: 575px){
                .mana{
                    margin-bottom: 50px !important;
                }
            }


        </style>

    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <hr>
        <div class="text-center container ">
            <h2>Management</h2>

            <a href="/management/product" class='btn btn-primary'>PRODUCTS</a>
            <br>
            <a href="/management/order" class='btn btn-success'>ORDERS</a>
        </div>
    </body>

</html>
