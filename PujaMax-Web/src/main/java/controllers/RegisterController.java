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
        // 1. Obtener los datos del formulario
        String dni = req.getParameter("txtDni");
        String name = req.getParameter("txtName");
        String lastName = req.getParameter("txtLastName");
        String email = req.getParameter("txtEmail");
        String password = req.getParameter("txtPassword");
        String phoneNumber = req.getParameter("txtPhoneNumber");
        String role = req.getParameter("txtRole");

            // 2. Validar el rol y crear el objeto correspondiente
            if ("auctioneer".equalsIgnoreCase(role)) {
                // Crear un Auctioneer
                Auctioneer auctioneer = new Auctioneer(dni, name, lastName, email, password, phoneNumber);

                // 3. Llamar al servicio de Auctioneer para guardarlo
                AuctioneerService auctioneerService = new AuctioneerService();
                if (auctioneerService.createAuctioneer(auctioneer)) {
                    // 4. Si el registro fue exitoso, redirigir al Login
                    resp.sendRedirect("LoginController?route=enter");
                } else {
                    // Si falla, enviar mensaje de error
                    req.setAttribute("error", "Failed to register Auctioneer. Please try again.");
                    req.getRequestDispatcher("jsp/REGISTER.jsp").forward(req, resp);
                }

            } else if ("bidder".equalsIgnoreCase(role)) {
                // Implementar lógica Bidder
                req.setAttribute("error", "Bidder registration is not yet implemented.");
                req.getRequestDispatcher("jsp/REGISTER.jsp").forward(req, resp);
            }
    }

}
