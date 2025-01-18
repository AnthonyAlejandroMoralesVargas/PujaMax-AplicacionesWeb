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
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AddressManagementController?route=list"><i class="fas fa-cogs"></i>
                                Profile</a></li>
                        <li><a class="dropdown-item"
                           href="${pageContext.request.contextPath}/LoginController?route=logOut"><i
                            class="fas fa-sign-out-alt"></i> Logout</a>
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
                <a href="LotManagementController?route=listBidder&idLot=${idLot}" class="nav-item"><i class="fas fa-home"></i> Home</a>
                <a href="${pageContext.request.contextPath}/PlaceBidController?route=history" class="nav-item"><i class="fas fa-history"></i>
                History</a>
            </nav>
        </section>

        <!-- Product Cards -->
        <section class="lots-container">
            <div class="row">
                <c:forEach var="product" items="${products}">
                <!-- Product Image Section -->
                    <div class="col-md-4 d-flex flex-column">
                    	<a href="PlaceBidController?route=productDetails&idProduct=${product.idProduct}" class="btn btn-primary">View Product</a>

                    </div>
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <img src="PlaceBidController?route=list&idProduct=${product.idProduct}" class="card-img-top" alt="Product Image">

                            <div class="card-body">
                                <h5 class="card-title">${product.title}</h5>
                                <p class="card-text">${product.description}</p>
                                <p><strong>Category:</strong> ${product.category}</p>
                                <p><strong>Initial Price:</strong> $${product.priceInitial}</p>
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