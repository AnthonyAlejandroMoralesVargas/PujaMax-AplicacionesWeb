<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Bidder Lot</title>
	<!-- Bootstrap CSS -->
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
		rel="stylesheet">
	<!-- Font Awesome for icons -->
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<!-- Custom CSS -->
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/style.css">
</head>
<style>
.product-carousel img {
	max-height: 350px;
	object-fit: contain;
	width: 100%;
}
</style>
<body>
	<!-- Header -->
	<header class="header-container bg-primary text-white py-2">
		<div
			class="container d-flex justify-content-between align-items-center">
			<div class="d-flex align-items-center">
				<img src="${pageContext.request.contextPath}/images/OnlyB.png"
					alt="Logo" style="height: 50px; margin-right: 10px;">
				<h1 class="app-name mb-0 fs-4">Online Auction</h1>
			</div>
			<div class="d-flex align-items-center">
				<div class="dropdown">
					<a href="#" class="dropdown-toggle text-white"
						id="dropdownMenuButton" data-bs-toggle="dropdown"
						aria-expanded="false"><i class="fas fa-user"></i> User</a>
					<ul class="dropdown-menu dropdown-menu-end"
						aria-labelledby="dropdownMenuButton">
						<li><a class="dropdown-item"
							href="${pageContext.request.contextPath}/AddressManagementController?route=list"><i
								class="fas fa-cogs"></i> Profile</a></li>
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
			<div
				class="alert ${messageType == 'info' ? 'alert-success' : 'alert-danger'}"
				role="alert">${message}</div>
		</c:if>

		<!-- Navigation -->
		<section class="home-container">
			<nav class="nav-container d-flex bg-primary p-2 rounded">
				<a href="LotManagementController?route=listBidder&idLot=${idLot}"
					class="nav-item"><i class="fas fa-home"></i> Home</a> <a
					href="${pageContext.request.contextPath}/PlaceBidController?route=viewHistory"
					class="nav-item"><i class="fas fa-history"></i> History</a>
			</nav>
		</section>
		<!-- Product Cards -->
		<section class="lots-container">
			<div class="row">
				<c:forEach var="product" items="${products}">
					<div class="col-md-4 mb-4">
						<div class="card h-100">
							<!-- Carousel for product images -->
							<div id="carouselProduct_${product.idProduct}"
								class="carousel slide product-carousel" data-bs-ride="carousel">
								<div class="carousel-inner">
									<c:forEach var="photo" items="${product.photos}"
										varStatus="status">
										<div class="carousel-item ${status.first ? 'active' : ''}">
											<img src="data:image/jpeg;base64,${photo}"
												class="d-block w-100" alt="Product Image">
										</div>
									</c:forEach>
								</div>
								<button class="carousel-control-prev" type="button"
									data-bs-target="#carouselProduct_${product.idProduct}"
									data-bs-slide="prev">
									<span class="carousel-control-prev-icon" aria-hidden="true"></span>
									<span class="visually-hidden">Previous</span>
								</button>
								<button class="carousel-control-next" type="button"
									data-bs-target="#carouselProduct_${product.idProduct}"
									data-bs-slide="next">
									<span class="carousel-control-next-icon" aria-hidden="true"></span>
									<span class="visually-hidden">Next</span>
								</button>

							</div>

							<div class="card-body">
								<h5 class="card-title">${product.title}</h5>
								<p class="card-text">${product.description}</p>
								<p>
									<strong>Category:</strong> ${product.category}
								</p>
								<p>
									<strong>Current Price:</strong>
									$${product.priceCurrent}
								</p>
							</div>
							<a
								href="${pageContext.request.contextPath}/PlaceBidController?route=productDetails&idProduct=${product.idProduct}&idLot=${idLot}"
								class="btn btn-primary w-100">VIEW THIS PRODUCT</a>

						</div>
					</div>
				</c:forEach>
			</div>
		</section>
	</main>
	<!-- Footer -->
	<footer class="text-center bg-dark text-white py-3 mt-4">
		<p>&copy; 2025 BIDMAX | All rights reserved</p>
	</footer>
	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
