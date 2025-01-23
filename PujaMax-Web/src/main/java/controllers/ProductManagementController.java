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
import java.util.Collection;
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
			req.setAttribute("idLot", idLot); // id del Lot para presentar sus productos asociados
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
		int idProduct = 0;
		String txtId = req.getParameter("txtId");
		if (txtId != null && !txtId.isEmpty()) {
			idProduct = Integer.parseInt(txtId);
		}

		int idLot = Integer.parseInt(req.getParameter("txtIdLot"));
		LotService lotService = new LotService();
		Lot lot = lotService.findLotById(idLot);

		String title = req.getParameter("txtTitle");
		String category = req.getParameter("txtCategory");
		double priceInitial = Double.parseDouble(req.getParameter("txtPriceInitial"));
		String description = req.getParameter("txtDescription");

		Product product = new Product(idProduct, lot, title, category, priceInitial, description);

		// Procesar múltiples fotos
		Collection<Part> photoParts = req.getParts().stream()
				.filter(part -> "txtPhotos".equals(part.getName()) && part.getSize() > 0).toList();

		for (Part photoPart : photoParts) {
			byte[] photoBytes = photoPart.getInputStream().readAllBytes();
			String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
			product.getPhotos().add(base64Photo);
		}

		return product;
	}

}
