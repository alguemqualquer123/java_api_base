package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Table;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void createUsersTable() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexão estabelecida com sucesso!");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                    + "id SERIAL PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "age INT"
                    + ")";

            try (Statement statement = connection.createStatement()) {
                statement.execute(createTableSQL);
                System.out.println("Tabela 'users' criada com sucesso!");
                connection.close();
                System.out.println("Conexão foi fechada!");

            } catch (SQLException e) {
                System.err.println("Erro ao criar a tabela: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
    }

    public static Map<String, Object> updateUser(String name, int age) {
        Map<String, Object> userTable = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement SelectScheme = connection.prepareStatement("SELECT * FROM users  WHERE name = ?");
            SelectScheme.setString(1, name);
            ResultSet rs = SelectScheme.executeQuery();

            while (rs.next()) {
                userTable.put("name", rs.getString("name"));
                userTable.put("age", rs.getInt("age"));
                userTable.put("existent", true);
                userTable.put("insert", "selecionado no banco de dados");
                return userTable;
            }

            PreparedStatement Scheme = connection.prepareStatement("INSERT INTO users(name, age) VALUES (?, ?)");

            Scheme.setString(1, name);
            Scheme.setInt(2, age);
            try (Statement statement = connection.createStatement()) {
                Scheme.executeUpdate();
                ResultSet ss = SelectScheme.executeQuery();
                System.out.println("Dados inserido na tabela 'users' !");
                while (ss.next()) {
                    userTable.put("name", ss.getString("name"));
                    userTable.put("age", ss.getInt("age"));
                    userTable.put("insert", "inserido no banco de dados");
                    userTable.put("existent", false);
                }

                connection.close();
                return userTable;

            } catch (SQLException e) {
                System.err.println("Erro ao criar a tabela: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
        return userTable;
    }


    public static ArrayList<Map<String, Object>> getUserss() {
        ArrayList<Map<String, Object>> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement SelectScheme = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = SelectScheme.executeQuery();
            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("name", rs.getString("name"));
                user.put("age", rs.getInt("age"));
                users.add(user);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
        return users;
    }
}
