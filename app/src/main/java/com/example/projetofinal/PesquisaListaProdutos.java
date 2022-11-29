package com.example.projetofinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classesmodelos.Mercado;
import classesmodelos.Produto;
import classesmodelos.ProdutoMercado;

public class PesquisaListaProdutos extends AppCompatActivity {

    //essa classe msotrar o resultado da pesquisa de mercados
    //recebera dois layouts para o recycler view
    List<ProdutoMercado> produtosMercado;
    List<Produto> produtos;
    List<Mercado> mercados;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);

        produtos = (ArrayList<Produto>) getIntent().getSerializableExtra("produtos");
        mercados = (ArrayList<Mercado>) getIntent().getSerializableExtra("mercados");

        try {
            produtosMercado = ProdutoMercado.separaProdutoPorMercado(produtos,mercados);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ProdutoMercado pm : produtosMercado){
            for(Produto p : produtos)
                Log.e("produtos", p.getNome()+" "+p.getPreco()+" "+p.getCnpj());
            Log.e("mercado",pm.getMercado().getCnpj()+" "+pm.getMercado().getNome()+" "+pm.getMercado().getAvaliacao());
        }

    }


}