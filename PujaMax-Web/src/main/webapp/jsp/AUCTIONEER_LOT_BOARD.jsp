<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%
    Date currentDate = new Date();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AUCTIONEER_LOT_BOARD</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<!-- Header -->
<header class="header-container">
    <div class="container d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center">
            <img src="${pageContext.request.contextPath}/images/OnlyB.png" alt="Logo"
                 style="height: 40px; margin-right: 10px;">
            <h1 class="app-name mb-0">Online Auction</h1>
        </div>
        <div class="d-flex align-items-center">
            <div class="dropdown">
                <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown"
                   aria-expanded="false"><i class="fas fa-user"></i> User</a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
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

<!-- Main Content -->
<main class="container my-4">
    <section class="home-container">
        <nav class="nav-container">
            <a href="${pageContext.request.contextPath}/LotManagementController?route=list" class="nav-item"><i class="fas fa-home"></i> Home</a>
            <a href="${pageContext.request.contextPath}/LotManagementController?route=add" class="nav-item">
                <i class="fas fa-plus-circle"></i> Add Lot</a>
            <a href="AUCTIONEER_HISTORY.html" class="nav-item"><i class="fas fa-history"></i>
                History</a>
        </nav>
    </section>

    <!-- Product Cards -->
    <section class="lots-container">
        <div class="row">
            <!-- Bucle para mostrar cada lote -->
            <c:forEach var="lot" items="${lots}">
                <div class="col-md-6 col-lg-6 mb-4">
                    <!-- Clase condicional -->
                    <div class="card ${lot.state == 'INACTIVE' ? 'inactive-card' : ''}">
                        <!-- Cabecera de la tarjeta -->
                        <div class="card-header">
                            <c:choose>
                                <c:when test="${lot.state == 'ACTIVE'}">
                    <span class="badge bg-success p-2">
                        <i class="fas fa-gavel"></i> ACTIVE
                    </span>
                                </c:when>
                                <c:otherwise>
                    <span class="badge bg-secondary p-2">
                        <i class="fas fa-gavel"></i> INACTIVE
                    </span>
                                </c:otherwise>
                            </c:choose>
                            <!-- Enlace a detalles -->
                            <a href="${pageContext.request.contextPath}/ProductManagementController?route=list&idLot=${lot.idLot}" class="text-white" title="Go to Details">
    									<i class="fas fa-angle-right"></i>
									</a>
                        </div>

                        <!-- Cuerpo de la tarjeta -->
                        <div class="card-body d-flex justify-content-between align-items-start">
                            <div>
                                <h3 class="card-title">${lot.title}</h3>
                                <h5 class="card-subtitle">${lot.address.city}</h5>
                                <p class="card-text">
                                    SCHEDULED CLOSURE DATE:
                                    <c:out value="${lot.dateClosing}"/>
                                    <br>
                                </p>
                            </div>
                            <!-- Iconos de acción -->
                            <div class="action-icons d-flex flex-column align-items-center">
                                <!-- EDITAR -->
                                <a href="${pageContext.request.contextPath}/LotManagementController?route=edit&idLot=${lot.idLot}"
                                   class="nav-item text-primary"
                                   title="Edit">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <!-- ELIMINAR -->
                                <a href="${pageContext.request.contextPath}/LotManagementController?route=delete&idLot=${lot.idLot}"
                                   class="text-danger"
                                   title="Delete">
                                    <i class="fas fa-trash-alt"></i>
                                </a>
                            </div>
                        </div>

                        <!-- Cálculos de tiempo restante -->
                        <c:set var="currentDateMillis" value="<%= currentDate.getTime() %>"/>
                        <c:set var="dateClosingMillis" value="${lot.dateClosing.time}"/>
                        <c:set var="diffInMillis" value="${dateClosingMillis - currentDateMillis}"/>
                        <c:set var="hoursRemaining" value="${diffInMillis / (60 * 60 * 1000)}"/>
                        <c:set var="roundedHoursRemaining"
                               value="${(hoursRemaining < 0) ? 0 : Math.round(hoursRemaining)}"/>

                        <div class="stats row text-center">
                            <div class="stat col-6 border">
                                <h3>${lot.quantityProducts}</h3>
                                <p><i class="fas fa-box"></i> Products in Auction</p>
                            </div>
                            <div class="stat col-6 border">
                                <h3>${roundedHoursRemaining}</h3>
                                <p><i class="fas fa-clock"></i> Hours to Close</p>
                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>
    </section>
</main>
<!-- Footer -->
<footer class="text-center bg-dark text-white py-3 mt-4">
    <p>&copy; 2024 BIDMAX | All rights reserved</p>
