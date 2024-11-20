import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class ProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT COUNT(*) FROM PRODUCTS WHERE NAME = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, productName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"isValid\": true}");
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"isValid\": false}");
                }
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
