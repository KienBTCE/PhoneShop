
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="//cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css"/>
        <script src="//cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
        <style>

            body{
                /*background-color: black;*/
            }
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
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

            <div class="container"> 

                <div class="h1 text-center mt-3">PRODUCT LIST</div>
                <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                    Add new product
                </button>



                <!-- ADDING PRODUCT -->
                <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="addProduct" action="/home" method="post" class="myform row  p-2 m-1" enctype="multipart/form-data">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Add New Product</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group row mb-2">
                                        <label for="txtId" class="col-sm-2 col-form-label">ID</label>
                                        <div class="col-sm-8">
                                            <input type="hidden" id="list" value="${listID}">
                                        <input type="text" class="form-control" id="txtId" name="txtId" >
                                    </div>
                                </div>
                                <div class="form-group row mb-2">
                                    <label for="txtId" class="col-sm-2 col-form-label">Name</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="txtName" name="txtName" >
                                    </div>
                                </div>

                                <div class="form-group row mb-2">
                                    <label for="txtQuan" class="col-sm-2 col-form-label">Quantity</label>
                                    <div class="col-sm-8">
                                        <input type="number" step="1" class="form-control" oninput="validity.valid||(value='');" min="0" id="txtQuan" name="txtQuan">
                                    </div>
                                </div>
                                <div class="form-group row mb-2">
                                    <label for="txtPrice" class="col-sm-2 col-form-label">Price</label>
                                    <div class="col-sm-8">
                                        <input type="number" step="any" inputmode="decimal" min="0" oninput="validity.valid||(value='')" 
                                               onblur = "this.value = parseFloat(parseFloat(this.value).toFixed(2));"
                                               class="form-control" id="txtPrice" name="txtPrice">
                                    </div>
                                </div>
                                <div class="form-group row mb-2">
                                    <label for="txtPic" class="col-sm-2 col-form-label">Picture</label>
                                    <div class="col-sm-8">
                                        <input type="file" accept="image/png, image/gif, image/jpeg, image/jpg" class="form-control" id="txtPic" name="txtPic">
                                    </div>
                                </div>
                                <div class="form-group row mb-2">
                                    <label for="txtDes" class="col-sm-2 col-form-label">Description</label>
                                    <div class="col-sm-8">
                                        <textarea name="txtDes" id="txtDes" cols="30" rows="4"></textarea>
                                    </div>
                                </div>
                                <div class="form-group row mb-2">
                                    <label for="txtDes" class="col-sm-2 col-form-label">Category</label>
                                    <div class="col-sm-8 mt-2">
                                        <select id="txtCate" name="txtCate">
                                            <c:forEach var="c" items="${listCate}" >
                                                <option value="${c.c_Id}">${c.c_Name}</option>
                                            </c:forEach> 
                                        </select>

                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">Canncel</button>
                                <input class="btn-primary btn " type="submit" name="btnAdd" value="Submit">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- ADDING PRODUCT -->


            <table id="example" class="bg-light border border-dark table table-striped table-bordered">
                <thead>
                    <tr >
                        <th>Id</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Picture</th>
                        <th>Description</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach var="p" items="${listProduct}">
                        <tr>
                            <td>${p.pd_Id}</td>
                            <td>${p.pd_Name}</td>
                            <td><c:forEach var="cate" items="${listCate}" >
                                    <c:if test="${p.c_Id==cate.c_Id}">${cate.c_Name}</c:if>
                                </c:forEach> </td>
                            <td>${p.pd_Quan}</td>
                            <td>${p.pd_Price}</td>
                            <td><img src="${p.pd_Img}" alt="${p.pd_Img}" width="auto" height="200px"/></td>
                            <td>${p.pd_Des}</td>

                            <td>
                                <button type="button" class="btn btn-primary btn-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop2${p.pd_Id}">
                                    Update
                                </button><!-- comment -->
                                <div class="modal fade" id="staticBackdrop2${p.pd_Id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <form id="addProduct" action="/home" method="post" class=" row  p-2 m-1" enctype="multipart/form-data">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Update Product</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">

                                                    <div class="form-group row mb-2">
                                                        <label for="txtId" class="col-sm-2 col-form-label">ID</label>
                                                        <div class="col-sm-8">

                                                            <input type="text" class="form-control" id="txtId" name="txtId" onblur="checkProductExist()" value="${p.pd_Id}">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row mb-2">
                                                        <label for="txtName" class="col-sm-2 col-form-label">Name</label>
                                                        <div class="col-sm-8">
                                                            <input type="text" class="form-control" id="txtName" name="txtName" value="${p.pd_Name}" >
                                                        </div>
                                                    </div>
                                                    <div class="form-group row mb-2">
                                                        <label for="txtDes" class="col-sm-2 col-form-label">Category</label>
                                                        <div class="col-sm-8 mt-2">
                                                            <select id="txtCate" name="txtCate">
                                                                <c:forEach var="c" items="${listCate}" >
                                                                    <option value="${c.c_Id}" ${p.c_Id==c.c_Id? 'selected="selected"':''} >${c.c_Name}</option>
                                                                </c:forEach> 
                                                            </select>

                                                        </div>
                                                    </div>
                                                    <div class="form-group row mb-2">
                                                        <label for="txtQuan" class="col-sm-2 col-form-label">Quantity</label>
                                                        <div class="col-sm-8">
                                                            <input type="number" class="form-control" oninput="validity.valid||(value='');" min="0" id="txtQuan" name="txtQuan" value="${p.pd_Quan}">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row mb-2">
                                                        <label for="txtPrice" class="col-sm-2 col-form-label">Price</label>
                                                        <div class="col-sm-8">
                                                            <input type="text" class="form-control" id="txtPrice" name="txtPrice" value="${p.pd_Price}">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row mb-2">
                                                        <label for="txtPic" class="col-sm-2 col-form-label">Picture</label>
                                                        <div class="col-sm-8">
                                                            <img src="${p.pd_Img}" alt="alt" width="auto" height="100px"/>
                                                            <input type="hidden" name="txtPicOr" value="${p.pd_Img}">
                                                            <input type="file" accept="image/png, image/gif, image/jpeg" class="form-control" id="txtPic" name="txtPic">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row mb-2">
                                                        <label for="txtDes" class="col-sm-2 col-form-label">Description</label>
                                                        <div class="col-sm-8">
                                                            <textarea name="txtDes" id="txtDes" cols="30" rows="4">${p.pd_Des}</textarea>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="modal-footer">
                                                    <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                    <input class="btn-primary btn " type="submit" name="btnUpdate" value="Update">
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td><a class="text-danger" onclick="return confirm('Are you sure want to delete this product?')"; href="/management/product/delete/${p.pd_Id}">Delete</a></td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>



        </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js"></script>
    <script>
                                $.validator.addMethod("checkProductID", function (value, element) {
                                    var listI = document.getElementById("list").value;
                                    var listID = listI.slice(1, -1).split(',');
                                    for (var i = 0; i < listID.length; i++) {
                                        if (value.trim() === listID[i].trim()) {
                                            return false;
                                        }
                                    }
                                    return true;
                                }, "This product is already exist");
    </script>
    <script>
        $(document).ready(function () {
            $('.myform').each(function () {
                $(this).validate({

                    rules: {
                        txtId: {
                            required: true,
                            checkProductID: true
                        },
                        txtName: {
                            required: true,
                            minlength: 3,
                            maxlength: 23
                        },
                        txtQuan: {
                            required: true,
                            digits: true
                        },
                        txtPrice: {
                            required: true,
                            number: true
                        },
                        txtPic: {
                            required: true
                        },
                        txtDes: {
                            required: true
                        },
                        txtCate: {
                            required: true
                        }
                    },
                    messages: {
                        txtId: {
                            required: "Please enter id!"

                        },
                        txtName: {
                            required: "Please enter name!"
                        },
                        txtQuan: {
                            required: "Please enter quantity of product!"
                        },
                        txtPrice: {
                            required: "Please enter the price!"
                        },
                        txtPic: {
                            required: "Please upload picture!"
                        },
                        txtDes: {
                            required: "Please enter some description about product!"
                        },
                        txtCate: {
                            required: "Please choose the category for product!"
                        }
                    },
                    submitHandler: function (form) {
                        form.submit();
                    }
                });
            });
        });
    </script>

</html>