</footer>
<!-- Add Lot Modal -->
<div class="modal fade" id="LOT_FORM" tabindex="-1" aria-labelledby="LOT_FORMLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="LOT_FORMLabel"><i class="fas fa-layer-group"></i> Lot</h5>
                <a href="LotManagementController?route=list" class="btn-close"
                   aria-label="Close"></a>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/LotManagementController?route=saveNew" method="POST">
                    <div class="mb-3">
                        <input type="hidden" name="txtId" id="txtId">
                        <label for="txtTitle" class="form-label">Title</label>
                        <input type="text" class="form-control" id="txtTitle" name="txtTitle" placeholder="Enter title">
                        <input type="hidden" name="txtQuantityProducts" id="txtQuantityProducts" value="0">
                    </div>
                    <div class="mb-3">
                        <label for="txtOpeningDate" class="form-label">Opening Date</label>
                        <input type="date" class="form-control" id="txtOpeningDate" name="txtOpeningDate">
                    </div>
                    <div class="mb-3">
                        <label for="txtClosingDate" class="form-label">Closing Date</label>
                        <input type="date" class="form-control" id="txtClosingDate" name="txtClosingDate">
                    </div>
                    <div class="mb-3">
                        <label for="delivery" class="form-label">Address</label>
                        <select id="delivery" class="form-select" name="txtIdAddress">
                            <option selected disabled>Choose Address</option>
                            <c:forEach var="addr" items="${addresses}">
                                <option value="${addr.idAddress}">${addr.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!-- Botones del modal -->
                    <div class="modal-footer justify-content-center">
                        <a href="LotManagementController?route=list"
                           class="btn btn-danger"> Cancel </a>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Edit Lot Modal -->
<div class="modal fade" id="EDIT_LOT_MODAL" tabindex="-1" aria-labelledby="EDIT_LOT_MODALLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="EDIT_LOT_MODALLabel"><i class="fas fa-edit"></i> Edit Lot</h5>
                <a href="LotManagementController?route=list" class="btn-close"
                   aria-label="Close"></a>
            </div>
            <form action="${pageContext.request.contextPath}/LotManagementController?route=saveExisting" method="POST">
                <div class="modal-body">
                    <!-- Hidden ID Field -->
                    <input type="hidden" name="txtId" id="editLotId" value="${lot.idLot}">

                    <!-- Title -->
                    <div class="mb-3">
                        <label for="editLotTitle" class="form-label">Title</label>
                        <input type="text" class="form-control" name="txtTitle" id="editLotTitle" value="${lot.title}"
                               required>
                        <input type="hidden" name="txtQuantityProducts" id="editLotQuantityProducts"
                               value="${lot.quantityProducts}">
                    </div>

                    <!-- Opening Date -->
                    <div class="mb-3">
                        <label for="editLotOpeningDate" class="form-label">Opening Date</label>
                        <input type="date" class="form-control" name="txtOpeningDate" id="editLotOpeningDate"
                               value="<fmt:formatDate value='${lot.dateOpening}' pattern='yyyy-MM-dd' />" required>
                    </div>

                    <!-- Closing Date -->
                    <div class="mb-3">
                        <label for="editLotClosingDate" class="form-label">Closing Date</label>
                        <input type="date" class="form-control" name="txtClosingDate" id="editLotClosingDate"
                               value="<fmt:formatDate value='${lot.dateClosing}' pattern='yyyy-MM-dd' />" required>
                    </div>

                    <!-- Address Dropdown -->
                    <div class="mb-3">
                        <label for="editLotAddress" class="form-label">Address</label>
                        <select class="form-select" name="txtIdAddress" id="editLotAddress" required>
                            <option disabled>Select Address</option>
                            <c:forEach var="addr" items="${addresses}">
                                <option value="${addr.idAddress}"
                                        <c:if test="${lot.address.idAddress == addr.idAddress}">selected</c:if>>${addr.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="LotManagementController?route=list" class="btn btn-danger"> Cancel </a>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>


<!-- Delete Lot Modal -->
<div class="modal fade" id="DELETE_LOT_MODAL" tabindex="-1" aria-labelledby="DELETE_LOT_MODALLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title" id="DELETE_LOT_MODALLabel"><i class="fas fa-trash-alt"></i> Confirm Deletion
                </h5>
                <a href="LotManagementController?route=list" class="btn-close"
                   aria-label="Close"></a>
            </div>
            <div class="modal-body text-center">
                <p>Are you sure you want to delete the following lot?</p>
                <!-- Información del lote -->
                <div class="modal-body text-center">
                    <h3>${lot.title}</h3>
                    <p class="card-text text-secondary small mb-4">
                        <i class="fas fa-map-marker-alt me-2"></i>${lot.address.province}, ${lot.address.city}
                    </p>
                    <p class="card-text text-secondary small mb-4">
                        <i class="fa-solid fa-hourglass-end"></i>  ${lot.dateClosing}
                    </p>
                    <p class="card-text text-secondary small mb-4">
                        <i class="fas fa-box"></i>  ${lot.quantityProducts}
                    </p>
                </div>
            </div>
            <div class="modal-footer justify-content-center">
                <!-- Cancel button, closes the modal -->
                <a href="LotManagementController?route=list"
                   class="btn btn-danger">Cancel</a>

                <!-- Form to confirm the deletion -->
                <form
                        action="${pageContext.request.contextPath}/LotManagementController?route=accept&idLot=${lot.idLot}"
                        method="POST">
                    <button type="submit" class="btn btn-success">Accept</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal para mensajes informativos y de error -->
<div class="modal modal-info" id="infoModal" tabindex="-1" aria-labelledby="infoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body ${messageType == 'info' ? 'info' : 'error'}">
                <i class="fas ${messageType == 'info' ? 'fa-info-circle text-info' : 'fa-exclamation-circle text-danger'}"></i>
                <span>${message}</span>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    window.onload = function () {
        var route = "${param.route}";
        if (route === "add") {
            var myModal = new bootstrap.Modal(document.getElementById('LOT_FORM'), {
                keyboard: false,
                backdrop: 'static'
            });
            myModal.show();
        } else if (route === "edit" && "${param.idLot}") {
            // Abre modal de Editar
            var editModal = new bootstrap.Modal(document.getElementById('EDIT_LOT_MODAL'));
            editModal.show();
        } else if (route === "delete" && "${param.idLot}") {
            // Abre modal de Delete
            var deleteModal = new bootstrap.Modal(document.getElementById('DELETE_LOT_MODAL'));
            deleteModal.show();
        }
        F
    };
</script>
</body>
</html>
