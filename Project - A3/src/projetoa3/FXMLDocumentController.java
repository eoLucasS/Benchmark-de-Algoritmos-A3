package projetoa3;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class FXMLDocumentController implements Initializable {

    @FXML
    private TableView<Game> tb_bd;
    @FXML
    private TableColumn<Game, Integer> col_id_bd;
    @FXML
    private TableColumn<Game, String> col_nome_bd;
    @FXML
    private TableColumn<Game, String> col_cat_bd;
    @FXML
    private TableColumn<Game, Integer> col_lan_bd;
    @FXML
    private TableView<Game> tb_alg_manual;
    @FXML
    private TableColumn<Game, Integer> col_id_alg;
    @FXML
    private TableColumn<Game, String> col_nome_alg;
    @FXML
    private TableColumn<Game, String> col_cat_alg;
    @FXML
    private TableColumn<Game, Integer> col_lan_alg;
    @FXML
    private TextField tf_buscar;
    @FXML
    private TextField tf_ciclo_alg;
    @FXML
    private TextField tf_ciclo_bd;
    @FXML
    private RadioButton tg_b_linear;
    @FXML
    private RadioButton tg_b_binaria;
    @FXML
    private ToggleGroup toogle_busca;

    private ObservableList<Game> gameData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa as colunas da tabela tb_bd
        col_id_bd.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nome_bd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cat_bd.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        col_lan_bd.setCellValueFactory(new PropertyValueFactory<>("lancamento"));

        // Inicializa as colunas da tabela tb_alg_manual
        col_id_alg.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nome_alg.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cat_alg.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        col_lan_alg.setCellValueFactory(new PropertyValueFactory<>("lancamento"));

        // Carrega os dados do banco de dados
        loadDataFromDatabase();

        // Carrega os dados do arquivo CSV para a tabela tb_alg_manual
        loadCSVData();
    }

    private void loadCSVData() {
        ObservableList<Game> csvGameData = FXCollections.observableArrayList();
        List<Game> csvGames = CSVReader.readGamesFromCSV("C:\\Users\\Nycolas Garcia\\Documents\\NetBeansProjects\\ProjetoA3\\src\\data\\games.csv");
        csvGameData.addAll(csvGames);
        tb_alg_manual.setItems(csvGameData);
    }

    private void loadDataFromDatabase() {
        gameData = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM games")) {

            System.out.println("Query executed");
            while (rs.next()) {
                Game game = new Game(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getInt("lancamento")
                );
                System.out.println("Game added: " + game.getNome());
                gameData.add(game);
            }

            tb_bd.setItems(gameData);
            System.out.println("Data loaded into table tb_bd. Number of items: " + gameData.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuscar(MouseEvent event) {
        String nome = tf_buscar.getText();
        if (tg_b_linear.isSelected()) {
            searchLinear(nome);
        } else if (tg_b_binaria.isSelected()) {
            // Implemente a busca binária
        }
    }

    @FXML
    private void handleRecarregar(MouseEvent event) {
        // Recarrega os dados do CSV
        loadCSVData();
        // Recarrega os dados do banco de dados
        loadDataFromDatabase();
        // Limpar campos de ciclos
        tf_ciclo_alg.setText("");
        tf_ciclo_bd.setText("");
    }

    private void searchLinear(String nome) {
        int[] ciclosAlg = new int[1];
        Game result = AlgSearchLinear.search(tb_alg_manual.getItems(), nome, ciclosAlg);
        tf_ciclo_alg.setText(String.valueOf(ciclosAlg[0]));

        // Mostrar o resultado na tabela tb_alg_manual
        if (result != null) {
            ObservableList<Game> resultData = FXCollections.observableArrayList(result);
            tb_alg_manual.setItems(resultData);
        } else {
            tb_alg_manual.setItems(FXCollections.observableArrayList());
        }

        // Busca no banco de dados
        searchLinearInDatabase(nome);
    }

    private void searchLinearInDatabase(String nome) {
        int ciclosSQL = 0;
        ObservableList<Game> resultData = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM games WHERE nome = '" + nome + "'")) {

            while (rs.next()) {
                ciclosSQL++;
                Game game = new Game(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getInt("lancamento")
                );
                resultData.add(game);
            }

            tb_bd.setItems(resultData);
            tf_ciclo_bd.setText(String.valueOf(ciclosSQL));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
