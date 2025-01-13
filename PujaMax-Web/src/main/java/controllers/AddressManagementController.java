package controllers;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Address;
import model.entities.Auctioneer;
import model.service.AddressService;

@WebServlet("/AddressManagementController")
public class AddressManagementController extends HttpServlet {

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

    private void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idAddress = Integer.parseInt(req.getParameter("idAddress"));
        AddressService addressService = new AddressService();
        if (addressService.removeAddress(idAddress)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Address deleted successfully.");
            req.getRequestDispatcher("AddressManagementController?route=list").forward(req, resp);
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to delete address.");
            req.getRequestDispatcher("AddressManagementController?route=list").forward(req, resp);
        }
    }

    private void addAddress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");

        try {
            List<Address> addresses = new AddressService().findAddressesByIdAuctioneer(auctioneer.getId());
            req.setAttribute("addresses", addresses);
            req.setAttribute("route", "add");
            req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving addresses", e);
        }
    }

    private void deleteAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        int idAddress = Integer.parseInt(req.getParameter("idAddress"));
        AddressService addressService = new AddressService();
        try {
            Address address = addressService.findAddressById(idAddress);
            List<Address> addresses = addressService.findAddressesByIdAuctioneer(auctioneer.getId());

            if (address != null) {
                req.setAttribute("address", address);
                req.setAttribute("addresses", addresses);
                req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
            } else {
                session.setAttribute("message", "Address could not be found");
                resp.sendRedirect("AddressManagementController?route=list");
            }
        } catch (SQLException e) {
            throw new ServletException("Error retrieving addresses", e);
        }
    }

    private void editAddress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        int idAddress = Integer.parseInt(req.getParameter("idAddress"));
        AddressService addressService = new AddressService();
        Address address = addressService.findAddressById(idAddress);

        try {
            List<Address> addresses = addressService.findAddressesByIdAuctioneer(auctioneer.getId());
            req.setAttribute("addresses", addresses);

            if (address != null) {
                req.setAttribute("address", address);
                req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
            } else {
                session.setAttribute("message", "Address not found");
                resp.sendRedirect("AddressManagementController?route=list");
            }
        } catch (SQLException e) {
            throw new ServletException("Error retrieving addresses", e);
        }
    }

    private void saveExistingAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Address address = parseAddressFromRequest(req);
        AddressService addressService = new AddressService();

        if (addressService.updateAddress(address)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Address updated successfully.");
            req.getRequestDispatcher("AddressManagementController?route=list").forward(req, resp);
            //resp.sendRedirect("AddressManagementController?route=list");
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to update address.");
            req.getRequestDispatcher("AddressManagementController?route=list").forward(req, resp);
        }
    }

    private void saveNewAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Address address = parseAddressFromRequest(req);
        AddressService addressService = new AddressService();
        if (addressService.createAddress(address)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Address created successfully.");
            req.getRequestDispatcher("AddressManagementController?route=list").forward(req, resp);
            //resp.sendRedirect("AddressManagementController?route=list");
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to create address.");
            req.getRequestDispatcher("AddressManagementController?route=list").forward(req, resp);
        }
    }

    private void viewAddresses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Auctioneer auctioneer = (Auctioneer) session.getAttribute("user");
        List<Address> addresses;
        try {
            addresses = new AddressService().findAddressesByIdAuctioneer(auctioneer.getId());
            req.setAttribute("addresses", addresses);
            req.getRequestDispatcher("jsp/AUCTIONEER_PROFILE.jsp").forward(req, resp);
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

        return new Address(id, name, province, city, mainStreet, secondaryStreet, postcode, houseNumber, company, auctioneer);
    }
}
