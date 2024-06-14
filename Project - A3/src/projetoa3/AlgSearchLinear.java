package projetoa3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class AlgSearchLinear {

    public static Game search(List<Game> list, String nome, int[] ciclos) {
        ciclos[0] = 0;
        for (Game game : list) {
            ciclos[0]++;
            if (game.getNome().equalsIgnoreCase(nome)) {
                return game;
            }
        }
        return null;
    }

    public static ObservableList<Game> searchDatabase(String nome, Connection conn, int[] ciclos) {
        ObservableList<Game> resultData = FXCollections.observableArrayList();
        ciclos[0] = 0;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM games WHERE nome = '" + nome + "'")) {

            while (rs.next()) {
                ciclos[0]++;
                Game game = new Game(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getInt("lancamento")
                );
                resultData.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultData;
    }
}