import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:uncle_mario.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found.");
            return;
        }

        String sql =
                "CREATE TABLE IF NOT EXISTS customers ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "username TEXT UNIQUE NOT NULL COLLATE NOCASE, "
                        + "password_hash TEXT NOT NULL, "
                        + "password_salt TEXT NOT NULL, "
                        + "points INTEGER NOT NULL DEFAULT 0"
                        + ");";

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

            if (!columnExists(connection, "student_email")) {
                statement.execute(
                        "ALTER TABLE customers "
                                + "ADD COLUMN student_email TEXT;");
            }

            if (!columnExists(connection, "student_verified")) {
                statement.execute(
                        "ALTER TABLE customers "
                                + "ADD COLUMN student_verified INTEGER "
                                + "NOT NULL DEFAULT 0;");
            }

            System.out.println("Database connected successfully.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static boolean columnExists(
            Connection connection,
            String columnName) throws SQLException {

        try (Statement statement = connection.createStatement();
             ResultSet result =
                     statement.executeQuery("PRAGMA table_info(customers);")) {

            while (result.next()) {
                if (columnName.equalsIgnoreCase(
                        result.getString("name"))) {
                    return true;
                }
            }
        }

        return false;
    }
}