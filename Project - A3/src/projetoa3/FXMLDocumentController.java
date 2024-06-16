package projetoa3;

// Importação de bibliotecas necessárias
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Controlador para o documento FXML.
 */
public class FXMLDocumentController implements Initializable {

    // Declaração de variáveis do front end

    // Declaração da tabela tb_bd
    @FXML private TableView<Game> tb_bd;
    @FXML private TableColumn<Game, Integer> col_id_bd;
    @FXML private TableColumn<Game, String> col_nome_bd;
    @FXML private TableColumn<Game, String> col_cat_bd;
    @FXML private TableColumn<Game, Integer> col_lan_bd;

    // Declaração da tabela tb_alg_manual
    @FXML private TableView<Game> tb_alg_manual;
    @FXML private TableColumn<Game, Integer> col_id_alg;
    @FXML private TableColumn<Game, String> col_nome_alg;
    @FXML private TableColumn<Game, String> col_cat_alg;
    @FXML private TableColumn<Game, Integer> col_lan_alg;

    // Declaração dos campos de texto para tf_buscar, tf_ciclos_alg/bd
    @FXML private TextField tf_buscar;
    @FXML private TextField tf_ciclo_alg;
    @FXML private TextField tf_ciclo_bd;

    // Declaração dos botões radiais para busca tg_b_linear e tg_b_binary
    // Declaração dos botões radiais para organização tb_o_bubble e tb_o_quick
    @FXML private RadioButton tg_b_linear;
    @FXML private RadioButton tg_b_binary;
    @FXML private RadioButton tg_o_bubble;
    @FXML private RadioButton tg_o_quick;

    // Declaração do Objeto ObservableList para armazenar os dados dos jogos
    @FXML private ObservableList<Game> gameData;

    /**
     * Método inicializador chamado após o carregamento do FXML.
     *
     * @param url URL para localizar o recurso.
     * @param rb  Bundle de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa a Tabela tb_bd
        col_id_bd.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nome_bd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cat_bd.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        col_lan_bd.setCellValueFactory(new PropertyValueFactory<>("lancamento"));

        // Inicializa a Tabela tb_alg_manual
        col_id_alg.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nome_alg.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cat_alg.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        col_lan_alg.setCellValueFactory(new PropertyValueFactory<>("lancamento"));

        // Chama os métodos que carregam os dados nas respectivas tabelas
        loadDataFromDatabase();
        loadCSVData();
    }

    /**
     * Método que lê o arquivo games.csv.
     */
    private void loadCSVData() {
        ObservableList<Game> csvGameData = FXCollections.observableArrayList();
        String path = "./src/data/games.csv";
        List<Game> csvGames = readGamesFromCSV(path);
        csvGameData.addAll(csvGames);
        tb_alg_manual.setItems(csvGameData);
    }

    /**
     * Método que insere os dados do arquivo games.csv na tabela tb_alg_manual.
     *
     * @param filePath Caminho do arquivo CSV.
     * @return Lista de jogos carregados do arquivo CSV.
     */
    private List<Game> readGamesFromCSV(String filePath) {
        List<Game> games = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Game game = new Game(Integer.parseInt(values[0]), values[1], values[2], Integer.parseInt(values[3]));
                games.add(game);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }

