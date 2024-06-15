/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package projetoa3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal do projeto, responsável por iniciar a aplicação JavaFX.
 */
public class ProjetoA3 extends Application {

    /**
     * Método start, chamado ao iniciar a aplicação.
     *
     * @param stage O palco principal da aplicação.
     * @throws Exception Se ocorrer um erro ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Carrega o arquivo FXML e define a cena da aplicação
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();  // Exibe o palco
    }

    /**
     * Método principal da aplicação, que lança a aplicação JavaFX.
     *
     * @param args Os argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);  // Lança a aplicação JavaFX
    }
}