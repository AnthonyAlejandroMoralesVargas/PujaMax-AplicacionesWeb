<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                    <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-user"></i> <c:out value="${user.name}" />
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="BIDDER_PROFILE.jsp"><i class="fas fa-cogs"></i> Profile</a></li>
                        <li><a class="dropdown-item" href="../logout.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
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
                    <a href="BIDDER_LOTS_BOARD.jsp" class="nav-item me-3"><i class="fas fa-home"></i> Home</a>
                    <a href="BIDDER_HISTORY.jsp" class="nav-item"><i class="fas fa-history"></i> History</a>
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
            <div class="col-12 col-lg-6 ">
                <!-- Bootstrap Carousel for Product Images -->
                <div id="productCarousel" class="carousel slide mb-3" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <c:forEach var="image" items="${product.images}">
                            <div class="carousel-item ${image.active ? 'active' : ''}">
                                <img src="${image.url}" class="d-block w-100 rounded" alt="Product Image">
                            </div>
                        </c:forEach>
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

                <!-- Status -->
                <div class="alert alert-secondary mt-1 text-center">
                    <span><c:out value="${product.bidStatus}" /></span>
                </div>
            </div>

            <!-- Right Section: Bid Information -->
            <div class="col-12 col-lg-6 ">
                <div class="card">
                    <!-- Status -->
                    <div class="card-header d-flex justify-content-between align-items-center bg-light">
                        <span class="badge bg-success"><i class="fas fa-gavel"></i> ACTIVE</span>
                        <span>LOT ${product.lotNumber}</span>
                    </div>

                    <!-- Bid Info -->
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-6">
                                <label class="form-label">Bids</label>
                                <input type="text" class="form-control" value="${product.bids}" readonly>
                            </div>
                            <div class="col-6">
                                <label class="form-label">Current price</label>
                                <input type="text" class="form-control" value="${product.currentPrice}" readonly>
                            </div>
                        </div>

                        <h5 class="text-primary mb-3">BID INFORMATION</h5>
                        <div class="row mb-3">
                            <div class="col-6 d-flex ">
                                <label class="form-label ">Your offer</label>
                                <input type="text" class="form-control text-danger fw-bold" value="${product.userBid}" readonly>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#NEW_HIGH_BID">Submit</button>
                    </div>
                </div>
            </div>
        </section>

        <!-- Description -->
        <section class="mt-1">
            <div class="row g-3">
                <div class="col-12 col-md-4">
                    <button class="btn btn-outline-secondary w-100">
                        <i class="fas fa-tools"></i> Repairable
                    </button>
                </div>
                <div class="col-12 col-md-8">
                    <div class="border rounded p-3">
                        <h5 class="text-secondary">Description</h5>
                        <p>${product.description}</p>
                    </div>
                </div>
            </div>
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