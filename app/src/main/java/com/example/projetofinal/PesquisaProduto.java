package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        //receberá um lista de itempesq vinda da tela Bycomp
        ArrayList<ItemPesq> itemp = new ArrayList<>();
        if(produtos==null){
            //retorn aum toast e devolve o ususario a tela inicial
            Toast.makeText(this, "Não temos esse produto,ou houve um erro de digitação", Toast.LENGTH_LONG).show();
            startActivity(new Intent(PesquisaProduto.this, Bycomp.class));

        }else{

            //dados a serem mostrados :
            //itempesq: nomeP, precoP, nomeM
            //itempesq2:nomeP, precoP, nomeM,loalizaçaoM,distanciaM, avaliaçaoM

            //lista simulada recyclerview
            //esses dois representam um item do recycler view
            ItemPesq itp1 = new ItemPesq("0","Calegaris","Aven Amizade",2,"", (float)6.00);
            ItemPesq itp2 = new ItemPesq("1","Galassi","Aven Saudade",3,"Cebola",(float)5.00);
            ItemPesq itp3 = new ItemPesq("2","Pague-Menos","Aven Amizade",5,"Yogurte Danone morango",(float)4.20);


            itemp.add(itp1);
            itemp.add(itp2);
            itemp.add(itp3);
            //passando a lista par o adapter personalizad
            RecyclerView recyclerTela = findViewById(R.id.ListaTelaPesq);
            recyclerTela.setLayoutManager(new LinearLayoutManager(PesquisaProduto.this));
            recyclerTela.setAdapter(new ProdutoAdapter(itemp));

            //repete a ista de produtos no log
            for (Produto p : produtos) {

                Log.e("ordem de mercado: ", p.getNome()+" "+p.getPreco()+" "+p.getCnpj());


            }
        }


    }

/* antes de gerar esses metodos deve se pegar as informçoes em questao , para so no final aplicar no recyclerview


    private void aplicandoAdapter() {



    }

    private void mostrarProdutos() {

    }*/
}