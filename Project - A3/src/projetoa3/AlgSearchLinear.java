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

/**
 * Classe responsável pela busca linear e busca no banco de dados para atualizar a interface do usuário.
 */
public class AlgSearchLinear {

    /**
     * Realiza uma busca linear em uma lista e atualiza a interface do usuário.
     *
     * @param list           A lista de jogos.
     * @param nome           O nome do jogo a ser buscado.
     * @param tableView      A tabela a ser atualizada com o resultado da busca.
     * @param cycleTextField Campo de texto para exibir o número de ciclos da busca.
     */
    public static void searchAndUpdateUI(List<Game> list, String nome, TableView<Game> tableView, TextField cycleTextField) {
        int[] ciclos = new int[1];
        Game result = search(list, nome, ciclos);
        cycleTextField.setText(String.valueOf(ciclos[0]));
        
        // Atualiza a tabela com o jogo encontrado ou limpa a tabela se nenhum jogo foi encontrado
        ObservableList<Game> resultData = result != null ? FXCollections.observableArrayList(result) : FXCollections.observableArrayList();
        tableView.setItems(resultData);
    }

    /**
     * Realiza uma busca linear em uma lista de jogos.
     *
     * @param list   A lista de jogos.
     * @param nome   O nome do jogo a ser buscado.
     * @param ciclos Array para armazenar o número de ciclos da busca.
     * @return O jogo encontrado ou null se não encontrado.
     */
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