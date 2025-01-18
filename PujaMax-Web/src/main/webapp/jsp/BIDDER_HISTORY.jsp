<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BIDDER_HISTORY</title>
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
                    <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown"
                        aria-expanded="false"><i class="fas fa-user"></i> User</a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AddressManagementController?route=list"><i class="fas fa-cogs"></i> Profile</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginController?route=logOut"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
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
                <a href="LotManagementController?route=listBidder&idLot=${idLot}" class="nav-item"><i class="fas fa-home"></i> Home</a>
                <a href="${pageContext.request.contextPath}/PlaceBidController?route=history" class="nav-item"><i class="fas fa-history"></i>
                History</a>
            </nav>
        </section>

        <!-- Bid History Section -->
        <div>
            <c:if test="${not empty bids}">
                <c:forEach var="bid" items="${bids}">
                    <div class="product-card">
                        <div class="row">
                            <!-- Product Photo -->
                            <div class="col-md-4">
                                <img src="PlaceBidController?route=list&idProduct=${bid.product.idProduct}" alt="Product Image" class="product-img mb-3">
                                <a href="PlaceBidController?route=productDetails&idProduct=${bid.product.idProduct}" class="btn btn-view w-100">VIEW THIS LOT</a>
                            </div>

                            <!-- Product Information -->
                            <div class="col-md-4">
                                <div>
                                    <strong>Title:</strong> ${bid.product.title}<br>
                                    <strong>Price:</strong> $${bid.currentPrice}<br>
                                    <strong>Description:</strong> ${bid.product.description}
                                </div>
                            </div>

                            <!-- Status and Options -->
                            <div class="col-md-4">
                                <div class="status-box ${bid.state == 'WON' ? 'status-win' : 'status-lose'}">
                                    ${bid.state}
                                </div>
                                <button class="btn-option" data-bs-toggle="modal"
                                    data-bs-target="#${bid.state == 'WON' ? 'SUBMIT_RECEIPT_PAYMENT' : 'DESCRIPTION_REJECTION'}">Options</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty bids}">
                <p class="text-center mt-4">You haven't placed any bids yet.</p>
            </c:if>
        </div>
    </main>

    <!-- Footer -->
    <footer class="text-center bg-dark text-white py-3 mt-4">
        <p>&copy; 2024 PujaMax | All rights reserved</p>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<div class="modal fade" id="SUBMIT_RECEIPT_PAYMENT" tabindex="-1" aria-labelledby="SUBMIT_RECEIPT_PAYMENTLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="SUBMIT_RECEIPT_PAYMENTLabel"><i class="fas fa-layer-group"></i> Upload
                    Receipt and Bidder Information</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="bidderInfo" class="form-label">Bidder Information</label>
                    <textarea class="form-control" id="bidderInfo" rows="4" placeholder="Enter bidder information."
                        readonly>
Username: 
Email: 
Phone: 
                    </textarea>
                </div>
                <div class="mb-3">
                    <label for="receiptFile" class="form-label">Upload Receipt</label>
                    <input class="form-control" type="file" id="receiptFile">
                </div>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">Submit</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="DESCRIPTION_REJECTION" tabindex="-1" aria-labelledby="DESCRIPTION_REJECTIONLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title" id="DESCRIPTION_REJECTIONLabel"><i class="fas fa-layer-group"></i> Description
                    Rejection</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label for="rejectedDescription" class="form-label">Description of the rejected decision</label>
                        <textarea id="rejectedDescription" class="form-control" rows="3" placeholder="Description"
                            readonly></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-success" data-bs-dismiss="modal">Accept</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="DESCRIPTION_ADDRESS" tabindex="-1" aria-labelledby="DESCRIPTION_ADDRESSLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="DESCRIPTION_ADDRESSLabel"><i class="fas fa-layer-group"></i> Delivery
                    Address</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Modal Body -->
            <div class="modal-body">
                <form>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="name" value="Name" readonly>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="company" class="form-label">Company</label>
                            <input type="text" class="form-control" id="company" value="Company" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="province" class="form-label">Province</label>
                            <input type="text" class="form-control" id="province" value="Province" readonly>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="city" class="form-label">City</label>
                            <input type="text" class="form-control" id="province" value="City" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="mainStreet" class="form-label">Main Street</label>
                            <input type="text" class="form-control" id="mainStreet" value="Main Streett" readonly>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="secondaryStreet" class="form-label">Secondary Street</label>
                            <input type="text" class="form-control" id="secondaryStreet" value="Secondary Street"
                                readonly>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="zipCode" class="form-label">Zip Code</label>
                            <input type="text" class="form-control" id="zipCode" value="Zip Code" readonly>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="houseNumber" class="form-label">House/Apartment No.</label>
                            <input type="text" class="form-control" id="houseNumber" value="House/Apartment No.1"
                                readonly>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Modal Footer -->
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-success" data-bs-dismiss="modal">Accept</button>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('openSubmitModal').addEventListener('click', () => {
            const rejectModal = bootstrap.Modal.getInstance(document.getElementById('DESCRIPTION_REJECTION'));
            rejectModal.hide();

            const submitModal = new bootstrap.Modal(document.getElementById('SUBMIT_RECEIPT_PAYMENT'));
            submitModal.show();

        });

        document.querySelectorAll('.modal .btn').forEach(button => {
            button.addEventListener('click', event => {
                if (button.dataset.bsDismiss === 'modal') {
                    const modalElement = event.target.closest('.modal');
                    const modalInstance = bootstrap.Modal.getInstance(modalElement);
                    if (modalInstance) {
                        modalInstance.hide();
                    }
                }
            });
        });
    </script>
</html>