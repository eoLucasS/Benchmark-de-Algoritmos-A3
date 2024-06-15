package projetoa3;

import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Classe responsável pela ordenação usando Quick Sort para atualizar a interface do usuário.
 */
public class AlgSortQuick {

    /**
     * Realiza a ordenação Quick Sort em uma lista observável e atualiza a interface do usuário.
     *
     * @param list           A lista observável de jogos a ser ordenada.
     * @param type           O tipo de ordenação (0 para ID, 1 para Nome, 2 para Categoria, 3 para Lançamento).
     * @param ciclos         Array para armazenar o número de ciclos da ordenação.
     * @param tableView      A tabela a ser atualizada com o resultado da ordenação.
     * @param cycleTextField Campo de texto para exibir o número de ciclos da ordenação.
     */
    public static void sort(ObservableList<Game> list, int type, int[] ciclos, TableView<Game> tableView, TextField cycleTextField) {
        ciclos[0] = 0;  // Resetando a contagem de ciclos
        quickSort(list, 0, list.size() - 1, type, ciclos);
        // Atualiza a interface do usuário no thread principal do JavaFX
        Platform.runLater(() -> {
            tableView.refresh();
            cycleTextField.setText(String.valueOf(ciclos[0]));
        });
    }

    /**
     * Método recursivo do Quick Sort.
     *
     * @param list   A lista de jogos a ser ordenada.
     * @param low    O índice inicial.
     * @param high   O índice final.
     * @param type   O tipo de ordenação.
     * @param ciclos Array para armazenar o número de ciclos da ordenação.
     */
    private static void quickSort(ObservableList<Game> list, int low, int high, int type, int[] ciclos) {
        if (low < high) {
            int pi = partition(list, low, high, type, ciclos);
            quickSort(list, low, pi - 1, type, ciclos);
            quickSort(list, pi + 1, high, type, ciclos);
        }
    }

    /**
     * Particiona a lista para o Quick Sort.
     *
     * @param list   A lista de jogos a ser ordenada.
     * @param low    O índice inicial.
     * @param high   O índice final.
     * @param type   O tipo de ordenação.
     * @param ciclos Array para armazenar o número de ciclos da ordenação.
     * @return O índice do pivot.
     */
    private static int partition(ObservableList<Game> list, int low, int high, int type, int[] ciclos) {
        Game pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // Verifica se deve trocar os elementos
            if (shouldSwap(list.get(j), pivot, type)) {
                i++;
                Game temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
            ciclos[0]++;  // Incrementa a contagem a cada comparação
        }
        Game temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
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
            case 0: return a.getId() < b.getId();
            case 1: return a.getNome().compareToIgnoreCase(b.getNome()) < 0;
            case 2: return a.getCategoria().compareToIgnoreCase(b.getCategoria()) < 0;
            case 3: return a.getLancamento() < b.getLancamento();
            default: return false;
        }
    }
}