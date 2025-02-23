import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBLogic extends DBConnection {

    public DBLogic() {
        super();
    }

    public void addTask(String task) {
        String sql = "INSERT INTO tasks (task_name) VALUES (?)";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, task);
            pstmt.executeUpdate();

            System.out.println("Task created: " + task);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> getTasks() {
        List<String> tasks = new ArrayList<>();

        String sql = "SELECT task_name FROM tasks";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
                ) {


            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tasks.add(rs.getString("task_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  tasks;
    }
    public void deleteTask(String task) {
        String sql = "DELETE FROM tasks WHERE task = ?"; // Пример SQL-запроса
        try (PreparedStatement statement = DBConnection.prepareStatement(sql)) {
            statement.setString(1, task);
            statement.executeUpdate(); // Выполняем запрос на удаление
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
