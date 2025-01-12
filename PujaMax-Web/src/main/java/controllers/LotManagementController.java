package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Address;
import model.entities.Auctioneer;
import model.entities.Lot;
import model.service.AddressService;
import model.service.LotService;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/LotManagementController")
public class LotManagementController extends HttpServlet {
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
        // Control logic
        String route = (req.getParameter("route") == null) ? "list" : req.getParameter("route");

        switch (route) {
            case "list":
                this.list(req, resp);
                break;
            case "add":
                this.addLot(req, resp);
                break;
            case "saveNew":
                this.saveNewLot(req, resp);
                break;
            case "edit":
                this.editLot(req, resp);
                break;
            case "saveExisting":
                this.saveExistingLot(req, resp);
                break;
            case "delete":
                this.deleteLot(req, resp);
                break;
            case "accept":
                this.accept(req, resp);
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        List<Lot> lots;

        try {
            LotService lotService = new LotService();
            lots = lotService.findLotsByIdAuctioneer(auctioneer.getId());
            req.setAttribute("lots", lots);
            req.getRequestDispatcher("jsp/AUCTIONEER_LOT_BOARD.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving lots", e);
        }

    }

    private void addLot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        try {
            List<Lot> lots = new LotService().findLotsByIdAuctioneer(auctioneer.getId());
            List<Address> addresses = new AddressService().findAddressesByIdAuctioneer(auctioneer.getId());
            req.setAttribute("addresses", addresses);
            req.setAttribute("lots", lots);
            req.setAttribute("route", "add");
            req.getRequestDispatcher("jsp/AUCTIONEER_LOT_BOARD.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveNewLot(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Lot lot = parseLotFromRequest(req);
        LotService lotService = new LotService();
        if (lotService.createLot(lot)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Lot created successfully.");
            req.getRequestDispatcher("LotManagementController?route=list").forward(req, resp);
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to create lot.");
            req.getRequestDispatcher("LotManagementController?route=list").forward(req, resp);
        }
    }

    private void editLot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        int idLot = Integer.parseInt(req.getParameter("idLot"));
        LotService lotService = new LotService();
        Lot lot = lotService.findLotById(idLot);
        try {
            List<Lot> lots = new LotService().findLotsByIdAuctioneer(auctioneer.getId());
            List<Address> addresses = new AddressService().findAddressesByIdAuctioneer(auctioneer.getId());
            req.setAttribute("addresses", addresses);
            req.setAttribute("lot", lot);
            req.setAttribute("lots", lots);
            req.setAttribute("route", "edit");
            req.getRequestDispatcher("jsp/AUCTIONEER_LOT_BOARD.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveExistingLot(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Lot lot = parseLotFromRequest(req);
        LotService lotService = new LotService();
        if (lotService.updateLot(lot)) {
            resp.sendRedirect("LotManagementController?route=list");
        } else {
            resp.sendRedirect("LotManagementController?route=edit&idLot=" + lot.getIdLot());
        }
    }

    private void deleteLot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        int idLot = Integer.parseInt(req.getParameter("idLot"));
        LotService lotService = new LotService();
        Lot lot = lotService.findLotById(idLot);
        try {
            List<Lot> lots = new LotService().findLotsByIdAuctioneer(auctioneer.getId());
            req.setAttribute("lot", lot);
            req.setAttribute("lots", lots);
            req.setAttribute("route", "delete");
            req.getRequestDispatcher("jsp/AUCTIONEER_LOT_BOARD.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idLot = Integer.parseInt(req.getParameter("idLot"));
        LotService lotService = new LotService();
        if (lotService.removeLot(idLot)) {
            resp.sendRedirect("LotManagementController?route=list");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("message", "Could not delete lot");
            resp.sendRedirect("LotManagementController?route=list");
        }
    }

    private Lot parseLotFromRequest(HttpServletRequest req) {
        int id = 0;
        String txtId = req.getParameter("txtId");
        int idAddress = Integer.parseInt(req.getParameter("txtIdAddress"));

        if (txtId != null && !txtId.trim().isEmpty()) {
            try {
                id = Integer.parseInt(txtId);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el ID: " + e.getMessage());
            }
        }

        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        AddressService addressService = new AddressService();
        Address address = addressService.findAddressById(idAddress);

        String title = req.getParameter("txtTitle");
        int quantityProducts = Integer.parseInt(req.getParameter("txtQuantityProducts"));
        java.sql.Date dateOpening = java.sql.Date.valueOf(req.getParameter("txtOpeningDate"));
        java.sql.Date dateClosing = java.sql.Date.valueOf(req.getParameter("txtClosingDate"));
        String state = req.getParameter("txtState");

        return new Lot(id, title, quantityProducts, dateOpening, dateClosing, address, state, auctioneer);
    }
}
