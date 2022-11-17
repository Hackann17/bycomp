package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import classesmodelos.Produto;

public class PesquisaProduto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisamercado);

        //receber lista serializada , dessserializar , puxar recycler view e adicionar as informaççoes em

        List<Produto> produtos = (List<Produto>) getIntent().getSerializableExtra("pessaos");

        //teste
        //for (Produto p:produtos) {
            Log.e("Passou","------------>"+produtos);

      //  }







    }
}