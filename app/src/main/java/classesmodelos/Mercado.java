package classesmodelos;

public class Mercado {
    private String nome;
    private String endereco;
    private double avaliacao;

    public Mercado(String nome, String endereco, double avaliacao) {
        this.nome = nome;
        this.endereco = endereco;
        this.avaliacao = avaliacao;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }
}
