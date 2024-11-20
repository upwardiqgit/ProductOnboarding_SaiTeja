import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;

public class SaveProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("product-name");
        String status = request.getParameter("status");
        String category = request.getParameter("category"); // Could be multiple values
        String description = request.getParameter("description");

        try (Connection connection = DBUtil.getConnection()) {
            String query = "INSERT INTO PRODUCTS (NAME, STATUS, CATEGORY, DESCRIPTION) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, status);
                stmt.setString(3, category); // Can be treated as comma-separated for multiple categories
                stmt.setString(4, description);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"message\": \"Product saved successfully!\"}");
                }
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving product");
        }
    }
}

