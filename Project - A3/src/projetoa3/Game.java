package projetoa3;

/**
 * Classe Game para representar as informações de um jogo.
 */
public class Game {
    private int id;
    private String nome;
    private String categoria;
    private int lancamento;

    /**
     * Construtor da classe Game.
     * 
     * @param id O identificador do jogo.
     * @param nome O nome do jogo.
     * @param categoria A categoria do jogo.
     * @param lancamento O ano de lançamento do jogo.
     */
    public Game(int id, String nome, String categoria, int lancamento) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.lancamento = lancamento;
    }

    // Getters e setters para ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters e setters para Nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getters e setters para Categoria
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Getters e setters para Lançamento
    public int getLancamento() {
        return lancamento;
    }

    public void setLancamento(int lancamento) {
        this.lancamento = lancamento;
    }

}
