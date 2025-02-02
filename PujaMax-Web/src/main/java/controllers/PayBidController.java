package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.service.BidService;
import model.service.ReceiptService;
import model.entities.Bid;
import model.entities.Bidder;

@WebServlet("/PayBidController")
@MultipartConfig
public class PayBidController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.router(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.router(req, resp);
	}

	private void router(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String route = (request.getParameter("route") == null) ? "viewHistory" : request.getParameter("route");
		switch (route) {
		case "viewHistory":
			this.viewHistory(request, response);
			break;
		case "payWinningBid":
			this.payWinningBid(request, response);
			break;
		case "updateReceipt":
			this.updateReceipt(request, response);
			break;
		}
	}

	private void viewHistory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Bidder bidder = (Bidder) session.getAttribute("user");

		if (bidder == null) {
			request.setAttribute("message", "You must log in to view your history.");
			getServletContext().getRequestDispatcher("/jsp/LOGIN.jsp").forward(request, response);
			return;
		}

		int bidderId = bidder.getId();
		BidService bidService = new BidService();
		List<Bid> bids = bidService.getBidsByUserId(bidderId);
		request.setAttribute("bids", bids);
		getServletContext().getRequestDispatcher("/jsp/BIDDER_HISTORY.jsp").forward(request, response);
	}

	private void payWinningBid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");

		if (user == null || !(user instanceof Bidder)) {
			request.setAttribute("messageType", "error");
			request.setAttribute("message", "You must be logged in as a bidder to perform this action.");
			getServletContext().getRequestDispatcher("/jsp/LOGIN.jsp").forward(request, response);
			return;
		}

		String bidId = request.getParameter("idBid");
		String stateParam = request.getParameter("state"); // Recibir el nuevo estado

		if (bidId == null || bidId.trim().isEmpty()) {
			response.sendRedirect("PayBidController?route=viewHistory");
			return;
		}

		BidService bidService = new BidService();
	    Bid bid = bidService.findBidById(Integer.parseInt(bidId));

		if (bid != null) {
			try {
				Bid.BidState newState = Bid.BidState.valueOf(stateParam);
				bid.setState(newState);
				bidService.updateBid(bid);
			} catch (IllegalArgumentException e) {
				request.setAttribute("messageType", "error");
				request.setAttribute("message", "Invalid state value provided.");
				response.sendRedirect("PayBidController?route=viewHistory");
				return;
			}
		}

		response.sendRedirect("PayBidController?route=viewHistory");
	}

	private void updateReceipt(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Bidder bidder = (Bidder) session.getAttribute("user");

		if (bidder == null) {
			request.setAttribute("messageType", "error");
			request.setAttribute("message", "You must be logged in as a bidder to upload a receipt.");
			getServletContext().getRequestDispatcher("/jsp/LOGIN.jsp").forward(request, response);
			return;
		}

		String bidId = request.getParameter("idBid");
		if (bidId == null || bidId.trim().isEmpty()) {
			response.sendRedirect("PayBidController?route=viewHistory");
			return;
		}

		Collection<Part> imageParts = request.getParts().stream()
				.filter(part -> "images".equals(part.getName()) && part.getSize() > 0).toList();

		if (imageParts.isEmpty()) {
			request.setAttribute("messageType", "error");
			request.setAttribute("message", "No images provided for the receipt.");
			response.sendRedirect("PayBidController?route=viewHistory");
			return;
		}

		try {
			List<String> base64Images = new ArrayList<>();
			for (Part imagePart : imageParts) {
				byte[] imageBytes = imagePart.getInputStream().readAllBytes();
				String base64Image = Base64.getEncoder().encodeToString(imageBytes);
				base64Images.add(base64Image);
			}

			// Usar ReceiptService en lugar de instanciar ReceiptDAO en el controlador
	        ReceiptService receiptService = new ReceiptService();
	        receiptService.createPayment(base64Images, Integer.parseInt(bidId));

			response.sendRedirect("PayBidController?route=viewHistory");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("messageType", "error");
			request.setAttribute("message", "An error occurred while updating the receipt: " + e.getMessage());
			request.getRequestDispatcher("/jsp/BIDDER_HISTORY.jsp").forward(request, response);
		}
	}
}