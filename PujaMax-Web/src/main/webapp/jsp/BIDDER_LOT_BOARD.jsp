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
    <title>BIDDER_LOTS_BOARD</title>
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
            <a href="LotManagementController?route=listBidder&idLot=${idLot}" class="nav-item"><i class="fas fa-home"></i> Home</a>
            <a href="${pageContext.request.contextPath}/PayBidController?route=viewHistory" class="nav-item"><i class="fas fa-history"></i>
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
                            <a href="${pageContext.request.contextPath}/PlaceBidController?route=list&idLot=${lot.idLot}" class="text-white" title="Go to Details">
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
        } 
        F
    };
</script>
</body>
</html>