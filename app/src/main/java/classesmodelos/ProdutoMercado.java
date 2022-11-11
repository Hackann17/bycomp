package classesmodelos;

import java.util.ArrayList;
import java.util.List;

public class ProdutoMercado{
    private Mercado mercado;
    private List<Produto> produtos;
    private float valorTotal;

    public ProdutoMercado(Mercado m, List<Produto> p) {
        this.mercado = m;
        this.produtos = p;
        for(Produto produto : produtos) {
            this.valorTotal += produto.getPreco();
        }
    }

    //getters e setters
    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public float getValorTotal() { return valorTotal; }

}
