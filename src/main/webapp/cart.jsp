<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

        <style>
            body {
                background-color: #f0f2f5;
                font-family: Arial, sans-serif;
            }
            tr {
                border: 2px solid black;
            }
            table {
                background-color: #ffffff;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-collapse: collapse;
                width: 100%;
            }
            table th, table td {
                border: 1px solid #ddd;
                padding: 8px;
            }
            tr:first-child {
                text-align: center;
                border: none;
                background-color: #e9ecef;
                font-weight: bold;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #ddd;
            }
            img {
                padding: 3px;
                border-radius: 5px;
            }
            td:last-child {
                text-align: center;
                border: none;
            }
            .btn-circle {
                border-radius: 100px;
                transition: transform 0.2s;
            }
            .btn-circle:hover {
                transform: scale(1.1);
            }
            #order_area {
                height: fit-content;
                background-color: #f7f9fc;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .btn:hover {
                background-color: #d63384 !important;
                transition: background-color 0.3s;
            }
            .btn-outline-primary:hover {
                color: #fff;
                background-color: #007bff;
                border-color: #007bff;
            }
            .btn-outline-danger:hover {
                color: #fff;
                background-color: #dc3545;
                border-color: #dc3545;
            }
            .btn-success:hover {
                background-color: #218838;
            }
            input[type="number"]::-webkit-inner-spin-button,
            input[type="number"]::-webkit-outer-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
            input[type="number"] {
                -moz-appearance: textfield;
            }
        </style>
    </head>

    <body>

        <jsp:include page="header.jsp"></jsp:include>
            <div class="container-fluid">
                <div class="row">
                <c:if test="${not empty listCart}">
                    <form action="/order/ordering" id="cartForm" method="get" class="w-100 d-lg-flex">
                        <div class="col-md-7 col-sm-12 bg-light p-3">

                            <div class="d-flex justify-content-between text-center">
                                <h3 class="">List of Products</h3>
                                <button type="button" id="submit-button"  name="btnUpdate" class="btn btn-circle btn-lg btn-success" onclick="order()" disabled="">Update cart</button>
                            </div>

                            <table class="w-100">
                                <tr>
                                    <th>Products</th>
                                    <th>Price</th>
                                    <th>Amount</th>
                                    <th>Total Price</th>
                                    <th></th>
                                </tr>
                                <c:forEach var="op" items="${listCart}" varStatus="status">

                                    <tr>
                                        <td> 
                                            <a href="/detail?productID=${op.key.pd_Id}" class="text-black text-decoration-none">
                                                <img src="${op.key.pd_Img}" alt="alt" width="70px" height="auto"/> 
                                                ${op.key.pd_Name}
                                            </a>

                                        </td>
                                        <td>${op.key.pd_Price}$</td>

                                        <td>
                                            <div class="d-flex align-items-center" style="width: 170px;">

                                                <button id="change-button1" type="button" class="btn btn-outline-primary m-2" name="minus" onclick="minusQuan('${op.key.pd_Id}')">-</button>
                                                <input  onchange="checkValue('${op.key.pd_Id}')"  oninput="validity.valid||(value='');" min="1" type ="number" class="form-control" name="quan-${op.key.pd_Id}" id="${op.key.pd_Id}" value="${op.value}" data-id="${op.key.pd_Id}">
                                                <button  id="change-button2" type="button" class="btn btn-outline-primary m-2" name="plus" onclick="plusQuan('${op.key.pd_Id}', '${op.key.pd_Quan}')">+</button>
                                            </div>

                                            <c:set var="err" value="error_${op.key.pd_Id}"/>
                                            <h5 class="text-danger" ><c:out value="${sessionScope[err]}"/></h5>
                                            <c:set var="quanti" value="${op.value}" />
                                            <c:set var="price" value="${op.key.pd_Price}" />
                                            <c:set var="tempPrice" value="${quanti*price}" />
                                            <c:set var="totalPrice" value="${totalPrice+tempPrice}" />

                                        <td>$<fmt:formatNumber type="number" value="${tempPrice}" minFractionDigits="2" maxFractionDigits="2" /></td>
                                        <td>
                                            <a onclick="return confirm('Are you sure want to delete this from cart?')" href="/cart/delete/${op.key.pd_Id}" class="btn btn-outline-danger btn-circle">X</a></td>
                                    </tr>

                                </c:forEach>
                            </table>

                        </div>

                        <div class="col-sm-12 col-md-5 p-3 text-center " id="order_area"><h3>Order's Information</h3>
                            <h4>Total: $<fmt:formatNumber type="number" value="${totalPrice}" minFractionDigits="2" maxFractionDigits="2" /> </h4>

                            <div class="" >
                                <div class="form-group row mb-2">
                                    <label for="txtId" class="col-sm-3 col-form-label">Full Name: </label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="txtName" name="txtName"  value="${us.a_Fullname}" >
                                    </div>
                                </div>
                                <div class="form-group row mb-2">
                                    <label for="txtQuan" class="col-sm-3 col-form-label">Phone</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="txtPhone" name="txtPhone" value="${us.a_Phone}">
                                    </div>
                                </div>
                                <div class="form-group row mb-2">
                                    <label for="txtPic" class="col-sm-3 col-form-label">Address</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="txtAddress" name="txtAddress" value="${us.a_Address}">
                                    </div>
                                </div>

                                <div class="text-center">
                                    <input type="submit" class="btn btn-primary" id="ordering" name="name" value="ORDER" ${openOrder}>
                                </div>
                            </div>

                        </div>
                    </form>
                </c:if>
                <c:if test="${ empty listCart}">
                    <div>
                        <h2 class='text-center m-4'>Seem like you don't have any product in your cart, let's go and <button class="btn btn-primary" onclick="location.href='/home'">Buy some!</button> </h2>
                    </div>
                </c:if>
            </div>

        </div>

    </body>

    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js"></script>
    <script>
                                                $('#cartForm').validate({
                                                    rules: {

                                                        txtName: {
                                                            required: true
                                                        },
                                                        txtPhone: {
                                                            required: true,
                                                            minlength: 10,
                                                            maxlength: 10
                                                        },

                                                        txtAddress: {
                                                            required: true
                                                        }
                                                    },
                                                    messages: {
                                                        txtName: {
                                                            required: "Please enter your name!"
                                                        },
                                                        txtPhone: {
                                                            required: "Please enter your phone number!",
                                                            minlength: "Your phone number must have 10 numbers",
                                                            maxlength: "Your phone number must have 10 numbers"
                                                        },
                                                        txtAddress: {
                                                            required: "Please enter your address!"
                                                        }

                                                    }

                                                });
                                                function order() {
                                                    var form = document.getElementById("cartForm");
                                                    form.action = "/cart/update";
                                                    form.submit();
                                                }

                                                window.onload = function () {
                                                    var inputFields = document.querySelectorAll('input[id^="p"]');
                                                    var changeButtons = document.querySelectorAll('button[id^="change-"]');
                                                    var submitButton = document.getElementById("submit-button");

                                                    inputFields.forEach(function (inputField) {
                                                        inputField.addEventListener('input', function () {
                                                            showSubmitButton();
                                                        });
                                                    });
                                                    changeButtons.forEach(function (changeButton) {
                                                        changeButton.addEventListener('click', function () {
                                                            showSubmitButton();
                                                        });
                                                    });


                                                    function showSubmitButton() {
                                                        var orderbtn = document.getElementById("ordering");
                                                        var inputFields = document.querySelectorAll('input[id^="p"]');
                                                        var hasValue = false;
                                                        inputFields.forEach(function (inputField) {
                                                            if (inputField.value.trim() !== "") {
                                                                hasValue = true;
                                                            }
                                                        });
                                                        if (hasValue) {
                                                            submitButton.removeAttribute("disabled");
                                                            orderbtn.setAttribute("disabled", "");
                                                        } else {
                                                            submitButton.setAttribute("disabled", "");
                                                            orderbtn.removeAttribute("disabled");
                                                        }
                                                    }
                                                };

                                                // Define a function to update the variables whenever a 'p' input field is changed
                                                function updateVariables() {
                                                    var pFields = document.querySelectorAll('input[type="number"][id^="p"]');
                                                    var pTotal = 0;
                                                    // Loop through each 'p' input field and add its value to the total
                                                    for (var i = 0; i < pFields.length; i++) {
                                                        var pValue = parseInt(pFields[i].value);  // Convert the input value to a number
                                                        if (!isNaN(pValue)) {
                                                            pTotal += pValue;
                                                        }
                                                    }
                                                    // Enable the submit button if the 'p' total is greater than zero
                                                    var submitButton = document.getElementById("submit-button");
                                                    var orderbtn = document.getElementById("ordering");
                                                    if (pTotal > 0) {
                                                        submitButton.disabled = false;
                                                        orderbtn.disabled = true;
                                                    } else {
                                                        submitButton.disabled = true;
                                                        orderbtn.disabled = false;
                                                    }
                                                }

                                                // Add onchange event listener to all 'p' input fields
                                                var pFields = document.querySelectorAll('input[type="number"][id^="p"]');
                                                for (var i = 0; i < pFields.length; i++) {
                                                    pFields[i].addEventListener('change', updateVariables);
                                                }

                                                function checkValue(id) {
                                                    // Get the input element
                                                    var input = document.getElementById(id);

                                                    // Check if its value is 0
                                                    if (input.value < 1) {

                                                        if (confirm("Delete this product from cart?")) {
                                                            var url = "/cart/delete/" + id;

                                                            // Redirect to the servlet
                                                            window.location.href = url;
                                                        } else {
                                                            input.value = 1;
                                                        }
                                                        // Construct the URL to the servlet with a query param

                                                    }
                                                }
                                                // Trigger the checkValue() function when the page loads

                                                function minusQuan(id) {

                                                    var x = Number(document.getElementById(id).value);

                                                    document.getElementById(id).value = x - 1;
                                                    checkValue(id);
                                                }
                                                function plusQuan(id, currentQuan) {
                                                    var x = Number(document.getElementById(id).value);
                                                    if (x < currentQuan) {
                                                        document.getElementById(id).value = x + 1;
                                                    } else
                                                        document.getElementById(id).value = currentQuan;

                                                }


    </script>


</html>
