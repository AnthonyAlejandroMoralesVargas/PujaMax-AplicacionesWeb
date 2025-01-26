package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.jpa.BidJPA;
import model.jpa.ReceiptJPA;
import model.service.ProductService;
import model.service.ReceiptService;
import model.entities.Bid;
import model.entities.Bidder;

@WebServlet("/PayBidController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class PayBidController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory entityManagerFactory; 

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    private void router(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        int bidderId = bidder.getId(); // Obtener el ID del usuario
        BidJPA bidJPA = new BidJPA();
        List<Bid> bids = bidJPA.getBidsByUserId(bidderId); // Consulta basada en el ID del usuario
        System.out.println("Bidder ID: " + bidderId);
        System.out.println("Bidder ID: " + bidder.getDni());
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

        BidJPA bidJPA = new BidJPA();
        Bid bid = bidJPA.findBidById(Integer.parseInt(bidId));

        if (bid != null) {
            try {
                // Convertir el valor del parámetro "state" al enumerador BidState
                Bid.BidState newState = Bid.BidState.valueOf(stateParam);
                bid.setState(newState); // Asignar el nuevo estado al bid
                bidJPA.updateBid(bid);
            } catch (IllegalArgumentException e) {
                // Manejar un estado no válido
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

        // Validar parámetros
        String bidId = request.getParameter("idBid");
        Part documentPart = request.getPart("document");

        if (bidId == null || bidId.trim().isEmpty() || documentPart == null || documentPart.getSize() == 0) {
            response.sendRedirect("PayBidController?route=viewHistory");
            return;
        }

        try {
            // Guardar archivo
            String fileName = Paths.get(documentPart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = getServletContext().getRealPath("") + "uploads";
            File uploads = new File(uploadDir);
            if (!uploads.exists()) {
                uploads.mkdir();
            }
            String filePath = uploadDir + File.separator + fileName;
            documentPart.write(filePath);

            // Crear el recibo usando el servicio
            ReceiptJPA receiptJPA = new ReceiptJPA();
            ReceiptService receiptService = new ReceiptService(receiptJPA);
            receiptService.createPayment(filePath, Integer.parseInt(bidId));
            response.sendRedirect("PayBidController?route=viewHistory");
            
        } catch (Exception e) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "An error occurred while updating the receipt.");
            response.sendRedirect("PayBidController?route=viewHistory");
        }
    }
}