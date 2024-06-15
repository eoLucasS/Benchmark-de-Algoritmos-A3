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
     * @param id         O identificador do jogo.
     * @param nome       O nome do jogo.
     * @param categoria  A categoria do jogo.
     * @param lancamento O ano de lançamento do jogo.
     */
    public Game(int id, String nome, String categoria, int lancamento) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.lancamento = lancamento;
    }

    /**
     * Obtém o identificador do jogo.
     * 
     * @return O identificador do jogo.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do jogo.
     * 
     * @param id O identificador do jogo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome do jogo.
     * 
     * @return O nome do jogo.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do jogo.
     * 
     * @param nome O nome do jogo.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a categoria do jogo.
     * 
     * @return A categoria do jogo.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do jogo.
     * 
     * @param categoria A categoria do jogo.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtém o ano de lançamento do jogo.
     * 
     * @return O ano de lançamento do jogo.
     */
    public int getLancamento() {
        return lancamento;
    }

    /**
     * Define o ano de lançamento do jogo.
     * 
     * @param lancamento O ano de lançamento do jogo.
     */
    public void setLancamento(int lancamento) {
        this.lancamento = lancamento;
    }

}