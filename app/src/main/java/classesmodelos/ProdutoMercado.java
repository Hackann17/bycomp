package classesmodelos;

import java.util.ArrayList;
import java.util.List;

public class ProdutoMercado{
    private Mercado mercado;
    private List<Produto> produtos;

    public ProdutoMercado(Mercado m, List<Produto> p) {
        this.mercado = m;
        this.produtos = p;
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

    //separar produtos por mercado
    public List<ProdutoMercado> separar(List<Produto> produtos) {
        List<ProdutoMercado> produtoMercado = new ArrayList<>();
        //armazenar cnpjs para não repetir
        List<String> cnpjsListados = new ArrayList<>();

        for (int i = 0; i < produtos.size(); i++) {
            //pegar o cnpj do mercado do produto
            String cnpj = produtos.get(i).getCnpj();

            //verificar se ja existe um mercado na lista com esse cnpj
            if(!cnpjsListados.contains(cnpj)){
                cnpjsListados.add(cnpj);
                //lista com todos os produtos na lista de produtos que tem esse cnpj
                List<Produto> produtosDoMercado = new ArrayList<>();
                //for para adicionar todos os produtos com esse cnpj na lista
                for (Produto p : produtos) {
                    if(p.getCnpj().equals(cnpj)){
                        produtosDoMercado.add(p);
                    }
                }
                Mercado mercado = null;////////////////////////////////
                //adicionar o mercado e os produtos desse mercado na lista de produtoMercado
                produtoMercado.add(new ProdutoMercado(mercado,produtosDoMercado));
            }

        }
        return produtoMercado;
    }
}
