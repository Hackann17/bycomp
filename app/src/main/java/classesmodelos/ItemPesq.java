package classesmodelos;

import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemPesq implements Serializable {

    private String cnpjM;
    private String nomeM;
    private String enderecoM;
    private float precoP;
    private double avaliacaoM;
    private String nomeP;


    public ItemPesq(String cnpjM, String nomeM, String enderecoM, double avaliacaoM, String nomeP, float precoP) {
        this.cnpjM = cnpjM;
        this.nomeM = nomeM;
        this.enderecoM = enderecoM;
        this.avaliacaoM = avaliacaoM;
        this.nomeP = nomeP;
        this.precoP = precoP;
    }

    public String getCnpjM() { return  cnpjM; }

    public String getNomeM() {
        return nomeM;
    }

    public String getEnderecoM() {
        return enderecoM;
    }

    public double getAvaliacaoM() {
        return avaliacaoM;
    }

    public String getNomeP() {
        return nomeP;
    }

    public float getPrecoP() {
        return precoP;
    }

    public void setNomeM(String nomeM) {
        this.nomeM = nomeM;
    }

    public void setEnderecoM(String enderecoM) {
        this.enderecoM = enderecoM;
    }

    public void setPrecoP(float precoP) {
        this.precoP = precoP;
    }

    public void setAvaliacaoM(double avaliacaoM) {
        this.avaliacaoM = avaliacaoM;
    }

    public void setNomeP(String nomeP) {
        this.nomeP = nomeP;
    }



    public static List<ItemPesq> separaProdutoPorMercado(List<Produto> produtos,List<Mercado> mercados) throws IOException {
        List<ItemPesq> produtoMercado = new ArrayList<>();

        //armazenar cnpjs para não repetir
        List<String> cnpjsListados = new ArrayList<>();

        for (int i = 0; i < produtos.size(); i++) {
            //pegar o cnpj do mercado do produto
            Produto produto = produtos.get(i);
            String cnpj = produto.getCnpj();

            //verificar se ja existe um mercado na lista com esse cnpj
            if(!cnpjsListados.contains(cnpj)){
                cnpjsListados.add(cnpj);
                //trazer o mercado do banco pelo cnpj
                //for para encontrar o mercado da lista que tem o mesmo cnpj do produto
                for(Mercado m : mercados) {
                    if (m.getCnpj().equals(cnpj)) {
                        //adicionar o mercado e os produtos desse mercado na lista de produtoMercado
                        produtoMercado.add(new ItemPesq(m.getCnpj(),m.getNome(),m.getBairro(),m.getAvaliacao(),
                                produto.getNome(),produto.getPreco()));
                        break;
                    }
                }

            }
        }
        return produtoMercado;
    }

}

