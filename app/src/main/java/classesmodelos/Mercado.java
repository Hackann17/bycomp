package classesmodelos;

public class Mercado {
    private String nome;
    private String cnpj;
    private String bairro;
    private String rua;
    private String uf;
    private String numero;
    private String avaliacao;


    public Mercado(String nome, String cnpj, String bairro, String rua, String uf, String numero, String avaliacao) {
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

    public String getNumero() {
        return numero;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpnj(String cnpj) {
        this.nome = nome;
    }

    public void setBairro(String bairro) {
        this.nome = nome;
    }

    public void setRua(String rua) {
        this.nome = nome;
    }

    public void setUf(String uf) {
        this.nome = nome;
    }

    public void setNumero(String numero) {
        this.nome = nome;
    }

    public void setAvaliacao(double avaliacao) {
        this.nome = nome;
    }

    public Mercado encontrarMercado(String cnpj){

        return null;
    }

}
