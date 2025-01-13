<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <!-- Header -->
    <header class="header-container">
        <div class="container d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
                <img src="${pageContext.request.contextPath}/images/OnlyB.png" alt="Logo" style="height: 50px; margin-right: 10px;">
                <h1 class="app-name mb-0">PUJAMAX Online Auction</h1>
            </div>
            <div class="d-flex align-items-center">
                <div class="dropdown">
                    <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown"
                        aria-expanded="false"><i class="fas fa-user"></i> User</a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="UserProfileController"><i class="fas fa-cogs"></i>
                                Profile</a></li>
                        <li><a class="dropdown-item" href="LogoutController"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header>

    <!-- Main Container -->
    <main class="main-container container my-4">
        <!-- Display Messages -->
        <c:if test="${message != null}">
            <div class="alert ${messageType == 'info' ? 'alert-success' : 'alert-danger'}" role="alert">
                ${message}
            </div>
        </c:if>

        <!-- Navigation -->
        <section class="home-container">
            <nav class="nav-container">
                <a href="LotManagementController?route=list&idLot=${idLot}" class="nav-item"><i class="fas fa-home"></i> Home</a>
                <a href="#" class="nav-item" data-bs-toggle="modal" data-bs-target="#PRODUCT_FORM"><i
                        class="fas fa-plus-circle"></i> Add Product</a>
            </nav>
        </section>

        <!-- Product Cards -->
        <section class="lots-container">
            <div class="row">
                <c:forEach var="product" items="${products}">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <img src="ProductImage?idProduct=${product.idProduct}" class="card-img-top" alt="Product Image">
                            <div class="card-body">
                                <h5 class="card-title">${product.title}</h5>
                                <p class="card-text">${product.description}</p>
                                <p><strong>Category:</strong> ${product.category}</p>
                                <p><strong>Initial Price:</strong> $${product.priceInitial}</p>
                            </div>
                            <div class="card-footer d-flex justify-content-between">
                                <a href="#" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#EDIT_PRODUCT_MODAL_${product.idProduct}">Edit</a>
                                <a href="#" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#DELETE_MODAL_${product.idProduct}">Delete</a>
                            </div>
                        </div>
                    </div>

                    <!-- Edit Product Modal -->
                    <div class="modal fade" id="EDIT_PRODUCT_MODAL_${product.idProduct}" tabindex="-1" aria-labelledby="EDIT_PRODUCT_MODALLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header bg-primary text-white">
                                    <h5 class="modal-title" id="EDIT_PRODUCT_MODALLabel">Edit Product</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="ProductManagementController" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="route" value="saveExisting">
                                        <input type="hidden" name="txtId" value="${product.idProduct}">
                                        <input type="hidden" name="txtIdLot" value="${idLot}">
                                        <div class="mb-3">
                                            <label for="editProductTitle_${product.idProduct}" class="form-label">Title</label>
                                            <input type="text" class="form-control" id="editProductTitle_${product.idProduct}" name="txtTitle" value="${product.title}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="editProductCategory_${product.idProduct}" class="form-label">Category</label>
                                            <input type="text" class="form-control" id="editProductCategory_${product.idProduct}" name="txtCategory" value="${product.category}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="editProductDescription_${product.idProduct}" class="form-label">Description</label>
                                            <textarea class="form-control" id="editProductDescription_${product.idProduct}" name="txtDescription" rows="3" required>${product.description}</textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="editProductPrice_${product.idProduct}" class="form-label">Initial Price</label>
                                            <input type="number" class="form-control" id="editProductPrice_${product.idProduct}" name="txtPriceInitial" value="${product.priceInitial}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="editProductPhoto_${product.idProduct}" class="form-label">Photo</label>
                                            <input type="file" class="form-control" id="editProductPhoto_${product.idProduct}" name="txtPhoto">
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            <button type="submit" class="btn btn-primary">Save</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Delete Product Modal -->
                    <div class="modal fade" id="DELETE_MODAL_${product.idProduct}" tabindex="-1" aria-labelledby="DELETE_MODAL_Label" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header bg-danger text-white">
                                    <h5 class="modal-title" id="DELETE_MODAL_Label">Confirm Deletion</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p>Are you sure you want to delete the product <strong>${product.title}</strong>?</p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    <form action="ProductManagementController" method="post">
                                        <input type="hidden" name="route" value="delete">
                                        <input type="hidden" name="idProduct" value="${product.idProduct}">
                                        <input type="hidden" name="idLot" value="${idLot}">
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </main>

    <!-- Add Product Modal -->
    <div class="modal fade" id="PRODUCT_FORM" tabindex="-1" aria-labelledby="PRODUCT_FORMLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="PRODUCT_FORMLabel">Add Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="ProductManagementController" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="route" value="saveNew">
                        <input type="hidden" name="txtIdLot" value="${idLot}">
                        <div class="mb-3">
                            <label for="productTitle" class="form-label">Title</label>
                            <input type="text" class="form-control" id="productTitle" name="txtTitle" placeholder="Enter product title" required>
                        </div>
                        <div class="mb-3">
                            <label for="productCategory" class="form-label">Category</label>
                            <input type="text" class="form-control" id="productCategory" name="txtCategory" placeholder="Enter category" required>
                        </div>
                        <div class="mb-3">
                            <label for="productDescription" class="form-label">Description</label>
                            <textarea class="form-control" id="productDescription" name="txtDescription" rows="3" placeholder="Enter description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="initialPrice" class="form-label">Initial Price</label>
                            <input type="number" class="form-control" id="initialPrice" name="txtPriceInitial" placeholder="Enter initial price" required>
                        </div>
                        <div class="mb-3">
                            <label for="productPhoto" class="form-label">Photo</label>
                            <input type="file" class="form-control" id="productPhoto" name="txtPhoto">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
