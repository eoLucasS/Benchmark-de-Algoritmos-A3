package projetoa3;

//importação de bibliotecas necessárias
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

public class FXMLDocumentController implements Initializable 
{
    //declaração de variáveis do front end
    
    //declaração da tabela tb_db
    @FXML private TableView<Game> tb_bd;
    @FXML private TableColumn<Game, Integer> col_id_bd;
    @FXML private TableColumn<Game, String> col_nome_bd;
    @FXML private TableColumn<Game, String> col_cat_bd;
    @FXML private TableColumn<Game, Integer> col_lan_bd;
    
    //declaração da tabela tb_alg_manual
    @FXML private TableView<Game> tb_alg_manual;
    @FXML private TableColumn<Game, Integer> col_id_alg;
    @FXML private TableColumn<Game, String> col_nome_alg;
    @FXML private TableColumn<Game, String> col_cat_alg;
    @FXML private TableColumn<Game, Integer> col_lan_alg;
    
    //declaração dos campos de texto para tf_buscar, tf_ciclos_alg/bd e tf_temp_alg/bd
    @FXML private TextField tf_buscar;
    @FXML private TextField tf_ciclo_alg;
    @FXML private TextField tf_ciclo_bd;
    @FXML private TextField tf_temp_alg;
    @FXML private TextField tf_temp_bd;
    
    //declaração dos botões radiais para busca tg_b_linear e tg_b_binary, além de
    //declaração dos botões radiais para organização tb_o_bubble e tb_o_quick
    @FXML private RadioButton tg_b_linear;
    @FXML private RadioButton tg_b_binaria;
    @FXML private RadioButton tg_o_bubble;
    @FXML private RadioButton tg_o_quick;
    
    //declaração do Objeto 
    @FXML private ObservableList<Game> gameData;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicializa a Tabela tb_bd
        col_id_bd.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nome_bd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cat_bd.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        col_lan_bd.setCellValueFactory(new PropertyValueFactory<>("lancamento"));
        
        //Inicializa a Tabela tb_alg_manual
        col_id_alg.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nome_alg.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_cat_alg.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        col_lan_alg.setCellValueFactory(new PropertyValueFactory<>("lancamento"));
        loadDataFromDatabase();
        loadCSVData();
    }
    
    //Método que Lê o arquivo games.csv
    private void loadCSVData() {
        ObservableList<Game> csvGameData = FXCollections.observableArrayList();
        List<Game> csvGames = readGamesFromCSV("C:\\Users\\Nycolas Garcia\\Documents\\NetBeansProjects\\ProjetoA3\\src\\data\\games.csv");
        csvGameData.addAll(csvGames);
        tb_alg_manual.setItems(csvGameData);
    }
    
    //Método que insere os dados do arquivo games.csv na tabela tb_alg_manual
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

    //Método que realiza a conexão SQL
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/games_db";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
    
    //Método que insere os dados do banco de dados na tabela tb_bd
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
    
    //Implementação das Funcionalidades de cada botão
    @FXML
    private void handleBuscar(MouseEvent event) {
        loadCSVData();
        loadDataFromDatabase();
        tf_ciclo_alg.setText("");
        tf_ciclo_bd.setText("");

        String nome = tf_buscar.getText();
        if (tg_b_linear.isSelected()) {
            searchLinear(nome);
        } else if (tg_b_binaria.isSelected()) {
            // Implemente a busca binária
        }
    }

    @FXML
    private void handleRecarregar(MouseEvent event) {
        loadCSVData();
        loadDataFromDatabase();
        tf_ciclo_alg.setText("");
        tf_ciclo_bd.setText("");
    }

    
    // Implementação da chamada dos Métodos de Busca MANUAIS do Back-End para as tabelas do Front-End
    private void searchLinear(String nome) {
        int[] ciclosAlg = new int[1];
        Game result = AlgSearchLinear.search(tb_alg_manual.getItems(), nome, ciclosAlg);
        tf_ciclo_alg.setText(String.valueOf(ciclosAlg[0]));

        if (result != null) {
            ObservableList<Game> resultData = FXCollections.observableArrayList(result);
            tb_alg_manual.setItems(resultData);
        } else {
            tb_alg_manual.setItems(FXCollections.observableArrayList());
        }

        searchLinearInDatabase(nome);
    }
    
    //Implementação da chamada dos Métodos de Busca SQL do Back-End para as tabelas do Front-End
    private void searchLinearInDatabase(String nome) {
        int[] ciclosSQL = new int[1];
        ObservableList<Game> resultData = searchDatabase(nome, ciclosSQL);
        tf_ciclo_bd.setText(String.valueOf(ciclosSQL[0]));
        tb_bd.setItems(resultData);
    }
    
    private ObservableList<Game> searchDatabase(String nome, int[] ciclos) {
        ObservableList<Game> resultData = FXCollections.observableArrayList();
        ciclos[0] = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
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
