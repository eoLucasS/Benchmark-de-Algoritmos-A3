package projetoa3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<Game> readGamesFromCSV(String fileName) {
        List<Game> games = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        String nome = data[1].trim();
                        String categoria = data[2].trim();
                        int lancamento = Integer.parseInt(data[3].trim());
                        Game game = new Game(id, nome, categoria, lancamento);
                        games.add(game);
                    } catch (NumberFormatException e) {
                        // Se houver um problema de conversão, pule esta linha e continue para a próxima
                        System.err.println("Erro ao converter dados na linha: " + line);
                    }
                }
            }
        } catch (IOException e) {
        }

        return games;
    }
}
