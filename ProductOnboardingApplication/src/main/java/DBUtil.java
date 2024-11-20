
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private static final String USER = "your_db_username";
    private static final String PASSWORD = "your_db_password";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle JDBC Driver not found", e);
        }
    }
}
