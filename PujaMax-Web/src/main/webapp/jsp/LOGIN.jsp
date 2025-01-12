<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<div>
    <div class="container">
        <div class="row align-items-center" style="min-height: 100vh;">
            <div class="col-md-6 text-center div-image height-div-image">
                <img src="${pageContext.request.contextPath}/images/logo1.png" alt="Auction Image" class="logo-image">
                <p class="image-description">
                    Welcome to our auction platform. Explore and bid on your favorite items!
                </p>
            </div>

            <!-- Right Section: Login Form -->
            <div class="col-md-6">
                <div class="form-frame">
                    <h1 class="text-center login-header">Login</h1>
                    <form method="POST" action="${pageContext.request.contextPath}/LoginController?route=login">
                        <div class="mb-3">
                            <label for="txtDni" class="form-label">DNI</label>
                            <input type="text" class="form-control" id="txtDni" name="txtDni"
                                   placeholder="Enter your DNI">
                        </div>
                        <div class="mb-3">
                            <label for="txtPassword" class="form-label">Password</label>
                            <input type="password" class="form-control" id="txtPassword" name="txtPassword"
                                   placeholder="Enter your password">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Role</label>
                            <div class="d-flex">
                                <div class="form-check me-3">
                                    <input class="form-check-input" type="radio" name="role" id="roleAuctioneer"
                                           value="auctioneer" required>
                                    <label class="form-check-label" for="roleAuctioneer">
                                        Auctioneer
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="role" id="roleBidder"
                                           value="bidder" required>
                                    <label class="form-check-label" for="roleBidder">
                                        Bidder
                                    </label>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 mb-3">Login</button>
                    </form>
                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/RegisterController?route=enter"
                           class="text-decoration-none">Don't have an account? Register here</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer>
    <div class="container">
        <div class="row text-center text-md-start align-items-center">
            <!-- Left: Contact Text -->
            <div class="col-md-6">
                <p class="mb-0">Contact us to auction</p>
            </div>
            <!-- Right: Social Icons -->
            <div class="col-md-6 text-md-end social-icons">
                <a href="#" aria-label="Facebook"><i class="fa-brands fa-facebook"></i></a>
                <a href="#" aria-label="Instagram"><i class="fa-brands fa-instagram"></i></a>
                <a href="#" aria-label="WhatsApp"><i class="fa-brands fa-whatsapp"></i></a>
                <a href="#" aria-label="Email"><i class="fa-solid fa-envelope"></i></a>
            </div>
        </div>
    </div>
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
