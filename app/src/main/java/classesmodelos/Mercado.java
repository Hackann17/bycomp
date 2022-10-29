package classesmodelos;

public class Mercado {
    private String nome;
    private String cnpj;
    private String bairro;
    private String rua;
    private String uf;
    private String numero;
    private int avaliacao;

    public Mercado(String nome, String cnpj, String bairro, String rua, String uf, String numero, int avaliacao) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.bairro = bairro;
        this.rua = rua;
        this.uf = uf;
        this.numero = numero;
        this.avaliacao = avaliacao;
    }


    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public String getUf() {
        return uf;
    }

    public String numero() {
        return numero;
    }

    public int avaliacao() {
        return avaliacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
