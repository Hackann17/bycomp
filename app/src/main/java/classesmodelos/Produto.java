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
    private double preco;
    private String unidade;

    public Produto(String nome, String cnpj, String codigo, double preco, String unidade) {
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

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setUnidade(String unidade) {this.unidade = unidade;}

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
