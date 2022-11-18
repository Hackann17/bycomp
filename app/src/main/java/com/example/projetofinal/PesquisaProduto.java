package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import classesmodelos.Produto;

public class PesquisaProduto extends AppCompatActivity {
    //receber lista serializada , dessserializar , puxar recycler view e adicionar as informaççoes nele

    private ArrayList<Produto> produtos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisaproduto);

        produtos = (ArrayList<Produto>) getIntent().getSerializableExtra("produtos");

        if(produtos==null){
            //retorn aum toast e devolve o ususario a tela inicial
            Toast.makeText(this, "Não temos esse produto,ou houve um erro de digitação", Toast.LENGTH_LONG).show();
            startActivity(new Intent(PesquisaProduto.this, Bycomp.class));

        }else{
            Log.e("else","");
            //repetir no recycler vire
            
            mostrarProdutos();
            aplicandoAdapter();
            
            
            
            //repete a ista de produtos no log
            for (Produto p : produtos) {

                Log.e("ordem de mercado: ", p.getNome()+" "+p.getPreco()+" "+p.getCnpj());


            }
        }


    }

    private void aplicandoAdapter() {



    }

    private void mostrarProdutos() {

    }
}