<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
      <img src="${pageContext.request.contextPath}/images/logo1.png" alt="Logo" style="height: 50px; margin-right: 10px;">
      <h1 class="app-name mb-0">PUJAMAX Online Auction</h1>
    </div>
    <div class="d-flex align-items-center">
      <div class="dropdown">
        <a href="#" class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown"
           aria-expanded="false"><i class="fas fa-user"></i> User</a>
        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AddressManagmentController?route=list"><i class="fas fa-cogs"></i>
            Profile</a></li>
          <li><a class="dropdown-item" href="../index.html"><i class="fas fa-sign-out-alt"></i> Logout</a>
          </li>
          </li>
        </ul>
      </div>
    </div>
  </div>
</header>

<!-- Main Content -->
<main class="container my-4">
  <section class="home-container">
    <nav class="nav-container">
      <a href="AUCTIONEER_LOT_BOARD.html" class="nav-item"><i class="fas fa-home"></i> Home</a>
      <a href="#" class="nav-item" data-bs-toggle="modal" data-bs-target="#LOT_FORM"><i
              class="fas fa-plus-circle"></i> Add Lot</a>
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
          <div class="card">
            <div class="card-header">
              <!-- Estado del lote: puedes ajustar color según 'ACTIVE' / 'INACTIVE' -->
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
              <a href="AUCTIONEER_LOT.html" class="text-white" title="Go to Details">
                <i class="fas fa-angle-right"></i>
              </a>
            </div>
            <div class="card-body d-flex justify-content-between align-items-start">
              <div>
                <!-- Título del lote y ciudad -->
                <h5 class="card-title">${lot.title}</h5>
                <h5 class="card-title">${lot.city}</h5>
                <!-- Fecha de cierre (ejemplo) -->
                <p class="card-text">
                  SCHEDULED CLOSURE DATE:
                  <c:out value="${lot.dateClosing}" />
                  <br>
                  GMT-0500 (COLOMBIA STANDARD TIME)
                </p>
              </div>
              <!-- Action Icons -->
              <div class="action-icons d-flex flex-column align-items-center">
                <!-- EDIT -->
                <a href="#"
                   class="nav-item text-primary"
                   data-bs-toggle="modal"
                   data-bs-target="#LOT_FORM"
                   title="Edit">
                  <i class="fas fa-edit"></i>
                </a>
                <!-- DELETE -->
                <a href="#"
                   class="text-danger"
                   title="Delete"
                   data-bs-toggle="modal"
                   data-bs-target="#DELETE_LOT">
                  <i class="fas fa-trash-alt"></i>
                </a>
              </div>
            </div>
            <div class="stats row text-center">
              <div class="stat col-6 border">
                <h3>${lot.quantityProducts}</h3>
                <p><i class="fas fa-box"></i> Products in Auction</p>
              </div>
              <div class="stat col-6 border">
                <!-- Simple placeholder para "hours to close" -->
                <h3>??</h3>
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
  <p>&copy; 2024 PujaMax | All rights reserved</p>
</footer>
<!-- Add Lot Modal -->
<div class="modal fade" id="LOT_FORM" tabindex="-1" aria-labelledby="LOT_FORMLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title" id="LOT_FORMLabel"><i class="fas fa-layer-group"></i> Lot</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form>
          <div class="mb-3">
            <label for="lotTitle" class="form-label">Title</label>
            <input type="text" class="form-control" id="lotTitle" placeholder="Enter title">
          </div>
          <div class="mb-3">
            <label for="openingDate" class="form-label">Opening Date</label>
            <input type="date" class="form-control" id="openingDate">
          </div>
          <div class="mb-3">
            <label for="closingDate" class="form-label">Closing Date</label>
            <input type="date" class="form-control" id="closingDate">
          </div>
          <div class="mb-3">
            <label for="city" class="form-label">City</label>
            <input type="text" class="form-control" id="city" placeholder="Enter city">
          </div>
          <div class="mb-3">
            <label for="delivery" class="form-label">Address</label>
            <select id="delivery" class="form-select">
              <option selected disabled>Choose Address</option>
              <option value="Pickup">AddressBook1</option>
              <option value="Shipping">AddressBook2</option>
            </select>
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

</div>

<!-- Delete Lot Modal -->
<div class="modal fade" id="DELETE_LOT" tabindex="-1" aria-labelledby="DELETE_LOTLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title" id="DELETE_LOTLabel">Confirm Deletion</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center">
        <p>Are you sure you want to delete the lot?</p>
      </div>
      <div class="modal-footer justify-content-center">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary">Delete</button>
      </div>
    </div>
  </div>
</div>


<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<script>
  function closeModal(modalId) {
    const modalElement = document.getElementById(modalId);
    const modalInstance = bootstrap.Modal.getInstance(modalElement);
    if (modalInstance) {
      modalInstance.hide();
    }
  }

  document.querySelector('#LOT_FORM .btn-primary').addEventListener('click', function () {
    closeModal('LOT_FORM');
  });

  document.querySelector('#DELETE_LOT .btn-primary').addEventListener('click', function () {
    closeModal('DELETE_LOT');
  });
</script>


</html>
