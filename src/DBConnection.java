import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

    private final String URL = "jdbc:mysql://localhost:3306/todo_db";
    private final String USER = "root";
    private final String PASS = "root";

    protected static Connection conn;

    public DBConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection is active");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            return conn.prepareStatement(sql);
        } else {
            throw new SQLException("Connection is not active");
        }
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
            System.out.println("Connection closed");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
