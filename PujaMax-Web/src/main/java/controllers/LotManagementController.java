package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.AddressDAO;
import model.entities.Address;
import model.entities.Auctioneer;
import model.entities.Lot;
import model.service.LotService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/LotManagementController")
public class LotManagementController  extends HttpServlet {
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
            case "create":
                this.create(req, resp);
                break;
            case "save":
                this.save(req, resp);
                break;
            case "edit":
                this.edit(req, resp);
                break;
            case "update":
                this.update(req, resp);
                break;
            case "delete":
                this.delete(req, resp);
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
            //getServletContext().getRequestDispatcher("/jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving lots", e);
        }

    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/CREATE_LOT.jsp");
    }

    private void save (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/AUCTIONEER_LOT_BOARD.jsp");
    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/EDIT_LOT.jsp");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/AUCTIONEER_LOT_BOARD.jsp");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/AUCTIONEER_LOT_BOARD.jsp");
    }
}
