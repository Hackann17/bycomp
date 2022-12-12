package com.example.projetofinal;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import classesmodelos.ItemPesq;
import recyclerviewclasses.ProdutoAdapter;

public class PesquisaProduto extends AppCompatActivity {
    //receber lista serializada , dessserializar , puxar recycler view e adicionar as informaççoes nele

    FloatingActionButton fbtVoltar;
    private ArrayList<ItemPesq> produtos;
    //LayoutInflater inflater =

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisaproduto);
        produtos = (ArrayList<ItemPesq>) getIntent().getSerializableExtra("produtos");

            //passando a lista par o adapter personalizada
            RecyclerView recyclerTela = findViewById(R.id.LPM);

            recyclerTela.setLayoutManager(new LinearLayoutManager(PesquisaProduto.this));
            recyclerTela.setAdapter(new ProdutoAdapter(produtos));

            //gerando o metodo para voltar para a activity principal
        fbtVoltar= findViewById(R.id.fbtVoltar);

        fbtVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(PesquisaProduto.this,Bycomp.class));
            }
        });


    }





}