package classesmodelos;

public class Produto {
    private String nome;
    private String cnpj;
    private String codigo;
    private  String preco;
    private String unidade;

    public Produto(String nome, String cnpj, String codigo, String preco, String unidade) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.codigo = codigo;
        this.preco = preco;
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public String getcnpj() {
        return cnpj;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getPreco() {
        return preco;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setUnidade(String unidade) {this.unidade = unidade;}


}
