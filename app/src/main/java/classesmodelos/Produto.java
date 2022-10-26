package classesmodelos;

public class Produto {
    private String nome;
    private  Float preco;
    private String dataenvio;


    public Produto(String nome, Float preco, Mercado mercado, Usuario usuario, String dataenvio) {
        this.nome = nome;
        this.preco = preco;
        this.dataenvio = dataenvio;
    }

    public String getNome() {
        return nome;
    }

    public Float getPreco() {
        return preco;
    }

    public String getDataenvio() {
        return dataenvio;
    }




}