    /**
     * Método que realiza a conexão SQL.
     *
     * @return Conexão com o banco de dados.
     * @throws SQLException Se ocorrer um erro ao conectar.
     */
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/games_db";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Método que insere os dados do banco de dados na tabela tb_bd.
     */
    private void loadDataFromDatabase() {
        gameData = FXCollections.observableArrayList();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM games")) {
            while (rs.next()) {
                Game game = new Game(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getInt("lancamento")
                );
                gameData.add(game);
            }
            tb_bd.setItems(gameData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementação das funcionalidades de cada botão.
     *
     * @param event Evento de clique do mouse.
     */
    @FXML
    private void handleRecarregar(MouseEvent event) {
        loadCSVData();
        loadDataFromDatabase();
        tf_ciclo_alg.setText("");
        tf_ciclo_bd.setText("");
    }

    /**
     * Método de busca e atualização das tabelas com base no nome do jogo.
     *
     * @param event Evento de clique do mouse.
     * @throws SQLException Se ocorrer um erro na consulta SQL.
     */
    @FXML
    private void handleBuscar(MouseEvent event) throws SQLException {
        String nome = tf_buscar.getText();
        if (tg_b_linear.isSelected()) {
            AlgSearchLinear.searchAndUpdateUI(tb_alg_manual.getItems(), nome, tb_alg_manual, tf_ciclo_alg);
            AlgSearchLinear.searchDatabaseAndUpdateUI(nome, tb_bd, tf_ciclo_bd, getConnection());
        } else if (tg_b_binary.isSelected()) {
            int[] ciclosAlg = new int[1];
            AlgSortBubble.sort(tb_alg_manual.getItems(), 1, ciclosAlg);
            tf_ciclo_alg.setText(String.valueOf(ciclosAlg[0]));
            AlgSearchBinaria.searchAndUpdateUI(tb_alg_manual.getItems(), nome, tb_alg_manual, tf_ciclo_alg);
            AlgSearchBinaria.searchDatabaseAndUpdateUI(nome, tb_bd, tf_ciclo_bd, getConnection());
        }
    }

    /**
     * Método para ordenar a tabela por ID.
     *
     * @param event Evento de clique do mouse.
     * @throws SQLException Se ocorrer um erro na consulta SQL.
     */
    @FXML
    private void handleOrgId(MouseEvent event) throws SQLException {
        int[] ciclosAlg = new int[1];
        if (tg_o_bubble.isSelected()) {
            AlgSortBubble.sort(tb_alg_manual.getItems(), 0, ciclosAlg);
            tf_ciclo_alg.setText(String.valueOf(ciclosAlg[0]));
            AlgSortBubble.sortDatabaseAndUpdateUI("id", tb_bd, tf_ciclo_bd, getConnection());
        } else if (tg_o_quick.isSelected()) {
            AlgSortQuick.sort(tb_alg_manual.getItems(), 0, ciclosAlg, tb_alg_manual, tf_ciclo_alg);
            AlgSortBubble.sortDatabaseAndUpdateUI("id", tb_bd, tf_ciclo_bd, getConnection());
        }
    }

    /**
     * Método para ordenar a tabela por Nome.
     *
     * @param event Evento de clique do mouse.
     * @throws SQLException Se ocorrer um erro na consulta SQL.
     */
    @FXML
    private void handleOrgNome(MouseEvent event) throws SQLException {
        int[] ciclosAlg = new int[1];
        if (tg_o_bubble.isSelected()) {
            AlgSortBubble.sort(tb_alg_manual.getItems(), 1, ciclosAlg);
            tf_ciclo_alg.setText(String.valueOf(ciclosAlg[0]));
            AlgSortBubble.sortDatabaseAndUpdateUI("nome", tb_bd, tf_ciclo_bd, getConnection());
        } else if (tg_o_quick.isSelected()) {
            AlgSortQuick.sort(tb_alg_manual.getItems(), 1, ciclosAlg, tb_alg_manual, tf_ciclo_alg);
            AlgSortBubble.sortDatabaseAndUpdateUI("nome", tb_bd, tf_ciclo_bd, getConnection());
        }
    }

    /**
     * Método para ordenar a tabela por Categoria.
     *
     * @param event Evento de clique do mouse.
     * @throws SQLException Se ocorrer um erro na consulta SQL.
     */
    @FXML
    private void handleOrgCat(MouseEvent event) throws SQLException {
        int[] ciclosAlg = new int[1];
        if (tg_o_bubble.isSelected()) {
            AlgSortBubble.sort(tb_alg_manual.getItems(), 2, ciclosAlg);
            tf_ciclo_alg.setText(String.valueOf(ciclosAlg[0]));
            AlgSortBubble.sortDatabaseAndUpdateUI("categoria", tb_bd, tf_ciclo_bd, getConnection());
        } else if (tg_o_quick.isSelected()) {
            AlgSortQuick.sort(tb_alg_manual.getItems(), 2, ciclosAlg, tb_alg_manual, tf_ciclo_alg);
            AlgSortBubble.sortDatabaseAndUpdateUI("categoria", tb_bd, tf_ciclo_bd, getConnection());
        }
    }

    /**
     * Método para ordenar a tabela por Lançamento.
     *
     * @param event Evento de clique do mouse.
     * @throws SQLException Se ocorrer um erro na consulta SQL.
     */
    @FXML
    private void handleOrgLan(MouseEvent event) throws SQLException {
        int[] ciclosAlg = new int[1];
        if (tg_o_bubble.isSelected()) {
            AlgSortBubble.sort(tb_alg_manual.getItems(), 3, ciclosAlg);
            tf_ciclo_alg.setText(String.valueOf(ciclosAlg[0]));
            AlgSortBubble.sortDatabaseAndUpdateUI("lancamento", tb_bd, tf_ciclo_bd, getConnection());
        } else if (tg_o_quick.isSelected()) {
            AlgSortQuick.sort(tb_alg_manual.getItems(), 3, ciclosAlg, tb_alg_manual, tf_ciclo_alg);
            AlgSortBubble.sortDatabaseAndUpdateUI("lancamento", tb_bd, tf_ciclo_bd, getConnection());
        }
    }
}