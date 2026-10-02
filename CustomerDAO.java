import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {

    public static boolean usernameExists(String username) {
        String sql = "SELECT id FROM customers WHERE username = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            return result.next();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    public static Customer createAccount(
            String username,
            String password) {

        if (usernameExists(username)) {
            System.out.println("That username already exists.");
            return null;
        }

        String salt = PasswordUtils.generateSalt();
        String passwordHash =
                PasswordUtils.hashPassword(password, salt);

        String sql =
                "INSERT INTO customers "
                        + "(username, password_hash, password_salt, "
                        + "points, student_email, student_verified) "
                        + "VALUES (?, ?, ?, 0, NULL, 0)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, salt);
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                int id = keys.getInt(1);
                return new Customer(id, username, 0, null, false);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not create account: " + e.getMessage());
        }

        return null;
    }

    public static Customer login(
            String username,
            String password) {

        String sql =
                "SELECT id, username, password_hash, password_salt, "
                        + "points, student_email, student_verified "
                        + "FROM customers WHERE username = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String storedHash =
                        result.getString("password_hash");

                String storedSalt =
                        result.getString("password_salt");

                boolean correctPassword =
                        PasswordUtils.verifyPassword(
                                password,
                                storedHash,
                                storedSalt);

                if (correctPassword) {
                    int id = result.getInt("id");
                    String savedUsername =
                            result.getString("username");
                    int points = result.getInt("points");
                    String studentEmail =
                            result.getString("student_email");

                    boolean studentVerified =
                            result.getInt("student_verified") == 1;

                    return new Customer(
                            id,
                            savedUsername,
                            points,
                            studentEmail,
                            studentVerified);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Login database error: " + e.getMessage());
        }

        return null;
    }

    public static void updatePoints(Customer customer) {
        String sql =
                "UPDATE customers SET points = ? WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customer.getPoints());
            statement.setInt(2, customer.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(
                    "Could not save points: " + e.getMessage());
        }
    }

    public static boolean verifyStudent(
            Customer customer,
            String email) {

        String sql =
                "UPDATE customers "
                        + "SET student_email = ?, student_verified = 1 "
                        + "WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setInt(2, customer.getId());

            if (statement.executeUpdate() > 0) {
                customer.setStudentEmail(email);
                customer.setStudentVerified(true);
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not save student verification: "
                            + e.getMessage());
        }

        return false;
    }
}