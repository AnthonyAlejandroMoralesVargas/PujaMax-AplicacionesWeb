package controllers;

import jakarta.servlet.ServletException;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entities.Auctioneer;
import model.service.AuctioneerService;

import java.io.IOException;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

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

    private void enter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/REGISTER.jsp");
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Get data from the request
        String role = req.getParameter("txtRole");
        System.out.println("Role: " + role);
            // Validar el rol y crear el objeto correspondiente
            if ("auctioneer".equalsIgnoreCase(role)) {
                // 2. talk to the model
                Auctioneer auctioneer = parseAuctioneerFromRequest(req);
                AuctioneerService auctioneerService = new AuctioneerService();
                if (auctioneerService.createAuctioneer(auctioneer)) {
                    // 3. Redirect to the view
                    resp.sendRedirect("LoginController?route=enter");
                } else {
                    req.setAttribute("error", "Failed to register Auctioneer. Please try again.");
                    req.getRequestDispatcher("jsp/REGISTER.jsp").forward(req, resp);
                }

            } else if ("bidder".equalsIgnoreCase(role)) {
                // Implementar lógica Bidder
                req.setAttribute("error", "Bidder registration is not yet implemented.");
                req.getRequestDispatcher("jsp/REGISTER.jsp").forward(req, resp);
            }
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
