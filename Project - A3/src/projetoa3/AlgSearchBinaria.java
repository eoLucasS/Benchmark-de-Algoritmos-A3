package projetoa3;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;

/**
 * Classe responsável pela busca binária e busca no banco de dados para atualizar a interface do usuário.
 */
public class AlgSearchBinaria {

    /**
     * Realiza uma busca binária em uma lista observável e atualiza a interface do usuário.
     *
     * @param list           A lista observável de jogos.
     * @param target         O nome do jogo a ser buscado.
     * @param tableView      A tabela a ser atualizada com o resultado da busca.
     * @param cycleTextField Campo de texto para exibir o número de ciclos da busca.
     */
    public static void searchAndUpdateUI(ObservableList<Game> list, String target, TableView<Game> tableView, TextField cycleTextField) {
        int[] ciclos = new int[1];
        int index = binarySearch(list, target, ciclos);
        cycleTextField.setText(String.valueOf(ciclos[0]));
        if (index != -1) {
            Game foundGame = list.get(index);
            // Atualiza a tabela apenas com o jogo encontrado
            tableView.setItems(FXCollections.observableArrayList(foundGame));
            tableView.getSelectionModel().select(0);
        } else {
            // Limpa a tabela se nenhum resultado for encontrado
            tableView.setItems(FXCollections.observableArrayList());
        }
    }
    
    /**
     * Realiza uma busca binária em uma lista observável.
     *
     * @param list   A lista observável de jogos.
     * @param target O nome do jogo a ser buscado.
     * @param ciclos Array para armazenar o número de ciclos da busca.
     * @return O índice do jogo encontrado ou -1 se não encontrado.
     */
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
                return mid; // Chave encontrada
            }
        }
        return -1; // Chave não encontrada
    }

    /**
     * Realiza uma busca no banco de dados e atualiza a interface do usuário.
     *
     * @param nome           O nome do jogo a ser buscado.
     * @param tableView      A tabela a ser atualizada com o resultado da busca.
     * @param cycleTextField Campo de texto para exibir o número de ciclos da busca.
     * @param conn           A conexão com o banco de dados.
     */
    public static void searchDatabaseAndUpdateUI(String nome, TableView<Game> tableView, TextField cycleTextField, Connection conn) {
        int[] ciclosSQL = new int[1];
        ObservableList<Game> resultData = searchDatabase(nome, conn, ciclosSQL);
        cycleTextField.setText(String.valueOf(ciclosSQL[0]));
        tableView.setItems(resultData);
    }
    
    /**
     * Realiza uma busca no banco de dados pelo nome do jogo.
     *
     * @param nome   O nome do jogo a ser buscado.
     * @param conn   A conexão com o banco de dados.
     * @param ciclos Array para armazenar o número de ciclos da busca.
     * @return Uma lista observável de jogos que correspondem ao critério de busca.
     */
    private static ObservableList<Game> searchDatabase(String nome, Connection conn, int[] ciclos) {
        ObservableList<Game> resultData = FXCollections.observableArrayList();
        ciclos[0] = 0;
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM games WHERE nome = '" + nome + "'")) {
            while (rs.next()) {
                ciclos[0]++;
                // Cria um novo objeto Game para cada registro encontrado
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