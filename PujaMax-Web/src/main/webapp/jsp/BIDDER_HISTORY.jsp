<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Bidder History</title>
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
<body>
	<!-- Header -->
	<header class="header-container">
		<div
			class="container d-flex justify-content-between align-items-center">
			<div class="d-flex align-items-center">
				<img src="${pageContext.request.contextPath}/images/OnlyB.png"
					alt="Logo" style="height: 40px; margin-right: 10px;">
				<h1 class="app-name mb-0">Online Auction</h1>
			</div>
			<div class="d-flex align-items-center">
				<div class="dropdown">
					<a href="#" class="dropdown-toggle" id="dropdownMenuButton"
						data-bs-toggle="dropdown" aria-expanded="false"><i
						class="fas fa-user"></i> User</a>
					<ul class="dropdown-menu dropdown-menu-end"
						aria-labelledby="dropdownMenuButton">
						<li><a class="dropdown-item"
							href="${pageContext.request.contextPath}/AddressManagementController?route=list"><i
								class="fas fa-cogs"></i> Profile</a></li>
						<li><a class="dropdown-item"
							href="${pageContext.request.contextPath}/LoginController?route=logOut"><i
								class="fas fa-sign-out-alt"></i> Logout</a>
					</ul>
				</div>
			</div>
		</div>
	</header>
	<!-- Main Container -->
	<main class="main-container container my-4">
		<!-- Filter Section -->
		<section class="home-container">
			<nav class="nav-container">
				<a href="LotManagementController?route=listBidder&idLot=${idLot}"
					class="nav-item"><i class="fas fa-home"></i> Home</a> <a
					href="${pageContext.request.contextPath}/PlaceBidController?route=viewHistory"
					class="nav-item"><i class="fas fa-history"></i> History</a>
			</nav>
		</section>
		<!-- Dynamic Product Cards -->
		<c:forEach var="bid" items="${bids}">
			<div class="product-card mb-3 p-3 shadow rounded"
				style="background-color: white;">
				<div class="row">
					<!-- Fotos del Producto -->
					<div
						class="col-md-4 d-flex justify-content-center align-items-center flex-column">
						<c:choose>
							<c:when test="${not empty bid.product.photos}">
								<!-- Mostrar la primera foto disponible -->
								<img src="data:image/jpeg;base64,${bid.product.photos[0]}"
									alt="${bid.product.title}" class="product-img mb-3"
									style="max-width: 100%; height: auto;">
							</c:when>
						</c:choose>
						<a href="PRODUCT.jsp?productId=${bid.product.idProduct}"
							class="btn btn-primary w-100">VIEW THIS LOT</a>
					</div>

					<!-- Detalles del Producto -->
					<div class="col-md-4 d-flex flex-column justify-content-center">
						<div>
							<strong>Title:</strong> ${bid.product != null ? bid.product.title : 'No Title'}<br>
							<strong>Price:</strong> $${bid.currentPrice != null ?
							bid.currentPrice : '0.00'}<br> <strong>Date:</strong>
							${bid.dateBid != null ? bid.dateBid : 'N/A'}<br> <strong>Description:</strong>
							${bid.product != null && bid.product.description != null ? bid.product.description : 'No Description'}
						</div>
					</div>
					<div
						class="col-md-4 d-flex flex-column justify-content-center align-items-center">
						<c:choose>
							<c:when test="${bid.state == 'WON'}">
								<div class="btn btn-success w-50 mb-2">Won</div>
								<button class="btn btn-primary w-50" data-bs-toggle="modal"
									data-bs-target="#SUBMIT_RECEIPT_PAYMENT"
									data-bid-id="${bid.idBid}">Submit Receipt</button>
							</c:when>
							<c:when test="${bid.state == 'PENDING_DELIVERY'}">
								<div class="btn btn-warning w-50 mb-2">Pending Delivery</div>
								<button class="btn btn-primary w-50" data-bs-toggle="modal"
									data-bs-target="#DESCRIPTION_ADDRESS"
									data-bid-id="${bid.idBid}">View Address</button>
							</c:when>
							<c:when test="${bid.state == 'LOST'}">
								<div class="btn btn-danger w-50 mb-2">Lost</div>
								<button class="btn btn-primary w-50" data-bs-toggle="modal"
									data-bs-target="#DESCRIPTION_REJECTION"
									data-bid-id="${bid.idBid}">View Reason</button>
							</c:when>
							<c:otherwise>
								<div class="btn btn-secondary w-50 mb-2">State:
									${bid.state}</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</c:forEach>
		<!-- No Bids Found -->
		<c:if test="${empty bids}">
			<div class="alert alert-warning text-center">No bids found for
				this bidder.</div>
		</c:if>
	</main>
	<!-- Footer -->
	<footer class="text-center bg-dark text-white py-3 mt-4">
		<p>&copy; 2025 BIDMAX | All rights reserved</p>
	</footer>
	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<div class="modal fade" id="SUBMIT_RECEIPT_PAYMENT" tabindex="-1"
	aria-labelledby="SUBMIT_RECEIPT_PAYMENTLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header bg-primary text-white">
				<h5 class="modal-title" id="SUBMIT_RECEIPT_PAYMENTLabel">
					<i class="fas fa-layer-group"></i> Upload Receipt and Bidder
					Information
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<form action="PayBidController?route=updateReceipt" method="POST"
				enctype="multipart/form-data">
				<div class="modal-body">
					<!-- Hidden fields to send additional data -->
					<input type="hidden" name="idBid" id="idBid"> <input
						type="hidden" name="bidderDni" value="${bidderDni}">
					<!-- Bidder information -->
					<div class="mb-3">
						<label for="bidderInfo" class="form-label">Bidder
							Information</label>
						<textarea class="form-control" id="bidderInfo" rows="4"
							placeholder="Enter bidder information." readonly>
	Username: 
	Email: 
	Phone: 
	                        </textarea>
					</div>
					<!-- File upload -->
					<div class="mb-3">
						<label for="receiptFile" class="form-label">Upload Receipt</label>
						<input class="form-control" type="file" name="document"
							id="receiptFile" required>
					</div>
				</div>
				<div class="modal-footer justify-content-center">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Cancel</button>
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
	    // JavaScript to dynamically update the modal's idBid field
	    document.addEventListener("DOMContentLoaded", function () {
	        const submitReceiptModal = document.getElementById('SUBMIT_RECEIPT_PAYMENT');
	        submitReceiptModal.addEventListener('show.bs.modal', function (event) {
	            const button = event.relatedTarget; // Button that triggered the modal
	            const bidId = button.getAttribute('data-bid-id'); // Extract bid ID from data attribute
	            document.getElementById('idBid').value = bidId; // Set bid ID in hidden field
	        });
	    });
	</script>
