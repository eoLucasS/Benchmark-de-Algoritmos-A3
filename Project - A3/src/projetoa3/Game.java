package projetoa3;

public class Game {
    private int id;
    private String nome;
    private String categoria;
    private int lancamento;

    public Game(int id, String nome, String categoria, int lancamento) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.lancamento = lancamento;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getLancamento() { return lancamento; }
    public void setLancamento(int lancamento) { this.lancamento = lancamento; }
}
