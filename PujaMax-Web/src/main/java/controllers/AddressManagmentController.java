package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.AddressDAO;
import model.entities.Address;
import model.entities.Auctioneer;

@WebServlet("/AddressManagmentController")
public class AddressManagmentController extends HttpServlet {

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
                this.viewAddresses(req, resp);
                break;
            case "add":
                this.addAddress(req, resp);
                break;
            case "saveNew":
                this.saveNewAddress(req, resp);
                break;
            case "edit":
                this.editAddress(req, resp);
                break;
            case "saveExisting":
                this.saveExistingAddress(req, resp);
                break;
            case "delete":
                this.deleteAddress(req, resp);
                break;
            case "accept":
                this.accept(req, resp);
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idAddress = Integer.parseInt(req.getParameter("idAddress"));
        AddressDAO addressDAO = new AddressDAO();
        if (addressDAO.removeAddress(idAddress)) {
            resp.sendRedirect("AddressManagmentController?route=list");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("message", "Could not delete address");

            resp.sendRedirect("AddressManagmentController?route=list");
        }
    }

    private void addAddress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        AddressDAO addressDAO = new AddressDAO();
        try {
            List<Address> addresses = addressDAO.findAddressesByAuctioneer(auctioneer.getId());
            req.setAttribute("addresses", addresses);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving addresses", e);
        }

        req.setAttribute("route", "add");
        req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
    }

    private void deleteAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idAddress = Integer.parseInt(req.getParameter("idAddress"));
        AddressDAO addressDAO = new AddressDAO();

        try {
            Address address = addressDAO.findAddressById(idAddress);
            List<Address> addresses = addressDAO.getAddresses();

            if (address != null) {
                req.setAttribute("address", address);
                req.setAttribute("addresses", addresses);
                req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("message", "Address could not be found");

                resp.sendRedirect("AddressManagmentController?route=list");
            }
        } catch (SQLException e) {
            throw new ServletException("Error retrieving addresses", e);
        }
    }

    private void editAddress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idAddress = Integer.parseInt(req.getParameter("idAddress"));
        AddressDAO addressDAO = new AddressDAO();
        Address address = addressDAO.findAddressById(idAddress);

        try {
            List<Address> addresses = addressDAO.getAddresses();
            req.setAttribute("addresses", addresses);

            if (address != null) {
                req.setAttribute("address", address);
                req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("message", "Address not found");

                resp.sendRedirect("AddressManagmentController?route=list");
            }
        } catch (SQLException e) {
            throw new ServletException("Error retrieving addresses", e);
        }
    }

    private void saveExistingAddress(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Address address = parseAddressFromRequest(req);
        AddressDAO addressDAO = new AddressDAO();

        if (addressDAO.updateAddress(address)) {
            resp.sendRedirect("AddressManagmentController?route=list");
        } else {
            // Almacena el mensaje en la sesión
            HttpSession session = req.getSession();
            session.setAttribute("message", "The address could not be updated");

            resp.sendRedirect("AddressManagmentController?route=list");
        }
    }

    private void saveNewAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Address address = parseAddressFromRequest(req);
        AddressDAO addressDAO = new AddressDAO();
        if (addressDAO.createAddress(address)) {
            resp.sendRedirect("AddressManagmentController?route=list");
        } else {
            // Almacena el mensaje en la sesión
            HttpSession session = req.getSession();
            session.setAttribute("message", "The address could not be created");

            resp.sendRedirect("AddressManagmentController?route=list");
        }
    }

    private void viewAddresses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        List<Address> addresses;
        try {
            AddressDAO addressDAO = new AddressDAO();
            addresses = addressDAO.findAddressesByAuctioneer(auctioneer.getId());
            req.setAttribute("addresses", addresses);
            req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
            //getServletContext().getRequestDispatcher("/jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving addresses", e);
        }
    }

    private Address parseAddressFromRequest(HttpServletRequest req) {
        int id = 0;
        String txtId = req.getParameter("txtId");

        if (txtId != null && !txtId.trim().isEmpty()) {
            try {
                id = Integer.parseInt(txtId);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el ID: " + e.getMessage());
            }
        }

        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");

        String name = req.getParameter("txtName");
        String province = req.getParameter("txtProvince");
        String city = req.getParameter("txtCity");
        String mainStreet = req.getParameter("txtMainStreet");
        String secondaryStreet = req.getParameter("txtSecondaryStreet");
        String postcode = req.getParameter("txtPostcode");
        String houseNumber = req.getParameter("txtHouseNumber");
        String company = req.getParameter("txtCompany");

        return new Address(id,auctioneer,name, province, city, mainStreet, secondaryStreet, postcode, houseNumber, company);
    }
}
