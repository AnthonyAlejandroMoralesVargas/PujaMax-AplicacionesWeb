<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AUCTIONEER_PROFILE</title>
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
            <a href="AUCTIONEER_LOT_BOARD.jsp" class="text-white text-decoration-none me-3"><i class="fas fa-home"></i> Home</a>
            <div class="dropdown">
                <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fas fa-user"></i> User
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                    <li><a class="dropdown-item" href="AUCTIONEER_PROFILE.jsp"><i class="fas fa-cogs"></i> Profile</a></li>
                    <li><a class="dropdown-item" href="../index.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container my-4">
    <h2 class="mb-4">User Profile</h2>
    <!-- Feedback Message -->
    <c:if test="${not empty message}">
        <div class="alert alert-info">${message}</div>
    </c:if>

    <div class="row">
        <!-- User Information Card -->
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">User Information</h3>
                    <p><strong>Name:</strong> ${user.name}</p>
                    <p><strong>Email:</strong> ${user.email}</p>
                    <p><strong>Role:</strong> ${user.role}</p>
                </div>
            </div>
        </div>

        <!-- Addresses Section -->
        <div class="col-md-8">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="mb-0">Addresses (Address Book)</h3>

                <a href="AddressManagmentController?route=add" class="btn btn-primary">
                    <i class="fas fa-plus-circle"></i> Add Address
                </a>

            </div>
            <div class="row">
                <c:forEach var="address" items="${addresses}">
                    <div class="col-md-6 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h1>${address.name}</h1>
                                <p>${address.province}, ${address.city}</p>
                                <div class="d-flex justify-content-end">
                                    <!-- Botón de editar -->
                                    <a href="AddressManagmentController?route=edit&idAddress=${address.idAddress}" class="btn btn-sm btn-primary me-2">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
                                    <!-- Botón de eliminar -->
                                    <a href="AddressManagmentController?route=delete&idAddress=${address.idAddress}" class="btn btn-sm btn-danger">
                                        <i class="fas fa-trash-alt"></i> Delete
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

<!-- Add Address Modal -->
<div class="modal fade" id="ADD_ADDRESS_MODAL" tabindex="-1" aria-labelledby="ADD_ADDRESS_MODALLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="ADD_ADDRESS_MODALLabel"><i class="fas fa-layer-group"></i> Add Address</h5>
                <a href="AddressManagmentController?route=list" class="btn-close" aria-label="Close"></a>
            </div>
            <form action="AddressManagmentController?route=saveNew" method="POST">
                <div class="modal-body">
                    <!-- Address Form Fields -->
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="addTxtName" class="form-label">Name</label>
                            <input type="text" class="form-control" name="txtName" id="txtName" placeholder="Enter name">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="addTxtCompany" class="form-label">Company (Optional)</label>
                            <input type="text" class="form-control" name="txtCompany" id="txtCompany" placeholder="Enter company">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="addTxtProvince" class="form-label">Province</label>
                            <input type="text" class="form-control" name="txtProvince" id="txtProvince" placeholder="Enter province">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="addTxtCity" class="form-label">City</label>
                            <input type="text" class="form-control" name="txtCity" id="txtCity" placeholder="Enter city">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="addTxtMainStreet" class="form-label">Main Street</label>
                            <input type="text" class="form-control" name="txtMainStreet" id="txtMainStreet" placeholder="Enter main street">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="addTxtSecondaryStreet" class="form-label">Secondary Street (Optional)</label>
                            <input type="text" class="form-control" name="txtSecondaryStreet" id="txtSecondaryStreet" placeholder="Enter secondary street">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="addTxtPostcode" class="form-label">Postcode</label>
                            <input type="text" class="form-control" name="txtPostcode" id="txtPostcode" placeholder="Enter postcode">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="addTxtHouseNumber" class="form-label">House/Apartment No.</label>
                            <input type="text" class="form-control" name="txtHouseNumber" id="txtHouseNumber" placeholder="Enter house/apartment no.">
                        </div>
                    </div>
                </div>
                <div class="modal-footer justify-content-center">
                    <a href="AddressManagmentController?route=list"  class="btn btn-danger"> Cancel </a>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Modal for Edit Address -->
