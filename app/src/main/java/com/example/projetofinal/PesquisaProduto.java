package com.example.projetofinal;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import classesmodelos.ItemPesq;
import recyclerviewclasses.ProdutoAdapter;

public class PesquisaProduto extends AppCompatActivity {
    //receber lista serializada , dessserializar , puxar recycler view e adicionar as informaççoes nele

    private ArrayList<ItemPesq> produtos;
    //LayoutInflater inflater =

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisaproduto);
        produtos = (ArrayList<ItemPesq>) getIntent().getSerializableExtra("produtos");


            //dados a serem mostrados :
            //itempesq: nomeP, precoP, nomeM
            //itempesq2:nomeP, precoP, nomeM,loalizaçaoM,distanciaM, avaliaçaoM

            for (ItemPesq p : produtos) {
                Log.e("produto",p.getNomeP()+" "+p.getPrecoP());
                Log.e("mercado",p.getCnpjM()+" "+p.getNomeM()+" "+p.getAvaliacaoM());
            }

            //passando a lista par o adapter personalizad
            RecyclerView recyclerTela = findViewById(R.id.LPM);

            recyclerTela.setLayoutManager(new LinearLayoutManager(PesquisaProduto.this));
            recyclerTela.setAdapter(new ProdutoAdapter(produtos));




    }





}