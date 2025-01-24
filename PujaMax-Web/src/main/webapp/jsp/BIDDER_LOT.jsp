<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bidder Lot</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <!-- Header -->
    <header class="header-container bg-primary text-white py-2">
        <div class="container d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
                <img src="${pageContext.request.contextPath}/images/OnlyB.png" alt="Logo" style="height: 50px; margin-right: 10px;">
                <h1 class="app-name mb-0 fs-4">PUJAMAX Online Auction</h1>
            </div>
            <div class="d-flex align-items-center">
                <div class="dropdown">
                    <a href="#" class="dropdown-toggle text-white" id="dropdownMenuButton" data-bs-toggle="dropdown"
                        aria-expanded="false"><i class="fas fa-user"></i> User</a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AddressManagementController?route=list"><i class="fas fa-cogs"></i>
                                Profile</a></li>
                        <li><a class="dropdown-item"
                                href="${pageContext.request.contextPath}/LoginController?route=logOut"><i
                                    class="fas fa-sign-out-alt"></i> Logout</a></li>
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
            <nav class="nav-container d-flex bg-primary p-2 rounded">
                <a href="LotManagementController?route=listBidder&idLot=${idLot}" class="nav-item"><i class="fas fa-home"></i> Home</a>
                <a href="${pageContext.request.contextPath}/PlaceBidController?route=viewHistory" class="nav-item"><i class="fas fa-history"></i>
                History</a>
            </nav>
        </section>
        <!-- Product Cards -->
        <section class="lots-container">
            <div class="row">
                <c:forEach var="product" items="${products}">
                    <div class="col-md-12 mb-4">
                        <div class="card d-flex flex-row align-items-center p-3">
                            <div id="carouselProduct_${product.idProduct}" class="carousel slide product-carousel" data-bs-ride="carousel">
                                <div class="carousel-inner col-3 d-flex flex-column align-items-center pe-3">
                                    <c:forEach var="photo" items="${product.photos}" varStatus="status">
                                        <div class="carousel-item ${status.first ? 'active' : ''}">
                                            <img src="data:image/jpeg;base64,${photo}" class="d-block w-100" alt="Product Image">
                                        </div>
                                    </c:forEach>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#carouselProduct_${product.idProduct}" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carouselProduct_${product.idProduct}" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                                <a href="${pageContext.request.contextPath}/PlaceBidController?route=productDetails&idProduct=${product.idProduct}" class="btn btn-primary w-100">VIEW THIS PRODUCT</a>
                                
                            </div>
                            <div class="col-md-8 ps-3">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <h4 class="card-title">${product.title}</h4>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-md-4">
                                        <strong>Current Price:</strong>
                                        <div class="current-price"> $ ${bid.currentPrice != null ?
							bid.currentPrice : product.priceInitial}</div>
                                    </div>
                                    <div class="col-md-4">
                                        <strong>Lot:</strong>
                                        <div><i class="fas fa-tag"></i> ${product.lot.idLot}</div>
                                    </div>
                                </div>
                                <div class="card-text bg-light border rounded p-3 pt-4">
                                    <p>${product.description}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </main>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
