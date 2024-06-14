package projetoa3;

import java.util.List;

public class AlgSearchLinear {

    public static Game search(List<Game> list, String nome, int[] ciclos) {
        ciclos[0] = 0;
        for (Game game : list) {
            ciclos[0]++;
            if (game.getNome().equalsIgnoreCase(nome)) {
                return game;
            }
        }
        return null;
    }
}
