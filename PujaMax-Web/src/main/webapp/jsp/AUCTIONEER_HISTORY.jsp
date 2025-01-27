<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Auctioneer History</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
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
	<main class="main-container container my-4">
		<section class="home-container">
			<nav class="nav-container">
				<a href="LotManagementController?route=listBidder&idLot=${idLot}"
					class="nav-item"><i class="fas fa-home"></i> Home</a> <a
					href="${pageContext.request.contextPath}/VerifyPaymentController?route=viewHistory"
					class="nav-item"><i class="fas fa-history"></i> History</a>
			</nav>
		</section>
		<c:forEach var="bid" items="${bids}">
			<div class="product-card mb-3 p-3 shadow rounded"
				style="background-color: white;">
				<div class="row">
					<div
						class="col-md-4 d-flex justify-content-center align-items-center flex-column">
						<c:choose>
							<c:when test="${not empty bid.product.photos}">
								<img src="data:image/jpeg;base64,${bid.product.photos[0]}"
									alt="${bid.product.title}" class="product-img mb-3"
									style="max-width: 100%; height: auto;">
							</c:when>
						</c:choose>
					</div>

					<div class="col-md-4 d-flex flex-column justify-content-center">
						<div>
							<strong>Title:</strong> ${bid.product != null ? bid.product.title : 'No Title'}<br>
							<strong>Price:</strong> $${bid.product.priceCurrent != null ?
							bid.product.priceCurrent : '0.00'}<br> <strong>Description:</strong>
							${bid.product != null && bid.product.description != null ? bid.product.description : 'No Description'}<br>

						</div>
					</div>
					<form id="bidForm" method="POST" action="${pageContext.request.contextPath}/VerifyPaymentController?route=aprove&idBid=${bid.id}">
					<!-- Status and Options -->
					<div class="col-md-4">
						<div class="status-box status-win">${bid.state}</div>
						<button type="submit" class="btn btn-primary"> Options</button>
					</div>
					</form>
					
				</div>
			</div>
		</c:forEach>
	</main>

	<!-- Footer -->
	<footer class="text-center bg-dark text-white py-3 mt-4">
		<p>&copy; 2024 PujaMax | All rights reserved</p>
	</footer>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<!-- Modal -->
<div class="modal fade" id="VIEW_RECEIPT" tabindex="-1"	aria-labelledby="VIEW_RECEIPTLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header bg-success text-white">
				<h5 class="modal-title" id="VIEW_RECEIPTLabel"><i class="fas fa-layer-group"></i> Confirm Receipt</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"	aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="bidConfirmationForm" action="VerifyPaymentController?route=aprove" method="POST" enctype="multipart/form-data">
				<input type="hidden" name="idReceipt" id="receiptIdField" value="${bid.receipt.id}" />
					<!-- Photo of the Receipt -->
					<div class="mb-3 text-center">
					</div>
					<!-- Bidder Information -->
					<div class="mb-3">
						<label for="bidderInfo" class="form-label">Bidder Information</label>
						<textarea class="form-control" id="bidderInfo" rows="4"	placeholder="Enter bidder information." readonly>
							Username:${bid.bidder.username}
							Email: ${bid.bidder.email}
							Phone: ${bid.bidder.phone}
            			</textarea>
					</div>
				
				<!-- Modal Footer -->
				<div class="modal-footer justify-content-center">
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#REJECTED_FORM">Reject</button>
					<button type="submit" class="btn btn-success">Accept</button>
				</div>
				</form>
		</div>
	</div>
</div>
</div>

<div class="modal fade" id="REJECTED_FORM" tabindex="-1"
	aria-labelledby="REJECTED_FORMLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title" id="REJECTED_FORMlLabel">
					<i class="fas fa-layer-group"></i> Reject
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
							placeholder="Enter description"></textarea>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary">Save</button>
			</div>
		</div>
	</div>
</div>

<script>
	function closeModal(modalId) {
		const modalElement = document.getElementById(modalId);
		const modalInstance = bootstrap.Modal.getInstance(modalElement);
		if (modalInstance) {
			modalInstance.hide();
		}
	}

	document.querySelector('#REJECTED_FORM .btn-primary').addEventListener(
			'click', function() {
				closeModal('REJECTED_FORM'); // 
			});

	document.querySelector('#REJECTED_FORM .btn-danger').addEventListener(
			'click',
			function() {
				closeModal('REJECTED_FORM'); // Cierra el modal REJECTED_FORM

				const viewReceiptModal = new bootstrap.Modal(document
						.getElementById('VIEW_RECEIPT'));
				viewReceiptModal.show();
			});

	document.querySelector('#VIEW_RECEIPT .btn-success').addEventListener(
			'click', function() {
				closeModal('VIEW_RECEIPT'); // Cierra el modal VIEW_RECEIPT
			});
</script>

</html>