<div class="modal fade" id="EDIT_ADDRESS_MODAL" tabindex="-1" aria-labelledby="EDIT_ADDRESS_MODALLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="EDIT_ADDRESS_MODALLabel"><i class="fas fa-edit"></i> Edit Address</h5>
                <a href="AddressManagmentController?route=list" class="btn-close" aria-label="Close"></a>
            </div>
            <form action="AddressManagmentController?route=saveExisting" method="POST">
                <div class="modal-body">
                    <!-- Hidden field to store idAddress -->
                    <input type="hidden" name="txtId" id="txtId" value="${address.idAddress}">

                    <!-- Form fields with pre-filled values from the retrieved address -->
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="editTxtName" class="form-label">Name</label>
                            <input type="text" class="form-control" name="txtName" id="txtName" value="${address.name}" placeholder="Enter name">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="editTxtCompany" class="form-label">Company (Optional)</label>
                            <input type="text" class="form-control" name="txtCompany" id="txtCompany" value="${address.company}" placeholder="Enter company">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="editTxtProvince" class="form-label">Province</label>
                            <input type="text" class="form-control" name="txtProvince" id="txtProvince" value="${address.province}" placeholder="Enter province">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="editTxtCity" class="form-label">City</label>
                            <input type="text" class="form-control" name="txtCity" id="txtCity" value="${address.city}" placeholder="Enter city">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="editTxtMainStreet" class="form-label">Main Street</label>
                            <input type="text" class="form-control" name="txtMainStreet" id="txtMainStreet" value="${address.mainStreet}" placeholder="Enter main street">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="editTxtSecondaryStreet" class="form-label">Secondary Street (Optional)</label>
                            <input type="text" class="form-control" name="txtSecondaryStreet" id="txtSecondaryStreet" value="${address.secondaryStreet}" placeholder="Enter secondary street">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="editTxtPostcode" class="form-label">Postcode</label>
                            <input type="text" class="form-control" name="txtPostcode" id="txtPostcode" value="${address.postcode}" placeholder="Enter postcode">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="editTxtHouseNumber" class="form-label">House/Apartment No.</label>
                            <input type="text" class="form-control" name="txtHouseNumber" id="txtHouseNumber" value="${address.houseNumber}" placeholder="Enter house/apartment no.">
                        </div>
                    </div>
                </div>
                <div class="modal-footer justify-content-center">
                    <a href="AddressManagmentController?route=list"  class="btn btn-danger"> Cancel </a>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Delete Address Modal -->
<div class="modal fade" id="DELETE_ADDRESS_MODAL" tabindex="-1" aria-labelledby="DELETE_ADDRESSLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #dc3545; color: #fff;">
                <h5 class="modal-title" id="DELETE_ADDRESSLabel">Delete Address</h5>
                <a href="AddressManagmentController?route=list" class="btn-close" aria-label="Close"></a>
            </div>
            <div class="modal-body text-center">
                <p>Are you sure you want to delete the address?</p>
                <p>${address.name} </p>
            </div>
            <div class="modal-footer justify-content-center">
                <!-- Cancel button, closes the modal -->
                <a href="AddressManagmentController?route=list" class="btn btn-danger">Cancel</a>

                <form action="AddressManagmentController?route=accept&idAddress=${address.idAddress}" method="POST">
                    <button type="submit" class="btn btn-success">Accept</button>
                </form>

            </div>
        </div>
    </div>
</div>


<!-- Footer -->
<footer class="text-center bg-dark text-white py-3">
    <p>&copy; 2024 PujaMax | All rights reserved</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    window.onload = function() {
        var route = "${param.route}";

        if (route === "add") {
            var myModal = new bootstrap.Modal(document.getElementById('ADD_ADDRESS_MODAL'), {
                keyboard: false
            });
            myModal.show();
        } else if (route === "edit" && "${param.idAddress}") {
            var myModal = new bootstrap.Modal(document.getElementById('EDIT_ADDRESS_MODAL'), {
                keyboard: false
            });
            myModal.show();
        } else if (route === "delete" && "${param.idAddress}") {
            var myModal = new bootstrap.Modal(document.getElementById('DELETE_ADDRESS_MODAL'), {
                keyboard: false
            });
            myModal.show();
        }
    };
</script>
</body>
</html>
