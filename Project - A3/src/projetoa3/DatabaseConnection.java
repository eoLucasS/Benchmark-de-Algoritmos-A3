package projetoa3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/games_db";
    private static final String USER = "root"; // Usuário padrão do MySQL
    private static final String PASSWORD = ""; // Senha padrão do MySQL (deixe vazio se não houver senha)

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Certifique-se de que o driver está correto
            System.out.println("Driver found");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver not found", e);
        }
    }
}
