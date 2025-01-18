<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PRODUCT</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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
                    <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-user"></i> User
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AddressManagementController?route=list">
                            <i class="fas fa-cogs"></i> Profile</a>
                        </li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginController?route=logOut">
                            <i class="fas fa-sign-out-alt"></i> Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </header>

    <!-- Main Container -->
    <main class="container my-4">
        <!-- Navigation -->
        <section class="home-container mb-3">
            <nav class="nav-container">
                <div class="d-flex flex-column flex-md-row">
                    <a href="LotManagementController?route=list&idLot=${idLot}" class="nav-item me-3">
                        <i class="fas fa-home"></i> Home
                    </a>
                    <a href="BIDDER_HISTORY.jsp" class="nav-item">
                        <i class="fas fa-history"></i> History
                    </a>
                </div>
            </nav>
        </section>

        <!-- Back Button -->
        <section class="btnBehind mb-4">
            <a href="javascript:history.back()" class="btn btn-outline-secondary d-inline-flex align-items-center">
                <i class="fas fa-arrow-left me-2"></i> Return to Auction
            </a>
        </section>

        <!-- Product Details -->
        <section class="bidProduct row gy-4">
            <h2 class="text-primary text-center text-lg-start">${product.title}</h2>
            <!-- Left Section: Product Details -->
            <div class="col-12 col-lg-6">
                <!-- Bootstrap Carousel for Product Images -->
                <div id="productCarousel" class="carousel slide mb-3" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="https://via.placeholder.com/300x200" class="d-block w-100 rounded" alt="Image 1">
                        </div>
                        <div class="carousel-item">
                            <img src="https://via.placeholder.com/300x200" class="d-block w-100 rounded" alt="Image 2">
                        </div>
                        <div class="carousel-item">
                            <img src="https://via.placeholder.com/300x200" class="d-block w-100 rounded" alt="Image 3">
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>

            <!-- Right Section: Bid Information -->
            <div class="col-12 col-lg-6">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center bg-light">
                        <span class="badge bg-success"><i class="fas fa-gavel"></i> ACTIVE</span>
                        <span>LOT 1</span>
                    </div>

                    <div class="card-body">
                        <form id="bidForm" action="PlaceBidController" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="route" value="submit">
                            <input type="hidden" name="idProduct" value="${product.idProduct}">
                            <div class="row mb-3">
                                <div class="col-6">
                                    <label class="form-label">Current price</label>
                                    <input type="text" class="form-control" value="${currentPrice}" readonly>
                                </div>
                                <div class="col-6">
                                    <label class="form-label">Your offer</label>
                                    <input type="number" id="yourBid" name="yourBid" class="form-control text-danger fw-bold" placeholder="Enter your bid" required min="${currentPrice + 1}">
                                </div>
                            </div>
                            <button type="button" class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#NEW_HIGH_BID">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </main>

    <!-- Modal for Confirmation -->
    <div class="modal fade" id="NEW_HIGH_BID" tabindex="-1" aria-labelledby="NEW_HIGH_BIDLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="NEW_HIGH_BIDLabel">
                        <i class="fas fa-layer-group"></i> Confirm Submission
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Product Title: <strong>${product.title}</strong></p>
                    <p>Current Price: <strong>${currentPrice}</strong></p>
                    <p>Your Offer: <strong><span id="modalYourBid"></span></strong></p>
                </div>
                <div class="modal-footer justify-content-center">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" id="confirmSubmit">Confirm</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="text-center bg-dark text-white py-3 mt-4">
        <p>&copy; 2024 PujaMax | All rights reserved</p>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.getElementById("confirmSubmit").addEventListener("click", function () {
            document.getElementById("bidForm").submit();
        });

        document.getElementById("yourBid").addEventListener("input", function () {
            document.getElementById("modalYourBid").innerText = this.value;
        });
    </script>
</body>

</html>
