package com.example.projetofinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import classesmodelos.Postagem;
import io.grpc.internal.JsonParser;

public class AvaliacoesFragment extends Fragment {

    //componentes da tela
    Button publicar, arquivar;
    RatingBar qtdEstrelas;
    TextView comentarioUsuario;
    RadioButton barato, qualidadeBoa, qualidadeRuim, caro;
    String rating;
    View v;

    //variaveis
    String comentario;
    Postagem postagem = null; //lista que vai guardar tudo o que vier da tela avaliacao
    RadioButton[] rdButtom = new RadioButton[4]; //guarda todos os checkboxes que possuem na tela
    List <String> opcoes = new ArrayList<>(); //guarda as opcoes

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_avaliacoes, container, false);

        //recurso.setBackgroundResource(R.drawable.botaoroxoescuro);

        //botoes
        publicar = v.findViewById(R.id.butPub);
        arquivar = v.findViewById(R.id.butArq);

        //ratingBar
        qtdEstrelas = v.findViewById(R.id.ratingBar);

        //input que o usuario comentou
        comentarioUsuario = v.findViewById(R.id.comentarioUsuario);

        //radioButton
        barato = v.findViewById(R.id.radioButtonBarato);
        qualidadeBoa = v.findViewById(R.id.radioButtonQualidadeBoa);
        qualidadeRuim = v.findViewById(R.id.radioButtonQualidadeInf);
        caro = v.findViewById(R.id.radioButtonCaro);
        
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comentario = comentarioUsuario.getText().toString();
                //pega a quantidade de estrelas que o usuario passou
                 rating = String.valueOf(qtdEstrelas.getRating());

                if(rating.equals("0.0")){
                    Toast.makeText(getContext(), "Por favor, faça a avaliação do mercado", Toast.LENGTH_SHORT).show();
                } else {
                    //adiciona os checkboxs na lista
                    rdButtom[0] = qualidadeBoa;
                    rdButtom[1] = qualidadeRuim;
                    rdButtom[2] = barato;
                    rdButtom[3] = caro;

                    verificarCheckSelecionado();

                    Log.e("Postagem: ", postagem.getAvaliacaoMercado());
                    Log.e("Postagem: ", postagem.getComentario());

                    for(int i = 0; i < postagem.getAdicional().size(); i++){
                        Log.e("Postagem: ", postagem.getAdicional().get(i));
                    }


                    /*for(int i = 0; i < postagem.size(); i++){
                        Log.e("Postagem: ", postagem.get(i).getComentario());
                        Log.e("Postagem: ", postagem.get(i).getAvaliacaoMercado());
                        Log.e("Postagem: ", postagem.get(i).getAdicional().get(i));
                    }*/


                }



                arquivar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor gravar = getContext().getSharedPreferences("postagem", Context.MODE_PRIVATE).edit();
                        gravar.putString("postagem", new Gson().toJson(postagem));
                        gravar.apply();

                        Log.e("FEITO!", "FEITO");


//                        Intent i = new Intent(getContext(), Historico.class);
//                        i.putExtra("armazenar", postagem);
//                        startActivity(i);
                    }
                });
            }

           private void verificarCheckSelecionado() {
               Postagem p = null;
               //tamanho do array checkboxes
                for(int i = 0; i < rdButtom.length; i++){
                    //verifica os indices que estão selecionados
                    if(rdButtom[i].isChecked()){
                        //adiciona numa lista de string
                        opcoes.add(rdButtom[i].getText().toString());
                    }
                }

               //cria o objeto postagem e adiciona o que o usuario colocou
               p = new Postagem(rating, comentario, opcoes);

                //percorre o tamanho das opcoes
                for(int i = 0; i < opcoes.size(); i++){
                    Log.e("Foi selecionado: ", opcoes.get(i));
                    //adiciona numa lista de objetos
                }

               postagem = p;
            }
        });

        return v;
    }
}