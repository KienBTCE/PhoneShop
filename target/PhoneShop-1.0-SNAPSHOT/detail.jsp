
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
            .gallery-wrap .img-big-wrap img {
                height: 450px;
                width: auto;
                display: inline-block;
                cursor: zoom-in;
            }


            .gallery-wrap .img-small-wrap .item-gallery {
                width: 60px;
                height: 60px;
                border: 1px solid #ddd;
                margin: 7px 2px;
                display: inline-block;
                overflow: hidden;
            }

            .gallery-wrap .img-small-wrap {
                text-align: center;
            }
            .gallery-wrap .img-small-wrap img {
                max-width: 100%;
                max-height: 100%;
                object-fit: cover;
                border-radius: 4px;
                cursor: zoom-in;
            }
            .img-big-wrap img{
                width: 100% !important;
                height: auto !important;
            }
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
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
        </style>
    </head>

    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <hr>
            <div class="container ">

                <div class="col-10 m-auto">
                    <div class="container">
                        <div class="card">
                            <div class="row">
                                <aside class="col-sm-5 border-right">
                                    <article class="gallery-wrap"> 
                                        <div class="img-big-wrap">
                                            <div> <a href="#"><img src="${detail.pd_Img}" class="w-100"></a></div>
                                    </div> <!-- slider-product.// -->

                                </article> <!-- gallery-wrap .end// -->
                            </aside>
                            <aside class="col-sm-7">
                                <article class="card-body p-5">
                                    <h3 class="title mb-3">${detail.pd_Name}</h3>

                                    <p class="price-detail-wrap"> 
                                        <span class="price h3 text-warning"> 
                                            <span class="currency">US $</span><span class="num">${detail.pd_Price}</span>
                                        </span> 
                                        <!--<span>/per kg</span>--> 
                                    </p> <!-- price-detail-wrap .// -->


                                    <hr>
                                    <dl class="item-property">
                                        <c:if test="${detail.pd_Quan != 0}">
                                            <dt class="h4 text-success">Available</dt>
                                        </c:if>                
                                    </dl>

                                    <c:if test="${detail.pd_Quan != 0}">

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <dl class="param param-inline d-flex align-items-center">
                                                    <dt class="h4">Quantity: </dt>
                                                    <dd class="d-flex align-items-center" style="width: 200px;">
                                                        <input type="hidden" id="cartquan" value="${cartQuan}">
                                                        <button class="btn btn-outline-primary m-2" onclick="minusQuan()">-</button>
                                                        <input onchange="checkQuan(${detail.pd_Quan})"
                                                               onchange="this.value === '0' ? '' : this.value"
                                                               onblur="checkQuan(${detail.pd_Quan})"
                                                               type="number" oninput="validity.valid||(value='');" min="0" 
                                                               class="form-control" name="quan" id="quan" value="1">
                                                        <button class="btn btn-outline-primary m-2" onclick="plusQuan()">+</button>
                                                    </dd>
                                                </dl>  <!-- item-property .// -->
                                            </div> <!-- col.// -->

                                        </div> <!-- row.// -->
                                        <hr>
                                    </c:if>



                                    <h5 id="error" class="text-danger"></h5>


                                    <c:choose>
                                        <c:when test="${detail.pd_Quan eq 0}">
                                            <a class=" p-2 btn btn-light btn-block text-danger">Contact Us</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="#" onclick=" return checkQuan(${detail.pd_Quan})"  id="buy-now-link" class="btn btn-lg btn-primary text-uppercase"> Buy now </a>
                                            <a href="#" onclick=" return checkQuan(${detail.pd_Quan})" id="add-to-cart-link" class="btn btn-lg btn-success">Add to cart</a>
                                        </c:otherwise>
                                    </c:choose>

                                </article> <!-- card-body.// -->
                            </aside> <!-- col.// -->

                        </div> <!-- row.// -->
                        <div class="card-footer">
                            <h3>Description</h3>
                            <pre class="ps-5">${detail.pd_Des}</pre>
                        </div>
                    </div> <!-- card.// -->


                </div>
            </div>
        </div>
    </div>
    <jsp:include    page="footer.jsp"></jsp:include>
    </body>
    <script>

        function checkQuan(quan) {
            var quaninput = parseInt(document.getElementById("quan").value);

            var btnA2c = document.getElementById("add-to-cart-link");
            var btnbn = document.getElementById("buy-now-link");
            var cartquan = parseInt(document.getElementById("cartquan").value);
            var total = quaninput + cartquan;
            if (quaninput > quan) {
                document.getElementById("error").innerHTML = "There're only " + quan + " product left!";
                btnA2c.href = '#';
                btnbn.href = '#';

            } else if (total > quan) {
                document.getElementById("error").innerHTML = "Your cart already have" + cartquan + " of this product. You can't have more than the stock!";
                btnA2c.href = '#';
                btnbn.href = '#';
            } else if (quaninput === 0 || quaninput === "") {
                document.getElementById("error").innerHTML = "Please enter the quantity";
                btnA2c.href = '#';
                btnbn.href = '#';
            } else {
                document.getElementById("error").innerHTML = "";
                btnA2c.href = '/cart/add/${detail.pd_Id}?from=a2c&quan=';
                btnbn.href = '/cart/add/${detail.pd_Id}?from=bn&quan=';

                return true;
            }
            return false;
        }
        ;


        function minusQuan() {

            var x = Number(document.getElementById("quan").value);
            if (x > 1) {
                document.getElementById("quan").value = x - 1;
            }
        }
        function plusQuan() {
            var x = Number(document.getElementById("quan").value);
            if (x < ${detail.pd_Quan}) {
                document.getElementById("quan").value = x + 1;
            } else
                document.getElementById("quan").value = ${detail.pd_Quan};

        }
</script>
<script>
    let addToCartLink = document.getElementById('add-to-cart-link');
    let quanInput = document.getElementById('quan');

    addToCartLink.addEventListener('click', function (e) {
        e.preventDefault();
        addToCartLink.href = addToCartLink.href + quanInput.value;
        window.location = addToCartLink.href;
    });
</script><script>
    let buyNowLink = document.getElementById('buy-now-link');
    let quanInput2 = document.getElementById('quan');

    buyNowLink.addEventListener('click', function (f) {
        f.preventDefault();
        buyNowLink.href = buyNowLink.href + quanInput2.value;
        window.location = buyNowLink.href;
    });
</script>
</html>
