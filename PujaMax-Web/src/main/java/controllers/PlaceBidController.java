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
	import model.entities.Bid;
	import model.entities.Bidder;
	import model.entities.Product;
	import model.service.AddressService;
	import model.service.BidService;
	import model.service.ProductService;
	
	@WebServlet("/PlaceBidController")
	public class PlaceBidController extends HttpServlet {
	
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
	        String route = (req.getParameter("route") == null) ? "list" : req.getParameter("route");
	        System.out.println("Route received: " + route); // Depuración
	        System.out.println("ID Product: " + req.getParameter("idProduct")); // Depuración
	
	        try {
				switch (route) {
				case "list":
					listProduct(req, resp);
					break;
				case "productDetails":
					showProductDetails(req, resp);
					break;
				case "bids":
	                showBidsForProduct(req, resp); // Nueva ruta para mostrar pujas
	                break;
				case "submit":
				    madeBid(req, resp);
				    break;

				default:
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown route: " + route);
				}
			} catch (Exception e) {
				req.setAttribute("messageType", "error");
				req.setAttribute("message", "Unexpected error: " + e.getMessage());
				req.getRequestDispatcher("BIDDER_LOT.jsp").forward(req, resp);
			}
	    }
	
	
	
	    private void listProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
			// Verificar si se solicita una imagen específica
			String idProductParam = req.getParameter("idProduct");
			if (idProductParam != null) {
				try {
					int idProduct = Integer.parseInt(idProductParam);
					ProductService productService = new ProductService();
					Product product = productService.findProductById(idProduct);
	
					if (product == null || product.getPhoto() == null) {
						resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
						return;
					}
	
					// Configurar el tipo de contenido y enviar la imagen
					resp.setContentType("image/jpeg");
					resp.setContentLength(product.getPhoto().length);
					resp.getOutputStream().write(product.getPhoto());
				} catch (NumberFormatException e) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID");
				} catch (Exception e) {
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"Error retrieving image: " + e.getMessage());
				}
				return; // Salir después de procesar la imagen
			}
	
			// Lógica para listar productos
			try {
				int idLot = Integer.parseInt(req.getParameter("idLot"));
				ProductService productService = new ProductService();
				List<Product> products = productService.findProductsByLotId(idLot);
	
				req.setAttribute("products", products);
				req.setAttribute("idLot", idLot);
				req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
			} catch (NumberFormatException e) {
				req.setAttribute("messageType", "error");
				req.setAttribute("message", "Invalid Lot ID provided.");
				req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
			}
		}
	    
	    private void showProductDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        System.out.println("Entering showProductDetails");

	        // Cargar el producto desde la base de datos
	        Product product = loadProduct(req, resp);
	        if (product == null) {
	            System.out.println("Product not found or invalid");
	            return; // Salir si el producto no se encuentra
	        }

	        System.out.println("Product loaded: " + product.getTitle());

	        // Determinar el precio actual del producto
	        double currentPrice = product.getBid().isEmpty() 
	            ? product.getPriceInitial() // Si no hay pujas, usar el precio inicial
	            : product.getBid().stream()
	                  .mapToDouble(Bid::getCurrentPrice)
	                  .max()
	                  .orElse(product.getPriceInitial()); // Si hay pujas, usar la más alta

	        // Enviar el precio calculado y el estado de las pujas al JSP
	        String bidStatus = product.getBid().isEmpty() ? "No bids available" : "Bids available";
	        req.setAttribute("currentPrice", currentPrice);
	        req.setAttribute("bidStatus", bidStatus);
	        req.setAttribute("product", product);

	        // Redirigir al JSP de detalles del producto
	        req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
	        System.out.println("Forwarding to PRODUCT.jsp with currentPrice: " + currentPrice);
	    }



	
	
	    private Product loadProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        String idProductParam = req.getParameter("idProduct");
	        if (idProductParam == null || idProductParam.isEmpty()) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
	            return null;
	        }
	
	        try {
	            int idProduct = Integer.parseInt(idProductParam);
	            ProductService productService = new ProductService();
	            Product product = productService.findProductById(idProduct);
	
	            if (product == null) {
	                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
	                return null;
	            }
	            return product;
	        } catch (NumberFormatException e) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID: " + idProductParam);
	        } catch (Exception e) {
	            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving product: " + e.getMessage());
	        }
	        return null;
	    }
	    
	    
	    
	
	    private void madeBid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        String idProductParam = req.getParameter("idProduct");
	        String yourBidParam = req.getParameter("yourBid");

	        if (idProductParam == null || yourBidParam == null || idProductParam.isEmpty() || yourBidParam.isEmpty()) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID and bid amount are required");
	            return;
	        }

	        try {
	            int idProduct = Integer.parseInt(idProductParam);
	            double yourBid = Double.parseDouble(yourBidParam);

	            ProductService productService = new ProductService();
	            BidService bidService = new BidService();

	            // Obtener el producto
	            Product product = productService.findProductById(idProduct);
	            if (product == null) {
	                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
	                return;
	            }

	            // Obtener el precio actual más alto
	            double currentPrice = product.getBid().isEmpty()
	                    ? product.getPriceInitial()
	                    : product.getBid().stream()
	                          .mapToDouble(Bid::getCurrentPrice)
	                          .max()
	                          .orElse(product.getPriceInitial());

	            // Validar la oferta
	            if (yourBid <= currentPrice) {
	                req.setAttribute("messageType", "error");
	                req.setAttribute("message", "Your bid must be higher than the current price.");
	                req.setAttribute("product", product);
	                req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
	                return;
	            }

	            // Crear una nueva puja
	            Bid newBid = new Bid();
	            newBid.setProduct(product);  // Establecer la relación
	            newBid.setCurrentPrice(yourBid);  // Precio actual de la puja
	            newBid.setPrice(yourBid);  // Asignar al precio (si aplica)
	            newBid.setDateBid(new java.util.Date());  // Fecha de la puja
	            newBid.setState("Pending");  // Estado inicial de la puja

	            // Relacionar la puja con el producto
	            product.getBid().add(newBid);
	            
	            System.out.println("Bid details: " + newBid.toString());

	            // Guardar la nueva puja
	            bidService.updateBid(newBid);

	            // Si es necesario, actualizar el producto
	            productService.updateProduct(product);

	            // Redirigir con éxito
	            req.setAttribute("messageType", "success");
	            req.setAttribute("message", "Your bid has been successfully submitted.");
	            req.setAttribute("product", product);
	            req.setAttribute("yourOffer", yourBid);
	            req.setAttribute("currentPrice", currentPrice);

	            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
	        } catch (NumberFormatException e) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format.");
	        } catch (Exception e) {
	            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
	        }
	    }



	    
	    private void showBidsForProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String idProductParam = req.getParameter("idProduct");
	        if (idProductParam == null || idProductParam.isEmpty()) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
	            return;
	        }

	        try {
	            int idProduct = Integer.parseInt(idProductParam);

	            ProductService productService = new ProductService();
	            BidService bidService = new BidService();

	            Product product = productService.findProductById(idProduct);
	            if (product == null) {
	                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
	                return;
	            }

	            int bidCount = bidService.findBidsByProductId(idProduct).size(); // Contar pujas

	            req.setAttribute("product", product);
	            req.setAttribute("bidCount", bidCount); // Enviar la cuenta al JSP

	            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
	        } catch (NumberFormatException e) {
	            req.setAttribute("messageType", "error");
	            req.setAttribute("message", "Invalid Product ID provided.");
	            req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
	        } catch (Exception e) {
	            req.setAttribute("messageType", "error");
	            req.setAttribute("message", "Unexpected error: " + e.getMessage());
	            req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
	        }
	    }
	
	}
