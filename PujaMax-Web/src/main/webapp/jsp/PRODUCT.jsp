<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Product</title>
	<!-- Bootstrap CSS -->
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
		rel="stylesheet">
	<!-- Font Awesome for icons -->
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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
    <header class="header-container">
        <div class="container d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
                <img src="${pageContext.request.contextPath}/images/OnlyB.png" alt="Logo" style="height: 50px; margin-right: 10px;">
                <h1 class="app-name mb-0">Online Auction</h1>
            </div>
            <div class="d-flex align-items-center">
                <div class="dropdown">
                    <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user"></i> User</a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AddressManagementController?route=list"><i class="fas fa-cogs"></i> Profile</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginController?route=logOut"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
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
                    <a href="LotManagementController?route=listBidder&idLot=${idLot}" class="nav-item"><i class="fas fa-home"></i> Home</a>
                    <a href="${pageContext.request.contextPath}/PayBidController?route=viewHistory" class="nav-item"><i class="fas fa-history"></i> History</a>
                </div>
            </nav>
        </section>

        <a href="${pageContext.request.contextPath}/PlaceBidController?route=list&idLot=${idLot}" class="btn btn-outline-secondary d-inline-flex align-items-center">
            <i class="fas fa-arrow-left me-2"></i> Return to Auction
        </a>

        <!-- Product Details -->
        <section class="bidProduct row gy-4">
            <h2 class="text-primary text-center text-lg-start">${product.title}</h2>
            <!-- Left Section: Product Details -->
            <div class="col-12 col-lg-6">
                <!-- Carousel for product images -->
                <div id="carouselProduct_${product.idProduct}" class="carousel slide product-carousel" data-bs-ride="carousel">
                    <div class="carousel-inner">
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
                </div>
            </div>

            <!-- Right Section: Bid Information -->
            <div class="col-12 col-lg-6">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center bg-light">
                        <span class="badge bg-success"><i class="fas fa-gavel"></i> ${product.lot.state}</span>
                    </div>

                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-6">
                                <label class="form-label">Bids</label>
                                <input type="text" class="form-control" value="${bidCount}" readonly>
                            </div>
                            <div class="col-6">
                                <label class="form-label">Current price</label>
                                <h3 class="text-primary">${product.priceCurrent}</h3>
                                <input type="text" class="form-control" value="${product.priceCurrent}" readonly>
                            </div>
                        </div>

                        <h5 class="text-primary mb-3">BID INFORMATION</h5>
                        <form id="bidForm">
                            <input type="hidden" name="idProduct" value="${product.idProduct}">
                            <input type="hidden" name="idLot" value="${idLot}">
                            <div class="mb-3">
                                <label for="bidAmount" class="form-label">Your Offer</label>
                                <input type="number" step="0.01" name="bidAmount" id="bidAmount" class="form-control" placeholder="Enter your bid" required>
                            </div>
                            <button type="button" class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#NEW_HIGH_BID">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <!-- Description -->
		<section class="mt-1">
			<div class="row g-3">
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
	    <p>&copy; 2025 BIDMAX | All rights reserved</p>
	</footer>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    

    <!-- Modal for New High Bid Confirmation -->
    <div class="modal fade" id="NEW_HIGH_BID" tabindex="-1" aria-labelledby="NEW_HIGH_BIDLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="NEW_HIGH_BIDLabel"><i class="fas fa-gavel"></i> New High Bid</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="bidConfirmationForm" action="${pageContext.request.contextPath}/PlaceBidController?route=placeBid" method="post">
                        <input type="hidden" name="idProduct" value="${product.idProduct}">
                        <input type="hidden" name="idLot" value="${idLot}">
                        <input type="hidden" name="bidAmount" id="hiddenBidAmount">
                        <div class="mb-3">
							<label for="productTitle" class="form-label">Title of
								product</label> <input type="text" id="productTitle"
								class="form-control" value="${product.title}" readonly>
						</div>
						<div class="mb-3">
							<label for="currentPrice" class="form-label">Current
								Price</label> <input type="text" id="currentPrice" class="form-control"
								value="${product.priceCurrent}" readonly>
						</div>
                        <div class="mb-3">
                            <label for="bidAmount" class="form-label">Your Bid</label>
                            <input type="text" id="bidAmountDisplay" class="form-control" readonly>
                        </div>
                    </form>
                </div>
                <div class="modal-footer justify-content-center">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" id="confirmBidButton">Confirm</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal para mensajes informativos y de error -->
	<div class="modal modal-info" id="infoModal" tabindex="-1"
		aria-labelledby="infoModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div
					class="modal-body ${messageType == 'success' ? 'success' : 'error'}">
					<i
						class="fas ${messageType == 'success' ? 'fa-info-circle text-info' : 'fa-exclamation-circle text-danger'}"></i>
					<span>${message}</span>
				</div>
			</div>
		</div>
	</div>

    <script>
		// Capture the bid amount and display it in the modal
		document
				.querySelector('[data-bs-target="#NEW_HIGH_BID"]')
				.addEventListener(
						'click',
						function() {
							const bidAmount = document
									.querySelector('[name="bidAmount"]').value;
							document.getElementById('bidAmountDisplay').value = bidAmount;
							document.getElementById('hiddenBidAmount').value = bidAmount;
						});

		// Submit the form when Confirm is clicked
		document.getElementById('confirmBidButton').addEventListener('click',
				function() {
					document.getElementById('bidConfirmationForm').submit();
				});
	</script>

	<script>
  document.addEventListener("DOMContentLoaded", function () {
    const infoModalElement = document.getElementById("infoModal");

    if (infoModalElement && "${message}" !== "") {
      const infoModal = new bootstrap.Modal(infoModalElement, {
        backdrop: false, // Sin fondo oscuro
        keyboard: false  // Desactiva cerrar con el teclado
      });

      // Mostrar el modal
      infoModal.show();

      // Cerrar automáticamente después de 5 segundos
      setTimeout(() => {
        infoModal.hide();
      }, 5000);
    }
  });
</script>
</body>

</html>