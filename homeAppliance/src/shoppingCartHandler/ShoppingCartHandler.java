package shoppingCartHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ShoppingDao;
import DAO.UserDao;
import sessionManagement.Session;
import sessionManagement.SessionManager;
import shoppingCart.ShoppingCart;
import shoppingCart.ShoppingCartItem;
import users.User;
import util.WebUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ShoppingCartHandler implements HttpHandler {
    private final SessionManager sessionManager;
    private final ShoppingDao shoppingDao;
    private final UserDao userDao;

    public ShoppingCartHandler(UserDao userDao, ShoppingDao shoppingDao, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.shoppingDao = shoppingDao;
        this.userDao = userDao;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract session
        String sessionId = WebUtil.extractSessionId(exchange);
        Session session = sessionManager.getSession(sessionId);

        if (session == null) {
            sendErrorResponse(exchange, 401, "Unauthorized", "Please log in to view your cart.");
            return;
        }

        // Retrieve user ID from the session
        int userId = (Integer) session.getAttribute("userId");
        System.out.println("User ID: " + userId);
        
        if (userId == 0) {
            sendErrorResponse(exchange, 400, "Bad Request", "User session invalid.");
            return;
        }

        // Fetch user and cart items
        User user = userDao.getUser(userId);
        System.out.println("User is " + user);
        
        if (user == null) {
            sendErrorResponse(exchange, 404, "Not Found", "User not found.");
            return;
        }

        List<ShoppingCartItem> cartItems = shoppingDao.findAll(userId, null);
        ShoppingCart cart = new ShoppingCart(user);

        if (cartItems != null) {
            for (ShoppingCartItem item : cartItems) {
                cart.addItem(item);
            }
        }

        // Generate the HTML page
        StringBuilder html = new StringBuilder();
        html.append("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Shopping Cart</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
                </head>
                <body>
                    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                        <a class="navbar-brand" href="/">Home Appliance Store</a>
                    </nav>
                    <div class="container mt-4">
                        <h2>Your Shopping Cart</h2>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Item</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                """);

        for (ShoppingCartItem item : cart.getItems()) {
            double itemTotal = item.getPrice(); // Update as per quantity if needed
            html.append(String.format("""
                    <tr>
                        <td>%s</td>
                        <td>£%.2f</td>
                        <td>%d</td>
                        <td>£%.2f</td>
                    </tr>
                    """, item.getDesc(), item.getPrice(), 1, itemTotal));
        }

        html.append(String.format("""
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="3">Total</th>
                                    <th>£%.2f</th>
                                </tr>
                            </tfoot>
                        </table>
                        <a href="/checkout" class="btn btn-primary">Proceed to Checkout</a>
                    </div>
                </body>
                </html>
                """, cart.getTotalPrice()));

        // Send the response
        String response = html.toString();
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String title, String message) throws IOException {
        String errorPage = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>%s</title>
                </head>
                <body>
                    <h1>%s</h1>
                    <p>%s</p>
                </body>
                </html>
                """, title, title, message);
        exchange.sendResponseHeaders(statusCode, errorPage.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(errorPage.getBytes());
        }
    }
}
