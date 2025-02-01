<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Bidder History</title>
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
				<a href="LotManagementController?route=list&idLot=${idLot}"
					class="nav-item"><i class="fas fa-home"></i> Home</a> <a
					href="${pageContext.request.contextPath}/PayBidController?route=viewHistory"
					class="nav-item"><i class="fas fa-history"></i> History</a>
			</nav>
		</section>
		<c:forEach var="bid" items="${bids}">
			<c:set var="address" value="${bid.product.lot.address}" />
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
							bid.product.priceCurrent : '0.00'}<br> <strong>Date:</strong>
							${bid.dateBid != null ? bid.dateBid : 'N/A'}<br> <strong>Description:</strong>
							${bid.product != null && bid.product.description != null ? bid.product.description : 'No Description'}<br>
							<strong>Category:</strong> ${bid.product.category != null ? bid.product.category : 'N/A'}<br>
						</div>
					</div>

					<div
						class="col-md-4 d-flex flex-column justify-content-center align-items-center">
						<c:choose>
							<c:when test="${bid.state == 'TOP'}">
								<div class="btn btn-success w-50 mb-2">You're the top
									bidder</div>
							</c:when>
							<c:when test="${bid.state == 'SURPASSED'}">
								<div class="btn btn-warning w-50 mb-2">Your bid has been
									surpassed</div>
							</c:when>
							<c:when test="${bid.state == 'WON'}">
								<div class="btn btn-success w-50 mb-2">You won the bid!</div>
								<button class="btn btn-primary w-50" data-bs-toggle="modal"
									data-bs-target="#SUBMIT_RECEIPT_PAYMENT"
									data-bid-id="${bid.idBid}">Submit Receipt</button>
							</c:when>
							<c:when test="${bid.state == 'LOST'}">
								<div class="btn btn-danger w-50 mb-2">You lost the bid</div>
							</c:when>
							<c:when test="${bid.state == 'PENDING_APPROVAL'}">
								<div class="btn btn-warning w-50 mb-2">Receipt Pending
									Approval</div>
							</c:when>
							<c:when test="${bid.state == 'ACCEPT'}">
								<div class="btn btn-success w-50 mb-2">Receipt Accepted</div>
								<button class="btn btn-primary w-50" data-bs-toggle="modal"
									data-bs-target="#DESCRIPTION_ADDRESS"
									data-bid-id="${bid.idBid}">View Delivery Address</button>
							</c:when>
							<c:when test="${bid.state == 'REJECT'}">
								<div class="btn btn-danger w-50 mb-2">Receipt Rejected</div>
								<button class="btn btn-primary w-50" data-bs-toggle="modal"
									data-bs-target="#DESCRIPTION_REJECTION"
									data-bid-id="${bid.idBid}">View Rejection Reason</button>
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

		<c:if test="${empty bids}">
			<div class="alert alert-warning text-center">No bids found for
				this bidder.</div>
		</c:if>
	</main>
	<footer class="text-center bg-dark text-white py-3 mt-4">
		<p>&copy; 2025 BIDMAX | All rights reserved</p>
	</footer>
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
					<input type="hidden" name="idBid" id="idBid"> <input
						type="hidden" name="bidderDni" value="${bidderDni}">
					<div class="mb-3">
						<label class="form-label fw-bold">Bidder Information</label>
						<div class="p-3 border rounded bg-light">
							<p><strong>Username:</strong> ${sessionScope.user.name}</p>
							<p><strong>Email:</strong> ${sessionScope.user.email}</p>
							<p><strong>Phone:</strong> ${sessionScope.user.phoneNumber}</p>
						</div>
					</div>
					<div class="mb-3">
						<label for="receiptFile" class="form-label">Upload Receipt</label>
						<input class="form-control" type="file" name="images" id="receiptFile" accept="image/png, image/jpeg, image/jpg" multiple required>
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
					<i class="fas fa-layer-group"></i> Rejection Reason
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<textarea class="form-control" rows="3" readonly>${bid.rejectionReason}</textarea>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="DESCRIPTION_ADDRESS" tabindex="-1"
	aria-labelledby="DESCRIPTION_ADDRESSLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header bg-primary text-white">
				<h5 class="modal-title" id="DESCRIPTION_ADDRESSLabel">
					<i class="fas fa-layer-group"></i> Delivery Address
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<!-- Mostrar los datos de la dirección -->
				<div class="row">
					<div class="col-md-6 mb-3">
						<label for="name" class="form-label">Name</label>
						<input type="text" class="form-control" id="name"
							value="${address.name}" readonly>
					</div>
					<div class="col-md-6 mb-3">
						<label for="company" class="form-label">Company</label>
						<input type="text" class="form-control" id="company"
							value="${address.company}" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 mb-3">
						<label for="province" class="form-label">Province</label>
						<input type="text" class="form-control" id="province"
							value="${address.province}" readonly>
					</div>
					<div class="col-md-6 mb-3">
						<label for="city" class="form-label">City</label>
						<input type="text" class="form-control" id="city"
							value="${address.city}" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 mb-3">
						<label for="mainStreet" class="form-label">Main Street</label>
						<input type="text" class="form-control" id="mainStreet"
							value="${address.mainStreet}" readonly>
					</div>
					<div class="col-md-6 mb-3">
						<label for="secondaryStreet" class="form-label">Secondary Street</label>
						<input type="text" class="form-control" id="secondaryStreet"
							value="${address.secondaryStreet}" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 mb-3">
						<label for="postcode" class="form-label">Postcode</label>
						<input type="text" class="form-control" id="postcode"
							value="${address.postcode}" readonly>
					</div>
					<div class="col-md-6 mb-3">
						<label for="houseNumber" class="form-label">House/Apartment No.</label>
						<input type="text" class="form-control" id="houseNumber"
							value="${address.houseNumber}" readonly>
					</div>
				</div>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-success" data-bs-dismiss="modal">Close</button>
			</div>
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
	        
	        document.addEventListener("DOMContentLoaded", function () {
	            // Handle dynamic data for modals
	            const modals = document.querySelectorAll("[data-bs-toggle='modal']");
	            modals.forEach(modal => {
	                modal.addEventListener("click", function (event) {
	                    const bidId = this.getAttribute("data-bid-id");
	                    console.log(`Modal opened for Bid ID: ${bidId}`);
	                    // Fetch and populate data dynamically if needed
	                });
	            });
	        });
	    });
	</script>
</html>
