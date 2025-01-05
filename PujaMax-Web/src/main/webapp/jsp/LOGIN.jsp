
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
            <!-- Left Section: Image -->
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
                            <input type="text" class="form-control" id="txtDni" name="txtDni" placeholder="Enter your DNI">
                        </div>
                        <div class="mb-3">
                            <label for="txtPassword" class="form-label">Password</label>
                            <input type="password" class="form-control" id="txtPassword" name="txtPassword"
                                   placeholder="Enter your password">
                        </div>
                        <button type="submit" class="btn btn-primary w-100 mb-3">Login</button>
                    </form>
                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/RegisterController?route=enter" class="text-decoration-none">Don't have an account? Register here</a>
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

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
