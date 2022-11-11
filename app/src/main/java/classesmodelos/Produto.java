package classesmodelos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Produto {
    private String nome;
    private String cnpj;
    private String codigo;
    private float preco;
    private String unidade;

    public Produto(String nome, String cnpj, String codigo, float preco, String unidade) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.codigo = codigo;
        this.preco = preco;
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPreco() {
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

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void setUnidade(String unidade) {this.unidade = unidade;}

    //método para separar os produtos do banco de acordo com os itens que o usuario digitou na lista de compras
    public static List<Produto> separarProdutos(List<Produto> produtosBanco, List<String> itens){

        List<Produto> produtos = new ArrayList<>();
        //for que vai percorrer a lista de itens do usuario
        for (int indexP = 0; indexP < itens.size(); indexP++) {

            //lista para guardar os produtos encontrados para não repetir produtos do mesmo mercado e do mesmo tipo
            List<Produto> produtosEncontrados = new ArrayList<>();
            //for que percorre a lista produtosBanco que contém todos os produtos do firebase
            for (int index = 0; index < produtosBanco.size(); index++) {

                //variavel que recebe o produto sendo analizado
                Produto produtoBanco = produtosBanco.get(index);
                //variavel que recebe o nome de cada item na lista produtosBanco
                String produtoBancoNome = produtoBanco.getNome().toUpperCase().trim();
                //variavel que recebe o nome de cada item da lista de usuario
                String produtoUsuarioNome = itens.get(indexP).toUpperCase().trim();

                //verifica se os produtos no banco contém o item que o usuario passou
                if (produtoBancoNome.contains(produtoUsuarioNome)) {
                    //verifica se o mercado do produto encontrado ja tem um produto do mesmo tipo
                    for(int indexPB = 0; indexPB < produtosEncontrados.size(); indexPB++){
                        if(produtosEncontrados.get(indexPB).getCnpj().equals(produtoBanco.getCnpj())){
                            //caso for encontrado, verifica se o preço desse produto é menor
                            if(produtoBanco.getPreco()<produtosEncontrados.get(indexPB).getPreco()){
                                produtos.remove(produtosEncontrados.get(indexPB));
                                produtos.add(produtoBanco);
                                produtosEncontrados.add(produtoBanco);
                            }
                        }
                    }
                    //verifica se o produto ja foi encontrado anteriormente
                    if(!produtosEncontrados.contains(produtoBanco)) {
                        produtos.add(produtoBanco); //adiciona na lista de produtos global
                        produtosEncontrados.add(produtoBanco);
                    }
                    //Log.e("Produtos contem: ", produtoBanco.getNome());
                } else {
                    //Log.e("Não foi encontrado: ", produtosBanco.get(index).getNome());
                }
            }
        }
        return produtos;
    }

    //método para reorganizar uma lista de produtos pela ordem de mais barato para mais caro
    public static List<Produto> organizarPorPreco(List<Produto> produtos){
        List<Produto> produtosOrganizados = new ArrayList<>();

        Produto ultimoMaisBarato = null;

        //passa por cada produto da lista de produtos
        for (Produto p : produtos) {
            //redefinir como null
            ultimoMaisBarato = null;
            //passa por cada produto da lista de produtos mais uma vez
            for(int i = 0; i < produtos.size(); i++){

                if(!produtosOrganizados.contains(produtos.get(i)))//verificar se o produto sendo analizado ja esta na lista organizada
                if(ultimoMaisBarato==null||
                         produtos.get(i).getPreco()<ultimoMaisBarato.getPreco()){//verificar se o produto sendo analizado é mais barato que o ultimo
                    ultimoMaisBarato = produtos.get(i);
                }
            }
            //adicionar o ultimo mais barato na lista
            produtosOrganizados.add(ultimoMaisBarato);
        }

        return produtosOrganizados;
    }
}
