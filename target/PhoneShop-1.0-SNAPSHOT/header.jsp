
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">


<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container-fluid">

        <a class="navbar-brand" href="/home">
            <img src="/img/PhoneShop.jpg"  width="100px" alt="alt" style="margin-left: 100px"/>
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
            <form action="/search" method="post" class="form-inline my-2 my-lg-0 m-auto">
                <div class="input-group input-group-sm" style="width: 800px">
                    <input name="txtSearch" type="text" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search..." style="opacity: 0.7;">
                    <div class="input-group-append">
                        <!--                        <button type="submit" class="btn btn-secondary btn-number">
                                                    <i class="fa fa-search"></i>
                                                </button>-->
                    </div>
                </div>
            </form>
            <a class="btn btn-danger text-light ${sessionScope.management}" href="/management">Managements</a>

            <ul class="navbar-nav align-content-end">
                <li class="nav-item">
                    <a class="nav-link" href="/profile">${usname}</a>
                </li>
                <c:choose>
                    <c:when  test='${ usname eq "Guest"}'>
                        <li class="nav-item">
                            <a class="nav-link" href="/login">Login</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <form id="logout" method="post" action="/login">
                                <input type="hidden" name="btnLogout" value="Logout" />
                                <a href="#" class="nav-link" onclick="document.getElementById('logout').submit()">Log out</a>
                            </form>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>

            <a class="btn btn-primary btn-sm ms-3 p-2" href="/cart/list">Cart
            </a>
            &nbsp
            <a class="btn btn-primary" href="/order/list">Ordered</a>

        </div>
    </div>
</nav>
