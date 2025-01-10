package controllers;

import jakarta.servlet.ServletException;

import java.io.Serial;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entities.Auctioneer;
import model.entities.Bidder;
import model.service.AuctioneerService;
import model.service.BidderService;

import java.io.IOException;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

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
        String route = (req.getParameter("route") == null) ? "register" : req.getParameter("route");

        switch (route) {
            case "enter":
                this.enter(req, resp);
                break;
            case "save":
                this.save(req, resp);
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void enter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("jsp/REGISTER.jsp");
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");
            if ("auctioneer".equalsIgnoreCase(role)) {
                Auctioneer auctioneer = parseAuctioneerFromRequest(req);
                AuctioneerService auctioneerService = new AuctioneerService();
                if (auctioneerService.createAuctioneer(auctioneer)) {
                    resp.sendRedirect("LoginController?route=enter");
                } else {
                    req.setAttribute("error", "Failed to register Auctioneer. Please try again.");
                    req.getRequestDispatcher("jsp/REGISTER.jsp").forward(req, resp);
                }
            } else if ("bidder".equalsIgnoreCase(role)) {
                Bidder bidder = parseBidderFromRequest(req);
                BidderService bidderService = new BidderService();
                if (bidderService.createBidder(bidder)) {
                    resp.sendRedirect("LoginController?route=enter");
                } else {
                    req.setAttribute("error", "Failed to register Bidder. Please try again.");
                    req.getRequestDispatcher("jsp/REGISTER.jsp").forward(req, resp);
                }
            }
    }

    private Bidder parseBidderFromRequest(HttpServletRequest req) {
        int id = 0;
        String dni = req.getParameter("txtDni");
        String name = req.getParameter("txtName");
        String lastName = req.getParameter("txtLastName");
        String email = req.getParameter("txtEmail");
        String password = req.getParameter("txtPassword");
        String phoneNumber = req.getParameter("txtPhoneNumber");

        return new Bidder(id, dni, name, lastName, email, password, phoneNumber);
    }

    private Auctioneer parseAuctioneerFromRequest(HttpServletRequest req) {
        int id = 0;
        String dni = req.getParameter("txtDni");
        String name = req.getParameter("txtName");
        String lastName = req.getParameter("txtLastName");
        String email = req.getParameter("txtEmail");
        String password = req.getParameter("txtPassword");
        String phoneNumber = req.getParameter("txtPhoneNumber");

        return new Auctioneer(id, dni, name, lastName, email, password, phoneNumber);
    }
}
