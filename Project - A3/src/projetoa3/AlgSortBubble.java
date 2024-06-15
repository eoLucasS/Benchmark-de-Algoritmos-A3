package projetoa3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Classe responsável pela ordenação usando Bubble Sort e ordenação no banco de dados para atualizar a interface do usuário.
 */
public class AlgSortBubble {

    /**
     * Método genérico para Bubble Sort atualizado para aceitar contagem de ciclos.
     *
     * @param list   A lista observável de jogos a ser ordenada.
     * @param type   O tipo de ordenação (0 para ID, 1 para Nome, 2 para Categoria, 3 para Lançamento).
     * @param ciclos Array para armazenar o número de ciclos da ordenação.
     */
    public static void sort(ObservableList<Game> list, int type, int[] ciclos) {
        boolean swapped;
        int n = list.size();
        ciclos[0] = 0;  // Resetando a contagem de ciclos

        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                // Verifica se deve trocar os elementos
                if (shouldSwap(list.get(i - 1), list.get(i), type)) {
                    Game temp = list.get(i - 1);
                    list.set(i - 1, list.get(i));
                    list.set(i, temp);
                    swapped = true;
                }
                ciclos[0]++;  // Incrementa a contagem a cada comparação
            }
            n = n - 1;
        } while (swapped);
    }

    /**
     * Método auxiliar para decidir se dois elementos devem ser trocados.
     *
     * @param a    O primeiro jogo.
     * @param b    O segundo jogo.
     * @param type O tipo de ordenação.
     * @return true se os elementos devem ser trocados, false caso contrário.
     */
    private static boolean shouldSwap(Game a, Game b, int type) {
        switch (type) {
            case 0: // ID
                return a.getId() > b.getId();
            case 1: // Nome
                return a.getNome().compareToIgnoreCase(b.getNome()) > 0;
            case 2: // Categoria
                return a.getCategoria().compareToIgnoreCase(b.getCategoria()) > 0;
            case 3: // Lançamento
                return a.getLancamento() > b.getLancamento();
            default:
                return false;
        }
    }

    /**
     * Realiza uma ordenação no banco de dados e atualiza a interface do usuário.
     *
     * @param orderBy        O campo pelo qual a ordenação será feita.
     * @param tableView      A tabela a ser atualizada com o resultado da ordenação.
     * @param cycleTextField Campo de texto para exibir o número de ciclos da ordenação.
     * @param conn           A conexão com o banco de dados.
     */
    public static void sortDatabaseAndUpdateUI(String orderBy, TableView<Game> tableView, TextField cycleTextField, Connection conn) {
        int[] ciclosSQL = new int[1];
        ObservableList<Game> resultData = sortDatabase(orderBy, conn, ciclosSQL);
        cycleTextField.setText(String.valueOf(ciclosSQL[0]));
        tableView.setItems(resultData);
    }

    /**
     * Realiza uma ordenação no banco de dados.
     *
     * @param sortBy O campo pelo qual a ordenação será feita.
     * @param conn   A conexão com o banco de dados.
     * @param ciclos Array para armazenar o número de ciclos da ordenação.
     * @return Uma lista observável de jogos ordenados.
     */
    private static ObservableList<Game> sortDatabase(String sortBy, Connection conn, int[] ciclos) {
        ObservableList<Game> resultData = FXCollections.observableArrayList();
        String query = String.format("SELECT * FROM games ORDER BY %s", sortBy);
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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