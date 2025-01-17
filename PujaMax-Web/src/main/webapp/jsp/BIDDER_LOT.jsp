<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BIDDER_LOT</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="../framework/myframework.css">
</head>

<body>
    <!-- Header -->
    <header class="header-container">
        <div class="container d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
                <img src="../images/logo1.png" alt="Logo" style="height: 50px; margin-right: 10px;">
                <h1 class="app-name mb-0">PUJAMAX Online Auction</h1>
            </div>
            <div class="d-flex align-items-center">
                <div class="dropdown">
                    <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown"
                        aria-expanded="false"><i class="fas fa-user"></i> <c:out value="${user.name}" /></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="BIDDER_PROFILE.jsp"><i class="fas fa-cogs"></i> Profile</a></li>
                        <li><a class="dropdown-item" href="../logout.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header>

    <!-- Main Container -->
    <main class="main-container container my-4">
        <!-- Navigation -->
        <section class="home-container">
            <nav class="nav-container">
                <a href="BIDDER_LOTS_BOARD.jsp" class="nav-item"><i class="fas fa-home"></i> Home</a>
                <a href="BIDDER_HISTORY.jsp" class="nav-item"><i class="fas fa-history"></i> History</a>
            </nav>
        </section>
        <!-- Filters Section -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            
            <div class="dropdown">
                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="categoryDropdown"
                    data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fas fa-filter"></i> Categories
                </button>
                <ul class="dropdown-menu" aria-labelledby="categoryDropdown">
                    <c:forEach var="category" items="${categories}">
                        <li><a class="dropdown-item" href="#">${category.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <!-- Product Cards -->
        <section class="lots-container">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <div class="row">
                        <!-- Product Image Section -->
                        <div class="col-md-4 d-flex flex-column">

                            <img src="${product.image}" alt="Product Image" class="product-img mb-3">
                            <a href="PRODUCT.jsp?id=${product.id}" class="btn btn-view w-100">VIEW THIS PRODUCT</a>

                        </div>

                        <!-- Product Details Section -->
                        <div class="col-md-8">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h4 class="mb-0">${product.title}</h4>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <strong>Current Price:</strong>
                                    <div class="current-price">${product.currentPrice}</div>
                                </div>
                                <div class="col-md-4">
                                    <strong>Condition:</strong>
                                    <div><i class="fas fa-tools"></i> ${product.condition}</div>
                                </div>
                                <div class="col-md-4">
                                    <strong>Lot:</strong>
                                    <div><i class="fas fa-tag"></i> ${product.lot}</div>
                                </div>
                            </div>
                            <div class="description">
                                <p>${product.description}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </section>
    </main>

    <!-- Footer -->
    <footer class="text-center bg-dark text-white py-3 mt-4">
        <p>&copy; 2024 PujaMax | All rights reserved</p>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
