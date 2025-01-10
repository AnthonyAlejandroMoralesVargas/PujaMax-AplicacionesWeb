<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AUCTIONEER_PROFILE</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome for icons -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
	<!-- Header -->
	<header class="header-container">
		<div

			class="container d-flex justify-content-between align-items-center">
			<div class="d-flex align-items-center">
				<img src="${pageContext.request.contextPath}/images/OnlyB.png" alt="Logo"
					style="height: 40px; margin-right: 10px;">
				<h1 class="app-name mb-0">Online Auction</h1>
			</div>
			<div class="d-flex align-items-center">
				<a href="${pageContext.request.contextPath}/LotManagementController?route=list"
					class="text-white text-decoration-none me-3"><i
					class="fas fa-home"></i> Home</a>
				<div class="dropdown">
					<a href="#" class="dropdown-toggle" id="dropdownMenuButton"
						data-bs-toggle="dropdown" aria-expanded="false"> <i
						class="fas fa-user"></i> User
					</a>
					<ul class="dropdown-menu dropdown-menu-end"
						aria-labelledby="dropdownMenuButton">
						<li><a class="dropdown-item" href="AUCTIONEER_PROFILE.jsp"><i
								class="fas fa-cogs"></i> Profile</a></li>
						<li><a class="dropdown-item" href="../index.jsp"><i
								class="fas fa-sign-out-alt"></i> Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</header>

	<!-- Main Content -->
	<main class="container my-4">
		<h2 class="mb-4">User Profile</h2>
		<!-- Feedback Message -->
		<c:if test="${not empty sessionScope.message}">
			<div id="notification" class="alert alert-danger"
				style="background-color: #f8d7da; color: #721c24;" role="alert">
				${sessionScope.message}</div>
			<c:remove var="message" scope="session" />
		</c:if>




		<div class="row">
			<!-- User Information Card -->
			<div class="col-md-4">
				<div class="card">
					<div class="card-body">
						<h3 class="card-title">User Information</h3>
						<p>
							<strong>Name:</strong> ${user.name}
						</p>
						<p>
							<strong>Email:</strong> ${user.email}
						</p>
					</div>
				</div>
			</div>

			<!-- Addresses Section -->
			<div class="col-md-8">
				<div class="d-flex justify-content-between align-items-center mb-3">

					<h3 class="mb-0">
						Addresses <i class="fa-solid fa-map-location-dot"></i>
					</h3>

					<a href="AddressManagementController?route=add"
						class="btn btn-primary"> <i class="fas fa-plus-circle"></i>
						Add Address
					</a>

				</div>
				<div class="row">
					<c:forEach var="address" items="${addresses}">

						<div class="col-md-6 mb-3">
							<div class="card border-0 rounded-3">
								<div class="card-body p-4">
									<h2 class="card-title text-dark fw-bold">${address.name}</h2>
									<p class="card-text text-secondary small mb-4">
										<i class="fas fa-map-marker-alt me-2"></i>${address.province},
										${address.city}
									</p>
									<p class="card-text text-secondary small mb-4">
										<i class="fa-solid fa-house me-2"></i>${address.houseNumber}
									</p>
									<div class="d-flex justify-content-end">
										<!-- Botón de editar -->
										<a
											href="AddressManagementController?route=edit&idAddress=${address.idAddress}"
											class="btn btn-primary btn-sm me-2 px-3"> <i
											class="fas fa-pen"></i> Edit
										</a>
										<!-- Botón de eliminar -->
										<a
											href="AddressManagementController?route=delete&idAddress=${address.idAddress}"
											class="btn btn-danger btn-sm px-3"> <i
											class="fas fa-trash-alt"></i> Delete
										</a>
									</div>
								</div>
							</div>
						</div>

					</c:forEach>
				</div>

			</div>
		</div>
	</main>

	<!-- ADD ADDRESS MODAL -->
	<div class="modal fade" id="ADD_ADDRESS_MODAL" tabindex="-1"
		aria-labelledby="ADD_ADDRESS_MODALLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title" id="ADD_ADDRESS_MODALLabel">
						<i class="fa-solid fa-location-crosshairs"></i> Add Address
					</h5>
					<a href="AddressManagementController?route=list" class="btn-close"
						aria-label="Close"></a>
				</div>
				<form action="AddressManagementController?route=saveNew"
					method="POST">
					<div class="modal-body">
						<!-- Address Form Fields -->
						<div class="row g-4">
							<div class="col-md-6">
								<label for="txtName" class="form-label fw-semibold">Name</label>
								<input type="text" class="form-control rounded-3" name="txtName"
									id="txtName" placeholder="Enter name" required>
							</div>
							<div class="col-md-6">
								<label for="txtCompany" class="form-label fw-semibold">Company
									(Optional)</label> <input type="text" class="form-control rounded-3"
									name="txtCompany" id="txtCompany" placeholder="Enter company">
							</div>
						</div>
						<div class="row g-4 mt-2">
							<div class="col-md-6">
								<label for="txtProvince" class="form-label fw-semibold">Province</label>
								<input type="text" class="form-control rounded-3"
									name="txtProvince" id="txtProvince"
									placeholder="Enter province" required>
							</div>
							<div class="col-md-6">
								<label for="txtCity" class="form-label fw-semibold">City</label>
								<input type="text" class="form-control rounded-3" name="txtCity"
									id="txtCity" placeholder="Enter city" required>
							</div>
						</div>
						<div class="row g-4 mt-2">
							<div class="col-md-6">
								<label for="txtMainStreet" class="form-label fw-semibold">Main
									Street</label> <input type="text" class="form-control rounded-3"
									name="txtMainStreet" id="txtMainStreet"
									placeholder="Enter main street" required>
							</div>
							<div class="col-md-6">
								<label for="txtSecondaryStreet" class="form-label fw-semibold">Secondary
									Street (Optional)</label> <input type="text"
									class="form-control rounded-3" name="txtSecondaryStreet"
									id="txtSecondaryStreet" placeholder="Enter secondary street">
							</div>
						</div>
						<div class="row g-4 mt-2">
							<div class="col-md-6">
								<label for="txtPostcode" class="form-label fw-semibold">Postcode</label>
								<input type="text" class="form-control rounded-3"
									name="txtPostcode" id="txtPostcode"
									placeholder="Enter postcode" required>
							</div>
							<div class="col-md-6">
								<label for="txtHouseNumber" class="form-label fw-semibold">House/Apartment
									No.</label> <input type="text" class="form-control rounded-3"
									name="txtHouseNumber" id="txtHouseNumber"
									placeholder="Enter house/apartment no." required>
							</div>
						</div>
					</div>

					<div class="modal-footer justify-content-center">
						<a href="AddressManagementController?route=list"
							class="btn btn-danger"> Cancel </a>
						<button type="submit" class="btn btn-primary">Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Modal for Edit Address -->
	<div class="modal fade" id="EDIT_ADDRESS_MODAL" tabindex="-1"
		aria-labelledby="EDIT_ADDRESS_MODALLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title" id="EDIT_ADDRESS_MODALLabel">
						<i class="fas fa-edit"></i> Edit Address
					</h5>
					<a href="AddressManagementController?route=list" class="btn-close"
						aria-label="Close"></a>
				</div>
				<form action="AddressManagementController?route=saveExisting"
					method="POST">
					<div class="modal-body">
						<!-- Form fields with pre-filled values from the retrieved address -->
						<div class="row">
							<input type="hidden" name="txtId" value="${address.idAddress}">
							<div class="col-md-6 mb-3">
								<label for="txtName" class="form-label fw-bold">Name</label> <input
									type="text" class="form-control" name="txtName" id="txtName"
									value="${address.name}" placeholder="Enter name">
							</div>
							<div class="col-md-6 mb-3">
								<label for="txtCompany" class="form-label fw-bold">Company
									(Optional)</label> <input type="text" class="form-control"
									name="txtCompany" id="txtCompany" value="${address.company}"
									placeholder="Enter company">
							</div>
						</div>

						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="txtProvince" class="form-label fw-bold">Province</label>
								<input type="text" class="form-control" name="txtProvince"
									id="txtProvince" value="${address.province}"
									placeholder="Enter province">
							</div>
							<div class="col-md-6 mb-3">
								<label for="txtCity" class="form-label fw-bold">City</label> <input
									type="text" class="form-control" name="txtCity" id="txtCity"
									value="${address.city}" placeholder="Enter city">
							</div>
						</div>

						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="txtMainStreet" class="form-label fw-bold">Main
									Street</label> <input type="text" class="form-control"
									name="txtMainStreet" id="txtMainStreet"
									value="${address.mainStreet}" placeholder="Enter main street">
							</div>
							<div class="col-md-6 mb-3">
								<label for="txtSecondaryStreet" class="form-label fw-bold">Secondary
									Street </label> <input type="text" class="form-control"
									name="txtSecondaryStreet" id="txtSecondaryStreet"
									value="${address.secondaryStreet}"
									placeholder="Enter secondary street">
							</div>
						</div>

						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="txtPostcode" class="form-label fw-bold">Postcode</label>
								<input type="text" class="form-control" name="txtPostcode"
									id="txtPostcode" value="${address.postcode}"
									placeholder="Enter postcode">
							</div>
							<div class="col-md-6 mb-3">
								<label for="txtHouseNumber" class="form-label fw-bold">House/Apartment
									No.</label> <input type="text" class="form-control"
									name="txtHouseNumber" id="txtHouseNumber"
									value="${address.houseNumber}"
									placeholder="Enter house/apartment no.">
							</div>
						</div>

					</div>
					<div class="modal-footer justify-content-center">
						<a href="AddressManagementController?route=list"
							class="btn btn-danger"> Cancel </a>
						<button type="submit" class="btn btn-primary">Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Delete Address Modal -->
	<div class="modal fade" id="DELETE_ADDRESS_MODAL" tabindex="-1"
		aria-labelledby="DELETE_ADDRESSLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #dc3545; color: #fff;">
					<h5 class="modal-title" id="DELETE_ADDRESSLabel">
						<i class="fa-solid fa-trash me-2"></i> Delete Address
					</h5>
					<a href="AddressManagementController?route=list" class="btn-close"
						aria-label="Close"></a>
				</div>
				<div class="modal-body text-center">
					<p>Are you sure you want to delete the address?</p>
					<h3>${address.name}</h3>
					<p class="card-text text-secondary small mb-4">
						<i class="fas fa-map-marker-alt me-2"></i>${address.province},
						${address.city}
					</p>
					<p class="card-text text-secondary small mb-4">
						<i class="fa-solid fa-house me-2"></i>${address.houseNumber}
					</p>
					<p class="card-text text-secondary small mb-4">
						<i class="fa-solid fa-road me-2"></i>${address.mainStreet} &
						${address.secondaryStreet}
					</p>
					<p class="card-text text-secondary small mb-4">
						<i class="fa-solid fa-envelope me-2"></i></i>${address.postcode}
					</p>
				</div>
				<div class="modal-footer justify-content-center">
					<!-- Cancel button, closes the modal -->
					<a href="AddressManagementController?route=list"
						class="btn btn-danger">Cancel</a>

					<!-- Form to confirm the deletion -->
					<form
						action="AddressManagementController?route=accept&idAddress=${address.idAddress}"
						method="POST">
						<button type="submit" class="btn btn-success">Accept</button>
					</form>

				</div>
			</div>
		</div>
	</div>


	<!-- Footer -->
	<footer class="text-center bg-dark text-white py-3">
		<p>&copy; 2024 BIDMAX | All rights reserved</p>
	</footer>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
    window.onload = function() {
        var route = "${param.route}";

        if (route === "add") {
            var myModal = new bootstrap.Modal(document
                .getElementById('ADD_ADDRESS_MODAL'), {
                keyboard : false,
                backdrop : 'static'
            });
            document.body.classList.remove('modal-open');
            myModal.show();
        } else if (route === "edit" && "${param.idAddress}") {
            var myModal = new bootstrap.Modal(document
                .getElementById('EDIT_ADDRESS_MODAL'), {
                keyboard : false,
                backdrop : 'static'
            });
            document.body.classList.remove('modal-open');
            myModal.show();
        } else if (route === "delete" && "${param.idAddress}") {
            var myModal = new bootstrap.Modal(document
                .getElementById('DELETE_ADDRESS_MODAL'), {
                keyboard : false,
                backdrop : 'static'
            });
            document.body.classList.remove('modal-open');
            myModal.show();
        }
    };

    document.addEventListener("DOMContentLoaded", function () {
        const notification = document.getElementById("notification");
        if (notification) {
            // Oculta el mensaje después de 2 segundos
            setTimeout(() => {
                notification.style.transition = "opacity 0.5s";
                notification.style.opacity = "0";
                setTimeout(() => notification.remove(), 1000); // Remueve el elemento después de la transición
            }, 2000);
        }
    });
</script>
</body>
</html>
