package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.entities.Product;
import model.entities.Auctioneer;
import model.entities.Lot;
import model.service.LotService;
import model.service.ProductService;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@WebServlet("/ProductManagementController")
@MultipartConfig
public class ProductManagementController extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.router(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.router(req, resp);
	}

	private void router(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String route = req.getParameter("route") != null ? req.getParameter("route") : "list";

		try {
			switch (route) {
			case "list":
				list(req, resp);
				break;
			case "add":
				addProduct(req, resp);
				break;
			case "saveNew":
				saveNewProduct(req, resp);
				break;
			case "edit":
				editProduct(req, resp);
				break;
			case "saveExisting":
				saveExistingProduct(req, resp);
				break;
			case "delete":
				deleteProduct(req, resp);
				break;
			case "acceptDelete":
				acceptDelete(req, resp); 
				break;
			default:
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown route: " + route);
			}
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Unexpected error: " + e.getMessage());
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
			if (auctioneer == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
				return;
			}

			int idLot = Integer.parseInt(req.getParameter("idLot"));
			req.setAttribute("idLot", idLot); // Asegurar que el JSP reciba el idLot
			ProductService productService = new ProductService();
			List<Product> products = productService.findProductsByLotId(idLot);

			req.setAttribute("products", products);
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Unexpected error: " + e.getMessage());
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
		try {
			List<Lot> lots = new LotService().findLotsByIdAuctioneer(auctioneer.getId());
			if (lots.isEmpty()) {
				throw new IllegalArgumentException("No lots found for this auctioneer.");
			}
			int idLot = lots.get(0).getIdLot();

			req.setAttribute("idLot", idLot);
			req.setAttribute("route", "add");
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Error adding product: " + e.getMessage());
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private void saveNewProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Product product = parseProductFromRequest(req);
			System.out.println("Lot ID in saveNewProduct: " + product.getLot().getIdLot());
			ProductService productService = new ProductService();

			if (productService.createProduct(product)) {
				req.setAttribute("messageType", "info");
				req.setAttribute("message", "Product created successfully.");
			} else {
				req.setAttribute("messageType", "error");
				req.setAttribute("message", "Failed to create product.");
			}
			resp.sendRedirect("ProductManagementController?route=list&idLot=" + product.getLot().getIdLot());
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Error saving product: " + e.getMessage());
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int idProduct = Integer.parseInt(req.getParameter("idProduct"));
			ProductService productService = new ProductService();
			Product product = productService.findProductById(idProduct);

			req.setAttribute("product", product);
			req.setAttribute("route", "edit");
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Invalid Product ID provided.");
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private void saveExistingProduct(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Product product = parseProductFromRequest(req);
			ProductService productService = new ProductService();

			if (productService.updateProduct(product)) {
				req.setAttribute("messageType", "info");
				req.setAttribute("message", "Product updated successfully.");
			} else {
				req.setAttribute("messageType", "error");
				req.setAttribute("message", "Failed to update product.");
			}
			resp.sendRedirect("ProductManagementController?route=list&idLot=" + product.getLot().getIdLot());
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Error updating product: " + e.getMessage());
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
		int idProduct = Integer.parseInt(req.getParameter("idProduct"));
		ProductService productService = new ProductService();

		try {
			Product product = productService.findProductById(idProduct);
			List<Product> products = productService.findProductsByLotId(product.getLot().getIdLot());

			req.setAttribute("product", product);
			req.setAttribute("products", products);
			req.setAttribute("route", "delete");
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Error retrieving product: " + e.getMessage());
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private void acceptDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int idProduct = Integer.parseInt(req.getParameter("idProduct"));
		ProductService productService = new ProductService();

		try {
			if (productService.removeProduct(idProduct)) {
				req.setAttribute("messageType", "info");
				req.setAttribute("message", "Product deleted successfully.");
			} else {
				req.setAttribute("messageType", "error");
				req.setAttribute("message", "Failed to delete product.");
			}
			resp.sendRedirect("ProductManagementController?route=list&idLot=" + req.getParameter("idLot"));
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Error deleting product: " + e.getMessage());
			req.getRequestDispatcher("jsp/AUCTIONEER_LOT.jsp").forward(req, resp);
		}
	}

	private Product parseProductFromRequest(HttpServletRequest req) throws IOException, ServletException {
		// Validar y asignar ID del producto
		int idProduct = 0;
		String txtId = req.getParameter("txtId");
		if (txtId != null && !txtId.isEmpty()) {
			try {
				idProduct = Integer.parseInt(txtId);
			} catch (NumberFormatException e) {
				System.out.println("Error parsing product ID: " + e.getMessage());
			}
		}

		// Validar y asignar ID del lote
		String txtIdLot = req.getParameter("txtIdLot");
		System.out.println("txtIdLot recibido: " + txtIdLot); // Log para depurar
		if (txtIdLot == null || txtIdLot.isEmpty()) {
			throw new IllegalArgumentException("Lot ID is required.");
		}
		int idLot = Integer.parseInt(txtIdLot);
		;
		LotService lotService = new LotService();
		Lot lot = lotService.findLotById(idLot);

		// Validar y asignar título
		String title = req.getParameter("txtTitle");
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Title is required.");
		}

		// Validar y asignar categoría
		String category = req.getParameter("txtCategory");
		if (category == null || category.isEmpty()) {
			throw new IllegalArgumentException("Category is required.");
		}

		// Validar y asignar precio inicial
		String txtPriceInitial = req.getParameter("txtPriceInitial");
		if (txtPriceInitial == null || txtPriceInitial.isEmpty()) {
			throw new IllegalArgumentException("Initial price is required.");
		}
		double priceInitial = Double.parseDouble(txtPriceInitial);

		// Validar y asignar descripción
		String description = req.getParameter("txtDescription");
		if (description == null) {
			description = ""; // Descripción opcional, asignar cadena vacía si no está presente
		}

		// Procesar imagen (opcional)
		String base64Photo = null;
		Part photoPart = req.getPart("txtPhoto");
		if (photoPart != null && photoPart.getSize() > 0) {
			byte[] photoBytes = photoPart.getInputStream().readAllBytes();
			base64Photo = Base64.getEncoder().encodeToString(photoBytes);
		}

		// Crear y retornar el producto utilizando el constructor con parámetros
		return new Product(idProduct, lot, title, category, priceInitial, description, base64Photo);
	}

}