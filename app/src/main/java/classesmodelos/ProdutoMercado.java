package classesmodelos;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProdutoMercado extends ArrayList<ProdutoMercado> implements Serializable {
    private Mercado mercado;
    private List<Produto> produtos;
    private Produto produto;
    private float valorTotal;

    public ProdutoMercado(List<Produto> p,Mercado m) {
        this.mercado = m;
        this.produtos = p;
        for(Produto produto : produtos) {
            this.valorTotal += produto.getPreco();
        }
    }

    public ProdutoMercado(Produto p, Mercado m){
        this.mercado = m;
        this.produto = p;
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


    public static List<ProdutoMercado> separaProdutoPorMercado(List<Produto> produtos,List<Mercado> mercados) throws IOException {
        List<ProdutoMercado> produtoMercado = new ArrayList<>();

        //armazenar cnpjs para não repetir
        List<String> cnpjsListados = new ArrayList<>();

        int maiorListaSize = 0;

        for (int i = 0; i < produtos.size(); i++) {
            //pegar o cnpj do mercado do produto
            String cnpj = produtos.get(i).getCnpj();

            //verificar se ja existe um mercado na lista com esse cnpj
            if(!cnpjsListados.contains(cnpj)){
                cnpjsListados.add(cnpj);
                //lista com todos os produtos na lista de produtos que tem esse cnpj
                List<Produto> produtosDoMercado = new ArrayList<>();
                //for para adicionar todos os produtos com esse cnpj na lista
                int listaSize = 0;
                for (Produto p : produtos) {
                    if(p.getCnpj().equals(cnpj)){
                        produtosDoMercado.add(p);
                        listaSize++;
                    }
                }
                if(listaSize>maiorListaSize)
                    maiorListaSize = listaSize;

                Mercado mercado = null;
                //for para encontrar o mercado da lista que tem o mesmo cnpj do produto
                for(Mercado m : mercados) {
                    if (m.getCnpj().equals(cnpj)) {
                        mercado = m;
                        break;
                    }
                }

                //adicionar o mercado e os produtos desse mercado na lista de produtoMercado
                produtoMercado.add(new ProdutoMercado(produtosDoMercado, mercado));
            }

        }

        //for para manter todas as listas de produto com o mesmo tamanho
        for(int i = 0; i < produtoMercado.size(); i++){
            if(produtoMercado.get(i).getProdutos().size()<maiorListaSize){
                produtoMercado.remove(i);
            }
        }

        return produtoMercado;
    }
}