<div class="modal fade" id="DESCRIPTION_REJECTION" tabindex="-1"
	aria-labelledby="DESCRIPTION_REJECTIONLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title" id="DESCRIPTION_REJECTIONLabel">
					<i class="fas fa-layer-group"></i> Description Rejection
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form>
					<div class="mb-3">
						<label for="rejectedDescription" class="form-label">Description
							of the rejected decision</label>
						<textarea id="rejectedDescription" class="form-control" rows="3"
							placeholder="Description" readonly></textarea>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-success"
					data-bs-dismiss="modal">Accept</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="DESCRIPTION_ADDRESS" tabindex="-1"
	aria-labelledby="DESCRIPTION_ADDRESSLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header bg-primary text-white">
				<h5 class="modal-title" id="DESCRIPTION_ADDRESSLabel">
					<i class="fas fa-layer-group"></i> Delivery Address
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<!-- Modal Body -->
			<div class="modal-body">
				<form>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="name" class="form-label">Name</label> <input
								type="text" class="form-control" id="name" value="Name" readonly>
						</div>
						<div class="col-md-6 mb-3">
							<label for="company" class="form-label">Company</label> <input
								type="text" class="form-control" id="company" value="Company"
								readonly>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="province" class="form-label">Province</label> <input
								type="text" class="form-control" id="province" value="Province"
								readonly>
						</div>
						<div class="col-md-6 mb-3">
							<label for="city" class="form-label">City</label> <input
								type="text" class="form-control" id="province" value="City"
								readonly>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="mainStreet" class="form-label">Main Street</label> <input
								type="text" class="form-control" id="mainStreet"
								value="Main Streett" readonly>
						</div>
						<div class="col-md-6 mb-3">
							<label for="secondaryStreet" class="form-label">Secondary
								Street</label> <input type="text" class="form-control"
								id="secondaryStreet" value="Secondary Street" readonly>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="zipCode" class="form-label">Zip Code</label> <input
								type="text" class="form-control" id="zipCode" value="Zip Code"
								readonly>
						</div>
						<div class="col-md-6 mb-3">
							<label for="houseNumber" class="form-label">House/Apartment
								No.</label> <input type="text" class="form-control" id="houseNumber"
								value="House/Apartment No.1" readonly>
						</div>
					</div>
				</form>
			</div>
			<!-- Modal Footer -->
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-success"
					data-bs-dismiss="modal">Accept</button>
			</div>
		</div>
	</div>
	<script>
	        // Handle Delivery modal
	        const deliveryModal = document.getElementById('DESCRIPTION_ADDRESS');
	        deliveryModal.addEventListener('show.bs.modal', function (event) {
	            const button = event.relatedTarget;
	            const bidId = button.getAttribute('data-bid-id');
	            // Example: Fetch and populate delivery details using bidId
	            console.log(`Fetching delivery details for bid ID: ${bidId}`);
	        });
	        // Handle Rejection modal
	        const rejectionModal = document.getElementById('DESCRIPTION_REJECTION');
	        rejectionModal.addEventListener('show.bs.modal', function (event) {
	            const button = event.relatedTarget;
	            const bidId = button.getAttribute('data-bid-id');
	
	            // Example: Fetch and populate rejection reason using bidId
	            console.log(`Fetching rejection reason for bid ID: ${bidId}`);
	        });
	    });
	</script>
</html>
