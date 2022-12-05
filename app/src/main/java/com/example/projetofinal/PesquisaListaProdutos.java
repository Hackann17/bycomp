package com.example.projetofinal;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classesmodelos.Mercado;
import classesmodelos.Produto;
import classesmodelos.ProdutoMercado;
import recyclerviewclasses.ListaProdutoAdapter;

public class PesquisaListaProdutos extends AppCompatActivity {

    //essa classe msotrar o resultado da pesquisa de mercados
    //recebera dois layouts para o recycler view
    List<ProdutoMercado> produtosMercado;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisalistaproduto);

        List<Produto> produtos = (ArrayList<Produto>) getIntent().getSerializableExtra("produtos");
        List<Mercado> mercados = (ArrayList<Mercado>) getIntent().getSerializableExtra("mercados");


        try {
            produtosMercado = ProdutoMercado.separaProdutoPorMercado(produtos,mercados);
        }
        catch (NullPointerException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(PesquisaListaProdutos.this);
            builder.setMessage("Não possível realizar a pesquisa")
                    .setPositiveButton("Retornar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Navigation.findNavController(getCurrentFocus()).navigate(R.id.lista2);
                        }
                    });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for (ProdutoMercado pm : produtosMercado){
            for(Produto p : pm.getProdutos())
                Log.e("produtos", p.getNome()+" "+p.getPreco()+" "+p.getCnpj());
            Log.e("mercado",pm.getMercado().getCnpj()+" "+pm.getMercado().getNome()+" "+pm.getMercado().getAvaliacao());
        }
        //passando a lista par o adapter personalizad
        RecyclerView recyclerTela = findViewById(R.id.ListaTelapes);

        recyclerTela.setLayoutManager(new LinearLayoutManager(PesquisaListaProdutos.this));
        recyclerTela.setAdapter(new ListaProdutoAdapter(produtosMercado));
    }


}