package projetoa3;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AlgSearchLinear {

    public static void searchAndUpdateUI(List<Game> list, String nome, TableView<Game> tableView, TextField cycleTextField) {
        int[] ciclos = new int[1];
        Game result = search(list, nome, ciclos);
        cycleTextField.setText(String.valueOf(ciclos[0]));
        
        ObservableList<Game> resultData = result != null ? FXCollections.observableArrayList(result) : FXCollections.observableArrayList();
        tableView.setItems(resultData);
    }

    private static Game search(List<Game> list, String nome, int[] ciclos) {
        ciclos[0] = 0;
        for (Game game : list) {
            ciclos[0]++;
            if (game.getNome().equalsIgnoreCase(nome)) {
                return game;
            }
        }
        return null;
    }

    public static void searchDatabaseAndUpdateUI(String nome, TableView<Game> tableView, TextField cycleTextField, Connection conn) {
        int[] ciclosSQL = new int[1];
        ObservableList<Game> resultData = searchDatabase(nome, conn, ciclosSQL);
        cycleTextField.setText(String.valueOf(ciclosSQL[0]));
        tableView.setItems(resultData);
    }

    private static ObservableList<Game> searchDatabase(String nome, Connection conn, int[] ciclos) {
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
