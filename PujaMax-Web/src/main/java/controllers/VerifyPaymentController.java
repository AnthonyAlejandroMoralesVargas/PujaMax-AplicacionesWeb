package controllers;

import java.io.IOException;
import java.io.Serial;
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
import model.entities.Auctioneer;
import model.entities.Bid;
import model.entities.Bidder;
import model.entities.Lot;
import model.entities.Product;
import model.jpa.BidJPA;
import model.service.BidService;
import model.service.LotService;
import model.service.ProductService;
import model.service.ReceiptService;

@WebServlet("/VerifyPaymentController")
@MultipartConfig
public class VerifyPaymentController extends HttpServlet {
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
			case "viewHistory":
				viewHistory(req, resp);
				break;
			case "aprove":
				approveReceipt(req, resp);
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

	private void viewHistory(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");

	    // Consultar las pujas con estado PENDING_APPROVAL
	    BidService bidService = new BidService();
	    List<Bid> bids = bidService.getBidsByState(Bid.BidState.PENDING_APPROVAL); // Método para consultar pujas

	    // Pasar las pujas al JSP
	    request.setAttribute("bids", bids);
	    getServletContext().getRequestDispatcher("/jsp/AUCTIONEER_HISTORY.jsp").forward(request, response);
	}
	
	private void approveReceipt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("idReceipt");
		System.out.println("Estoy en approveReceipt");
		System.out.println("idReceipt param: " + param);
		int idReceipt = Integer.parseInt(req.getParameter("idReceipt"));

	    ReceiptService receiptService = new ReceiptService(null);
	    if (receiptService.approveReceipt(idReceipt)) {
	        req.setAttribute("messageType", "info");
	        req.setAttribute("message", "Receipt approved successfully.");
	    } else {
	        req.setAttribute("messageType", "error");
	        req.setAttribute("message", "Failed to approve receipt.");
	    }

	    req.getRequestDispatcher("/VerifyPaymentController?route=viewHistory").forward(req, resp);
	}





	

}
