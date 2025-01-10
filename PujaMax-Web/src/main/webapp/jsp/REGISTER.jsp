
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome for icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="../css/style.css">
</head>

<body>
<div class="container">
  <div class="row align-items-center" style="min-height: 100vh;">
    <!-- Left Section: Image -->
    <div class="col-md-6 text-center div-image height-div-image">
      <img src="../images/logo1.png" alt="Auction Image" class="logo-image">
      <p class="image-description">
        Join our platform and start bidding or managing auctions today!
      </p>
    </div>

    <!-- Right Section: Registration Form -->
    <div class="col-md-6">
      <div class="form-frame">
        <h1 class="text-center login-header">Register</h1>
        <form action="${pageContext.request.contextPath}/RegisterController?route=save" method="POST">
          <div class="mb-3">
            <input type="hidden" name="txtId" id="txtId">
            <label for="txtDni" class="form-label">DNI</label>
            <input type="text" class="form-control" id="txtDni" name="txtDni" placeholder="Enter your DNI" required>
          </div>
          <div class="mb-3">
            <label for="txtName" class="form-label">Name</label>
            <input type="text" class="form-control" id="txtName" name="txtName" placeholder="Enter your name" required>
          </div>
          <div class="mb-3">
            <label for="txtLastName" class="form-label">Last Name</label>
            <input type="text" class="form-control" id="txtLastName" name="txtLastName" placeholder="Enter your last name" required>
          </div>
          <div class="mb-3">
            <label for="txtEmail" class="form-label">Email</label>
            <input type="email" class="form-control" id="txtEmail" name="txtEmail" placeholder="Enter your email" required>
          </div>
          <div class="mb-3">
            <label for="txtPassword" class="form-label">Password</label>
            <input type="password" class="form-control" id="txtPassword" name="txtPassword" placeholder="Enter your password" required>
          </div>
          <div class="mb-3">
            <label for="txtPhoneNumber" class="form-label">Phone Number</label>
            <input type="tel" class="form-control" id="txtPhoneNumber" name="txtPhoneNumber" placeholder="Enter your phone number" required>
          </div>
          <div class="mb-3">
            <label for="txtRole" class="form-label">Role</label>
            <select class="form-select" id="txtRole" name="role" required>
              <option value="auctioneer">Auctioneer</option>
              <option value="bidder">Bidder</option>
            </select>
          </div>
          <button type="submit" class="btn btn-primary w-100 mb-3">Register</button>
        </form>
        <div class="text-center">
          <a href="${pageContext.request.contextPath}/LoginController?route=enter" class="text-decoration-none">Already have an account? Login here</a>
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
