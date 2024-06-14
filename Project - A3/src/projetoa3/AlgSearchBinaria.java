package projetoa3;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;

public class AlgSearchBinaria {

    public static void searchAndUpdateUI(ObservableList<Game> list, String target, TableView<Game> tableView, TextField cycleTextField) {
        int[] ciclos = new int[1];
        int index = binarySearch(list, target, ciclos);
        cycleTextField.setText(String.valueOf(ciclos[0]));
        if (index != -1) {
            Game foundGame = list.get(index);
            tableView.setItems(FXCollections.observableArrayList(foundGame));  // Atualiza a tabela apenas com o jogo encontrado
            tableView.getSelectionModel().select(0);
        } else {
            tableView.setItems(FXCollections.observableArrayList());  // Limpa a tabela se nenhum resultado for encontrado
        }
    }
    
    private static int binarySearch(ObservableList<Game> list, String target, int[] ciclos) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Game midVal = list.get(mid);
            int cmp = midVal.getNome().compareToIgnoreCase(target);

            ciclos[0]++; // Conta cada comparação

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // chave encontrada
            }
        }
        return -1; // chave não encontrada
    }

    public static void searchDatabaseAndUpdateUI(String nome, TableView<Game> tableView, TextField cycleTextField, Connection conn) {
        int[] ciclosSQL = new int[1];
        ObservableList<Game> resultData = searchDatabase(nome, conn, ciclosSQL);
        cycleTextField.setText(String.valueOf(ciclosSQL[0]));
        tableView.setItems(resultData);
    }
    
    private static ObservableList<Game> searchDatabase(String nome, Connection conn, int[] ciclos) 
    {
        ObservableList<Game> resultData = FXCollections.observableArrayList();
        ciclos[0] = 0;
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM games WHERE nome = '" + nome + "'")) {
            while (rs.next()) 
            {
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
