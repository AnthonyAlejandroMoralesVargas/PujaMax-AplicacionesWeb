package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Auctioneer;
import model.entities.Bidder;
import model.entities.User;
import model.service.UserService;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

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
        String route = (req.getParameter("route") == null) ? "login" : req.getParameter("route");

        switch (route) {
            case "enter":
                this.enter(req, resp);
                break;
            case "login":
                this.login(req, resp);
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void enter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/LOGIN.jsp");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("txtDni");
        String password = req.getParameter("txtPassword");
        String role = req.getParameter("role");

        UserService userService = new UserService();
        User user = userService.authenticate(dni, password, role);

        if (user != null) {
            // Crear sesión y guardar el usuario autenticado
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // Redirigir según el rol
            if (user instanceof Auctioneer) {
                resp.sendRedirect("LotManagementController?route=list");
            }  else if(user instanceof Bidder) {
                resp.sendRedirect("LotManagementController?route=list");
            }
        } else {
            req.setAttribute("message", "Invalid credentials");
            req.getRequestDispatcher("jsp/LOGIN.jsp").forward(req, resp);
        }
    }
}
